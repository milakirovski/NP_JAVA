package kolok1.examples.generic_example;

public class Toy implements Drawable<Toy>{

    @Override
    public Toy drawElement() {
        return this;
    }
}
