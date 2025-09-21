package kotetsu.auth.unit.usecase.getinternaltokenbyemailusecase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.IssuedInternalAuthToken;
import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.repository.IFetchMeProfilePort;
import kotetsu.auth.application.domain.service.CreateIssuedInternalAuthTokeService;
import kotetsu.auth.application.domain.service.CreatePendingInternalAuthTokenService;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.InternalAuthTokenValue;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.domain.value.UserName;
import kotetsu.auth.application.dto.input.GetInternalTokenInput;
import kotetsu.auth.application.dto.output.IdTokenOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.MeProfileNotFoundIOException;
import kotetsu.auth.application.usecase.GetInternalTokenByEmailUsecase;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IFetchMeProfilePort fetchMeProfilePort;

    @Mock
    private CreatePendingInternalAuthTokenService createPendingInternalAuthTokenService;

    @Mock
    private CreateIssuedInternalAuthTokeService createIssuedInternalAuthTokeService;

    @Mock
    private IFetchCurrentDatePort fetchCurrentDatePort;

    @InjectMocks
    private GetInternalTokenByEmailUsecase getInternalTokenByEmailUsecase;

    @Test
    public void canGetInternalTokenIfUserExists() {
        final Date currentDate = Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );

        when(fetchCurrentDatePort.fetch()).thenReturn(currentDate);
        
        when(fetchMeProfilePort.fetch(any())).thenReturn(Optional.of(
            MeProfile.of(
                Key.of("990a9655-8ace-499c-11db-503fbc63b0e2"),
                UserName.of("testDisplayName"),
                Email.of("test@example.com"),
                ImageUrl.of("https://example.com/avatar.jpg")
            )
        ));

        final Date expiredDate = Date.from(
            LocalDateTime.of(2025, 9, 20, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );
        
        final PendingInternalAuthToken mockPendingToken = PendingInternalAuthToken.of(
            Subject.of("990a9655-8ace-499c-11db-503fbc63b0e2"),
            MeProfile.of(
                Key.of("990a9655-8ace-499c-11db-503fbc63b0e2"),
                UserName.of("testDisplayName"),
                Email.of("test@example.com"),
                ImageUrl.of("https://example.com/avatar.jpg")
            ),
            Duration.of(IssuedAt.of(currentDate), ExpiredAt.of(expiredDate))
        );

        when(createPendingInternalAuthTokenService.create(any(), any(), any())).thenReturn(mockPendingToken);

        final IssuedInternalAuthToken mockIssuedToken = IssuedInternalAuthToken.of(
            InternalAuthTokenValue.of("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.token")
        );

        when(createIssuedInternalAuthTokeService.create(any())).thenReturn(mockIssuedToken);

        assertDoesNotThrow(() -> {
            final IdTokenOutput result = getInternalTokenByEmailUsecase.execute(
                GetInternalTokenInput.of("990a9655-8ace-499c-11db-503fbc63b0e2")
            );
            
            ArgumentCaptor<Subject> subjectCaptor = ArgumentCaptor.forClass(Subject.class);
            ArgumentCaptor<MeProfile> meProfileCaptor = ArgumentCaptor.forClass(MeProfile.class);
            ArgumentCaptor<IssuedAt> issuedAtCaptor = ArgumentCaptor.forClass(IssuedAt.class);
            
            verify(createPendingInternalAuthTokenService).create(
                subjectCaptor.capture(),
                meProfileCaptor.capture(),
                issuedAtCaptor.capture()
            );

            assertEquals("990a9655-8ace-499c-11db-503fbc63b0e2", subjectCaptor.getValue().getValue());
            assertEquals("test@example.com", meProfileCaptor.getValue().getEmail().getValue());
            assertEquals("testDisplayName", meProfileCaptor.getValue().getName().getValue());
            assertEquals(currentDate, issuedAtCaptor.getValue().getValue());

            verify(createIssuedInternalAuthTokeService).create(mockPendingToken);

            assertEquals("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.token", result.getIdToken());
            assertEquals(IssuedInternalAuthToken.TOKEN_TYPE, result.getTokenType());
        });
    }

    @Test
    public void throwExceptionIfInputIsNull() {
        InputNullRuntimeException exception = assertThrows(InputNullRuntimeException.class, () -> {
            getInternalTokenByEmailUsecase.execute(null);
        });

        assertEquals("inputはnullが許容されていません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfMeProfileNotFound() {
        when(fetchCurrentDatePort.fetch()).thenReturn(new Date());
        when(fetchMeProfilePort.fetch(any())).thenReturn(Optional.empty());

        MeProfileNotFoundIOException exception = assertThrows(MeProfileNotFoundIOException.class, () -> {
            getInternalTokenByEmailUsecase.execute(
                GetInternalTokenInput.of("nonExistentUserKey")
            );
        });

        assertEquals("MeProfileが見つかりません。", exception.getMessage());
    }
}