package kotetsu.auth.application.util;

import kotetsu.auth.application.dto.data.ClientCredentialData;

public interface IGenerateClientCredentialPort {
    ClientCredentialData generate(final String clientCredentialToken);
}
