package scu.mingyuan.com.carmanager.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;
import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.app.baseactivity.BaseActivity;
import scu.mingyuan.com.carmanager.app.bean.MyUser;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText account_edit;
    private EditText password_edit;

    private LinearLayout account_clear_layout;
    private LinearLayout password_clear_layout;

    private Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getResources().getString(R.string.register));

        // 显示回退键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(this) != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
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
                        register_btn.setEnabled(true);
                    } else {
                        register_btn.setEnabled(false);
                    }
                } else {
                    account_clear_layout.setVisibility(View.INVISIBLE);
                    register_btn.setEnabled(false);
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
                        register_btn.setEnabled(true);
                    } else {
                        register_btn.setEnabled(false);
                    }
                } else {
                    password_clear_layout.setVisibility(View.INVISIBLE);
                    register_btn.setEnabled(false);
                }
            }
        });

        account_clear_layout = (LinearLayout) findViewById(R.id.clear_account);
        account_clear_layout.setOnClickListener(this);

        password_clear_layout = (LinearLayout) findViewById(R.id.clear_password);
        password_clear_layout.setOnClickListener(this);

        register_btn = (Button) findViewById(R.id.btn_register);
        register_btn.setOnClickListener(this);

        register_btn.setEnabled(false);
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

            case R.id.btn_register:
                register();
                break;
        }
    }

    // 注册新用户
    private void register() {
        String username = account_edit.getText().toString();
        String password = password_edit.getText().toString();
        MyUser user = new MyUser(username);
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_succeed), Toast.LENGTH_LONG).show();
                onBackPressed();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_failed) + ":" + code + "," + msg, Toast.LENGTH_LONG).show();
            }
        });
    }

}
