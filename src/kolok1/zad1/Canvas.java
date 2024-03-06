package kolok1.zad1;

import java.util.ArrayList;
import java.util.List;

public class Canvas{

    private String id;
    private ArrayList<Integer> squares;
    public Canvas(String id, ArrayList<Integer> squares) {
        this.id = id;
        this.squares = squares;
    }

    public int parameter(){
        return squares.stream()
                .mapToInt(element -> Integer.valueOf(element))
                .sum() * 4;
    }

    public String toString(){
        return id + " " + squares.size() + " " + parameter();
    }
}
