package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ResourceOwnerValidator;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchResourceOwnerValidator {
    Optional<ResourceOwnerValidator> fetch(Key key);
}
