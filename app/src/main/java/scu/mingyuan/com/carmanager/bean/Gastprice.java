package scu.mingyuan.com.carmanager.bean;

import java.io.Serializable;

/**
 * Created by 莫绪旻 on 16/3/18.
 */
public class Gastprice implements Serializable{

    private String name;//92#
    private double price;//6.77

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
