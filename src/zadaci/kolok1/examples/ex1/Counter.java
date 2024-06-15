package kolok1.examples.ex1;

public class Counter {

    private int lines;
    private int words;
    private int characters;

    public Counter(int lines, int words, int characters) {
        this.lines = lines;
        this.words = words;
        this.characters = characters;
    }

    public Counter(){
        lines = 0;
        words = 0;
        characters = 0;
    }
   public Counter(String line){
       ++lines;
       words += line.split("\\s+").length;
       characters += line.length();
    }

    public Counter sum(Counter other){
        return new Counter((this.lines + other.lines) , (this.words + other.words), (this.characters + other.characters));
    }


    @Override
    public String toString() {
        return String.format("lines: %d  words: %d  characters: %d \n", lines, words, characters);
    }
}

