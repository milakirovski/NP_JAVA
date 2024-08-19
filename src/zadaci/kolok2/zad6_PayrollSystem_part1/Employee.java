package kolok2.zad6_PayrollSystem_part1;


import java.util.Comparator;
import java.util.Map;

enum TypeOfEmployee{
    H,
    F
}

public class Employee implements Comparable<Employee>{

    protected TypeOfEmployee typeOfEmployee;
    protected String id;
    protected String level;
    protected Map<String,Double> rateByLevel;

    public Employee(TypeOfEmployee typeOfEmployee, String id, String level) {
        this.typeOfEmployee = typeOfEmployee;
        this.id = id;
        this.level = level;
    }

    public double getSalary(){
        return 0.0;
    }

    public String getLevel() {
        return level;
    }

    public void setRateByLevel(Map<String, Double> rateByLevel) {
        this.rateByLevel = rateByLevel;
    }

    @Override
    public int compareTo(Employee o) {
        Comparator<Employee> comparator = Comparator.comparingDouble(Employee::getSalary)
                .thenComparing(Employee::getLevel)
                .reversed();

        return comparator.compare(this, o);
    }
}
