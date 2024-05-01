package kolok1.examples.ex1;

import kolok1.examples.ex1.Counter;

import java.io.*;

public class WordCountTest {
    public static void readDataWithBufferedReader(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Counter result = bufferedReader.lines()
                .map(line -> new Counter(line))
                .reduce(new Counter(), (midResult, newResult) -> midResult.sum(newResult));

        System.out.println("The total count = " + result);
    }

    public static void main(String[] args) throws IOException {

        File file = new File("D:\\FINKI-2godina-predmeti-MOJA\\gitHubRepos2ndYear\\NaprednoProgramiranje_JAVA\\src\\students.txt");

        readDataWithBufferedReader(new FileInputStream(file));
    }
}
