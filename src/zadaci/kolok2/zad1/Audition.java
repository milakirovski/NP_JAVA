package kolok2.zad1;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class Audition {
    Map<String, Set<Participant>> participantsByCity;

    public Audition() {
        participantsByCity = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant p = new Participant(city, code, name, age);

        participantsByCity.putIfAbsent(city,new HashSet<>());

        for (Participant p1 : participantsByCity.get(city)) {
            if(p1.compareTo(p) == 0){
                return;
            }
        }
        participantsByCity.get(city).add(p);
    }


    public void listByCity(String city) {
        participantsByCity.get(city)
                .stream()
                .sorted(Comparator.comparing(Participant::getName).thenComparingInt(Participant::getAge).thenComparing(Participant::getCode))
                .forEach(System.out::println);
    }
}
