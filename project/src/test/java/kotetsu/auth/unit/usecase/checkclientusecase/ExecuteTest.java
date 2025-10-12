package kotetsu.auth.unit.usecase.checkclientusecase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.dto.input.CheckClientInput;
import kotetsu.auth.application.dto.output.ClientCheckOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.usecase.CheckClientUsecase;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IFetchRequesterClientPort fetchRequeterClientPort;

    @InjectMocks
    private CheckClientUsecase checkClientUsecase;

    @Test
    public void canCheckClientIfAllConditionIsSatisfied() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        final ClientCheckOutput result = checkClientUsecase.execute(
            CheckClientInput.of(
                "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                "https://example.com/callback"
            )
        );

        assertEquals(ClientCheckOutput.STATUS_OK, result.getStatus());
    }

    @Test
    public void throwExceptionIfInputIsNull() {
        InputNullRuntimeException exception = assertThrows(InputNullRuntimeException.class, () -> {
            checkClientUsecase.execute(null);
        });

        assertEquals("inputはnullが許容されていません。", exception.getMessage());
    }

    @Test
    public void returnClientNotFoundIfRequesterClientIsEmpty() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.empty());

        final ClientCheckOutput result = checkClientUsecase.execute(
            CheckClientInput.of(
                "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                "https://example.com/callback"
            )
        );

        assertEquals(ClientCheckOutput.STATUS_CLIENT_NOT_FOUND, result.getStatus());
    }

    @Test
    public void returnInvalidRedirectUriIfRedirectUriDoesNotMatch() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/invalid-callback")
        )));

        final ClientCheckOutput result = checkClientUsecase.execute(
            CheckClientInput.of(
                "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                "https://example.com/callback"
            )
        );

        assertEquals(ClientCheckOutput.STATUS_INVALID_REDIRECT_URI, result.getStatus());
    }
}
