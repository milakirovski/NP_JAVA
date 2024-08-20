package kolok2.zad11_TaskManager;


import java.time.LocalDateTime;

public class Task implements ITask{
    private String category;
    private String name;
    private String description;

    public Task(String category, String name, String description) {
        this.category = category;
        this.name = name;
        this.description = description;
    }


    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    @Override
    public LocalDateTime getDeadlineDate() {
        return null;
    }

    @Override
    public long getTimeDifference() {
//        return 1000000;
        return Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
