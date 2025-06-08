package kotetsu.auth.application.dto.data;

import lombok.Getter;

public class IdTokenProfileData {
    @Getter
    final String name;

    @Getter
    final String email;

    @Getter
    final String imageUrl;

    private IdTokenProfileData(final String name, final String email, final String imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static IdTokenProfileData of(final String name, final String email, final String imageUrl) {
        return new IdTokenProfileData(name, email, imageUrl);
    }
}
