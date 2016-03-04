package scu.mingyuan.com.carmanager.bean;

/**
 * Created by miomin on 16/3/3.
 */
public class Car {

    private String brand; //品牌
    private String car;  //车系
    private String img;  //汽车图片url
    private String price; //价格
    private String displacement; // 排量
    private String oil_consumption; //油耗
    private String speed_changing_box; //变速箱
    private String car_type; //车型
    private String body_structure; // 车身级别(几门几座)

    public Car(String body_structure, String brand, String car,
               String car_type, String displacement, String img,
               String oil_consumption, String price, String speed_changing_box) {
        super();
        this.body_structure = body_structure;
        this.brand = brand;
        this.car = car;
        this.car_type = car_type;
        this.displacement = displacement;
        this.img = img;
        this.oil_consumption = oil_consumption;
        this.price = price;
        this.speed_changing_box = speed_changing_box;
    }

    public String getBody_structure() {
        return body_structure;
    }

    public void setBody_structure(String body_structure) {
        this.body_structure = body_structure;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOil_consumption() {
        return oil_consumption;
    }

    public void setOil_consumption(String oil_consumption) {
        this.oil_consumption = oil_consumption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpeed_changing_box() {
        return speed_changing_box;
    }

    public void setSpeed_changing_box(String speed_changing_box) {
        this.speed_changing_box = speed_changing_box;
    }
}
