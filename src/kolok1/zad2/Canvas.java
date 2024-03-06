package kolok1.zad2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Canvas implements Comparable<Canvas>{

    private String id;
    private ArrayList<Shape> shapeList;

    public Canvas(String id, ArrayList<Shape> shapeList) {
        this.id = id;
        this.shapeList = shapeList;
    }

    public int numberOfShapes(){
        return shapeList.size();
    }

    public long numberOfCircles(){
        return shapeList.stream().filter(s -> s.type.equals(ShapeType.C)).count();
    }

    public long numberOfSquares(){
        return shapeList.stream().filter(s -> s.type.equals(ShapeType.S)).count();
    }

    public double maxArea(){
        return Collections.max(shapeList).area();
    }

    public double minArea(){
        return Collections.min(shapeList).area();
    }

    public double sumArea(){
        return shapeList.stream()
                .mapToDouble(Shape::area)
                .sum();
    }

    public double averageArea(){
        return sumArea() / numberOfShapes();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f", getId(), numberOfShapes(), numberOfCircles(), numberOfSquares(), minArea(), maxArea(), averageArea());
    }

    @Override
    public int compareTo(Canvas o) {
        return Double.compare(this.sumArea(), o.sumArea());
    }
}
