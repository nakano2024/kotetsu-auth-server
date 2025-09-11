package kotetsu.auth.unit.domain.entity.meprofile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.UserName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        UserName name = UserName.of("test-user");
        Email email = Email.of("test@example.com");
        ImageUrl imageUrl = ImageUrl.of("https://example.com/image.jpg");

        MeProfile meProfile = MeProfile.of(
            key,
            name,
            email,
            imageUrl
        );

        assertEquals("test-key", meProfile.getKey().getValue());
        assertEquals("test-user", meProfile.getName().getValue());
        assertEquals("test@example.com", meProfile.getEmail().getValue());
        assertEquals("https://example.com/image.jpg", meProfile.getImageUrl().getValue());
    }
}