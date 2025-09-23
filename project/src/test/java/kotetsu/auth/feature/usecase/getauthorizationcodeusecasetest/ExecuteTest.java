package kotetsu.auth.feature.usecase.getauthorizationcodeusecasetest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kotetsu.auth.application.usecase.GetAuthorizationCodeUsecase;

@SpringBootTest
public class ExecuteTest {
    @Autowired
    GetAuthorizationCodeUsecase getAuthorizationCodeUsecase;

    @Test
    public void canGetAuthorizationCode() {
        
    }
}