package patternLesson;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class PatternDemo {

    @Test
    void name() {
        Pattern compile = Pattern.compile("\\d{3}");
        Matcher matcher = compile.matcher("123");
        System.out.println(matcher.matches());
        assertTrue(matcher.matches());
    }

    @Test
    void name1() {
        Pattern compile = Pattern.compile("\\d{3}");
        Matcher matcher = compile.matcher("1239");
        assertFalse(matcher.matches());
            }

    @Test
    void name3() {
        String phoneNumber = "+375 29) 898-12-13";
        Pattern compile = Pattern.compile("\\+375 \\(?\\d{2}\\) \\d{3}-\\d{2}-\\d{2}");
        Matcher matcher = compile.matcher(phoneNumber);

        assertTrue(matcher.matches());
    }

    @Test
    void name4() {
        String phoneNumber = " 29) 898-12-13";
        Pattern compile = Pattern.compile("(\\+375 )?\\(?\\d{2}\\) \\d{3}-\\d{2}-\\d{2}");
        Matcher matcher = compile.matcher(phoneNumber);

        assertTrue(matcher.matches());
    }

    @Test
    void name5() {
        String phoneNumber = "29) 898-12-13";
        boolean result = Pattern.matches("(\\+375 )?\\(?\\d{2}\\) \\d{3}-\\d{2}-\\d{2}",phoneNumber);

        assertTrue(result);
    }


    @Test
    void name6() {
        String phoneNumber = "29) 898-12-13";
        boolean result = phoneNumber.matches("(\\+375 )?\\(?\\d{2}\\) \\d{3}-\\d{2}-\\d{2}");

        assertTrue(result);
    }

    @Test
    void name7() {
        String phoneNumber = "dfdsf+375 (11) 898-12-13dfdsf+375 (29) 898-22-13dfdsf+375 (29) 898-12-13dfdsf+375 (33)" +
                " " +
                "898-12-13";

        String regex = "(\\+375 )?\\(?\\d{2}\\) \\d{3}-\\d{2}-\\d{2}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }


    @Test
    void name8() {
        String phoneNumber = "+375 (11) 898-12-13dfdsf+375 (29) 898-22-13dfdsf+375 (29) 898-12-13dfdsf+375 (33)" +
                " " +
                "898-12-13";

        String regex = "^(\\+375 )?\\(?\\d{2}\\) \\d{3}-\\d{2}-\\d{2}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    void name9() {
        String phoneNumber = "+375 (11) 898-12-13dfdsf+375 (29) 898-22-13dfdsf+375 (29) 898-12-13dfdsf+375 (33)" +
                " " +
                "898-12-13";

        String regex = "(\\+375 )?\\(?(\\d{2})\\) \\d{3}-\\d{2}-\\d{2}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        while (matcher.find()) {
            System.out.println(matcher.group(2));
        }
    }

    @Test
    void name10() {
        String phoneNumber = "+375 (11) 898-12-13dfdsf+375 (29) 898-22-29dfdsf+375 (29) 898-12-13dfdsf+375 (33)" +
                " " +
                "898-12-13";

        String regex = "(?:\\+375 )?\\(?(?<code>\\d{2})\\) \\d{3}-\\d{2}-\\1";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group(0));
            System.out.println(matcher.group("code"));
        }
    }

    @Test
    void name11() {
        String phoneNumber = "+375 (11) 898-12-13 dfdsf +375 (29) 898-22-29 dfdsf +375 (29) 898-12-13dfdsf+375 (33)" +
                " " +
                "898-12-13";

        String regex = "(?:\\+375 )?\\(?(?<code>\\d{2})\\) \\d{3}-\\d{2}-\\d{2}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuilder, "XXX");
        }
        matcher.appendTail(stringBuilder);
        System.out.println(stringBuilder.toString());
    }

    @Test
    void name12() {
        String phoneNumber = "+375 (11) 898-12-13 dfdsf +375 (29) 898-22-29 dfdsf +375 (29) 898-12-13dfdsf+375 (33)" +
                " " +
                "898-12-13";

        String regex = "(?:\\+375 )?\\(?(?<code>\\d{2})\\) (\\d{3})-(\\d{2})-(\\d{2})";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            String group1 = matcher.group(2);
            String group2 = matcher.group(3);
            String group3 = matcher.group(4);
            matcher.appendReplacement(stringBuilder, group1 + " " + group2 + " " + group3);
        }

        matcher.appendTail(stringBuilder);
        System.out.println(stringBuilder.toString());
    }

    @Test
    void name12_1() {
        String phoneNumber = "+375 (11) 898-12-13 dfdsf +375 (29) 898-22-29 dfdsf +375 (29) 898-12-13dfdsf+375 (33)" +
                " " +
                "898-12-13";

        String regex = "(?:\\+375 )?\\(?(?<code>\\d{2})\\) (\\d{3})-(\\d{2})-(\\d{2})";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuilder, "$2 $3 $4");
        }

        matcher.appendTail(stringBuilder);
        System.out.println(stringBuilder.toString());
        System.out.println(phoneNumber.replaceAll(regex,"$2 $3d $4 |"));
    }
}
