package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class AccessType {
    public static final String OFFLINE = "offline";
    public static final String ONLINE = "online";

    @Getter
    @NotBlank
    private final String value;

    private AccessType(final String value) {
        this.value = value;
    }

    public static AccessType of(final String value) {
        if (value == null) {
            return new AccessType(AccessType.ONLINE);
        }

        final AccessType accessType = new AccessType(value);
        return accessType;
    }

    public boolean isOffline() {
        return (value == OFFLINE);
    }

    public boolean isOnline() {
        return (value == ONLINE);
    }
}
