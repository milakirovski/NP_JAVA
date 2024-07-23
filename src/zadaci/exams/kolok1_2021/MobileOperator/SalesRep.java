package exams.kolok1_2021.MobileOperator;

import java.util.*;
import java.util.stream.Collectors;

public class SalesRep {
    private String id;
    private double commission;
    private List<Customer> customerBills;

    public SalesRep(String id, List<Customer> customerBills) {
        this.id = id;
        this.commission = 0.0;
        this.customerBills = customerBills;
    }

    //475 4642771 M 248 90 14.94 2281930 S 139 48 6.19 4040003 M 189 100 11.90 5064198 M 159 78 9.32
    //    salesRepID [customerBill1] [customerBill2] ... [customerBillN].
    //    customerID package_type count_of_minutes count_of_SMS count_of_data_in_GB

    public static SalesRep createSalesRep(String line) throws InvalidIdException {
        String[] parts = line.split("\\s+");

        String saleRepId = parts[0];

        if(!validSalesRepId(saleRepId)){
            throw new InvalidIdException(String.format("%s is not a valid sales rep id", saleRepId));
        }

        List<Customer> customerBills = new ArrayList<Customer>();
        

        for (int i = 1; i < parts.length; i+=5) {

            try{
                Customer newCustomer = getCustomer(parts, i);
                customerBills.add(newCustomer);
            }catch (InvalidIdException e){
                System.out.println(e.getMessage());
            }
        }

        return new SalesRep(saleRepId,customerBills);
    }

    private static Customer getCustomer(String[] parts, int i) throws InvalidIdException {
        String customerId = parts[i];
        Packet packet = null;

        int count_of_minutes = Integer.parseInt(parts[i +2]);
        int count_of_SMS = Integer.parseInt(parts[i +3]);
        double count_of_data_in_GB = Double.parseDouble(parts[i +4]);

        if(parts[i +1].equals("S")){
            packet = new S();
        } else if (parts[i +1].equals("M")) {
            packet = new M();
        }

        Customer newCustomer = new Customer(customerId,packet,count_of_minutes,count_of_SMS,count_of_data_in_GB);

        if (!newCustomer.validCustomerId(customerId)){
            throw new InvalidIdException(String.format("%s is not a valid customer id", customerId));
        }
        return newCustomer;
    }

    private static boolean validSalesRepId(String id){
        
       int digits = (int) id.chars()
                .map(i -> (char) i)
                .filter(Character::isDigit)
                .count();
        
       return digits == id.length() && id.length() == 3 ;
    }


    //ID number_of_bills min_bill average_bill max_bill total_commission
    //Под bill се подразбира износот на сметката на некој клиент.
    //
    //    Еден sales rep добива провизија 4% од сметката на корисник на пакет M и 7% од сметката на корисник на пакет S.
    //    Извештаите да бидат испечатени сортирани во опаѓачки редослед според провизијата на sales rep-от.

    public int numberOfBills(){
        return customerBills.size();
    }

    public double totalCommission(){
        return customerBills.stream()
                .mapToDouble(i -> i.getPacketType().calculateCommission(i.getCount_of_minutes(),i.getCount_of_SMS(),i.getCount_of_data_in_GB()))
                .sum();
    }

    public double minBill(){
        return customerBills.stream()
                .mapToDouble(Customer::priceOfBill)
                .min().orElse(0.0);
    }

    public double maxBill(){
        return customerBills.stream()
                .mapToDouble(Customer::priceOfBill)
                .max().orElse(0.0);
    }

    public double averageBill(){
        return customerBills.stream()
                .mapToDouble(Customer::priceOfBill)
                .average().orElse(0.0);
    }

    public String getId() {
        return id;
    }


    @Override
    public String toString() {
        return String.format(Locale.US,"%s Count: %d Min: %.2f Average: %.2f Max: %.2f Commission: %.2f",
                id,
                numberOfBills(),
                minBill(),
                averageBill(),
                maxBill(),
                totalCommission());
    }
}
