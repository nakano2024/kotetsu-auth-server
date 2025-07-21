package kotetsu.auth.application.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.constant.ClientCheckConstant;
import kotetsu.auth.application.constant.GrantTypeConstant;
import kotetsu.auth.application.dto.data.AccessTokenDraftData;
import kotetsu.auth.application.dto.data.AuthorizationCodeData;
import kotetsu.auth.application.dto.data.ClientCredentialData;
import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.dto.data.IdTokenDraftData;
import kotetsu.auth.application.dto.input.AuthCodeExchangeInput;
import kotetsu.auth.application.dto.input.CheckClientCredentialInput;
import kotetsu.auth.application.dto.input.CheckClientInput;
import kotetsu.auth.application.dto.input.ExchangeTokenInput;
import kotetsu.auth.application.dto.output.TokenOutput;
import kotetsu.auth.application.dto.store.AccessTokenStore;
import kotetsu.auth.application.dto.store.RefreshTokenStore;
import kotetsu.auth.application.exception.AuthorizationCodeExpiredIOException;
import kotetsu.auth.application.exception.AuthorizationCodeNotFoundIOException;
import kotetsu.auth.application.exception.ClientCheckIOException;
import kotetsu.auth.application.exception.ClientCredentialDataNullException;
import kotetsu.auth.application.exception.ClientNotFoundIOException;
import kotetsu.auth.application.exception.InputNullException;
import kotetsu.auth.application.exception.InvalidGrantTypeIOException;
import kotetsu.auth.application.persistence.IDeleteAuthorizationCodePort;
import kotetsu.auth.application.persistence.IFindAccessTokenDraftByIdPort;
import kotetsu.auth.application.persistence.IFindAuthorizationCodeByValuePort;
import kotetsu.auth.application.persistence.IFindClientInformationByIdPort;
import kotetsu.auth.application.persistence.IFindIdTokenDraftByCodePort;
import kotetsu.auth.application.persistence.IStoreAccessTokenPort;
import kotetsu.auth.application.persistence.IStoreRefreshTokenPort;
import kotetsu.auth.application.util.IGenerateAccessTokenValuePort;
import kotetsu.auth.application.util.IGenerateClientCredentialPort;
import kotetsu.auth.application.util.IGenerateIdTokenFromDraftPort;
import kotetsu.auth.application.util.IGenerateRefreshTokenValuePort;
import kotetsu.auth.application.util.IGetCurrentInstantPort;
import kotetsu.auth.application.util.IHashStringPort;

public class ExchangeTokenUsecase {
    private final IFindAuthorizationCodeByValuePort findAuthorizationCodeByCodePort;
    private final IFindClientInformationByIdPort findClientInformationByIdPort;
    private final IFindAccessTokenDraftByIdPort findAccessTokenDraftByIdPort;
    private final IFindIdTokenDraftByCodePort findIdTokenDraftByIdPort;
    private final IStoreAccessTokenPort storeAccessTokenPort;
    private final IStoreRefreshTokenPort storeRefreshTokenPort;
    private final IDeleteAuthorizationCodePort deleteAuthorizationCodePort;
    private final IHashStringPort hashStringPort;
    private final IGenerateAccessTokenValuePort generateAccessTokenValuePort;
    private final IGenerateRefreshTokenValuePort generateRefreshTokenValuePort;
    private final IGenerateIdTokenFromDraftPort generateIdTokenFromDraftPort;
    private final IGetCurrentInstantPort getCurrentInstantPort;
    private final IGenerateClientCredentialPort generateClientCredentialPort;

    public ExchangeTokenUsecase(
        final IFindAuthorizationCodeByValuePort findAuthorizationCodeByCodePort,
        final IFindClientInformationByIdPort findClientInformationByIdPort,
        final IFindAccessTokenDraftByIdPort findAccessTokenDraftByIdPort,
        final IFindIdTokenDraftByCodePort findIdTokenDraftByIdPort,
        final IStoreAccessTokenPort storeAccessTokenPort,
        final IStoreRefreshTokenPort storeRefreshTokenPort,
        final IDeleteAuthorizationCodePort deleteAuthorizationCodePort,
        final IHashStringPort hashStringPort,
        final IGenerateAccessTokenValuePort generateAccessTokenValuePort,
        final IGenerateRefreshTokenValuePort generateRefreshTokenValuePort,
        final IGenerateIdTokenFromDraftPort generateIdTokenFromDraftPort,
        final IGetCurrentInstantPort getCurrentInstantPort,
        final IGenerateClientCredentialPort generateClientCredentialPort
    ) {
        this.findAuthorizationCodeByCodePort = findAuthorizationCodeByCodePort;
        this.findClientInformationByIdPort = findClientInformationByIdPort;
        this.findAccessTokenDraftByIdPort = findAccessTokenDraftByIdPort;
        this.findIdTokenDraftByIdPort = findIdTokenDraftByIdPort;
        this.storeAccessTokenPort = storeAccessTokenPort;
        this.storeRefreshTokenPort = storeRefreshTokenPort;
        this.deleteAuthorizationCodePort = deleteAuthorizationCodePort;
        this.hashStringPort = hashStringPort;
        this.generateAccessTokenValuePort = generateAccessTokenValuePort;
        this.generateRefreshTokenValuePort = generateRefreshTokenValuePort;
        this.generateIdTokenFromDraftPort = generateIdTokenFromDraftPort;
        this.getCurrentInstantPort = getCurrentInstantPort;
        this.generateClientCredentialPort = generateClientCredentialPort;
    }

