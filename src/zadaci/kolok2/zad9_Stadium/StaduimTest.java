package kolok2.zad9_Stadium;

import java.util.*;
import java.util.stream.IntStream;

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

class SeatTakenException extends Exception {
    public SeatTakenException() {
        super("SeatTakenException");
    }
}

class SeatNotAllowedException extends Exception {
    public SeatNotAllowedException() {
        super("SeatNotAllowedException");
    }
}

class Sector implements Comparable<Sector> {
    String code;
    int seats;
    int type = 0;
    Map<Integer, Boolean> isSeatOccupied = new HashMap<>();

    public Sector(String code, int seats) {
        this.code = code;
        this.seats = seats;
        IntStream.range(1, seats + 1).boxed().forEach(i -> isSeatOccupied.put(i, false));
    }

    public int getNumberOfFreeSeats() {
        return (int) isSeatOccupied.values().stream().filter(i -> !i).count();
    }

    private double getPercentage() {
        return (1 - ((double) getNumberOfFreeSeats() / seats)) * 100.0;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%", code, getNumberOfFreeSeats(), seats, getPercentage());
    }

    @Override
    public int compareTo(Sector o) {
        Comparator<Sector> comp = Comparator.comparingInt(Sector::getNumberOfFreeSeats)
                .reversed()
                .thenComparing(Sector::getCode);

        return comp.compare(this, o);
    }
}

class Stadium {
    String name; // stadiums name
    Map<String, Sector> sectorsByCode;

    public Stadium(String name) {
        this.name = name;
        this.sectorsByCode = new HashMap<>();
    }

    void createSectors(String[] sectorNames, int[] sizes) {
        for (int i = 0; i < sectorNames.length; ++i) {
            sectorsByCode.put(sectorNames[i], new Sector(sectorNames[i], sizes[i]));
        }
    }

    void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {

        if (sectorsByCode.get(sectorName).isSeatOccupied.get(seat)) {
            throw new SeatTakenException();
        }

        //ne bil uste pristapen toj sektor, mu dodadav type
        if (sectorsByCode.get(sectorName).type == 0) {
            sectorsByCode.get(sectorName).type = type;
        } else if (sectorsByCode.get(sectorName).type == 1 && type == 2) {
            throw new SeatNotAllowedException();
        } else if (sectorsByCode.get(sectorName).type == 2 && type == 1) {
            throw new SeatNotAllowedException();
        }

        sectorsByCode.get(sectorName).isSeatOccupied.computeIfPresent(seat, (k, v) -> true);
    }

    void showSectors() {
        sectorsByCode.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    System.out.println(entry.getValue());
                });

    }
}