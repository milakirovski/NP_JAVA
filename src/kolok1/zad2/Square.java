package kolok1.zad2;

public class Square extends Shape {

    public Square(ShapeType type, int value) {
        super(type, value);
    }

    @Override
    public double area() {
        return value * value;
    }

    @Override
    public String toString() {
        return type + " " + value;
    }
}
