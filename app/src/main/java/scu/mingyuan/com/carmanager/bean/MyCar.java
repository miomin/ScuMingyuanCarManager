package scu.mingyuan.com.carmanager.bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by 莫绪旻 on 16/3/3.
 */
public class MyCar extends BmobObject {

    private Integer id;
    private String brand; //品牌
    private String car; //车系
    private String model; //具体型号
    private String license_plate_number; // 车牌号
    private String engine_number; // 发动机号
    private Float mileage; // 里程数
    private Float remaining_oil; //剩余油量%
    private Boolean engine_statu; //发动机好坏
    private Boolean speed_changing_box_statu; //变速箱好坏
    private Boolean antomative_lighting_statu; //车灯好坏
    private String car_location; //车辆所在地
    private BmobDate registration_date; //上牌日期
    private MyUser owner;//汽车拥有者
    private String img;  //汽车图片url
    private String price; //价格
    private String displacement; // 排量
    private String oil_consumption; //油耗
    private String speed_changing_box; //变速箱
    private String car_type; //车型
    private String body_structure; // 车身级别(几门几座)
    private ArrayList<Float> oil_day;// 每日油耗
    private ArrayList<Float> mileage_day; // 每日里程

    public Boolean getAntomative_lighting_statu() {
        return antomative_lighting_statu;
    }

    public void setAntomative_lighting_statu(Boolean antomative_lighting_statu) {
        this.antomative_lighting_statu = antomative_lighting_statu;
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

    public String getCar_location() {
        return car_location;
    }

    public void setCar_location(String car_location) {
        this.car_location = car_location;
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

    public String getEngine_number() {
        return engine_number;
    }

    public void setEngine_number(String engine_number) {
        this.engine_number = engine_number;
    }

    public Boolean getEngine_statu() {
        return engine_statu;
    }

    public void setEngine_statu(Boolean engine_statu) {
        this.engine_statu = engine_statu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }

    public Float getMileage() {
        return mileage;
    }

    public void setMileage(Float mileage) {
        this.mileage = mileage;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOil_consumption() {
        return oil_consumption;
    }

    public void setOil_consumption(String oil_consumption) {
        this.oil_consumption = oil_consumption;
    }

    public MyUser getOwner() {
        return owner;
    }

    public void setOwner(MyUser owner) {
        this.owner = owner;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public BmobDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(BmobDate registration_date) {
        this.registration_date = registration_date;
    }

    public Float getRemaining_oil() {
        return remaining_oil;
    }

    public void setRemaining_oil(Float remaining_oil) {
        this.remaining_oil = remaining_oil;
    }

    public String getSpeed_changing_box() {
        return speed_changing_box;
    }

    public void setSpeed_changing_box(String speed_changing_box) {
        this.speed_changing_box = speed_changing_box;
    }

    public Boolean getSpeed_changing_box_statu() {
        return speed_changing_box_statu;
    }

    public void setSpeed_changing_box_statu(Boolean speed_changing_box_statu) {
        this.speed_changing_box_statu = speed_changing_box_statu;
    }

    public ArrayList<Float> getMileage_day() {
        return mileage_day;
    }

    public void setMileage_day(ArrayList<Float> mileage_day) {
        this.mileage_day = mileage_day;
    }

    public ArrayList<Float> getOil_day() {
        return oil_day;
    }

    public void setOil_day(ArrayList<Float> oil_day) {
        this.oil_day = oil_day;
    }
}
