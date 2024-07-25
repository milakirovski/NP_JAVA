package auditoriski.aud7_Randomizacija.ArrangeLetters;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ArrangeLettersTest {

    public static String rearrangeSentence(String sentence) {
        String[] words = sentence.split("\\s+");

        return Arrays.stream(words)
                .map(i -> rearrangeWord(i))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    private static String rearrangeWord(String word){
        // Prvin golemite bukvi se sortiraat, pa posle malite
        return word.chars()
                .sorted()
                .mapToObj(ch -> String.valueOf((char)ch))
                .collect(Collectors.joining());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();

        System.out.println(ArrangeLettersTest.rearrangeSentence(sentence));


    }
}
