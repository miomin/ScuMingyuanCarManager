package scu.mingyuan.com.carmanager.cache;

import java.util.ArrayList;

import scu.mingyuan.com.carmanager.bean.MyCar;

/**
 * Created by 莫绪旻 on 16/3/2.
 */
public class MyCarCache {

    private ArrayList<MyCar> myCars;

    private static MyCarCache myCarCache;

    private MyCarCache() {
        myCars = new ArrayList<>();
    }

    synchronized public static MyCarCache getMyCarCache() {
        if (myCarCache == null) {
            myCarCache = new MyCarCache();
        }
        return myCarCache;
    }

    public ArrayList<MyCar> getMyCars() {
        return myCars;
    }

    public void add(MyCar myCar) {
        myCars.add(myCar);
    }

    public void addAll(ArrayList<MyCar> myCars) {
        this.myCars.addAll(myCars);
    }

    public void clear() {
        myCars.clear();
    }

    public MyCar getItemById(int id) {
        MyCar myCar = null;
        for (MyCar item : myCars) {
            if (item.getId() == id)
                myCar = item;
        }
        return myCar;
    }
}
