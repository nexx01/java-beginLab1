package serialisation;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;

public class SerialisationTEst {

    @Test
    void name() throws IOException {
        Path path = Path.of("resources", "student.out");

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            Person sergey = new Person(26, "Sergey");
            objectOutputStream.writeObject(sergey);
        }
    }

    @Test
    void deserialisationTest() throws IOException, ClassNotFoundException {
        Path path = Path.of("resources", "student.out");
        try (ObjectInputStream bufferedInputStream = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            Person o = (Person)bufferedInputStream.readObject();
            System.out.println(o);
        }


    }
}

class Person implements Serializable {
    private static final long serialVersionUID = -3272822353956750476L;
    private int age;
    private String firstName;

    public Person(int age, String firstName) {
        this.age = age;
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}