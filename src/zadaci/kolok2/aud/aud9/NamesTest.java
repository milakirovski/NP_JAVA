package kolok2.aud.aud9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class NamesTest {

    public static Map<String,Integer> createFromFile (String path) throws FileNotFoundException {

        Map<String,Integer> result = new HashMap<String,Integer>();
        InputStream is = new FileInputStream(path);
        Scanner scanner = new Scanner(is);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            String name = parts[0];
            Integer frequency = Integer.valueOf(parts[1]);
            result.put(name, frequency);
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // ako ne se bara da se sortirani so HashMap da odime
        Map<String,Integer> boyNames = NamesTest.createFromFile("D:\\FINKI-2godina-predmeti-MOJA\\gitHubRepos2ndYear\\NP_JAVA\\src\\zadaci\\kolok2\\aud\\aud9\\data\\boynames.txt");
        Map<String,Integer> girlNames = NamesTest.createFromFile("D:\\FINKI-2godina-predmeti-MOJA\\gitHubRepos2ndYear\\NP_JAVA\\src\\zadaci\\kolok2\\aud\\aud9\\data\\girlnames.txt");

        System.out.println(boyNames);
        System.out.println(girlNames);

        boyNames.keySet(); // vrakja mnozestvo na site keys(iminja) vo hashTabelata, kako lista, collection


        //set for all unique names =)
        Set<String> allNames = new HashSet<String>();
        allNames.addAll(boyNames.keySet());
        allNames.addAll(girlNames.keySet());


        Map<String,Integer> unisexNames = new HashMap<>();

        allNames.stream()
                .filter(name -> boyNames.containsKey(name) && girlNames.containsKey(name))
                .forEach(name -> {
                    unisexNames.put(name, boyNames.get(name) + girlNames.get(name));
//                    System.out.println(String.format("%s : Male: %d Female: %d Total: %d",
//                        name,
//                        boyNames.get(name),
//                        girlNames.get(name),
//                        boyNames.get(name) + girlNames.get(name)));
                });
//        System.out.println(unisexNames);
        Set<Map.Entry<String, Integer>> entrySet = unisexNames.entrySet(); //mnozestvo od site parovi vo hashTabelata
        entrySet.stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(String.format("%s : %d", entry.getKey(), entry.getValue())));

    }

}
