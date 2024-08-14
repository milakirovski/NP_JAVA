package kolok2.zad23_Student;

import java.util.List;

public class Student{
    private String index;
    private List<Integer> points;
    private static double  NUM_OF_LABS = 10.0;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
    }

    public String getIndex() {
        return index;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public double summaryPoints() {
        return (double) points.stream().mapToInt(i -> i).sum() / NUM_OF_LABS;
    }


    public boolean gotSignature(){
        return points.size() >= 8;
    }


    public int getYearOfStudy(){
        return 20 - Integer.parseInt(index.substring(0,2));
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f",
                index,
                gotSignature() ? "YES" : "NO",
                summaryPoints());
    }
}

