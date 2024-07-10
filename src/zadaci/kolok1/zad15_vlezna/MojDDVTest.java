package kolok1.zad15_vlezna;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class AmountNotAllowedException extends Exception{
    AmountNotAllowedException(int sum){
        super(String.format("Receipt with amount %d is not allowed to be scanned",sum));
    }
}

public class MojDDVTest {

    public static void main(String[] args) throws IOException {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        try {
            mojDDV.readRecords(System.in);
        } catch (AmountNotAllowedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);


    }
}

class MojDDV{

    private List<Fiskalna> fiskalni;

    public MojDDV() {
        this.fiskalni = new ArrayList<>();
    }

    public void readRecords (InputStream inputStream) throws IOException, AmountNotAllowedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

       reader.lines()
                .map(i -> {
                    try{
                       return Fiskalna.of(i);
                    }catch (AmountNotAllowedException e){
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
               .filter(i -> i != null)
               .forEach(i -> fiskalni.add(i));

    }

    public void printTaxReturns (OutputStream outputStream) throws IOException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));

        for (Fiskalna fiskalna : fiskalni) {
            writer.println(fiskalna);
            writer.flush();
        }
    }
}

interface TaxReturn{
    double getTax();
}

class Item implements TaxReturn{
    private int price;
    private String taxType;

    public Item(int price, String taxType) {
        this.price = price;
        this.taxType = taxType;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public double getTax() {
        switch(taxType){
            case "A": return (price * 0.18) * 0.15;
            case "B": return (price * 0.05) * 0.15;
        }
        return 0 ;
    }
}

class Fiskalna{
    private long id;
    private List<Item> items;

    public Fiskalna(long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public static Fiskalna of(String line) throws AmountNotAllowedException {
        String[] lineParts = line.split("\\s+");
        long id = Long.parseLong(lineParts[0]);
        List<Item> items = new ArrayList<>();

        for (int i = 1; i < lineParts.length; i+=2){
            items.add(new Item(Integer.parseInt(lineParts[i]),lineParts[i+1]));
        }

        if(sumOfPricesPerFiskalna(items) > 30000)
            throw new AmountNotAllowedException(sumOfPricesPerFiskalna(items));

        return new Fiskalna(id,items);
    }

    public static int sumOfPricesPerFiskalna(List<Item> items){
        return items.stream()
                .mapToInt(i -> i.getPrice())
                .sum();
    }

    private static double sumOfTaxesPerFiskalna(List<Item> items){
        return items.stream()
                .mapToDouble(i -> i.getTax())
                .sum();
    }

    @Override
    public String toString() {
        return String.format(Locale.US,"%d %d %.2f",id, sumOfPricesPerFiskalna(items),sumOfTaxesPerFiskalna(items));
    }
}

