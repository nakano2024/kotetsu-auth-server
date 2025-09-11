package kotetsu.auth.unit.domain.entity.existingidtokencore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.IdTokenProfile;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.domain.value.UserName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        Issuer issuer = Issuer.of("test-issuer");
        Subject subject = Subject.of("test-subject");
        Nonce nonce = Nonce.of("test-nonce");
        IdTokenProfile profile = IdTokenProfile.of(
            UserName.of("test-user"),
            Email.of("test@example.com"),
            ImageUrl.of("https://example.com/image.jpg")
        );

        ExistingIdTokenCore existingIdTokenCore = ExistingIdTokenCore.of(
            key,
            issuer,
            subject,
            nonce,
            profile
        );

        assertEquals("test-key", existingIdTokenCore.getKey().getValue());
        assertEquals("test-issuer", existingIdTokenCore.getIssuer().getValue());
        assertEquals("test-subject", existingIdTokenCore.getSubject().getValue());
        assertEquals("test-nonce", existingIdTokenCore.getNonce().getValue());
        assertEquals("test-user", existingIdTokenCore.getProfile().getName().getValue());
        assertEquals("test@example.com", existingIdTokenCore.getProfile().getEmail().getValue());
        assertEquals("https://example.com/image.jpg", existingIdTokenCore.getProfile().getImageUrl().getValue());
    }
}