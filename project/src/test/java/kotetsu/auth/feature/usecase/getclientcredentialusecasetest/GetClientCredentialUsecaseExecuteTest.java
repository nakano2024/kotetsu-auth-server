package kotetsu.auth.feature.usecase.getclientcredentialusecasetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.dto.input.ClientCredentialOutput;
import kotetsu.auth.application.dto.input.GetClientCredentialInput;
import kotetsu.auth.application.usecase.GetClientCredentialUsecase;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class GetClientCredentialUsecaseExecuteTest {

    @Autowired
    private GetClientCredentialUsecase getClientCredentialUsecase;

    @Test
    @Sql("/test-data/get-client-credential-test.sql")
    public void canGetClientCredential() {
        assertDoesNotThrow(() -> {
            ClientCredentialOutput output = getClientCredentialUsecase.execute(
                GetClientCredentialInput.of("94e435a9-414f-34bd-5e6d-2e59678b09a6.kotetsu.com")
            );

            ClientCredentialOutput expected = ClientCredentialOutput.of(
                "94e435a9-414f-34bd-5e6d-2e59678b09a6.kotetsu.com",
                "$2a$08$XdggoeA6f2uvZ07PlhHtqeRq6f/PXB3WnFsWmlUp.DNlEKwDdzCEC"
            );

            assertEquals(output, expected);
        });
    }
}