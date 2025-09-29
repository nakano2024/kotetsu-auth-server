package kotetsu.auth.feature.usecase.getauthorizationcodeusecasetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.dto.input.GetAuthorizationCodeInput;
import kotetsu.auth.application.dto.output.AuthorizationCodeOutput;
import kotetsu.auth.application.usecase.GetAuthorizationCodeUsecase;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FeatturedGetAuthorizationCodeUsecaseExecuteTest {
    @Autowired
    GetAuthorizationCodeUsecase getAuthorizationCodeUsecase;

    @Test
    @Sql("/test-data/get-authorization-code-test.sql")
    
    public void canGetAuthorizationCode() {
        assertDoesNotThrow(() -> {
            AuthorizationCodeOutput output = getAuthorizationCodeUsecase.execute(GetAuthorizationCodeInput.of(
                "e3714a8a-16d6-e645-218b-4276371791c2",
                "94e435a9-414f-34bd-5e6d-2e59678b09a6.kotetsu.com",
                "https://client.example.com/callback",
                "task.delete openid task.write",
                "0a3e4e7974ef324222ed6b1c5dba9d9ebb4ad35a541186e3c3d8b95cc9ff69c5", // verifier = 4Tgh3ds21g3s
                "SnGkLSwXfO",
                "offline"
            ));

            System.out.println("🚀code: " + output.getCode());
            System.out.println("🚀redirect_uri: " + output.getRedirectUri());
        });
    }
}