package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.AccessTokenDraft;
import kotetsu.auth.application.domain.value.Id;

public interface IFetchAccessTokenDraftPort {
    AccessTokenDraft fetch(Id code);
}
