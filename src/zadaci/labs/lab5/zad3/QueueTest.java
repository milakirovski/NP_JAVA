package labs.lab5.zad3;

import java.util.LinkedList;
import java.util.Scanner;

class EmptyQueueException extends Exception {
    public EmptyQueueException() {
        super("EmptyQueueException");
    }
}

class Node<T> {
    T value;
    Node<T> next;

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    public T getElement() {
        return value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}

class Queue<T> {

    Node<T> head;

    public Queue() {
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (head == null) {
            head = new Node<>(newNode.getElement(), newNode.next);
        } else {
            Node<T> current = head;
            while (current.getNext() != null) { // Traverse to the end of the list
                current = current.getNext();
            }
            current.setNext(newNode); // Link the new node to the last node
        }
    }

    public T dequeue() throws EmptyQueueException {
        if (head == null) {
            throw new EmptyQueueException();
        }
        T value = head.getElement(); // Get the value at the front of the queue
        head = head.getNext(); // Move the head to the next node
        return value;
    }

    public T peek() throws EmptyQueueException {
        if (head == null) {
            throw new EmptyQueueException();
        }
        return head.getElement(); // Return the value at the front of the queue without removing it
    }

    public int count() {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public T inspect() throws EmptyQueueException {
        if (head == null) {
            throw new EmptyQueueException();
        } else {
            Node<T> current = head;
            while (current.getNext() != null) { // Traverse to the end of the list
                current = current.getNext();
            }
            return current.getElement();
        }
    }
}

public class QueueTest {

    public static void main(String[] args) throws EmptyQueueException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //Simple test case with one int element
            int t = jin.nextInt();
            Queue<Integer> queue = new Queue<Integer>();
            System.out.println("Queue empty? - " + queue.isEmpty());
            System.out.println("Queue count? - " + queue.count());
            System.out.println("Queue enqueue " + t);
            queue.enqueue(t);
            System.out.println("Queue empty? - " + queue.isEmpty());
            System.out.println("Queue count? - " + queue.count());
            System.out.println("Queue dequeue? - " + queue.dequeue());
            System.out.println("Queue empty? - " + queue.isEmpty());
            System.out.println("Queue count? - " + queue.count());
        }
        if (k == 1) { //a more complex test with strings
            Queue<String> queue = new Queue<String>();
            int counter = 0;
            while (jin.hasNextInt()) {
                String t = jin.next();
                queue.enqueue(t);
                ++counter;
            }
            for (int i = 0; i < counter; ++i) {
                System.out.println(queue.dequeue());
            }
            queue.enqueue(jin.next());
            System.out.println("Queue inspect? - " + queue.inspect());
            System.out.println("Queue peek? - " + queue.peek());
            queue.enqueue(queue.dequeue());
            queue.enqueue(jin.next());
            System.out.println("Queue inspect? - " + queue.inspect());
            System.out.println("Queue peek? - " + queue.peek());
        }
        if (k == 2) {
            Queue<String> queue = new Queue<String>();
            String next = "";
            int counter = 0;
            while (true) {
                next = jin.next();
                if (next.equals("stop")) break;
                queue.enqueue(next);
                ++counter;
            }
            while (!queue.isEmpty()) {
                if (queue.count() < counter) System.out.print(" ");
                System.out.print(queue.dequeue());
            }
        }
        if (k == 3) { //random testing
            Queue<Double> queue = new Queue<Double>();
            LinkedList<Double> java_queue = new LinkedList<Double>();
            boolean flag = true;
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                double q = Math.random();
                if (q < 0.5) {
                    double t = Math.random();
                    queue.enqueue(t);
                    java_queue.addFirst(t);
                }
                if (q < 0.8 && q >= 0.5) {
                    if (!java_queue.isEmpty()) {
                        double t1 = java_queue.removeLast();
                        double t2 = queue.dequeue();
                        flag &= t1 == t2;
                    } else {
                        flag &= java_queue.isEmpty() == queue.isEmpty();
                    }
                }
                if (q < 0.9 && q >= 0.8) {
                    if (!java_queue.isEmpty()) {
                        double t1 = java_queue.peekLast();
                        double t2 = queue.peek();
                        flag &= t1 == t2;
                    } else {
                        flag &= java_queue.isEmpty() == queue.isEmpty();
                    }
                }
                if (q < 1 && q >= 0.9) {
                    if (!java_queue.isEmpty()) {
                        double t1 = java_queue.peekFirst();
                        double t2 = queue.inspect();
                        flag &= t1 == t2;
                    } else {
                        flag &= java_queue.isEmpty() == queue.isEmpty();
                    }
                }
                flag &= java_queue.size() == queue.count();
            }
            System.out.println("Compared to the control queue the results were the same? - " + flag);
        }
        if (k == 4) { //performance testing
            Queue<Double> queue = new Queue<Double>();
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                if (Math.random() < 0.5) {
                    queue.enqueue(Math.random());
                } else {
                    if (!queue.isEmpty()) {
                        queue.dequeue();
                    }
                }
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
        if (k == 5) { //Exceptions testing
            Queue<String> queue = new Queue<String>();
            try {
                queue.dequeue();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                queue.peek();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                queue.inspect();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}

