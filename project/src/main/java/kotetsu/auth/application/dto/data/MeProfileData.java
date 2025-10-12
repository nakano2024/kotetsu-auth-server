package kotetsu.auth.application.dto.data;

import java.util.UUID;

import lombok.Getter;

public class MeProfileData {
    @Getter
    final UUID code;

    @Getter
    final String name;

    @Getter
    final String email;

    @Getter
    final String imageUrl;

    private MeProfileData(final UUID code, final String name, final String email, final String imageUrl) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static MeProfileData of(final UUID code, final String name, final String email, final String imageUrl) {
        return new MeProfileData(code, name, email, imageUrl);
    }
}
