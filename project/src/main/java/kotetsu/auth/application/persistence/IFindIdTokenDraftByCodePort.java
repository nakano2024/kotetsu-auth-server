package kotetsu.auth.application.persistence;

import java.util.UUID;

import kotetsu.auth.application.dto.data.IdTokenDraftData;

public interface IFindIdTokenDraftByCodePort {
    IdTokenDraftData findByCode(final UUID id);
}