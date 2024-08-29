package labs.lab8.zad3;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


enum TypeOfQuestion{
    TRUEFALSE,
    FREEFORM
}
class TriviaQuestion {

    public String question;		// Actual question
    public String answer;		// Answer to question
    public int value;			// Point value of question
    public TypeOfQuestion type;			// Question type, TRUEFALSE or FREEFORM

    public TriviaQuestion() {
        question = "";
        answer = "";
        value = 0;
        type = TypeOfQuestion.FREEFORM;
    }

    public TriviaQuestion(String q, String a, int v, TypeOfQuestion t) {
        question = q;
        answer = a;
        value = v;
        type = t;
    }
}

class TriviaData {

    private ArrayList<TriviaQuestion> data;

    public TriviaData() {
        data = new ArrayList<TriviaQuestion>();
    }

    public void addQuestion(String q, String a, int v, TypeOfQuestion t) {
        data.add(new TriviaQuestion(q, a, v, t));
    }

    public void showQuestion(int index) {
        TriviaQuestion q = data.get(index);
        System.out.println("Question " + (index + 1) + ".  " + q.value + " points.");
        if (q.type == TypeOfQuestion.TRUEFALSE) {
            System.out.println(q.question);
            System.out.println("Enter 'T' for true or 'F' for false.");
        } else if (q.type == TypeOfQuestion.FREEFORM) {
            System.out.println(q.question);
        }
    }

    public int numQuestions() {
        return data.size();
    }

    public TriviaQuestion getQuestion(int index) {
        return data.get(index);
    }
}

public class TriviaGame {

    public TriviaData questions;	// Questions

    public TriviaGame() {
        // Load questions
        questions = new TriviaData();
        questions.addQuestion("The possession of more than two sets of chromosomes is termed?",
                "polyploidy", 3, TypeOfQuestion.FREEFORM);
        questions.addQuestion("Erling Kagge skiied into the north pole alone on January 7, 1993.",
                "F", 1, TypeOfQuestion.TRUEFALSE);
        questions.addQuestion("1997 British band that produced 'Tub Thumper'",
                "Chumbawumba", 2, TypeOfQuestion.FREEFORM);
        questions.addQuestion("I am the geometric figure most like a lost parrot",
                "polygon", 2, TypeOfQuestion.FREEFORM);
        questions.addQuestion("Generics were introducted to Java starting at version 5.0.",
                "T", 1, TypeOfQuestion.TRUEFALSE);
    }
    // Main game loop

    public static void main(String[] args) {
        int score = 0;			// Overall score
        int questionNum = 0;	// Which question we're asking
        TriviaGame game = new TriviaGame();
        Scanner keyboard = new Scanner(System.in);
        // Ask a question as long as we haven't asked them all
        while (questionNum < game.questions.numQuestions()) {
            // Show question
            game.questions.showQuestion(questionNum);
            // Get answer
            String answer = keyboard.nextLine();
            // Validate answer
            TriviaQuestion q = game.questions.getQuestion(questionNum);
            if (q.type == TypeOfQuestion.TRUEFALSE) {
                if (answer.charAt(0) == q.answer.charAt(0)) {
                    System.out.println("That is correct!  You get " + q.value + " points.");
                    score += q.value;
                } else {
                    System.out.println("Wrong, the correct answer is " + q.answer);
                }
            } else if (q.type == TypeOfQuestion.FREEFORM) {
                if (answer.toLowerCase().equals(q.answer.toLowerCase())) {
                    System.out.println("That is correct!  You get " + q.value + " points.");
                    score += q.value;
                } else {
                    System.out.println("Wrong, the correct answer is " + q.answer);
                }
            }
            System.out.println("Your score is " + score);
            questionNum++;
        }
        System.out.println("Game over!  Thanks for playing!");
    }
}
