package scu.mingyuan.com.carmanager.cache;

import android.content.Context;

import scu.mingyuan.com.carmanager.bean.MyUser;

/**
 * Created by miomin on 16/3/2.
 */
public class UserCache {

    private MyUser currentUser;

    private static UserCache mUserCache;
    private Context mAppContext;

    private UserCache(Context appContext) {
        this.mAppContext = appContext;
    }

    public static UserCache getUserCache(Context context) {
        if (mUserCache == null) {
            mUserCache = new UserCache(context.getApplicationContext());
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
