package kotetsu.auth.unit.usecase.getclientcredentialusecase;

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

import kotetsu.auth.application.dto.data.ClientCredentialData;
import kotetsu.auth.application.dto.input.ClientCredentialOutput;
import kotetsu.auth.application.dto.output.GetClientCredentialInput;
import kotetsu.auth.application.exception.ClientCredentialNotFoundException;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.query.IFindClientCredentialPort;
import kotetsu.auth.application.usecase.GetClientCredentialUsecase;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IFindClientCredentialPort findClientCredentialPort;

    @InjectMocks
    private GetClientCredentialUsecase getClientCredentialUsecase;

    @Test
    public void canGetClientCredentialIfClientExists() {
        when(findClientCredentialPort.findByClientId(any())).thenReturn(Optional.of(
            ClientCredentialData.of("testClientId", "hashedSecret123")
        ));

        assertDoesNotThrow(() -> {
            final ClientCredentialOutput result = getClientCredentialUsecase.execute(
                GetClientCredentialInput.of("testClientId")
            );
            
            assertEquals("testClientId", result.getClientId());
            assertEquals("hashedSecret123", result.getClientSecret());
        });
    }

    @Test
    public void throwExceptionIfInputIsNull() {
        InputNullRuntimeException exception = assertThrows(InputNullRuntimeException.class, () -> {
            getClientCredentialUsecase.execute(null);
        });

        assertEquals("inputはnullが許容されていません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfClientCredentialNotFound() {
        when(findClientCredentialPort.findByClientId(any())).thenReturn(Optional.empty());

        ClientCredentialNotFoundException exception = assertThrows(ClientCredentialNotFoundException.class, () -> {
            getClientCredentialUsecase.execute(GetClientCredentialInput.of("nonExistentClientId"));
        });

        assertEquals("ClientCredentialが見つかりません。", exception.getMessage());
    }
}