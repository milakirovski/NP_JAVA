package kolok1.zad8;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LockedArchive extends Archive{
    private LocalDate dateToOpen; // датум до кој не смее да се отвори

    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    public String log(LocalDate openingDate){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // moze i bez formatter po default ima "-" megju datite
        String formattedDate = dateToOpen.format(formatter);

        if(openingDate.isBefore(dateToOpen)){
            return String.format("Item %s cannot be opened before %s",id,formattedDate);
        }
        return String.format("Item %d opened at %s",id,openingDate);
    }
}
