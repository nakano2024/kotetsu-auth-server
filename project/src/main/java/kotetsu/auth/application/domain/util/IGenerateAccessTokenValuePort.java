package kotetsu.auth.application.domain.util;

import kotetsu.auth.application.domain.value.AccessTokenValue;

public interface IGenerateAccessTokenValuePort {
    AccessTokenValue generate(int length);
}
