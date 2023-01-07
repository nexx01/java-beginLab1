package dateTime;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

//1. Создать объект LocalDateTime, представляющий собой
//дату 25.06.2020 19:47. Используя этот объект, создать другой объект LocalDateTime,
//представляющий собой дату через 3 месяца после сегодняшней.
//Вывести на консоль содержащиеся в нем объеты LocalDate and LocalTime.
//
//2. Создать объект LocalDate, представляющий собой сегодняшнюю
//дату. Преобразовать дату в строку вида "05.05.2017". Вывести эту строку на
//консоль.
//
//3. Дана строка вида "26-03-1968T09:24". Получить объект LocalDateTime, представляющий
//собой дату, полученную из этой строки.
//
//4. Использовать LocalDateTime из предыдущего задания, преобразовать его
//в объект типа Instant, указав тайм зону "America/Chicago". Вывести количество
//прошедших миллисекунд.
//
//4. Создать объект LocalDate, представляющий собой сегодняшнюю
//дату. Создать объект LocalDate, представляющий собой дату
//07.07.2018. Используя созданные объекты, найти количество дней между
//этими двумя датами.
//
//5. Даны два объекта LocalDate из предыдущего задания. Подсчитать количество
//секунд между полуночью первой даты и полуночью второй даты.
//
//6. Создать объект Instant. Вывести его на консоль. Затем создать
//объект ZonedDateTime на основании предыдущего объекта с тайм зоной "Africa/Cairo".
//
//7. Написать свою реализацию интерфейса TemporalAdjuster, которая бы
//прибавляла к дате 42 дня
public class DateTimePractice {

