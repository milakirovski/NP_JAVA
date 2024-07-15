package exams.kolok1_2021.cakeShop_2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class InvalidOrderException extends Exception {
    public InvalidOrderException(int idOrder) {
        super(String.format("The order with id %d has less items than the minimum allowed.",idOrder));
    }
}

class CakeShopApplication{

    private List<Order> orders;
    public int minOrderItems;

    public CakeShopApplication(){
       this.orders = new ArrayList<>();
    }

    public CakeShopApplication(int minOrderItems) {
        this.minOrderItems = minOrderItems;
    }

    public void readCakeOrders(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        orders = br.lines().map(i -> {
            try{
                return Order.of(i,minOrderItems);
            } catch (InvalidOrderException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));

    }


    public void printLongestOrder(PrintStream out) {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

       orders.stream()
                .sorted().forEach(i -> {
                   pw.println(i);
                   pw.flush();
               });
        pw.close();
    }
}

enum ItemType{
    CAKE, PIE
}

class Item{
    private String name;
    private int price;
    private ItemType itemType;

    public Item() {
        this.name = "defaultName";
        this.price = 0;
        this.itemType = null;
    }

    public Item(String name, int price, ItemType itemType) {
        this.name = name;
        this.price = price;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }
}

class Order implements Comparable<Order>{
    int orderID;
    List<Item> items;

    public Order(int orderID, List<Item> items) {
        this.orderID = orderID;
        this.items = items;
    }

    public static Order of(String line, int minItemsPerOrder) throws InvalidOrderException {
        String[] parts = line.split("\\s+");
        int orderID = Integer.parseInt(parts[0]);
        List<Item> items = new ArrayList<>();

        for (int i = 1; i < parts.length; i+=2) {
            String itemName = parts[i];
            char type =  parts[i].charAt(0);
            int price = Integer.parseInt(parts[i+1]);

            if(type == 'C'){
                items.add(new Item(itemName, price, ItemType.CAKE));
            }else if(type == 'P'){
                items.add(new Item(itemName, price + 50, ItemType.PIE));
            }else
                items.add(new Item());
        }
        Order newOrder = new Order(orderID, items);
        if(newOrder.countNumberOFItems() < minItemsPerOrder){
            throw new InvalidOrderException(newOrder.orderID);
        }
        return newOrder;
    }

    public int countNumberOFItems(){
        return items.size();
    }

    public int sumOfOrder(){
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public int countPies(){
        return (int) items.stream().filter(i -> i.getItemType()==ItemType.PIE).count();
    }

    public int countCakes(){
        return (int) items.stream().filter(i -> i.getItemType()==ItemType.CAKE).count();
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(o.sumOfOrder(), this.sumOfOrder());
    }

    @Override
    public String toString() {
        return orderID + " " +  countNumberOFItems() + " " + countPies() + " " + countCakes() + " " + sumOfOrder();
    }
}

public class CakeShopApplicationTest2{
    public static void main(String[] args) {
        CakeShopApplication cakeShopApplication = new CakeShopApplication(4);

        System.out.println("--- READING FROM INPUT STREAM ---");
        cakeShopApplication.readCakeOrders(System.in);

        System.out.println("--- PRINTING LARGEST ORDER TO OUTPUT STREAM ---");
        cakeShopApplication.printLongestOrder(System.out);
    }
}
