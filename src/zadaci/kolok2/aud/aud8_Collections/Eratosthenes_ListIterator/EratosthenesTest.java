package kolok2.aud.aud8_Collections.Eratosthenes_ListIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EratosthenesTest {

    public static boolean isPrime(int number){

        for (int i = 0; i <= number / 2; i++){
            if(number % i == 0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>();

        numbers = IntStream.range(2,101)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        ListIterator<Integer> iterator_i = numbers.listIterator();

        // outer iterator (i - elements)
        while(iterator_i.hasNext()){
        // we need to check weather the i element is Prime
            Integer number_i = iterator_i.next();
            if(isPrime(number_i)){
                // inner Iterator (j - elements)
                ListIterator<Integer> iterator_j = numbers.listIterator(iterator_i.nextIndex());
                while(iterator_j.hasNext()){
                    if(iterator_j.next() % number_i == 0){
                        iterator_j.remove();
                    }
                }
            }
        }

        System.out.println(numbers);
    }
}
