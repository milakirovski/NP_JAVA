package auditoriski.aud5_Generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * T - type
 * E - element
 * V - value
 * K - key
 */
public class Box <E extends Drawable<E>>{
    private final List<E> elements;
    private static final Random random = new Random();


    public Box() {
        this.elements = new ArrayList<E>();
    }

    public void add(E element) {
        elements.add(element);
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }

    public E draw(){
        if (elements.isEmpty()){
            return null;
        }
       return elements.remove(random.nextInt(elements.size()));
    }

    @Override
    public String toString() {
        return this.elements.toString();
    }
}
