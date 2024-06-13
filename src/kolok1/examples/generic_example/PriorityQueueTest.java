package kolok1.examples.generic_example;

public class PriorityQueueTest {

    public static void main(String[] args) {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();

        priorityQueue.addElement("middle1",49);
        priorityQueue.addElement("middle2",50);
        priorityQueue.addElement("middle3",51);
        priorityQueue.addElement("top",100);
        priorityQueue.addElement("bottom",10);

        System.out.println(priorityQueue);

        String element;

        while((element = priorityQueue.removeElement()) != null){
            System.out.println(element);
        }
    }
}
