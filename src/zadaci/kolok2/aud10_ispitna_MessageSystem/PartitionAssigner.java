package kolok2.aud10_ispitna_MessageSystem;

class PartitionAssigner {
    public static Integer assignPartition(Message message, int partitionsCount) {
        return (Math.abs(message.key.hashCode()) % partitionsCount) + 1;
    }
}