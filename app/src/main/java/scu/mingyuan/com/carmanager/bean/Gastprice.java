package scu.mingyuan.com.carmanager.bean;

/**
 * Created by miomin on 16/3/18.
 */
public class Gastprice {

    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Gastprice{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
