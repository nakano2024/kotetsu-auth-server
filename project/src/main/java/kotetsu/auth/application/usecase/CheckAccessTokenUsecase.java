package kotetsu.auth.application.usecase;

import java.util.Date;
import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.entity.IssuedAccessToken;
import kotetsu.auth.application.domain.entity.ResourceOwnerValidator;
import kotetsu.auth.application.domain.repository.IFetchAudienceClientPort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenCorePort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenPort;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.repository.IFetchResourceOwnerValidator;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.value.AccessTokenValue;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.dto.input.CheckAccessTokenInput;
import kotetsu.auth.application.dto.output.AccessTokenCheckOutput;
import kotetsu.auth.application.exception.ExistingAccessTokenCoreNullRuntimeException;
import kotetsu.auth.application.exception.InputNullRuntimeException;

public class CheckAccessTokenUsecase {
    private final IFetchCurrentDatePort fetchCurrentDatePort;
    private final IFetchExistingAccessTokenPort fetchExistingAccessTokenPort;
    private final IFetchExistingAccessTokenCorePort fetchExistingAccessTokenCorePort;
    private final IFetchResourceOwnerValidator fetchResourceOwnerValidatorPort;

    public CheckAccessTokenUsecase(
        final IFetchCurrentDatePort fetchCurrentDatePort,
        final IFetchExistingAccessTokenPort fetchExistingAccessTokenPort,
        final IFetchExistingAccessTokenCorePort fetchExistingAccessTokenCorePort,
        final IFetchResourceOwnerValidator fetchResourceOwnerValidatorPort,
        final IFetchRequesterClientPort fetchRequesterClientPort,
        final IFetchAudienceClientPort fetchAudienceClientPort
    ) {
        this.fetchCurrentDatePort = fetchCurrentDatePort;
        this.fetchExistingAccessTokenPort = fetchExistingAccessTokenPort;
        this.fetchExistingAccessTokenCorePort = fetchExistingAccessTokenCorePort;
        this.fetchResourceOwnerValidatorPort = fetchResourceOwnerValidatorPort;
    }

    public AccessTokenCheckOutput execute(final CheckAccessTokenInput input) {
        if (input == null) {
            throw new InputNullRuntimeException();
        }

        final Optional<ExistingAccessToken> accessTokenOptional = fetchExistingAccessTokenPort.fetch(AccessTokenValue.of(input.getToken()));

        if (accessTokenOptional.isEmpty()) {
            return AccessTokenCheckOutput.of(
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
        }

        final ExistingAccessToken accessToken = accessTokenOptional.get();

        final Date currentDate = fetchCurrentDatePort.fetch();
        if (accessToken.getDuration().getExpiredAt().hasExpired(currentDate)) {
            return AccessTokenCheckOutput.of(
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
        }

        final ExistingAccessTokenCore accessTokenCore = fetchExistingAccessTokenCorePort.fetch(Key.of(accessToken.getLinkedAccessTokenCoreKey().getValue()))
            .orElseThrow(() -> new ExistingAccessTokenCoreNullRuntimeException());
        
        final ResourceOwnerValidator resourceOwnerValidator = fetchResourceOwnerValidatorPort.fetch(Key.of(accessTokenCore.getSubject().getValue()))
            .orElseThrow(() -> new ExistingAccessTokenCoreNullRuntimeException());

        if (!resourceOwnerValidator.isActive()) {
            return AccessTokenCheckOutput.of(
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
        }

        return AccessTokenCheckOutput.of(
            true,
            accessTokenCore.getScopeList().toScopeListToken(),
            accessTokenCore.getRequesterClientId().getValue(),
            accessToken.getDuration().getIssuedAt().getUnixSec(),
            accessToken.getDuration().getDifferenceSec(),
            accessTokenCore.getSubject().getValue(),
            accessTokenCore.getRelatedAudienceList().toStringList(),
            accessTokenCore.getIssuer().getValue(),
            IssuedAccessToken.TOKEN_TYPE
        );
    }
}
