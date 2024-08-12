package kolok1.zad23_QuizTest;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Quiz {

    private List<Question> questions;

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(String s) throws InvalidOperationException {
        questions.add(Question.createQuestion(s));
    }

    public void printQuiz(PrintStream out) {

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));

        questions.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(printWriter::println);

        printWriter.close();

    }

    public void answerQuiz(List<String> answers, OutputStream os) throws InvalidOperationException {

        if(questions.size() != answers.size()) {
            throw new InvalidOperationException("Answers and questions must be of same length!");
        }
        for (int q = 0, a = 0; q < questions.size() && a < answers.size(); q++, a++) {
            double pointsPerQuestion = questions.get(q).calculatePoints(answers.get(a));

            questions.get(q).setPoints(pointsPerQuestion);
        }

        for (int i = 0; i < questions.size(); i++) {
            System.out.println(String.format("%d. %.2f", i+1, questions.get(i).getPoints()));
        }
        System.out.println(String.format("Total points: %.2f",totalPoints()));
    }

    public double totalPoints(){
        return questions.stream().mapToDouble(Question::getPoints).sum();
    }
}
