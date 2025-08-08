package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.Key;
import lombok.Getter;

public class RequesterClient {
    @Getter
    private Key key;

    @Getter
    private ClientId clientId;
}
