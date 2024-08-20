package kolok2.zad11_TaskManager;

public abstract class TaskDecorator implements ITask{

    ITask task;

    public TaskDecorator(ITask task) {
        this.task = task;
    }
}
