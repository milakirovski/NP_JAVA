package auditoriski.aud5_Generic;

public class Toy implements Drawable<Toy>{

    private String name;
    private String type;
    private int age; // za koja vozrast e nameneta igrackata
    private double price;

    public Toy(String name, String type, int age, double price) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.price = price;
    }

    @Override
    public Toy draw() {
        return null;
    }

    @Override
    public String toString() {
        return  "{name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                ", price=" + price + "}";
    }
}
