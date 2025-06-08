package kotetsu.auth.application.persistence;

import kotetsu.auth.application.dto.store.AuthorizationCodeStore;

public interface IStoreAuthorizationCodePort {
    String store(AuthorizationCodeStore authorizationCode);
}
