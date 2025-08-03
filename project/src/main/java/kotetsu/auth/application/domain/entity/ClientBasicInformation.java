package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientName;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.RedirectUri;
import lombok.Getter;

public class ClientBasicInformation {
    @Getter
    private Id id;

    @Getter
    private ClientId clientId;

    @Getter
    private ClientName name;

    @Getter
    private RedirectUri redirectUri;
}
