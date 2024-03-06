package kolok1.zad2;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class ShapesApplication {

    private List<Canvas> canvasList;
    private final double maxArea;

    /*
     најголемата дозволена плоштина на секоја форма поединечно
     */
    public ShapesApplication(double maxArea) {
        this.canvasList = new ArrayList<>();
        this.maxArea = maxArea;
    }

    // add in CanvasApp only if the requirement is fulfilled
    public void addCanvasIntoApp(Canvas canvas) throws IrregularCanvasException {
        if(canvas.maxArea() > maxArea){
            throw new IrregularCanvasException(canvas.getId(), maxArea);
        }
        canvasList.add(canvas);
    }

    public void readCanvases (InputStream inputStream) throws IrregularCanvasException {

        Scanner input = new Scanner(inputStream);

        while(input.hasNextLine()){
            String line = input.nextLine();

            String[] lineParts = line.split("\\s+");
            String canvasId = lineParts[0];
            ArrayList<Shape> shapeArrayList = new ArrayList<>();

            for (int i = 1; i < lineParts.length; i+=2){
                String typeString = lineParts[i];
                int value = Integer.parseInt(lineParts[i+1]);

                if(typeString.equals("S")){
                    shapeArrayList.add(new Square(ShapeType.S, value));

                }else if(typeString.equals("C")){
                    shapeArrayList.add(new Circle(ShapeType.C, value));
                }
            }
            try {
                addCanvasIntoApp(new Canvas(canvasId, shapeArrayList));
            } catch (IrregularCanvasException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void printCanvases (OutputStream os){
        PrintWriter printWriter = new PrintWriter(os);

       canvasList.stream().sorted(Collections.reverseOrder()).forEach(System.out::println);

       printWriter.flush();
    }


}
