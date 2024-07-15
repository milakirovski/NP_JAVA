package exams.kolok1_2021.canvas_vlezna;

public class Square {
    private int a;

    public Square(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public int perimeter(){
        return 4 * a;
    }
}
