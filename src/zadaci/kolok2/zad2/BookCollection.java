package kolok2.zad2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class BookCollection {

    private List<Book> books;

    public BookCollection() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void printByCategory(String category) {
        books.stream()
                .filter(i -> i.getCategory().equalsIgnoreCase(category))
                .sorted(Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice))
                .forEach(System.out::println);
    }


    public List<Book> getCheapestN(int n) {
        return books.stream()
                .limit(n)
                .sorted(Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // 1. prv nacin na koristenje na Comparator functional interface so lamba izrazi
    /*
    static Comparator<Book> compareByTitleAndPrice = (b1,b2) -> {
        int res = b1.getTitle().compareToIgnoreCase(b2.getTitle());
        if(res == 0){
            res = Float.compare(b1.getPrice(), b2.getPrice());
        }
        return res;
    };

    static Comparator<Book> compareByPriceAndTitle = (b1,b2) ->{
      int res = Float.compare(b1.getPrice(), b2.getPrice());
      if(res == 0){
          res = b1.getTitle().compareToIgnoreCase(b2.getTitle());
      }
      return res;
    };
     */

    // 2. vtor nacin koristenje na Comparator functional interface so abstract class
    /*
   public static Comparator<Book> compareTitleAndPrice = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            int res = o1.getTitle().compareToIgnoreCase(o2.getTitle());
            if(res == 0){
                res = Float.compare(o1.getPrice(), o2.getPrice());
            }
            return res;
        }
    };

   public static Comparator<Book> comparePriceAndTitle = new Comparator<Book>() {
       @Override
       public int compare(Book o1, Book o2) {
           int res = Float.compare(o1.getPrice(), o2.getPrice());
           if(res == 0){
               res = o1.getTitle().compareToIgnoreCase(o2.getTitle());
           }
           return res;
       }
   };
     */

    // 3. tret nacin na koristenje na Comparator functional interface
    /*
    public static Comparator<Book> compareByTitleAndPrice = Comparator
            .comparing(Book::getTitle).thenComparing(Book::getPrice);

    public static Comparator<Book> compareByPriceAndTitle = Comparator
            .comparing(Book::getPrice).thenComparing(Book::getTitle);
     */

    // 4. cetvrtion nacin e vnatre vo sorted() da go napisheme Comparator-ot
    // na ovoj nacin e reshena finalno zadacata =)

}
