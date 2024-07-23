package exams.kolok1_2021.MobileOperator;

public class S extends Packet{

    private static int freeMinutes = 100;
    private static  int freeSMS = 50;
    private static  double freeInternet = 5.0;
    private static int basePrice = 500;

    private static int additionalPerMinutePrice = 5;
    private static int additionalPerSMSPrice = 6 ;
    private static double additionalPerInternet = 25.0;

    private static double commissionRate = 0.07;

    public S() {
        super(freeMinutes,freeSMS,freeInternet,basePrice);
    }

    @Override
    public int exceededMinutes(int count_of_minutes) {
        int exceededMinutes =  count_of_minutes - freeMinutes;

        if(exceededMinutes < 0){
            exceededMinutes = 0;
        }
        return exceededMinutes * additionalPerMinutePrice;
    }

    @Override
    public int exceededSMS(int count_of_SMS) {
        int exceededSMS =  count_of_SMS - freeSMS;

        if(exceededSMS < 0){
            exceededSMS = 0;
        }
        return exceededSMS * additionalPerSMSPrice;
    }

    @Override
    public double exceededInternet(double count_of_data_in_GB) {
        double exceededInternet =  count_of_data_in_GB - freeInternet;

        if(exceededInternet < 0){
            exceededInternet = 0;
        }
        return exceededInternet * additionalPerInternet;
    }

    // се собира основната цена на пакетот со дополнителните трошоци направени како резултат на надминување на бесплатните минути/пораки/GB интернет.
    @Override
    public double calculateBillPrice(int count_of_minutes, int count_of_SMS, double count_of_data_in_GB){
        return basePrice + exceededMinutes(count_of_minutes) + exceededSMS(count_of_SMS) + exceededInternet(count_of_data_in_GB);
    }

    @Override
    public double calculateCommission(int count_of_minutes, int count_of_SMS, double count_of_data_in_GB) {
        return calculateBillPrice(count_of_minutes,count_of_SMS,count_of_data_in_GB) * commissionRate;
    }
}
