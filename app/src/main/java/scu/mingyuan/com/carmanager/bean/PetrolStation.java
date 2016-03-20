package scu.mingyuan.com.carmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 莫绪旻 on 16/3/18.
 */
public class PetrolStation implements Serializable{

    private String id;
    private String name; //中油燕宾北邮加油站‎（办卡优惠）
    private String area; //chongwen
    private String areaname; //北京市 崇文区
    private String address; //北京市崇文区天坛路12号，与东市场东街路交叉西南角（天坛北门往西一公里路南）。
    private String brandname; //中石油
    private String type; //加盟店
    private String discount; //打折加油站
    private String exhaust; //京Ⅴ
    private String position;//116.401654,39.886973
    private double lon; //116.40804671453
    private double lat; //39.893324983272
    private List<Price> price; // [{"type": "E90","price": "7.31"}
    private List<Gastprice> gastprice; // {"name": "92#","price": "6.77"},{"name": "95#","price": "7.36"}
    private String fwlsmc;//银联卡,信用卡支付
    private int distance;//2564

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
