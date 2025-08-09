package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import lombok.Getter;

public class ClientRedirectInformation {
    @Getter
    @NotNull
    private final ClientRedirectUri redirectUri;

    private ClientRedirectInformation(final ClientRedirectUri redirectUri) {
        this.redirectUri = redirectUri;
    }

    public static ClientRedirectInformation of(final ClientRedirectUri redirectUri) {
        final ClientRedirectInformation clientRedirectInformation = new ClientRedirectInformation(redirectUri);

        return clientRedirectInformation;
    }
}
