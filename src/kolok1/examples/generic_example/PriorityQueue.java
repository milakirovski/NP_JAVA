package kolok1.examples.generic_example;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<T> {
    // item, priority za sekoj element
    private List<PriorityQueueElement<T>> elements;

    public PriorityQueue() {
        elements = new ArrayList<>();
    }

    public void addElement(T item, int priority) {
        PriorityQueueElement<T> newElement = new PriorityQueueElement<>(item, priority);

        for (int i = 0; i < elements.size(); i++) {
            if (newElement.compareTo(elements.get(i)) <= 0){
                break;
            }
            elements.add(i, newElement);
        }
    }

    public T removeElement() {
        if(elements.size() == 0){
            return null;
        }

        return elements.remove(elements.size() - 1).getElement();
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
