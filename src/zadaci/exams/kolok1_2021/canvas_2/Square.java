package exams.kolok1_2021.canvas_2;

public class Square extends GeometryShape{

    public Square(int x, ShapeType type) {
        super(x,type);
    }

    @Override
    public double getArea() {
        return Math.pow(x,2);
    }

    @Override
    public ShapeType getType() {
        return super.getType();
    }

}
