package zad10;

public class Evaluator {

    public static <T extends Comparable<T>> boolean evaluateExpression(T left, T right, String operator ) throws InvalidOperatorException {
        IEvaluator<T> iEvaluator = EvaluatorBuilder.build(operator);
        return iEvaluator.evaluate(left, right);
    }
}



