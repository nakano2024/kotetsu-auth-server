package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class ImageUrl {
    @Getter
    private final String value;

    private ImageUrl(final String value) {
        this.value = value;
    }

    public static ImageUrl of(final String value) {
        final ImageUrl imageUrl = new ImageUrl(value);
        return imageUrl;
    }
}
