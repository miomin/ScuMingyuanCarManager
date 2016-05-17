package scu.mingyuan.com.carmanager.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.app.activity.LoginActivity;
import scu.mingyuan.com.carmanager.base.toolbar.NoneToolbarActivity;
import scu.mingyuan.com.carmanager.constants.APPStatu;
import scu.mingyuan.com.carmanager.core.AppStatusTracker;


/**
 * Created by Miomin and Stay on 2/2/16.
 */
public class WelcomeActivity2 extends NoneToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 将APP状态置为已正常启动
        AppStatusTracker.getInstance(getApplication()).setAppStatus(APPStatu.STATUS_ONLINE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(WelcomeActivity2.this, LoginActivity.class));
            finish();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }
}
