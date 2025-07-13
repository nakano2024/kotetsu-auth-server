package kotetsu.auth.application.dto.data;

import java.util.UUID;

import lombok.Getter;

public class ResourceServerData {
    @Getter
    private final UUID code;

    @Getter
    private final String name;

    @Getter
    private final String url;

    private ResourceServerData(final UUID code, final String name, final String url) {
        this.code = code;
        this.name = name;
        this.url = url;
    }

    public static ResourceServerData of(final UUID code, final String name, final String url) {
        return new ResourceServerData(code, name, url);
    }
}
