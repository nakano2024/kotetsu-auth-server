package kotetsu.auth.application.usecase;

import java.util.Date;

import kotetsu.auth.application.domain.entity.IssuedInternalAuthToken;
import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.repository.IFetchMeProfilePort;
import kotetsu.auth.application.domain.service.CreateIssuedInternalAuthTokeService;
import kotetsu.auth.application.domain.service.CreatePendingInternalAuthTokenService;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.dto.input.GetInternalTokenInput;
import kotetsu.auth.application.dto.output.IdTokenOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.MeProfileNotFoundIOException;

public class GetInternalTokenByEmailUsecase {
    private final IFetchMeProfilePort fetchMeProfilePort;
    private final CreatePendingInternalAuthTokenService createPendingInternalAuthTokenService;
    private final CreateIssuedInternalAuthTokeService createIssuedInternalAuthTokeService;
    private final IFetchCurrentDatePort fetchCurrentDatePort;

    public GetInternalTokenByEmailUsecase(
        final IFetchMeProfilePort fetchMeProfilePort,
        final CreatePendingInternalAuthTokenService createPendingInternalAuthTokenService,
        final CreateIssuedInternalAuthTokeService createIssuedInternalAuthTokeService,
        final IFetchCurrentDatePort fetchCurrentDatePort
    ) {
        this.fetchMeProfilePort = fetchMeProfilePort;
        this.createPendingInternalAuthTokenService = createPendingInternalAuthTokenService;
        this.createIssuedInternalAuthTokeService = createIssuedInternalAuthTokeService;
        this.fetchCurrentDatePort = fetchCurrentDatePort;
    }

    public IdTokenOutput getInternalToken(GetInternalTokenInput input) throws MeProfileNotFoundIOException {
        if (input == null) {
            throw new InputNullRuntimeException();
        }

        final Date currentDate = fetchCurrentDatePort.fetch();

        final MeProfile meProfile = fetchMeProfilePort.fetch(Key.of(input.getUserKey()))
            .orElseThrow(() -> new MeProfileNotFoundIOException());

        final PendingInternalAuthToken pendingInternalAuthToken = createPendingInternalAuthTokenService.create(
            meProfile,
            IssuedAt.of(currentDate)
        );

        final IssuedInternalAuthToken issuedInternalAuthToken = createIssuedInternalAuthTokeService.create(pendingInternalAuthToken);

        return IdTokenOutput.of(
            issuedInternalAuthToken.getValue().getValue(),
            IssuedInternalAuthToken.TOKEN_TYPE,
            pendingInternalAuthToken.getDuration().getDifferenceSec()
        );
    }
}
