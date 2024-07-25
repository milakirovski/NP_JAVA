package auditoriski.aud7_Randomizacija.Finalists;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class InvalidPickerArgument extends Exception{

    public InvalidPickerArgument(String message) {
        super(message);
    }
}

class FinalistPicker{
    private int finalists;
    private static final Random RANDOM = new Random();// ednash za sekogash (za ista namena), pozelno e da e static, ramnomerna raspredelba se generira pri sekoja instanca na objekt od Random

    public FinalistPicker(int finalists) {
        this.finalists = finalists;
    }

    public int getFinalists() {
        return finalists;
    }

    public List<Integer> pick(int n) throws InvalidPickerArgument {

        if(n > finalists){
            throw new InvalidPickerArgument(String.format("The number %d cannot exceed the number of finalists",n));
        }

        if(n <= 0){
            throw new InvalidPickerArgument(String.format("The number n=%d must be a positive number",n));
        }
//        List<Integer> pickedFinalists = new ArrayList<>();
//
//        while(pickedFinalists.size() != n){
//            // RANDOM.nextInt(30) => 0 - 29 (exclusive)
//            int pick = RANDOM.nextInt(finalists) + 1; // [0-bound) + 1 --> [1,bound]
//
//            if(!pickedFinalists.contains(pick)){
//                pickedFinalists.add(pick);
//            }
//        }

        return RANDOM.ints(2*n, 1, finalists + 1)
                .boxed()
                .distinct() // samo uniq se zadruvaat
                .limit(n) // samo prvite n gi zemame
                .collect(Collectors.toList());

//        return pickedFinalists;
    }
}
public class FinalistTest {
    public static void main(String[] args) {

        FinalistPicker picker = new FinalistPicker(5);

        try{
            System.out.println(picker.pick(3));
        }catch (InvalidPickerArgument e){
            System.out.println(e.getMessage());
        }
    }
}
