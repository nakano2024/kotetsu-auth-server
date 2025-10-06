package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchRequestedScopeListPort;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.dto.input.CheckAuthorizationRequestInput;
import kotetsu.auth.application.dto.output.AuthorizationRequestCheckOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.PermittedScopeListNullRuntimeException;
import kotetsu.auth.application.exception.RequestedScopeListNullRuntimeException;

@Component
public class CheckAuthorizationRequestUsecase {

    private final IFetchPermittedScopeListPort permittedScopeListPort;
    private final IFetchRequestedScopeListPort fetchRequestedScopeListPort;
    private final IFetchRequesterClientPort fetchRequeterClientPort;

    public CheckAuthorizationRequestUsecase(
        final IFetchPermittedScopeListPort permittedScopeListPort,
        final  IFetchRequestedScopeListPort fetchRequestedScopeListPort,
        final IFetchRequesterClientPort fetchRequeterClientPort
    ) {
        this.permittedScopeListPort = permittedScopeListPort;
        this.fetchRequestedScopeListPort = fetchRequestedScopeListPort;
        this.fetchRequeterClientPort = fetchRequeterClientPort;
    }

    public AuthorizationRequestCheckOutput execute(final CheckAuthorizationRequestInput input) {
        if (input == null) {
            throw new InputNullRuntimeException();
        }

        final RequesterClient requesterClient = fetchRequeterClientPort.fetch(ClientId.of(input.getClientId()))
            .orElse(null);
        if (requesterClient == null) {
            return AuthorizationRequestCheckOutput.of(AuthorizationRequestCheckOutput.STATUS_CLIENT_NOT_FOUND);
        }

        if(!requesterClient.getRedirectUri().equals(ClientRedirectUri.of(input.getRedirectUri()))) {
            return AuthorizationRequestCheckOutput.of(AuthorizationRequestCheckOutput.STATUS_INVALID_REDIRECT_URI);
        }

        final RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(RequestedScopeNameListToken.of(input.getScopeListToken()));
        
        final RequestedScopeList requestedScopeList = fetchRequestedScopeListPort.fetch(requestedScopeNameList)
            .orElseThrow(() -> new RequestedScopeListNullRuntimeException());

        if (!requestedScopeList.matchesRequestedScopeNameList(requestedScopeNameList)) {
            return AuthorizationRequestCheckOutput.of(AuthorizationRequestCheckOutput.STATUS_INVALID_REDIRECT_URI);
        }

        final PermittedScopeList permittedScopeList =  permittedScopeListPort.fetch(Key.of(requesterClient.getKey().getValue()))
            .orElseThrow(() -> new PermittedScopeListNullRuntimeException());

        if(!permittedScopeList.containsAll(requestedScopeList.getScopes())) {
            return AuthorizationRequestCheckOutput.of(AuthorizationRequestCheckOutput.STATUS_INVALID_REDIRECT_URI);
        }

        return AuthorizationRequestCheckOutput.of(AuthorizationRequestCheckOutput.STATUS_OK);
    }
}
