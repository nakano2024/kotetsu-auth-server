package kotetsu.auth.application.usecase;

import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.domain.entity.AccessTokenBody;
import kotetsu.auth.application.domain.entity.AuthorizationInformation;
import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.RequestedScopeAudienceWrapper;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchScopeAudienceListPort;
import kotetsu.auth.application.domain.repository.IStoreAccessTokenDraftPort;
import kotetsu.auth.application.domain.service.CreateAuthorizationInformationService;
import kotetsu.auth.application.domain.util.IFetchServerUrlPort;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.exception.InputNullException;
import kotetsu.auth.application.exception.InvalidPendingScopesIOException;

public class GetAuthorizationCodeUsecaseV2 {
    final IFetchPermittedScopeListPort permittedScopeListPort;
    final IFetchScopeAudienceListPort fetchScopeAudienceListPort;
    final IStoreAccessTokenDraftPort storeAccessTokenDraftPort;
    final CreateAuthorizationInformationService createAuthorizationInformationService;
    final IFetchServerUrlPort fetchServerUrlPort;

    public GetAuthorizationCodeUsecaseV2(
        final IFetchPermittedScopeListPort permittedScopeListPort,
        final IFetchScopeAudienceListPort fetchScopeAudienceListPort,
        final IStoreAccessTokenDraftPort storeAccessTokenDraftPort,
        final CreateAuthorizationInformationService createAuthorizationInformationService,
        final IFetchServerUrlPort fetchServerUrlPort
    ) {
        this.permittedScopeListPort = permittedScopeListPort;
        this.fetchScopeAudienceListPort = fetchScopeAudienceListPort;
        this.storeAccessTokenDraftPort = storeAccessTokenDraftPort;
        this.createAuthorizationInformationService = createAuthorizationInformationService;
        this.fetchServerUrlPort = fetchServerUrlPort;
    }

    @Transactional
    public AuthorizationCodeOutput execute(GetAuthorizationCodeInput input) {
        if (input == null) {
            throw new InputNullException();
        }

        final RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(RequestedScopeNameListToken.of(input.getScopeListToken()));
        
        RequestedScopeAudienceWrapper requestedScopeAudienceList = fetchScopeAudienceListPort.fetch(requestedScopeNameList);
        PermittedScopeList permittedScopeList =  permittedScopeListPort.fetch(Id.of("clientCode"));
        if(!permittedScopeList.containsAll(requestedScopeAudienceList.getRequestedScopeList().getScopes())) {
            throw new InvalidPendingScopesIOException("許可されていないscopeが含まれています。");
        }

        AuthorizationInformation authorizationInformation = createAuthorizationInformationService.create(
            Id.of("authinfoid"),
            AuthorizationCodeChallenge.of(input.getCodeChallenge()),
            AccessType.of(input.getAccessType())
        );

        AccessTokenBody accessTokenBody = AccessTokenBody.of(
            authorizationInformation.getId(),
            Issuer.of(fetchServerUrlPort.fetch()),
            Subject.of(input.getResourceOwnerCode()),
            requestedScopeAudienceList.getRequestedScopeList(),
            requestedScopeAudienceList.getRequestedScopeRelatedAudienceList()
            
        );

        if (requestedScopeAudienceList.getRequestedScopeList().hasOpenid()) {
            // TODO: IDトークンのBodyを保存
        }

        if (authorizationInformation.getAccessType().isOnline()) {
            // TODO: RefreshTokenのBodyを保存
        }

        storeAccessTokenDraftPort.store(accessTokenBody);

        return AuthorizationCodeOutput.of(authorizationInformation.getAuthorizationCode().getToken().getValue());
    }
}
