package kotetsu.auth.unit.getuserprofilebyemailusecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.exception.UserProfileNotFoundException;
import kotetsu.auth.application.dto.data.UserProfileData;
import kotetsu.auth.application.dto.input.GetUserProfileEmailInput;
import kotetsu.auth.application.dto.output.UserProfileOutput;
import kotetsu.auth.application.persistence.IFindUserProfileByEmailPort;
import kotetsu.auth.application.usecase.GetUserProfileByEmailUsecase;

@ExtendWith(MockitoExtension.class)
public class GetUserProfileTest {
    @Mock
    GetUserProfileEmailInput input;

    @Mock
    IFindUserProfileByEmailPort findUserProfileByEmailPort;

    @Mock
    UserProfileData userProfile;

    GetUserProfileByEmailUsecase getUserProfileByEmailUsecase;

    @BeforeEach
    public void setUpForEach() {
        getUserProfileByEmailUsecase = new GetUserProfileByEmailUsecase(findUserProfileByEmailPort);
    }

    @Test
    public void returnUserProfileIfUserExist() {
        try (MockedStatic<UserProfileOutput> outputStatic = mockStatic(UserProfileOutput.class)) {
            when(userProfile.getCode()).thenReturn(UUID.fromString("b4aabeed-c3bc-7873-636f-a38786457162"));
            when(userProfile.getName()).thenReturn("田中太郎");
            when(userProfile.getEmail()).thenReturn("tanaka@example.com");
            when(userProfile.getImageUrl()).thenReturn("https://example.com/image.png");
            when(findUserProfileByEmailPort.findByEmail(anyString())).thenReturn(userProfile);
            
            when(input.getEmail()).thenReturn("tanaka@example.com");

            assertDoesNotThrow(() -> {
                getUserProfileByEmailUsecase.getUserProfile(input);
            });

            ArgumentCaptor<UUID> codeCaptor = ArgumentCaptor.forClass(UUID.class);
            ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> imageUrlCaptor = ArgumentCaptor.forClass(String.class);
            outputStatic.verify(() -> UserProfileOutput.of(
                codeCaptor.capture(),
                nameCaptor.capture(), 
                emailCaptor.capture(),
                imageUrlCaptor.capture())
            );

            assertEquals("b4aabeed-c3bc-7873-636f-a38786457162", codeCaptor.getValue().toString());
            assertEquals("田中太郎", nameCaptor.getValue());
            assertEquals("tanaka@example.com", emailCaptor.getValue());
            assertEquals("https://example.com/image.png", imageUrlCaptor.getValue());
        }
    }

    @Test
    public void throwExceptionIfUserExist() {
        try (MockedStatic<UserProfileOutput> outputStatic = mockStatic(UserProfileOutput.class)) {
            when(findUserProfileByEmailPort.findByEmail(anyString())).thenReturn(null);
            
            when(input.getEmail()).thenReturn("tanaka@example.com");

            assertThrows(UserProfileNotFoundException.class, () -> {
                getUserProfileByEmailUsecase.getUserProfile(input);
            }, "UserProfile Not Found");
        }
    }
}
