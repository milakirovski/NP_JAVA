package kolok1.zad23_QuizTest;

import java.text.DecimalFormat;

public class TFQuestion extends Question{

    public TFQuestion(String text, double points, String answer) {
        super(text, points, answer);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedPoints = df.format(getPoints());
        return String.format("True/False Question: %s Points: %s Answer: %s", getText(), formattedPoints, getAnswer());
    }

    /**
     * За точен одговор на true/false прашање се добиваат сите предвидени поени, а за неточен одговор се добиваат 0 поени
     * @param realAnswer
     * @return
     */
    @Override
    public double calculatePoints(String realAnswer) {
        if(realAnswer.equals(getAnswer())){
            return getPoints();
        }else{
            return 0;
        }
    }
}
