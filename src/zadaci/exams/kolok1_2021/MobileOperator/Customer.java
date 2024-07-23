package exams.kolok1_2021.MobileOperator;

import java.util.Locale;

public class Customer implements Comparable<Customer>{

    private String id;
    private Packet packetType;
    private int count_of_minutes;
    private int count_of_SMS;
    private double count_of_data_in_GB;

    public Customer(String id, Packet packetType, int count_of_minutes, int count_of_SMS, double count_of_data_in_GB) {
        this.id = id;
        this.packetType = packetType;
        this.count_of_minutes = count_of_minutes;
        this.count_of_SMS = count_of_SMS;
        this.count_of_data_in_GB = count_of_data_in_GB;
    }

    public String getId() {
        return id;
    }

    public Packet getPacketType() {
        return packetType;
    }

    public int getCount_of_minutes() {
        return count_of_minutes;
    }

    public int getCount_of_SMS() {
        return count_of_SMS;
    }

    public double getCount_of_data_in_GB() {
        return count_of_data_in_GB;
    }

    public boolean validCustomerId(String id){
        int digits = (int) id.chars()
                .map(i -> (char) i)
                .filter(Character::isDigit)
                .count();

        return digits == id.length() && id.length() == 7 ;
    }


    public double priceOfBill(){
        return packetType.calculateBillPrice(count_of_minutes,count_of_SMS,count_of_data_in_GB);
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", packetType=" + packetType +
                ", count_of_minutes=" + count_of_minutes +
                ", count_of_SMS=" + count_of_SMS +
                ", count_of_data_in_GB=" + count_of_data_in_GB +
                '}';
    }

    @Override
    public int compareTo(Customer o) {
        return Double.compare(this.priceOfBill(), o.priceOfBill());
    }
}
