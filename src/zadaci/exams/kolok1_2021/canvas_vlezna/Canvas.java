package exams.kolok1_2021.canvas_vlezna;

import java.util.ArrayList;
import java.util.List;

public class Canvas implements  Comparable<Canvas>{
    private String id;
    private List<Square> squares;

    public Canvas(String id, List<Square> squares) {
        this.squares = squares;
        this.id = id;
    }

    public int countSquaresPerCanvas(){
        return squares.size();
    }

    public static Canvas createCanvas(String line){
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Square> squares = new ArrayList<>();

        for(int i = 1; i < parts.length; i++){
            squares.add(new Square(Integer.parseInt(parts[i])));
        }

        return new Canvas(id, squares);
    }

    public int totalCanvasSquaresParameter(){
        return squares.stream().mapToInt(Square::perimeter).sum();
    }


    @Override
    public String toString() {
        //canvas_id squares_count total_squares_perimeter
        return id + " " + countSquaresPerCanvas() + " " + totalCanvasSquaresParameter();
    }

    @Override
    public int compareTo(Canvas o) {
        return Integer.compare(this.totalCanvasSquaresParameter(), o.totalCanvasSquaresParameter());
    }
}
