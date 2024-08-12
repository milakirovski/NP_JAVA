package kolok1.zad21_F1_Drivers;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {

        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }
}

class Timing implements Comparable<Timing>{
    private int mm;
    private int ss;
    private int nnn;

    public Timing(int mm, int ss, int nnn) {
        this.mm = mm;
        this.ss = ss;
        this.nnn = nnn;
    }

    public int getMm() {
        return mm;
    }

    public int getSs() {
        return ss;
    }

    public int getNnn() {
        return nnn;
    }

    public int timingInMillis(){
        return this.mm * 60 * 1000 + this.ss * 1000 + this.nnn;
    }


    @Override
    public int compareTo(Timing o) {
        return Integer.compare(this.timingInMillis(), o.timingInMillis());
    }

    @Override
    public String toString() {
        return String.format("%01d:%02d:%03d", mm, ss, nnn);
    }
}

class F1Driver implements Comparable<F1Driver>{
    private String name;
    private List<Timing> laps;

    public F1Driver(String name, List<Timing> laps) {
        this.name = name;
        this.laps = laps;
    }

    public String getName() {
        return name;
    }

    public static F1Driver createDriver(String line) {

        String[] parts = line.split("\\s+");
        String name = parts[0];

        List<Timing> laps = new ArrayList<>();

        for (int i = 1; i < parts.length; i++) {

            String[] lapTimeParts = parts[i].split(":");
            int mm = Integer.parseInt(lapTimeParts[0]);
            int ss = Integer.parseInt(lapTimeParts[1]);
            int nnn = Integer.parseInt(lapTimeParts[2]);

            laps.add(new Timing(mm,ss,nnn));
        }
        return new F1Driver(name, laps);
    }

    @Override
    public String toString() {
        return String.format("%s %s", name,this.findBestTiming());
    }

    public Timing findBestTiming(){
        return this.laps.stream().min(Comparator.comparing(Timing::timingInMillis)).orElse(null);
    }

    @Override
    public int compareTo(F1Driver o) {
        return this.findBestTiming().compareTo(o.findBestTiming());
    }
}

class F1Race {

    private List<F1Driver> f1Drivers;

    public F1Race() {
        this.f1Drivers = new ArrayList<>();
    }

    public void readResults(InputStream in) {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        f1Drivers = br.lines()
                .map(F1Driver::createDriver)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));


    }

    public void printSorted(PrintStream out) {

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(out));

        for (int i = 0; i < f1Drivers.size(); i++) {
            printWriter.println(String.format("%d. %-10s%10s",
                    i+1,
                    f1Drivers.get(i).getName(),
                    f1Drivers.get(i).findBestTiming()));
        }

        printWriter.close();
    }


}


