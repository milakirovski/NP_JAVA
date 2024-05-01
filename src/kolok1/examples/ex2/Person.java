package kolok1.examples.ex2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Person implements Comparable<Person>{
    private String name;
    private int age;


    public Person(String line) {
       this.name = Arrays.stream(line.split("\\s+")).toList().get(0);
       this.age = Integer.parseInt(Arrays.stream(line.split("\\s+")).toList().get(1));
    }


    @Override
    public String toString() {
        return name + " " + age;
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age, o.age);
    }
}
