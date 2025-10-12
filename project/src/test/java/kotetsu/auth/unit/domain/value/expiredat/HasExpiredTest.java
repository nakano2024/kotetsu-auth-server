package kotetsu.auth.unit.domain.value.expiredat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import kotetsu.auth.application.domain.value.ExpiredAt;

public class HasExpiredTest {
    public static Stream<Arguments> getExpiredDates() {
        return Stream.of(
            Arguments.of(2025, 9, 12, 0, 0, 1),
            Arguments.of(2025, 9, 12, 13, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getExpiredDates")
    public void returnTrueIfExpired(int year, int month, int day, int hour, int minute, int second) {
        ExpiredAt expiredAt = ExpiredAt.of(Date.from(
            LocalDateTime.of(2025, 9, 12, 0, 0, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        ));

        final Date today = Date.from(
            LocalDateTime.of(year, month, day, hour, minute, second).atZone(ZoneId.of("UTC")).toInstant()
        );

        assertTrue(expiredAt.hasExpired(today));
    }

    public static Stream<Arguments> getNotExpiredDates() {
        return Stream.of(
            Arguments.of(2025, 9, 12, 0, 0, 0),
            Arguments.of(2025, 9, 11, 23, 59, 59),
            Arguments.of(2025, 9, 11, 12, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getNotExpiredDates")
    public void returnFalseIfNotExpired(int year, int month, int day, int hour, int minute, int second) {
        ExpiredAt expiredAt = ExpiredAt.of(Date.from(
            LocalDateTime.of(2025, 9, 12, 0, 0, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        ));

        final Date today = Date.from(
            LocalDateTime.of(year, month, day, hour, minute, second).atZone(ZoneId.of("UTC")).toInstant()
        );

        assertFalse(expiredAt.hasExpired(today));
    }
}
