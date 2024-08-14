package kolok2.zad24_Movies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private String title;
    private int[] ratings;
    private double coef;

    public Movie(String title, int[] ratings) {
        this.title = title;
       this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public int[] getRatings() {
        return ratings;
    }

    public double averageRating(){
       return Arrays.stream(ratings).average().orElse(0.00);
    }

    public double getCoef(){
        return coef;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",
                title,
                averageRating(),
                ratings.length);
    }
}
