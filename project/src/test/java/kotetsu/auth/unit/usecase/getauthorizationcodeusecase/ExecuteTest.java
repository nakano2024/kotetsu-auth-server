package kotetsu.auth.unit.usecase.getauthorizationcodeusecase;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
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
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;
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
    }
}