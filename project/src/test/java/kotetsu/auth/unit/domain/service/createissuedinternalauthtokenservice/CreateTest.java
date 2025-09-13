package kotetsu.auth.unit.domain.service.createissuedinternalauthtokenservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kotetsu.auth.application.domain.entity.IssuedInternalAuthToken;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.service.CreateIssuedInternalAuthTokeService;
import kotetsu.auth.application.domain.util.IGenerateInternalAuthTokenValudPort;
import kotetsu.auth.application.domain.value.InternalAuthTokenValue;

@ExtendWith(MockitoExtension.class)
public class CreateTest {
    @Mock
    private IGenerateInternalAuthTokenValudPort generateInternalAuthTokenValudPort;

    @InjectMocks
    private CreateIssuedInternalAuthTokeService createIssuedInternalAuthTokeService;

    @Test
    public void createTest() {
        PendingInternalAuthToken pendingToken = mock(PendingInternalAuthToken.class);
        
        when(generateInternalAuthTokenValudPort.generate(pendingToken)).thenReturn(InternalAuthTokenValue.of("internal-auth-token-value"));

        IssuedInternalAuthToken token = createIssuedInternalAuthTokeService.create(pendingToken);

        assertEquals("internal-auth-token-value", token.getValue().getValue());
    }
}