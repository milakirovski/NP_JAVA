package kolok2.zad19_SortedMap.resolved;

import java.util.*;
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

    public static <K,V extends Comparable<V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

        // racno go pishuvam comparatorot za da mozam da se spravam so istite vrednosti za value
        Comparator<Map.Entry<K,V>> comparator = (entry1, entry2) -> {
            int rez = entry2.getValue().compareTo(entry1.getValue());
            if(rez == 0){
                return 1;
            }
            return rez;
        };

        System.out.println(map);

        return map.entrySet().stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(comparator.reversed())));

        //Dopolnitelen nacin dokolku treba da se otstranat duplikat vrednosti za value
        // bidejki e SortedSet gi vadi duplikatite, no vo nashiot slucaj ova ne treba da se koristi
        // Cisto kolku da go imame na um za dr zadaci =)
//        Comparator<Map.Entry<K,V>> comparator2 = Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder());
//
//        System.out.println(map);
//
//        return map.entrySet().stream()
//                .sorted(comparator2.reversed())
//                .collect(Collectors.toCollection(() -> new TreeSet<>(comparator2)));
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
