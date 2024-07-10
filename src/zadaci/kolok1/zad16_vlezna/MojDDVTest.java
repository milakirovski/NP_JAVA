package kolok1.zad16_vlezna;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(int sum) {
        super(String.format("Receipt with amount %d is not allowed to be scanned",sum));
    }

}

class MojDDV {

    private List<Bill> bills;

    public MojDDV() {
        bills = new ArrayList<Bill>();
    }

    public void readRecords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        bills = br.lines()
                .map(i -> {
                    try {
                        return Bill.createBill(i);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

    }


    public void printTaxReturns(PrintStream out) {

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(out));

       bills.forEach(i -> {
           printWriter.println(i);
           printWriter.flush();
       });

    }


    public void printStatistics(PrintStream out) {
        DoubleSummaryStatistics doubleSummaryStatistics = bills.stream().mapToDouble(Bill::sumOfTaxReturnBill).summaryStatistics();

        System.out.printf(Locale.US,"min:\t%05.03f\nmax:\t%05.03f\nsum:\t%05.03f\ncount:\t%d\navg:\t%05.03f\n",
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getSum(),
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getAverage());
    }
}

class Bill {
    private long id;
    private List<Item> items;

    public Bill(long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public static Bill createBill(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        long id = Long.parseLong(parts[0]);
        List<Item> items = new ArrayList<>();

        for (int i=1, j=2; i<parts.length && j < parts.length; i+=2, j+=2){
            items.add(new Item(Integer.parseInt(parts[i]),TaxType.valueOf(parts[j])));
        }

        int sumOfAmountsBill = items.stream().mapToInt(Item::getPrice).sum();

       if(sumOfAmountsBill > 30000){
           throw new AmountNotAllowedException(sumOfAmountsBill);
       }

        return new Bill(id, items);
    }

    public int sumOfAmountsBill() {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public double sumOfTaxReturnBill(){
        return items.stream().mapToDouble(Item::calculateTaxReturn).sum();
    }

    @Override
    public String toString() {
        return String.format(Locale.US,"%10d\t%10d\t%10.5f", id, sumOfAmountsBill(), sumOfTaxReturnBill());
    }
}


enum TaxType{
    A,B,V
}

class Item {
    private int price;
    private TaxType tax;

    public Item(int price, TaxType tax) {
        this.price = price;
        this.tax = tax;
    }

    public double calculateTaxReturn(){
        double taxReturnByType = 0.0;

        if(tax == TaxType.A){
            taxReturnByType = price * 0.18;
        }else if(tax == TaxType.B){
            taxReturnByType = price * 0.05;
        }
        return taxReturnByType * 0.15;
    }


    @Override
    public String toString() {
        return "price=" + price + ", tax=" + tax;
    }

    public int getPrice() {
        return price;
    }
}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();
        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}