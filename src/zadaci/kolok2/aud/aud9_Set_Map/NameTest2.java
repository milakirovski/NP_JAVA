package kolok2.aud.aud9_Set_Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NameTest2 {

    public static Map<String,Integer> readFromFile (String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        Map<String,Integer> namesMap = new HashMap<String,Integer>();

        while((line=br.readLine()) != null){
            String[] parts = line.split("\\s++");
            String name = parts[0];
            Integer freq = Integer.parseInt(parts[1]);
            namesMap.put(name,freq);
        }
        return namesMap;
    }

    public static void main(String[] args) throws IOException {

        Map<String,Integer> boysNameMap = readFromFile("D:\\FINKI-2godina-predmeti-MOJA\\gitHubRepos2ndYear\\NP_JAVA\\src\\zadaci\\kolok2\\aud\\aud9\\data\\boynames.txt");
        Map<String,Integer> girlsNameMap = readFromFile("D:\\FINKI-2godina-predmeti-MOJA\\gitHubRepos2ndYear\\NP_JAVA\\src\\zadaci\\kolok2\\aud\\aud9\\data\\girlnames.txt");

//        System.out.println(boysNameMap);
//        System.out.println(girlsNameMap);


//        System.out.println(boysNameMap.keySet());
//        System.out.println(girlsNameMap.keySet());

        Set<String> allNames = new HashSet<>();
        allNames.addAll(boysNameMap.keySet());
        allNames.addAll(girlsNameMap.keySet());

//        System.out.println(allNames);

        Map<String, Integer> unisexNamesMap = new HashMap<>();

        allNames.stream()
                .filter(name -> boysNameMap.containsKey(name) && girlsNameMap.containsKey(name))
                .forEach(name -> unisexNamesMap.put(name, boysNameMap.get(name) + girlsNameMap.get(name)));

        unisexNamesMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(name -> System.out.println(name.getKey() + " " + name.getValue()));
    }
}
