package kotetsu.auth.unit.domain.value.clientname;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ClientName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        ClientName clientName = ClientName.of("Test Application");

        assertEquals("Test Application", clientName.getValue());
    }
}