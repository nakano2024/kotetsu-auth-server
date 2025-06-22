package kotetsu.auth.application.dto.data;

import java.util.UUID;

import lombok.Getter;

public class ClientInformationData {
    @Getter
    private final UUID code;

    @Getter
    private final String name;

    @Getter
    private final String secret;

    @Getter
    private final String redirectUri;

    @Getter
    private final boolean isValid;


    private ClientInformationData(
        final UUID code,
        final String name,
        final String secret,
        final String redirectUri,
        final boolean isValid
    ) {
        this.code = code;
        this.name = name;
        this.secret = secret;
        this.redirectUri = redirectUri;
        this.isValid = isValid;
    }

    public static ClientInformationData of(
        final UUID code,
        final String name,
        final String secret,
        final String redirectUri,
        final boolean isValid
    ) {
        return new ClientInformationData(
            code,
            name,
            secret,
            redirectUri,
            isValid
        );
    }
}
