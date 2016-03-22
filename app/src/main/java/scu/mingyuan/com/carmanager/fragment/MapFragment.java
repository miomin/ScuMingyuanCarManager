package scu.mingyuan.com.carmanager.fragment;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
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
import com.baidu.mapapi.map.BitmapDescriptorFactory;
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
import scu.mingyuan.com.carmanager.activity.PreOrderActivity;
import scu.mingyuan.com.carmanager.adapter.PlaceAdapter;
import scu.mingyuan.com.carmanager.bean.DrivingRouteOverlay;
import scu.mingyuan.com.carmanager.bean.PetrolStation;
import scu.mingyuan.com.carmanager.bean.PetrolStationList;
import scu.mingyuan.com.carmanager.cache.PetrolStationCache;
import scu.mingyuan.com.carmanager.httpparse.JsonRequestListener;
import scu.mingyuan.com.carmanager.httprequest.MioRequest;
import scu.mingyuan.com.carmanager.httprequest.MioRequestManager;
import scu.mingyuan.com.carmanager.resource.MyUrl;

/**
 * Created by 应俊康 on 16/2/29.
 */
public class MapFragment extends Fragment implements BDLocationListener, View.OnClickListener {

    // fragment的布局
    private View fragmentView;

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
    private PoiSearch searcher_park;
    private RoutePlanSearch searcher_route;

    private static final String TAG = "YJK";


    private LatLng myLocation;
    private String sourceAdd;
    private LatLng sourceLocation;

    private String desAdd;
    private LatLng desLocation;
    private String cityName;

    private ArrayList<PoiInfo> focus_list = new ArrayList<>();
    private ArrayList<PoiInfo> source_list = new ArrayList<>();
    private ArrayList<PoiInfo> des_list = new ArrayList<>();

    private PlaceAdapter place_adapter;
    private String desAddDes;
    private int pageNumber;
    private String key;

