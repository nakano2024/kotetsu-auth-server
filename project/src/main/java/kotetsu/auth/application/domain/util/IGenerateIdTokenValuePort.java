package kotetsu.auth.application.domain.util;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.IdTokenMeta;
import kotetsu.auth.application.domain.value.IdTokenValue;

public interface IGenerateIdTokenValuePort {
    IdTokenValue generate(IdTokenMeta meta, ExistingIdTokenCore core);
}
