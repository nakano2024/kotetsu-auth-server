package kotetsu.auth.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kotetsu.auth.application.dto.util.OidcPublicKeyJwk;
import kotetsu.auth.application.util.IFetchOidcPublicKeyJwksPort;
import kotetsu.auth.dto.util.OidcPublicKeyJwkJson;
import kotetsu.auth.dto.util.OidcPublicKeyJwkWrapperJson;

public class OidcPublicKeyJwksFetcher implements IFetchOidcPublicKeyJwksPort {
    @Override
    public Optional<List<OidcPublicKeyJwk>> fetch() {
        try {
            final String publicKeyJwksJsonRaw = System.getenv("OIDC_PUBLIC_KEYS");

            if (publicKeyJwksJsonRaw == null) {
                throw new IllegalArgumentException("環境変数からの秘密鍵取得に失敗しました。");
            }

            final ObjectMapper objectMapper = new ObjectMapper();
            final OidcPublicKeyJwkWrapperJson wrapperJson = objectMapper.readValue(publicKeyJwksJsonRaw, OidcPublicKeyJwkWrapperJson.class);
            final List<OidcPublicKeyJwkJson> publicKeyJwkJsons = wrapperJson.getKeys();
            return Optional.of(
                publicKeyJwkJsons.stream()
                    .map(keyJwkJson -> new OidcPublicKeyJwk(
                        keyJwkJson.getKid(),
                        keyJwkJson.getKty(),
                        keyJwkJson.getAlg(),
                        keyJwkJson.getUse(),
                        keyJwkJson.getN(),
                        keyJwkJson.getE()
                    ))
                    .collect(Collectors.toList())
            );
        }
        catch(JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
