package multithreading;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ExampleRunner {

    @Test
    void name() {
        Example example = new Example(new ArrayList<>());

        List<Integer> list = example.getList();
        list.add(2);

        System.out.println(example.getList());

    }
}

class Example {
    private List<Integer> list;

    public Example(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> getList() {
        return new ArrayList<>(list);
    }
}
