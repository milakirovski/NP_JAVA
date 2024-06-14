package zad9;

import java.util.Locale;
import java.util.Scanner;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        // Read integers
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf(Locale.US,"%.2f\n", tInt.max());
        System.out.printf(Locale.US,"%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);

        // Read floats
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf(Locale.US,"%.2f\n", tFloat.max());
        System.out.printf(Locale.US,"%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);

        // Read doubles
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf(Locale.US,"%.2f\n", tDouble.max());
        System.out.printf(Locale.US,"%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple


