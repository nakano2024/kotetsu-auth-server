package kotetsu.auth.unit.domain.entity.pendingidtokencore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.PendingIdTokenCore;
import kotetsu.auth.application.domain.value.IdTokenAudience;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.Subject;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        Issuer issuer = Issuer.of("test-issuer");
        Subject subject = Subject.of("test-subject");
        Nonce nonce = Nonce.of("test-nonce");
        IdTokenAudience audience = IdTokenAudience.of("test-audience");

        PendingIdTokenCore pendingIdTokenCore = PendingIdTokenCore.of(
            key,
            issuer,
            subject,
            nonce,
            audience
        );

        assertEquals("test-key", pendingIdTokenCore.getKey().getValue());
        assertEquals("test-issuer", pendingIdTokenCore.getIssuer().getValue());
        assertEquals("test-subject", pendingIdTokenCore.getSubject().getValue());
        assertEquals("test-nonce", pendingIdTokenCore.getNonce().getValue());
        assertEquals("test-audience", pendingIdTokenCore.getAudience().getValue());
    }
}