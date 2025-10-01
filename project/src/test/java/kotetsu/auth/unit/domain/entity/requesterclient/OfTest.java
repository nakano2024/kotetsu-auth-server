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
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/callback")
        );

        assertEquals("test-key", requesterClient.getKey().getValue());
        assertEquals("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com", requesterClient.getClientId().getValue());
        assertEquals("https://example.com/callback", requesterClient.getRedirectUri().getValue());
    }
}