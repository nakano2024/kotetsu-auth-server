package kotetsu.auth.unit.domain.value.accesstype;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AccessType;

public class IsOnlineTest {
    @Test
    public void returnTrueIfArgumentIsOnline() {
        AccessType accessType = AccessType.of("online");

        assertTrue(accessType.isOnline());
    }

    @Test
    public void returnFalseIfArgumentIsOffline() {
        AccessType accessType = AccessType.of("offline");

        assertFalse(accessType.isOnline());
    }
}
