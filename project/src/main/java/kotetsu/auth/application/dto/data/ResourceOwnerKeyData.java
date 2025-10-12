package kotetsu.auth.application.dto.data;

import lombok.Getter;

public class ResourceOwnerKeyData {
    @Getter
    private final String key;

    private ResourceOwnerKeyData(final String key) {
        this.key = key;
    }

    public static ResourceOwnerKeyData of(final String key) {
        final ResourceOwnerKeyData resourceOwnerKeyData = new ResourceOwnerKeyData(key);

        return resourceOwnerKeyData;
    }
}
