package kotetsu.auth.unit.exchangetokenusecase;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.dto.data.AccessTokenDraftData;
import kotetsu.auth.application.dto.data.AuthorizationCodeData;
import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.dto.data.IdTokenDraftData;
import kotetsu.auth.application.dto.data.ScopeData;
import kotetsu.auth.application.dto.input.ExchangeTokenInput;
import kotetsu.auth.application.dto.output.TokenOutput;
import kotetsu.auth.application.dto.store.AccessTokenStore;
import kotetsu.auth.application.dto.store.RefreshTokenStore;
import kotetsu.auth.application.exception.AuthorizationCodeExpiredIOException;
import kotetsu.auth.application.exception.AuthorizationCodeNotFoundIOException;
import kotetsu.auth.application.exception.ClientCheckIOException;
import kotetsu.auth.application.exception.ClientNotFoundIOException;
import kotetsu.auth.application.persistence.IDeleteAuthorizationCodePort;
import kotetsu.auth.application.persistence.IFindAccessTokenDraftByIdPort;
import kotetsu.auth.application.persistence.IFindAuthorizationCodeByCodePort;
import kotetsu.auth.application.persistence.IFindClientInformationByIdPort;
import kotetsu.auth.application.persistence.IFindIdTokenDraftByCodePort;
import kotetsu.auth.application.persistence.IStoreAccessTokenPort;
import kotetsu.auth.application.persistence.IStoreRefreshTokenPort;
import kotetsu.auth.application.usecase.ExchangeTokenUsecase;
import kotetsu.auth.application.util.IGenerateIdTokenFromDraftPort;
import kotetsu.auth.application.util.IGenerateRandomStringPort;
import kotetsu.auth.application.util.IGetCurrentInstantPort;
import kotetsu.auth.application.util.IGetSelfUrlPort;
import kotetsu.auth.application.util.IHashStringPort;

@ExtendWith(MockitoExtension.class)
public class ExchangeTokenTest {

    private ExchangeTokenUsecase exchangeTokenUsecase;

    @Mock
    private IFindAuthorizationCodeByCodePort findAuthorizationCodeByCodePort;

    @Mock
    private IFindClientInformationByIdPort findClientInformationByIdPort;

    @Mock
    private IFindAccessTokenDraftByIdPort findAccessTokenDraftByIdPort;

    @Mock
    private IFindIdTokenDraftByCodePort findIdTokenDraftByIdPort;

    @Mock
    private IStoreAccessTokenPort storeAccessTokenPort;

    @Mock
    private IStoreRefreshTokenPort storeRefreshTokenPort;

    @Mock
    private IDeleteAuthorizationCodePort deleteAuthorizationCodePort;

    @Mock
    private IHashStringPort hashStringPort;

    @Mock
    private IGenerateRandomStringPort generateRandomStringPort;

    @Mock
    private IGenerateIdTokenFromDraftPort generateIdTokenFromDraftPort;

    @Mock
    private IGetCurrentInstantPort getCurrentInstantPort;

    @Mock
    private IGetSelfUrlPort getSelfUrlPort;

    @Mock
    private ExchangeTokenInput input;

    @Mock
    private AuthorizationCodeData authorizationCode;

    @Mock
    private ClientInformationData clientInformation;

    @Mock
    private AccessTokenDraftData accessTokenDraft;

    @Mock
    private IdTokenDraftData idTokenDraft;

    @Mock
    private ScopeData scopeTaskRead;

    @Mock
    private ScopeData scopeTaskWrite;

    @BeforeEach
    public void setUp() {
        exchangeTokenUsecase = new ExchangeTokenUsecase(
            findAuthorizationCodeByCodePort,
            findClientInformationByIdPort,
            findAccessTokenDraftByIdPort,
            findIdTokenDraftByIdPort,
            storeAccessTokenPort,
            storeRefreshTokenPort,
            deleteAuthorizationCodePort,
            hashStringPort,
            generateRandomStringPort,
            generateIdTokenFromDraftPort,
            getCurrentInstantPort,
            getSelfUrlPort
        );
    }

