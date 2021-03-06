package scu.mingyuan.com.carmanager.app.cache;

import java.util.ArrayList;

import scu.mingyuan.com.carmanager.app.bean.MyCar;


/**
 * Created by 莫绪旻 on 16/3/2.
 */
public class MyCarCache {

    private ArrayList<MyCar> myCars = new ArrayList<>();

    private static MyCarCache mMyCarCache;

    private MyCarCache() {

    }

    // 双重检查锁定优化
    public static MyCarCache getMyCarCache() {
        if (mMyCarCache == null) {
            synchronized (MyCarCache.class) {
                if (mMyCarCache == null) {
                    mMyCarCache = new MyCarCache();
                }
            }
        }
        return mMyCarCache;
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

    public ArrayList<MyCar> getMyCars() {
        return myCars;
    }
}
