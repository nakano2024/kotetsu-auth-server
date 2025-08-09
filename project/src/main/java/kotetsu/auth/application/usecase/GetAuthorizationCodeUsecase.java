package kotetsu.auth.application.usecase;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;
import kotetsu.auth.application.domain.entity.PendingIdTokenCore;
import kotetsu.auth.application.domain.entity.PendingRefreshTokenCore;
import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.RequestedAuthorization;
import kotetsu.auth.application.domain.entity.RequestedScopeAudienceWrapper;
import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.repository.IFetchScopeAudienceWrapperPort;
import kotetsu.auth.application.domain.repository.IStorePendingAccessTokenCorePort;
import kotetsu.auth.application.domain.repository.IStorePendingIdTokenCorePort;
import kotetsu.auth.application.domain.repository.IStorePendingRefreshTokenCorePort;
import kotetsu.auth.application.domain.repository.IStoreRequestedAuthorizationPort;
import kotetsu.auth.application.domain.service.CreateAuthorizationService;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.util.IFetchServerUrlPort;
import kotetsu.auth.application.domain.util.IGenerateUuidPort;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.IdTokenAudience;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.ClientNotPermittedScopesContainedException;
import kotetsu.auth.application.exception.PermittedScopeListNullRuntimeException;
import kotetsu.auth.application.exception.RequestedScopeAudienceWrapperNullRuntimeException;
import kotetsu.auth.application.exception.RequesterClientNotFoundRuntimeException;

public class GetAuthorizationCodeUsecase {
    final IFetchPermittedScopeListPort permittedScopeListPort;
    final IFetchScopeAudienceWrapperPort fetchScopeAudienceWrapperPort;
    final IFetchRequesterClientPort fetchRequeterClientPort;
    final IStorePendingAccessTokenCorePort storeAccessTokenBodyPort;
    final IStorePendingIdTokenCorePort storeIdTokenBodyPort;
    final IStorePendingRefreshTokenCorePort storeRefreshTokenBodyPort;
    final IStoreRequestedAuthorizationPort storeAuthorizationPort;
    final CreateAuthorizationService createAuthorizationInformationService;
    final IFetchServerUrlPort fetchServerUrlPort;
    final IGenerateUuidPort generateUuidPort;
    final IFetchCurrentDatePort fetchCurrentDatePort;

    public GetAuthorizationCodeUsecase(
        final IFetchPermittedScopeListPort permittedScopeListPort,
        final IFetchScopeAudienceWrapperPort fetchScopeAudienceWrapperPort,
        final IFetchRequesterClientPort fetchRequeterClientPort,
        final IStorePendingAccessTokenCorePort storeAccessTokenBodyPort,
        final IStorePendingIdTokenCorePort storeIdTokenBodyPort,
        final IStorePendingRefreshTokenCorePort storeRefreshTokenBodyPort,
        final IStoreRequestedAuthorizationPort storeAuthorizationPort,
        final CreateAuthorizationService createAuthorizationInformationService,
        final IFetchServerUrlPort fetchServerUrlPort,
        final IGenerateUuidPort generateUuidPort,
        final IFetchCurrentDatePort fetchCurrentDatePort
    ) {
        this.permittedScopeListPort = permittedScopeListPort;
        this.fetchScopeAudienceWrapperPort = fetchScopeAudienceWrapperPort;
        this.fetchRequeterClientPort = fetchRequeterClientPort;
        this.storeAccessTokenBodyPort = storeAccessTokenBodyPort;
        this.storeIdTokenBodyPort = storeIdTokenBodyPort;
        this.storeRefreshTokenBodyPort = storeRefreshTokenBodyPort;
        this.storeAuthorizationPort = storeAuthorizationPort;
        this.createAuthorizationInformationService = createAuthorizationInformationService;
        this.fetchServerUrlPort = fetchServerUrlPort;
        this.generateUuidPort = generateUuidPort;
        this.fetchCurrentDatePort = fetchCurrentDatePort;
    }

    @Transactional
    public AuthorizationCodeOutput execute(final GetAuthorizationCodeInput input)
        throws ClientNotPermittedScopesContainedException
    {
        final Date currentDate = fetchCurrentDatePort.fetch();

        if (input == null) {
            throw new InputNullRuntimeException();
        }

        final RequesterClient requesterClient = fetchRequeterClientPort.fetch(Key.of(input.getClientKey()))
            .orElseThrow(() -> new RequesterClientNotFoundRuntimeException());

        final RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(RequestedScopeNameListToken.of(input.getScopeListToken()));
        
        final RequestedScopeAudienceWrapper requestedScopeAudienceWrapper = fetchScopeAudienceWrapperPort.fetch(requestedScopeNameList)
            .orElseThrow(() -> new RequestedScopeAudienceWrapperNullRuntimeException());

        final PermittedScopeList permittedScopeList =  permittedScopeListPort.fetch(Key.of(requesterClient.getKey().getValue()))
            .orElseThrow(() -> new PermittedScopeListNullRuntimeException());

        if(!permittedScopeList.containsAll(requestedScopeAudienceWrapper.getRequestedScopeList().getScopes())) {
            throw new ClientNotPermittedScopesContainedException();
        }

        final PendingAccessTokenCore accessTokenCore = PendingAccessTokenCore.of(
            Key.of(generateUuidPort.generate()),
            Issuer.of(fetchServerUrlPort.fetch()),
            Subject.of(input.getResourceOwnerCode()),
            requestedScopeAudienceWrapper.getRequestedScopeList(),
            requestedScopeAudienceWrapper.getRequestedScopeRelatedAudienceList()
            
        );

        final PendingIdTokenCore idTokenCore = PendingIdTokenCore.of(
            Key.of((generateUuidPort.generate())),
            Issuer.of(fetchServerUrlPort.fetch()),
            Subject.of(input.getResourceOwnerCode()),
            Nonce.of(input.getNonce()),
            IdTokenAudience.of(requesterClient.getClientId().getValue())
        );

        final PendingRefreshTokenCore refreshTokenCore = PendingRefreshTokenCore.of(
            Key.of(generateUuidPort.generate()),
            LinkedAccessTokenCoreKey.of(accessTokenCore.getKey().getValue()), 
            LinkedIdTokenCoreKey.of(idTokenCore.getKey().getValue())
        );

        final RequestedAuthorization authorization = createAuthorizationInformationService.create(
            AuthorizationCodeChallenge.of(input.getCodeChallenge()),
            AccessType.of(input.getAccessType()),
            LinkedAccessTokenCoreKey.of(accessTokenCore.getKey().getValue()),
            LinkedIdTokenCoreKey.of(idTokenCore.getKey().getValue()),
            LinkedRefreshTokenCoreKey.of(refreshTokenCore.getKey().getValue()),
            IssuedAt.of(currentDate)
        );

        storeAccessTokenBodyPort.store(accessTokenCore);
        storeIdTokenBodyPort.store(idTokenCore);
        storeRefreshTokenBodyPort.store(refreshTokenCore);
        storeAuthorizationPort.store(authorization);

        return AuthorizationCodeOutput.of(authorization.getAuthorizationCode().getValue().getValue());
    }
}
