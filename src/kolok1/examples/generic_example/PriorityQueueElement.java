package kolok1.examples.generic_example;

public class PriorityQueueElement<T> implements Comparable<PriorityQueueElement<T>> {

    private T element;
    private int priority;

    public PriorityQueueElement(T element, int priority) {
        this.element = element;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return element + " " + priority;
    }

    public T getElement() {
        return element;
    }

    @Override
    public int compareTo(PriorityQueueElement<T> other)  {
        return Integer.compare(this.priority, other.priority);
    }
}