    /**
     * 1. Создать объект java.time.LocalDateTime, представляющий собой
     * дату 25.06.2020 19:47. Используя этот объект, создать другой объект LocalDateTime,
     * представляющий собой дату через 3 месяца после сегодняшней.
     * Вывести на консоль содержащиеся в нем объеты LocalDate and LocalTime.
     */
    @Test
    void task1() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 25, 19, 47);
        LocalDateTime result = dateTime.plusMonths(3);
        System.out.println("Localdate - " + result.toLocalDate());
        System.out.println("LocalTime - " + result.toLocalTime());
    }

    /**
     * 2. Создать объект LocalDate, представляющий собой сегодняшнюю
     * дату. Преобразовать дату в строку вида "05.05.2017". Вывести эту строку на
     * консоль.
     */
    @Test
    void task2() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM");

        String format = dateTimeFormatter.format(now);

        System.out.println("formating now - " + format);
    }

    /**
     * 3. Дана строка вида "26-03-1968T09:24". Получить объект LocalDateTime, представляющий
     * собой дату, полученную из этой строки.
     */
    @Test
    void task3() {
        String formateDateTime = "26-03-1968T09:24";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm");
        LocalDateTime result = LocalDateTime.parse(formateDateTime, formatter);
        System.out.println(result);

    }

    /**
     * 4. Использовать LocalDateTime из предыдущего задания, преобразовать его
     * в объект типа Instant, указав тайм зону "America/Chicago". Вывести количество
     * прошедших миллисекунд.
     */
    @Test
    void task4() {
        String formateDateTime = "26-03-1968T09:24";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(formateDateTime, formatter);

        Instant instant = localDateTime.toInstant(ZoneId.of("America/Chicago").getRules().getOffset(localDateTime));
        System.out.println(instant);
        System.out.println(instant.toEpochMilli());

    }

    /**
     * 5. Даны два объекта LocalDate из предыдущего задания. Подсчитать количество
     * секунд между полуночью первой даты и полуночью второй даты.
     */
    @Test
    void task5() {
        LocalDate now = LocalDate.now();
        LocalDate prevDate = LocalDate.of(2018, 7, 7);

//        LocalDateTime of = LocalDateTime.of(now, LocalTime.MIDNIGHT);

        LocalDateTime localDateTimeNow = now.atStartOfDay();
        LocalDateTime localDateTimePrev = prevDate.atStartOfDay();
        Duration duration = Duration.between(localDateTimePrev, localDateTimeNow);
        System.out.println(duration.getSeconds());
    }

  /**  6. Создать объект Instant. Вывести его на консоль. Затем создать
объект ZonedDateTime на основании предыдущего объекта с тайм зоной "Africa/Cairo".
*/
  @Test
  void task6() {
      Instant instant = Instant.now();
      System.out.println("Time by UTC -> " + instant);
      ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Africa/Cairo"));

      System.out.println(zonedDateTime);
  }

    /**
     * 7. Написать свою реализацию интерфейса TemporalAdjuster, которая бы
     * прибавляла к дате 42 дня
     */
    @Test
    void task6_1() {
        TemporalAdjuster temporalAdjuster = t -> t.plus(42, ChronoUnit.DAYS);
        LocalDate localDate = LocalDate.now();

        LocalDate result = localDate.with(temporalAdjuster);
        System.out.println(result);
    }

    /**
     * Написать свою реализацию интерфейса TemporalAdjuster,
     * которая бы изменяла дату на ближайшее (в днях) 1 января
     */
    @Test
    void task11() {
        LocalDateTime of = LocalDateTime.of(LocalDate.of(2018, 8, 27), LocalTime.MIDNIGHT);
        System.out.println("LocalDateTime from LocalDate - 2018.0.27 -> " + of);

        LocalDateTime result = adjustToNearestYearTask6(of);
        System.out.println("Result -> " + result);

        class Helper {

        }
    }

    private static LocalDateTime adjustToNearestYearTask6(LocalDateTime localDateTime) {
        return localDateTime.with(it -> {
            Temporal currentYear = it.with(TemporalAdjusters.firstDayOfYear());
            Temporal nextYear = it.with(TemporalAdjusters.firstDayOfNextYear());
            long betweenCurrentYear = ChronoUnit.DAYS.between(currentYear, it);
            long betweenNextYear = ChronoUnit.DAYS.between(it, nextYear);

            return betweenCurrentYear < betweenNextYear ? currentYear : nextYear;
        });
    }

    /**
     * To obtain the date of the Sunday after 2017-07-08
     */
    @Test
    void task7() {
        LocalDate localDate = LocalDate.of(2017, 07, 8);
        LocalDate nextSunday = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        String expected = "2017-07-09";

        assertEquals(expected, nextSunday.toString());
    }


    /**
     * obtain the last day of the current month
     */
    @Test
    void task8() {
        LocalDate localDate = LocalDate.of(2023, 1, 1);
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());

        String expected = "2023-01-31";

        assertEquals(expected, lastDayOfMonth.toString());
    }


    /**
     * obtain the date that's 14 days after 2017-07-08 using the Temporal.with() method
     */
    @Test
    void task9() {
        LocalDate localDate = LocalDate.of(2017, 7, 8);
        TemporalAdjuster temporalAdjuster = t -> t.plus(Period.ofDays(14));
        LocalDate result = localDate.with(temporalAdjuster);

        String forteenDaysAfterDate = "2017-07-22";

        assertEquals(forteenDaysAfterDate, result.toString());
    }

    /**
     * obtain the date of the working day right after 2017-07-08 by defining our own TemporalAdjuster implementations
     * ofDateAdjuster() static factory method:
     */
    @Test
    void task10() {
        LocalDate localDate = LocalDate.of(2017, 7, 8);
        TemporalAdjuster temporalAdjuster = NEXT_WORKING_DAY;
        LocalDate result = localDate.with(temporalAdjuster);

        assertEquals("2017-07-10", result.toString());
    }

    static TemporalAdjuster NEXT_WORKING_DAY = TemporalAdjusters.ofDateAdjuster(date -> {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int daysToAdd;
        if (dayOfWeek == DayOfWeek.FRIDAY) {
            daysToAdd = 3;
        } else if (dayOfWeek == DayOfWeek.SATURDAY) {
            daysToAdd = 2;
        } else daysToAdd = 1;
        return date.plusDays(daysToAdd);
    });

    /**
     * write a custom TemporalAdjuster that obtains the working
     * day after 2017-07-08 by implementing the TemporalAdjuster interface:
     */
    @Test
    void task12() {
        class CustomTrmporalAdjuster implements TemporalAdjuster {

            @Override
            public Temporal adjustInto(Temporal temporal) {
                DayOfWeek dayOfWeek = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                System.out.println("temporal.get(ChronoField.DAY_OF_WEEK) -> " + dayOfWeek);

                int daysToAdd;

                if (dayOfWeek == DayOfWeek.FRIDAY) {
                    daysToAdd = 3;
                } else if (dayOfWeek == DayOfWeek.SATURDAY) {
                    daysToAdd = 2;
                } else daysToAdd = 1;
                return temporal.plus(daysToAdd, ChronoUnit.DAYS);
            }
        }

        LocalDate localDate = LocalDate.of(2017, 7, 8);
        CustomTrmporalAdjuster customTrmporalAdjuster = new CustomTrmporalAdjuster();
        LocalDate nextWorkingDay = localDate.with(customTrmporalAdjuster);

        assertEquals("2017-07-10", nextWorkingDay.toString());

    }
}
