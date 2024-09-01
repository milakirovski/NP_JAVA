package kolokviumski2023_2024.vtor_kolokvium.zad1_Parking;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

class DateUtil {
    public static long durationBetween(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMinutes();
    }
}

public class ParkingTesting {

    public static <K, V extends Comparable<V>> void printMapSortedByValue(Map<K, V> map) {
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(String.format("%s -> %s", entry.getKey().toString(), entry.getValue().toString())));

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int capacity = Integer.parseInt(sc.nextLine());

        Parking parking = new Parking(capacity);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equals("update")) {
                String registration = parts[1];
                String spot = parts[2];
                LocalDateTime timestamp = LocalDateTime.parse(parts[3]);
                boolean entrance = Boolean.parseBoolean(parts[4]);
                parking.update(registration, spot, timestamp, entrance);
            } else if (parts[0].equals("currentState")) {
                System.out.println("PARKING CURRENT STATE");
                parking.currentState();
            } else if (parts[0].equals("history")) {
                System.out.println("PARKING HISTORY");
                parking.history();
            } else if (parts[0].equals("carStatistics")) {
                System.out.println("CAR STATISTICS");
                printMapSortedByValue(parking.carStatistics());
            } else if (parts[0].equals("spotOccupancy")) {
                LocalDateTime start = LocalDateTime.parse(parts[1]);
                LocalDateTime end = LocalDateTime.parse(parts[2]);
                printMapSortedByValue(parking.spotOccupancy(start, end));
            }
        }
    }
}

class Parking {
    int capacity;
    Map<String, ParkInfo> entryMap; //momentalno vo parkingot
    Map<String, ParkInfo> exitMap; //izlegle od parkingot
    Map<String,Integer> registrationByNumberOfTimeParkedMap;
    Map<String,List<ParkInfo>> historyByRegistrationMap;

    //koga iskaca vlozlo od parking se brishe od entry se stava vo exit

    public Parking(int capacity) {
        this.capacity = capacity;
        this.entryMap = new HashMap<>();
        this.exitMap = new HashMap<>();
        this.historyByRegistrationMap = new HashMap<>();
        this.registrationByNumberOfTimeParkedMap = new TreeMap<>(Comparator.naturalOrder());
    }

    void update(String registration, String spot, LocalDateTime timestamp, boolean entry) {
        ParkInfo parkInfo = new ParkInfo(registration, spot, timestamp, entry);

        registrationByNumberOfTimeParkedMap.putIfAbsent(registration,0);
        historyByRegistrationMap.putIfAbsent(registration,new ArrayList<>());
        historyByRegistrationMap.get(registration).add(parkInfo);


        if (entry) {
            entryMap.putIfAbsent(registration, parkInfo);
            exitMap.remove(registration);
            registrationByNumberOfTimeParkedMap.computeIfPresent(registration,(k,v) -> ++v);
        } else {
            exitMap.putIfAbsent(registration, parkInfo);
            entryMap.remove(registration);
        }
    }

    public void currentState() {
        int parked = entryMap.keySet().size();
        double dec = parked * 1.0 / capacity * 100;
        System.out.printf("Capacity filled: %.2f%%%n", dec);

        entryMap.values().stream()
                .sorted(Comparator.reverseOrder())
                .forEach(i -> {
                    LocalDateTime entryTime = historyByRegistrationMap.get(i.registration).get(0).getTimestamp();
                    System.out.printf("Registration number: %s Spot: %s Start timestamp: %s%n",
                            i.registration,i.spot,entryTime);
                });
    }

    public void history() {
        exitMap.values().stream()
                .sorted()
                .forEach(i -> {
                    LocalDateTime entryTime = historyByRegistrationMap.get(i.registration).get(0).getTimestamp();
                    LocalDateTime exitTime = historyByRegistrationMap.get(i.registration).get(1).getTimestamp();
                    i.setDurationPark(DateUtil.durationBetween(entryTime, exitTime));
                });

        exitMap.values().stream()
                .sorted(Comparator.comparingLong(ParkInfo::getDurationPark).reversed())
                .forEach(i -> {
                    LocalDateTime entryTime = historyByRegistrationMap.get(i.registration).get(0).getTimestamp();
                    LocalDateTime exitTime = historyByRegistrationMap.get(i.registration).get(1).getTimestamp();
                    i.setEntryTime(entryTime);
                    i.setExitTime(exitTime);
                    System.out.printf("Registration number: %s Spot: %s Start timestamp: %s End timestamp: %s Duration in minutes: %d%n",
                            i.registration,i.spot,entryTime, exitTime, i.getDurationPark());
                });

    }

    public Map<String, Integer> carStatistics() {
        return registrationByNumberOfTimeParkedMap;
    }


    public Map<String, Double> spotOccupancy(LocalDateTime start, LocalDateTime end) {
        Map<String, Double> map = new HashMap<>();

        double totalDuration = end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC);

        Set<String> allSpots = exitMap.values().stream()
                .map(ParkInfo::getSpot)
                .collect(Collectors.toSet());

        allSpots.forEach(spot -> map.put(spot, 0.0));

        allSpots.forEach(spot -> {
            historyByRegistrationMap.values().stream()
                    .flatMap(Collection::stream)
                    .filter(info -> info.spot.equals(spot))
                    .filter(info -> !info.entry)
                    .filter(info -> {
                        LocalDateTime entryTime = historyByRegistrationMap.get(info.registration).get(0).getTimestamp();
                        LocalDateTime exitTime = historyByRegistrationMap.get(info.registration).get(1).getTimestamp();
                        return !entryTime.isAfter(end) && !exitTime.isBefore(start);
                    })
                    .forEach(info -> map.computeIfPresent(info.spot, (k, v) -> {
                        LocalDateTime entryTime = historyByRegistrationMap.get(info.registration).get(0).getTimestamp();
                        LocalDateTime exitTime = historyByRegistrationMap.get(info.registration).get(1).getTimestamp();
                        long overlapDuration = exitTime.toEpochSecond(ZoneOffset.UTC) - entryTime.toEpochSecond(ZoneOffset.UTC);

                        return v + (overlapDuration / totalDuration) * 100.0;
                    }));
        });

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}

class ParkInfo implements Comparable<ParkInfo> {
    String registration;
    String spot;
    LocalDateTime timestamp;
    boolean entry;
    long durationPark;
    LocalDateTime entryTime;
    LocalDateTime exitTime;

    public ParkInfo(String registration, String spot, LocalDateTime timestamp, boolean entry) {
        this.registration = registration;
        this.spot = spot;
        this.timestamp = timestamp;
        this.entry = entry;
    }

    public String getRegistration() {
        return registration;
    }

    public String getSpot() {
        return spot;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isEntry() {
        return entry;
    }

    @Override
    public int compareTo(ParkInfo o) {
        return this.timestamp.compareTo(o.timestamp);
    }

    public void setDurationPark(long durationPark) {
        this.durationPark = durationPark;
    }

    public long getDurationPark() {
        return durationPark;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }
}

