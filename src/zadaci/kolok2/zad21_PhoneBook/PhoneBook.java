package kolok2.zad21_PhoneBook;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneBook {

    private Map<String, Set<Contact>> contactsByNameTable;
    private Map<String, Set<Contact>> contactsByNumberTable;
    private Set<String> allNumbers;
    private static Comparator<Contact> THE_COMPARATOR = Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber);

    public PhoneBook() {
        this.contactsByNumberTable = new TreeMap<>();
        this.contactsByNameTable = new HashMap<String, Set<Contact>>();
        this.allNumbers = new HashSet<String>();
    }


    public void addContact(String name, String number) throws DuplicateNumberException {
        Contact newContact = new Contact(name, number);
        if (allNumbers.contains(number)) {
            throw new DuplicateNumberException(String.format("Duplicate number: %s", number));
        } else {
            allNumbers.add(number);
            contactsByNameTable.putIfAbsent(name, new TreeSet<>(THE_COMPARATOR));
            contactsByNameTable.get(name).add(newContact);

            contactsByNumberTable.putIfAbsent(number, new TreeSet<>(THE_COMPARATOR));
            contactsByNumberTable.get(number).add(newContact);
        }
    }


    public void contactsByNumber(String number) {
        List<Map.Entry<String, Set<Contact>>> list = contactsByNumberTable.entrySet().stream()
                .filter(contact -> contact.getKey().contains(number))
                .toList();

        if (list.isEmpty()) {
            System.out.println("NOT FOUND");
            return;
        }

       list.stream()
               .map(Map.Entry::getValue)
               .flatMap(Collection::stream)// Flatten the stream of lists into a single stream of contacts
               .sorted(THE_COMPARATOR)
               .forEach(System.out::println);

    }

    public void contactsByName(String name) {
        if (contactsByNameTable.containsKey(name)) {
            contactsByNameTable.get(name).forEach(System.out::println);
        } else {
            System.out.println("NOT FOUND");
        }
    }
}
