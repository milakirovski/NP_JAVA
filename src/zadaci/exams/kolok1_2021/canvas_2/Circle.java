package exams.kolok1_2021.canvas_2;

public class Circle extends GeometryShape{


    public Circle(int x, ShapeType type) {
        super(x,type);
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(x, 2);
    }

    @Override
    public ShapeType getType() {
        return super.getType();
    }

}
