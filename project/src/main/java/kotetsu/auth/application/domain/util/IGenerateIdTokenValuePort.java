package kotetsu.auth.application.domain.util;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.value.IdTokenValue;

public interface IGenerateIdTokenValuePort {
    IdTokenValue generate(IssuedIdTokenMeta meta, ExistingIdTokenCore idTokenCore);
}
