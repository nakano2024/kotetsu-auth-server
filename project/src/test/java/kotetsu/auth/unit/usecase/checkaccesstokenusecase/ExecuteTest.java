package kotetsu.auth.unit.usecase.checkaccesstokenusecase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.entity.RequestedRelatedAudienceList;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.ResourceOwnerValidator;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenPort;
import kotetsu.auth.application.domain.repository.IFetchResourceOwnerValidator;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.ScopeName;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.domain.value.UserActivation;
import kotetsu.auth.application.dto.input.CheckAccessTokenInput;
import kotetsu.auth.application.dto.output.AccessTokenCheckOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.usecase.CheckAccessTokenUsecase;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IFetchCurrentDatePort fetchCurrentDatePort;

    @Mock
    private IFetchExistingAccessTokenPort fetchExistingAccessTokenPort;

    @Mock
    private IFetchExistingAccessTokenCorePort fetchExistingAccessTokenCorePort;

    @Mock
    private IFetchResourceOwnerValidator fetchResourceOwnerValidatorPort;

    @InjectMocks
    private CheckAccessTokenUsecase checkAccessTokenUsecase;

    @Test
    public void returnActiveResultIfAllChecksHasPassed() {
        when(fetchCurrentDatePort.fetch()).thenReturn(Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        ));

        when(fetchExistingAccessTokenPort.fetch(any())).thenReturn(Optional.of(ExistingAccessToken.of(
            Key.of("c789e138-0034-cbbd-c2ba-a683bfa8bcf2"),
            LinkedAccessTokenCoreKey.of("bb6f53cc-7d37-7688-39ed-5ab28d4b36b0"),
            Duration.of(
                IssuedAt.of(Date.from(
                    LocalDateTime.of(2025, 9, 13, 11, 0, 1).atZone(ZoneId.of("UTC")).toInstant()
                )),
                ExpiredAt.of(Date.from(
                    LocalDateTime.of(2025, 9, 13, 12, 0, 1).atZone(ZoneId.of("UTC")).toInstant()
                ))
            )
        )));

        when(fetchExistingAccessTokenCorePort.fetch(any())).thenReturn(Optional.of(ExistingAccessTokenCore.of(
            Key.of("bb6f53cc-7d37-7688-39ed-5ab28d4b36b0"),
            Issuer.of("https://oauth.example.com"),
            Subject.of("f1993751-8223-5e7d-5138-fb99cfb6cb68"),
            RequestedScopeList.of(
                List.of(
                    Scope.of(
                        Key.of("d540fe41-6ea5-8e4e-2ad9-aee94db5b7dc"),
                        ScopeName.of("task.read")
                    ),
                    Scope.of(
                        Key.of("f2642471-cf73-7fdd-a4c2-617e7c49379f"),
                        ScopeName.of("task.delete")
                    ),
                    Scope.of(
                        Key.of("dae4465f-2da5-2ac4-fff8-96791057b091"),
                        ScopeName.of("file.write")
                    )
                )
            ),
            RequestedRelatedAudienceList.of(
                List.of(
                    "https://task.example.com",
                    "https://file.example.com"
                )
            ),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com")
        )));

        when(fetchResourceOwnerValidatorPort.fetch(any())).thenReturn(Optional.of(ResourceOwnerValidator.of(
            UserActivation.of(true)
        )));

        AccessTokenCheckOutput output = checkAccessTokenUsecase.execute(
            CheckAccessTokenInput.of(
                "MJESxgCnWYOfMoOJguNBa7HgCdysD4WElDlGLlplWsYDefAjJynBKA0xjy6SckdPxjbH8YtVkd8jaXklr5b0hPaupYbwFGuPQUlJ5CwHWBMseFIFepqPHdAMnuSSoh12h6FWJWyUhxUSlpJJ0uDfPEWx3qMlGhWqlqfkOvUVrnwc4GWTrbmCwseOGvv1IVtVlvCTbEnoSrj0SaldGMr7Plrh7ddO6JRlh0q3w3ITJQ8relckhXoE48YGEF"
            )
        );

        final AccessTokenCheckOutput expectedOutput = AccessTokenCheckOutput.of(
            true,
            "task.read task.delete file.write",
            "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
            1757761201L,
            1757764801L,
            "f1993751-8223-5e7d-5138-fb99cfb6cb68",
            List.of(
                "https://task.example.com",
                "https://file.example.com"
            ),
            "https://oauth.example.com",
            "Bearer"
        );

        assertEquals(expectedOutput, output);
    }

    @Test
    public void returnInactiveResultIfAccessTokenIsNotExist() {
        when(fetchExistingAccessTokenPort.fetch(any())).thenReturn(Optional.empty());

        AccessTokenCheckOutput output = checkAccessTokenUsecase.execute(
            CheckAccessTokenInput.of(
                "MJESxgCnWYOfMoOJguNBa7HgCdysD4WElDlGLlplWsYDefAjJynBKA0xjy6SckdPxjbH8YtVkd8jaXklr5b0hPaupYbwFGuPQUlJ5CwHWBMseFIFepqPHdAMnuSSoh12h6FWJWyUhxUSlpJJ0uDfPEWx3qMlGhWqlqfkOvUVrnwc4GWTrbmCwseOGvv1IVtVlvCTbEnoSrj0SaldGMr7Plrh7ddO6JRlh0q3w3ITJQ8relckhXoE48YGEF"
            )
        );

        final AccessTokenCheckOutput expectedOutput = AccessTokenCheckOutput.of(
            false,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
        
        assertEquals(expectedOutput, output);
    }

    @Test
    public void returnInactiveResultIfAccessTokenIsExpired() {
        when(fetchCurrentDatePort.fetch()).thenReturn(Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        ));

        when(fetchExistingAccessTokenPort.fetch(any())).thenReturn(Optional.of(ExistingAccessToken.of(
            Key.of("c789e138-0034-cbbd-c2ba-a683bfa8bcf2"),
            LinkedAccessTokenCoreKey.of("bb6f53cc-7d37-7688-39ed-5ab28d4b36b0"),
            Duration.of(
                IssuedAt.of(Date.from(
                    LocalDateTime.of(2025, 9, 13, 10, 59, 59).atZone(ZoneId.of("UTC")).toInstant()
                )),
                ExpiredAt.of(Date.from(
                    LocalDateTime.of(2025, 9, 13, 11, 59, 59).atZone(ZoneId.of("UTC")).toInstant()
                ))
            )
        )));

        AccessTokenCheckOutput output = checkAccessTokenUsecase.execute(
            CheckAccessTokenInput.of("MJESxgCnWYOfMoOJguNBa7HgCdysD4WElDlGLlplWsYDefAjJynBKA0xjy6SckdPxjbH8YtVkd8jaXklr5b0hPaupYbwFGuPQUlJ5CwHWBMseFIFepqPHdAMnuSSoh12h6FWJWyUhxUSlpJJ0uDfPEWx3qMlGhWqlqfkOvUVrnwc4GWTrbmCwseOGvv1IVtVlvCTbEnoSrj0SaldGMr7Plrh7ddO6JRlh0q3w3ITJQ8relckhXoE48YGEF")
        );

        final AccessTokenCheckOutput expectedOutput = AccessTokenCheckOutput.of(
            false,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
        
        assertEquals(expectedOutput, output);
    }

    @Test
    public void returnInactiveResultIfUserIsDisable() {
        when(fetchCurrentDatePort.fetch()).thenReturn(Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        ));

        when(fetchExistingAccessTokenPort.fetch(any())).thenReturn(Optional.of(ExistingAccessToken.of(
            Key.of("c789e138-0034-cbbd-c2ba-a683bfa8bcf2"),
            LinkedAccessTokenCoreKey.of("bb6f53cc-7d37-7688-39ed-5ab28d4b36b0"),
            Duration.of(
                IssuedAt.of(Date.from(
                    LocalDateTime.of(2025, 9, 13, 11, 0, 1).atZone(ZoneId.of("UTC")).toInstant()
                )),
                ExpiredAt.of(Date.from(
                    LocalDateTime.of(2025, 9, 13, 12, 0, 1).atZone(ZoneId.of("UTC")).toInstant()
                ))
            )
        )));

        when(fetchExistingAccessTokenCorePort.fetch(any())).thenReturn(Optional.of(ExistingAccessTokenCore.of(
            Key.of("bb6f53cc-7d37-7688-39ed-5ab28d4b36b0"),
            Issuer.of("https://oauth.example.com"),
            Subject.of("f1993751-8223-5e7d-5138-fb99cfb6cb68"),
            RequestedScopeList.of(
                List.of(
                    Scope.of(
                        Key.of("d540fe41-6ea5-8e4e-2ad9-aee94db5b7dc"),
                        ScopeName.of("task.read")
                    ),
                    Scope.of(
                        Key.of("f2642471-cf73-7fdd-a4c2-617e7c49379f"),
                        ScopeName.of("task.delete")
                    ),
                    Scope.of(
                        Key.of("dae4465f-2da5-2ac4-fff8-96791057b091"),
                        ScopeName.of("file.write")
                    )
                )
            ),
            RequestedRelatedAudienceList.of(
                List.of(
                    "https://task.example.com",
                    "https://file.example.com"
                )
            ),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com")
        )));

        when(fetchResourceOwnerValidatorPort.fetch(any())).thenReturn(Optional.of(ResourceOwnerValidator.of(
            UserActivation.of(false)
        )));

        AccessTokenCheckOutput output = checkAccessTokenUsecase.execute(
            CheckAccessTokenInput.of("MJESxgCnWYOfMoOJguNBa7HgCdysD4WElDlGLlplWsYDefAjJynBKA0xjy6SckdPxjbH8YtVkd8jaXklr5b0hPaupYbwFGuPQUlJ5CwHWBMseFIFepqPHdAMnuSSoh12h6FWJWyUhxUSlpJJ0uDfPEWx3qMlGhWqlqfkOvUVrnwc4GWTrbmCwseOGvv1IVtVlvCTbEnoSrj0SaldGMr7Plrh7ddO6JRlh0q3w3ITJQ8relckhXoE48YGEF")
        );

        final AccessTokenCheckOutput expectedOutput = AccessTokenCheckOutput.of(
            false,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
        
        assertEquals(expectedOutput, output);
    }

    @Test
    public void throwExceptionIfInputIsNull() {
        InputNullRuntimeException exception = assertThrows(InputNullRuntimeException.class, () -> {
            checkAccessTokenUsecase.execute(null);
        });

        assertEquals("inputはnullが許容されていません。", exception.getMessage());
    }
}