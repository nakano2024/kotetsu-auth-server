package kotetsu.auth.application.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kotetsu.auth.application.dto.data.AccessTokenDraftData;
import kotetsu.auth.application.dto.data.AuthorizationCodeData;
import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.dto.data.IdTokenDraftData;
import kotetsu.auth.application.dto.input.ExchangeTokenInput;
import kotetsu.auth.application.dto.output.TokenOutput;
import kotetsu.auth.application.dto.store.AccessTokenStore;
import kotetsu.auth.application.dto.store.RefreshTokenStore;
import kotetsu.auth.application.exception.AuthorizationCodeExpiredIOException;
import kotetsu.auth.application.exception.AuthorizationCodeNotFoundIOException;
import kotetsu.auth.application.exception.ClientCheckIOException;
import kotetsu.auth.application.exception.ClientNotFoundIOException;
import kotetsu.auth.application.exception.InputNullException;
import kotetsu.auth.application.persistence.IDeleteAuthorizationCodePort;
import kotetsu.auth.application.persistence.IFindAccessTokenDraftByIdPort;
import kotetsu.auth.application.persistence.IFindAuthorizationCodeByCodePort;
import kotetsu.auth.application.persistence.IFindClientInformationByIdPort;
import kotetsu.auth.application.persistence.IFindIdTokenDraftByCodePort;
import kotetsu.auth.application.persistence.IStoreAccessTokenPort;
import kotetsu.auth.application.persistence.IStoreRefreshTokenPort;
import kotetsu.auth.application.util.IGenerateIdTokenFromDraftPort;
import kotetsu.auth.application.util.IGenerateRandomStringPort;
import kotetsu.auth.application.util.IGetCurrentInstantPort;
import kotetsu.auth.application.util.IHashStringPort;

public class ExchangeTokenUsecase {
    private final IFindAuthorizationCodeByCodePort findAuthorizationCodeByCodePort;
    private final IFindClientInformationByIdPort findClientInformationByIdPort;
    private final IFindAccessTokenDraftByIdPort findAccessTokenDraftByIdPort;
    private final IFindIdTokenDraftByCodePort findIdTokenDraftByIdPort;
    private final IStoreAccessTokenPort storeAccessTokenPort;
    private final IStoreRefreshTokenPort storeRefreshTokenPort;
    private final IDeleteAuthorizationCodePort deleteAuthorizationCodePort;
    private final IHashStringPort hashStringPort;
    private final IGenerateRandomStringPort generateRandomStringPort;
    private final IGenerateIdTokenFromDraftPort generateIdTokenFromDraftPort;
    private final IGetCurrentInstantPort getCurrentInstantPort;

    public ExchangeTokenUsecase(
        final IFindAuthorizationCodeByCodePort findAuthorizationCodeByCodePort,
        final IFindClientInformationByIdPort findClientInformationByIdPort,
        final IFindAccessTokenDraftByIdPort findAccessTokenDraftByIdPort,
        final IFindIdTokenDraftByCodePort findIdTokenDraftByIdPort,
        final IStoreAccessTokenPort storeAccessTokenPort,
        final IStoreRefreshTokenPort storeRefreshTokenPort,
        final IDeleteAuthorizationCodePort deleteAuthorizationCodePort,
        final IHashStringPort hashStringPort,
        final IGenerateRandomStringPort generateRandomStringPort,
        final IGenerateIdTokenFromDraftPort generateIdTokenFromDraftPort,
        final IGetCurrentInstantPort getCurrentInstantPort
    ) {
        this.findAuthorizationCodeByCodePort = findAuthorizationCodeByCodePort;
        this.findClientInformationByIdPort = findClientInformationByIdPort;
        this.findAccessTokenDraftByIdPort = findAccessTokenDraftByIdPort;
        this.findIdTokenDraftByIdPort = findIdTokenDraftByIdPort;
        this.storeAccessTokenPort = storeAccessTokenPort;
        this.storeRefreshTokenPort = storeRefreshTokenPort;
        this.deleteAuthorizationCodePort = deleteAuthorizationCodePort;
        this.hashStringPort = hashStringPort;
        this.generateRandomStringPort = generateRandomStringPort;
        this.generateIdTokenFromDraftPort = generateIdTokenFromDraftPort;
        this.getCurrentInstantPort = getCurrentInstantPort;
    }

