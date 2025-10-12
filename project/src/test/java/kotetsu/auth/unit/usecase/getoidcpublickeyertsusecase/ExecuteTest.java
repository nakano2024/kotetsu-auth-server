package kotetsu.auth.unit.usecase.getoidcpublickeyertsusecase;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.dto.output.OidcPublicKeyCertsOutput;
import kotetsu.auth.application.dto.util.OidcPublicKeyJwk;
import kotetsu.auth.application.exception.OidcPublicKeyJwksNullRuntimeException;
import kotetsu.auth.application.usecase.GetOidcPublicKeyCertsUsecase;
import kotetsu.auth.application.util.IFetchOidcPublicKeyJwksPort;

@ExtendWith(MockitoExtension.class)
public class ExecuteTest {
    @Mock
    private IFetchOidcPublicKeyJwksPort fetchOidcPublicKeyJwksPort;

    @InjectMocks
    private GetOidcPublicKeyCertsUsecase getOidcPublicKeyCertsUsecase;

    @Test
    public void canGetOidcPublicKeyCertsIfJwksExists() {
        when(fetchOidcPublicKeyJwksPort.fetch()).thenReturn(Optional.of(List.of(
            new OidcPublicKeyJwk(
                "test-kid-1",
                "RSA",
                "RS256",
                "sig",
                "test-n-value-1",
                "AQAB"
            ),
            new OidcPublicKeyJwk(
                "test-kid-2",
                "RSA",
                "RS256", 
                "sig",
                "test-n-value-2",
                "AQAB"
            )
        )));

        assertDoesNotThrow(() -> {
            final OidcPublicKeyCertsOutput result = getOidcPublicKeyCertsUsecase.execute();
            
            assertEquals(2, result.getKeys().size());
            
            assertEquals("test-kid-1", result.getKeys().get(0).getKid());
            assertEquals("RSA", result.getKeys().get(0).getKty());
            assertEquals("RS256", result.getKeys().get(0).getAlg());
            assertEquals("sig", result.getKeys().get(0).getUse());
            assertEquals("test-n-value-1", result.getKeys().get(0).getN());
            assertEquals("AQAB", result.getKeys().get(0).getE());
            
            assertEquals("test-kid-2", result.getKeys().get(1).getKid());
            assertEquals("RSA", result.getKeys().get(1).getKty());
            assertEquals("RS256", result.getKeys().get(1).getAlg());
            assertEquals("sig", result.getKeys().get(1).getUse());
            assertEquals("test-n-value-2", result.getKeys().get(1).getN());
            assertEquals("AQAB", result.getKeys().get(1).getE());
        });
    }

    @Test
    public void throwExceptionIfJwksIsNull() {
        when(fetchOidcPublicKeyJwksPort.fetch()).thenReturn(Optional.empty());

        OidcPublicKeyJwksNullRuntimeException exception = assertThrows(OidcPublicKeyJwksNullRuntimeException.class, () -> {
            getOidcPublicKeyCertsUsecase.execute();
        });

        assertEquals("OidcPublicKeyJwksはNULLが許容されません。", exception.getMessage());
    }
}