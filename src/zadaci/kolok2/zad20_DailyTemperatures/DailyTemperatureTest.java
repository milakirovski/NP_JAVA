package kolok2.zad20_DailyTemperatures;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

// Vashiot kod ovde
class DailyTemperatures {

    Map<Integer, TemperatureStatsCels> temperatureStatsMapCels;
    Map<Integer, TemperatureStatsFahr> temperatureStatsMapFahr;
    Map<Integer, List<Celsius>> temperaturesPerDayInCelsius;
    Map<Integer, List<Fahrenheit>> temperaturesPerDayInFahrenheit;

    public DailyTemperatures() {
        this.temperatureStatsMapCels = new TreeMap<>(Comparator.naturalOrder());
        this.temperatureStatsMapFahr = new TreeMap<>(Comparator.naturalOrder());

        this.temperaturesPerDayInCelsius = new TreeMap<>(Comparator.naturalOrder());
        this.temperaturesPerDayInFahrenheit = new TreeMap<>(Comparator.naturalOrder());

    }


    public void readTemperatures(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        List<String> inputs = br.lines().collect(Collectors.toList());


        for (String line : inputs) {
            Temperature temperature = null;
            Celsius celsius = null;
            Fahrenheit fahrenheit = null;
            String[] parts = line.split("\\s++");

            int day = Integer.parseInt(parts[0]);
            temperaturesPerDayInCelsius.putIfAbsent(day,new ArrayList<>());
            temperaturesPerDayInFahrenheit.putIfAbsent(day,new ArrayList<>());
            // temps
            for (int i = 1; i < parts.length; i++){
                int value = extractValue(parts[i]);
                char type = parts[i].charAt(parts[i].length() - 1);

                if(type == 'C'){
                    temperature = new Celsius(value);
                    celsius = new Celsius(value);
                    fahrenheit = temperature.fromCtoF();

                }else if(type == 'F'){
                    temperature = new Fahrenheit(value);
                    celsius = temperature.fromFtoC();
                    fahrenheit = new Fahrenheit(value);
                }
                temperaturesPerDayInCelsius.get(day).add(celsius);
                temperaturesPerDayInFahrenheit.get(day).add(fahrenheit);

            }

            TemperatureStatsCels temperatureStatsCels = new TemperatureStatsCels(temperaturesPerDayInCelsius.get(day));
            TemperatureStatsFahr temperatureStatsFahr = new TemperatureStatsFahr(temperaturesPerDayInFahrenheit.get(day));

            temperatureStatsMapCels.put(day,temperatureStatsCels);
            temperatureStatsMapFahr.put(day,temperatureStatsFahr);

        }


    }

    private int extractValue(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if(Character.isDigit(c) || c == '-') {
                sb.append(c);
            }
        }
        return Integer.parseInt(sb.toString());
    }

    public void writeDailyStats(PrintStream out, char scale) {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(out));

        if(scale == 'C'){
            //stats in C
            printWriter.println(temperatureStatsMapCels.entrySet().stream()
                    .map(entry -> String.format("%3d: %s",entry.getKey(),entry.getValue()))
                    .collect(Collectors.joining("\n")));

            printWriter.flush();

        }else if(scale == 'F'){
            // convert temps to F
            printWriter.println(temperatureStatsMapFahr.entrySet().stream()
                    .map(entry -> String.format("%3d: %s",entry.getKey(),entry.getValue()))
                    .collect(Collectors.joining("\n")));

            printWriter.flush();
        }

    }
}


class Temperature{
    double value;
    char type;

    public Temperature(double value, char type) {
        this.value = value;
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public char getType() {
        return type;
    }

    @Override
    public String toString() {
        return "value: " + value + " type: " + type;
    }

    public Fahrenheit fromCtoF(){
        value = (value * 9) / 5 + 32;
        return new Fahrenheit(value);
    }

    public Celsius fromFtoC(){
        value= ((value - 32) * 5) / 9;
        return new Celsius(value);
    }
}

class Celsius extends Temperature{
    public Celsius(double value) {
        super(value, 'C');
    }
}

class Fahrenheit extends Temperature{
    public Fahrenheit(double value) {
        super(value, 'F');
    }
}

class TemperatureStatsCels{
    List<Celsius> celsiusList;

    public TemperatureStatsCels(List<Celsius> celsiusList) {
        this.celsiusList = celsiusList;
    }

    //11: Count:   7 Min:  38.33C Max:  40.56C Avg:  39.44C


    @Override
    public String toString() {
        DoubleSummaryStatistics doubleSummaryStatistics = celsiusList.stream().mapToDouble(temp -> temp.value)
                .summaryStatistics();

        return String.format("Count:%4d Min:%7.2fC Max:%7.2fC Avg:%7.2fC",
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getAverage());
    }
}

class TemperatureStatsFahr{
    List<Fahrenheit> fahrenheitList;

    public TemperatureStatsFahr(List<Fahrenheit> fahrenheitList) {
        this.fahrenheitList = fahrenheitList;
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics doubleSummaryStatistics = fahrenheitList.stream().mapToDouble(temp -> temp.value)
                .summaryStatistics();

        return String.format("Count:%4d Min:%7.2fF Max:%7.2fF Avg:%7.2fF",
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getAverage());
    }
}