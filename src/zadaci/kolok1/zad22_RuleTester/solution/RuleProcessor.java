package kolok1.zad22_RuleTester.solution;

import java.util.List;
import java.util.Optional;

public class RuleProcessor {

   public static <IN,OUT> void process(List<IN> inputs, List<Rule<IN,OUT>> rules){

       for (IN input : inputs) {
           System.out.println("Input: " + input);
           for(Rule<IN,OUT> rule : rules){
               Optional<OUT> result = rule.apply(input);
               if(result != Optional.empty()){
                   System.out.println("Result: " + result.get());
               }else{
                   System.out.println("Condition not met");
               }
           }
       }

    }
}


