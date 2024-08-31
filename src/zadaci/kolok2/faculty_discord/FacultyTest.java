package kolok2.faculty_discord;

import java.util.*;
import java.util.stream.Collectors;

class Faculty{

    Map<String,List<Course>> studentsCoursesMap; // key -> studentId  value -> Set<Course>
    Map<String,List<Student>> coursesStudentsMap; // key -> courseId  value -> Set<Student>

    public Faculty() {
        this.studentsCoursesMap = new TreeMap<>();
        this.coursesStudentsMap = new TreeMap<>();
    }

    public void addInfo(String courseId, String studentId, int totalPoints){
        Course course = new Course(courseId);
        Student student = new Student(studentId,totalPoints);

        studentsCoursesMap.putIfAbsent(studentId,new ArrayList<>());
        studentsCoursesMap.get(studentId).add(course);

        coursesStudentsMap.putIfAbsent(courseId,new ArrayList<>());
        coursesStudentsMap.get(courseId).add(student);
    }

    public void printCourseReport(String courseId, String comparator, boolean descending) {
        coursesStudentsMap.get(courseId).stream()
                .sorted(getComparator(comparator, descending))
                .forEach(System.out::println);
    }

    private Comparator<Student> getComparator(String comparator, boolean descending) {
        Comparator<Student> comp = null;

        if(comparator.equalsIgnoreCase("byID")){
            comp = Comparator.comparing(Student::getStudentId);
        }else if(comparator.equalsIgnoreCase("byGrade")){
            comp = Comparator.comparing(Student::getGrade)
                    .thenComparingInt(Student::getTotalPoints)
                    .thenComparing(Student::getStudentId);
        }

        if(descending)
            comp = comp.reversed();
        return comp;
    }

    public void printStudentReport(String studentId){

        studentsCoursesMap.get(studentId).stream()
                .sorted(Comparator.comparing(Course::getCourseId))
                .forEach(i -> System.out.println(String.format("%s %d (%d)",
                        i.courseId,
                        coursesStudentsMap.get(i.courseId).stream().filter(j -> j.studentId.equals(studentId)).mapToInt(Student::getTotalPoints).sum(),
                        coursesStudentsMap.get(i.courseId).stream().filter(j -> j.studentId.equals(studentId)).mapToInt(Student::getGrade).sum()
                )));
    }


    public Map<Integer, Long> gradeDistribution(String courseId) {

        return coursesStudentsMap.get(courseId).stream()
                .collect(Collectors.groupingBy(
                        Student::getGrade,
                        Collectors.counting()
                ));
    }
}

class Course{
    String courseId;

    public Course(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}

class Student {
    String studentId;
    int totalPoints;

    public Student(String studentId, int totalPoints) {
        this.studentId = studentId;
        this.totalPoints = totalPoints;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getGrade(){
        if(totalPoints < 50){
            return 5;
        }else if(totalPoints >= 50 && totalPoints < 60){
            return 6;
        }else if(totalPoints >= 60 && totalPoints < 70){
            return 7;
        }else if(totalPoints >= 70 && totalPoints < 80){
            return 8;
        }else if(totalPoints >= 80 && totalPoints < 90){
            return 9;
        }else
            return 10;
    }

    @Override
    public String toString() {
        return String.format("%s %d (%d)", studentId, totalPoints, getGrade());
    }
}

public class FacultyTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Faculty faculty = new Faculty();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s++");
            if (parts[0].equals("addInfo")) {
                String courseId = parts[1];
                String studentId = parts[2];
                int totalPoints = Integer.parseInt(parts[3]);
                faculty.addInfo(courseId, studentId, totalPoints);
            } else if (parts[0].equals("printCourseReport")) {
                String courseId = parts[1];
                String comparator = parts[2];
                boolean descending = Boolean.parseBoolean(parts[3]);
                faculty.printCourseReport(courseId, comparator, descending);
            } else if (parts[0].equals("printStudentReport")) { //printStudentReport
                String studentId = parts[1];
                faculty.printStudentReport(studentId);
            } else {
                String courseId = parts[1];
                Map<Integer, Long> grades = faculty.gradeDistribution(courseId);
                grades.forEach((key, value) -> System.out.println(String.format("%2d -> %3d", key, value)));
            }
        }
    }
}
