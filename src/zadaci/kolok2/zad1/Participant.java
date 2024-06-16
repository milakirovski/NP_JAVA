package kolok2.zad1;

import java.util.Objects;

public class Participant implements Comparable<Participant>{
    private String city;
    private String code;
    private String name;
    private int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return age == that.age && Objects.equals(city, that.city) && Objects.equals(code, that.code) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(Participant o) {
        return this.code.compareTo(o.code);
    }
}
