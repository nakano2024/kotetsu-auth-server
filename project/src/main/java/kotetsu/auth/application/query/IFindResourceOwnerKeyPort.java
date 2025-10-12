package kotetsu.auth.application.query;

import java.util.Optional;

import kotetsu.auth.application.dto.data.ResourceOwnerKeyData;

public interface IFindResourceOwnerKeyPort {
    Optional<ResourceOwnerKeyData> findByResourceOwnerKey(String key);
}
