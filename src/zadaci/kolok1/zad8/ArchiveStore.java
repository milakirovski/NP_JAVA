package kolok1.zad8;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArchiveStore {
    private List<Archive> archiveList;

    List<String> logs = new ArrayList<>();

    public ArchiveStore() {
        this.archiveList = new ArrayList<>();
    }

    /**
     * метод за архивирање елемент item на одреден датум date
     * @param item
     * @param date
     */
    public void archiveItem(Archive item, LocalDate date){
        item.setDateArchived(date);
        archiveList.add(item);

        // item archived
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        logs.add(String.format("Item %d archived at %s",item.id,formattedDate));
    }

    public boolean hasItem(int id){
        return archiveList.stream()
                .anyMatch(a -> a.id == id);
    }

    /**
     * метод за отварање елемент од архивата со зададен id и одреден датум date
     * @param id
     * @param date
     * @throws NonExistingItemException
     */
    public void openItem(int id, LocalDate date) throws NonExistingItemException {
        if(!hasItem(id)){
            throw new NonExistingItemException(id);
        }

        // filer and get the item
        Archive archive = archiveList.stream().filter(i -> i.id == id).findFirst().orElse(null);

        // archive opened
        if(archive instanceof LockedArchive){
            LockedArchive lockedArchive = (LockedArchive) archive;
            logs.add(lockedArchive.log(date));
        }else if(archive instanceof SpecialArchive){
            SpecialArchive specialArchive = (SpecialArchive) archive;
            logs.add(specialArchive.log(date));
        }
    }

    public String getLog(){
        StringBuilder sb = new StringBuilder();

        logs.forEach(log -> sb.append(log).append("\n"));

        return sb.toString();

    }

}
