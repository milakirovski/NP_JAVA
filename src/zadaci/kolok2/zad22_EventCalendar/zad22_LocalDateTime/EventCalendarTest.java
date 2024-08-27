package kolok2.zad22_EventCalendar.zad22_LocalDateTime;
// Poubavo e da se koristi LocalDateTime, Date e macenje mora i Calendar klasata da se iskoristi
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;


public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
//        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            LocalDateTime date = LocalDateTime.parse(parts[2],dtf);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        String desiredDate = scanner.nextLine();
        LocalDateTime date = LocalDateTime.parse(desiredDate,dtf);
//        LocalDateTime date = LocalDateTime.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde

class WrongDateException extends Exception {
    public WrongDateException(String date) {
        super(String.format("Wrong date: %s", date));
    }
}

class EventCalendar {
    int year;
    Map<String,Set<Event>> eventsByDate;
    Map<Integer,Integer> eventsByMonths;

    public EventCalendar(int year) {
        this.year = year;
        this.eventsByDate = new HashMap<>();
        this.eventsByMonths = new HashMap<>();

        //inicijalno site ke imaat nula kako vrednost za boj na eventi
        IntStream.range(1,13).forEach(month -> eventsByMonths.put(month,0));

    }


    public void addEvent(String name, String location, LocalDateTime date) throws WrongDateException {

        if(year != date.getYear()) {
            //Wrong date: Thu Feb 14 11:00:00 UTC 2013
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss 'UTC' yyyy");
            String formattedDate = date.format(dtf);

            throw new WrongDateException(formattedDate);
        }

        //15.12.2012
        String formattedDate = formatKeyForDate(date);

        eventsByDate.putIfAbsent(formattedDate, new TreeSet<>(Comparator.comparing(Event::getDate).thenComparing(Event::getName)));
        eventsByDate.get(formattedDate).add(new Event(name,location,date));

        eventsByMonths.computeIfPresent(
                date.getMonthValue(),
                (k,v) -> v+1);
    }

    public void listEvents(LocalDateTime date) {

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

    private String formatKeyForDate(LocalDateTime date){
        //15.12.2012
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(dtf);
    }
}

class Event{
    String name;
    String location;
    LocalDateTime date;

    public Event(String name, String location, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        //dd MMM, YYY HH:mm at [location], [name].
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, yyy HH:mm");
        return String.format("%s at %s %s", date.format(dtf), location, name);
    }
}