package kotetsu.auth.unit.domain.service.createauthorizationservice;

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

import kotetsu.auth.application.domain.entity.RequestedAuthorization;
import kotetsu.auth.application.domain.service.CreateAuthorizationService;
import kotetsu.auth.application.domain.util.IGenerateAuthorizationCodeValuePort;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;

@ExtendWith(MockitoExtension.class)
public class CreateTest {
    @Mock
    private IGenerateAuthorizationCodeValuePort generateAuthorizationCodeValuePort;

    @InjectMocks
    private CreateAuthorizationService createAuthorizationService;

    @Test
    public void createTest() {
        when(generateAuthorizationCodeValuePort.generate()).thenReturn(AuthorizationCodeValue.of("authorization-code-value"));

        RequestedAuthorization authorization = createAuthorizationService.create(
            AuthorizationCodeChallenge.of("code-challenge"),
            AccessType.of(AccessType.ONLINE),
            LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea"),
            LinkedIdTokenCoreKey.of("linked-id-token-core-key"),
            LinkedRefreshTokenCoreKey.of("linked-refresh-token-core-key"),
            IssuedAt.of(Date.from(
                LocalDateTime.of(2025, 9, 13, 17, 15, 1).atZone(ZoneId.of("UTC")).toInstant()
            ))
        );

        assertEquals("authorization-code-value", authorization.getAuthorizationCode().getValue().getValue());
        assertEquals("code-challenge", authorization.getAuthorizationCode().getChallenge().getValue());
        assertEquals(AccessType.ONLINE, authorization.getAccessType().getValue());
        assertEquals("3498665a-6863-7065-62ee-0be766cff4ea", authorization.getLinkedAccessTokenCoreKey().getValue());
        assertEquals("linked-id-token-core-key", authorization.getLinkedIdTokenCoreKey().getValue());
        assertEquals("linked-refresh-token-core-key", authorization.getLinkedRefreshTokenCoreKey().getValue());
        final Date expectedExpiredAt = Date.from(
            LocalDateTime.of(2025, 9, 13, 17, 16, 1).atZone(ZoneId.of("UTC")).toInstant()
        );
        assertEquals(expectedExpiredAt, authorization.getAuthorizationCode().getExpiredAt().getValue());
    }
}