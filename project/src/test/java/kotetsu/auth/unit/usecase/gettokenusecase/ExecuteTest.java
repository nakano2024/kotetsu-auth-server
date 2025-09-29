package kotetsu.auth.unit.usecase.gettokenusecase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.entity.ExistingAuthorization;
import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.ExistingRefreshToken;
import kotetsu.auth.application.domain.entity.ExistingRefreshTokenCore;
import kotetsu.auth.application.domain.entity.IssuedAccessToken;
import kotetsu.auth.application.domain.entity.IssuedIdToken;
import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.entity.IssuedRefreshToken;
import kotetsu.auth.application.domain.entity.RequestedRelatedAudienceList;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.repository.IDeleteExistingAccessTokenPort;
import kotetsu.auth.application.domain.repository.IDeleteExistingAuthorization;
import kotetsu.auth.application.domain.repository.IDeleteExistingRefreshTokenPort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenByCoreKeyPort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingAuthorizationForUpdatePort;
import kotetsu.auth.application.domain.repository.IFetchExistingIdTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingRefreshTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingRefreshTokenForUpdatePort;
import kotetsu.auth.application.domain.repository.IStoreIssuedAccessTokenPort;
import kotetsu.auth.application.domain.repository.IStoreIssuedIdTokenMetaPort;
import kotetsu.auth.application.domain.repository.IStoreIssuedRefreshTokenPort;
import kotetsu.auth.application.domain.service.CheckCodeVerifilerService;
import kotetsu.auth.application.domain.service.CreateIdTokenMetaService;
import kotetsu.auth.application.domain.service.CreateIssuedAccessTokenService;
import kotetsu.auth.application.domain.service.CreateIssuedIdTokenService;
import kotetsu.auth.application.domain.service.CreateIssuedRefreshTokenService;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.util.IGenerateUuidPort;
import kotetsu.auth.application.domain.value.AccessTokenValue;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.GrantType;
import kotetsu.auth.application.domain.value.IdTokenAudience;
import kotetsu.auth.application.domain.value.IdTokenProfile;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.IdTokenValue;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.RefreshTokenValue;
import kotetsu.auth.application.domain.value.ScopeName;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.domain.value.UserName;
import kotetsu.auth.application.dto.input.GetTokenInput;
import kotetsu.auth.application.dto.output.TokenOutput;
import kotetsu.auth.application.exception.AuthorizationCodeExpiredException;
import kotetsu.auth.application.exception.AuthorizationCodeNotFoundException;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.InvalidGrantTypeException;
import kotetsu.auth.application.exception.RefreshTokenExpiredException;
import kotetsu.auth.application.exception.RefreshTokenNotFoundException;
import kotetsu.auth.application.usecase.GetTokenUsecase;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IGenerateUuidPort generateUuidPort;
    @Mock
    private IFetchExistingAuthorizationForUpdatePort fetchExistingAuthorizationPort;
    @Mock
    private IFetchCurrentDatePort fetchCurrentDatePort;
    @Mock
    private IDeleteExistingAuthorization deleteExistingAuthorization;
    @Mock
    private IFetchExistingAccessTokenCorePort fetchExistingAccessTokenCorePort;
    @Mock
    private CreateIssuedAccessTokenService createIssuedAccessTokenService;
    @Mock
    private IStoreIssuedAccessTokenPort storeIssuedAccessTokenPort;
    @Mock
    private CreateIssuedRefreshTokenService createIssuedRefreshTokenService;
    @Mock
    private IStoreIssuedRefreshTokenPort storeIssuedRefreshTokenPort;
    @Mock
    private IFetchExistingRefreshTokenCorePort fetchExistingRefreshTokenCorePort;
    @Mock
    private IStoreIssuedIdTokenMetaPort storeIdTokenMetaPort;
    @Mock
    private IFetchExistingIdTokenCorePort fetchExistingIdTokenCorePort;
    @Mock
    private CreateIdTokenMetaService createIdTokenMetaService;
    @Mock
    private CreateIssuedIdTokenService createIssuedIdTokenService;
    @Mock
    private CheckCodeVerifilerService checkCodeVerifilerService;
    @Mock
    private IFetchExistingRefreshTokenForUpdatePort fetchExistingRefreshTokenPort;
    @Mock
    private IDeleteExistingRefreshTokenPort deleteExistingRefreshTokenPort;
    @Mock
    private IFetchExistingAccessTokenByCoreKeyPort fetchExistingAccessTokenByCoreKeyPort;
    @Mock
    private IDeleteExistingAccessTokenPort deleteExistingAccessTokenPort;

    @InjectMocks
    private GetTokenUsecase getTokenUsecase;

    @Test
    public void canExchangeAuthorizationCodeWithOnlineAccess() {
        final Date currentDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );
        final Date expiredDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 10, 0).atZone(ZoneId.of("UTC")).toInstant()
        );

        when(fetchCurrentDatePort.fetch()).thenReturn(currentDate);

        when(fetchExistingAuthorizationPort.fetchForUpdate(any())).thenReturn(Optional.of(
            ExistingAuthorization.of(
                Key.of("auth-key-123"),
                AuthorizationCode.of(
                    AuthorizationCodeValue.of("auth-code-123"),
                    AuthorizationCodeChallenge.of("challenge-123"),
                    ExpiredAt.of(expiredDate)
                ),
                AccessType.of("online"),
                LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                LinkedIdTokenCoreKey.of("id-core-key-123"),
                LinkedRefreshTokenCoreKey.of("refresh-core-key-123"),
                GrantType.of("authorization_code")
            )
        ));

        when(checkCodeVerifilerService.isValid(any(), any())).thenReturn(true);

        when(fetchExistingAccessTokenCorePort.fetch(any())).thenReturn(Optional.of(
            ExistingAccessTokenCore.of(
                Key.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                Issuer.of("https://auth.example.com"),
                Subject.of("user-123"),
                RequestedScopeList.of(List.of(
                    Scope.of(Key.of("scope-key-1"), ScopeName.of("read")),
                    Scope.of(Key.of("scope-key-2"), ScopeName.of("write"))
                )),
                RequestedRelatedAudienceList.of(List.of("https://api.example.com")),
                ClientId.of("client-123")
            )
        ));

        final IssuedAccessToken mockAccessToken = IssuedAccessToken.of(
            AccessTokenValue.of("access-token-123"),
            LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate))
        );
        when(createIssuedAccessTokenService.create(any(), any())).thenReturn(mockAccessToken);

        assertDoesNotThrow(() -> {
            final TokenOutput result = getTokenUsecase.execute(
                GetTokenInput.of(
                    "authorization_code",
                    "auth-code-123",
                    "verifier-123",
                    null
                )
            );

            assertEquals("access-token-123", result.getAccessToken());
            assertEquals("Bearer", result.getTokenType());
            assertFalse(result.getRefreshToken().isPresent());
            assertFalse(result.getIdToken().isPresent());
            assertEquals("read write", result.getScopeToken());
        });
    }

    @Test
    public void canExchangeAuthorizationCodeWithOnlineAccessAndOpenid() {
        final Date currentDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );
        final Date expiredDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 10, 0).atZone(ZoneId.of("UTC")).toInstant()
        );

        when(fetchCurrentDatePort.fetch()).thenReturn(currentDate);
        when(generateUuidPort.generate()).thenReturn("unique-id-123");

        when(fetchExistingAuthorizationPort.fetchForUpdate(any())).thenReturn(Optional.of(
            ExistingAuthorization.of(
                Key.of("auth-key-123"),
                AuthorizationCode.of(
                    AuthorizationCodeValue.of("auth-code-123"),
                    AuthorizationCodeChallenge.of("challenge-123"),
                    ExpiredAt.of(expiredDate)
                ),
                AccessType.of("online"),
                LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                LinkedIdTokenCoreKey.of("id-core-key-123"),
                LinkedRefreshTokenCoreKey.of("refresh-core-key-123"),
                GrantType.of("authorization_code")
            )
        ));

        when(checkCodeVerifilerService.isValid(any(), any())).thenReturn(true);

        when(fetchExistingAccessTokenCorePort.fetch(any())).thenReturn(Optional.of(
            ExistingAccessTokenCore.of(
                Key.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                Issuer.of("https://auth.example.com"),
                Subject.of("user-123"),
                RequestedScopeList.of(List.of(
                    Scope.of(Key.of(Scope.KEY_OPENID), ScopeName.of(Scope.NAME_OPENID)),
                    Scope.of(Key.of("scope-key-2"), ScopeName.of("profile"))
                )),
                RequestedRelatedAudienceList.of(List.of("https://api.example.com")),
                ClientId.of("client-123")
            )
        ));

        when(fetchExistingIdTokenCorePort.fetch(any())).thenReturn(Optional.of(
            ExistingIdTokenCore.of(
                Key.of("id-core-key-123"),
                Issuer.of("https://auth.example.com"),
                IdTokenAudience.of("client-id"),
                Subject.of("user-123"),
                Nonce.of("nonce-123"),
                IdTokenProfile.of(
                    UserName.of("user123"),
                    Email.of("user@example.com"),
                    ImageUrl.of("https://example.com/avatar.jpg")
                )
            )
        ));

        final IssuedAccessToken mockAccessToken = IssuedAccessToken.of(
            AccessTokenValue.of("access-token-123"),
            LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate))
        );
        when(createIssuedAccessTokenService.create(any(), any())).thenReturn(mockAccessToken);

        final IssuedIdTokenMeta mockIdTokenMeta = IssuedIdTokenMeta.of(
            LinkedIdTokenCoreKey.of("id-core-key-123"),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate)),
            IdTokenUniqueId.of("unique-id-123")
        );
        when(createIdTokenMetaService.create(any(), any(), any())).thenReturn(mockIdTokenMeta);

        final IssuedIdToken mockIdToken = IssuedIdToken.of(
            IdTokenValue.of("id-token-123")
        );
        when(createIssuedIdTokenService.create(any(), any())).thenReturn(mockIdToken);

        assertDoesNotThrow(() -> {
            final TokenOutput result = getTokenUsecase.execute(
                GetTokenInput.of(
                    "authorization_code",
                    "auth-code-123",
                    "verifier-123",
                    null
                )
            );

            assertEquals("access-token-123", result.getAccessToken());
            assertEquals("Bearer", result.getTokenType());
            assertFalse(result.getRefreshToken().isPresent());
            assertTrue(result.getIdToken().isPresent());
            assertEquals("id-token-123", result.getIdToken().get());
            assertEquals("openid profile", result.getScopeToken());

            verify(deleteExistingAuthorization).delete(any());
            verify(storeIssuedAccessTokenPort).store(any());
            verify(storeIdTokenMetaPort).store(any());
        });
    }

    @Test
    public void canExchangeAuthorizationCodeWithOfflineAccessAndOpenid() {
        final Date currentDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );
        final Date expiredDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 10, 0).atZone(ZoneId.of("UTC")).toInstant()
        );

        when(fetchCurrentDatePort.fetch()).thenReturn(currentDate);
        when(generateUuidPort.generate()).thenReturn("unique-id-123");

        when(fetchExistingAuthorizationPort.fetchForUpdate(any())).thenReturn(Optional.of(
            ExistingAuthorization.of(
                Key.of("auth-key-123"),
                AuthorizationCode.of(
                    AuthorizationCodeValue.of("auth-code-123"),
                    AuthorizationCodeChallenge.of("challenge-123"),
                    ExpiredAt.of(expiredDate)
                ),
                AccessType.of("offline"),
                LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                LinkedIdTokenCoreKey.of("id-core-key-123"),
                LinkedRefreshTokenCoreKey.of("refresh-core-key-123"),
                GrantType.of("authorization_code")
            )
        ));

        when(checkCodeVerifilerService.isValid(any(), any())).thenReturn(true);

        when(fetchExistingAccessTokenCorePort.fetch(any())).thenReturn(Optional.of(
            ExistingAccessTokenCore.of(
                Key.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                Issuer.of("https://auth.example.com"),
                Subject.of("user-123"),
                RequestedScopeList.of(List.of(
                    Scope.of(Key.of(Scope.KEY_OPENID), ScopeName.of(Scope.NAME_OPENID)),
                    Scope.of(Key.of("scope-key-2"), ScopeName.of("profile"))
                )),
                RequestedRelatedAudienceList.of(List.of("https://api.example.com")),
                ClientId.of("client-123")
            )
        ));

        when(fetchExistingIdTokenCorePort.fetch(any())).thenReturn(Optional.of(
            ExistingIdTokenCore.of(
                Key.of("id-core-key-123"),
                Issuer.of("https://auth.example.com"),
                IdTokenAudience.of("client-id"),
                Subject.of("user-123"),
                Nonce.of("nonce-123"),
                IdTokenProfile.of(
                    UserName.of("user123"),
                    Email.of("user@example.com"),
                    ImageUrl.of("https://example.com/avatar.jpg")
                )
            )
        ));

        when(fetchExistingRefreshTokenCorePort.fetch(any())).thenReturn(Optional.of(
            ExistingRefreshTokenCore.of(
                Key.of("refresh-core-key-123"),
                LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                LinkedIdTokenCoreKey.of("id-core-key-123")
            )
        ));

        final IssuedAccessToken mockAccessToken = IssuedAccessToken.of(
            AccessTokenValue.of("access-token-123"),
            LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate))
        );
        when(createIssuedAccessTokenService.create(any(), any())).thenReturn(mockAccessToken);

        final IssuedIdTokenMeta mockIdTokenMeta = IssuedIdTokenMeta.of(
            LinkedIdTokenCoreKey.of("id-core-key-123"),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate)),
            IdTokenUniqueId.of("unique-id-123")
        );
        when(createIdTokenMetaService.create(any(), any(), any())).thenReturn(mockIdTokenMeta);

        final IssuedIdToken mockIdToken = IssuedIdToken.of(
            IdTokenValue.of("id-token-123")
        );
        when(createIssuedIdTokenService.create(any(), any())).thenReturn(mockIdToken);

        final IssuedRefreshToken mockRefreshToken = IssuedRefreshToken.of(
            RefreshTokenValue.of("refresh-token-123"),
            LinkedRefreshTokenCoreKey.of("refresh-core-key-123"),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate))
        );
        when(createIssuedRefreshTokenService.create(any(), any())).thenReturn(mockRefreshToken);

        assertDoesNotThrow(() -> {
            final TokenOutput result = getTokenUsecase.execute(
                GetTokenInput.of(
                    "authorization_code",
                    "auth-code-123",
                    "verifier-123",
                    null
                )
            );

            assertEquals("access-token-123", result.getAccessToken());
            assertEquals("Bearer", result.getTokenType());
            assertTrue(result.getRefreshToken().isPresent());
            assertEquals("refresh-token-123", result.getRefreshToken().get());
            assertTrue(result.getIdToken().isPresent());
            assertEquals("id-token-123", result.getIdToken().get());
            assertEquals("openid profile", result.getScopeToken());

            verify(deleteExistingAuthorization).delete(any());
            verify(storeIssuedAccessTokenPort).store(any());
            verify(storeIdTokenMetaPort).store(any());
            verify(storeIssuedRefreshTokenPort).store(any());
        });
    }

    @Test
    public void canExchangeRefreshToken() {
        final Date currentDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );
        final Date expiredDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 10, 0).atZone(ZoneId.of("UTC")).toInstant()
        );

        when(fetchCurrentDatePort.fetch()).thenReturn(currentDate);

        when(fetchExistingRefreshTokenPort.fetchForUpdate(any())).thenReturn(Optional.of(
            ExistingRefreshToken.of(
                Key.of("refresh-key-123"),
                LinkedRefreshTokenCoreKey.of("refresh-core-key-123"),
                Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate)),
                GrantType.of("refresh_token")
            )
        ));

        when(fetchExistingRefreshTokenCorePort.fetch(any())).thenReturn(Optional.of(
            ExistingRefreshTokenCore.of(
                Key.of("refresh-core-key-123"),
                LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                LinkedIdTokenCoreKey.of("id-core-key-123")
            )
        ));

        when(fetchExistingAccessTokenCorePort.fetch(any())).thenReturn(Optional.of(
            ExistingAccessTokenCore.of(
                Key.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                Issuer.of("https://auth.example.com"),
                Subject.of("user-123"),
                RequestedScopeList.of(List.of(
                    Scope.of(Key.of("scope-key-1"), ScopeName.of("read")),
                    Scope.of(Key.of("scope-key-2"), ScopeName.of("write"))
                )),
                RequestedRelatedAudienceList.of(List.of("https://api.example.com")),
                ClientId.of("client-123")
            )
        ));

        when(fetchExistingAccessTokenByCoreKeyPort.fetchForUpdateByCoreKey(any())).thenReturn(Optional.of(
            ExistingAccessToken.of(
                Key.of("access-token-key-123"),
                LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate))
            )
        ));

        final IssuedRefreshToken mockNewRefreshToken = IssuedRefreshToken.of(
            RefreshTokenValue.of("new-refresh-token-123"),
            LinkedRefreshTokenCoreKey.of("refresh-core-key-123"),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate))
        );
        when(createIssuedRefreshTokenService.create(any(), any())).thenReturn(mockNewRefreshToken);

        final IssuedAccessToken mockAccessToken = IssuedAccessToken.of(
            AccessTokenValue.of("new-access-token-123"),
            LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate))
        );
        when(createIssuedAccessTokenService.create(any(), any())).thenReturn(mockAccessToken);

        assertDoesNotThrow(() -> {
            final TokenOutput result = getTokenUsecase.execute(
                GetTokenInput.of(
                    "refresh_token",
                    null,
                    null,
                    "refresh-token-123"
                )
            );

            assertEquals("new-access-token-123", result.getAccessToken());
            assertEquals("Bearer", result.getTokenType());
            assertTrue(result.getRefreshToken().isPresent());
            assertEquals("new-refresh-token-123", result.getRefreshToken().get());
            assertFalse(result.getIdToken().isPresent());
            assertEquals("read write", result.getScopeToken());

            verify(deleteExistingRefreshTokenPort).delete(any());
            verify(deleteExistingAccessTokenPort).delete(any());
            verify(storeIssuedRefreshTokenPort).store(any());
            verify(storeIssuedAccessTokenPort).store(any());
        });
    }

    @Test
    public void throwExceptionIfInputIsNull() {
        InputNullRuntimeException exception = assertThrows(InputNullRuntimeException.class, () -> {
            getTokenUsecase.execute(null);
        });

        assertEquals("inputはnullが許容されていません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfGrantTypeIsInvalid() {
        InvalidGrantTypeException exception = assertThrows(InvalidGrantTypeException.class, () -> {
            getTokenUsecase.execute(
                GetTokenInput.of(
                    "invalid_grant",
                    null,
                    null,
                    null
                )
            );
        });

        assertEquals("無効なgrantTypeです。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfAuthorizationCodeNotFound() {
        when(fetchExistingAuthorizationPort.fetchForUpdate(any())).thenReturn(Optional.empty());

        AuthorizationCodeNotFoundException exception = assertThrows(AuthorizationCodeNotFoundException.class, () -> {
            getTokenUsecase.execute(
                GetTokenInput.of(
                    "authorization_code",
                    "nonexistent-code",
                    "verifier-123",
                    null
                )
            );
        });

        assertEquals("認可コードが見つかりません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfAuthorizationCodeExpired() {
        final Date currentDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );
        final Date expiredDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 11, 59, 59).atZone(ZoneId.of("UTC")).toInstant()
        );

        when(fetchCurrentDatePort.fetch()).thenReturn(currentDate);

        when(fetchExistingAuthorizationPort.fetchForUpdate(any())).thenReturn(Optional.of(
            ExistingAuthorization.of(
                Key.of("auth-key-123"),
                AuthorizationCode.of(
                    AuthorizationCodeValue.of("expired-code"),
                    AuthorizationCodeChallenge.of("challenge-123"),
                    ExpiredAt.of(expiredDate)
                ),
                AccessType.of("online"),
                LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
                LinkedIdTokenCoreKey.of("id-core-key-123"),
                LinkedRefreshTokenCoreKey.of("refresh-core-key-123"),
                GrantType.of("authorization_code")
            )
        ));

        AuthorizationCodeExpiredException exception = assertThrows(AuthorizationCodeExpiredException.class, () -> {
            getTokenUsecase.execute(
                GetTokenInput.of(
                    "authorization_code",
                    "expired-code",
                    "verifier-123",
                    null
                )
            );
        });

        assertEquals("認可コードの期限が切れています。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfRefreshTokenNotFound() {
        when(fetchExistingRefreshTokenPort.fetchForUpdate(any())).thenReturn(Optional.empty());

        RefreshTokenNotFoundException exception = assertThrows(RefreshTokenNotFoundException.class, () -> {
            getTokenUsecase.execute(
                GetTokenInput.of(
                    "refresh_token",
                    null,
                    null,
                    "nonexistent-refresh-token"
                )
            );
        });

        assertEquals("RefreshTokenが見つかりません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfRefreshTokenExpired() {
        final Date currentDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );
        final Date expiredDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 11, 59, 59).atZone(ZoneId.of("UTC")).toInstant()
        );

        when(fetchCurrentDatePort.fetch()).thenReturn(currentDate);

        when(fetchExistingRefreshTokenPort.fetchForUpdate(any())).thenReturn(Optional.of(
            ExistingRefreshToken.of(
                Key.of("refresh-key-123"),
                LinkedRefreshTokenCoreKey.of("refresh-core-key-123"),
                Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate)),
                GrantType.of("refresh_token")
            )
        ));

        RefreshTokenExpiredException exception = assertThrows(RefreshTokenExpiredException.class, () -> {
            getTokenUsecase.execute(
                GetTokenInput.of(
                    "refresh_token",
                    null,
                    null,
                    "expired-refresh-token"
                )
            );
        });

        assertEquals("RefreshTokenの期限が切れています。", exception.getMessage());
    }
}