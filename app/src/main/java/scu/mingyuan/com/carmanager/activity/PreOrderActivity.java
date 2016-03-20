package scu.mingyuan.com.carmanager.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.baseactivity.BaseActivity;
import scu.mingyuan.com.carmanager.bean.PetrolStation;
import scu.mingyuan.com.carmanager.util.DensityUtil;

/**
 * Created by 应俊康 on 16/3/19.
 */
public class PreOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_station_pic;
    private TextView tv_name;
    private TextView tv_distance;
    private TextView tv_time;
    private TextView tv_type;
    private EditText edit_price;
    private TextView tv_amont;
    private Button btn_pre_order;

    private PopupWindow popup_selecter;

    private String[] gas_type;

    private PetrolStation petrolStation;

    private int year;
    private int month;
    private int day;
    private String type;
    private int price;

    private static final String TAG = "YJK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);

        gas_type = getResources().getStringArray(R.array.gas_type);

        Intent intent = getIntent();
        if (intent != null) {

            petrolStation = (PetrolStation) intent.getSerializableExtra("petrolStation");
            Log.i(TAG, "onMarkerClick " + petrolStation.toString());

        }
        initView();
    }

    private void initView() {
        iv_station_pic = (ImageView) findViewById(R.id.iv_station_pic);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_type = (TextView) findViewById(R.id.tv_type);
        edit_price = (EditText) findViewById(R.id.edit_price);
        tv_amont = (TextView) findViewById(R.id.tv_amont);
        btn_pre_order = (Button) findViewById(R.id.btn_preorder);

        tv_time.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        btn_pre_order.setOnClickListener(this);

        tv_name.setText(petrolStation.getName());
        tv_distance.setText(petrolStation.getDistance() + "千米");

        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String times[] = format.format(new Date(time)).split("-");
        year = Integer.parseInt(times[0]);
        month = Integer.parseInt(times[1]);
        day = Integer.parseInt(times[2]);

        tv_time.setText(year + "年" + month + "月" + day + "日");

    }

    public static void startAction(Context context, PetrolStation petrolStation) {
        Intent starter = new Intent(context, PreOrderActivity.class);
        starter.putExtra("petrolStation", petrolStation);
        context.startActivity(starter);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:


                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        year = year;
                        month = monthOfYear;
                        day = dayOfMonth;

                        tv_time.setText(year + getResources().getString(R.string.year)
                                + monthOfYear + getResources().getString(R.string.month)
                                + dayOfMonth + getResources().getString(R.string.day));

                    }
                }, year, month, day).show();
                break;

            case R.id.tv_type:
                if (popup_selecter == null) {
                    ListView list_type = new ListView(this);
                    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gas_type);

                    list_type.setAdapter(adapter);

                    list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            tv_type.setText(gas_type[position]);
                            popup_selecter.dismiss();
                        }
                    });
                    list_type.setBackgroundResource(R.color.whiteDivideLine);

                    Log.i(TAG, "onClick:" + DensityUtil.dip2px(this, 200));
                    popup_selecter = new PopupWindow(list_type, tv_type.getWidth(), DensityUtil.dip2px(this, 200));
                    popup_selecter.setElevation(DensityUtil.dip2px(this, 3));
                }
                popup_selecter.showAsDropDown(tv_type, 0, 16);
                break;
            case R.id.btn_preorder:

                String preOrder = "time:" + tv_time.getText().toString() + "type:" + tv_type.getText().toString()
                        + "price:" + edit_price.getText().toString() + "amont:" + tv_amont.getText().toString();

                break;
        }
    }
}
