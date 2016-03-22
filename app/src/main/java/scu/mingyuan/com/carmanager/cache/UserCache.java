package scu.mingyuan.com.carmanager.cache;

import scu.mingyuan.com.carmanager.bean.MyUser;

/**
 * Created by 莫绪旻 on 16/3/2.
 */
public class UserCache {

    private MyUser currentUser;

    private static UserCache mUserCache;

    private UserCache() {

    }

    // 双重检查锁定优化
    synchronized public static UserCache getUserCache() {
        if (mUserCache == null) {
            synchronized (UserCache.class) {
                if (mUserCache == null) {
                    mUserCache = new UserCache();
                }
            }
        }
        return mUserCache;
    }

    public MyUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(MyUser currentUser) {
        this.currentUser = currentUser;
    }
}
