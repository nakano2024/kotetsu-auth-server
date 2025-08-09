package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingAuthorization;
import kotetsu.auth.application.domain.repository.IDeleteExistingAuthorization;
import kotetsu.auth.application.domain.repository.IFetchExistingAuthorizationPort;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;

public class ExistingAuthorizationRepository 
    implements IDeleteExistingAuthorization,
        IFetchExistingAuthorizationPort {
    
    @Override
    public void delete(ExistingAuthorization existingAuthorization) {
        
    }
    
    @Override
    public Optional<ExistingAuthorization> fetch(AuthorizationCodeValue authorizationCodeValue) {
        return Optional.empty();
    }
}