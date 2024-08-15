package kolok2.aud10_ispitna_MessageSystem;

import java.time.LocalDateTime;

public class Message implements Comparable<Message>{
    LocalDateTime timestamp;
    String message;
    Integer partition;
    String key;

    public Message(LocalDateTime timestamp, String message, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.key = key;
        this.partition = null; //partition e null;
    }

    public Message(LocalDateTime timestamp, String message, Integer partition, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.partition = partition;
        this.key = key;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Integer getPartition() {
        return partition;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Message{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public int compareTo(Message o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}
