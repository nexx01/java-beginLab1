package reflectionLesson;

import org.junit.jupiter.api.Test;
import reflectionLesson.model.User;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflectionLesson1 {

    @Test
    void name() {

        User user = new User(25L, "Ivan");
        Class<? extends User> aClass = user.getClass();
        Class<User> userClass = User.class;

        System.out.println(aClass == userClass);
        assertTrue(aClass == userClass);
    }

    @Test
    void testConstructors() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<User> constructor = User.class.getConstructor(Long.class, String.class);
        User petr = constructor.newInstance(12L, "Petr");
        System.out.println(petr);
    }

    @Test
    void testFields() throws IllegalAccessException {
        User user = new User(13L, ":Ferdor");
        Field[] declaredFields = User.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object value = field.get(user);
            System.out.println(value);
        }
    }
    @Test
    void testFieldsSuperClass() throws IllegalAccessException {
        User user = new User(13L, ":Ferdor");
        Field[] declaredFields = User.class.getSuperclass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object value = field.get(user);
            System.out.println(field.getModifiers());
            System.out.println(Modifier.isPrivate(field.getModifiers()));
            System.out.println(value);
        }
    }

    @Test
    void testMethods() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User(14L, "Grisha");
        Class<? extends User> aClass = user.getClass();
        Method method = aClass.getDeclaredMethod("getName");

        System.out.println(method.invoke(user));
    }

    @Test
    void testMethods2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User(15L, "Vasilii");
        Method method = user.getClass().getDeclaredMethod("setName", String.class);
        method.invoke(user, "Sveta");
        System.out.println(user);

    }

    @Test
    void minAgeAnnotation() {
        User user = new User(15L, "Kirill", 19);

        System.out.println();
    }

    private class Test1 {


    }

    private static class Test3 {
    }


    private enum Test2 {
        ONE,TWO;
    }

}
