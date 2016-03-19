package scu.mingyuan.com.carmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.List;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.adapter.PlaceAdapter;

/**
 * Created by 应俊康 on 16/2/29.
 */
public class MapFragment extends Fragment implements BDLocationListener, View.OnClickListener {

    // fragment的布局
    private View fragmentView;

    private AppBarLayout mbar;

    private LinearLayout layout_input;
    private EditText edit_inputSourceAdd;
    private EditText edit_inputDesAdd;
    private LinearLayout btn_swap;

    private ImageButton btn_gasStation;
    private ImageButton btn_patk;
    private ImageButton btn_location;
    private FloatingActionButton btn_navigation;

    private ListView list_place;

    private MapView mapView;
    private BaiduMap baiduMap;
    boolean isFirstLoc = true;
    private LocationClient LocationClient;
    private PoiSearch searcher;

    private static final String TAG = "YJK";

    private boolean isGasStationShow = false;
    private boolean isParkShow = false;
    private boolean isLocationOn = false;

    private String sourceAdd;
    private String desAdd;
    private String cityName;
    private PlaceAdapter place_adapter;

    private EditText focusEdit;

    private List<PoiInfo> poi_list;
    private int screenWidth;
    private int screenHeigth;

    private View.OnFocusChangeListener focusChangeListner = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            //如果焦点edit改变则list滑出


