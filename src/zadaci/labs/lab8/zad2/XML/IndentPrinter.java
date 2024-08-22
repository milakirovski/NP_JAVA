package labs.lab8.zad2.XML;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IndentPrinter {

    public static String printIndent(int level) {
        return IntStream.range(0,level).mapToObj(i -> "\t").collect(Collectors.joining());
    }
}
