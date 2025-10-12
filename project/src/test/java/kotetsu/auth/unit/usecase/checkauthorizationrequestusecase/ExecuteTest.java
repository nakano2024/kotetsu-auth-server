package kotetsu.auth.unit.usecase.checkauthorizationrequestusecase;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchRequestedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;
import kotetsu.auth.application.dto.input.CheckAuthorizationRequestInput;
import kotetsu.auth.application.dto.output.AuthorizationRequestCheckOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.PermittedScopeListNullRuntimeException;
import kotetsu.auth.application.exception.RequestedScopeListNullRuntimeException;
import kotetsu.auth.application.usecase.CheckAuthorizationRequestUsecase;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IFetchPermittedScopeListPort permittedScopeListPort;

    @Mock
    private IFetchRequestedScopeListPort fetchRequestedScopeListPort;

    @Mock
    private IFetchRequesterClientPort fetchRequeterClientPort;

    @InjectMocks
    private CheckAuthorizationRequestUsecase checkAuthorizationRequestUsecase;

    @Test
    public void canCheckAuthorizationRequestIfAllConditionIsSatisfied() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.of(RequestedScopeList.of(List.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("36b4c06d-4921-ad84-21ed-c96d9945668a"), ScopeName.of("task.write"))
        ))));

        when(permittedScopeListPort.fetch(any())).thenReturn(Optional.of(PermittedScopeList.of(Set.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("36b4c06d-4921-ad84-21ed-c96d9945668a"), ScopeName.of("task.write")),
            Scope.of(Key.of("f6f170ea-1d20-4642-8289-98f87d9893dc"), ScopeName.of("task.delete")),
            Scope.of(Key.of("3da7e043-c147-fe02-5586-824b3ade58bf"), ScopeName.of("task"))
        ))));

        final AuthorizationRequestCheckOutput result = checkAuthorizationRequestUsecase.execute(
            CheckAuthorizationRequestInput.of(
                "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                "https://example.com/callback",
                "task.read task.write"
            )
        );

        assertEquals(AuthorizationRequestCheckOutput.STATUS_OK, result.getStatus());
    }

    @Test
    public void throwExceptionIfInputIsNull() {
        InputNullRuntimeException exception = assertThrows(InputNullRuntimeException.class, () -> {
            checkAuthorizationRequestUsecase.execute(null);
        });

        assertEquals("inputはnullが許容されていません。", exception.getMessage());
    }

    @Test
    public void returnClientNotFoundIfRequesterClientIsEmpty() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.empty());

        final AuthorizationRequestCheckOutput result = checkAuthorizationRequestUsecase.execute(
            CheckAuthorizationRequestInput.of(
                "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                "https://example.com/callback",
                "task.read task.write"
            )
        );

        assertEquals(AuthorizationRequestCheckOutput.STATUS_CLIENT_NOT_FOUND, result.getStatus());
    }

    @Test
    public void returnInvalidRedirectUriIfRedirectUriDoesNotMatch() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/invalid-callback")
        )));

        final AuthorizationRequestCheckOutput result = checkAuthorizationRequestUsecase.execute(
            CheckAuthorizationRequestInput.of(
                "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                "https://example.com/callback",
                "task.read task.write"
            )
        );

        assertEquals(AuthorizationRequestCheckOutput.STATUS_INVALID_REDIRECT_URI, result.getStatus());
    }

    @Test
    public void throwExceptionIfRequestedScopeListIsNull() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.empty());

        RequestedScopeListNullRuntimeException exception = assertThrows(RequestedScopeListNullRuntimeException.class, () -> {
            checkAuthorizationRequestUsecase.execute(
                CheckAuthorizationRequestInput.of(
                    "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                    "https://example.com/callback",
                    "task.read task.write"
                )
            );
        });

        assertEquals("RequestedScopeListはnullが許容されません。", exception.getMessage());
    }

    @Test
    public void returnInvalidRedirectUriIfRequestedScopeNameListDoesNotMatch() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.of(RequestedScopeList.of(List.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("d4003dd9-cce3-f2f7-7b52-0329cc8f929b"), ScopeName.of("file.delete"))
        ))));

        final AuthorizationRequestCheckOutput result = checkAuthorizationRequestUsecase.execute(
            CheckAuthorizationRequestInput.of(
                "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                "https://example.com/callback",
                "task.read task.write"
            )
        );

        assertEquals(AuthorizationRequestCheckOutput.STATUS_INVALID_REDIRECT_URI, result.getStatus());
    }

    @Test
    public void throwExceptionIfPermittedScopeListIsNull() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.of(RequestedScopeList.of(List.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("36b4c06d-4921-ad84-21ed-c96d9945668a"), ScopeName.of("task.write"))
        ))));

        when(permittedScopeListPort.fetch(any())).thenReturn(Optional.empty());

        PermittedScopeListNullRuntimeException exception = assertThrows(PermittedScopeListNullRuntimeException.class, () -> {
            checkAuthorizationRequestUsecase.execute(
                CheckAuthorizationRequestInput.of(
                    "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                    "https://example.com/callback",
                    "task.read task.write"
                )
            );
        });

        assertEquals("PermittedScopeListはnullが許容されません。", exception.getMessage());
    }

    @Test
    public void returnInvalidRedirectUriIfNotPermittedScopesAreRequested() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.of(RequestedScopeList.of(List.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("d4003dd9-cce3-f2f7-7b52-0329cc8f929b"), ScopeName.of("file.delete"))
        ))));

        when(permittedScopeListPort.fetch(any())).thenReturn(Optional.of(PermittedScopeList.of(Set.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("36b4c06d-4921-ad84-21ed-c96d9945668a"), ScopeName.of("task.write")),
            Scope.of(Key.of("f6f170ea-1d20-4642-8289-98f87d9893dc"), ScopeName.of("task.delete"))
        ))));

        final AuthorizationRequestCheckOutput result = checkAuthorizationRequestUsecase.execute(
            CheckAuthorizationRequestInput.of(
                "30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com",
                "https://example.com/callback",
                "task.read file.delete"
            )
        );

        assertEquals(AuthorizationRequestCheckOutput.STATUS_INVALID_REDIRECT_URI, result.getStatus());
    }
}
