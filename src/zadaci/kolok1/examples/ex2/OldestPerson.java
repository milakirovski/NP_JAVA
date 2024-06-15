package kolok1.examples.ex2;

import org.w3c.dom.ls.LSOutput;

import java.io.*;

public class OldestPerson {

    public static void oldestPerson(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        bufferedReader.lines().
                map(l -> new Person(l))
                .max( (p1,p2) -> p1.compareTo(p2) )
                .ifPresent(System.out::println);

    }
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("D:\\FINKI-2godina-predmeti-MOJA\\gitHubRepos2ndYear\\NaprednoProgramiranje_JAVA\\src\\kolok1\\examples\\ex2\\students.txt");

        oldestPerson(new FileInputStream(file));

    }
}
