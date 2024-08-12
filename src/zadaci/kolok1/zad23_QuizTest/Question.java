package kolok1.zad23_QuizTest;

import java.util.Objects;

enum TypeOfQuestion {
    TF,
    MC
}
public class Question implements Comparable<Question>{
    private String text;
    private double points;
    private String answer;

    public Question(String text, double points, String answer) {
        this.text = text;
        this.points = points;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public double getPoints() {
        return points;
    }

    public String getAnswer() {
        return answer;
    }

    public static Question createQuestion(String s) throws InvalidOperationException {

        String[] parts = s.split(";");
        String type = parts[0];
        String text = parts[1];
        double points = Integer.parseInt(parts[2]);
        String answer = parts[3];

        if (TypeOfQuestion.valueOf(type) == TypeOfQuestion.TF) {
           return new TFQuestion(text, points, answer);
        } else if (TypeOfQuestion.valueOf(type) == TypeOfQuestion.MC) {

            MCQuestion question = new MCQuestion(text, points, answer);

            if(!question.checkOption(question.getAnswer())){
                throw new InvalidOperationException(String.format("%s is not allowed option for this question",question.getAnswer()));
            }
          return question;

        }else {
            return null;
        }
    }

    @Override
    public int compareTo(Question o) {
        return Double.compare(this.getPoints(), o.getPoints());
    }

    public double calculatePoints(String realAnswer){
        return 0.0;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}