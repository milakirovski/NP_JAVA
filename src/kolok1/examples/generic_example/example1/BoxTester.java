package kolok1.examples.generic_example.example1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoxTester {

    public static void main(String[] args) {
        Box<Integer> intBox1 = new Box<>(12);
        Box<Integer> intBox2 = new Box<>(13);
        Box<String> stringBox1 = new Box<>("Lampion");
        Box<String> stringBox2 = new Box<>("Zvezda");

        BoxItem boxItem1 = new BoxItem("BoxItem1",3,2024,05,11);
        BoxItem boxItem2 = new BoxItem("BoxItem2",2,2025,05,11);

        List<Box<Integer>> intItems = new ArrayList<>();
        List<Box<String>> stringItems = new ArrayList<>();
        List<BoxItem> boxItems = new ArrayList<>();

        intItems.add(intBox1);
        intItems.add(intBox2);

        stringItems.add(stringBox1);
        stringItems.add(stringBox2);

        boxItems.add(boxItem1);
        boxItems.add(boxItem2);

        intItems.stream().mapToInt(i -> Integer.parseInt(i.toString())).forEach(i -> System.out.printf("%d ",i));
        System.out.println();
        stringItems.forEach(i -> System.out.printf("%s ",i));
        System.out.println();
        boxItems.forEach(i -> System.out.println(i.toString()));

    }

}
