package kolok2.zad25_OnlineShop;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(String.format("Product with id %s does not exist in the online shop!",message));
    }
}


class Product {
    String category;
    String id;
    String name;
    LocalDateTime createdAt;
    double price;
    int quantitySold;

    public Product(String category, String id, String name, LocalDateTime createdAt, double price) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        quantitySold = 0;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getPrice() {
        return price;
    }

    //Product{id='050be27b', name='product0', createdAt=2019-01-14T23:17:46.715710, price=2913.14, quantitySold=14}

    public void setQuantitySold(int qs) {
        this.quantitySold = qs;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", price=" + price +
                ", quantitySold=" + quantitySold +
                '}';
    }
}

class OnlineShop {

    Map<String, Product> productsById;
    Map<String, List<Product>> productsByCategory;
    Map<String, List<Product>> productsByPageSize;

    OnlineShop() {
        this.productsById = new HashMap<>();
        this.productsByCategory = new HashMap<>();
        this.productsByPageSize = new HashMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
        Product product = new Product(category, id, name, createdAt, price);

        productsById.put(id,product);
        productsByCategory.putIfAbsent(category,new ArrayList<>());
        productsByCategory.get(category).add(product);
    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException{

        if(!productsById.containsKey(id)){
                throw new ProductNotFoundException(id);
        }

        Product productToBuy = productsById.get(id);
        productToBuy.setQuantitySold(quantity);
        return productToBuy.getPrice()*quantity;
    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {

        List<List<Product>> groupedProducts = new ArrayList<>(new ArrayList<>());

        List<Product> products = productsByCategory.get(category);

        if(!productsByCategory.containsKey(category)){
            products = new ArrayList<>(productsById.values());
        }

        Comparator<Product> comparator = FactoryComparator.sortBasedOnComparator(comparatorType);

        products = products.stream().sorted(comparator).collect(Collectors.toList());

       List<Product> productToBeAdded = new ArrayList<>();

        for (Product product : products) {
            productToBeAdded.add(product);
            if(productToBeAdded.size() == pageSize){
                groupedProducts.add(new ArrayList<>(productToBeAdded));
                productToBeAdded.clear();
            }
        }

        // Add the last group if it has fewer than groupSize items
        if (!productToBeAdded.isEmpty()) {
            groupedProducts.add(productToBeAdded);
        }

        return groupedProducts;
    }

}

class FactoryComparator {
    public static Comparator<Product> sortBasedOnComparator(COMPARATOR_TYPE comparatorType) {
        Comparator<Product> comparator = null;
        if(comparatorType == COMPARATOR_TYPE.NEWEST_FIRST){
            comparator = Comparator.comparing(Product::getCreatedAt).reversed();
        }else if(comparatorType == COMPARATOR_TYPE.OLDEST_FIRST){
            comparator = Comparator.comparing(Product::getCreatedAt);
        }else if(comparatorType == COMPARATOR_TYPE.LOWEST_PRICE_FIRST){
            comparator = Comparator.comparing(Product::getPrice);
        }else if(comparatorType == COMPARATOR_TYPE.HIGHEST_PRICE_FIRST){
            comparator = Comparator.comparing(Product::getPrice).reversed();
        }else if (comparatorType == COMPARATOR_TYPE.MOST_SOLD_FIRST){
            comparator = Comparator.comparingInt(Product::getQuantitySold).reversed();
        }else {
            comparator = Comparator.comparingInt(Product::getQuantitySold);
        }
        return comparator;
    }
}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category=null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}

