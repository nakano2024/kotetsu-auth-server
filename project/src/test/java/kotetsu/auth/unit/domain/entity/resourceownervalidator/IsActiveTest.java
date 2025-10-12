package kotetsu.auth.unit.domain.entity.resourceownervalidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ResourceOwnerValidator;
import kotetsu.auth.application.domain.value.UserActivation;

public class IsActiveTest {
    @Test
    public void returnTrueIfArgumentIsTrue() {
        UserActivation activation = UserActivation.of(true);

        ResourceOwnerValidator resourceOwnerValidator = ResourceOwnerValidator.of(activation);
        
        assertTrue(resourceOwnerValidator.isActive());
    }

        @Test
    public void returnFalseIfArgumentIsTrue() {
        UserActivation activation = UserActivation.of(false);

        ResourceOwnerValidator resourceOwnerValidator = ResourceOwnerValidator.of(activation);
        
        assertFalse(resourceOwnerValidator.isActive());
    }
}