    @Transactional
    public TokenOutput exchangeToken(final @Validated ExchangeTokenInput input)
        throws ClientNotFoundIOException, ClientCheckIOException, AuthorizationCodeNotFoundIOException, AuthorizationCodeExpiredIOException
    {
        if (input == null) {
            throw new InputNullException();
        }

        final ClientInformationData clientInformation = findClientInformationByIdPort.findById(input.getClientId());
        if (clientInformation == null) {
            throw new ClientNotFoundIOException();
        }

        if (!clientInformation.getSecret().equals(input.getClientSecret())) {
            throw new ClientCheckIOException("クライアントシークレットが一致しません。");
        }

        if (!clientInformation.getRedirectUri().equals(input.getRedirectUri())) {
            throw new ClientCheckIOException("redirectUriが登録情報と一致しません。");
        }

        final AuthorizationCodeData authorizationCode = findAuthorizationCodeByCodePort.findByCode(input.getCode());
        if (authorizationCode == null) {
            throw new AuthorizationCodeNotFoundIOException("認可コードが見つかりません。");
        }

        final Instant current = getCurrentInstantPort.getCurrent();
        if (authorizationCode.getExpiredAt().before(Date.from(current))) {
            throw new AuthorizationCodeExpiredIOException("認可コードの有効期限が切れています。");
        }

        final String hashedCodeVerifier = hashStringPort.hashSha256(input.getCodeVerifier());
        if (!authorizationCode.getChallenge().equals(hashedCodeVerifier)) {
            throw new ClientCheckIOException("code_verifierが一致しません。");
        }

        final AccessTokenDraftData accessTokenDraft = findAccessTokenDraftByIdPort.findById(authorizationCode.getAccessTokenDraftCode());
        final IdTokenDraftData idTokenDraft = findIdTokenDraftByIdPort.findByCode(authorizationCode.getIdTokenDraftCode());

        final List<UUID> scopeCodes = accessTokenDraft.getScopes()
            .stream()
            .map(scope -> scope.getCode())
            .toList();

        final List<String> scopeNames = accessTokenDraft.getScopes()
            .stream()
            .map(scope -> scope.getName())
            .toList();

        final String accessTokenValue = storeAccessTokenPort.store(AccessTokenStore.of(
            generateRandomStringPort.generate(512),
            accessTokenDraft.getIssuer(),
            accessTokenDraft.getSubject(),
            scopeCodes,
            Date.from(current),
            Date.from(current.plus(1, ChronoUnit.HOURS))
        ));

        // else ifでの分岐を避けるためにあらかじめnullを格納し条件に応じてセットする方式にしている
        String idToken = null;
        if (authorizationCode.isEnableOpenid()) {
            idToken = generateIdTokenFromDraftPort.generate(idTokenDraft);
        }

        String refreshTokenValue = null;
        if (authorizationCode.isEnableOfflineAccess()) {
            refreshTokenValue = storeRefreshTokenPort.store(RefreshTokenStore.of(
                generateRandomStringPort.generate(256),
                authorizationCode.getAccessTokenDraftCode(),
                authorizationCode.getIdTokenDraftCode(),
                Date.from(current),
                Date.from(current.plus(30, ChronoUnit.DAYS))
            ));
        }

        deleteAuthorizationCodePort.deleteByCode(authorizationCode.getValue());

        return TokenOutput.of(
            accessTokenValue,
            "Bearer",
            3600L,
            refreshTokenValue,
            idToken,
            scopeNames,
            accessTokenDraft.getAudiences()
        );
    }
}