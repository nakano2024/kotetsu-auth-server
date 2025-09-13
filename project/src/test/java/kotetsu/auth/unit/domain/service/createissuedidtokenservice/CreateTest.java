package kotetsu.auth.unit.domain.service.createissuedidtokenservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.IssuedIdToken;
import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.service.CreateIssuedIdTokenService;
import kotetsu.auth.application.domain.util.IGenerateIdTokenValuePort;
import kotetsu.auth.application.domain.value.IdTokenValue;

@ExtendWith(MockitoExtension.class)
public class CreateTest {
    @Mock
    private IGenerateIdTokenValuePort generateIdTokenValuePort;

    @InjectMocks
    private CreateIssuedIdTokenService createIssuedIdTokenService;

    @Test
    public void createTest() {
        IssuedIdTokenMeta meta = mock(IssuedIdTokenMeta.class);
        ExistingIdTokenCore idTokenCore = mock(ExistingIdTokenCore.class);
        
        when(generateIdTokenValuePort.generate(meta, idTokenCore)).thenReturn(IdTokenValue.of("id-token-value"));

        IssuedIdToken token = createIssuedIdTokenService.create(meta, idTokenCore);

        assertEquals("id-token-value", token.getValue().getValue());
    }
}