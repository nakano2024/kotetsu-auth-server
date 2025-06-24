package kotetsu.auth.unit.getauthorizationcodeusecase;

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
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.dto.data.ScopeData;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.dto.store.AccessTokenDraftStore;
import kotetsu.auth.application.dto.store.AuthorizationCodeStore;
import kotetsu.auth.application.dto.store.IdTokenDraftStore;
import kotetsu.auth.application.exception.ClientCheckIOException;
import kotetsu.auth.application.exception.ClientNotFoundIOException;
import kotetsu.auth.application.exception.InvalidPendingScopesIOException;
import kotetsu.auth.application.persistence.IFindClientInformationByIdPort;
import kotetsu.auth.application.persistence.IFindPermittedScopeListByClientCodePort;
import kotetsu.auth.application.persistence.IFindScopeListByScopeNameListPort;
import kotetsu.auth.application.persistence.IStoreAccessTokenDraftPort;
import kotetsu.auth.application.persistence.IStoreAuthorizationCodePort;
import kotetsu.auth.application.persistence.IStoreIdTokenDraftPort;
import kotetsu.auth.application.usecase.GetAuthorizationCodeUsecase;
import kotetsu.auth.application.util.IGenerateRandomStringPort;
import kotetsu.auth.application.util.IGetCurrentInstantPort;
import kotetsu.auth.application.util.IGetSelfUrlPort;

@ExtendWith(MockitoExtension.class)
public class GetAuthorizationCodeTest {

    private GetAuthorizationCodeUsecase getAuthorizationCodeUsecase;

    @Mock
    private IStoreAccessTokenDraftPort storeAccessTokenDraftPort;

    @Mock
    private IStoreIdTokenDraftPort storeIdTokenDraftPort;

    @Mock
    private IStoreAuthorizationCodePort storeAuthorizationCodePort;

    @Mock
    private IFindScopeListByScopeNameListPort findScopeListByScopeNameListPort;

    @Mock
    IFindPermittedScopeListByClientCodePort findPermittedScopeListByClientCodePort;

    @Mock
    IFindClientInformationByIdPort findClientInformationByIdPort;

    @Mock
    IGenerateRandomStringPort generateRandomStringPort;

    @Mock
    IGetSelfUrlPort getSelfUrlPort;

    @Mock
    IGetCurrentInstantPort getCurrentInstantPort;

    @Mock
    GetAuthorizationCodeInput input;

    @Mock
    ScopeData pendingScopeTaskRead;

    @Mock
    ScopeData pendingScopeTaskWrite;

    @Mock
    ScopeData permittedScopeTaskRead;

    @Mock
    ScopeData permittedScopeTaskWrite;

    @Mock
    ScopeData permittedScopeTaskDelete;

    @Mock
    ClientInformationData clientInformation;

    @BeforeEach
    public void setUp() {
        getAuthorizationCodeUsecase = new GetAuthorizationCodeUsecase(
            storeAccessTokenDraftPort,
            storeIdTokenDraftPort,
            storeAuthorizationCodePort,
            findScopeListByScopeNameListPort,
            findPermittedScopeListByClientCodePort,
            findClientInformationByIdPort,
            generateRandomStringPort,
            getSelfUrlPort,
            getCurrentInstantPort
        );
    }

