package scu.mingyuan.com.carmanager.app.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.listener.UpdateListener;
import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.app.bean.MyCar;
import scu.mingyuan.com.carmanager.app.cache.MyCarCache;
import scu.mingyuan.com.carmanager.app.util.MyImageLoader;
import scu.mingyuan.com.carmanager.zxing.activity.CaptureActivity;

/**
 * Created by 莫绪旻 on 16/3/18.
 */
public class MyCarDetailActivity extends AppCompatActivity {

    private static int id = -1;
    private static MyCar myCar = null;

    private ImageView ivCar;
    private TextView tvModel;
    private TextView tvPrice;
    private TextView tv_registration_date;
    private CheckBox cb_engine, cb_speed_change_box, cb_antomative_lighting_statu;
    private TextView tv_engine, tv_speed_change_box, tv_antomative_lighting_statu;
    private TextView tv_mileage;
    private TextView tv_license_plate_number;
    private TextView tv_car_location;
    private TextView tv_registration_date_sub;
    private TextView tv_remaining_oil;

    // 加载中dialog
    private ProgressDialog loadingdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_mycar_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(MyCarDetailActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });

        id = getIntent().getIntExtra("id", -1);
        if (id < 0)
            return;
        myCar = MyCarCache.getMyCarCache().getItemById(id);

        initView();
        initData();
    }

    private void initView() {
        ivCar = (ImageView) findViewById(R.id.ivCar);
        tvModel = (TextView) findViewById(R.id.tvModel);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tv_registration_date = (TextView) findViewById(R.id.tv_registration_date);
        cb_engine = (CheckBox) findViewById(R.id.cb_engine);
        cb_speed_change_box = (CheckBox) findViewById(R.id.cb_speed_change_box);
        cb_antomative_lighting_statu = (CheckBox) findViewById(R.id.cb_antomative_lighting_statu);
        tv_engine = (TextView) findViewById(R.id.tv_engine);
        tv_speed_change_box = (TextView) findViewById(R.id.tv_speed_change_box);
        tv_antomative_lighting_statu = (TextView) findViewById(R.id.tv_antomative_lighting_statu);
        tv_mileage = (TextView) findViewById(R.id.tv_mileage);
        tv_license_plate_number = (TextView) findViewById(R.id.tv_license_plate_number);
        tv_car_location = (TextView) findViewById(R.id.tv_car_location);
        tv_registration_date_sub = (TextView) findViewById(R.id.tv_registration_date_sub);
        tv_remaining_oil = (TextView) findViewById(R.id.tv_remaining_oil);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initData() {
        setTitle(myCar.getBrand() + " " + myCar.getCar());
        MyImageLoader.getInstance().dispalyFromAssets(id + ".JPG", ivCar);
        tvModel.setText(myCar.getModel());
        tvPrice.setText(myCar.getPrice());
        tv_registration_date.setText(myCar.getRegistration_date().getDate().split(" ")[0]);
        if (myCar.getEngine_statu()) {
            cb_engine.setChecked(true);
            tv_engine.setTextColor(getColor(R.color.colorPrimary));
        } else {
            cb_engine.setChecked(false);
            tv_engine.setTextColor(getColor(R.color.grayLight));
        }
        if (myCar.getSpeed_changing_box_statu()) {
            cb_speed_change_box.setChecked(true);
            tv_speed_change_box.setTextColor(getColor(R.color.colorPrimary));
        } else {
            cb_speed_change_box.setChecked(false);
            tv_speed_change_box.setTextColor(getColor(R.color.grayLight));
        }
        if (myCar.getAntomative_lighting_statu()) {
            cb_antomative_lighting_statu.setChecked(true);
            tv_antomative_lighting_statu.setTextColor(getColor(R.color.colorPrimary));
        } else {
            cb_antomative_lighting_statu.setChecked(false);
            tv_antomative_lighting_statu.setTextColor(getColor(R.color.grayLight));
        }
        tv_mileage.setText(myCar.getMileage() + "万km");
        tv_license_plate_number.setText(myCar.getLicense_plate_number());
        tv_car_location.setText(myCar.getCar_location());
        tv_registration_date_sub.setText(myCar.getRegistration_date().getDate().split(" ")[0]);
        tv_remaining_oil.setText(myCar.getRemaining_oil() + "%");
    }

    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, MyCarDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mycar_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startMillActivity(View view) {
        TrendLineChartActivity.actionStart(this, "每日里程", myCar.getMileage_day());
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {

            loadingdialog = new ProgressDialog(MyCarDetailActivity.this);
            loadingdialog.setMessage(getResources().getString(R.string.data_loading));
            loadingdialog.show();

            Bundle bundle = data.getExtras();
            String scanResult[] = bundle.getString("result").split(";");

            // 1;0;1;0.4;32.5;12,23,43,11
            // 更新界面

            if (scanResult[0].equals("0")) {
                myCar.setEngine_statu(true);
                cb_engine.setChecked(true);
                tv_engine.setTextColor(getColor(R.color.colorPrimary));
            } else {
                myCar.setEngine_statu(false);
                cb_engine.setChecked(false);
                tv_engine.setTextColor(getColor(R.color.grayLight));
            }
            if (scanResult[1].equals("0")) {
                myCar.setSpeed_changing_box_statu(true);
                cb_speed_change_box.setChecked(true);
                tv_speed_change_box.setTextColor(getColor(R.color.colorPrimary));
            } else {
                myCar.setSpeed_changing_box_statu(false);
                cb_speed_change_box.setChecked(false);
                tv_speed_change_box.setTextColor(getColor(R.color.grayLight));
            }
            if (scanResult[2].equals("0")) {
                myCar.setAntomative_lighting_statu(true);
                cb_antomative_lighting_statu.setChecked(true);
                tv_antomative_lighting_statu.setTextColor(getColor(R.color.colorPrimary));
            } else {
                myCar.setAntomative_lighting_statu(false);
                cb_antomative_lighting_statu.setChecked(false);
                tv_antomative_lighting_statu.setTextColor(getColor(R.color.grayLight));
            }

            myCar.setMileage(myCar.getMileage() + Float.parseFloat(scanResult[3]));
            tv_mileage.setText(myCar.getMileage() + "");

            myCar.setRemaining_oil(Float.parseFloat(scanResult[4]));
            tv_remaining_oil.setText(myCar.getRemaining_oil() + "%");

            String[] mil = scanResult[5].split(",");
            for (int i = 0; i < mil.length; i++) {
                myCar.getMileage_day().add(Float.parseFloat(mil[i]));
            }

            myCar.update(this, myCar.getObjectId(), new UpdateListener() {

                @Override
                public void onSuccess() {
                    Toast.makeText(MyCarDetailActivity.this, getResources().getString(R.string.data_add_succeed), Toast.LENGTH_LONG).show();
                    loadingdialog.dismiss();
                }

                @Override
                public void onFailure(int code, String msg) {
                    Toast.makeText(MyCarDetailActivity.this, getResources().getString(R.string.data_load_failed), Toast.LENGTH_LONG).show();
                    loadingdialog.dismiss();
                }
            });
        }
    }
}
