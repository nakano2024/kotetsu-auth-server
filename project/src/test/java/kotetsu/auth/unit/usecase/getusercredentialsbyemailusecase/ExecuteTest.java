package kotetsu.auth.unit.usecase.getusercredentialsbyemailusecase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.dto.data.UserCredentialData;
import kotetsu.auth.application.dto.input.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.output.UserCredentialsOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.UserCredentialNotFoundException;
import kotetsu.auth.application.query.IFindUserCredentialByEmailPort;
import kotetsu.auth.application.usecase.GetUserCredentialsByEmailUsecase;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IFindUserCredentialByEmailPort findUserCredentialByEmailPort;

    @InjectMocks
    private GetUserCredentialsByEmailUsecase getUserCredentialsByEmailUsecase;

    @Test
    public void canGetUserCredentialsIfUserExists() {
        when(findUserCredentialByEmailPort.findByEmail(any())).thenReturn(Optional.of(
            UserCredentialData.of("user-key-123", "test@example.com", "hashedPassword123")
        ));

        assertDoesNotThrow(() -> {
            final UserCredentialsOutput result = getUserCredentialsByEmailUsecase.execute(
                GetUserCredentialEmailInput.of("test@example.com")
            );
            
            assertEquals("test@example.com", result.getEmail());
            assertEquals("hashedPassword123", result.getHashedPassword());
        });
    }

    @Test
    public void throwExceptionIfInputIsNull() {
        InputNullRuntimeException exception = assertThrows(InputNullRuntimeException.class, () -> {
            getUserCredentialsByEmailUsecase.execute(null);
        });

        assertEquals("inputはnullが許容されていません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfUserCredentialNotFound() {
        when(findUserCredentialByEmailPort.findByEmail(any())).thenReturn(Optional.empty());

        UserCredentialNotFoundException exception = assertThrows(UserCredentialNotFoundException.class, () -> {
            getUserCredentialsByEmailUsecase.execute(
                GetUserCredentialEmailInput.of("nonexistent@example.com")
            );
        });

        assertEquals("UserCredentialが見つかりません。", exception.getMessage());
    }
}