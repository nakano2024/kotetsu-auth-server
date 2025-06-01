package kotetsu.auth.unit.getuserprofilebyemailusecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.dto.data.UserProfileData;
import kotetsu.auth.application.dto.input.GetUserProfileEmailInput;
import kotetsu.auth.application.dto.output.IdTokenOutput;
import kotetsu.auth.application.exception.UserProfileNotFoundException;
import kotetsu.auth.application.persistence.IFindUserProfileByEmailPort;
import kotetsu.auth.application.usecase.GetIdTokenByEmailUsecase;
import kotetsu.auth.application.util.IGenerateIdTokenPort;
import kotetsu.auth.application.util.IGetCurrentInstantPort;

@ExtendWith(MockitoExtension.class)
public class GetUserProfileTest {
    @Mock
    GetUserProfileEmailInput input;

    @Mock
    IFindUserProfileByEmailPort findUserProfileByEmailPort;

    @Mock
    IGenerateIdTokenPort generateIdTokenPort;

    @Mock
    IGetCurrentInstantPort getCurrentDatePort;

    @Mock
    UserProfileData userProfile;

    GetIdTokenByEmailUsecase getUserProfileByEmailUsecase;

    @BeforeEach
    public void setUpForEach() {
        getUserProfileByEmailUsecase = new GetIdTokenByEmailUsecase(
            findUserProfileByEmailPort,
            generateIdTokenPort,
            getCurrentDatePort
        );
    }

    @Test
    public void returnUserProfileIfUserExist() {
        try (MockedStatic<IdTokenOutput> outputStatic = mockStatic(IdTokenOutput.class)) {
            when(userProfile.getCode()).thenReturn(UUID.fromString("b4aabeed-c3bc-7873-636f-a38786457162"));
            when(userProfile.getName()).thenReturn("田中太郎");
            when(userProfile.getEmail()).thenReturn("tanaka@example.com");
            when(userProfile.getImageUrl()).thenReturn("https://example.com/image.png");
            when(findUserProfileByEmailPort.findByEmail(anyString())).thenReturn(userProfile);
            
            when(input.getEmail()).thenReturn("tanaka@example.com");

            when(generateIdTokenPort.generate(anyString(), any(), any(), any()))
                .thenReturn("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI4YTExMDFiMS0yYmJkLTQxYzktOTIwNi0zOWE2NDQ1M2I3YzUiLCJwcm9maWxlIjp7ImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsImltYWdlX3VybCI6Imh0dHBzOi8vZXhhbXBsZS5jb20vdXNlci8zNWRiMTI1MS00NGE3LWI0MzItNTZkNC1kNDg2NGY4Yjk0ZDEucG5nIiwibmFtZSI6IuODhuOCueODiOWkqumDjiJ9LCJpYXQiOjE3NDg3NzI1NDQsImV4cCI6MTc0OTM3NzM0NH0.s8AEkS4_F1sUEn8oEnKhWy0wXdQ2SoY3GWfG7A0bWAJ-zgGhySFK1J970WZWT3kTEngla88SRe4swXYyb6AT3ZHyLf8vzn_NQYgQdgJYE-DQ9xQChPYMdfvc9sJ9kjHWWMvHv_XpUvblztVBen7oaFkXsUwTbaniWQcqs9hFJzKa7Zpy7MKPRf7kF-O-KGjU_qg_DPDwde9LO5RsoWqgCpFvBXguYEty8i9WOrFdWtCUmpmka0tr7qxd6faehy9aRlUAZVIHcdP0h970talgCBdcYQM26n-mVOwPCaKd_a76JqjML7Oq9GUHpleeB2xRCP2Oy6wKllkefiHo6oueYQ");

            when(getCurrentDatePort.getCurrent()).thenReturn(Instant.parse("2025-06-01T00:00:00Z"));

            assertDoesNotThrow(() -> {
                getUserProfileByEmailUsecase.getUserProfile(input);
            });

            ArgumentCaptor<String> jwtCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> tokenTypeCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Long> expiresInCaptor = ArgumentCaptor.forClass(Long.class);
            outputStatic.verify(() -> IdTokenOutput.of(
                jwtCaptor.capture(), 
                tokenTypeCaptor.capture(),
                expiresInCaptor.capture())
            );

            assertEquals("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI4YTExMDFiMS0yYmJkLTQxYzktOTIwNi0zOWE2NDQ1M2I3YzUiLCJwcm9maWxlIjp7ImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsImltYWdlX3VybCI6Imh0dHBzOi8vZXhhbXBsZS5jb20vdXNlci8zNWRiMTI1MS00NGE3LWI0MzItNTZkNC1kNDg2NGY4Yjk0ZDEucG5nIiwibmFtZSI6IuODhuOCueODiOWkqumDjiJ9LCJpYXQiOjE3NDg3NzI1NDQsImV4cCI6MTc0OTM3NzM0NH0.s8AEkS4_F1sUEn8oEnKhWy0wXdQ2SoY3GWfG7A0bWAJ-zgGhySFK1J970WZWT3kTEngla88SRe4swXYyb6AT3ZHyLf8vzn_NQYgQdgJYE-DQ9xQChPYMdfvc9sJ9kjHWWMvHv_XpUvblztVBen7oaFkXsUwTbaniWQcqs9hFJzKa7Zpy7MKPRf7kF-O-KGjU_qg_DPDwde9LO5RsoWqgCpFvBXguYEty8i9WOrFdWtCUmpmka0tr7qxd6faehy9aRlUAZVIHcdP0h970talgCBdcYQM26n-mVOwPCaKd_a76JqjML7Oq9GUHpleeB2xRCP2Oy6wKllkefiHo6oueYQ", jwtCaptor.getValue());
            assertEquals("Bearer", tokenTypeCaptor.getValue());
            assertEquals(86400, expiresInCaptor.getValue());
        }
    }

    @Test
    public void throwExceptionIfUserExist() {
        try (MockedStatic<IdTokenOutput> outputStatic = mockStatic(IdTokenOutput.class)) {
            when(findUserProfileByEmailPort.findByEmail(anyString())).thenReturn(null);
            
            when(input.getEmail()).thenReturn("tanaka@example.com");

            assertThrows(UserProfileNotFoundException.class, () -> {
                getUserProfileByEmailUsecase.getUserProfile(input);
            }, "UserProfile Not Found");
        }
    }
}
