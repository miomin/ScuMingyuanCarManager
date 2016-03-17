package scu.mingyuan.com.carmanager.cache;

import java.util.ArrayList;

import scu.mingyuan.com.carmanager.bean.MyCar;

/**
 * Created by 莫绪旻 on 16/3/2.
 */
public class MyCarCache {

    private ArrayList<MyCar> myCars = new ArrayList<>();

    private static MyCarCache mMyCarCache;

    private MyCarCache() {

    }

    synchronized public static MyCarCache getMyCarCache() {
        if (mMyCarCache == null) {
            mMyCarCache = new MyCarCache();
        }
        return mMyCarCache;
    }

    public void add(MyCar myCar) {
        myCars.add(myCar);
    }

    public void addList(ArrayList<MyCar> myCars) {
        this.myCars.addAll(myCars);
    }

    public ArrayList<MyCar> getMyCars() {
        return myCars;
    }
}
