package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.Key;
import lombok.Getter;

public class AudienceClient {
    @Getter
    private final Key key;

    @Getter
    private final ClientId clientId;

    private AudienceClient(final Key key, final ClientId clientId) {
        this.key = key;
        this.clientId = clientId;
    }

    public static AudienceClient of(final Key key, final ClientId clientId) {
        final AudienceClient audienceClient = new AudienceClient(key, clientId);

        return audienceClient;
    }
}
