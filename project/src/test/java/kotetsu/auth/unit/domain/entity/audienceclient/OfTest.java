package kotetsu.auth.unit.domain.entity.audienceclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.AudienceClient;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.Key;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AudienceClient audienceClient = AudienceClient.of(
            Key.of("6277c4cb-85a9-73b5-42ba-f3d279e749de"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com")
        );

        assertEquals("6277c4cb-85a9-73b5-42ba-f3d279e749de", audienceClient.getKey().getValue());
        assertEquals("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com", audienceClient.getClientId().getValue());
    }
}
