package kotetsu.auth.unit.domain.entity.pendinginternalauthtoken;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.domain.value.UserName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Subject subject = Subject.of("test-subject");
        MeProfile profile = MeProfile.of(
            Key.of("profile-key"),
            UserName.of("test-user"),
            Email.of("test@example.com"),
            ImageUrl.of("https://example.com/image.jpg")
        );
        IssuedAt issuedAt = IssuedAt.of(new Date(1000));
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        Duration duration = Duration.of(issuedAt, expiredAt);

        PendingInternalAuthToken pendingInternalAuthToken = PendingInternalAuthToken.of(
            subject,
            profile,
            duration
        );

        assertEquals("test-subject", pendingInternalAuthToken.getSubject().getValue());
        assertEquals("profile-key", pendingInternalAuthToken.getProfile().getKey().getValue());
        assertEquals("test-user", pendingInternalAuthToken.getProfile().getName().getValue());
        assertEquals("test@example.com", pendingInternalAuthToken.getProfile().getEmail().getValue());
        assertEquals("https://example.com/image.jpg", pendingInternalAuthToken.getProfile().getImageUrl().getValue());
        assertEquals(new Date(1000), pendingInternalAuthToken.getDuration().getIssuedAt().getValue());
        assertEquals(new Date(2000), pendingInternalAuthToken.getDuration().getExpiredAt().getValue());
    }
}