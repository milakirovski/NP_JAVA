package kolok1.zad2;

enum ShapeType{
    S,
    C
}
public class Shape implements Comparable<Shape>{
    protected ShapeType type;
    protected int value;

    public Shape(ShapeType type, int value) {
        this.type = type;
        this.value = value;
    }

    public double area(){
        return 0.0;
    }

    @Override
    public int compareTo(Shape o) {
        return Double.compare(this.area(),o.area());
    }
}
