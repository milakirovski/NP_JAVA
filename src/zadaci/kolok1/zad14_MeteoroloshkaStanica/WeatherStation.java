package kolok1.zad14_MeteoroloshkaStanica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class WeatherStation {
    private int days; //measures in the last x days
    private List<Measure> measures;

    public WeatherStation(int days) {
        this.days = days;
        this.measures = new ArrayList<Measure>();
    }


    // која ќе ги чува податоците за временските услови за последните x денови
    // (при додавање на податоци за ново мерење, сите мерења чие што време е постаро за x денови од новото се бришат ).
    // Исто така ако времето на новото мерење кое се додава се разликува за помалку од 2.5 минути од времето на некое претходно
    // додадено мерење, тоа треба да се игнорира (не се додава).

    public void addMeasurment(float temp, float wind, float hum, float vis, Date date) {

        int flag = 0;
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        Measure measure = new Measure(temp, wind, hum, vis, date);

        long newMeasureInMillis = measure.getDate().getTime();
        long xDaysInMillis = days * oneDayInMillis;

        for (int i = 0; i < measures.size(); i++) {
            Measure m = measures.get(i);
            long difference = newMeasureInMillis - m.getDate().getTime();

            if (difference > xDaysInMillis) {
                measures.remove(i);
                i--; // Adjust index to account for the removed element
                continue;
            }

            double millisLimiter = 2.5 * 60 * 1000;

            if (Math.abs(difference) < millisLimiter) {
                flag = 1;
                break;
            }
        }

        if (flag == 0){
            measures.add(measure);
        }

    }


    public int total() {
        return measures.size();
    }


    public void status(Date from, Date to) {
        ArrayList<Measure> measuresFromTo = measures.stream()
                .filter(measure -> measure.getDate().compareTo(from) >= 0 && measure.getDate().compareTo(to) <= 0)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        if(measuresFromTo.isEmpty()){
            throw new RuntimeException();
        }

        measuresFromTo.forEach(System.out::println);

        double averageTemperature = measuresFromTo.stream()
                .mapToDouble(Measure::getDegrees)
                .average().orElse(0);

        System.out.printf(Locale.US,"Average temperature: %.2f\n",averageTemperature);


    }
}
