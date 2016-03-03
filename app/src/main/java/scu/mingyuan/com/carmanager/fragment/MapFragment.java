package scu.mingyuan.com.carmanager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import scu.mingyuan.com.carmanager.R;

/**
 * Created by 应俊康 on 16/2/29.
 */
public class MapFragment extends Fragment implements BDLocationListener {

    // fragment的布局
    private View fragmentView;

    private EditText edit_inputSourceAdd;
    private EditText edit_inputDesAdd;
    private LinearLayout btn_swap;

    private MapView mapView;
    private BaiduMap baiduMap;
    boolean isFirstLoc = true;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取Fragment的布局
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false);

        findView();

        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name);
    }

    private void findView() {

        mapView = (MapView) fragmentView.findViewById(R.id.mapView);
        edit_inputSourceAdd = (EditText) fragmentView.findViewById(R.id.edit_inputSourceAdd);
        edit_inputDesAdd = (EditText) fragmentView.findViewById(R.id.edit_inputSourceAdd);
        btn_swap = (LinearLayout) fragmentView.findViewById(R.id.btn_swap);

        baiduMap = mapView.getMap();
        int childCount = mapView.getChildCount();
        View zoom = null;
        for (int i = 0; i < childCount; i++) {
            View child = mapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                zoom = child;
                break;
            }
        }
        zoom.setVisibility(View.GONE);

        // 开启定位
        baiduMap.setMyLocationEnabled(true);

        // 定位初始化
        LocationClient mLocClient = new LocationClient(getContext());
        mLocClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setNeedDeviceDirect(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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

        Log.i("YJK",
                "Lat:" + location.getLatitude() +
                        "  Lng" + location.getLongitude() +
                        "  Direction:" + location.getDirection() +
                        "  Direct:" + location.getDerect());

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
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

        }
    }
}
