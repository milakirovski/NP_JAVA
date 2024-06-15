package kolok1.zad2;

public class IrregularCanvasException extends Exception{

    public IrregularCanvasException(String id, double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f.", id, maxArea));
    }
}
