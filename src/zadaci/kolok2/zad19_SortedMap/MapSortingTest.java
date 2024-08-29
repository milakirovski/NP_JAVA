package kolok2.zad19_SortedMap;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class MapSortingTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        List<String> l = readMapPairs(scanner);
        if(n==1){
            Map<String, Integer> map = new HashMap<>();
            fillStringIntegerMap(l, map);
            SortedSet<Map.Entry<String, Integer>> s = entriesSortedByValues(map);
            System.out.println(s);
        } else {
            Map<Integer, String> map = new HashMap<>();
            fillIntegerStringMap(l, map);
            SortedSet<Map.Entry<Integer, String>> s = entriesSortedByValues(map);
            System.out.println(s);
        }

    }

    private static <K,V extends Comparable<V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K, V> map) {

        Comparator<Map.Entry<K,V>> comparator = (entry1,entry2) -> {
            if(entry1.getValue().equals(entry2.getValue())){
                return 1;
            }
            else
                return entry1.getValue().compareTo(entry2.getValue());
        };

        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>(comparator.reversed());


        for (Map.Entry<K, V> entry : map.entrySet()) {
            sortedEntries.add(entry);
        }
        System.out.println(map);
        return sortedEntries;



    }


    private static List<String> readMapPairs(Scanner scanner) {
        String line = scanner.nextLine();
        String[] entries = line.split("\\s+");
        return Arrays.asList(entries);
    }

    static void fillStringIntegerMap(List<String> l, Map<String,Integer> map) {
        l.stream()
                .forEach(s -> map.put(s.substring(0, s.indexOf(':')), Integer.parseInt(s.substring(s.indexOf(':') + 1))));
    }

    static void fillIntegerStringMap(List<String> l, Map<Integer, String> map) {
        l.stream()
                .forEach(s -> map.put(Integer.parseInt(s.substring(0, s.indexOf(':'))), s.substring(s.indexOf(':') + 1)));
    }

    //вашиот код овде
}