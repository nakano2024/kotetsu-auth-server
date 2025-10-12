package kotetsu.auth.application.domain.util;

import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.value.InternalAuthTokenValue;

public interface IGenerateInternalAuthTokenValudPort {
    InternalAuthTokenValue generate(PendingInternalAuthToken pendingInternalAuthToken);
}
