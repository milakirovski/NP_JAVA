package exams.kolok1_2021.LineProcessor_vlezna;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


class LineProcessor{

    List<String> lines;

    public LineProcessor() {
        this.lines = new ArrayList<>();
    }

    public void readLines(InputStream in, PrintStream out, char a) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

        lines = br.lines().collect(Collectors.toList());

        String maxString = "";
        int currMax = 0;
        for (String line : lines){
            if(countCharacterOccurrences(line,a) >= currMax){
                currMax = countCharacterOccurrences(line,a);
                maxString = line;
            }
        }

        pw.println(maxString);
        pw.flush();
        pw.close();

    }

    public int countCharacterOccurrences(String line, char a){
        return (int) line.chars()
                .map(i -> (char) i)
                .map(Character::toLowerCase)
                .filter(i -> i == a)
                .count();
    }


}

public class LineProcessorTest {
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

        lineProcessor.readLines(System.in, System.out, 'a');
    }

}

