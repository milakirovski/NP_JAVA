package auditoriski.aud5_Generic;

import java.util.stream.IntStream;

public class BoxTester {

    public static void main(String[] args) {
        Box box = new Box();

        IntStream.range(0,20).forEach(i -> box.add(new Toy("Toy " + i, "type " + i, i, i * 1.00)));

        System.out.println(box);

        IntStream.range(0,25).forEach(i -> System.out.println(box.draw()));
    }
}
