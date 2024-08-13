package kolok2.zad1.resolved;

import java.util.*;

public class Audition {

    private Map<String, List<Participant>> participantsTable;

    public Audition() {
        this.participantsTable = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant newParticipant = new Participant(city, code, name, age);

        if(participantsTable.containsKey(city)) {
            // go ima toj grad
            //proverime dali ima covek so odreden code, duplicat
            List<Participant> participants = participantsTable.get(city);

            Participant participantOptional = participants.stream()
                    .filter(participant -> participant.getCode().equals(newParticipant.getCode()))
                    .findFirst()
                    .orElse(null);

            if(participantOptional==null) {
                participants.add(newParticipant);
            }
        }else{
            // go nema naploni nova koficka <CITY,LIST<Participants>>
            // dodaj go participantot vo listata
            List<Participant> participants = new ArrayList<>();
            participants.add(newParticipant);
            participantsTable.put(city,participants);
        }
    }

    public void listByCity(String city) {
        participantsTable.get(city)
                .stream()
                .sorted(Comparator.comparing(Participant::getName).thenComparingInt(Participant::getAge).thenComparing(Participant::getCode))
                .forEach(System.out::println);
    }

}
