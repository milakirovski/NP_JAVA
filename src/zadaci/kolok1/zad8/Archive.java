package kolok1.zad8;

import java.time.LocalDate;

public class Archive implements Comparable<Archive>{

    protected int id;
    protected LocalDate dateArchived;

    public Archive(){
        this.id = 0;
        this.dateArchived = LocalDate.of(2000, 1, 1);
    }
    public Archive(int id) {
        this.id = id;
        this.dateArchived = LocalDate.of(2000, 1, 1);
    }

    public Archive(int id, LocalDate dateArchived) {
        this.id = id;
        this.dateArchived = dateArchived;
    }

    public void setDateArchived(LocalDate dateArchived) {
        this.dateArchived = dateArchived;
    }

    @Override
    public int compareTo(Archive o) {
        return Integer.compare(this.id,o.id);
    }
}
