package kotetsu.auth.unit.domain.service.creatependinginternalauthtokenservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.service.CreatePendingInternalAuthTokenService;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.domain.value.UserName;

public class CreateTest {
    @Test
    public void createTest() {
        CreatePendingInternalAuthTokenService createPendingInternalAuthTokenService = new CreatePendingInternalAuthTokenService();

        PendingInternalAuthToken token = createPendingInternalAuthTokenService.create(
            Subject.of("user-subject-key"),
            MeProfile.of(
                Key.of("user-subject-key"),
                UserName.of("user-name"),
                Email.of("email@example.com"),
                ImageUrl.of("https://example.com/image")
            ),
            IssuedAt.of(Date.from(
                LocalDateTime.of(2025, 9, 13, 17, 15, 1).atZone(ZoneId.of("UTC")).toInstant()
            ))
        );

        assertEquals("user-subject-key", token.getSubject().getValue());
        final MeProfile expectedProfile = MeProfile.of(
            Key.of("user-subject-key"),
            UserName.of("user-name"),
            Email.of("email@example.com"),
            ImageUrl.of("https://example.com/image")
        );
        assertEquals(expectedProfile.getKey().getValue(), token.getProfile().getKey().getValue());
        assertEquals(expectedProfile.getName().getValue(), token.getProfile().getName().getValue());
        assertEquals(expectedProfile.getEmail().getValue(), token.getProfile().getEmail().getValue());
        assertEquals(expectedProfile.getImageUrl().getValue(), token.getProfile().getImageUrl().getValue());

        final Date expectedIssuedAt = Date.from(
            LocalDateTime.of(2025, 9, 13, 17, 15, 1).atZone(ZoneId.of("UTC")).toInstant()
        );
        assertEquals(expectedIssuedAt, token.getDuration().getIssuedAt().getValue());

        final Date expectedExpiredAt = Date.from(
            LocalDateTime.of(2025, 9, 20, 17, 15, 1).atZone(ZoneId.of("UTC")).toInstant()
        );
        assertEquals(expectedExpiredAt, token.getDuration().getExpiredAt().getValue());
    }
}