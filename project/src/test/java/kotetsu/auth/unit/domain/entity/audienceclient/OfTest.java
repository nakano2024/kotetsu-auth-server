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
            ClientId.of("test-client-id")
        );

        assertEquals("6277c4cb-85a9-73b5-42ba-f3d279e749de", audienceClient.getKey().getValue());
        assertEquals("test-client-id", audienceClient.getClientId().getValue());
    }
}
