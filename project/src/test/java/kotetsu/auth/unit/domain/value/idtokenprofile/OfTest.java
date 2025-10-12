package kotetsu.auth.unit.domain.value.idtokenprofile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.IdTokenProfile;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.UserName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        UserName name = UserName.of("test-user");
        Email email = Email.of("test@example.com");
        ImageUrl imageUrl = ImageUrl.of("https://example.com/image.jpg");
        
        IdTokenProfile idTokenProfile = IdTokenProfile.of(name, email, imageUrl);

        assertEquals("test-user", idTokenProfile.getName().getValue());
        assertEquals("test@example.com", idTokenProfile.getEmail().getValue());
        assertEquals("https://example.com/image.jpg", idTokenProfile.getImageUrl().getValue());
    }
}