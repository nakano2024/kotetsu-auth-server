package kotetsu.auth.application.util;

import kotetsu.auth.application.dto.data.IdTokenDraftData;

public interface IGenerateIdTokenFromDraftPort {
    String generate(final IdTokenDraftData idTokenDraft);
}