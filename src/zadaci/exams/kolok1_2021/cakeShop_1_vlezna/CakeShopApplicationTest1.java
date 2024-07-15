package exams.kolok1_2021.cakeShop_1_vlezna;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CakeShopApplication{

    private List<Order> orders;

    public CakeShopApplication(){
       this.orders = new ArrayList<>();
    }

    public int readCakeOrders(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        orders = br.lines().map(Order::of).collect(Collectors.toCollection(ArrayList::new));

        return orders.stream().mapToInt(Order::countNumberOFItems).sum();

    }


    public void printLongestOrder(PrintStream out) {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

        Order biggestOrderMade = orders.stream()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new))
                .getLast();

        pw.println(biggestOrderMade);
        pw.flush();
        pw.close();
    }
}

class Item{
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
}

class Order implements Comparable<Order>{
    int orderID;
    List<Item> items;

    public Order(int orderID, List<Item> items) {
        this.orderID = orderID;
        this.items = items;
    }

    public static Order of(String line){
        String[] parts = line.split("\\s+");
        int orderID = Integer.parseInt(parts[0]);
        List<Item> items = new ArrayList<>();

        for (int i = 1; i < parts.length; i+=2) {
            String itemName = parts[i];
            int price = Integer.parseInt(parts[i+1]);
            items.add(new Item(itemName, price));
        }
        return new Order(orderID,items);
    }

    public int countNumberOFItems(){
        return items.size();
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(this.countNumberOFItems(), o.countNumberOFItems());
    }

    @Override
    public String toString() {
        return orderID + " " + countNumberOFItems();
    }
}

public class CakeShopApplicationTest1 {
    public static void main(String[] args) {
        CakeShopApplication cakeShopApplication = new CakeShopApplication();

        System.out.println("--- READING FROM INPUT STREAM ---");
        System.out.println(cakeShopApplication.readCakeOrders(System.in));

        System.out.println("--- PRINTING LARGEST ORDER TO OUTPUT STREAM ---");
        cakeShopApplication.printLongestOrder(System.out);
    }
}