    @Test
    public void returnTokenIfAllConditionsValid() {
        try (
            MockedStatic<TokenOutput> outputStatic = mockStatic(TokenOutput.class);
            MockedStatic<AccessTokenStore> accessTokenStoreStatic = mockStatic(AccessTokenStore.class);
            MockedStatic<RefreshTokenStore> refreshTokenStoreStatic = mockStatic(RefreshTokenStore.class);
        ) {
            when(findAuthorizationCodeByCodePort.findByCode(anyString())).thenReturn(authorizationCode);
            when(authorizationCode.getExpiredAt()).thenReturn(Date.from(Instant.parse("2025-06-01T00:10:00Z")));
            when(authorizationCode.getChallenge()).thenReturn("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
            when(authorizationCode.getAccessTokenDraftCode()).thenReturn(UUID.fromString("0ad217c3-0018-6627-0500-e9d315f74e32"));
            when(authorizationCode.getIdTokenDraftCode()).thenReturn(UUID.fromString("2896437a-4cec-7cb4-43af-bf5efa279f61"));

            when(getCurrentInstantPort.getCurrent()).thenReturn(Instant.parse("2025-06-01T00:00:00Z"));

            when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);
            when(clientInformation.getSecret()).thenReturn("client-secret");
            when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");

            when(hashStringPort.hashSha256(anyString())).thenReturn("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");

            when(findAccessTokenDraftByIdPort.findById(any())).thenReturn(accessTokenDraft);
            when(accessTokenDraft.getSubject()).thenReturn("f47ac10b-58cc-4372-a567-0e02b2c3d479");
            when(scopeTaskRead.getCode()).thenReturn(UUID.fromString("a8b9c7d2-4f5e-4a1b-9c8d-7e6f5a4b3c2d"));
            when(scopeTaskRead.getName()).thenReturn("task.read");
            when(scopeTaskWrite.getCode()).thenReturn(UUID.fromString("3e7f8a9b-2c1d-4e5f-8a7b-6c9d2e1f4a5b"));
            when(scopeTaskWrite.getName()).thenReturn("task.write");
            when(accessTokenDraft.getScopes()).thenReturn(List.of(scopeTaskRead, scopeTaskWrite));
            when(accessTokenDraft.getAudiences()).thenReturn(List.of("api.example.com", "resource.example.com"));

            when(findIdTokenDraftByIdPort.findByCode(any())).thenReturn(idTokenDraft);

            when(generateRandomStringPort.generate(512)).thenReturn("access-token-512-chars");
            when(generateRandomStringPort.generate(256)).thenReturn("refresh-token-256-chars");
            when(getSelfUrlPort.getUrl()).thenReturn("https://auth.example.com");

            when(storeAccessTokenPort.store(any())).thenReturn("stored-access-token");
            when(storeRefreshTokenPort.store(any())).thenReturn("stored-refresh-token");
            when(generateIdTokenFromDraftPort.generate(any())).thenReturn("generated-id-token");

            when(input.getCode()).thenReturn("authorization-code");
            when(input.getClientId()).thenReturn("client-id");
            when(input.getClientSecret()).thenReturn("client-secret");
            when(input.getCodeVerifier()).thenReturn("code-verifier");
            when(input.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");

            assertDoesNotThrow(() -> {
                exchangeTokenUsecase.exchangeToken(input);
            });

            ArgumentCaptor<String> accessTokenValueCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> accessTokenIssuerCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<UUID> accessTokenSubjectCaptor = ArgumentCaptor.forClass(UUID.class);
            @SuppressWarnings("unchecked")
            ArgumentCaptor<List<UUID>> accessTokenScopeCodesCaptor = ArgumentCaptor.forClass(List.class);
            ArgumentCaptor<Date> accessTokenIssuedAtCaptor = ArgumentCaptor.forClass(Date.class);
            ArgumentCaptor<Date> accessTokenExpiredAtCaptor = ArgumentCaptor.forClass(Date.class);
            accessTokenStoreStatic.verify(() -> AccessTokenStore.of(
                accessTokenValueCaptor.capture(),
                accessTokenIssuerCaptor.capture(),
                accessTokenSubjectCaptor.capture(),
                accessTokenScopeCodesCaptor.capture(),
                accessTokenIssuedAtCaptor.capture(),
                accessTokenExpiredAtCaptor.capture()
            ));
            assertEquals("access-token-512-chars", accessTokenValueCaptor.getValue());
            assertEquals("https://auth.example.com", accessTokenIssuerCaptor.getValue());
            assertEquals(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), accessTokenSubjectCaptor.getValue());
            assertEquals(List.of(
                UUID.fromString("a8b9c7d2-4f5e-4a1b-9c8d-7e6f5a4b3c2d"),
                UUID.fromString("3e7f8a9b-2c1d-4e5f-8a7b-6c9d2e1f4a5b")
            ), accessTokenScopeCodesCaptor.getValue());
            assertEquals(Date.from(Instant.parse("2025-06-01T00:00:00Z")), accessTokenIssuedAtCaptor.getValue());
            assertEquals(Date.from(Instant.parse("2025-06-01T01:00:00Z")), accessTokenExpiredAtCaptor.getValue());

            ArgumentCaptor<String> refreshTokenValueCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<UUID> refreshTokenAccessTokenDraftIdCaptor = ArgumentCaptor.forClass(UUID.class);
            ArgumentCaptor<UUID> refreshTokenIdTokenDraftIdCaptor = ArgumentCaptor.forClass(UUID.class);
            ArgumentCaptor<Date> refreshTokenIssuedAtCaptor = ArgumentCaptor.forClass(Date.class);
            ArgumentCaptor<Date> refreshTokenExpiredAtCaptor = ArgumentCaptor.forClass(Date.class);
            refreshTokenStoreStatic.verify(() -> RefreshTokenStore.of(
                refreshTokenValueCaptor.capture(),
                refreshTokenAccessTokenDraftIdCaptor.capture(),
                refreshTokenIdTokenDraftIdCaptor.capture(),
                refreshTokenIssuedAtCaptor.capture(),
                refreshTokenExpiredAtCaptor.capture()
            ));
            assertEquals("refresh-token-256-chars", refreshTokenValueCaptor.getValue());
            assertEquals(UUID.fromString("0ad217c3-0018-6627-0500-e9d315f74e32"), refreshTokenAccessTokenDraftIdCaptor.getValue());
            assertEquals(UUID.fromString("2896437a-4cec-7cb4-43af-bf5efa279f61"), refreshTokenIdTokenDraftIdCaptor.getValue());
            assertEquals(Date.from(Instant.parse("2025-06-01T00:00:00Z")), refreshTokenIssuedAtCaptor.getValue());
            assertEquals(Date.from(Instant.parse("2025-07-01T00:00:00Z")), refreshTokenExpiredAtCaptor.getValue());

            ArgumentCaptor<String> outputAccessTokenCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> outputTokenTypeCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Long> outputExpiresInCaptor = ArgumentCaptor.forClass(Long.class);
            ArgumentCaptor<String> outputRefreshTokenCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> outputIdTokenCaptor = ArgumentCaptor.forClass(String.class);
            @SuppressWarnings("unchecked")
            ArgumentCaptor<List<String>> outputScopesCaptor = ArgumentCaptor.forClass(List.class);
            @SuppressWarnings("unchecked")
            ArgumentCaptor<List<String>> outputAudiencesCaptor = ArgumentCaptor.forClass(List.class);
            outputStatic.verify(() -> TokenOutput.of(
                outputAccessTokenCaptor.capture(),
                outputTokenTypeCaptor.capture(),
                outputExpiresInCaptor.capture(),
                outputRefreshTokenCaptor.capture(),
                outputIdTokenCaptor.capture(),
                outputScopesCaptor.capture(),
                outputAudiencesCaptor.capture()
            ));
            assertEquals("stored-access-token", outputAccessTokenCaptor.getValue());
            assertEquals("Bearer", outputTokenTypeCaptor.getValue());
            assertEquals(3600L, outputExpiresInCaptor.getValue());
            assertEquals("stored-refresh-token", outputRefreshTokenCaptor.getValue());
            assertEquals("generated-id-token", outputIdTokenCaptor.getValue());
            assertEquals(List.of("task.read", "task.write"), outputScopesCaptor.getValue());
            assertEquals(List.of("api.example.com", "resource.example.com"), outputAudiencesCaptor.getValue());
        }
    }

