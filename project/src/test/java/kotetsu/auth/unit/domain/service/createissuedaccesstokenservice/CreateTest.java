package kotetsu.auth.unit.domain.service.createissuedaccesstokenservice;

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

import kotetsu.auth.application.domain.entity.IssuedAccessToken;
import kotetsu.auth.application.domain.service.CreateIssuedAccessTokenService;
import kotetsu.auth.application.domain.util.IGenerateAccessTokenValuePort;
import kotetsu.auth.application.domain.value.AccessTokenValue;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

@ExtendWith(MockitoExtension.class)
public class CreateTest {
    @Mock
    private IGenerateAccessTokenValuePort generateAccessTokenValuePort;

    @InjectMocks
    private CreateIssuedAccessTokenService createIssuedAccessTokenService;

    @Test
    public void createTest() {
        when(generateAccessTokenValuePort.generate(AccessTokenValue.LENGTH)).thenReturn(AccessTokenValue.of("access-token-value"));

        IssuedAccessToken token = createIssuedAccessTokenService.create(
            LinkedAccessTokenCoreKey.of("linked-access-token-core-key"),
            IssuedAt.of(Date.from(
                LocalDateTime.of(2025, 9, 13, 17, 15).atZone(ZoneId.of("UTC")).toInstant()
            ))
        );

        assertEquals("access-token-value", token.getValue().getValue());
        final Date expectedIssuedAt = Date.from(
                LocalDateTime.of(2025, 9, 13, 17, 15).atZone(ZoneId.of("UTC")).toInstant()
        );
        assertEquals(expectedIssuedAt, token.getDuration().getIssuedAt().getValue());
        assertEquals("linked-access-token-core-key", token.getLinkedAccessTokenCoreKey().getValue());
        final Date expectedExpiredAt = Date.from(
            LocalDateTime.of(2025, 9, 13, 18, 15).atZone(ZoneId.of("UTC")).toInstant()
        );
        assertEquals(expectedExpiredAt, token.getDuration().getExpiredAt().getValue());
    }
}