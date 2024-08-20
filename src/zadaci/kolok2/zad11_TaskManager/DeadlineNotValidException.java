package kolok2.zad11_TaskManager;

import java.time.LocalDateTime;

public class DeadlineNotValidException extends Exception{
    public DeadlineNotValidException(LocalDateTime deadLineDate) {
        super(String.format("The deadline %s has already passed",deadLineDate));
    }
}
