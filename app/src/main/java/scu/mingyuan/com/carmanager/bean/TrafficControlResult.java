package scu.mingyuan.com.carmanager.bean;

import java.util.ArrayList;

/**
 * Created by miomin on 16/3/20.
 */
public class TrafficControlResult {

    private String date;
    private String week;
    private String city;
    private String cityname;
    private ArrayList<Des> des;
    private String fine;
    private String remarks;
    private String isxianxing;
    private int[] xxweihao;
    private String holiday;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Des> getDes() {
        return des;
    }

    public void setDes(ArrayList<Des> des) {
        this.des = des;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getIsxianxing() {
        return isxianxing;
    }

    public void setIsxianxing(String isxianxing) {
        this.isxianxing = isxianxing;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int[] getXxweihao() {
        return xxweihao;
    }

    public void setXxweihao(int[] xxweihao) {
        this.xxweihao = xxweihao;
    }

    @Override
    public String toString() {
        return "TrafficControlResult{" +
                "city='" + city + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", cityname='" + cityname + '\'' +
                ", des=" + des +
                ", fine='" + fine + '\'' +
                ", remarks='" + remarks + '\'' +
                ", isxianxing='" + isxianxing + '\'' +
                ", xxweihao='" + xxweihao + '\'' +
                ", holiday='" + holiday + '\'' +
                '}';
    }
}
