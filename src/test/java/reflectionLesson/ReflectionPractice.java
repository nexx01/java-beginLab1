package reflectionLesson;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

//
//1. Создать класс Car с полями String brand и String model.
//Создать аннотации @Table (принимает название схемы и таблицы
//в базе данных) и @Column (принимает название колонки в таблице
//базы данных). Пометить класс аннотацией @Table и поля
//аннотацией @Column. Написать программу, принимающую
//объект класс  Car c проинициализированными полями и
//составляющую запрос "INSERT" в виде строки на основании
//данных объекта.
//    Пример: дан объект Car car = new Car("Toyota", "Corolla");
//    Программа, принимающая этот объект, должна вывести в консоль строку:
//"INSERT INTO garage.car (model, brand) VALUES ('Toyota', 'Corolla');"
//
//2. Для получения данных программа должна
//использовать только get-методы (нельзя использовать
//значения приватных полей).
public class ReflectionPractice {

    @Test
    void main1() {
        Car car = new Car("Toyota", "Corolla");
        System.out.println(car);
        System.out.println(generationInsert(car));
        System.out.println(generationInsertWithMethod(car));
    }

    private String generationInsert(Car car) {
        String template = "INSERT INTO %s.%s (%s) VALUES (%s);";
        Table annotation = car.getClass().getAnnotation(Table.class);
        Field[] declaredFields = car.getClass().getDeclaredFields();
        String fieldNames = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Collumn.class))
                .sorted(Comparator.comparing(Field::getName))
                .map(f -> f.getAnnotation(Collumn.class))
                .map(Collumn::name)
                .collect(Collectors.joining(", "));

        String fieldValues = Arrays.stream(declaredFields)
                .peek(field->                        field.setAccessible(true))
                .filter(field -> field.isAnnotationPresent(Collumn.class))
                .sorted(Comparator.comparing(Field::getName))

//                .map(f -> f.getAnnotation(Collumn.class))
                .map(field-> {
                    try {
                        return String.valueOf(field.get(car));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "";
                    }
                })
                .map(value->"'"+value+"'")
                .collect(Collectors.joining(", "));

        return String.format(template, annotation.schema(), annotation.value(), fieldNames, fieldValues);
    }

    private String generationInsertWithMethod(Car car) {
        String template = "INSERT INTO %s.%s (%s) VALUES (%s);";
        Table annotation = car.getClass().getAnnotation(Table.class);
        Field[] declaredFields = car.getClass().getDeclaredFields();
        String fieldNames = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Collumn.class))
                .sorted(Comparator.comparing(Field::getName))
                .map(f -> f.getAnnotation(Collumn.class))
                .map(Collumn::name)
                .collect(Collectors.joining(", "));

        String fieldValues = Arrays.stream(declaredFields)
                .peek(field->                        field.setAccessible(true))
                .filter(field -> field.isAnnotationPresent(Collumn.class))
                .sorted(Comparator.comparing(Field::getName))
                .map(field -> getMethodName(car, field))
                .map(method -> {
                    try {
                        return method.invoke(car);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })

                .map(value->"'"+value+"'")
                .collect(Collectors.joining(", "));

        return String.format(template, annotation.schema(), annotation.value(), fieldNames, fieldValues);
    }

    private Method getMethodName(Car car, Field field) {
        String name = field.getName();
        try {
            return car.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Table {
    String schema() default "public";

    String value();
}



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Collumn {
    String name();
}

@Table("car")
class Car {
    @Collumn(name = "brand")
    private String brand;

    @Collumn(name = "model")
    private String model;

    public Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
