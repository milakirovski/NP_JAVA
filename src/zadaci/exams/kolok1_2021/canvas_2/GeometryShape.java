package exams.kolok1_2021.canvas_2;

public class GeometryShape implements Comparable<GeometryShape>{

    protected ShapeType type;
    protected int x;

    public GeometryShape(int x,ShapeType type) {
        this.x = x;
        this.type = type;
    }


    public double getArea(){
        return 0.0;
    }

    public ShapeType getType() {
        return type;
    }

    @Override
    public int compareTo(GeometryShape o) {
        return Double.compare(this.getArea(), o.getArea());
    }
}
