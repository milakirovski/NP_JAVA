package exams.kolok1_2021.MobileOperator;

public class Packet {
    private int freeMinutes;
    private int freeSMS;
    private double freeInternet;
    private int basePrice;

    public Packet(int freeMinutes, int freeSMS, double freeInternet, int basePrice) {
        this.freeMinutes = freeMinutes;
        this.freeSMS = freeSMS;
        this.freeInternet = freeInternet;
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "freeMinutes=" + freeMinutes +
                ", freeSMS=" + freeSMS +
                ", freeInternet=" + freeInternet +
                ", basePrice=" + basePrice +
                '}';
    }

    public int exceededMinutes(int count_of_minutes){
        return 0;
    }

    public int exceededSMS(int count_of_SMS){
        return 0;
    }

    public double exceededInternet(double count_of_data_in_GB){
        return 0.0;
    }

    public double calculateBillPrice(int count_of_minutes, int count_of_SMS, double count_of_data_in_GB){
        return 0.0;
    }

    public double calculateCommission(int count_of_minutes, int count_of_SMS, double count_of_data_in_GB){
        return 0.0;
    }
}
