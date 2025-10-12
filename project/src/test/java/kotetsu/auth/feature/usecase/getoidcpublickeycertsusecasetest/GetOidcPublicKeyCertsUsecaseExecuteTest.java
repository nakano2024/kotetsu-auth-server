package kotetsu.auth.feature.usecase.getoidcpublickeycertsusecasetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.dto.output.OidcPublicKeyCertsOutput;
import kotetsu.auth.application.dto.output.OidcPublicKeyJwkOutput;
import kotetsu.auth.application.usecase.GetOidcPublicKeyCertsUsecase;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class GetOidcPublicKeyCertsUsecaseExecuteTest {
    @Autowired
    GetOidcPublicKeyCertsUsecase oidcPublicKeyCertsUsecase;

    @Test
    public void canGetTokenWithAuthorizationCode() {
        assertDoesNotThrow(() -> {
            OidcPublicKeyCertsOutput output = oidcPublicKeyCertsUsecase.execute();
            for (OidcPublicKeyJwkOutput jwk : output.getKeys()) {
                System.out.println("kid: " + jwk.getKid());
                System.out.println("alg: " + jwk.getAlg());
                System.out.println("kty: " + jwk.getKty());
                System.out.println("n: "   + jwk.getN());
                System.out.println("e: "   + jwk.getE());
                System.out.println("----------------------");
            }
        });
    }
}