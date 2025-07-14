package kotetsu.auth.application.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kotetsu.auth.application.constant.ScopeNameConstant;
import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.dto.data.ScopeData;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.dto.store.AccessTokenDraftStore;
import kotetsu.auth.application.dto.store.AuthorizationCodeStore;
import kotetsu.auth.application.dto.store.IdTokenDraftStore;
import kotetsu.auth.application.exception.ClientCheckIOException;
import kotetsu.auth.application.exception.ClientNotFoundIOException;
import kotetsu.auth.application.exception.InputNullException;
import kotetsu.auth.application.exception.InvalidPendingScopesIOException;
import kotetsu.auth.application.persistence.IFindClientInformationByIdPort;
import kotetsu.auth.application.persistence.IFindPermittedScopeListByClientCodePort;
import kotetsu.auth.application.persistence.IFindScopeListByScopeNameListPort;
import kotetsu.auth.application.persistence.IStoreAccessTokenDraftPort;
import kotetsu.auth.application.persistence.IStoreAuthorizationCodePort;
import kotetsu.auth.application.persistence.IStoreIdTokenDraftPort;
import kotetsu.auth.application.util.IGenerateAuthorizationCodeValuePort;
import kotetsu.auth.application.util.IGetCurrentInstantPort;
import kotetsu.auth.application.util.IGetSelfUrlPort;

public class GetAuthorizationCodeUsecase {
    private final IStoreAccessTokenDraftPort storeAccessTokenDraftPort;

    private final IStoreIdTokenDraftPort storeIdTokenDraftPort;

    private final IFindScopeListByScopeNameListPort findScopeListByScopeNameList;

    private final IFindClientInformationByIdPort findClientInformationByIdPort;

    private final IFindPermittedScopeListByClientCodePort findPermittedScopeListByClientCodePort;

    private final IStoreAuthorizationCodePort storeAuthorizationCodePort;

    private final IGenerateAuthorizationCodeValuePort generateAuthorizationCodeValuePort;

    private final IGetSelfUrlPort getSelfUrlport;

    private final IGetCurrentInstantPort getCurrentInstantPort;
    
    public GetAuthorizationCodeUsecase(
        final IStoreAccessTokenDraftPort storeAccessTokenDraftPort,
        final IStoreIdTokenDraftPort storeIdTokenDraftPort,
        final IStoreAuthorizationCodePort storeAuthorizationCodePort,
        final IFindScopeListByScopeNameListPort findScopeListByScopeNameList,
        final IFindPermittedScopeListByClientCodePort findPermittedScopeListByClientCodePort,
        final IFindClientInformationByIdPort findClientInformationByIdPort,
        final IGenerateAuthorizationCodeValuePort generateAuthorizationCodeValuePort,
        final IGetSelfUrlPort getSelfUrlport,
        final IGetCurrentInstantPort getCurrentInstantPort
    ) {
        this.storeAccessTokenDraftPort = storeAccessTokenDraftPort;
        this.storeIdTokenDraftPort = storeIdTokenDraftPort;
        this.storeAuthorizationCodePort = storeAuthorizationCodePort;
        this.findScopeListByScopeNameList = findScopeListByScopeNameList;
        this.findPermittedScopeListByClientCodePort = findPermittedScopeListByClientCodePort;
        this.findClientInformationByIdPort = findClientInformationByIdPort;
        this.generateAuthorizationCodeValuePort = generateAuthorizationCodeValuePort;
        this.getSelfUrlport = getSelfUrlport;
        this.getCurrentInstantPort = getCurrentInstantPort;
    }

    @Transactional
    public AuthorizationCodeOutput getAuthorizationCode(final @Validated GetAuthorizationCodeInput input) 
        throws ClientNotFoundIOException, ClientCheckIOException, InvalidPendingScopesIOException
    {
        if (input == null) {
            throw new InputNullException();
        }

        final ClientInformationData clientInformation = findClientInformationByIdPort.findById(input.getClientId());
        if (clientInformation == null) {
            throw new ClientNotFoundIOException();
        }
        if (!clientInformation.getRedirectUri().equals(input.getRedirectUri())) {
            throw new ClientCheckIOException("redirectUriが登録情報と一致しません。");
        }

        final List<String> allPendingScopeNames = Arrays.asList(input.getPendingScopeString().split(" "));
        final List<String> oauth2PendingScopeNames = allPendingScopeNames.stream()
            .filter(scope -> !scope.equals(ScopeNameConstant.OPENID) && !scope.equals(ScopeNameConstant.OFFLINE_ACCESS))
            .collect(Collectors.toList());
        final List<ScopeData> permittedScopes = findPermittedScopeListByClientCodePort.findByClientCode(clientInformation.getCode());
        final Set<String> permittedScopeNames = permittedScopes.stream()
            .map(scope -> scope.getName())
            .collect(Collectors.toSet());
        if (!new HashSet<>(oauth2PendingScopeNames).stream().allMatch(permittedScopeNames::contains)) {
            throw new InvalidPendingScopesIOException("許可されていないscopeが含まれています。");
        }

        final List<ScopeData> scopes = findScopeListByScopeNameList.findByScopeNames(oauth2PendingScopeNames);
        final List<UUID> scopeCodes = scopes
            .stream()
            .map(scope -> scope.getCode())
            .toList();

        final UUID accessTokenDraftCode = storeAccessTokenDraftPort.store(AccessTokenDraftStore.of(
            getSelfUrlport.getUrl(),
            UUID.fromString(input.getResourceOwnerCode()),
            scopeCodes
        ));
        final UUID idTokenDraftCode = storeIdTokenDraftPort.store(IdTokenDraftStore.of(
            UUID.fromString(input.getResourceOwnerCode()),
            getSelfUrlport.getUrl(),
            clientInformation.getCode(),
            input.getNonce()
        ));
        final Instant current = getCurrentInstantPort.getCurrent();
        final String authorizationCode = storeAuthorizationCodePort.store(AuthorizationCodeStore.of(
            generateAuthorizationCodeValuePort.generate(),
            input.getCodeChallenge(),
            accessTokenDraftCode,
            idTokenDraftCode,
            Date.from(current),
            Date.from(current.plus(1, ChronoUnit.MINUTES)),
            allPendingScopeNames.contains(ScopeNameConstant.OPENID),
            allPendingScopeNames.contains(ScopeNameConstant.OFFLINE_ACCESS)
        ));
        return AuthorizationCodeOutput.of(authorizationCode);
    }
}
