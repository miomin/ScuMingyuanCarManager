package scu.mingyuan.com.carmanager.fragment;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.adapter.PlaceAdapter;
import scu.mingyuan.com.carmanager.bean.DrivingRouteOverlay;

/**
 * Created by 应俊康 on 16/2/29.
 */
public class MapFragment extends Fragment implements BDLocationListener, View.OnClickListener, OnGetRoutePlanResultListener {

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

    private RelativeLayout layout_desInf;
    private TextView tv_desAdd;
    private TextView tv_desAddDes;

    private Button btn_showLine;
    private Button btn_navigation;


    private PullToRefreshListView list_place;

    private MapView mapView;
    private BaiduMap baiduMap;
    boolean isFirstLoc = true;
    private LocationClient LocationClient;
    private PoiSearch searcher;
    private PoiSearch searcher_gas_station;
    private PoiSearch searcher_park;
    private RoutePlanSearch searcher_route;

    private static final String TAG = "YJK";

    private boolean isGasStationShow = false;
    private boolean isParkShow = false;

    private LatLng mylatlng;
    private PoiInfo sourcePoi;
    private PoiInfo desPoi;
    private String cityName;
    private PlaceAdapter place_adapter;

    private EditText focusEdit;

    private List<PoiInfo> poi_list = new ArrayList<>();
    private List<Marker> gas_station_list = new ArrayList<>();
    private List<Marker> park_list = new ArrayList<>();


    private String key;
    private int pageNumber = 1;

    private BitmapDescriptor redmarker_bg;
    private BitmapDescriptor greenmarker_bg;
    private BitmapDescriptor bluemarker_bg;
    private BitmapDescriptor yellowmarker_bg;

    private View.OnFocusChangeListener focusChangeListner = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            //如果焦点edit改变则list滑出
            if (focusEdit != v) {
                if (sourcePoi != null) {
                    edit_inputSourceAdd.setText(sourcePoi.name);
                }
                if (desPoi != null) {
                    edit_inputDesAdd.setText(desPoi.name);
                }
                hideViews();
                pageNumber = 1;
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

                poi_list.clear();
                key = s.toString();
                searcher.searchInCity(new PoiCitySearchOption().city(cityName).keyword(key).pageNum(pageNumber));
                pageNumber++;

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

        mapView = (MapView) fragmentView.findViewById(R.id.mapView);

        layout_input = (LinearLayout) fragmentView.findViewById(R.id.layout_input);
        edit_inputSourceAdd = (EditText) fragmentView.findViewById(R.id.edit_inputSourceAdd);
        edit_inputDesAdd = (EditText) fragmentView.findViewById(R.id.edit_inputDesAdd);

        btn_swap = (LinearLayout) fragmentView.findViewById(R.id.btn_swap);
        btn_gasStation = (ImageButton) fragmentView.findViewById(R.id.btn_gasStation);
        btn_patk = (ImageButton) fragmentView.findViewById(R.id.btn_park);
        btn_location = (ImageButton) fragmentView.findViewById(R.id.btn_location);

        layout_desInf = (RelativeLayout) fragmentView.findViewById(R.id.layout_desInf);
        tv_desAdd = (TextView) fragmentView.findViewById(R.id.tv_desAdd);
        tv_desAddDes = (TextView) fragmentView.findViewById(R.id.tv_desAddDes);

        btn_showLine = (Button) fragmentView.findViewById(R.id.btn_showLine);
        btn_navigation = (Button) fragmentView.findViewById(R.id.btn_navigation);

        edit_inputSourceAdd.setOnFocusChangeListener(focusChangeListner);
        edit_inputDesAdd.setOnFocusChangeListener(focusChangeListner);

        list_place = (PullToRefreshListView) fragmentView.findViewById(R.id.list_place);

        list_place.setBackgroundResource(R.color.whiteLight);
        list_place.setVisibility(View.INVISIBLE);

        list_place.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "收起软键盘");
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

