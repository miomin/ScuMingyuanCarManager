package scu.mingyuan.com.carmanager.app.application;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class ActivityCollector {

    // 用来管理所有的Activity
    public static List<Activity> activities = new ArrayList<Activity>();

    // 添加一个Activity
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    // 释放一个Activity
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    // 退出程序，释放所有的Activity
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
