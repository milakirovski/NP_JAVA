package kolok1.zad8;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SpecialArchive extends Archive{
    private int maxOpen; // чуваат максимален број на дозволени отварања
    private int openingTimes; // kolku do sega pati bila otvorena

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.openingTimes = 0;

    }

    public String log(LocalDate date) {
        ++openingTimes;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);

        if(openingTimes > maxOpen){
            return String.format("Item %d cannot be opened more than %d times",id,maxOpen);
        }
        return String.format("Item %d opened at %s",id,formattedDate);
    }
}
