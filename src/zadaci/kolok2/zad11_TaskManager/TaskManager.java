package kolok2.zad11_TaskManager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {

    private Map<String, List<ITask>> tasksByCategory;

    public TaskManager() {
        this.tasksByCategory = new TreeMap<>();
    }

    public void readTasks(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        tasksByCategory = br.lines()
                .map(line -> {
                    try {
                        return TaskFactory.createTask(line);
                    } catch (DeadlineNotValidException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        ITask::getCategory,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    public void printTasks(PrintStream out, boolean includePriority, boolean includeCategory) {
        PrintWriter pw = new PrintWriter(out);

        Comparator<ITask> priorityTrue = Comparator.comparingInt(ITask::getPriority).thenComparingLong(ITask::getTimeDifference);
        Comparator<ITask> priorityFalse = Comparator.comparingLong(ITask::getTimeDifference);

        Set<String> categories = tasksByCategory.keySet();

        if (includeCategory) {
            for (String category : categories) {
                pw.println(category.toUpperCase());
                tasksByCategory.get(category)
                        .stream()
                        .sorted(includePriority ? priorityTrue : priorityFalse)
                        .forEach(task -> pw.println(task.toString()));
            }
        } else {
            tasksByCategory.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .sorted(includePriority ? priorityTrue : priorityFalse)
                    .forEach(pw::println);
        }

        pw.flush();
    }
}