    @Test
    public void throwAuthorizationCodeNotFoundIOExceptionIfCodeNotFound() {
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);
        when(clientInformation.getSecret()).thenReturn("client-secret");
        when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(findAuthorizationCodeByCodePort.findByCode(anyString())).thenReturn(null);
        when(input.getClientId()).thenReturn("client-id");
        when(input.getClientSecret()).thenReturn("client-secret");
        when(input.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(input.getCode()).thenReturn("authorization-code");

        AuthorizationCodeNotFoundIOException exception = assertThrows(AuthorizationCodeNotFoundIOException.class, () -> {
            exchangeTokenUsecase.exchangeToken(input);
        });

        assertEquals("認可コードが見つかりません。", exception.getMessage());
    }

    @Test
    public void throwAuthorizationCodeExpiredIOExceptionIfCodeExpired() {
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);
        when(clientInformation.getSecret()).thenReturn("client-secret");
        when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(findAuthorizationCodeByCodePort.findByCode(anyString())).thenReturn(authorizationCode);
        when(authorizationCode.getExpiredAt()).thenReturn(Date.from(Instant.parse("2025-05-31T23:59:00Z")));
        when(getCurrentInstantPort.getCurrent()).thenReturn(Instant.parse("2025-06-01T00:00:00Z"));
        when(input.getClientId()).thenReturn("client-id");
        when(input.getClientSecret()).thenReturn("client-secret");
        when(input.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(input.getCode()).thenReturn("expired-authorization-code");

        AuthorizationCodeExpiredIOException exception = assertThrows(AuthorizationCodeExpiredIOException.class, () -> {
            exchangeTokenUsecase.exchangeToken(input);
        });

        assertEquals("認可コードの有効期限が切れています。", exception.getMessage());
    }

