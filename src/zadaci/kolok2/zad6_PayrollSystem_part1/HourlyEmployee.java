package kolok2.zad6_PayrollSystem_part1;

import java.util.HashMap;
import java.util.Map;

//HourlyEmployee: H;ID;level;hours;
public class HourlyEmployee extends Employee{

    private double hours;
    private static double COEFFICIENT = 1.5;
    private static double MAX_REGULAR_HOURS = 40;

    public HourlyEmployee(TypeOfEmployee typeOfEmployee, String id, String level, double hours) {
        super(typeOfEmployee, id, level);
        this.hours = hours;
    }

    @Override
    public double getSalary() {

        return getRegularHours() * PayrollSystem.HOURLY_RATE_BY_LEVEl.get(level)
                + getOverTimeHours() * PayrollSystem.HOURLY_RATE_BY_LEVEl.get(level)
                * COEFFICIENT;
    }

    @Override
    public void setRateByLevel(Map<String, Double> hourlyRateByLevel) {
        super.setRateByLevel(hourlyRateByLevel);
    }

    public double getHours() {
        return hours;
    }
    public double getRegularHours(){
        if(hours < MAX_REGULAR_HOURS)
            return getHours();
        else return MAX_REGULAR_HOURS;
    }

    public double getOverTimeHours(){

        double overTimeHours = getHours() - MAX_REGULAR_HOURS;

        if(overTimeHours < 0){
            overTimeHours = 0;
        }
        return overTimeHours;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f",
                id,
                level,
                getSalary(),
                getRegularHours(),
                getOverTimeHours());
    }
}
