package exams.kolok1_2021.canvas_vlezna;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShapesApplication {
    private List<Canvas> canvasList;

    public ShapesApplication() {
        this.canvasList = new ArrayList<Canvas>();
    }

    public int readCanvases (InputStream inputStream){
        //canvas_id size_1 size_2 size_3 …. size_n
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        canvasList = br.lines().map(Canvas::createCanvas).collect(Collectors.toCollection(ArrayList::new));

        return canvasList.stream().mapToInt(Canvas::countSquaresPerCanvas).sum();
    }

    public void printLargestCanvasTo(OutputStream out) {
        //canvas_id squares_count total_squares_perimeter
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

        Canvas maxParametarCanvas = canvasList.stream()
                        .max(Comparator.naturalOrder()).orElse(null);

        pw.println(maxParametarCanvas);
        pw.flush();
        pw.close();
    }
}
