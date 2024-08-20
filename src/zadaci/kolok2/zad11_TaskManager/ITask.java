package kolok2.zad11_TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

public interface ITask {
    String getCategory();
    String getDescription();
    String getName();
    int getPriority();
    LocalDateTime getDeadlineDate();
    long getTimeDifference();
}