    private boolean isGasStationShow = false;
    private boolean isParkShow = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取Fragment的布局
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false);

        findViewById();
        setListenner();
        initMapOptions();
        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name);
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);

    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();

        return fragment;
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

    private void findViewById() {
        mapView = (MapView) fragmentView.findViewById(R.id.mapView);

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

        list_place = (PullToRefreshListView) fragmentView.findViewById(R.id.list_place);

        place_adapter = new PlaceAdapter(getContext(), focus_list);
        list_place.setAdapter(place_adapter);
        //只能在这里设置不可见，在布局中设置会导致一直不可见
        list_place.setVisibility(View.INVISIBLE);
        baiduMap = mapView.getMap();
    }

    private void setListenner() {

        edit_inputSourceAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String skey = s.toString();
                if (!skey.equals(sourceAdd) && !skey.equals(desAdd)) {


                    key = skey;
                    place_adapter.setType(PlaceAdapter.TYPE_SOURCE);
                    focus_list = source_list;
                    focus_list.clear();
                    place_adapter.setData(focus_list);
                    place_adapter.notifyDataSetChanged();

                    pageNumber = 1;
                    searcher.searchInCity(new PoiCitySearchOption().city(cityName).keyword(key).pageNum(pageNumber));

                    btn_navigation.setVisibility(View.GONE);
                    list_place.setVisibility(View.VISIBLE);
                }
            }
        });


        edit_inputDesAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String skey = s.toString();
                if (!skey.equals(sourceAdd) && !skey.equals(desAdd)) {

                    key = skey;
                    place_adapter.setType(PlaceAdapter.TYPE_DES);
                    focus_list = des_list;
                    focus_list.clear();
                    place_adapter.setData(focus_list);
                    place_adapter.notifyDataSetChanged();

                    pageNumber = 1;
                    searcher.searchInCity(new PoiCitySearchOption().city(cityName).keyword(key).pageNum(pageNumber));

                    btn_navigation.setVisibility(View.GONE);
                    list_place.setVisibility(View.VISIBLE);
                }
            }
        });

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    list_place.setVisibility(View.GONE);
                    btn_navigation.setVisibility(View.VISIBLE);

                    edit_inputDesAdd.setText(desAdd);
                    edit_inputSourceAdd.setText(sourceAdd);

                } else {
                    btn_navigation.setVisibility(View.GONE);
                    list_place.setVisibility(View.VISIBLE);

                }
            }
        };
        edit_inputSourceAdd.setOnFocusChangeListener(focusChangeListener);
        edit_inputDesAdd.setOnFocusChangeListener(focusChangeListener);


        list_place.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
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

                PoiInfo poi = focus_list.get(position - 1);
                if (focus_list == source_list) {

                    sourceAdd = poi.name;
                    sourceLocation = poi.location;
                    edit_inputSourceAdd.setText(sourceAdd);

                } else {

                    desAdd = poi.name;
                    desLocation = poi.location;
                    desAddDes = poi.address;
                    edit_inputDesAdd.setText(desAdd);
                    //显示终点位置信息
                    refreshDesLayout();

                }

                list_place.setVisibility(View.INVISIBLE);
                btn_navigation.setVisibility(View.VISIBLE);
            }
        });

        list_place.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
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

        btn_gasStation.setOnClickListener(this);
        btn_patk.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        btn_showLine.setOnClickListener(this);
        btn_swap.setOnClickListener(this);
        btn_navigation.setOnClickListener(this);

    }

    private void initMapOptions() {
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

        //去除底部放大缩小button
        for (int i = 0; i < mapView.getChildCount(); i++) {
            View child = mapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                child.setVisibility(View.GONE);
                break;
            }
        }

        //Poi点查询初始化
        searcher = PoiSearch.newInstance();
        searcher.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {

                List<PoiInfo> list = poiResult.getAllPoi();
                if (list != null) {
                    Log.i(TAG, "查找到Poi");
                    focus_list.addAll(list);

                    Log.i(TAG, "focus_list Size:" + focus_list.size());

                    int selection = list_place.getRefreshableView().getSelectedItemPosition();
                    place_adapter.notifyDataSetChanged();
                    list_place.getRefreshableView().setSelection(selection);

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
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_blue))
                                .zIndex(9).draggable(true);
                        baiduMap.addOverlay(markerOptions);
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

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                PetrolStation p = (PetrolStation) marker.getExtraInfo().get("pre");

                Log.i(TAG, "onMarkerClick "+p.toString());
                PreOrderActivity.startAction(getContext(), p);
                return false;
            }
        });

        //路径规划查询
        searcher_route = RoutePlanSearch.newInstance();
        searcher_route.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
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
        });

    }

    @Override
    public void onReceiveLocation(BDLocation location) {

        if (location == null || mapView == null) {
            return;
        }

        myLocation = new LatLng(location.getLatitude(),
                location.getLongitude());
        cityName = location.getCity();

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
            builder.target(myLocation).zoom(18.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory
                    .newMapStatus(builder.build()));


            sourceAdd = location.getStreet();
            sourceLocation = new LatLng(location.getLatitude(), location.getLongitude());

            edit_inputSourceAdd.setText(location.getStreet());
        }
    }

    private void refreshDesLayout() {
        tv_desAdd.setText(desAdd);
        tv_desAddDes.setText(desAddDes);
        layout_desInf.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        clearEditFocus();
        switch (v.getId()) {
            case R.id.btn_swap:

                if (desLocation != null && sourceLocation != null) {
                    String temp_add;
                    LatLng temp_location;

                    temp_add = sourceAdd;
                    sourceAdd = desAdd;
                    desAdd = temp_add;

                    temp_location = sourceLocation;
                    sourceLocation = desLocation;
                    desLocation = temp_location;

                    edit_inputSourceAdd.setText(sourceAdd);
                    edit_inputDesAdd.setText(desAdd);

                    refreshDesLayout();
                }
                break;
            case R.id.btn_park:

                baiduMap.clear();

                if (isParkShow) {
                    //清除停车场marker
                    isParkShow = false;
                    btn_patk.setImageResource(R.drawable.ic_park_n);


                } else {
                    //添加停车场marker

                    if (isGasStationShow) {
                        isGasStationShow = false;
                        btn_gasStation.setImageResource(R.drawable.ic_gas_station_n);
                    }

                    isParkShow = true;
                    btn_patk.setImageResource(R.drawable.ic_park_s);
                    searcher_park.searchNearby(new PoiNearbySearchOption().keyword("停车场").location(myLocation).pageCapacity(20).pageNum(1).radius(10000));
                }


                break;
            case R.id.btn_gasStation:

                baiduMap.clear();

                if (isGasStationShow) {

                    //清除停车场marker
                    isGasStationShow = false;
                    btn_gasStation.setImageResource(R.drawable.ic_gas_station_n);

                } else {

                    //添加停车场marker
                    if (isParkShow) {
                        isParkShow = false;
                        btn_patk.setImageResource(R.drawable.ic_park_n);

                    }

                    isGasStationShow = true;
                    btn_gasStation.setImageResource(R.drawable.ic_gas_station_s);

                    String url = MyUrl.PetrolStationUrl + "&lon=" + myLocation.longitude + "&lat=" + myLocation.latitude + "&format=2&r=3000";
                    MioRequest request = new MioRequest(url, MioRequest.REQUSET_METHOD.GET, new JsonRequestListener<PetrolStationList>() {
                        @Override
                        public void onSuccess(PetrolStationList result) {
                            PetrolStationCache.getInstance().setPetrolStationList(result);
                            ArrayList<PetrolStation> petrolStations = PetrolStationCache.getInstance().getPetrolStationList().getData();


                            for (PetrolStation p : petrolStations) {
                                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(p.getLat(), p.getLon()))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_red))
                                        .zIndex(9).draggable(true);
                                Marker marker = (Marker) baiduMap.addOverlay(markerOptions);
                                Bundle b = new Bundle();
                                b.putSerializable("pre", p);
                                marker.setExtraInfo(b);
                            }

                        }

                        @Override
                        public void onFaild(Exception exception) {
                            Log.i("miomin", getContext().getResources().getString(R.string.data_load_failed));
                        }
                    }.setRootKey("result"));
                    // 为request设置tag
                    request.setTag(getContext().toString());

                    //执行request
                    MioRequestManager.getInstance().excuteRequest(request);
                }

                break;
            case R.id.btn_location:

                //将地图以定位为中心
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(myLocation));

                break;
            case R.id.btn_navigation:
                if (desLocation != null && sourceLocation != null) {

                } else {
                    Toast.makeText(getContext(), "请选择起点", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.btn_showLine:
                baiduMap.clear();

                if (desLocation != null && sourceLocation != null) {


                    Toast.makeText(getContext(), "显示路线", Toast.LENGTH_SHORT).show();

                    PlanNode source = PlanNode.withLocation(new LatLng(sourceLocation.latitude, sourceLocation.longitude));
                    PlanNode destination = PlanNode.withLocation(new LatLng(sourceLocation.latitude, desLocation.longitude));


                    searcher_route.drivingSearch(new DrivingRoutePlanOption().from(source).to(destination));

                } else {
                    Toast.makeText(getContext(), "请选择起点", Toast.LENGTH_SHORT).show();

                }
                break;

            default:
                break;
        }
    }

    private void clearEditFocus() {

        edit_inputDesAdd.clearFocus();
        edit_inputSourceAdd.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
