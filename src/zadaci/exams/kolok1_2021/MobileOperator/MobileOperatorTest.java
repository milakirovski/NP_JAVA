package exams.kolok1_2021.MobileOperator;

import java.util.Locale;

public class MobileOperatorTest {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---- READING OF THE SALES REPORTS ----");
        mobileOperator.readSalesRepData(System.in);
        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}
