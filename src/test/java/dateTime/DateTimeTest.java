package dateTime;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeTest {

    @Test
    void zoneDateTime() {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("zoneDateTime: " + now);

        Instant instant = now.toInstant();
        System.out.println("Instant from ZoneDateTime: " + instant);
        System.out.println("Second from instant: " + instant.toEpochMilli());

        ZonedDateTime plus = now.plus(1L, ChronoUnit.DAYS);
        System.out.println("zoneDateTime plus day: " + plus);
        System.out.println("is mutable?:" + (now.plus(1L, ChronoUnit.DAYS) == plus));

        ZonedDateTime truncatedTo = now.truncatedTo(ChronoUnit.DAYS);
        System.out.println("With truncate to: " + truncatedTo);

        LocalDateTime now1 = LocalDateTime.now(ZoneOffset.UTC);
        System.out.println(now1);

    }
}
