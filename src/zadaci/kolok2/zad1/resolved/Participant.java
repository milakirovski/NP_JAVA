package kolok2.zad1.resolved;

public class Participant {
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

    public static Participant parse(String line) {
        String[] parts = line.split(";");
        String city = parts[0];
        String code = parts[1];
        String name = parts[2];
        int age = Integer.parseInt(parts[3]);

        return new Participant(city, code, name, age);
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
}
