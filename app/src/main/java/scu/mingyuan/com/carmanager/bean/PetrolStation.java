package scu.mingyuan.com.carmanager.bean;

import java.util.List;

/**
 * Created by miomin on 16/3/18.
 */
public class PetrolStation {

    private String id;
    private String name;
    private String area;
    private String areaname;
    private String address;
    private String brandname;
    private String type;
    private String discount;
    private String exhaust;
    private String position;
    private double lon;
    private double lat;
    private List<Price> price;
    private List<Gastprice> gastprice;
    private String fwlsmc;
    private int distance;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getExhaust() {
        return exhaust;
    }

    public void setExhaust(String exhaust) {
        this.exhaust = exhaust;
    }

    public String getFwlsmc() {
        return fwlsmc;
    }

    public void setFwlsmc(String fwlsmc) {
        this.fwlsmc = fwlsmc;
    }

    public List<Gastprice> getGastprice() {
        return gastprice;
    }

    public void setGastprice(List<Gastprice> gastprice) {
        this.gastprice = gastprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
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
        return "PetrolStation{" +
                "address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", areaname='" + areaname + '\'' +
                ", brandname='" + brandname + '\'' +
                ", type='" + type + '\'' +
                ", discount='" + discount + '\'' +
                ", exhaust='" + exhaust + '\'' +
                ", position='" + position + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", price=" + price +
                ", gastprice=" + gastprice +
                ", fwlsmc='" + fwlsmc + '\'' +
                ", distance=" + distance +
                '}';
    }
}
