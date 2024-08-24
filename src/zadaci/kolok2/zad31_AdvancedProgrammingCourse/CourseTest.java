package kolok2.zad31_AdvancedProgrammingCourse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Student implements Comparable<Student>{
    String index;
    String name;
    int pointsMidterm1; //max value 100
    int pointsMidterm2; //max value 100
    int pointsLabs; // max value 10
    Map<String,Integer> pointsPerActivty;
    int grade;

    //151020 Stefan
    public Student(String index, String name) {
        this.index = index;
        this.name = name;
        this.pointsMidterm1 = 0;
        this.pointsMidterm2 = 0;
        this.pointsLabs = 0;
        this.pointsPerActivty = new HashMap<>();
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public int getPointsMidterm1() {
        return pointsMidterm1;
    }

    public int getPointsMidterm2() {
        return pointsMidterm2;
    }

    public int getPointsLabs() {
        return pointsLabs;
    }
    public double sumPoints(){
        //midterm1 * 0.45 + midterm2 * 0.45 + labs.
        return pointsPerActivty.get("midterm1") * 0.45 + pointsPerActivty.get("midterm2") * 0.45 + pointsPerActivty.get("labs");
    }

    public int getGrade(){
        if(sumPoints() < 50){
            return grade = 5;
        }else if(sumPoints() >= 50 && sumPoints() < 60){
            return grade = 6;
        }else if(sumPoints() >= 60 && sumPoints() <= 70){
            return grade = 7;
        }else if(sumPoints() >= 70 && sumPoints() <= 80){
            return grade = 8;
        }else if(sumPoints() >= 80 && sumPoints() <= 90){
            return grade = 9;
        }else if(sumPoints() >= 90 && sumPoints() <= 100){
            return grade = 10;
        }
        return grade=5;
    }

    @Override
    public int compareTo(Student o) {
        Comparator<Student> comp = Comparator.comparing(Student::sumPoints).reversed();
        return comp.compare(this,o);
    }

    @Override
    public String toString() {
        //ID: 151020 Name: Stefan First midterm: 78 Second midterm 80 Labs: 8 Summary points: 79.10 Grade: 8
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
                getIndex(),
                getName(),
                getPointsMidterm1(),
                getPointsMidterm2(),
                getPointsLabs(),
                sumPoints(),
                getGrade());
    }
}

class AdvancedProgrammingCourse{

    Map<String,Student> studentsByIndex;
    Map<Integer,List<Student>> numberOfStudentsPerGrade;

    public AdvancedProgrammingCourse() {
        this.studentsByIndex = new HashMap<>();
        this.numberOfStudentsPerGrade = new TreeMap<>(Comparator.naturalOrder());
    }

    public void addStudent(Student student) {
        studentsByIndex.putIfAbsent(student.getIndex(), student);
    }

    public void updateStudent(String idNumber, String activity, int points) {

        Student student = studentsByIndex.get(idNumber);
        student.pointsPerActivty.put(activity,points);

        if(activity.equals("midterm1")) {
            if(points > 100){
                throw new RuntimeException();
            }
            student.pointsMidterm1 += points;
        }else if(activity.equals("midterm2")){
            if(points > 100){
                throw new RuntimeException();
            }
            student.pointsMidterm2 += points;
        }else if(activity.equals("labs")) {
            if(points > 10){
                throw new RuntimeException();
            }
            student.pointsLabs += points;
        }

    }

    public List<Student> getFirstNStudents(int n) {
        return studentsByIndex.values()
                .stream()
                .sorted()
                .limit(n)
                .collect(Collectors.toList());
    }

    public Map<Integer, Integer> getGradeDistribution() {

        Map<Integer,Integer> getGradeDistribution = new HashMap<>();
        Set<Integer> keys = new TreeSet<>(Comparator.naturalOrder());
        keys = IntStream.range(5,11).boxed().collect(Collectors.toSet());
        for (Integer key : keys) {
            int numOfStudents = (int) studentsByIndex.values()
                    .stream()
                    .filter(student -> student.getGrade() == key)
                    .count();
            getGradeDistribution.put(key,numOfStudents);
        }
        return getGradeDistribution;
    }


    public void printStatistics() {
        List<Double> sumPointsPassedStudents = studentsByIndex.values().stream()
                .filter(student -> student.getGrade() == 6 || student.getGrade() == 7 || student.getGrade() == 8 || student.getGrade() == 9 || student.getGrade() == 10)
                .map(Student::sumPoints)
                .collect(Collectors.toList());

        DoubleSummaryStatistics summaryStatistics = sumPointsPassedStudents.stream()
                .mapToDouble(Double::doubleValue).summaryStatistics();

        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f\n",
                summaryStatistics.getCount(),
                summaryStatistics.getMin(),
                summaryStatistics.getAverage(),
                summaryStatistics.getMax()
                );
    }
}


public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}
