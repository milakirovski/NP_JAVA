package exams.kolok1_2021.MobileOperator;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MobileOperator {

    private List<SalesRep> salesRepList;

    public MobileOperator() {
        this.salesRepList = new ArrayList<SalesRep>();
    }

    //475 4642771 M 248 90 14.94 2281930 S 139 48 6.19 4040003 M 189 100 11.90 5064198 M 159 78 9.32
    //    salesRepID [customerBill1] [customerBill2] ... [customerBillN].
    //    customerID package_type count_of_minutes count_of_SMS count_of_data_in_GB
    public void readSalesRepData(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        salesRepList = br.lines()
                .map(i -> {
                    try {
                        return SalesRep.createSalesRep(i);
                    } catch (InvalidIdException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        return "MobileOperator{" +
                ", salesRepList=" + salesRepList +
                '}';
    }

    //ID number_of_bills min_bill average_bill max_bill total_commission
    //Под bill се подразбира износот на сметката на некој клиент.
    //    Сметката на даден клиент се пресметува така што се собира основната цена на пакетот со дополнителните трошоци направени како резултат на надминување на бесплатните минути/пораки/GB интернет.
    //    Еден sales rep добива провизија 4% од сметката на корисник на пакет M и 7% од сметката на корисник на пакет S.
    //    Извештаите да бидат испечатени сортирани во опаѓачки редослед според провизијата на sales rep-от.
    public void printSalesReport(PrintStream out) {

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(out));

        salesRepList.stream()
                .sorted(Comparator.comparing(SalesRep::totalCommission).reversed())
                .forEach(printWriter::println);

        printWriter.flush();
        printWriter.close();

    }
}
