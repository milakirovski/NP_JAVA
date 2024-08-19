package kolok2.zad6_PayrollSystem_part1;

import java.util.*;

//FreelanceEmployee: F;ID;level;ticketPoints1;ticketPoints2;...;ticketPointsN;

public class FreelanceEmployee extends Employee{

    private List<Integer> points;

    public FreelanceEmployee(TypeOfEmployee typeOfEmployee, String id, String level, List<Integer> points) {
        super(typeOfEmployee, id, level);
        this.points = points;
    }

    @Override
    public double getSalary() {
        return ticketPoints() * PayrollSystem.TICKET_RATE_BY_LEVEl.get(getLevel());
    }

    @Override
    public void setRateByLevel(Map<String, Double> ticketRateByLevel) {
        super.setRateByLevel(ticketRateByLevel);
    }

    public List<Integer> getPoints() {
        return points;
    }

    private int countTickets(){
        return points.size();
    }

    private int ticketPoints(){
        return points.stream().mapToInt(i -> i).sum();
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %d",
                id,
                level,
                getSalary(),
                countTickets(),
                ticketPoints());
    }

}
