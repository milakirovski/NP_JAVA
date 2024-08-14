package kolok2.zad23_Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LabExercises {
    private List<Student> students;

    public LabExercises() {
        this.students = new ArrayList<Student>();
    }


    public void addStudent(Student student) {
        this.students.add(student);
    }


    public void printByAveragePoints(boolean ascending, int n) {

        Comparator<Student> comparator = Comparator.comparingDouble(Student::summaryPoints)
                .thenComparing(Student::getIndex);

        if (!ascending) {
            comparator = comparator.reversed();
        }

        students.stream()
                .sorted(comparator)
                .limit(n)
                .forEach(System.out::println);
    }

    public List<Student> failedStudents() {
        return students.stream()
                .filter(student -> !student.gotSignature())
                .sorted(Comparator.comparing(Student::getIndex).thenComparing(Student::summaryPoints))
                .collect(Collectors.toList());
    }

    public Map<Integer, Double> getStatisticsByYear() {
        return students.stream()
                .filter(Student::gotSignature)
                .collect(Collectors.groupingBy(
                        Student::getYearOfStudy,
                        Collectors.averagingDouble(Student::summaryPoints)
                ));
    }
}
