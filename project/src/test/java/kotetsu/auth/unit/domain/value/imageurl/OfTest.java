package kotetsu.auth.unit.domain.value.imageurl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ImageUrl;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        ImageUrl imageUrl = ImageUrl.of("https://example.com/image.jpg");

        assertEquals("https://example.com/image.jpg", imageUrl.getValue());
    }
}