package kotetsu.auth.application.usecase;

import java.util.Date;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.constant.GrantTypeConstant;
import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.entity.ExistingAuthorization;
import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.ExistingRefreshTokenCore;
import kotetsu.auth.application.domain.entity.IdTokenMeta;
import kotetsu.auth.application.domain.entity.IssuedAccessToken;
import kotetsu.auth.application.domain.entity.IssuedIdToken;
import kotetsu.auth.application.domain.entity.IssuedRefreshToken;
import kotetsu.auth.application.exception.ExistingAccessTokenCoreNullRuntimeException;
import kotetsu.auth.application.exception.ExistingIdTokenCoreNullRuntimeException;
import kotetsu.auth.application.exception.ExistingRefreshTokenCoreNullRuntimeException;
import kotetsu.auth.application.domain.repository.IDeleteExistingAuthorization;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingAuthorizationPort;
import kotetsu.auth.application.domain.repository.IFetchExistingIdTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingRefreshTokenCorePort;
import kotetsu.auth.application.domain.repository.IStoreIssuedAccessTokenPort;
import kotetsu.auth.application.domain.repository.IStoreIssuedRefreshTokenPort;
import kotetsu.auth.application.domain.service.CheckCodeVerifilerService;
import kotetsu.auth.application.domain.service.CreateIdTokenMetaService;
import kotetsu.auth.application.domain.service.CreateIssuedAccessTokenService;
import kotetsu.auth.application.domain.service.CreateIssuedIdTokenService;
import kotetsu.auth.application.domain.service.CreateIssuedRefreshTokenService;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.util.IGenerateUuidPort;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.AuthorizationCodeVerifier;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.dto.input.ExchangeTokenInput;
import kotetsu.auth.application.dto.output.TokenOutput;
import kotetsu.auth.application.exception.AuthorizationCodeExpiredException;
import kotetsu.auth.application.exception.AuthorizationCodeNotFoundException;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.InvalidCodeVerifierException;
import kotetsu.auth.application.exception.InvalidGrantTypeException;

public class GetTokenUsecase {
    private final IGenerateUuidPort generateUuidPort;
    private final IFetchExistingAuthorizationPort fetchExistingAuthorizationPort;
    private final IFetchCurrentDatePort fetchCurrentDatePort;
    private final IDeleteExistingAuthorization deleteExistingAuthorization;
    private final IFetchExistingAccessTokenCorePort fetchExistingAccessTokenCorePort;
    private final CreateIssuedAccessTokenService createIssuedAccessTokenService;
    private final IStoreIssuedAccessTokenPort storeIssuedAccessTokenPort;
    private final CreateIssuedRefreshTokenService createIssuedRefreshTokenService;
    private final IStoreIssuedRefreshTokenPort storeIssuedRefreshTokenPort;
    private final IFetchExistingRefreshTokenCorePort fetchExistingRefreshTokenCorePort;
    private final IFetchExistingIdTokenCorePort fetchExistingIdTokenCorePort;
    private final CreateIdTokenMetaService createIdTokenMetaService;
    private final CreateIssuedIdTokenService createIssuedIdTokenService;
    private final CheckCodeVerifilerService checkCodeVerifilerService;

    public GetTokenUsecase(
        final IGenerateUuidPort generateUuidPort,
        final IFetchExistingAuthorizationPort fetchExistingAuthorizationPort,
        final IFetchCurrentDatePort fetchCurrentDatePort,
        final IDeleteExistingAuthorization deleteExistingAuthorization,
        final IFetchExistingAccessTokenCorePort fetchExistingAccessTokenCorePort,
        final CreateIssuedAccessTokenService createIssuedAccessTokenService,
        final IStoreIssuedAccessTokenPort storeIssuedAccessTokenPort,
        final CreateIssuedRefreshTokenService createIssuedRefreshTokenService,
        final IStoreIssuedRefreshTokenPort storeIssuedRefreshTokenPort,
        final IFetchExistingRefreshTokenCorePort fetchExistingRefreshTokenCorePort,
        final IFetchExistingIdTokenCorePort fetchExistingIdTokenCorePort,
        final CreateIdTokenMetaService createIdTokenMetaService,
        final CreateIssuedIdTokenService createIssuedIdTokenService,
        final CheckCodeVerifilerService checkCodeVerifilerService
    ) {
        this.generateUuidPort = generateUuidPort;
        this.fetchExistingAuthorizationPort = fetchExistingAuthorizationPort;
        this.fetchCurrentDatePort = fetchCurrentDatePort;
        this.deleteExistingAuthorization = deleteExistingAuthorization;
        this.fetchExistingAccessTokenCorePort = fetchExistingAccessTokenCorePort;
        this.createIssuedAccessTokenService = createIssuedAccessTokenService;
        this.storeIssuedAccessTokenPort = storeIssuedAccessTokenPort;
        this.createIssuedRefreshTokenService = createIssuedRefreshTokenService;
        this.storeIssuedRefreshTokenPort = storeIssuedRefreshTokenPort;
        this.fetchExistingRefreshTokenCorePort = fetchExistingRefreshTokenCorePort;
        this.fetchExistingIdTokenCorePort = fetchExistingIdTokenCorePort;
        this.createIdTokenMetaService = createIdTokenMetaService;
        this.createIssuedIdTokenService = createIssuedIdTokenService;
        this.checkCodeVerifilerService = checkCodeVerifilerService;
    }

