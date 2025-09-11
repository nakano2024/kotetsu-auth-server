package kotetsu.auth.application.domain.value;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ClientRedirectUri {
    @Getter
    @NotBlank
    private final String value;

    private ClientRedirectUri(final String value) {
        this.value = value;
    }

    public static ClientRedirectUri of(final String value) {
        final ClientRedirectUri clientRedirectUri = new ClientRedirectUri(value);
        return clientRedirectUri;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final ClientRedirectUri anotherClientRedirectUri = (ClientRedirectUri) obj;

        return this.value.equals(anotherClientRedirectUri.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
