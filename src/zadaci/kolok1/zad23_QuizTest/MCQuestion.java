package kolok1.zad23_QuizTest;

import java.text.DecimalFormat;

public class MCQuestion extends Question{

    public MCQuestion(String text, double points, String answer) {
        super(text, points, answer);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##"); //#: Represents a digit. If there is no digit in that position, nothing is displayed (no leading or trailing zeros are added).
        String formattedPoints = df.format(getPoints());
        return String.format("Multiple Choice Question: %s Points %s Answer: %s", this.getText(), formattedPoints, this.getAnswer());
    }

    public boolean checkOption(String op){
        return op.equals("A") || op.equals("B") || op.equals("C") || op.equals("D") || op.equals("E");
    }

    /**
     * За точен одговор на прашање со повеќе понудени одговори се добиваат сите предвидени поени,
     * а за неточен одговор се добиваат негативни поени (20% од предвидените поени).
     * @param realAnswer
     * @return
     */
    @Override
    public double calculatePoints(String realAnswer) {

        if(this.getAnswer().equals(realAnswer)){
            return this.getPoints();
        }else{
            return this.getPoints() * 0.20 * (-1);
        }
    }
}
