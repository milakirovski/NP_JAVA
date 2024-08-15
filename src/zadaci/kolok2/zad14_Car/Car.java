package kolok2.zad14_Car;

import java.text.DecimalFormat;

public class Car {
    private String manufacturer;
    private String model;
    private int price;
    private  float power;

    public Car(String manufacturer, String model, int price, float power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public float getPower() {
        return power;
    }

    @Override
    public String toString() {

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String powerFormated = decimalFormat.format(power);
        return String.format("%s %s (%sKW) %d",
                manufacturer,
                model,
                powerFormated,
                price);
    }
}
