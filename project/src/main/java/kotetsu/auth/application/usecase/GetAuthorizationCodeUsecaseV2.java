package kotetsu.auth.application.usecase;

import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.domain.entity.AccessTokenBody;
import kotetsu.auth.application.domain.entity.AuthorizationInformation;
import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.ResourceScopeNameList;
import kotetsu.auth.application.domain.entity.ScopeAudienceList;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchScopeAudienceListPort;
import kotetsu.auth.application.domain.repository.IStoreAccessTokenDraftPort;
import kotetsu.auth.application.domain.service.CreateAuthorizationInformationService;
import kotetsu.auth.application.domain.util.IFetchServerUrlPort;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.EnableOfflineAccess;
import kotetsu.auth.application.domain.value.EnableOpenId;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.Issuer;
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

        final RequestedScopeNameListToken requestedScopeNameListToken = RequestedScopeNameListToken.of(input.getPendingScopeString());
        
        ResourceScopeNameList requestedResourceScopeNameList = ResourceScopeNameList.of(requestedScopeNameListToken);
        ScopeAudienceList scopeAudienceList = fetchScopeAudienceListPort.fetch(requestedResourceScopeNameList);
        PermittedScopeList permittedScopeList =  permittedScopeListPort.fetch(Id.of("clientCode"));
        if(!permittedScopeList.containsAll(scopeAudienceList.getScopes())) {
            throw new InvalidPendingScopesIOException("許可されていないscopeが含まれています。");
        }

        final EnableOpenId enableOpenId = EnableOpenId.of(requestedScopeNameListToken);
        final EnableOfflineAccess enableOfflineAccess = EnableOfflineAccess.of(requestedScopeNameListToken);
        AuthorizationInformation authorizationInformation = createAuthorizationInformationService.create(
            Id.of("authinfoid"),
            AuthorizationCodeChallenge.of(input.getCodeChallenge()),
            enableOpenId,
            enableOfflineAccess
        );

        AccessTokenBody accessTokenBody = AccessTokenBody.of(
            authorizationInformation.getId(),
            Issuer.of(fetchServerUrlPort.fetch()),
            Subject.of(input.getResourceOwnerCode()),
            scopeAudienceList
        );
        storeAccessTokenDraftPort.store(accessTokenBody);

        if (enableOpenId.isEnabled()) {
            // TODO: IDトークンのBodyを保存
        }

        if (enableOfflineAccess.isEnabled()) {
            // TODO: RefreshTokenのBodyを保存
        }

        return AuthorizationCodeOutput.of(authorizationInformation.getAuthorizationCode().getToken().getValue());
    }
}
