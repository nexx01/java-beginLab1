package patternLesson;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
////
//1. Написать программу, проверяющую, является ли введённая
//        строка адресом почтового ящика.
//        В названии почтового ящика разрешить использование только
//        букв, цифр и нижних подчёркиваний, при этом оно должно
//        начинаться с буквы.
//        Возможные домены верхнего уровня: .org .com
//
//        2. Написать программу, выполняющую поиск в строке
//        шестнадцатеричных чисел, записанных по правилам Java,
//        с помощью регулярных выражений и выводящую их на страницу.
//
//        3. Написать программу, выполняющую поиск в строке всех тегов
//        абзацев, в т.ч. тех, у которых есть параметры, например <p id
//        ="p1">,
//        и замену их на простые теги абзацев <p>.
public class PatternPractice {

    /**
     * 1. Написать программу, проверяющую, является ли введённая
     * строка адресом почтового ящика.
     * В названии почтового ящика разрешить использование только
     * букв, цифр и нижних подчёркиваний, при этом оно должно
     * начинаться с буквы.
     * Возможные домены верхнего уровня: .org .com
     */
    @Test
    void name() {
        String regex = "[a-zA-Z]\\w+@\\w{3,}\\.(org|com)";
        var input = "asdd@gmdd.com";
        System.out.println(Pattern.matches(regex,input));
    }

    /**
     * 2. Написать программу, выполняющую поиск в строке
     * шестнадцатеричных чисел, записанных по правилам Java,
     * с помощью регулярных выражений и выводящую их на страницу.
     */
    @Test
    void name1() {
        var regex = "0[Xx][0-9A-Fa-f]+";
        var input = "sfsdff 0Xff efvfdv 0x12 ddf 0XAB dfdf 1x24F adf d 0TaC";

        Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(input);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    /**
     * 3. Написать программу, выполняющую поиск в строке всех тегов
     * абзацев, в т.ч. тех, у которых есть параметры, например <p id
     * ="p1">,
     * и замену их на простые теги абзацев <p>.
     */
    @Test
    void name2() {
        String regex = "(<p .+?>)(.+?</p>)";
        String input = "<p> dsf sfeferfe</p> fedfree<b> </b>esrfef<p id\"p1\">fddfd</p>" +
                "sfsdfd <p>sefs<p id=\"p2\"> 123 fsdf</p> dfvfd";

        System.out.println(input.replaceAll(regex, "<p>$2"));
    }



}
