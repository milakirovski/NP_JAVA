package auditoriski.aud7_Randomizacija.BenfordsLaw;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BenfordLawExperiment{

    private List<Integer> numbers;

    public BenfordLawExperiment() {
        this.numbers = new ArrayList<>();
    }

    public void readData(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //        Scanner scanner = new Scanner(inputStream);
//        while(scanner.hasNext()){
//            int number = scanner.nextInt();
//            numbers.add(number);
//        }

        numbers = reader.lines()
                .filter(i -> !i.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    private int countFistDigitOcc(int digit){
        return (int) numbers.stream()
                .map(this::getFirstDigit)
                .filter(i -> i == digit)
                .count();
    }

    private int getFirstDigit(int number) {

        while(number >= 10){
            number /= 10;
        }
        return number;
    }

    private int numbersSize(){
        return numbers.size();
    }

    public double getFirstDigitOccPercentage(int digit){
        return ((double) countFistDigitOcc(digit) / numbersSize()) * 100;
    }

    @Override
    public String toString() {
        return IntStream.range(1,10)
                .mapToObj(i ->String.format("Occ for %d: %.2f %%",i,this.getFirstDigitOccPercentage(i)))
                .collect(Collectors.joining("\n"));
    }
}

public class BendfordsLawTest {

    public static void main(String[] args) {
        BenfordLawExperiment experiment = new BenfordLawExperiment();
        try {
            experiment.readData(new FileInputStream("D:\\FINKI-2godina-predmeti-MOJA\\gitHubRepos2ndYear\\NP_JAVA\\src\\zadaci\\auditoriski\\aud7_Randomizacija\\BenfordsLaw\\data\\librarybooks.txt"));
            Locale.setDefault(Locale.US);

            System.out.println(experiment);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
