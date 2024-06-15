package kolok1.zad10;

public class InvalidOperatorException extends Exception{
    public InvalidOperatorException(String operator){
        super(String.format("Invalid operator: %s", operator));
    }
}
