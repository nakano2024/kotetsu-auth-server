package kotetsu.auth.unit.domain.entity.requesterclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.domain.value.Key;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        RequesterClient requesterClient = RequesterClient.of(
            Key.of("test-key"),
            ClientId.of("test-client-id"),
            ClientRedirectUri.of("https://example.com/callback")
        );

        assertEquals("test-key", requesterClient.getKey().getValue());
        assertEquals("test-client-id", requesterClient.getClientId().getValue());
        assertEquals("https://example.com/callback", requesterClient.getRedirectUri().getValue());
    }
}