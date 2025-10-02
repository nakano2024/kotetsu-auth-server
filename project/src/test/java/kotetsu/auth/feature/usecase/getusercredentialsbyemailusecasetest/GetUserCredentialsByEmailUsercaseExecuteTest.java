package kotetsu.auth.feature.usecase.getusercredentialsbyemailusecasetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.dto.input.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.output.UserCredentialsOutput;
import kotetsu.auth.application.usecase.GetUserCredentialsByEmailUsecase;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class GetUserCredentialsByEmailUsercaseExecuteTest {

    @Autowired
    private GetUserCredentialsByEmailUsecase getUserCredentialsByEmailUsecase;

    @Test
    @Sql("/test-data/get-user-credential-test.sql")
    public void canGetUserCredential() {
        assertDoesNotThrow(() -> {
            final UserCredentialsOutput resultOutput = getUserCredentialsByEmailUsecase.execute(
                GetUserCredentialEmailInput.of("tanaka@example.com")
            );

            final UserCredentialsOutput expectedOutput = UserCredentialsOutput.of(
                "e3714a8a-16d6-e645-218b-4276371791c2",
                "田中太郎",
                "https://file.example.com/1b210543-8f30-762f-6467-ea1aeca1fabc",
                "tanaka@example.com",
                "$2a$08$4p9u7J2OBNzu.PTf1ZB9peWx1AKCNkUifWRUwFzZr24Vgq95WyfJ2"
            );

            assertEquals(expectedOutput, resultOutput);
        });
    }
}
