
package scu.mingyuan.com.carmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.baseactivity.BaseActivity;


public class TrendLineChartActivity extends BaseActivity implements OnChartValueSelectedListener {

    private LineChart trendLineChart;

    private String titleStr;

    private TextView tvTitleSub;
    private TextView tvSubTitle;
    private TextView tvUnit, tvMax, tvMin, tvToday, tvArg, tvUnitTitle;

    private ArrayList<Float> datas;

    public static void actionStart(Context context, String titleStr, ArrayList<Float> datas) {
        Intent intent = new Intent(context, TrendLineChartActivity.class);
        intent.putExtra("titleStr", titleStr);
        intent.putExtra("datas", datas);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trendlinechart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 显示回退键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(this) != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        initView();
        initData();

        trendLineChart = (LineChart) findViewById(R.id.trendLineChart);
        trendLineChart.setOnChartValueSelectedListener(this);

        // no description text
        trendLineChart.setDescription("");
        trendLineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        trendLineChart.setTouchEnabled(true);

        trendLineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        trendLineChart.setDragEnabled(true);
        trendLineChart.setScaleEnabled(true);
        trendLineChart.setDrawGridBackground(false);
        trendLineChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        trendLineChart.setPinchZoom(true);

        // set an alternative background color
        trendLineChart.setBackgroundResource(R.color.whiteLight);

        // add data
        setData();

        trendLineChart.animateY(3000);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = trendLineChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(R.color.blackLight);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = trendLineChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaxValue(160);
        leftAxis.setDrawGridLines(true);
        leftAxis.setStartAtZero(true);

        YAxis rightAxis = trendLineChart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setAxisMaxValue(160);
        rightAxis.setDrawGridLines(false);
        leftAxis.setStartAtZero(true);
    }

    private void initData() {
        titleStr = getIntent().getStringExtra("titleStr");
        datas = (ArrayList<Float>) getIntent().getSerializableExtra("datas");

        if (titleStr == null || datas == null) {
            Toast.makeText(this, getResources().getString(R.string.data_load_failed), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        tvTitleSub.setText(titleStr);
        tvSubTitle.setText(titleStr);
        setTitle(titleStr);

        tvUnit.setText("km/天");
        tvMax.setText("" + ArrayListMax(datas) + "km/天");
        tvMin.setText("" + ArrayListMin(datas) + "km/天");
        tvToday.setText("" + datas.get(datas.size() - 1) + "km/天");
        tvArg.setText("" + ArrayListAru(datas));
        tvUnitTitle.setText("km/天");
    }

    private void initView() {
        tvTitleSub = (TextView) findViewById(R.id.tvTitleSub);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
        tvUnit = (TextView) findViewById(R.id.tvUnit);
        tvMax = (TextView) findViewById(R.id.tvMax);
        tvMin = (TextView) findViewById(R.id.tvMin);
        tvToday = (TextView) findViewById(R.id.tvToday);
        tvArg = (TextView) findViewById(R.id.tvArg);
        tvUnitTitle = (TextView) findViewById(R.id.tvUnitTitle);
    }


    private void setData() {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < datas.size(); i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < datas.size(); i++) {
            yVals2.add(new Entry(datas.get(i), i));
        }

        // create a dataset and give it a type
        LineDataSet set2 = new LineDataSet(yVals2, titleStr);
        set2.setAxisDependency(AxisDependency.RIGHT);
        set2.setColor(getResources().getColor(R.color.colorPrimary));
        set2.setCircleColor(getResources().getColor(R.color.colorPrimary));
        set2.setLineWidth(2f);
        set2.setCircleSize(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(getResources().getColor(R.color.colorPrimary));
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setDrawFilled(true);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set2);

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(R.color.blackLight);
        data.setValueTextSize(9f);

        // set data
        trendLineChart.setData(data);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    public void back(View view) {
        super.onBackPressed();
    }

    //获取ArrayList中的最大值
    private float ArrayListMax(ArrayList<Float> sampleList) {
        try {
            float maxDevation = 0.0f;
            int totalCount = sampleList.size();
            if (totalCount >= 1) {
                float max = Float.parseFloat(sampleList.get(0).toString());
                for (int i = 0; i < totalCount; i++) {
                    float temp = Float.parseFloat(sampleList.get(i).toString());
                    if (temp > max) {
                        max = temp;
                    }
                }
                maxDevation = max;
            }
            return maxDevation;
        } catch (Exception ex) {
            throw ex;
        }
    }

    //获取ArrayList中的最小值
    private float ArrayListMin(ArrayList<Float> sampleList) {
        try {
            float mixDevation = 0.0f;
            int totalCount = sampleList.size();
            if (totalCount >= 1) {
                float min = Float.parseFloat(sampleList.get(0).toString());
                for (int i = 0; i < totalCount; i++) {
                    float temp = Float.parseFloat(sampleList.get(i).toString());
                    if (min > temp) {
                        min = temp;
                    }
                }
                mixDevation = min;
            }
            return mixDevation;
        } catch (Exception ex) {
            throw ex;
        }
    }

    //获取ArrayList的平均值
    private float ArrayListAru(ArrayList<Float> sampleList) {

        if (sampleList.size() == 0)
            return 0.0f;

        float sum = 0.0f;
        for (int i = 0; i < sampleList.size(); i++) {
            sum += sampleList.get(i);
        }
        float retult = sum / sampleList.size();
        return retult;
    }
}
