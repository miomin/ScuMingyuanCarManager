package scu.mingyuan.com.carmanager.app.bean;

/**
 * Created by miomin on 16/3/20.
 */
public class Des {

    private String time;
    private String place;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Des{" +
                "info='" + info + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
