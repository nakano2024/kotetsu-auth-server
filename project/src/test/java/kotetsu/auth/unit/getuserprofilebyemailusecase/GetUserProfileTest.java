package kotetsu.auth.unit.getuserprofilebyemailusecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.UserProfile;
import kotetsu.auth.application.domain.exception.UserProfileNotFoundException;
import kotetsu.auth.application.domain.repository.IFetchUserProfileByEmailRepository;
import kotetsu.auth.application.domain.value.Code;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.UserImageUrl;
import kotetsu.auth.application.domain.value.UserName;
import kotetsu.auth.application.dto.GetUserProfileEmailInput;
import kotetsu.auth.application.dto.UserProfileOutput;
import kotetsu.auth.application.usecase.GetUserProfileByEmailUsecase;

@ExtendWith(MockitoExtension.class)
public class GetUserProfileTest {
    @Mock
    GetUserProfileEmailInput input;

    @Mock
    Email inputEmail;

    @Mock
    IFetchUserProfileByEmailRepository fetchUserProfileByEmailRepository;

    @Mock
    UserProfile userProfile;

    @Mock
    Code code;

    @Mock
    UserName name;

    @Mock
    Email email;

    @Mock
    UserImageUrl imageUrl;

    GetUserProfileByEmailUsecase getUserProfileByEmailUsecase;

    @BeforeEach
    public void setUpForEach() {
        getUserProfileByEmailUsecase = new GetUserProfileByEmailUsecase(fetchUserProfileByEmailRepository);
    }

    @Test
    public void returnUserProfileIfUserExist() {
        try (
            MockedStatic<Email> emailStatic = mockStatic(Email.class);
            MockedStatic<UserProfileOutput> outputStatic = mockStatic(UserProfileOutput.class);
        ) {
            when(code.getValue()).thenReturn("b4aabeed-c3bc-7873-636f-a38786457162");
            when(userProfile.getCode()).thenReturn(code);
            when(name.getValue()).thenReturn("田中太郎");
            when(userProfile.getName()).thenReturn(name);
            when(email.getValue()).thenReturn("tanaka@example.com");
            when(userProfile.getEmail()).thenReturn(email);
            when(imageUrl.getValue()).thenReturn("https://example.com/image.png");
            when(userProfile.getImageUrl()).thenReturn(imageUrl);
            when(fetchUserProfileByEmailRepository.fetchByEmail(any())).thenReturn(userProfile);
            
            when(input.getEmail()).thenReturn("tanaka@example.com");
            emailStatic.when(() -> Email.of(anyString())).thenReturn(inputEmail);

            assertDoesNotThrow(() -> {
                getUserProfileByEmailUsecase.getUserProfile(input);
            });

            ArgumentCaptor<String> codeCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> imageUrlCaptor = ArgumentCaptor.forClass(String.class);
            outputStatic.verify(() -> UserProfileOutput.of(
                codeCaptor.capture(),
                nameCaptor.capture(), 
                emailCaptor.capture(),
                imageUrlCaptor.capture())
            );

            assertEquals("b4aabeed-c3bc-7873-636f-a38786457162", codeCaptor.getValue());
            assertEquals("田中太郎", nameCaptor.getValue());
            assertEquals("tanaka@example.com", emailCaptor.getValue());
            assertEquals("https://example.com/image.png", imageUrlCaptor.getValue());
        }
    }

    @Test
    public void throwExceptionIfUserExist() {
        try (
            MockedStatic<Email> emailStatic = mockStatic(Email.class);
            MockedStatic<UserProfileOutput> outputStatic = mockStatic(UserProfileOutput.class);
        ) {
            when(fetchUserProfileByEmailRepository.fetchByEmail(any())).thenReturn(null);
            
            when(input.getEmail()).thenReturn("tanaka@example.com");
            emailStatic.when(() -> Email.of(anyString())).thenReturn(inputEmail);

            assertThrows(UserProfileNotFoundException.class, () -> {
                getUserProfileByEmailUsecase.getUserProfile(input);
            }, "UserProfile Not Found");
        }
    }
}
