package kotetsu.auth.repository;

import kotetsu.auth.application.domain.entity.PendingIdTokenCore;
import kotetsu.auth.application.domain.repository.IStorePendingIdTokenCorePort;

public class PendingIdTokenCoreRepository implements IStorePendingIdTokenCorePort {
    
    @Override
    public void store(PendingIdTokenCore tokenBody) {
        
    }
}