    @Transactional
    public TokenOutput exchangeToken(final ExchangeTokenInput input)
        throws ClientNotFoundIOException,
        ClientCheckIOException,
        AuthorizationCodeNotFoundIOException,
        AuthorizationCodeExpiredIOException,
        InvalidGrantTypeIOException
    {
        if (input == null) {
            throw new InputNullException();
        }

        if ((input.getClientCredentialToken() == null && (input.getClientId() == null || input.getClientSecret() == null))) {
            throw new ClientCheckIOException("client認証情報の入力が不正です。");
        }

        if (input.getClientCredentialToken() != null) {
            checkClientCredential(CheckClientCredentialInput.of(input.getClientCredentialToken(), input.getRedirectUri()));
        }

        if (input.getClientCredentialToken() == null) {
            checkClient(CheckClientInput.of(input.getClientId(), input.getClientSecret(), input.getRedirectUri()));
        }

        if (input.getGrantType().equals(GrantTypeConstant.AUTORIZATION_CODE)) {
            return exchangeWithAuthCode(AuthCodeExchangeInput.of(input.getCode(), input.getCodeVerifier()));
        }

        if (input.getGrantType().equals(GrantTypeConstant.REFRESH)) {
            return exchangeRefreshTokenForToken(input);
        }

        throw new InvalidGrantTypeIOException("無効なgrantTypeです。");
    }

    private void checkClient(final CheckClientInput input)
        throws ClientNotFoundIOException,
        ClientCheckIOException
    {
        if (input == null) {
            throw new InputNullException();
        }
        
        final ClientInformationData clientInformation = findClientInformationByIdPort.findById(input.getClientId());
        if (clientInformation == null) {
            throw new ClientNotFoundIOException();
        }

        if (!clientInformation.getSecret().equals(input.getClientSecret())) {
            throw new ClientCheckIOException(ClientCheckConstant.CLIENT_SECRET_ERROR_MESSAGE);
        }

        if (!clientInformation.getRedirectUri().equals(input.getRedirectUri())) {
            throw new ClientCheckIOException(ClientCheckConstant.REDIRECT_URI_ERROR_NESSAGE);
        }        
    }

    private void checkClientCredential(final CheckClientCredentialInput input)
        throws ClientNotFoundIOException,
        ClientCheckIOException
    {
        if (input == null) {
            throw new InputNullException();
        }

        final ClientCredentialData clientCredential = generateClientCredentialPort.generate(input.getCredentialToken());
        if (clientCredential == null) {
            throw new ClientCredentialDataNullException();
        }
        
        final ClientInformationData clientInformation = findClientInformationByIdPort.findById(clientCredential.getClientId());
        if (clientInformation == null) {
            throw new ClientNotFoundIOException();
        }

        if (!clientInformation.getSecret().equals(clientCredential.getClientSecret())) {
            throw new ClientCheckIOException(ClientCheckConstant.CLIENT_SECRET_ERROR_MESSAGE);
        }

        if (!clientInformation.getRedirectUri().equals(input.getRedirectUri())) {
            throw new ClientCheckIOException(ClientCheckConstant.REDIRECT_URI_ERROR_NESSAGE);
        }  
    }

    private TokenOutput exchangeWithAuthCode(final AuthCodeExchangeInput input) 
        throws ClientNotFoundIOException,
        ClientCheckIOException,
        AuthorizationCodeNotFoundIOException,
        AuthorizationCodeExpiredIOException,
        InvalidGrantTypeIOException
    {
        if (input == null) {
            throw new InputNullException();
        }

        final AuthorizationCodeData authorizationCode = findAuthorizationCodeByCodePort.findByValue(input.getCode());
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

        final List<UUID> scopeCodes = accessTokenDraft.getScopes()
            .stream()
            .map(scope -> scope.getCode())
            .toList();

        final List<String> scopeNames = accessTokenDraft.getScopes()
            .stream()
            .map(scope -> scope.getName())
            .toList();
        
        final List<String> audienceNames = accessTokenDraft.getAudiences()
            .stream()
            .map(audience -> audience.getUrl())
            .toList();

        final String accessTokenValue = storeAccessTokenPort.store(AccessTokenStore.of(
            generateAccessTokenValuePort.generate(),
            accessTokenDraft.getIssuer(),
            accessTokenDraft.getSubject(),
            scopeCodes,
            Date.from(current),
            Date.from(current.plus(1, ChronoUnit.HOURS))
        ));

        // else ifでの分岐を避けるためにあらかじめnullを格納し条件に応じてセットする方式にしている
        final IdTokenDraftData idTokenDraft = findIdTokenDraftByIdPort.findByCode(authorizationCode.getIdTokenDraftCode());
        String idToken = null;
        if (authorizationCode.isEnableOpenid()) {
            idToken = generateIdTokenFromDraftPort.generate(idTokenDraft);
        }

        String refreshTokenValue = null;
        if (authorizationCode.isEnableOfflineAccess()) {
            refreshTokenValue = storeRefreshTokenPort.store(RefreshTokenStore.of(
                generateRefreshTokenValuePort.generate(),
                authorizationCode.getAccessTokenDraftCode(),
                authorizationCode.getIdTokenDraftCode(),
                Date.from(current),
                Date.from(current.plus(30, ChronoUnit.DAYS))
            ));
        }

        deleteAuthorizationCodePort.deleteByValue(authorizationCode.getValue());
        return TokenOutput.of(
            accessTokenValue,
            "Bearer",
            3600L,
            refreshTokenValue,
            idToken,
            scopeNames,
            audienceNames
        );
    }

    private TokenOutput exchangeRefreshTokenForToken(final ExchangeTokenInput input) {
        return null;
    }
}