package kolokviumski2023_2024.vtor_kolokvium.zad2_StreamingPlatform;

import java.util.*;
import java.util.stream.Collectors;

class CosineSimilarityCalculator {

    public static double cosineSimilarity(Map<String, Integer> c1, Map<String, Integer> c2) {
        return cosineSimilarity(c1.values(), c2.values());
    }

    public static double cosineSimilarity(Collection<Integer> c1, Collection<Integer> c2) {
        int[] array1;
        int[] array2;
        array1 = c1.stream().mapToInt(i -> i).toArray();
        array2 = c2.stream().mapToInt(i -> i).toArray();
        double up = 0.0;
        double down1 = 0, down2 = 0;

        for (int i = 0; i < c1.size(); i++) {
            up += (array1[i] * array2[i]);
        }

        for (int i = 0; i < c1.size(); i++) {
            down1 += (array1[i] * array1[i]);
        }

        for (int i = 0; i < c1.size(); i++) {
            down2 += (array2[i] * array2[i]);
        }

        return up / (Math.sqrt(down1) * Math.sqrt(down2));
    }
}


public class StreamingPlatform2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StreamingPlatform sp = new StreamingPlatform();

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String [] parts = line.split("\\s+");

            if (parts[0].equals("addMovie")) {
                String id = parts[1];
                String name = Arrays.stream(parts).skip(2).collect(Collectors.joining(" "));
                sp.addMovie(id ,name);
            } else if (parts[0].equals("addUser")){
                String id = parts[1];
                String name = parts[2];
                sp.addUser(id ,name);
            } else if (parts[0].equals("addRating")){
                //String userId, String movieId, int rating
                String userId = parts[1];
                String movieId = parts[2];
                int rating = Integer.parseInt(parts[3]);
                sp.addRating(userId, movieId, rating);
            } else if (parts[0].equals("topNMovies")){
                int n = Integer.parseInt(parts[1]);
                System.out.println("TOP " + n + " MOVIES:");
                sp.topNMovies(n);
            } else if (parts[0].equals("favouriteMoviesForUsers")) {
                List<String> users = Arrays.stream(parts).skip(1).collect(Collectors.toList());
                System.out.println("FAVOURITE MOVIES FOR USERS WITH IDS: " + users.stream().collect(Collectors.joining(", ")));
                sp.favouriteMoviesForUsers(users);
            } else if (parts[0].equals("similarUsers")) {
                String userId = parts[1];
                System.out.println("SIMILAR USERS TO USER WITH ID: " + userId);
                sp.similarUsers(userId);
            }
        }
    }
}

class StreamingPlatform{

    Map<String,User> usersMap;
    Map<String,Movie> moviesMap;

    public StreamingPlatform() {
        this.usersMap = new HashMap<>();
        this.moviesMap = new HashMap<>();
    }

    void addUser(String userId, String name) {
        User user = new User(userId, name);
        this.usersMap.put(userId, user);
    }

    void addMovie(String movieId, String name) {
        Movie movie = new Movie(movieId,name);
        this.moviesMap.put(movieId, movie);
    }

    void addRating (String userId, String movieId, int rating){
        User user = this.usersMap.get(userId);
        Movie movie = this.moviesMap.get(movieId);

        //1) add reting movies
        //2) add raiting for the movie

        user.ratedMoviesMap.putIfAbsent(movieId,movie);
        user.ratedMoviesMap.get(movieId).ratingPerUser.putIfAbsent(userId,rating);

        //3) add rating in the allRatings array in Movie
        movie.allRatings.add(rating);
    }

    public void topNMovies (int n){
        this.moviesMap.values().stream()
                .sorted(Comparator.comparingDouble(Movie::averageRating).reversed())
                .limit(n)
                .forEach(System.out::println);
    }


    public void favouriteMoviesForUsers(List<String> users) {
        users.forEach(user ->{
            System.out.println(usersMap.get(user));
            usersMap.get(user).getFaveMovie().stream().sorted(Comparator.comparingDouble(Movie::averageRating).reversed())
                    .forEach(System.out::println);
            System.out.println();
        });
    }

    public void similarUsers(String userId) {
    }
}

class User{
    String name;
    String id;
    Map<String, Movie> ratedMoviesMap = new HashMap<>(); // movieId = Movie obj

    public User(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public List<Movie> getFaveMovie() {
        int currMax = 0;
        List<Movie> favouriteMovies = new ArrayList<>();


        for (Movie movie : ratedMoviesMap.values()) {
            currMax = movie.ratingPerUser.get(id);

            if(currMax == getMaxRating()){
                favouriteMovies.add(movie);
            }
        }
        return favouriteMovies;
    }

    private int getMaxRating(){
        int currMax = 0;
        int maxRating = 0;
        for (Movie movie : ratedMoviesMap.values()) {
            currMax = movie.ratingPerUser.get(id);
            if(currMax > maxRating){
                maxRating = currMax;
            }
        }
        return maxRating;
    }

    @Override
    public String toString() {
        return String.format("User ID: %s Name: %s", id, name);
    }
}

class Movie{
    String name;
    String id;
    List<Integer> allRatings = new ArrayList<>();
    Map<String,Integer> ratingPerUser = new HashMap<>(); //userId = (int) rating

    public Movie(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public double averageRating() {
        return allRatings.stream().mapToDouble(i -> i).average().getAsDouble();
    }

    @Override
    public String toString() {
        return String.format("Movie ID: %s Title: %s Rating: %.2f",id,name,averageRating());
    }
}