    @Test
    public void throwClientNotFoundIOExceptionIfClientNotFound() {
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(null);
        when(input.getClientId()).thenReturn("nonexistent-client");

        ClientNotFoundIOException exception = assertThrows(ClientNotFoundIOException.class, () -> {
            exchangeTokenUsecase.exchangeToken(input);
        });

        assertEquals(ClientNotFoundIOException.class, exception.getClass());
    }

    @Test
    public void throwClientCheckIOExceptionIfClientSecretMismatch() {
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);
        when(clientInformation.getSecret()).thenReturn("correct-secret");
        when(input.getClientId()).thenReturn("client-id");
        when(input.getClientSecret()).thenReturn("wrong-secret");

        ClientCheckIOException exception = assertThrows(ClientCheckIOException.class, () -> {
            exchangeTokenUsecase.exchangeToken(input);
        });

        assertEquals("クライアントシークレットが一致しません。", exception.getMessage());
    }

    @Test
    public void throwClientCheckIOExceptionIfRedirectUriMismatch() {
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);
        when(input.getClientId()).thenReturn("client-id");
        when(clientInformation.getSecret()).thenReturn("client-secret");
        when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(input.getClientSecret()).thenReturn("client-secret");
        when(input.getRedirectUri()).thenReturn("https://malicious.example.com/oauth2/callback");

        ClientCheckIOException exception = assertThrows(ClientCheckIOException.class, () -> {
            exchangeTokenUsecase.exchangeToken(input);
        });

        assertEquals("redirectUriが登録情報と一致しません。", exception.getMessage());
    }

    @Test
    public void throwClientCheckIOExceptionIfCodeVerifierMismatch() {
        when(findAuthorizationCodeByCodePort.findByCode(anyString())).thenReturn(authorizationCode);
        when(authorizationCode.getExpiredAt()).thenReturn(Date.from(Instant.parse("2025-06-01T00:10:00Z")));
        when(authorizationCode.getChallenge()).thenReturn("correct-hash");
        when(getCurrentInstantPort.getCurrent()).thenReturn(Instant.parse("2025-06-01T00:00:00Z"));
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);
        when(clientInformation.getSecret()).thenReturn("client-secret");
        when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(hashStringPort.hashSha256(anyString())).thenReturn("wrong-hash");
        when(input.getClientId()).thenReturn("client-id");
        when(input.getClientSecret()).thenReturn("client-secret");
        when(input.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(input.getCode()).thenReturn("authorization-code");
        when(input.getCodeVerifier()).thenReturn("wrong-verifier");

        ClientCheckIOException exception = assertThrows(ClientCheckIOException.class, () -> {
            exchangeTokenUsecase.exchangeToken(input);
        });

        assertEquals("code_verifierが一致しません。", exception.getMessage());
    }
}