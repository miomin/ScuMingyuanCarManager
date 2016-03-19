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
import scu.mingyuan.com.carmanager.util.DensityUtil;

public class PreOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_station_pic;
    private TextView tv_name;
    private TextView tv_score;
    private TextView tv_time;
    private TextView tv_type;
    private EditText edit_amont;
    private TextView tv_price;
    private Button btn_pre_order;

    private PopupWindow popup_selecter;

    private String[] strs_type = new String[]{"90号汽油", "93号汽油", "97号汽油", "30#号柴油", "20#号柴油", "10#号柴油",
            "5#号柴油", "0#号柴油", "-5#号柴油", "-10#号柴油", "-21#号柴油", "-35#号柴油"};

    private static final String TAG = "YJK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);

        initView();
    }

    private void initView() {
        iv_station_pic = (ImageView) findViewById(R.id.iv_station_pic);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_type = (TextView) findViewById(R.id.tv_type);
        edit_amont = (EditText) findViewById(R.id.edit_amont);
        tv_price = (TextView) findViewById(R.id.tv_price);
        btn_pre_order = (Button) findViewById(R.id.btn_preorder);

        tv_time.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        btn_pre_order.setOnClickListener(this);


    }

    public static void startAction(Context context) {
        Intent starter = new Intent(context, PreOrderActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:

                long time = System.currentTimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String times[] = format.format(new Date(time)).split("-");
                int year = Integer.parseInt(times[0]);
                final int month = Integer.parseInt(times[1]);
                int day = Integer.parseInt(times[2]);

                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        tv_time.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");

                    }
                }, year, month, day).show();
                break;
            case R.id.tv_type:
                if (popup_selecter == null) {
                    ListView list_type = new ListView(this);
                    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strs_type);

                    list_type.setAdapter(adapter);

                    list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            tv_type.setText(strs_type[position]);
                            popup_selecter.dismiss();
                        }
                    });
                    list_type.setBackgroundResource(R.color.whiteDivideLine);

                    Log.i(TAG, "onClick:" + DensityUtil.dip2px(this, 200));
                    popup_selecter = new PopupWindow(list_type, tv_type.getWidth(), DensityUtil.dip2px(this, 200));
                    popup_selecter.setElevation(DensityUtil.dip2px(this,3 ));
                }
                popup_selecter.showAsDropDown(tv_type, 0, 16);
                break;
            case R.id.btn_preorder:
                break;
        }
    }
}
