package kolok1.zad5;

import java.util.ArrayList;
import java.util.List;

public class MinMax <T extends Comparable<T>>{

    private T currentMax;
    public T currentMin;
    public List<T> list;
    public MinMax(){
        this.list = new ArrayList<>();
    }

    /**
     * updates min and max values as new items arrive
     * @param element
     */
    public void update(T element){
        list.add(element);

       if (list.size() == 1){
            currentMin = element;
            currentMax = element;
        }else{
            if(element.compareTo(currentMin) < 0){
                currentMin = element;
            }else if(element.compareTo(currentMax) > 0){
                currentMax = element;
            }
        }
    }

    public long differentFromCurrMinMax(){
        return  list.stream().filter(i -> (!(i.equals(currentMax)) && !(i.equals(currentMin)))).count();
    }
    public T max(){
        return currentMax;
    }

    public T min(){
        return currentMin;
    }

    @Override
    public String toString() {
        return min() + " " + max() + " " + differentFromCurrMinMax() + "\n";
    }
}
