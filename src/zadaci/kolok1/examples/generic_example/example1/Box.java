package kolok1.examples.generic_example.example1;

public class Box <E>{
    private E object;

    public Box(E object){
        this.object = object;
    }

    @Override
    public String toString() {
        return object.toString();
    }
}
