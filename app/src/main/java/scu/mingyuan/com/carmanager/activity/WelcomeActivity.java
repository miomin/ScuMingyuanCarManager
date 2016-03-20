package scu.mingyuan.com.carmanager.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.baseactivity.BaseActivity;
import scu.mingyuan.com.carmanager.bean.MyUser;
import scu.mingyuan.com.carmanager.cache.UserCache;
import scu.mingyuan.com.carmanager.util.MyImageLoader;

/**
 * 描述:欢迎页 创建日期:2016/3/5
 *
 * @author 莫绪旻
 */
public class WelcomeActivity extends BaseActivity {

    public static Activity instance;
    private static final int TIME = 1000;
    private static final int GO_LOGIN = 1000;
    private static final int GO_HOME = 1001;

    private ImageView bg,logo;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        instance = this;
        bg = (ImageView) findViewById(R.id.bg);
        logo = (ImageView) findViewById(R.id.logo);

        MyImageLoader.getInstance().dispalyFromAssets("full_bg.png",bg);
        MyImageLoader.getInstance().dispalyFromAssets("logo_text.png",logo);
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
        currentUser.login(this, new SaveListener() {
            @Override
            public void onSuccess() {

                // 获取登录成功的用户信息
                BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                //查询username对应的数据
                query.addWhereEqualTo("username", username);
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(1);
                //执行查询方法
                query.findObjects(WelcomeActivity.this, new FindListener<MyUser>() {
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
                        Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.login_failed) + "：" + code + "," + msg, Toast.LENGTH_LONG).show();
                        mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(WelcomeActivity.this, getResources().getString(R.string.login_failed) + "：" + code + "," + msg, Toast.LENGTH_LONG).show();
                mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
            }
        });
    }
}
