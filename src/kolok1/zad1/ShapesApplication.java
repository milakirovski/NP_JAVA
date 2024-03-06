package kolok1.zad1;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class ShapesApplication {

    private List<Canvas> canvases;

    public ShapesApplication() {
        this.canvases = new ArrayList<>();
    }

    public int readCanvases(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int squareCounter = 0;

        while (scanner.hasNextLine()) {
            String canvas = scanner.nextLine();

            String[] canvasParts = canvas.split("\\s+");
            String id = canvasParts[0];

            ArrayList<Integer> sides = new ArrayList<>();

            for (int i = 1; i < canvasParts.length; i++) {
                sides.add(Integer.parseInt(canvasParts[i]));
                squareCounter++;
            }
            canvases.add(new Canvas(id, sides));
        }
        return squareCounter;
    }


    public void printLargestCanvasTo(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);

        System.out.println(canvases.stream()
                .max(Comparator.comparing(el -> el.parameter()))
                .orElse(null));

        printWriter.flush();
    }
}