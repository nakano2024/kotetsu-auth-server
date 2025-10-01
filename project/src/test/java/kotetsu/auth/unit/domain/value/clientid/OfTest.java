package kotetsu.auth.unit.domain.value.clientid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ClientId;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        ClientId clientId = ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com");

        assertEquals("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com", clientId.getValue());
    }
}