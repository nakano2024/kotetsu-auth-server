package kotetsu.auth.unit.domain.service.checkcodevertifilerservice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.service.CheckCodeVerifilerService;
import kotetsu.auth.application.domain.util.IConvertToCodeChallengePort;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeVerifier;

@ExtendWith(MockitoExtension.class)
public class IsValidTest {
    @Mock
    private IConvertToCodeChallengePort convertToCodeChallengePort;

    @InjectMocks
    private CheckCodeVerifilerService checkCodeVerifilerService;

    @Test
    public void returnTrueIfCodeChallengeMatchToCodeVerifier() {
        final AuthorizationCodeVerifier inputCodeVerifier = AuthorizationCodeVerifier.of("code-verifier");
        final AuthorizationCodeChallenge codeChallenge = AuthorizationCodeChallenge.of("code-challenge");

        when(convertToCodeChallengePort.convert(inputCodeVerifier)).thenReturn(AuthorizationCodeChallenge.of("code-challenge"));
        assertTrue(checkCodeVerifilerService.isValid(inputCodeVerifier, codeChallenge));
    }

    @Test
    public void returnFalseIfCodeChallengeNotMatchToCodeVerifier() {
        final AuthorizationCodeVerifier inputCodeVerifier = AuthorizationCodeVerifier.of("code-verifier");
        final AuthorizationCodeChallenge codeChallenge = AuthorizationCodeChallenge.of("code-challenge");

        when(convertToCodeChallengePort.convert(inputCodeVerifier)).thenReturn(AuthorizationCodeChallenge.of("defferent-code-challenge"));
        assertFalse(checkCodeVerifilerService.isValid(inputCodeVerifier, codeChallenge));
    }
}