    @Test
    public void returnAuthorizationCodeIfAllConditionsValid() {
        try (
            MockedStatic<AuthorizationCodeOutput> outputStatic = mockStatic(AuthorizationCodeOutput.class);
            MockedStatic<AccessTokenDraftStore> accessTokenDraftStoreStatic = mockStatic(AccessTokenDraftStore.class);
            MockedStatic<IdTokenDraftStore> idTokenDraftStoreStatic = mockStatic(IdTokenDraftStore.class);
            MockedStatic<AuthorizationCodeStore> authorizationCodeStoreStatic = mockStatic(AuthorizationCodeStore.class);
        ) {
            when(storeAccessTokenDraftPort.store(any())).thenReturn(UUID.fromString("0ad217c3-0018-6627-0500-e9d315f74e32"));
            when(storeIdTokenDraftPort.store(any())).thenReturn(UUID.fromString("2896437a-4cec-7cb4-43af-bf5efa279f61"));
            when(storeAuthorizationCodePort.store(any())).thenReturn("YKovutYoVp2MttO7EBmVSLidrOHEvWrlCwpFjhSHjaqtWxIc3eB0g5K367Hi6vjW");

            when(pendingScopeTaskRead.getCode()).thenReturn(UUID.fromString("a8b9c7d2-4f5e-4a1b-9c8d-7e6f5a4b3c2d"));
            when(pendingScopeTaskWrite.getCode()).thenReturn(UUID.fromString("3e7f8a9b-2c1d-4e5f-8a7b-6c9d2e1f4a5b"));
            when(findScopeListByScopeNameListPort.findByScopeNames(any())).thenReturn(List.of(
                pendingScopeTaskRead,
                pendingScopeTaskWrite
            ));

            when(permittedScopeTaskRead.getName()).thenReturn("task.read");
            when(permittedScopeTaskDelete.getName()).thenReturn("task.delete");
            when(permittedScopeTaskWrite.getName()).thenReturn("task.write");
            when(findPermittedScopeListByClientCodePort.findByClientCode(any())).thenReturn(List.of(
                permittedScopeTaskRead,
                permittedScopeTaskDelete,
                permittedScopeTaskWrite
            ));

            when(clientInformation.getCode()).thenReturn(UUID.fromString("ef726f1b-2569-1b91-3385-88d1eb375df6"));
            when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
            when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);

            when(generateRandomStringPort.generate(64)).thenReturn("YKovutYoVp2MttO7EBmVSLidrOHEvWrlCwpFjhSHjaqtWxIc3eB0g5K367Hi6vjW");

            when(getSelfUrlPort.getUrl()).thenReturn("https://auth.example.com");

            when(getCurrentInstantPort.getCurrent()).thenReturn(Instant.parse("2025-06-01T00:00:00Z"));

            when(input.getClientId()).thenReturn("wSHyRwltCIQVaXlynrVkFiiyNAu1vnis.kotetsu");
            when(input.getCodeChallenge()).thenReturn("e1c26038efc6260cfc740e974652593249ccb440e8bb37afd2867444922285a2");
            when(input.getPendingScopeString()).thenReturn("task.read task.write");
            when(input.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
            when(input.getResourceOwnerCode()).thenReturn("f47ac10b-58cc-4372-a567-0e02b2c3d479");

            assertDoesNotThrow(() -> {
                getAuthorizationCodeUsecase.getAuthorizationCode(input);
            });

            ArgumentCaptor<String> accessTokenIssuerCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<UUID> accessTokenSubjectCaptor = ArgumentCaptor.forClass(UUID.class);
            @SuppressWarnings("unchecked")
            ArgumentCaptor<List<UUID>> accessTokenScopeCodesCaptor = ArgumentCaptor.forClass(List.class);
            accessTokenDraftStoreStatic.verify(() -> AccessTokenDraftStore.of(
                accessTokenIssuerCaptor.capture(),
                accessTokenSubjectCaptor.capture(),
                accessTokenScopeCodesCaptor.capture()
            ));
            assertEquals("https://auth.example.com", accessTokenIssuerCaptor.getValue());
            assertEquals(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), accessTokenSubjectCaptor.getValue());
            assertEquals(List.of(
                UUID.fromString("a8b9c7d2-4f5e-4a1b-9c8d-7e6f5a4b3c2d"),
                UUID.fromString("3e7f8a9b-2c1d-4e5f-8a7b-6c9d2e1f4a5b")
            ), accessTokenScopeCodesCaptor.getValue());

            ArgumentCaptor<UUID> idTokenSubjectCaptor = ArgumentCaptor.forClass(UUID.class);
            ArgumentCaptor<String> idTokenIssuerCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<UUID> idTokenAudienceCaptor = ArgumentCaptor.forClass(UUID.class);
            idTokenDraftStoreStatic.verify(() -> IdTokenDraftStore.of(
                idTokenSubjectCaptor.capture(),
                idTokenIssuerCaptor.capture(),
                idTokenAudienceCaptor.capture()
            ));
            assertEquals(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), idTokenSubjectCaptor.getValue());
            assertEquals("https://auth.example.com", idTokenIssuerCaptor.getValue());
            assertEquals(UUID.fromString("ef726f1b-2569-1b91-3385-88d1eb375df6"), idTokenAudienceCaptor.getValue());

