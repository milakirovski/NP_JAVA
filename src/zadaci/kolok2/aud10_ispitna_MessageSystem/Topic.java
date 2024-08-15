package kolok2.aud10_ispitna_MessageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Topic {
    String topicName;
    int partitionsCount;
    Map<Integer, Partition> partitions;

    public Topic(String topicName, int partitionsCount) {
        this.topicName = topicName;
        this.partitionsCount = partitionsCount;
        this.partitions = new TreeMap<>();

        //odma gi keirame particiite
        for (int i = 1; i <= partitionsCount; i++) {
            partitions.put(i, new Partition(i));
        }
    }

    void addMessage(Message message) throws PartitionDoesNotExistException {
        Integer partition = message.getPartition();

        if (partition == null) {
            partition = PartitionAssigner.assignPartition(message, partitionsCount);
        }

        if (!partitions.containsKey(partition)) {
            throw new PartitionDoesNotExistException(topicName, partitionsCount);
        }

        partitions.get(partition).addMessage(message);
    }

    void changeNumberOfPartitions(int newPartitionsNumber) throws UnsupportedOperationException {

        if (newPartitionsNumber < partitionsCount) {
            throw new UnsupportedOperationException("Partitions number cannot be decreased!");
        } else {
            for (int i = partitionsCount + 1; i <= newPartitionsNumber; i++) {
                partitions.put(i, new Partition(i));
            }
            partitionsCount = newPartitionsNumber;
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Topic: %10s Partitions: %5d\n.", topicName, partitionsCount));

        String partitionsStr = partitions.values().stream()
                .map(Partition::toString)
                .collect(Collectors.joining("\n"));

        sb.append(partitionsStr);

        return sb.toString();
    }
}
