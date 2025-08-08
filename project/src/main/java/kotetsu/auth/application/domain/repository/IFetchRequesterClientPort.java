package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchRequesterClientPort {
    RequesterClient fetch(Key key);
}
