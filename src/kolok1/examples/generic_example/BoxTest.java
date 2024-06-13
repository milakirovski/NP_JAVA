package kolok1.examples.generic_example;

import java.util.stream.IntStream;

public class BoxTest {

    public static void main(String[] args) {
        Box<Toy> box = new Box<>();

        IntStream.range(0,100)
                .forEach(i -> new Toy());

        IntStream.range(0,103)
                .forEach(s -> System.out.println(box.drawElement()));
    }
}
