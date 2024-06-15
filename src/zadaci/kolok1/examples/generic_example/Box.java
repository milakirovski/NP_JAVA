package kolok1.examples.generic_example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * V - value
 * E - element
 * T - type
 */
public class Box<E extends Drawable> {

    private List<E> elements;
    public static Random random = new Random();

    public Box(){
        this.elements = new ArrayList<>();
    }


    public void add(E element){
        elements.add(element);
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }

    public E drawElement(){
        if(elements.isEmpty()){
            return null;
        }
//        int index = random.nextInt(elements.size());
//        E elem = elements.get(index);
//        elements.remove(elem);
//        return elem;
        return elements.remove(random.nextInt(elements.size()));
    }
}
