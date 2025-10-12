package kotetsu.auth.unit.domain.value.duration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;

public class GetDifferenceSecTest {
    public static Stream<Arguments> provideDatesAndExpected() {
        return Stream.of(
            Arguments.of(2025, 9, 12, 0, 0, 0, 0, 2025, 9, 12, 1, 0, 0, 0, 3600L),
            Arguments.of(2025, 9, 12, 0, 0, 0, 0, 2025, 9, 12, 1, 0, 0, 1, 3600L), // 秒単位より小さい差分は切り捨てられることを確認
            Arguments.of(2025, 9, 12, 10, 10, 5, 0, 2025, 9, 13, 10, 10, 5, 0, 86400L), // より差分が大きい場合で確認
            Arguments.of(2025, 9, 12, 0, 0, 0, 0, 2025, 9, 12, 0, 0, 1, 0, 1L) // 秒単位の差分が正確に計算可能か確認
        );
    }

    @ParameterizedTest
    @MethodSource("provideDatesAndExpected")
    public void returnCorrectResult(
        int issuedAtYear,
        int issuedAtMonth,
        int issuedAtDay,
        int issuedAtHour,
        int issuedAtMinute,
        int issuedAtSecond,
        int issuedAtNanoOfSecond,
        int expiredAtYear,
        int expiredAtMonth,
        int expiredAtDay,
        int expiredAtHour,
        int expiredAtMinute,
        int expiredAtSecond,
        int expiredAtNanoOfSecond,
        Long expected
    ) {
        IssuedAt issuedAt = IssuedAt.of(Date.from(
            LocalDateTime.of(
                issuedAtYear,
                issuedAtMonth,
                issuedAtDay,
                issuedAtHour,
                issuedAtMinute,
                issuedAtSecond,
                issuedAtNanoOfSecond
            )
            .atZone(ZoneId.of("UTC")).toInstant()
        ));
        ExpiredAt expiredAt = ExpiredAt.of(Date.from(
            LocalDateTime.of(
                expiredAtYear,
                expiredAtMonth,
                expiredAtDay,
                expiredAtHour,
                expiredAtMinute,
                expiredAtSecond,
                expiredAtNanoOfSecond
            ).atZone(ZoneId.of("UTC")).toInstant()
        ));
        
        Duration duration = Duration.of(issuedAt, expiredAt);

        assertEquals(expected, duration.getDifferenceSec());
    }
}
