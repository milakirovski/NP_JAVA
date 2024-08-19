package kolok2.zad6_PayrollSystem_part1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PayrollSystem {
    public static Map<String, Double> HOURLY_RATE_BY_LEVEl;
    public static Map<String, Double> TICKET_RATE_BY_LEVEl;
    private Map<String, Set<Employee>> employees;

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        HOURLY_RATE_BY_LEVEl = hourlyRateByLevel;
        TICKET_RATE_BY_LEVEl = ticketRateByLevel;
        this.employees = new TreeMap<>();
    }


    public void readEmployees(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        employees = reader.lines()
                .map(EmployeeFactory::createEmployee)
                .collect(Collectors.groupingBy(
                        Employee::getLevel,
                        (Supplier<Map<String, Set<Employee>>>) TreeMap::new,
                        Collectors.toCollection(TreeSet::new)
                ));
    }


    public Map<String, Set<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {

        Set<String> keys = new HashSet<>(employees.keySet());
        //keys.addAll(employeesByLevel.keySet())

        keys.stream()
                .filter(level -> !levels.contains(level))
                .forEach(employees::remove);

        return employees;
    }
}
