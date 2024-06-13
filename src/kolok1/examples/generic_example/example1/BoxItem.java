package kolok1.examples.generic_example.example1;

import java.util.Date;

public class BoxItem{
    private String name;
    private int age;
    private Date date;

    public BoxItem(String name, int age, int year, int month, int day) {
        this.name = name;
        this.age = age;
        this.date = new Date(year,month,day);
    }

    @Override
    public String toString() {
        return "BoxItem {" + "name: " + name + " age: " + age + " date: " + date;
    }
}