            if (focusEdit != v) {
                edit_inputDesAdd.setText(desAdd);
                edit_inputSourceAdd.setText(sourceAdd);
                hideViews();
            }
            if (hasFocus) {
                focusEdit = (EditText) v;
            }
        }
    };
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (focusEdit != null && !TextUtils.isEmpty(s)) {
                Log.i(TAG, "开始搜索周边Poi   s:" + s);
                searcher.searchInCity(new PoiCitySearchOption().city(cityName).keyword(s.toString()).pageNum(1));
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    };

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取Fragment的布局
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false);

        init();

        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name);
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    private void init() {

        screenWidth = getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth();
        screenHeigth = getActivity().getWindow().getWindowManager().getDefaultDisplay().getHeight();

        mapView = (MapView) fragmentView.findViewById(R.id.mapView);

        layout_input = (LinearLayout) fragmentView.findViewById(R.id.layout_input);
        edit_inputSourceAdd = (EditText) fragmentView.findViewById(R.id.edit_inputSourceAdd);
        edit_inputDesAdd = (EditText) fragmentView.findViewById(R.id.edit_inputDesAdd);

        btn_swap = (LinearLayout) fragmentView.findViewById(R.id.btn_swap);
        btn_gasStation = (ImageButton) fragmentView.findViewById(R.id.btn_gasStation);
        btn_patk = (ImageButton) fragmentView.findViewById(R.id.btn_park);
        btn_location = (ImageButton) fragmentView.findViewById(R.id.btn_location);
        btn_navigation = (FloatingActionButton) fragmentView.findViewById(R.id.btn_navigation);

        edit_inputSourceAdd.setOnFocusChangeListener(focusChangeListner);
        edit_inputDesAdd.setOnFocusChangeListener(focusChangeListner);

        list_place = (ListView) fragmentView.findViewById(R.id.list_place);

        list_place.setBackgroundResource(R.color.whiteLight);
//        list_place.setElevation((float) 3);

        list_place.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                }
                return false;
            }
        });
        list_place.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = poi_list.get(position).name;
                if (focusEdit == edit_inputSourceAdd) {
                    sourceAdd = name;
                } else {
                    desAdd = name;
                }
                focusEdit.setText(name);
                hideViews();
            }
        });


        mbar = (AppBarLayout) getActivity().findViewById(R.id.appbar);

        btn_swap.setOnClickListener(this);
        btn_gasStation.setOnClickListener(this);
        btn_patk.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        btn_navigation.setOnClickListener(this);

        edit_inputDesAdd.clearFocus();
        edit_inputSourceAdd.clearFocus();

        baiduMap = mapView.getMap();
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                clearEditFocus();
                hideViews();

            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        //去除底部放大缩小button
        for (int i = 0; i < mapView.getChildCount(); i++) {
            View child = mapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                child.setVisibility(View.GONE);
                break;
            }
        }

        // 定位初始化
        LocationClient = new LocationClient(getContext());
        LocationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setNeedDeviceDirect(true);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);

        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        LocationClient.setLocOption(option);
        LocationClient.start();

        //Poi点查询初始化
        searcher = PoiSearch.newInstance();
        searcher.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {

                poi_list = poiResult.getAllPoi();

                if (poi_list != null) {
                    //初始化地理位置的adapter
                    Log.i(TAG, "检索到周边poi");
                    showViews();
                    int type;
                    if (focusEdit == edit_inputDesAdd) {
                        type = PlaceAdapter.TYPE_DES;
                    } else {
                        type = PlaceAdapter.TYPE_SOURCE;
                    }
                    place_adapter = new PlaceAdapter(getContext(), poi_list, type);
                    list_place.setAdapter(place_adapter);
                    place_adapter.notifyDataSetChanged();
                }
                //没有搜索到结果
                else {
                    Log.i(TAG, "没有检索到周边poi");
                    hideViews();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart ");
    }

    @Override
    public void onPause() {
        super.onPause();
        baiduMap.setMyLocationEnabled(false);
        LocationClient.stop();
        mapView.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        isFirstLoc = true;
        baiduMap.setMyLocationEnabled(true);
        LocationClient.start();
        mapView.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onReceiveLocation(BDLocation location) {

        if (location == null || mapView == null) {
            return;
        }

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(location.getDirection())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        baiduMap.setMyLocationConfigeration(config);
        baiduMap.setMyLocationData(locData);


        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory
                    .newMapStatus(builder.build()));

            cityName = location.getCity();

            Log.i(TAG, "onReceiveLocation " + location.getStreet());
            sourceAdd = location.getStreet();
            edit_inputSourceAdd.setText(sourceAdd);


            //edit初始化完成后为edit添加监听器
            edit_inputSourceAdd.addTextChangedListener(textWatcher);
            edit_inputDesAdd.addTextChangedListener(textWatcher);

        }
    }

    @Override
    public void onClick(View v) {

        clearEditFocus();
        hideViews();
        switch (v.getId()) {
            case R.id.btn_swap:

                sourceAdd = edit_inputSourceAdd.getText().toString();
                desAdd = edit_inputDesAdd.getText().toString();

                String temp;
                temp = sourceAdd;
                sourceAdd = desAdd;
                desAdd = temp;

                edit_inputSourceAdd.setText(sourceAdd);
                edit_inputDesAdd.setText(desAdd);

                break;
            case R.id.btn_park:

                showViews();
                if (isParkShow) {
                    //清除停车场marker
                    btn_patk.setImageResource(R.drawable.ic_park_n);

                } else {
                    //添加停车场marker
                    btn_patk.setImageResource(R.drawable.ic_park_s);

                }
                isParkShow = !isParkShow;

                break;
            case R.id.btn_gasStation:
                if (isGasStationShow) {
                    //清除加油站marker
                    btn_gasStation.setImageResource(R.drawable.ic_gas_station_n);

                } else {
                    //添加加油站marker
                    btn_gasStation.setImageResource(R.drawable.ic_gas_station_s);

                }
                isGasStationShow = !isGasStationShow;

                break;
            case R.id.btn_location:
                if (isLocationOn) {
                    //开启地图以定位为中心
                    btn_location.setImageResource(R.drawable.ic_location_n);

                } else {
                    //关闭地图以定位为中心
                    btn_location.setImageResource(R.drawable.ic_location_s);
                }
                isLocationOn = !isLocationOn;

                //跳转到导航界面
                break;

            default:
                break;
        }
    }


    //隐藏显示地理位置列表的list
    private void hideViews() {

        btn_navigation.setVisibility(View.VISIBLE);
        list_place.setVisibility(View.GONE);
    }

    private void showViews() {

        btn_navigation.setVisibility(View.GONE);
        list_place.setVisibility(View.VISIBLE);
    }

    private void clearEditFocus() {
        focusEdit = null;
        edit_inputDesAdd.clearFocus();
        edit_inputSourceAdd.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
