package kotetsu.auth.unit.usecase.getauthorizationcodeusecase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;
import kotetsu.auth.application.domain.entity.PendingIdTokenCore;
import kotetsu.auth.application.domain.entity.PendingRefreshTokenCore;
import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.RequestedAuthorization;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchRequestedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.repository.IStorePendingAccessTokenCorePort;
import kotetsu.auth.application.domain.repository.IStorePendingIdTokenCorePort;
import kotetsu.auth.application.domain.repository.IStorePendingRefreshTokenCorePort;
import kotetsu.auth.application.domain.repository.IStoreRequestedAuthorizationPort;
import kotetsu.auth.application.domain.service.CreateAuthorizationService;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.util.IFetchServerUrlPort;
import kotetsu.auth.application.domain.util.IGenerateUuidPort;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.ScopeName;
import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.exception.ClientNotPermittedScopesContainedException;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.InvalidScopeNameListTokenException;
import kotetsu.auth.application.exception.PermittedScopeListNullRuntimeException;
import kotetsu.auth.application.exception.RedirectUriDoseNotMatchException;
import kotetsu.auth.application.exception.RequestedScopeListNullRuntimeException;
import kotetsu.auth.application.exception.RequesterClientNotFoundRuntimeException;
import kotetsu.auth.application.usecase.GetAuthorizationCodeUsecase;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IFetchPermittedScopeListPort permittedScopeListPort;

    @Mock
    private IFetchRequestedScopeListPort fetchRequestedScopeListPort;

    @Mock
    private IFetchRequesterClientPort fetchRequeterClientPort;

    @Mock
    private IFetchServerUrlPort fetchServerUrlPort;

    @Mock
    private IStorePendingAccessTokenCorePort storeAccessTokenBodyPort;

    @Mock
    private IStorePendingIdTokenCorePort storeIdTokenBodyPort;

    @Mock
    private IStorePendingRefreshTokenCorePort storeRefreshTokenBodyPort;

    @Mock
    private IStoreRequestedAuthorizationPort storeAuthorizationPort;

    @Mock
    private CreateAuthorizationService createAuthorizationInformationService;

    @Mock
    private IGenerateUuidPort generateUuidPort;

    @Mock
    private IFetchCurrentDatePort fetchCurrentDatePort;

    @InjectMocks
    private GetAuthorizationCodeUsecase getAuthorizationCodeUsecase;

    @Test
    public void canGetAuthorizationCodeIfAllConditionIsSatisfied() {
        when(permittedScopeListPort.fetch(any())).thenReturn(Optional.of(PermittedScopeList.of(Set.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("36b4c06d-4921-ad84-21ed-c96d9945668a"), ScopeName.of("task.write")),
            Scope.of(Key.of("f6f170ea-1d20-4642-8289-98f87d9893dc"), ScopeName.of("task.delete")),
            Scope.of(Key.of("3da7e043-c147-fe02-5586-824b3ade58bf"), ScopeName.of("task"))
        ))));

        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.of(RequestedScopeList.of(List.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("36b4c06d-4921-ad84-21ed-c96d9945668a"), ScopeName.of("task.write"))
        ))));

        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("52a95015-f708-41d3-8f46-f6c5c2ebc8e6"),
            ClientId.of("2G3qRGhp2lBU2N5kXahQgBGx2H"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        when(fetchServerUrlPort.fetch()).thenReturn("https://auth.example.com");
        
        when(generateUuidPort.generate())
            .thenReturn("f6d8d529-de2f-fe1d-7df0-c85746bf247b")
            .thenReturn("df8f55e1-2498-2b65-c610-b7145ccbc53f")
            .thenReturn("32972779-f6e2-60be-44cc-dbb2b7dc8fd2");
        
        when(fetchCurrentDatePort.fetch()).thenReturn(Date.from(
            LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        ));
        
        when(createAuthorizationInformationService.create(
            any(AuthorizationCodeChallenge.class),
            any(AccessType.class),
            any(LinkedAccessTokenCoreKey.class),
            any(LinkedIdTokenCoreKey.class),
            any(LinkedRefreshTokenCoreKey.class),
            any(IssuedAt.class)
        )).thenReturn(RequestedAuthorization.of(
            AuthorizationCode.of(
                AuthorizationCodeValue.of("LfYEaydDWOCIINJopILoPl"),
                AuthorizationCodeChallenge.of("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"),
                ExpiredAt.of(Date.from(
                    LocalDateTime.of(2025, 9, 13, 12, 10, 1).atZone(ZoneId.of("UTC")).toInstant()
                ))
            ),
            AccessType.of("offline"),
            LinkedAccessTokenCoreKey.of("f6d8d529-de2f-fe1d-7df0-c85746bf247b"),
            LinkedIdTokenCoreKey.of("df8f55e1-2498-2b65-c610-b7145ccbc53f"),
            LinkedRefreshTokenCoreKey.of("32972779-f6e2-60be-44cc-dbb2b7dc8fd2")
        ));

        assertDoesNotThrow(() -> {
            AuthorizationCodeOutput result = getAuthorizationCodeUsecase.execute(GetAuthorizationCodeInput.of(
            "990a9655-8ace-499c-11db-503fbc63b0e2",
            "LfYEaydDWOCIINJopILoPl",
            "https://example.com/callback",
            "task.read task.write",
            "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
            "sqro48PJQ7L3teGAkN8J",
            "offline"
            ));
            
            ArgumentCaptor<PendingAccessTokenCore> accessTokenCoreCaptor = ArgumentCaptor.forClass(PendingAccessTokenCore.class);
            verify(storeAccessTokenBodyPort).store(accessTokenCoreCaptor.capture());
            assertEquals("f6d8d529-de2f-fe1d-7df0-c85746bf247b", accessTokenCoreCaptor.getValue().getKey().getValue());
            assertEquals("990a9655-8ace-499c-11db-503fbc63b0e2", accessTokenCoreCaptor.getValue().getSubject().getValue());
            assertEquals("https://auth.example.com", accessTokenCoreCaptor.getValue().getIssuer().getValue());
            assertEquals(Set.of(
                Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
                Scope.of(Key.of("36b4c06d-4921-ad84-21ed-c96d9945668a"), ScopeName.of("task.write"))
            ), accessTokenCoreCaptor.getValue().getRequestedScopeList().getScopes());
            
            ArgumentCaptor<PendingIdTokenCore> idTokenCoreCaptor = ArgumentCaptor.forClass(PendingIdTokenCore.class);
            verify(storeIdTokenBodyPort).store(idTokenCoreCaptor.capture());
            assertEquals("df8f55e1-2498-2b65-c610-b7145ccbc53f", idTokenCoreCaptor.getValue().getKey().getValue());
            assertEquals("2G3qRGhp2lBU2N5kXahQgBGx2H", idTokenCoreCaptor.getValue().getAudience().getValue());
            assertEquals("https://auth.example.com", idTokenCoreCaptor.getValue().getIssuer().getValue());
            assertEquals("sqro48PJQ7L3teGAkN8J", idTokenCoreCaptor.getValue().getNonce().getValue());
            assertEquals("990a9655-8ace-499c-11db-503fbc63b0e2", idTokenCoreCaptor.getValue().getSubject().getValue());
            
            ArgumentCaptor<PendingRefreshTokenCore> refreshTokenCoreCaptor = ArgumentCaptor.forClass(PendingRefreshTokenCore.class);
            verify(storeRefreshTokenBodyPort).store(refreshTokenCoreCaptor.capture());
            assertEquals("32972779-f6e2-60be-44cc-dbb2b7dc8fd2", refreshTokenCoreCaptor.getValue().getKey().getValue());
            assertEquals("f6d8d529-de2f-fe1d-7df0-c85746bf247b", refreshTokenCoreCaptor.getValue().getLinkedAccessTokenCoreId().getValue());
            assertEquals("df8f55e1-2498-2b65-c610-b7145ccbc53f", refreshTokenCoreCaptor.getValue().getLinkedIdTokenCoreId().getValue());
            
            ArgumentCaptor<AuthorizationCodeChallenge> challengeCaptor = ArgumentCaptor.forClass(AuthorizationCodeChallenge.class);
            ArgumentCaptor<AccessType> accessTypeCaptor = ArgumentCaptor.forClass(AccessType.class);
            ArgumentCaptor<LinkedAccessTokenCoreKey> accessTokenKeyCaptor = ArgumentCaptor.forClass(LinkedAccessTokenCoreKey.class);
            ArgumentCaptor<LinkedIdTokenCoreKey> idTokenKeyCaptor = ArgumentCaptor.forClass(LinkedIdTokenCoreKey.class);
            ArgumentCaptor<LinkedRefreshTokenCoreKey> refreshTokenKeyCaptor = ArgumentCaptor.forClass(LinkedRefreshTokenCoreKey.class);
            ArgumentCaptor<IssuedAt> issuedAtCaptor = ArgumentCaptor.forClass(IssuedAt.class);
            
            verify(createAuthorizationInformationService).create(
                challengeCaptor.capture(),
                accessTypeCaptor.capture(),
                accessTokenKeyCaptor.capture(),
                idTokenKeyCaptor.capture(),
                refreshTokenKeyCaptor.capture(),
                issuedAtCaptor.capture()
            );

            assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", challengeCaptor.getValue().getValue());
            assertEquals("offline", accessTypeCaptor.getValue().getValue());
            assertEquals("f6d8d529-de2f-fe1d-7df0-c85746bf247b", accessTokenKeyCaptor.getValue().getValue());
            assertEquals("df8f55e1-2498-2b65-c610-b7145ccbc53f", idTokenKeyCaptor.getValue().getValue());
            assertEquals("32972779-f6e2-60be-44cc-dbb2b7dc8fd2", refreshTokenKeyCaptor.getValue().getValue());
            assertEquals(Date.from(
                LocalDateTime.of(2025, 9, 13, 12, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
            ), issuedAtCaptor.getValue().getValue());

            ArgumentCaptor<RequestedAuthorization> authorizationCaptor = ArgumentCaptor.forClass(RequestedAuthorization.class);
            verify(storeAuthorizationPort).store(authorizationCaptor.capture());
            assertEquals("LfYEaydDWOCIINJopILoPl", authorizationCaptor.getValue().getAuthorizationCode().getValue().getValue());
            assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", authorizationCaptor.getValue().getAuthorizationCode().getChallenge().getValue());
            assertEquals(Date.from(
                LocalDateTime.of(2025, 9, 13, 12, 10, 1).atZone(ZoneId.of("UTC")).toInstant()
            ), authorizationCaptor.getValue().getAuthorizationCode().getExpiredAt().getValue());
            assertEquals("offline", authorizationCaptor.getValue().getAccessType().getValue());
            assertEquals("f6d8d529-de2f-fe1d-7df0-c85746bf247b", authorizationCaptor.getValue().getLinkedAccessTokenCoreKey().getValue());
            assertEquals("df8f55e1-2498-2b65-c610-b7145ccbc53f", authorizationCaptor.getValue().getLinkedIdTokenCoreKey().getValue());
            assertEquals("32972779-f6e2-60be-44cc-dbb2b7dc8fd2", authorizationCaptor.getValue().getLinkedRefreshTokenCoreKey().getValue());

            assertEquals("LfYEaydDWOCIINJopILoPl", result.getCode());
        });
    }

    @Test
    public void throwExceptionIfInputIsNull() {
        InputNullRuntimeException exception = assertThrows(InputNullRuntimeException.class, () -> {
            getAuthorizationCodeUsecase.execute(null);
        });

        assertEquals("inputはnullが許容されていません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfRequesterClientIsEmpty() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.empty());

        RequesterClientNotFoundRuntimeException exception = assertThrows(RequesterClientNotFoundRuntimeException.class, () -> {
            getAuthorizationCodeUsecase.execute(GetAuthorizationCodeInput.of(
            "990a9655-8ace-499c-11db-503fbc63b0e2",
            "52a95015-f708-41d3-8f46-f6c5c2ebc8e6",
            "https://example.com/callback",
            "task.read task.write",
            "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
            "sqro48PJQ7L3teGAkN8J",
            "offline"
            ));
        });

        assertEquals("RequesterClientが見つかりません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfRedirectUriIsInvalid() {
        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("52a95015-f708-41d3-8f46-f6c5c2ebc8e6"),
            ClientId.of("2G3qRGhp2lBU2N5kXahQgBGx2H"),
            ClientRedirectUri.of("https://example.com/invalid-callback")
        )));

        RedirectUriDoseNotMatchException exception = assertThrows(RedirectUriDoseNotMatchException.class, () -> {
            getAuthorizationCodeUsecase.execute(GetAuthorizationCodeInput.of(
            "990a9655-8ace-499c-11db-503fbc63b0e2",
            "52a95015-f708-41d3-8f46-f6c5c2ebc8e6",
            "https://example.com/callback",
            "task.read task.write",
            "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
            "sqro48PJQ7L3teGAkN8J",
            "offline"
            ));
        });

        assertEquals("redirectUriが一致しません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfNotPermittedScopesAreRequested() {
        when(permittedScopeListPort.fetch(any())).thenReturn(Optional.of(PermittedScopeList.of(Set.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("36b4c06d-4921-ad84-21ed-c96d9945668a"), ScopeName.of("task.write")),
            Scope.of(Key.of("f6f170ea-1d20-4642-8289-98f87d9893dc"), ScopeName.of("task.delete")),
            Scope.of(Key.of("3da7e043-c147-fe02-5586-824b3ade58bf"), ScopeName.of("task"))
        ))));

        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.of(RequestedScopeList.of(List.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("d4003dd9-cce3-f2f7-7b52-0329cc8f929b"), ScopeName.of("file.delete"))
        ))));

        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("52a95015-f708-41d3-8f46-f6c5c2ebc8e6"),
            ClientId.of("2G3qRGhp2lBU2N5kXahQgBGx2H"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        ClientNotPermittedScopesContainedException exception = assertThrows(ClientNotPermittedScopesContainedException.class, () -> {
            getAuthorizationCodeUsecase.execute(GetAuthorizationCodeInput.of(
            "990a9655-8ace-499c-11db-503fbc63b0e2",
            "52a95015-f708-41d3-8f46-f6c5c2ebc8e6",
            "https://example.com/callback",
            "task.read task.write",
            "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
            "sqro48PJQ7L3teGAkN8J",
            "offline"
            ));
        });

        assertEquals("RequesterClientに許可されていないスコープが含まれています。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfRequestedScopeNameistNull() {
        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.empty());

        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("52a95015-f708-41d3-8f46-f6c5c2ebc8e6"),
            ClientId.of("2G3qRGhp2lBU2N5kXahQgBGx2H"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        RequestedScopeListNullRuntimeException exception = assertThrows(RequestedScopeListNullRuntimeException.class, () -> {
            getAuthorizationCodeUsecase.execute(GetAuthorizationCodeInput.of(
            "990a9655-8ace-499c-11db-503fbc63b0e2",
            "52a95015-f708-41d3-8f46-f6c5c2ebc8e6",
            "https://example.com/callback",
            "task.read task.write",
            "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
            "sqro48PJQ7L3teGAkN8J",
            "offline"
            ));
        });

        assertEquals("RequestedScopeListはnullが許容されません。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfRequesteScopeNameListTokenDoseNotMatcheRequestedScopeList() {
        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.of(RequestedScopeList.of(List.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("d4003dd9-cce3-f2f7-7b52-0329cc8f929b"), ScopeName.of("file.delete"))
        ))));

        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("52a95015-f708-41d3-8f46-f6c5c2ebc8e6"),
            ClientId.of("2G3qRGhp2lBU2N5kXahQgBGx2H"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        InvalidScopeNameListTokenException exception = assertThrows(InvalidScopeNameListTokenException.class, () -> {
            getAuthorizationCodeUsecase.execute(GetAuthorizationCodeInput.of(
            "990a9655-8ace-499c-11db-503fbc63b0e2",
            "52a95015-f708-41d3-8f46-f6c5c2ebc8e6",
            "https://example.com/callback",
            "task.read task.write notexist.read",
            "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
            "sqro48PJQ7L3teGAkN8J",
            "offline"
            ));
        });

        assertEquals("存在しないスコープが含まれてます。", exception.getMessage());
    }

    @Test
    public void throwExceptionIfPermittedScopeListNull() {
        when(permittedScopeListPort.fetch(any())).thenReturn(Optional.empty());

        when(fetchRequestedScopeListPort.fetch(any())).thenReturn(Optional.of(RequestedScopeList.of(List.of(
            Scope.of(Key.of("4b4f03a8-12bd-f6eb-f69b-0cfa8ec8b23c"), ScopeName.of("task.read")),
            Scope.of(Key.of("d4003dd9-cce3-f2f7-7b52-0329cc8f929b"), ScopeName.of("file.delete"))
        ))));

        when(fetchRequeterClientPort.fetch(any())).thenReturn(Optional.of(RequesterClient.of(
            Key.of("52a95015-f708-41d3-8f46-f6c5c2ebc8e6"),
            ClientId.of("2G3qRGhp2lBU2N5kXahQgBGx2H"),
            ClientRedirectUri.of("https://example.com/callback")
        )));

        PermittedScopeListNullRuntimeException exception = assertThrows(PermittedScopeListNullRuntimeException.class, () -> {
            getAuthorizationCodeUsecase.execute(GetAuthorizationCodeInput.of(
            "990a9655-8ace-499c-11db-503fbc63b0e2",
            "52a95015-f708-41d3-8f46-f6c5c2ebc8e6",
            "https://example.com/callback",
            "task.read task.write",
            "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
            "sqro48PJQ7L3teGAkN8J",
            "offline"
            ));
        });

        assertEquals("PermittedScopeListはnullが許容されません。", exception.getMessage());
    }
}