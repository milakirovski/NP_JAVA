package kolok2.aud10_ispitna_MessageSystem;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Partition {
    int number; //number of partition
    TreeSet<Message> messages;

    public Partition(int number) {
        this.number = number;
        this.messages = new TreeSet<>();
    }

    public void addMessage(Message message) {

        if(message.timestamp.isBefore(MessageBroker.MINIMUM_DATE)){
            return;
        }
        if(messages.size() == MessageBroker.CAPACITY_PER_PARTITION){
            messages.remove(messages.first());
        }
        messages.add(message);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%2d : Count of messages:%6d\n", number, messages.size()));
        sb.append("Messages:\n");

        String messagesStr = messages.stream()
                .map(Message::toString)
                .collect(Collectors.joining("\n"));

        sb.append(messagesStr);

        return sb.toString();
    }
}
