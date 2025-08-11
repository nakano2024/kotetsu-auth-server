package kotetsu.auth.application.usecase;

import java.util.Date;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.entity.ExistingAuthorization;
import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.ExistingIdTokenMeta;
import kotetsu.auth.application.domain.entity.ExistingRefreshToken;
import kotetsu.auth.application.domain.entity.ExistingRefreshTokenCore;
import kotetsu.auth.application.domain.entity.IdTokenMeta;
import kotetsu.auth.application.domain.entity.IssuedAccessToken;
import kotetsu.auth.application.domain.entity.IssuedIdToken;
import kotetsu.auth.application.domain.entity.IssuedRefreshToken;
import kotetsu.auth.application.exception.ExistingAccessTokenCoreNullRuntimeException;
import kotetsu.auth.application.exception.ExistingAccessTokenNullRuntimeException;
import kotetsu.auth.application.exception.ExistingIdTokenCoreNullRuntimeException;
import kotetsu.auth.application.exception.ExistingIdTokenMetaNullRuntimeException;
import kotetsu.auth.application.exception.ExistingRefreshTokenCoreNullRuntimeException;
import kotetsu.auth.application.exception.InputAuthorizationCodeNullException;
import kotetsu.auth.application.domain.repository.IDeleteExistingAccessTokenPort;
import kotetsu.auth.application.domain.repository.IDeleteExistingAuthorization;
import kotetsu.auth.application.domain.repository.IDeleteExistingIdTokenMetaPort;
import kotetsu.auth.application.domain.repository.IDeleteExistingRefreshTokenPort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenByCoreKeyPort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingAuthorizationPort;
import kotetsu.auth.application.domain.repository.IFetchExistingIdTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingIdTokenMetaPort;
import kotetsu.auth.application.domain.repository.IFetchExistingRefreshTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingRefreshTokenPort;
import kotetsu.auth.application.domain.repository.IStoreIdTokenMetaPort;
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
import kotetsu.auth.application.domain.value.GrantType;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.RefreshTokenValue;
import kotetsu.auth.application.dto.input.GetTokenInput;
import kotetsu.auth.application.dto.output.TokenOutput;
import kotetsu.auth.application.exception.AuthorizationCodeExpiredException;
import kotetsu.auth.application.exception.AuthorizationCodeNotFoundException;
import kotetsu.auth.application.exception.InputCodeVerifierNullException;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.InputRefreshTokenNullException;
import kotetsu.auth.application.exception.InvalidCodeVerifierException;
import kotetsu.auth.application.exception.InvalidGrantTypeException;
import kotetsu.auth.application.exception.RefreshTokenExpiredException;
import kotetsu.auth.application.exception.RefreshTokenNotFoundException;
import kotetsu.auth.application.exception.TokenGrantTypeDoseNotMatchException;

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
    private final IStoreIdTokenMetaPort storeIdTokenMetaPort;
    private final CreateIdTokenMetaService createIdTokenMetaService;
    private final CreateIssuedIdTokenService createIssuedIdTokenService;
    private final CheckCodeVerifilerService checkCodeVerifilerService;
    private final IFetchExistingRefreshTokenPort fetchExistingRefreshTokenPort;
    private final IDeleteExistingRefreshTokenPort deleteExistingRefreshTokenPort;
    private final IFetchExistingAccessTokenByCoreKeyPort fetchExistingAccessTokenByCoreKeyPort;
    private final IDeleteExistingAccessTokenPort deleteExistingAccessTokenPort;
    private final IFetchExistingIdTokenMetaPort fetchExistingIdTokenMetaPort;
    private final IDeleteExistingIdTokenMetaPort deleteExistingIdTokenMetaPort;

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
        final IStoreIdTokenMetaPort storeIdTokenMetaPort,
        final IFetchExistingIdTokenCorePort fetchExistingIdTokenCorePort,
        final CreateIdTokenMetaService createIdTokenMetaService,
        final CreateIssuedIdTokenService createIssuedIdTokenService,
        final CheckCodeVerifilerService checkCodeVerifilerService,
        final IFetchExistingRefreshTokenPort fetchExistingRefreshTokenPort,
        final IDeleteExistingRefreshTokenPort deleteExistingRefreshTokenPort,
        final IFetchExistingAccessTokenByCoreKeyPort fetchExistingAccessTokenByCoreKeyPort,
        final IDeleteExistingAccessTokenPort deleteExistingAccessTokenPort,
        final IFetchExistingIdTokenMetaPort fetchExistingIdTokenMetaPort,
        final IDeleteExistingIdTokenMetaPort deleteExistingIdTokenMetaPort
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
        this.fetchExistingRefreshTokenPort = fetchExistingRefreshTokenPort;
        this.deleteExistingRefreshTokenPort = deleteExistingRefreshTokenPort;
        this.storeIdTokenMetaPort = storeIdTokenMetaPort;
        this.fetchExistingAccessTokenByCoreKeyPort = fetchExistingAccessTokenByCoreKeyPort;
        this.deleteExistingAccessTokenPort = deleteExistingAccessTokenPort;
        this.fetchExistingIdTokenMetaPort = fetchExistingIdTokenMetaPort;
        this.deleteExistingIdTokenMetaPort = deleteExistingIdTokenMetaPort;
    }

    @Transactional
    public TokenOutput execute(final GetTokenInput input) 
        throws AuthorizationCodeNotFoundException,
            AuthorizationCodeExpiredException,
            InvalidGrantTypeException,
            InvalidCodeVerifierException,
            InputAuthorizationCodeNullException,
            InputCodeVerifierNullException,
            InputRefreshTokenNullException,
            RefreshTokenNotFoundException,
            TokenGrantTypeDoseNotMatchException,
            RefreshTokenExpiredException
    {
        if (input == null) {
            throw new InputNullRuntimeException();
        }

        if (input.getGrantType().equals(GrantType.GRANT_TYPE_AUTORIZATION_CODE)) {
            return exchangeWithCode(input);
        }

        if (input.getGrantType().equals(GrantType.GRANT_TYPE_REFRESH_TOKEN)) {
            return exchangeWithRefresh(input);
        }

        throw new InvalidGrantTypeException();
    }

    private TokenOutput exchangeWithCode(final GetTokenInput input)
        throws InputAuthorizationCodeNullException,
            AuthorizationCodeNotFoundException,
            AuthorizationCodeExpiredException,
            InvalidCodeVerifierException,
            InputCodeVerifierNullException,
            TokenGrantTypeDoseNotMatchException
    {
        final String inputCode = input.getCode()
            .orElseThrow(() -> new InputAuthorizationCodeNullException());

        final String inputCodeVerifier = input.getCodeVerifier()
            .orElseThrow(() -> new InputCodeVerifierNullException());

        final Date currentDate = fetchCurrentDatePort.fetch();

        final ExistingAuthorization authorization = fetchExistingAuthorizationPort.fetch(
            AuthorizationCodeValue.of(inputCode)
        ).orElseThrow(() -> new AuthorizationCodeNotFoundException());

        if (!authorization.getGrantType().isAuthorizationCode()) {
            throw new TokenGrantTypeDoseNotMatchException();
        }

        if (authorization.getAuthorizationCode().getExpiredAt().hasExpired(currentDate)) {
            throw new AuthorizationCodeExpiredException();
        }

        if (!checkCodeVerifilerService.isValid(AuthorizationCodeVerifier.of(inputCodeVerifier), authorization.getAuthorizationCode().getChallenge())) {
            throw new InvalidCodeVerifierException();
        }

        deleteExistingAuthorization.delete(authorization);
        
        final ExistingAccessTokenCore accessTokenCore = fetchExistingAccessTokenCorePort.fetch(
            Key.of(authorization.getLinkedAccessTokenCoreKey().getValue())
        ).orElseThrow(() -> new ExistingAccessTokenCoreNullRuntimeException());

        final IssuedAccessToken issuedAccessToken = createIssuedAccessTokenService.create(
            LinkedAccessTokenCoreKey.of(accessTokenCore.getKey().getValue()), 
            IssuedAt.of(currentDate)
        );

        storeIssuedAccessTokenPort.store(issuedAccessToken);

        final ExistingIdTokenCore idTokenCore = fetchExistingIdTokenCorePort.fetch(
            Key.of(authorization.getLinkedIdTokenCoreKey().getValue())
        ).orElseThrow(() -> new ExistingIdTokenCoreNullRuntimeException());

        IssuedIdToken idToken = null;
        if (accessTokenCore.getScopeList().hasOpenid()) {
            final IdTokenMeta idTokenMeta = createIdTokenMetaService.create(
                LinkedIdTokenCoreKey.of(idTokenCore.getKey().getValue()),
                IdTokenUniqueId.of(generateUuidPort.generate()),
                IssuedAt.of(currentDate)
            );

            storeIdTokenMetaPort.store(idTokenMeta);
            idToken = createIssuedIdTokenService.create(idTokenMeta);
        }

        IssuedRefreshToken refreshToken = null;
        if (authorization.getAccessType().isOffline()) {
            final ExistingRefreshTokenCore refreshTokenCore = fetchExistingRefreshTokenCorePort.fetch(
                Key.of(authorization.getLinkedRefreshTokenCoreKey().getValue())
            ).orElseThrow(() -> new ExistingRefreshTokenCoreNullRuntimeException());

            refreshToken = createIssuedRefreshTokenService.create(
                LinkedRefreshTokenCoreKey.of(refreshTokenCore.getKey().getValue()),
                IssuedAt.of(currentDate)
            );

            storeIssuedRefreshTokenPort.store(refreshToken);
        }

        return TokenOutput.of(
            issuedAccessToken.getValue().getValue(),
            IssuedAccessToken.TOKEN_TYPE,
            issuedAccessToken.getDuration().getIssuedAt().getUnixSec(),
            issuedAccessToken.getDuration().getDifferenceSec(),
            (refreshToken != null) ? refreshToken.getValue().getValue() : null ,
            (idToken != null) ? idToken.getValue().getValue() : null,
            accessTokenCore.getScopeList().toScopeListToken(),
            accessTokenCore.getRelatedAudienceList().toStringList()
        );
    }

    private TokenOutput exchangeWithRefresh(final GetTokenInput input)
        throws InputRefreshTokenNullException,
            RefreshTokenNotFoundException,
            TokenGrantTypeDoseNotMatchException,
            RefreshTokenExpiredException
    {
        final String inputRefreshToken = input.getRefreshToken()
            .orElseThrow(() -> new InputRefreshTokenNullException());

        final Date currentDate = fetchCurrentDatePort.fetch();

        final ExistingRefreshToken existingRefreshToken = fetchExistingRefreshTokenPort.fetch(RefreshTokenValue.of(inputRefreshToken))
            .orElseThrow(() -> new RefreshTokenNotFoundException());

        if (!existingRefreshToken.getGrantType().isRefreshToken()) {
            throw new TokenGrantTypeDoseNotMatchException();
        }

        if (existingRefreshToken.getDuration().getExpiredAt().hasExpired(currentDate)) {
            throw new RefreshTokenExpiredException();
        }

        final ExistingRefreshTokenCore refreshTokenCore = fetchExistingRefreshTokenCorePort.fetch(
            Key.of(existingRefreshToken.getLinkedRefreshTokenCoreKey().getValue())
        ).orElseThrow(() -> new ExistingRefreshTokenCoreNullRuntimeException());

        final IssuedRefreshToken newRefreshToken = createIssuedRefreshTokenService.create(
            LinkedRefreshTokenCoreKey.of(refreshTokenCore.getKey().getValue()),
            IssuedAt.of(currentDate)
        );

        deleteExistingRefreshTokenPort.delete(existingRefreshToken);
        storeIssuedRefreshTokenPort.store(newRefreshToken);

        final ExistingAccessTokenCore accessTokenCore = fetchExistingAccessTokenCorePort.fetch(
            Key.of(refreshTokenCore.getLinkedAccessTokenCoreKey().getValue())
        ).orElseThrow(() -> new ExistingAccessTokenCoreNullRuntimeException());

        final ExistingAccessToken existingAccessToken = fetchExistingAccessTokenByCoreKeyPort.fetchByCoreKey(LinkedAccessTokenCoreKey.of(accessTokenCore.getKey().getValue()))
            .orElseThrow(() -> new ExistingAccessTokenNullRuntimeException());

        final IssuedAccessToken issuedAccessToken = createIssuedAccessTokenService.create(
            LinkedAccessTokenCoreKey.of(accessTokenCore.getKey().getValue()), 
            IssuedAt.of(currentDate)
        );

        deleteExistingAccessTokenPort.delete(existingAccessToken);
        storeIssuedAccessTokenPort.store(issuedAccessToken);

        IssuedIdToken idToken = null;

        if (accessTokenCore.getScopeList().hasOpenid()) {
            final ExistingIdTokenCore idTokenCore = fetchExistingIdTokenCorePort.fetch(
                Key.of(refreshTokenCore.getLinkedIdTokenCoreKey().getValue())
            ).orElseThrow(() -> new ExistingIdTokenCoreNullRuntimeException());

            final ExistingIdTokenMeta existingIdTokenMeta = fetchExistingIdTokenMetaPort.fetch(LinkedIdTokenCoreKey.of(idTokenCore.getKey().getValue()))
                .orElseThrow(() -> new ExistingIdTokenMetaNullRuntimeException());

            final IdTokenMeta idTokenMeta = createIdTokenMetaService.create(
                LinkedIdTokenCoreKey.of(idTokenCore.getKey().getValue()),
                IdTokenUniqueId.of(generateUuidPort.generate()),
                IssuedAt.of(currentDate)
            );
            
            deleteExistingIdTokenMetaPort.delete(existingIdTokenMeta);
            storeIdTokenMetaPort.store(idTokenMeta);
            idToken = createIssuedIdTokenService.create(idTokenMeta);
        }

        return TokenOutput.of(
            issuedAccessToken.getValue().getValue(),
            IssuedAccessToken.TOKEN_TYPE,
            issuedAccessToken.getDuration().getIssuedAt().getUnixSec(),
            issuedAccessToken.getDuration().getDifferenceSec(),
            newRefreshToken.getValue().getValue(),
            (idToken != null) ? idToken.getValue().getValue() : null,
            accessTokenCore.getScopeList().toScopeListToken(),
            accessTokenCore.getRelatedAudienceList().toStringList()
        );
    }
}
