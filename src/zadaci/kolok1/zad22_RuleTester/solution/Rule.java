package kolok1.zad22_RuleTester.solution;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Rule<IN, OUT>{
    private Predicate<IN> predicate; //filter
    private Function<IN, OUT> function; //map

    public Rule(Predicate<IN> predicate, Function<IN, OUT> function) {
        this.predicate = predicate;
        this.function = function;
    }

    public Optional<OUT> apply(IN input) {
        if(predicate.test(input))
            return Optional.of(function.apply(input));
        else return Optional.empty();
    }
}
