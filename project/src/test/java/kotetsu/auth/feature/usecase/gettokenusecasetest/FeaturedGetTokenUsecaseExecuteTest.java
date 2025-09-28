package kotetsu.auth.feature.usecase.gettokenusecasetest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import kotetsu.auth.application.dto.input.GetTokenInput;
import kotetsu.auth.application.dto.output.TokenOutput;
import kotetsu.auth.application.usecase.GetTokenUsecase;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FeaturedGetTokenUsecaseExecuteTest {
    @MockitoBean
    private Clock clock;

    @Autowired
    GetTokenUsecase getTokenUsecase;

    @Test
    @Sql("/test-data/get-token-with-authorization-code-test.sql")
    public void canGetTokenWithAuthorizationCode() {
        final Instant fixedInstant = LocalDateTime.of(2025, 9, 29, 23, 59, 59)
                .atZone(ZoneId.of("UTC"))
                .toInstant();

        when(clock.instant()).thenReturn(fixedInstant);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        assertDoesNotThrow(() -> {
            getTokenUsecase.execute(GetTokenInput.of(
                "authorization_code",
                "XDTSRunXDmvgyWX6NamCU6UsJkbad1iv",
                "test",
                null
            ));
        });
    }

    @Test
    @Sql("/test-data/get-token-with-refresh-token-test.sql")
    public void canGetTokenWithRefreshToken() {
        final Instant fixedInstant = LocalDateTime.of(2025, 9, 29, 23, 59, 59)
                .atZone(ZoneId.of("UTC"))
                .toInstant();

        when(clock.instant()).thenReturn(fixedInstant);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));
    
        assertDoesNotThrow(() -> {
            TokenOutput output = getTokenUsecase.execute(GetTokenInput.of(
                "refresh_token",
                null,
                null,
                "rt_XyZw9876543210abcdef1234567890"
            ));

            System.out.println("🌍: " + output.getAccessToken());
            System.out.println("🌍: " + output.getScopeToken());
            System.out.println("🌍: " + output.getTokenType());
            System.out.println("🌍: " + output.getExpiresIn());
            System.out.println("🌍: " + output.getIdToken().orElse(null));
            System.out.println("🌍: " + output.getRefreshToken().orElse(null));
        });
    }
}
