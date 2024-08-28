package kolok2.zad16_BlockContainer;
import java.util.*;
import java.util.stream.Collectors;

class Block<T extends Comparable<T>> implements Comparable<Block<T>> {
    Set<T> elements;

    public Block() {
        this.elements = new TreeSet<T>();
    }

    public void addElement(T element) {
        elements.add(element);
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    @Override
    public int compareTo(Block<T> o) {
        Comparator<Block<T>> comp = Comparator.comparing(Block::toString);
        return comp.compare(this, o);
    }
}

class BlockContainer<T extends Comparable<T>> {
    List<Block<T>> blocks;
    Block<T> lastBlock;
    int n;

    public BlockContainer(int n) {
        this.blocks = new ArrayList<>();
        this.n = n;
    }

    public void add(T a) {
        if (blocks.isEmpty() || lastBlock.elements.size() >= n) {
            Block<T> newBlock = new Block<T>();
            newBlock.addElement(a);
            lastBlock = newBlock;
            blocks.add(lastBlock);
        } else {
            lastBlock.addElement(a);
        }
    }

    public boolean remove(T a) {
        boolean result = false;

        if(!blocks.isEmpty()){
            Block<T> s = blocks.get(blocks.size() - 1);
            result = s.elements.remove(a);

            if(s.elements.isEmpty()){
                blocks.remove(blocks.size() - 1);
            }
        }
        return result;
    }

    public void sort() {

       List<T> allElements =  blocks.stream()
                .map(block -> block.elements)
               .flatMap(Collection::stream)
                .sorted()
               .collect(Collectors.toList());

       //rebuild the Block Container -> empty it and them invoke add(T el) =)
        blocks = new ArrayList<Block<T>>();
        for (T element : allElements) {
            add(element);
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(blocks.stream().map(block -> block.toString()).collect(Collectors.joining(",")));
        return sb.toString();
    }
}

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for (int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for (int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}

// Вашиот код овде



