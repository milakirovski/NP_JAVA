package kolok1.zad23_QuizTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizTest {
    public static void main(String[] args)  {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine()); // number of questions

        for (int i=0;i<questions;i++) {

            try {
                quiz.addQuestion(sc.nextLine()); // MC;Question1;3;E OR TF;Question3;2;false
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine()); // number of answers

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine()); // answers D or B or false or true ...
        }

        int testCase = Integer.parseInt(sc.nextLine()); // number of a test case, to invoke a specific method =)

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}
