package kotetsu.auth.unit.getauthorizationcodeusecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.dto.data.ScopeData;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.dto.store.AccessTokenDraftStore;
import kotetsu.auth.application.dto.store.AuthorizationCodeStore;
import kotetsu.auth.application.dto.store.IdTokenDraftStore;
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

            when(pendingScopeTaskRead.getName()).thenReturn("task.read");
            when(pendingScopeTaskRead.getCode()).thenReturn(UUID.fromString("a8b9c7d2-4f5e-4a1b-9c8d-7e6f5a4b3c2d"));
            when(pendingScopeTaskWrite.getName()).thenReturn("task.write");
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

            when(clientInformation.getId()).thenReturn("wSHyRwltCIQVaXlynrVkFiiyNAu1vnis.kotetsu");
            when(clientInformation.getCode()).thenReturn(UUID.fromString("ef726f1b-2569-1b91-3385-88d1eb375df6"));
            when(clientInformation.getRedirectUri()).thenReturn("https://app.example.com/oauth2/callback");
            when(findClientInformationByIdPort.findById(anyString())).thenReturn(clientInformation);

            when(generateRandomStringPort.generate(anyInt())).thenReturn("YKovutYoVp2MttO7EBmVSLidrOHEvWrlCwpFjhSHjaqtWxIc3eB0g5K367Hi6vjW");

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

            ArgumentCaptor<String> accessTokenValueCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> accessTokenIssuerCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<UUID> accessTokenSubjectCaptor = ArgumentCaptor.forClass(UUID.class);
            @SuppressWarnings("unchecked")
            ArgumentCaptor<List<UUID>> accessTokenScopeCodesCaptor = ArgumentCaptor.forClass(List.class);
            accessTokenDraftStoreStatic.verify(() -> AccessTokenDraftStore.of(
                accessTokenValueCaptor.capture(), 
                accessTokenIssuerCaptor.capture(),
                accessTokenSubjectCaptor.capture(),
                accessTokenScopeCodesCaptor.capture()
            ));
        }
    }
}
