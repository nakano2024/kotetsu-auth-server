package kotetsu.auth.application.domain.util;

import kotetsu.auth.application.domain.value.RefreshTokenValue;

public interface IGenerateRefreshTokenValuePort {
    RefreshTokenValue generate();
}
