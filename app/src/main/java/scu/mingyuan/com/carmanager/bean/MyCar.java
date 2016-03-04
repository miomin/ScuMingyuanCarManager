package scu.mingyuan.com.carmanager.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by 莫绪旻 on 16/3/3.
 */
public class MyCar extends BmobObject {

    private Integer id;
    private String brand; //品牌
    private String car; //车系
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

    public MyCar(Boolean antomative_lighting_statu, String brand, String car,
                 String car_location, String engine_number, Boolean engine_statu,
                 Integer id, String license_plate_number, Float mileage, MyUser owner,
                 BmobDate registration_date, Float remaining_oil, Boolean speed_changing_box_statu) {
        super();
        this.antomative_lighting_statu = antomative_lighting_statu;
        this.brand = brand;
        this.car = car;
        this.car_location = car_location;
        this.engine_number = engine_number;
        this.engine_statu = engine_statu;
        this.id = id;
        this.license_plate_number = license_plate_number;
        this.mileage = mileage;
        this.owner = owner;
        this.registration_date = registration_date;
        this.remaining_oil = remaining_oil;
        this.speed_changing_box_statu = speed_changing_box_statu;
    }

    public Boolean getAntomative_lighting_statu() {
        return antomative_lighting_statu;
    }

    public void setAntomative_lighting_statu(Boolean antomative_lighting_statu) {
        this.antomative_lighting_statu = antomative_lighting_statu;
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

    public MyUser getOwner() {
        return owner;
    }

    public void setOwner(MyUser owner) {
        this.owner = owner;
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

    public Boolean getSpeed_changing_box_statu() {
        return speed_changing_box_statu;
    }

    public void setSpeed_changing_box_statu(Boolean speed_changing_box_statu) {
        this.speed_changing_box_statu = speed_changing_box_statu;
    }
}
