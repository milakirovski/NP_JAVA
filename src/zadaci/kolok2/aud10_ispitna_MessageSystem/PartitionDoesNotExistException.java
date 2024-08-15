package kolok2.aud10_ispitna_MessageSystem;

public class PartitionDoesNotExistException extends Exception {
    public PartitionDoesNotExistException(String topicName, int partitionNumber ) {
        super(String.format("The topic %s does not have a partition with number %d", topicName, partitionNumber));
    }
}
