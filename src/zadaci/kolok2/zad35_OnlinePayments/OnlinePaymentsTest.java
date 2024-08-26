package kolok2.zad35_OnlinePayments;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Item implements Comparable<Item>{
    String name;
    int price;

    public Item(String name, int price) {
        this.price = price;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int compareTo(Item o) {
        return Integer.compare(this.price, o.price);
    }

    @Override
    public String toString() {
        return name + " " + price;
    }
}

class Student{
    String id;
    List<Item> items; ////Trik beshe so itemsot, ja koristev TreeSet i nekjeshe deka mozat povekje studenti da naracaat ist item zatoa List si e najsigurno =)

    public Student(String id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public int getNeto(){
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public int getFree(){
        int fee = (int) Math.round((1.14 / 100) * getNeto());
        if(fee < 3){
            fee = 3;
        }else if(fee > 300){
            fee = 300;
        }
        return fee;
    }

    public int getTotal(){
        return getNeto() + getFree();
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("Student: %s Net: %d Fee: %d Total: %d\n", id, getNeto(), getFree(), getTotal()));
        builder.append("Items:\n");
        AtomicInteger i = new AtomicInteger(1);

        String itemsStr = items.stream()
                .sorted(Comparator.comparingInt(Item::getPrice).reversed())
                .map(item -> String.format("%d. %s",i.getAndIncrement(),item.toString()))
                .collect(Collectors.joining("\n"));

        builder.append(itemsStr);
        return builder.toString();
    }
}

class OnlinePayments{

    Map<String,Student> studentsMap;

    public OnlinePayments() {
        this.studentsMap = new HashMap<>()
    }

    public void readItems(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        List<String> inputLines = reader.lines().collect(Collectors.toList());

        for (String line : inputLines) {
            String[] parts = line.split(";");
            String studentId = parts[0];
            String name = parts[1];
            int price = Integer.parseInt(parts[2]);
            Item item = new Item(name,price);

            studentsMap.putIfAbsent(studentId,new Student(studentId));
            studentsMap.get(studentId).items.add(item);
        }


    }

    public void printStudentReport(String id, PrintStream out) {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));

        if(!studentsMap.containsKey(id)){
            writer.println(String.format("Student %s not found!",id));
        }else{
            writer.println(studentsMap.get(id).toString());
        }

        writer.flush();
    }
}

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}