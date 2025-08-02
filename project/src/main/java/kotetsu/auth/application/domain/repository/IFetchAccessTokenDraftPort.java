package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.AccessTokenBody;
import kotetsu.auth.application.domain.value.Id;

public interface IFetchAccessTokenDraftPort {
    AccessTokenBody fetch(Id code);
}
