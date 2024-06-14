package zad11;

import java.util.Locale;

public class GenericFraction <T extends Number, U extends Number>{
    private T numerator;
    private U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        this.numerator = numerator;
        this.denominator = denominator;
        if(denominator.doubleValue() == 0){
            throw new ZeroDenominatorException("Denominator cannot be zero");
        }
    }


    public double toDouble(){
        return numerator.doubleValue() / denominator.doubleValue();
    }

    public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {

        double resultNumerator = this.numerator.doubleValue() * gf.denominator.doubleValue() + this.denominator.doubleValue() * gf.numerator.doubleValue();
        double resultDenominator = this.denominator.doubleValue() * gf.denominator.doubleValue();

        return new GenericFraction<Double, Double>(resultNumerator, resultDenominator);
    }

    //Calculate the Greatest Common Divisor (GCD)

    static double gcd(double a, double b) {
        if (b == 0)
            return a;
        if (a < b)
            return gcd(a, b - a);
        else
            return gcd(b, a - b);
    }

    public double gcd() {
        return gcd(this.numerator.doubleValue(), this.denominator.doubleValue());
    }


    @Override
    public String toString() {
        double gcd = this.gcd();
        return String.format(Locale.US,"%.2f / %.2f", this.numerator.doubleValue() / gcd,
                this.denominator.doubleValue() / gcd);
    }
}
