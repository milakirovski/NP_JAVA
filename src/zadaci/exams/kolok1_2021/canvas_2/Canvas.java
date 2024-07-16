package exams.kolok1_2021.canvas_2;

import java.util.*;
import java.util.stream.Collectors;

public class Canvas implements Comparable<Canvas>{
    private String id;
    private List<GeometryShape> shapes;

    public Canvas(String id, List<GeometryShape> shapes) {
        this.id = id;
        this.shapes = shapes;
    }

    public static Canvas createCanvas(String line, double maxArea) throws IrregularCanvasException {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<GeometryShape> shapes = new ArrayList<>();

        //5960017f C 30 S 15 S 588 C 25 C 14 S 14 S 17 C 19

        for (int i = 1; i < parts.length; i+=2) {
            char type = parts[i].charAt(0);
            int x = Integer.parseInt(parts[i+1]);

            if(type == 'C'){
                shapes.add(new Circle(x,ShapeType.Circle));
            }else if(type == 'S'){
                shapes.add(new Square(x,ShapeType.Square));
            }

        }

        Canvas newCanvas = new Canvas(id, shapes);

        if(!newCanvas.checkArea(maxArea)){
          throw new IrregularCanvasException(id,maxArea);
        }

        return newCanvas;
    }

    public boolean checkArea(double maxArea){
        return shapes.stream()
                .map(GeometryShape::getArea)
                .filter(i -> i > maxArea)
                .collect(Collectors.toCollection(ArrayList::new))
                .isEmpty();
    }

    public int totalCircles(){
        return (int) shapes.stream()
                .filter(i -> i.getType().equals(ShapeType.Circle))
                .count();
    }

    public int totalSquares(){
        return (int) shapes.stream()
                .filter(i -> i.getType().equals(ShapeType.Square))
                .count();
    }

    public DoubleSummaryStatistics statistics(){
        return shapes.stream().mapToDouble(GeometryShape::getArea).summaryStatistics();
    }
    @Override
    public String toString() {
        //ID total_shapes total_circles total_squares min_area max_area average_area
        return String.format(Locale.US,"%s %d %d %d %.2f %.2f %.2f",
                id,
                statistics().getCount(),
                totalCircles(),
                totalSquares(),
                statistics().getMin(),
                statistics().getMax(),
                statistics().getAverage());
    }

    @Override
    public int compareTo(Canvas o) {
        return Double.compare(statistics().getSum(), o.statistics().getSum());
    }
}
