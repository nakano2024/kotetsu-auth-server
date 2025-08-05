package kotetsu.auth.application.usecase;

import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.domain.entity.AccessTokenBody;
import kotetsu.auth.application.domain.entity.Authorization;
import kotetsu.auth.application.domain.entity.ClientBasicInformation;
import kotetsu.auth.application.domain.entity.IdTokenBody;
import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.RefreshTokenBody;
import kotetsu.auth.application.domain.entity.RequestedScopeAudienceWrapper;
import kotetsu.auth.application.domain.repository.IFetchClientBasicInformationPort;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchScopeAudienceWrapperPort;
import kotetsu.auth.application.domain.repository.IStoreAccessTokenBodyPort;
import kotetsu.auth.application.domain.repository.IStoreAuthorizationPort;
import kotetsu.auth.application.domain.repository.IStoreIdTokenBodyPort;
import kotetsu.auth.application.domain.repository.IStoreRefreshTokenBodyPort;
import kotetsu.auth.application.domain.service.CreateAuthorizationInformationService;
import kotetsu.auth.application.domain.util.IFetchServerUrlPort;
import kotetsu.auth.application.domain.util.IGenerateUuidPort;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.IdTokenAudience;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.LinkedAccessTokenBodyId;
import kotetsu.auth.application.domain.value.LinkedIdTokenBodyId;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenBodyId;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.exception.InputNullException;
import kotetsu.auth.application.exception.InvalidRequestedScopesIOException;

public class GetAuthorizationCodeUsecase {
    final IFetchPermittedScopeListPort permittedScopeListPort;
    final IFetchScopeAudienceWrapperPort fetchScopeAudienceWrapperPort;
    final IFetchClientBasicInformationPort fetchClientBasicInformationPort;
    final IStoreAccessTokenBodyPort storeAccessTokenBodyPort;
    final IStoreIdTokenBodyPort storeIdTokenBodyPort;
    final IStoreRefreshTokenBodyPort storeRefreshTokenBodyPort;
    final IStoreAuthorizationPort storeAuthorizationPort;
    final CreateAuthorizationInformationService createAuthorizationInformationService;
    final IFetchServerUrlPort fetchServerUrlPort;
    final IGenerateUuidPort generateUuidPort;

    public GetAuthorizationCodeUsecase(
        final IFetchPermittedScopeListPort permittedScopeListPort,
        final IFetchScopeAudienceWrapperPort fetchScopeAudienceWrapperPort,
        final IFetchClientBasicInformationPort fetchClientBasicInformationPort,
        final IStoreAccessTokenBodyPort storeAccessTokenBodyPort,
        final IStoreIdTokenBodyPort storeIdTokenBodyPort,
        final IStoreRefreshTokenBodyPort storeRefreshTokenBodyPort,
        final IStoreAuthorizationPort storeAuthorizationPort,
        final CreateAuthorizationInformationService createAuthorizationInformationService,
        final IFetchServerUrlPort fetchServerUrlPort,
        final IGenerateUuidPort generateUuidPort
    ) {
        this.permittedScopeListPort = permittedScopeListPort;
        this.fetchScopeAudienceWrapperPort = fetchScopeAudienceWrapperPort;
        this.fetchClientBasicInformationPort = fetchClientBasicInformationPort;
        this.storeAccessTokenBodyPort = storeAccessTokenBodyPort;
        this.storeIdTokenBodyPort = storeIdTokenBodyPort;
        this.storeRefreshTokenBodyPort = storeRefreshTokenBodyPort;
        this.storeAuthorizationPort = storeAuthorizationPort;
        this.createAuthorizationInformationService = createAuthorizationInformationService;
        this.fetchServerUrlPort = fetchServerUrlPort;
        this.generateUuidPort = generateUuidPort;
    }

    @Transactional
    public AuthorizationCodeOutput execute(GetAuthorizationCodeInput input) {
        if (input == null) {
            throw new InputNullException();
        }

        final ClientBasicInformation client = fetchClientBasicInformationPort.fetch(ClientId.of(input.getClientId()));

        final RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(RequestedScopeNameListToken.of(input.getScopeListToken()));
        
        RequestedScopeAudienceWrapper requestedScopeAudienceWrapper = fetchScopeAudienceWrapperPort.fetch(requestedScopeNameList);
        PermittedScopeList permittedScopeList =  permittedScopeListPort.fetch(Id.of(client.getId().getValue()));
        if(!permittedScopeList.containsAll(requestedScopeAudienceWrapper.getRequestedScopeList().getScopes())) {
            throw new InvalidRequestedScopesIOException("許可されていないscopeが含まれています。");
        }

        final AccessTokenBody accessTokenBody = AccessTokenBody.of(
            Id.of(generateUuidPort.generate()),
            Issuer.of(fetchServerUrlPort.fetch()),
            Subject.of(input.getResourceOwnerCode()),
            requestedScopeAudienceWrapper.getRequestedScopeList(),
            requestedScopeAudienceWrapper.getRequestedScopeRelatedAudienceList()
            
        );

        final IdTokenBody idTokenBody = IdTokenBody.of(
            Id.of((generateUuidPort.generate())),
            Issuer.of(fetchServerUrlPort.fetch()),
            Subject.of(input.getResourceOwnerCode()),
            Nonce.of(input.getNonce()),
            IdTokenAudience.of(client.getClientId().getValue())
        );

        final RefreshTokenBody refreshTokenBody = RefreshTokenBody.of(
            Id.of(generateUuidPort.generate()),
            LinkedAccessTokenBodyId.of(accessTokenBody.getId().getValue()), 
            LinkedIdTokenBodyId.of(idTokenBody.getId().getValue())
        );

        final Authorization authorization = createAuthorizationInformationService.create(
            Id.of(generateUuidPort.generate()),
            AuthorizationCodeChallenge.of(input.getCodeChallenge()),
            AccessType.of(input.getAccessType()),
            LinkedAccessTokenBodyId.of(accessTokenBody.getId().getValue()),
            LinkedIdTokenBodyId.of(idTokenBody.getId().getValue()),
            LinkedRefreshTokenBodyId.of(refreshTokenBody.getId().getValue())
        );

        storeAccessTokenBodyPort.store(accessTokenBody);
        storeIdTokenBodyPort.store(idTokenBody);
        storeRefreshTokenBodyPort.store(refreshTokenBody);
        storeAuthorizationPort.store(authorization);

        return AuthorizationCodeOutput.of(authorization.getAuthorizationCode().getToken().getValue());
    }
}
