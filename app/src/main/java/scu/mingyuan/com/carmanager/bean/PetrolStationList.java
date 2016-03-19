package scu.mingyuan.com.carmanager.bean;

import java.util.ArrayList;

/**
 * Created by 莫绪旻 on 16/3/18.
 */
public class PetrolStationList {

    private ArrayList<PetrolStation> data;

    public PetrolStationList() {
        data = new ArrayList<>();
    }

    public ArrayList<PetrolStation> getData() {
        return data;
    }

    public void setData(ArrayList<PetrolStation> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PetrolStationList{" +
                "data=" + data +
                '}';
    }
}