            ArgumentCaptor<String> authCodeValueCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> authCodeChallengeCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<UUID> authCodeAccessTokenDraftIdCaptor = ArgumentCaptor.forClass(UUID.class);
            ArgumentCaptor<UUID> authCodeIdTokenDraftIdCaptor = ArgumentCaptor.forClass(UUID.class);
            ArgumentCaptor<Date> authCodeIssuedAtCaptor = ArgumentCaptor.forClass(Date.class);
            ArgumentCaptor<Date> authCodeExpiresAtCaptor = ArgumentCaptor.forClass(Date.class);
            authorizationCodeStoreStatic.verify(() -> AuthorizationCodeStore.of(
                authCodeValueCaptor.capture(),
                authCodeChallengeCaptor.capture(),
                authCodeAccessTokenDraftIdCaptor.capture(),
                authCodeIdTokenDraftIdCaptor.capture(),
                authCodeIssuedAtCaptor.capture(),
                authCodeExpiresAtCaptor.capture()
            ));
            assertEquals("YKovutYoVp2MttO7EBmVSLidrOHEvWrlCwpFjhSHjaqtWxIc3eB0g5K367Hi6vjW", authCodeValueCaptor.getValue());
            assertEquals("e1c26038efc6260cfc740e974652593249ccb440e8bb37afd2867444922285a2", authCodeChallengeCaptor.getValue());
            assertEquals(UUID.fromString("0ad217c3-0018-6627-0500-e9d315f74e32"), authCodeAccessTokenDraftIdCaptor.getValue());
            assertEquals(UUID.fromString("2896437a-4cec-7cb4-43af-bf5efa279f61"), authCodeIdTokenDraftIdCaptor.getValue());
            assertEquals(Date.from(Instant.parse("2025-06-01T00:00:00Z")), authCodeIssuedAtCaptor.getValue());
            assertEquals(Date.from(Instant.parse("2025-06-01T00:01:00Z")), authCodeExpiresAtCaptor.getValue());

            ArgumentCaptor<String> outputAuthCodeCaptor = ArgumentCaptor.forClass(String.class);
            outputStatic.verify(() -> AuthorizationCodeOutput.of(outputAuthCodeCaptor.capture()));
            assertEquals("YKovutYoVp2MttO7EBmVSLidrOHEvWrlCwpFjhSHjaqtWxIc3eB0g5K367Hi6vjW", outputAuthCodeCaptor.getValue());
        }
    }

    @Test
    public void throwClientCheckIOExceptionIfRedirectUriMismatch() {
        when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);

        when(input.getClientId()).thenReturn("wSHyRwltCIQVaXlynrVkFiiyNAu1vnis.kotetsu");
        when(input.getRedirectUri()).thenReturn("https://malicious.example.com/oauth2/callback");

        ClientCheckIOException exception = assertThrows(ClientCheckIOException.class, () -> {
            getAuthorizationCodeUsecase.getAuthorizationCode(input);
        });

        assertEquals("redirectUriが登録情報と一致しません。", exception.getMessage());
    }

    @Test
    public void throwClientNotFoundIOExceptionIfClientInformationIsNull() {
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(null);
        when(input.getClientId()).thenReturn("nonexistent.client");

        ClientNotFoundIOException exception = assertThrows(ClientNotFoundIOException.class, () -> {
            getAuthorizationCodeUsecase.getAuthorizationCode(input);
        });
        
        // 例外が正しく投げられたことを確認（戻り値を使用）
        assertEquals(ClientNotFoundIOException.class, exception.getClass());
    }

    @Test
    public void throwInvalidPendingScopesIOExceptionIfScopeNotPermitted() {
        when(clientInformation.getCode()).thenReturn(UUID.fromString("ef726f1b-2569-1b91-3385-88d1eb375df6"));
        when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);

        // 許可されたスコープ（task.read, task.deleteのみ）
        when(permittedScopeTaskRead.getName()).thenReturn("task.read");
        when(permittedScopeTaskDelete.getName()).thenReturn("task.delete");
        when(findPermittedScopeListByClientCodePort.findByClientCode(any())).thenReturn(List.of(
            permittedScopeTaskRead,
            permittedScopeTaskDelete
        ));

        when(input.getClientId()).thenReturn("wSHyRwltCIQVaXlynrVkFiiyNAu1vnis.kotetsu");
        when(input.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
        // 許可されていないスコープ（task.write）を含むリクエスト
        when(input.getPendingScopeString()).thenReturn("task.read task.write");

        InvalidPendingScopesIOException exception = assertThrows(InvalidPendingScopesIOException.class, () -> {
            getAuthorizationCodeUsecase.getAuthorizationCode(input);
        });

        assertEquals("許可されていないscopeが含まれています。", exception.getMessage());
    }
}
