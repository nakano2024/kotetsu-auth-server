package kotetsu.auth.unit.domain.service.createissuedrefreshtokenservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.IssuedRefreshToken;
import kotetsu.auth.application.domain.service.CreateIssuedRefreshTokenService;
import kotetsu.auth.application.domain.util.IGenerateRefreshTokenValuePort;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.RefreshTokenValue;

@ExtendWith(MockitoExtension.class)
public class CreateTest {
    @Mock
    private IGenerateRefreshTokenValuePort generateRefreshTokenValuePort;

    @InjectMocks
    private CreateIssuedRefreshTokenService createIssuedRefreshTokenService;

    @Test
    public void createTest() {
        when(generateRefreshTokenValuePort.generate()).thenReturn(RefreshTokenValue.of("refresh-token-value"));

        IssuedRefreshToken token = createIssuedRefreshTokenService.create(
            LinkedRefreshTokenCoreKey.of("linked-refresh-token-core-key"),
            IssuedAt.of(Date.from(
                LocalDateTime.of(2025, 9, 13, 17, 15, 1).atZone(ZoneId.of("UTC")).toInstant()
            ))
        );

        assertEquals("refresh-token-value", token.getValue().getValue());
        assertEquals("linked-refresh-token-core-key", token.getLinkedRefreshTokenCoreKey().getValue());
        
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