package kotetsu.auth.unit.domain.value.clientid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ClientId;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        ClientId clientId = ClientId.of("test-client-id");

        assertEquals("test-client-id", clientId.getValue());
    }
}