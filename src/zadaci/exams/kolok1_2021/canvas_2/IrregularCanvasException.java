package exams.kolok1_2021.canvas_2;

import java.util.Locale;

public class IrregularCanvasException extends Exception{
    public IrregularCanvasException(String id,double maxArea) {
        super(String.format(Locale.US,"Canvas %s has a shape with area larger than %.2f",id,maxArea));
    }
}