    @Transactional
    public TokenOutput execute(final ExchangeTokenInput input) 
        throws AuthorizationCodeNotFoundException,
            AuthorizationCodeExpiredException,
            InvalidGrantTypeException,
            InvalidCodeVerifierException
    {
        if (input == null) {
            throw new InputNullRuntimeException();
        }

        if (input.getGrantType().equals(GrantTypeConstant.AUTORIZATION_CODE)) {
            return exchangeWithCode(input);
        }

        if (input.getGrantType().equals(GrantTypeConstant.REFRESH_TOKEN)) {
            return exchangeWithRefresh(input);
        }

        throw new InvalidGrantTypeException();
    }

    private TokenOutput exchangeWithCode(final ExchangeTokenInput input)
        throws AuthorizationCodeNotFoundException,
            AuthorizationCodeExpiredException,
            InvalidCodeVerifierException
    {
        final Date currentDate = fetchCurrentDatePort.fetch();

        final ExistingAuthorization authorization = fetchExistingAuthorizationPort.fetch(
            AuthorizationCodeValue.of(input.getCode())
        ).orElseThrow(() -> new AuthorizationCodeNotFoundException());

        if (authorization.getAuthorizationCode().getExpiredAt().hasExpired(currentDate)) {
            throw new AuthorizationCodeExpiredException();
        }

        if (!checkCodeVerifilerService.isValid(AuthorizationCodeVerifier.of(input.getCodeVerifier()), authorization.getAuthorizationCode())) {
            throw new InvalidCodeVerifierException();
        }

        final ExistingAccessTokenCore accessTokenCore = fetchExistingAccessTokenCorePort.fetch(
            Key.of(authorization.getLinkedAccessTokenCoreKey().getValue())
        ).orElseThrow(() -> new ExistingAccessTokenCoreNullRuntimeException());

        final IssuedAccessToken issuedAccessToken = createIssuedAccessTokenService.create(
            LinkedAccessTokenCoreKey.of(accessTokenCore.getKey().getValue()), 
            IssuedAt.of(currentDate)
        );

        storeIssuedAccessTokenPort.store(issuedAccessToken);

        IssuedIdToken idToken = null;
        final ExistingIdTokenCore idTokenCore = fetchExistingIdTokenCorePort.fetch(
            Key.of(authorization.getLinkedIdTokenCoreKey().getValue())
        ).orElseThrow(() -> new ExistingIdTokenCoreNullRuntimeException());

        if (accessTokenCore.getScopeList().hasOpenid()) {
            final IdTokenMeta idTokenMeta = createIdTokenMetaService.create(IdTokenUniqueId.of(generateUuidPort.generate()), IssuedAt.of(currentDate));
            idToken = createIssuedIdTokenService.create(idTokenMeta, idTokenCore);
        }

        IssuedRefreshToken refreshToken = null;
        final ExistingRefreshTokenCore refreshTokenCore = fetchExistingRefreshTokenCorePort.fetch(
            Key.of(authorization.getLinkedRefreshTokenCoreKey().getValue())
        ).orElseThrow(() -> new ExistingRefreshTokenCoreNullRuntimeException());

        if (authorization.getAccessType().isOffline()) {
            refreshToken = createIssuedRefreshTokenService.create(
                LinkedRefreshTokenCoreKey.of(refreshTokenCore.getKey().getValue()),
                IssuedAt.of(currentDate)
            );
            storeIssuedRefreshTokenPort.store(refreshToken);
        }
        
        deleteExistingAuthorization.delete(authorization);

        return TokenOutput.of(
            issuedAccessToken.getValue().getValue(),
            IssuedAccessToken.TOKEN_TYPE,
            issuedAccessToken.getDuration().getDifferenceSec(),
            Optional.of(refreshToken).orElse(null).getValue().getValue(),
            Optional.of(idToken).orElse(null).getValue().getValue(),
            accessTokenCore.getScopeList().toScopeListToken(),
            accessTokenCore.getRelatedAudienceList().toStringList()
        );
    }

    private TokenOutput exchangeWithRefresh(final ExchangeTokenInput input) {
        return TokenOutput.of(null, null, null, null, null, null, null);
    }
}