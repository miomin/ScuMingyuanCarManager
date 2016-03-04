package scu.mingyuan.com.carmanager.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.baseactivity.BaseActivity;
import scu.mingyuan.com.carmanager.bean.MyUser;
import scu.mingyuan.com.carmanager.cache.UserCache;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText account_edit;
    private EditText password_edit;

    private LinearLayout account_clear_layout;
    private LinearLayout password_clear_layout;

    private Button login_btn;
    private Button btn_forget_password;

    // 登录dialog
    private ProgressDialog logindialog;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
        initData();
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences("logininfo", MODE_PRIVATE);

        if (sp != null) {
            // 从本地读取上次登录成功时保存的用户登录信息
            String phonenumber = sp.getString("phonenumber", null);
            if (phonenumber != null) {
                account_edit.setText(phonenumber);
            }
        }
    }

    private void initView() {

        account_edit = (EditText) findViewById(R.id.account_edit);
        account_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(account_edit.getText())) {
                    account_clear_layout.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(password_edit.getText())) {
                        login_btn.setEnabled(true);
                    } else {
                        login_btn.setEnabled(false);
                    }
                } else {
                    account_clear_layout.setVisibility(View.INVISIBLE);
                    login_btn.setEnabled(false);
                }
            }
        });
        password_edit = (EditText) findViewById(R.id.password_edit);
        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(password_edit.getText())) {
                    password_edit.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(account_edit.getText())) {
                        login_btn.setEnabled(true);
                    } else {
                        login_btn.setEnabled(false);
                    }
                } else {
                    password_clear_layout.setVisibility(View.INVISIBLE);
                    login_btn.setEnabled(false);
                }
            }
        });

        account_clear_layout = (LinearLayout) findViewById(R.id.clear_account);
        account_clear_layout.setOnClickListener(this);

        password_clear_layout = (LinearLayout) findViewById(R.id.clear_password);
        password_clear_layout.setOnClickListener(this);

        login_btn = (Button) findViewById(R.id.btn_login);
        login_btn.setOnClickListener(this);

        login_btn.setEnabled(false);

        btn_forget_password = (Button) findViewById(R.id.forget_password);
        btn_forget_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_account:
                account_edit.setText("");
                account_clear_layout.setVisibility(View.INVISIBLE);
                break;

            case R.id.clear_password:
                password_edit.setText("");
                password_clear_layout.setVisibility(View.INVISIBLE);
                break;

            case R.id.btn_login:
                login();
                break;

            case R.id.forget_password:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_register) {
            RegisterActivity.startActivity(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void login() {

        logindialog = new ProgressDialog(LoginActivity.this);
        logindialog.setMessage("登录中，请稍后");
        logindialog.show();

        final String username = account_edit.getText().toString();
        String password = password_edit.getText().toString();

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
                query.findObjects(LoginActivity.this, new FindListener<MyUser>() {
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

                        UserCache.getUserCache(LoginActivity.this).setCurrentUser(currentUser);

                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        HomeActivity.startActivity(LoginActivity.this);
                        logindialog.dismiss();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(LoginActivity.this, "登录失败：" + code + "," + msg, Toast.LENGTH_LONG).show();
                        logindialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(LoginActivity.this, "登录失败：" + code + "," + msg, Toast.LENGTH_LONG).show();
                logindialog.dismiss();
            }
        });
    }
}
