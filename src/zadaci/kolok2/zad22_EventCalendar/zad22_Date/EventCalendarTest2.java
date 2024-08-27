package kolok2.zad22_EventCalendar.zad22_Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventCalendarTest2 {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = sdf.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        String desiredDate = scanner.nextLine();
        Date date = sdf.parse(desiredDate);
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

class WrongDateException extends Exception {
    public WrongDateException(String date) {
        super(String.format("Wrong date: %s", date));
    }
}

class EventCalendar {
    int year;
    Map<String, Set<Event>> eventsByDate;
    Map<Integer, Integer> eventsByMonths;

    public EventCalendar(int year) {
        this.year = year;
        this.eventsByDate = new HashMap<>();
        this.eventsByMonths = new HashMap<>();

        // Initially, all months have zero events
        for (int i = 1; i <= 12; i++) {
            eventsByMonths.put(i, 0);
        }
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int eventYear = cal.get(Calendar.YEAR);

        if (year != eventYear) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC' yyyy");
            String formattedDate = sdf.format(date);
            throw new WrongDateException(formattedDate);
        }

        String formattedDate = formatKeyForDate(date);

        eventsByDate.putIfAbsent(formattedDate, new TreeSet<>(Comparator.comparing(Event::getDate).thenComparing(Event::getName)));
        eventsByDate.get(formattedDate).add(new Event(name, location, date));

        eventsByMonths.computeIfPresent(
                cal.get(Calendar.MONTH) + 1,  // Months are 0-based in Calendar, so we add 1
                (k, v) -> v + 1);
    }

    public void listEvents(Date date) {

        String formattedDate = formatKeyForDate(date);
        if(!eventsByDate.containsKey(formattedDate)) {
            System.out.println("No events on this day!");
            return;
        }

        eventsByDate.get(formattedDate).forEach(System.out::println);
    }

    public void listByMonth() {
        eventsByMonths.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    private String formatKeyForDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }
}

class Event {
    String name;
    String location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        return String.format("%s at %s, %s", sdf.format(date), location, name);
    }
}

