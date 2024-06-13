package kolok1.zad9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Triple<T extends Number> {
    private T a;
    private T b;
    private T c;
    List<T> list;

    public Triple(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
    }

    public double max(){
        double max = a.doubleValue();
        if(max < b.doubleValue()) {
            max = b.doubleValue();
        }
        if(max < c.doubleValue()){
            max = c.doubleValue();
        }
        return max;
    }

    public double avarage(){
        return (a.doubleValue() + b.doubleValue() + c.doubleValue()) / 3.0;
    }

    public void sort(){
        list = list.stream()
                .sorted(Comparator.comparing(Number::doubleValue))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", list.get(0).doubleValue(), list.get(1).doubleValue(), list.get(2).doubleValue());
    }

}
