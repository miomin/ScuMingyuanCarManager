package scu.mingyuan.com.carmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.baseactivity.BaseActivity;

/**
 * 描述:欢迎页 创建日期:2015/7/23
 *
 * @author 莫绪旻
 */
public class WelcomeActivity extends BaseActivity {

    private int welcome;
    public static Activity instance;
    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        instance = this;
    }

    private void init() {
//        SharedPreferences perPreferences = getSharedPreferences(MyString.WELCOME,
//                MODE_PRIVATE);
//        welcome = perPreferences.getInt(MyString.WELCOME, 0);
//        if (welcome == 0) {
//            Editor editor = perPreferences.edit();
//            editor.clear();
//            editor.putInt(MyString.WELCOME, 1);
//            editor.commit();
        mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
//        } else {
        mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
//        }
    }

    private void goHome() {
        LoginActivity.startActivity(this);
        overridePendingTransition(R.anim.push_right_in, R.anim.hold_long);
        finish();
    }

    private void goGuide() {
        LoginActivity.startActivity(this);
        overridePendingTransition(R.anim.push_right_in, R.anim.hold_long);
        finish();
    }
}
