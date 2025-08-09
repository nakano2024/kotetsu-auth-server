package kotetsu.auth.repository;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;
import kotetsu.auth.application.domain.repository.IStorePendingAccessTokenCorePort;

public class PendingAccessTokenCoreRepository implements IStorePendingAccessTokenCorePort {
    
    @Override
    public void store(PendingAccessTokenCore accessTokenBody) {
        
    }
}