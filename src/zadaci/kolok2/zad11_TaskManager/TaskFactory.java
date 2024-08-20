package kolok2.zad11_TaskManager;

import java.time.LocalDateTime;

public class TaskFactory {

    public static ITask createTask(String line) throws DeadlineNotValidException {
        String[] parts = line.split(",");
        String category = parts[0];
        String name = parts[1];
        String description = parts[2];

        //basic task
        ITask task = new Task(category,name,description);

        if(parts.length == 4){
            // basic task + priority
            //          or
            // basic task + deadline
            String notSure = parts[3];
            if(notSure.contains("-") || notSure.contains(":")){
                LocalDateTime deadLineDate = LocalDateTime.parse(notSure);
                task = new DeadlineTask(task,deadLineDate);

                if(checkDeadLineIfValid(task.getDeadlineDate())){
                    throw new DeadlineNotValidException(task.getDeadlineDate());
                }

            }else{
                task = new PriorityTask(task,Integer.parseInt(notSure));
            }

        }else if(parts.length == 5){
            //basic task + priority + deadline
            task = new PriorityTask(new DeadlineTask(task,LocalDateTime.parse(parts[3])),Integer.parseInt(parts[4]));
            if(checkDeadLineIfValid(task.getDeadlineDate())){
                throw new DeadlineNotValidException(task.getDeadlineDate());
            }
        }

        return task;
    }

    public static boolean checkDeadLineIfValid(LocalDateTime deadLine){
        return deadLine.isBefore(LocalDateTime.of(2020, 6, 2, 23, 59, 59));
    }
}
