package kolok2.zad14_Car;
import java.util.*;
import java.util.stream.Collectors;

public class CarCollection {
    private List<Car> cars;

    public CarCollection() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }


    public void sortByPrice(boolean ascending) {
        Comparator<Car> comparator = Comparator.comparingInt(Car::getPrice)
                .thenComparingDouble(Car::getPower);

        if(!ascending){
            comparator = comparator.reversed();
        }

        cars = cars.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Car> filterByManufacturer(String manufacturer) {
        return cars.stream()
                .filter(car -> car.getManufacturer().equalsIgnoreCase(manufacturer))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }


    public List<Car> getList() {
        return cars;
    }
}
