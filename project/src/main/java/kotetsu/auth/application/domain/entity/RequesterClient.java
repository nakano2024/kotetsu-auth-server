package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.domain.value.Key;
import lombok.Getter;

public class RequesterClient {
    @Getter
    private final Key key;

    @Getter
    private final ClientId clientId;

    @Getter
    private final ClientRedirectUri redirectUri;

    private RequesterClient(final Key key, final ClientId clientId, final ClientRedirectUri redirectUri) {
        this.key = key;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public static RequesterClient of(final Key key, final ClientId clientId, final ClientRedirectUri redirectUri) {
        final RequesterClient requesterClient = new RequesterClient(key, clientId, redirectUri);

        return requesterClient;
    }
}