                PoiInfo poi = poi_list.get(position);
                if (focusEdit == edit_inputSourceAdd) {
                    sourcePoi = poi;

                } else {
                    desPoi = poi;
                    //显示终点位置信息
                    refreshDesLayout();

                }
                focusEdit.setText(poi.name);
                hideViews();
            }
        });


        mbar = (AppBarLayout) getActivity().findViewById(R.id.appbar);

        btn_swap.setOnClickListener(this);
        btn_gasStation.setOnClickListener(this);
        btn_patk.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        btn_navigation.setOnClickListener(this);
        btn_showLine.setOnClickListener(this);

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

        place_adapter = new PlaceAdapter(getContext(), poi_list);

        list_place.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        list_place.setAdapter(place_adapter);
        list_place.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                list_place.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        searcher.searchInCity(new PoiCitySearchOption().city(cityName).keyword(key).pageNum(pageNumber));
                        pageNumber++;
                        list_place.onRefreshComplete();

                    }
                }, 1000);
            }
        });

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

                List<PoiInfo> list = poiResult.getAllPoi();

                if (list != null) {
                    //初始化地理位置的adapter
                    Log.i(TAG, "检索到周边poi");
                    for (PoiInfo inf : list) {
                        poi_list.add(inf);
                    }
                    int type;
                    if (focusEdit == edit_inputDesAdd) {
                        type = PlaceAdapter.TYPE_DES;
                    } else {
                        type = PlaceAdapter.TYPE_SOURCE;
                    }
                    place_adapter.setType(type);
                    int selection = list_place.getRefreshableView().getSelectedItemPosition();
                    place_adapter.notifyDataSetChanged();
                    list_place.getRefreshableView().setSelection(selection);

                    showViews();
                    //没有搜索到结果
                } else {
                    Log.i(TAG, "没有检索到周边poi");
                    hideViews();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });


        //周边加油站
        searcher_gas_station = PoiSearch.newInstance();
        searcher_gas_station.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {

                List<PoiInfo> list = poiResult.getAllPoi();

                if (list != null) {
                    //初始化地理位置的adapter
                    Log.i(TAG, "检索到加油站poi");
                    for (PoiInfo inf : list) {
                        MarkerOptions markerOptions = new MarkerOptions().position(inf.location)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_blue))
                                .zIndex(9).draggable(true);
                        Marker marker = (Marker) baiduMap.addOverlay(markerOptions);
                        gas_station_list.add(marker);

                    }

                } else {
                    Log.i(TAG, "未检索到加油站poi");
                    Toast.makeText(getContext(), "未查询到周边加油站", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });


        //周边停车场
        searcher_park = PoiSearch.newInstance();
        searcher_park.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {

                List<PoiInfo> list = poiResult.getAllPoi();

                if (list != null) {
                    //初始化地理位置的adapter
                    Log.i(TAG, "检索到停车场poi");
                    for (PoiInfo inf : list) {
                        MarkerOptions markerOptions = new MarkerOptions().position(inf.location)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_red))
                                .zIndex(9).draggable(true);
                        Marker marker = (Marker) baiduMap.addOverlay(markerOptions);
                        park_list.add(marker);
                    }

                } else {
                    Log.i(TAG, "未检索到停车场poi");
                    Toast.makeText(getContext(), "未查询到周边停车场", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });

        //路径规划查询
        searcher_route = RoutePlanSearch.newInstance();
        searcher_route.setOnGetRoutePlanResultListener(this);

        //为marker添加事件监听
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (gas_station_list.indexOf(marker) != -1) {

                    //启动预约加油界面
                }
                return false;
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

        mylatlng = new LatLng(location.getLatitude(),
                location.getLongitude());

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(location.getDirection())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null);
        baiduMap.setMyLocationConfigeration(config);
        baiduMap.setMyLocationData(locData);


        if (isFirstLoc) {
            isFirstLoc = false;


            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(mylatlng).zoom(18.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory
                    .newMapStatus(builder.build()));

            cityName = location.getCity();

            sourcePoi = new PoiInfo();
            sourcePoi.name = location.getStreet();
            sourcePoi.location = new LatLng(location.getLatitude(), location.getLongitude());
            sourcePoi.address = location.getAddrStr();
            sourcePoi.city = location.getCity();

            edit_inputSourceAdd.setText(sourcePoi.name);


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

                PoiInfo temp;
                temp = sourcePoi;
                sourcePoi = desPoi;
                desPoi = temp;

                edit_inputSourceAdd.setText(sourcePoi.name);
                edit_inputDesAdd.setText(desPoi.name);

                refreshDesLayout();

                break;
            case R.id.btn_park:

                baiduMap.clear();
                park_list.clear();
                gas_station_list.clear();

                if (isParkShow) {
                    //清除停车场marker
                    btn_patk.setImageResource(R.drawable.ic_park_n);


                } else {
                    //添加停车场marker
                    btn_patk.setImageResource(R.drawable.ic_park_s);
                    btn_gasStation.setImageResource(R.drawable.ic_gas_station_n);
                    searcher_park.searchNearby(new PoiNearbySearchOption().keyword("停车场").location(mylatlng).pageCapacity(20).pageNum(1).radius(10000));
                }

                isGasStationShow = !isGasStationShow;
                isParkShow = !isParkShow;

                break;
            case R.id.btn_gasStation:

                baiduMap.clear();
                park_list.clear();
                gas_station_list.clear();

                if (isGasStationShow) {
                    //清除停车场marker
                    btn_gasStation.setImageResource(R.drawable.ic_gas_station_n);

                } else {
                    //添加停车场marker
                    btn_patk.setImageResource(R.drawable.ic_park_n);
                    btn_gasStation.setImageResource(R.drawable.ic_gas_station_s);
                    searcher_gas_station.searchNearby(new PoiNearbySearchOption().keyword("加油站").location(mylatlng).pageCapacity(20).pageNum(1).radius(10000));
                }

                isGasStationShow = !isGasStationShow;
                isParkShow = !isParkShow;
                break;
            case R.id.btn_location:

                //将地图以定位为中心
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(mylatlng));

                break;
            case R.id.btn_navigation:
                if (desPoi != null && sourcePoi != null) {
                    Log.i(TAG, "起点--name:" + sourcePoi.name + "  lat:" + sourcePoi.location.latitude + "  long:" + sourcePoi.location.longitude);
                    Log.i(TAG, "终点--name:" + desPoi.name + "  lat:" + desPoi.location.latitude + "  long:" + desPoi.location.longitude);

                } else {
                    Toast.makeText(getContext(), "请选择起点", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.btn_showLine:
                baiduMap.clear();

                if (desPoi != null && sourcePoi != null) {


                    Toast.makeText(getContext(), "显示路线", Toast.LENGTH_SHORT).show();

                    PlanNode source = PlanNode.withLocation(new LatLng(sourcePoi.location.latitude, sourcePoi.location.longitude));
                    PlanNode destination = PlanNode.withLocation(new LatLng(desPoi.location.latitude, desPoi.location.longitude));


                    searcher_route.drivingSearch(new DrivingRoutePlanOption().from(source).to(destination));

                } else {
                    Toast.makeText(getContext(), "请选择起点", Toast.LENGTH_SHORT).show();

                }
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

    private void refreshDesLayout() {
        tv_desAdd.setText(desPoi.name);
        tv_desAddDes.setText(desPoi.address);
        layout_desInf.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }


        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
}
