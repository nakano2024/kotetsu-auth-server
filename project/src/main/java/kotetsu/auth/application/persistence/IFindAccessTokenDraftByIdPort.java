package kotetsu.auth.application.persistence;

import java.util.UUID;
import kotetsu.auth.application.dto.data.AccessTokenDraftData;

public interface IFindAccessTokenDraftByIdPort {
    AccessTokenDraftData findById(final UUID id);
}