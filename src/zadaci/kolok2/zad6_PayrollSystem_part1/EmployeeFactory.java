package kolok2.zad6_PayrollSystem_part1;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeFactory {
    public static Employee createEmployee(String line) {
        Employee employee = null;

        String[] parts = line.split(";");
        TypeOfEmployee type = TypeOfEmployee.valueOf(parts[0]);
        String id = parts[1];
        String level = parts[2];

        if(type.equals(TypeOfEmployee.H)){
            double hours = Double.parseDouble(parts[3]);
            employee = new HourlyEmployee(TypeOfEmployee.H,id,level,hours);
        }else if(type.equals(TypeOfEmployee.F)){
            List<Integer> points = Arrays.stream(parts)
                    .skip(3)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());
            employee = new FreelanceEmployee(TypeOfEmployee.F,id,level,points);
        }
        return employee;
    }
}
