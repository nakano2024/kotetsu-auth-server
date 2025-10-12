package kotetsu.auth.feature.usecase.checkaccesstokenusecasetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.dto.input.CheckAccessTokenInput;
import kotetsu.auth.application.dto.output.AccessTokenCheckOutput;
import kotetsu.auth.application.usecase.CheckAccessTokenUsecase;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CheckAccessTokenUsecaseExecuteTest {

    @MockitoBean
    private Clock clock;

    @Autowired
    private CheckAccessTokenUsecase checkAccessTokenUsecase;

    @Test
    @Sql("/test-data/check-access-token-test.sql")
    public void canExecuteCheckingAccessToken() {
        final Instant fixedInstant = LocalDateTime.of(2025, 9, 29, 23, 59, 59)
                .atZone(ZoneId.of("UTC"))
                .toInstant();

        when(clock.instant()).thenReturn(fixedInstant);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        assertDoesNotThrow(() -> {
            AccessTokenCheckOutput output = checkAccessTokenUsecase.execute(CheckAccessTokenInput.of(
                "wFOGcNAgstf0r4hCLBkVJ46DayBNc5AN0oHLm0FwYYPOFoFepe6c7cjV3KLGNexysXWo0h4SftwfMP0lJRUPLlgbWdV2IhwrrnXfQMEBY6QmYIqjKnXGRGlNUVvy4UGN"
            ));

            assertTrue(output.isActive());
            System.out.println("🌍active: " + output.isActive());
            System.out.println("🌍scope: " + output.getScopeToken().orElse(null));
            System.out.println("🌍iss: " + output.getIssuer().orElse(null));
            System.out.println("🌍aud: " + output.getAudiences().orElse(null).toString());
            System.out.println("🌍client_id: " + output.getClientId().orElse(null));
            System.out.println("🌍iat: " + output.getIssuedAt().orElse(null));
            System.out.println("🌍exp: " + output.getExpiredAt().orElse(null));
            System.out.println("🌍sub: " + output.getSubject().orElse(null));
            System.out.println("🌍token_type: " + output.getTokenType().orElse(null));
        });
    }
}
