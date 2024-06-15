package kolok1.zad6;

import java.util.*;
import java.util.stream.Collectors;

enum Color {
    RED, GREEN, BLUE
}

interface Scalable{

    /**
     * за соодветно зголемување/намалување на формата за дадениот фактор
     */
    void scale(float scaleFactor);
}

interface Stackable{

    /**
     * кој враќа тежината на формата (се пресметува како плоштина на соодветната форма)
     */
    float weight();
}

class Shape implements Stackable, Scalable, Comparable<Shape>{
    protected String  id;
    protected Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }


    @Override
    public float weight() {
        return 0;
    }

    @Override
    public void scale(float scaleFactor) {}

    @Override
    public int compareTo(Shape o) {
        return Float.compare(this.weight(), o.weight());
    }
}

class Canvas {
    private List<Shape> shapeList;

    public Canvas() {
        this.shapeList = new ArrayList<>();
    }

    public void add(String id, Color color, float radius){
        shapeList.add(new Circle(id,color,radius));
        sortList(shapeList);
    }

    public void add(String id, Color color, float width, float height){
        shapeList.add(new Rectangle(id,color,width,height));
        sortList(shapeList);
    }

    public void sortList(List<Shape> shapeList){
        this.shapeList = shapeList.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    public void scale(String id, float scaleFactor){
        Shape shape = shapeList.stream()
                .filter(f -> f.id.equals(id))
                .findAny().orElse(null);
        if(shape != null){
            shape.scale(scaleFactor);
            sortList(shapeList);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        shapeList.forEach(shape -> sb.append(shape.toString()));
        return sb.toString();
    }
}

class Circle extends Shape implements Stackable, Scalable{

    private float r;
    public Circle(String id, Color color, float r) {
        super(id, color);
        this.r = r;
    }

    @Override
    public void scale(float scaleFactor) {
        this.r = r * scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (r * r * Math.PI);
    }

    @Override
    public String toString() {
        return String.format("C :%-5s%-10s%10.2f\n", id, color,weight());
    }
}

class Rectangle extends Shape implements Stackable, Scalable{
    private float heigth;
    private float width;

    public Rectangle(String id, Color color, float width,float heigth) {
        super(id, color);
        this.heigth = heigth;
        this.width = width;
    }

    @Override
    public void scale(float scaleFactor) {
        this.heigth = heigth * scaleFactor;
        this.width = width * scaleFactor;
    }

    @Override
    public float weight() {
        return heigth * width;
    }

    @Override
    public String toString() {
        return String.format("R: %-5s%-10s%10.2f\n", id, color, weight());
    }
}


public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}
