package kolok2.aud.aud8_Collections.Eratosthenes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EratosthenesTest {

    public static boolean isPrime(int n) {
        for (int i = 2; i <= n/2; i++) {
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<Integer>();

        numbers = IntStream.range(2,101)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < numbers.size(); i++){
            if(isPrime(numbers.get(i))){
                for (int j = i+1; j < numbers.size(); j++){
                    if(numbers.get(j) % numbers.get(i) == 0){
                        numbers.remove(numbers.get(j));
                        --j;
                    }
                }
            }
        }
        System.out.println(numbers);
    }
}
