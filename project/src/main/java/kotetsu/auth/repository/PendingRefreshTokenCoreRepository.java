package kotetsu.auth.repository;

import kotetsu.auth.application.domain.entity.PendingRefreshTokenCore;
import kotetsu.auth.application.domain.repository.IStorePendingRefreshTokenCorePort;

public class PendingRefreshTokenCoreRepository implements IStorePendingRefreshTokenCorePort {
    
    @Override
    public void store(PendingRefreshTokenCore refreshTokenBody) {
        
    }
}