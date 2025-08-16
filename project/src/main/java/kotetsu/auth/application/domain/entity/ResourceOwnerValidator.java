package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.UserActivation;

public class ResourceOwnerValidator {
    @NotNull
    private final UserActivation activation;

    private ResourceOwnerValidator(final UserActivation activation) {
        this.activation = activation;
    }

    public static ResourceOwnerValidator of(final UserActivation activation) {
        final ResourceOwnerValidator resourceOwnerValidator = new ResourceOwnerValidator(activation);

        return resourceOwnerValidator;
    }

    public boolean isActive() {
        return activation.isActive();
    }
}
