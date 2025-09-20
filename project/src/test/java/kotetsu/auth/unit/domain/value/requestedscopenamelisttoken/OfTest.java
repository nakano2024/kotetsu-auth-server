package kotetsu.auth.unit.domain.value.requestedscopenamelisttoken;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import kotetsu.auth.application.domain.exception.RequestedScopeNameListTokenValidationException;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;

public class OfTest {
    public static Stream<Arguments> getValidArguments() {
        return Stream.of(
            Arguments.of(
                "task.read task.write",
                "task.read task.write"
            ),
            Arguments.of(
                "task.read task.write task.delete",
                "task.read task.write task.delete"
            ),
            Arguments.of(
                "task.read",
                "task.read"
            ),
            Arguments.of(
                "openid",
                "openid"
            )
        );
    }

    @ParameterizedTest
    @MethodSource("getValidArguments")
    public void objectIsConstructedWithMatchingArgument(final String argument, final String expected) {
        assertDoesNotThrow(() -> {
            RequestedScopeNameListToken requestedScopeNameListToken = RequestedScopeNameListToken.of(argument);
            assertEquals(expected, requestedScopeNameListToken.getValue());   
        });
    }

    public static Stream<Arguments> getInvalidArguments() {
        return Stream.of(
            Arguments.of(""),
            Arguments.of(" "),
            Arguments.of("  "),
            Arguments.of("　"),
            Arguments.of("あ "),
            Arguments.of("task.read "),
            Arguments.of("  task.read"),
            Arguments.of(" task.read "),
            Arguments.of("  task.read"),
            Arguments.of("task.read  ")
        );
    }

    @ParameterizedTest
    @MethodSource("getInvalidArguments")
    public void throwExceptionIfArgumentIsInvalid(final String argument) {
        final Exception exception = assertThrows(RequestedScopeNameListTokenValidationException.class, () -> {
            RequestedScopeNameListToken.of(argument);  
        });

        assertEquals(RequestedScopeNameListTokenValidationException.class, exception.getClass());
    }
}