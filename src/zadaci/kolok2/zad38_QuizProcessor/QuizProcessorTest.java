package kolok2.zad38_QuizProcessor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class NotAValidQuiz extends Exception{
    public NotAValidQuiz() {
        super("A quiz must have same number of correct and selected answers");
    }
}

class Quiz{
    String id;
    List<String> correctAnswers = new ArrayList<>();
    List<String> studentAnswers = new ArrayList<>();
    double points = 0;

    public Quiz(String id, List<String> correctAnswers, List<String> studentAnswers) {
        this.id = id;
        this.correctAnswers = correctAnswers;
        this.studentAnswers = studentAnswers;
    }

    public static Quiz createQuiz(String line) throws Exception{
        String[] parts = line.split(";");
        String id = parts[0];
        String[] answerParts = parts[1].split(",");
        String[] studentAnswerParts = parts[2].split(",");
        List<String> correctAnswers = Arrays.stream(answerParts).map(String::trim).collect(Collectors.toList());
        List<String> studentAnswers = Arrays.stream(studentAnswerParts).map(String::trim).collect(Collectors.toList());

        if(correctAnswers.size() != studentAnswers.size()){
            throw new NotAValidQuiz();
        }

        return new Quiz(id, correctAnswers, studentAnswers);
    }

    public double getPoints(){
        double points = 0;
        for (int i = 0, j = 0; i < studentAnswers.size(); i++, j++){
            if(correctAnswers.get(i).equals(studentAnswers.get(j))){
                //correct answer
                points+=1;
            }else{
                points+=-0.25;
            }
        }
        return points;
    }
}

class QuizProcessor{


    public static Map<String, Double> processAnswers(InputStream is){
        Map<String,Double> studentPointsMap = new TreeMap<>(Comparator.naturalOrder());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        List<Quiz> quizList = br.lines().map(line -> {
            try{
                return Quiz.createQuiz(line);
            }catch(Exception e){
                System.out.println(e.getMessage());
                return null;
            }}).filter(Objects::nonNull).collect(Collectors.toList());

        quizList.forEach(quiz -> {studentPointsMap.putIfAbsent(quiz.id, quiz.getPoints());});
        return studentPointsMap;
    }
}

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}
