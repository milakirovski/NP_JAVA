package kolok1.zad10;

public class EvaluatorBuilder{

     // The syntax for a generic method includes a list of type parameters, inside angle brackets, which appears before the method's return type

    /**
     * @param operator
     * @return
     * @param <T>
     * @throws InvalidOperatorException
     */
    public static <T extends Comparable<T>> IEvaluator<T> build (String operator) throws InvalidOperatorException {

        switch (operator){
            case ">":
                return (left, right) -> left.compareTo(right) > 0;
            case "==":
                return (left, right) -> left.compareTo(right) == 0;
            case "!=":
                return (left, right) -> left.compareTo(right) != 0;
            case "<":
                return (left, right) -> left.compareTo(right) < 0;
        }
        throw new InvalidOperatorException(operator); // ova jas si go dodadov ne se bara vo zadacata, cisto za vezba !
    }
}
