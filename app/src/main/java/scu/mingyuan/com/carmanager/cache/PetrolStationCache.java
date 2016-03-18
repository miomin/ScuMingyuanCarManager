package scu.mingyuan.com.carmanager.cache;

import scu.mingyuan.com.carmanager.bean.PetrolStationList;

/**
 * Created by miomin on 16/3/18.
 */
public class PetrolStationCache {

    private PetrolStationList petrolStationList = new PetrolStationList();

    private static PetrolStationCache petrolStationCache;

    private PetrolStationCache() {

    }

    synchronized public static PetrolStationCache getInstance() {
        if (petrolStationCache == null) {
            petrolStationCache = new PetrolStationCache();
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
