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
            UserCredentialData.of(
                "ce2b299f-b745-cced-2ff6-666fde7eb16f",
                "田中太郎",
                "https://file.example.com/5a224f1e-2d4b-02ac-43af-d82d7fa123cd.png",
                "test@example.com",
                "hashedPassword123"
            )
        ));

        assertDoesNotThrow(() -> {
            final UserCredentialsOutput resultOutput = getUserCredentialsByEmailUsecase.execute(
                GetUserCredentialEmailInput.of("test@example.com")
            );

            final UserCredentialsOutput expectedOutput = UserCredentialsOutput.of(
                "ce2b299f-b745-cced-2ff6-666fde7eb16f",
                "田中太郎",
                "https://file.example.com/5a224f1e-2d4b-02ac-43af-d82d7fa123cd.png",
                "test@example.com",
                "hashedPassword123"
            );

            assertEquals(expectedOutput, resultOutput);
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