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

import kotetsu.auth.application.domain.exception.UserCredentialNotFoundException;
import kotetsu.auth.application.dto.data.UserCredentialData;
import kotetsu.auth.application.dto.input.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.output.UserCredentialsOutput;
import kotetsu.auth.application.persistence.IFindUserCredentialByEmailPort;
import kotetsu.auth.application.usecase.GetUserCredentialsByEmailUsecase;

@ExtendWith(MockitoExtension.class)
public class GetUserCredentialsTest {

    @Mock
    IFindUserCredentialByEmailPort findUserCredentialByEmailPort;

    @Mock
    GetUserCredentialEmailInput input;

    @Mock
    UserCredentialData user;

    GetUserCredentialsByEmailUsecase usecase;

    @BeforeEach
    public void setUp() {
        usecase = new GetUserCredentialsByEmailUsecase(findUserCredentialByEmailPort);
    }

    @Test
    public void canGetReturnExpectedValue() {
        try(MockedStatic<UserCredentialsOutput> outputStatic = mockStatic(UserCredentialsOutput.class)) {
            when(user.getEmail()).thenReturn("hoge@example.com");
            when(user.getHashedPassword()).thenReturn("$2a$12$6/QoLrOnG9M.t9bKhOQqFeWDnw/EsmT2/z8Hse.HDwuNwxdJnEVt2");

            when(input.getEmail()).thenReturn("hoge@example.com");
            when(findUserCredentialByEmailPort.findByEmail(anyString())).thenReturn(user);

            assertDoesNotThrow(() -> {
                usecase.getUserCredentials(input);
            });

            ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
            outputStatic.verify(() -> UserCredentialsOutput.of(
                emailCaptor.capture(),
                passwordCaptor.capture()
            ));

            assertEquals("hoge@example.com", emailCaptor.getValue());
            assertEquals("$2a$12$6/QoLrOnG9M.t9bKhOQqFeWDnw/EsmT2/z8Hse.HDwuNwxdJnEVt2", passwordCaptor.getValue());
        }
    }

    @Test
    public void throwExceptionIfUserNull() {
        when(input.getEmail()).thenReturn("hoge@example.com");
        when(findUserCredentialByEmailPort.findByEmail(any())).thenReturn(null);
        
        assertThrows(UserCredentialNotFoundException.class, () -> {
            usecase.getUserCredentials(input);
        }, "UserCredential Not Found");
    }
}
