package exams.kolok1_2021.canvas_2;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShapesApplication {

    private double maxArea; // max ploshtina na sekoja figura posebno koja moze da se iscrta
    private List<Canvas> canvasList;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        this.canvasList = new ArrayList<Canvas>();
    }

    //5960017f C 30 S 15 S 588 C 25 C 14 S 14 S 17 C 19
    public void readCanvases(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        canvasList = br.lines().map(i -> {
                    try {
                        return Canvas.createCanvas(i, maxArea);
                    } catch (IrregularCanvasException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public void printCanvases(PrintStream out) {

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(out));

        canvasList.stream().sorted(Comparator.reverseOrder()).forEach(printWriter::println);
        printWriter.flush();
        printWriter.close();
    }
}
