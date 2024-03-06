package kolok1.zad2;

public class Circle extends Shape{

    public Circle(ShapeType type, int value) {
        super(type, value);
    }

    @Override
    public double area() {
        return value * value * Math.PI;
    }

    @Override
    public String toString() {
        return type + " " + value;
    }
}
