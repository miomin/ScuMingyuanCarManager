package scu.mingyuan.com.carmanager.app.cache;


import scu.mingyuan.com.carmanager.app.bean.PetrolStationList;

/**
 * Created by 莫绪旻 on 16/3/18.
 */
public class PetrolStationCache {

    private PetrolStationList petrolStationList = new PetrolStationList();

    private static PetrolStationCache petrolStationCache;

    private PetrolStationCache() {

    }

    // 双重检查锁定优化
    public static PetrolStationCache getInstance() {
        if (petrolStationCache == null) {
            synchronized (PetrolStationCache.class) {
                if (petrolStationCache == null) {
                    petrolStationCache = new PetrolStationCache();
                }
            }
        }
        return petrolStationCache;
    }

    public PetrolStationList getPetrolStationList() {
        return petrolStationList;
    }

    public void setPetrolStationList(PetrolStationList petrolStationList) {
        this.petrolStationList = petrolStationList;
    }
}
