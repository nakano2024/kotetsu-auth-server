package kotetsu.auth.unit.domain.value.accesstype;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AccessType;

public class IsOfflineTest {
    @Test
    public void returnTrueIfArgumentIsOffline() {
        AccessType accessType = AccessType.of("offline");

        assertTrue(accessType.isOffline());
    }

    @Test
    public void returnFalseIfArgumentIsOnline() {
        AccessType accessType = AccessType.of("online");

        assertFalse(accessType.isOffline());
    }
}
