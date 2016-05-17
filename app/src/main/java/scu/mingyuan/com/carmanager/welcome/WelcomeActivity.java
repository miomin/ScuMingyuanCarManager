package scu.mingyuan.com.carmanager.welcome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.app.activity.HomeActivity;
import scu.mingyuan.com.carmanager.app.activity.LoginActivity;
import scu.mingyuan.com.carmanager.app.application.MyApplication;
import scu.mingyuan.com.carmanager.app.baseactivity.BaseActivity;
import scu.mingyuan.com.carmanager.app.bean.MyUser;
import scu.mingyuan.com.carmanager.app.cache.UserCache;
import scu.mingyuan.com.carmanager.utils.MyImageLoader;

/**
 * 描述:欢迎页 创建日期:2016/3/5
 *
 * @author 莫绪旻
 */
public class WelcomeActivity extends BaseActivity {

    private static final int TIME = 1000;
    private static final int GO_LOGIN = 1000;
    private static final int GO_HOME = 1001;

    private ImageView bg, logo;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_LOGIN:
                    goLogin();
                    break;
                case GO_HOME:
                    goHome();
                    break;
            }
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        bg = (ImageView) findViewById(R.id.bg);
        logo = (ImageView) findViewById(R.id.logo);

        MyImageLoader.getInstance().dispalyFromAssets("full_bg.png", bg);
        MyImageLoader.getInstance().dispalyFromAssets("logo_text.png", logo);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        SharedPreferences sp = getSharedPreferences(LoginActivity.LOGININFO, MODE_PRIVATE);
        if (sp != null) {
            // 从本地读取上次登录成功时保存的用户登录信息
            String username = sp.getString(LoginActivity.ACCOUNT, null);
            String password = sp.getString(LoginActivity.PASSWORD, null);
            if (username != null && password != null) {
                login(username, password);
            } else {
                mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
            }
        } else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
        }
    }

    private void goLogin() {
        LoginActivity.startActivity(this);
//        overridePendingTransition(R.anim.push_right_in, R.anim.hold_long);
        finish();
    }

    private void goHome() {
        HomeActivity.startActivity(this);
//        overridePendingTransition(R.anim.push_right_in, R.anim.hold_long);
        finish();
    }

    private void login(final String username, final String password) {

        final MyUser currentUser = new MyUser(username);
        currentUser.setUsername(username);
        currentUser.setPassword(password);
        currentUser.login(MyApplication.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {

                // 获取登录成功的用户信息
                BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                //查询username对应的数据
                query.addWhereEqualTo("username", username);
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(1);
                //执行查询方法
                query.findObjects(MyApplication.getContext(), new FindListener<MyUser>() {
                    @Override
                    public void onSuccess(List<MyUser> object) {
                        currentUser.setEmail(object.get(0).getEmail());
                        currentUser.setAge(object.get(0).getAge());
                        currentUser.setNick(object.get(0).getNick());
                        currentUser.setSex(object.get(0).isMale());
                        currentUser.setEmailVerified(object.get(0).getEmailVerified());
                        currentUser.setMobilePhoneNumber(object.get(0).getMobilePhoneNumber());
                        currentUser.setMobilePhoneNumberVerified(object.get(0).getMobilePhoneNumberVerified());
                        currentUser.setObjectId(object.get(0).getObjectId());

                        UserCache.getUserCache().setCurrentUser(currentUser);
                        mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(MyApplication.getContext(), getResources().getString(R.string.login_failed) + "：" + code + "," + msg, Toast.LENGTH_LONG).show();
                        mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(MyApplication.getContext(), getResources().getString(R.string.login_failed) + "：" + code + "," + msg, Toast.LENGTH_LONG).show();
                mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
            }
        });
    }

}
