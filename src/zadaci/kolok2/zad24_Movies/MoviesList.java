package kolok2.zad24_Movies;

import java.util.*;
import java.util.stream.Collectors;

public class MoviesList {
    private List<Movie> movies;

    public MoviesList() {
        this.movies = new ArrayList<>();
    }


    public void addMovie(String title, int[] ratings) {
        movies.add(new Movie(title, ratings));
    }


    public List<Movie> top10ByAvgRating() {
        return movies.stream()
                .sorted(Comparator.comparingDouble(Movie::averageRating).reversed()
                        .thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Movie> top10ByRatingCoef() {

        Map<Double, List<Movie>> movieMap = new TreeMap<>(Comparator.comparingDouble(Double::doubleValue)
                .reversed()
                .thenComparing(String::valueOf));

        for (Movie movie : movies) {
            movieMap.putIfAbsent(calculateCoef(movie), new ArrayList<>());
            movieMap.get(calculateCoef(movie)).add(movie);
        }

        return movieMap.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingDouble(Movie::getCoef).reversed().thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toList());

    }

    private double calculateCoef(Movie m) {
        double coef = m.averageRating() * m.getRatings().length / maxNumberOfMovies();
        m.setCoef(coef);
        return coef;
    }

    public int maxNumberOfMovies() {
        return movies.stream().mapToInt(movie -> movie.getRatings().length).max().orElse(0);
    }
}
