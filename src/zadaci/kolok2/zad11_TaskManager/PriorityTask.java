package kolok2.zad11_TaskManager;

import java.time.LocalDateTime;

public class PriorityTask extends TaskDecorator{

    private int priority;

    public PriorityTask(ITask task, int priority) {
        super(task);
        this.priority = priority;
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
        return priority;
    }

    @Override
    public LocalDateTime getDeadlineDate() {
        return task.getDeadlineDate();
    }

    @Override
    public long getTimeDifference() {
        return task.getTimeDifference();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(task.toString(),0,task.toString().length() - 1);
        sb.append(", priority=").append(getPriority());
        sb.append('}');
        return sb.toString();
    }
}
