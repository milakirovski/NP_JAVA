package kolok2.zad11_TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

public class DeadlineTask extends TaskDecorator {
    private LocalDateTime deadlineDate;

    public DeadlineTask(ITask task, LocalDateTime deadlineDate) {
        super(task);
        this.deadlineDate = deadlineDate;
    }


    @Override
    public String getCategory() {
        return task.getCategory();
    }

    @Override
    public String getDescription() {
        return task.getDescription();
    }

    @Override
    public String getName() {
        return task.getName();
    }

    @Override
    public int getPriority() {
        return task.getPriority();
    }

    @Override
    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    @Override
    public long getTimeDifference() {
        return Duration.between(LocalDateTime.now(), deadlineDate).toMillis();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(task.toString(), 0, task.toString().length() - 1);
        sb.append(", deadline=").append(getDeadlineDate());
        sb.append('}');
        return sb.toString();
    }
}
