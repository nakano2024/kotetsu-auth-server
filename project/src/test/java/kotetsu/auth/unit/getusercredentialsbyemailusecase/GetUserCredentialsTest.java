package kotetsu.auth.unit.getusercredentialsbyemailusecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.UserCredential;
import kotetsu.auth.application.domain.exception.UserCredentialNotFoundException;
import kotetsu.auth.application.domain.repository.IFetchUserCredentialByEmailRepository;
import kotetsu.auth.application.domain.value.Code;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.HashedPassword;
import kotetsu.auth.application.domain.value.UserImageUrl;
import kotetsu.auth.application.domain.value.UserName;
import kotetsu.auth.application.dto.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.UserCredentialsOutput;
import kotetsu.auth.application.usecase.GetUserCredentialsByEmailUsecase;

@ExtendWith(MockitoExtension.class)
public class GetUserCredentialsTest {

    @Mock
    IFetchUserCredentialByEmailRepository fetchUserCredentialByEmailRepository;

    @Mock
    GetUserCredentialEmailInput input;

    @Mock
    Email inputEmail;

    @Mock
    Code code;

    @Mock
    UserName name;

    @Mock
    Email email;

    @Mock
    HashedPassword password;

    @Mock
    UserImageUrl imageUrl;

    @Mock
    UserCredential user;

    GetUserCredentialsByEmailUsecase usecase;

    @BeforeEach
    public void setUp() {
        usecase = new GetUserCredentialsByEmailUsecase(fetchUserCredentialByEmailRepository);
    }

    @Test
    public void canGetReturnExpectedValue() {
        try(
            MockedStatic<Email> emailStatic = mockStatic(Email.class);
            MockedStatic<UserCredentialsOutput> outputStatic = mockStatic(UserCredentialsOutput.class);
        ) {
            when(code.getValue()).thenReturn("12334545-5d1b-5fa9-f9fd-b6e896bbff17");
            when(email.getValue()).thenReturn("hoge@example.com");
            when(password.getValue()).thenReturn("$2a$12$6/QoLrOnG9M.t9bKhOQqFeWDnw/EsmT2/z8Hse.HDwuNwxdJnEVt2");

            when(user.getCode()).thenReturn(code);
            when(user.getEmail()).thenReturn(email);
            when(user.getPassword()).thenReturn(password);

            when(input.getEmail()).thenReturn("hoge@example.com");
            when(fetchUserCredentialByEmailRepository.fetchByEmail(any())).thenReturn(user);

            emailStatic.when(() -> Email.of(anyString())).thenReturn(inputEmail);

            assertDoesNotThrow(() -> {
                usecase.getUserCredentials(input);
            });

            ArgumentCaptor<String> codeCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
            outputStatic.verify(() -> UserCredentialsOutput.of(
                codeCaptor.capture(),
                emailCaptor.capture(),
                passwordCaptor.capture()
            ));

            assertEquals("12334545-5d1b-5fa9-f9fd-b6e896bbff17", codeCaptor.getValue());
            assertEquals("hoge@example.com", emailCaptor.getValue());
            assertEquals("$2a$12$6/QoLrOnG9M.t9bKhOQqFeWDnw/EsmT2/z8Hse.HDwuNwxdJnEVt2", passwordCaptor.getValue());
        }
    }

    @Test
    public void throwExceptionIfUserNull() {
        try (MockedStatic<Email> emailStatic = mockStatic(Email.class)) {
            when(input.getEmail()).thenReturn("hoge@example.com");
            when(fetchUserCredentialByEmailRepository.fetchByEmail(any())).thenReturn(null);
            
            assertThrows(UserCredentialNotFoundException.class, () -> {
                usecase.getUserCredentials(input);
            }, "UserCredential Not Found");
        }
    }
}
