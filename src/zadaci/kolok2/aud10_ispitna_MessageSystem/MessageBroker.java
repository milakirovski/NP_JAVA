package kolok2.aud10_ispitna_MessageSystem;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MessageBroker {
    static LocalDateTime MINIMUM_DATE;
    static Integer CAPACITY_PER_PARTITION;
    Map<String, Topic> topics;

    public MessageBroker(LocalDateTime minimumDate, Integer partitionsCount){
        MINIMUM_DATE = minimumDate;
        CAPACITY_PER_PARTITION = partitionsCount;
        this.topics = new TreeMap<>();
    }

    public void addTopic(String topicName, int partitionsCount) {
        if(!topics.containsKey(topicName)){
            topics.put(topicName, new Topic(topicName, partitionsCount));
        }
    }

    public void addMessage(String topic, Message message) throws PartitionDoesNotExistException {

        if(message.timestamp.isBefore(MINIMUM_DATE)){
            return;
        }

        topics.get(topic).addMessage(message);
    }

    public void changeTopicSettings(String topicName, int partitionsCount) throws UnsupportedOperationException {
        topics.get(topicName).changeNumberOfPartitions(partitionsCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Topics with %d topics:\n",topics.size()));

        String topicsStr = topics.values().stream()
                .map(Topic::toString)
                .collect(Collectors.joining("\n"));

        sb.append(topicsStr);
        return sb.toString();
    }
}
