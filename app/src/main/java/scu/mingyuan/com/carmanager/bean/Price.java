package scu.mingyuan.com.carmanager.bean;

/**
 * Created by miomin on 16/3/18.
 */
public class Price {

    private String type;//E90
    private double price;//7.31

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Price{" +
                "price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
