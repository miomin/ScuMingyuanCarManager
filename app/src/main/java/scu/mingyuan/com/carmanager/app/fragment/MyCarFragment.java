package scu.mingyuan.com.carmanager.app.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.app.activity.MyCarDetailActivity;
import scu.mingyuan.com.carmanager.app.adapter.MyCarAdapter;
import scu.mingyuan.com.carmanager.app.bean.MyCar;
import scu.mingyuan.com.carmanager.app.bean.MyUser;
import scu.mingyuan.com.carmanager.app.cache.MyCarCache;
import scu.mingyuan.com.carmanager.app.cache.UserCache;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class MyCarFragment extends Fragment {

    // 下拉刷新的布局
    private MaterialRefreshLayout materialRefreshLayout;

    // fragment的布局
    private View fragmentView;
    private ListView lvMycar;
    private MyCarAdapter myCarAdapter;

    // 加载中dialog
    private ProgressDialog loadingdialog;

    public static MyCarFragment newInstance() {
        MyCarFragment fragment = new MyCarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.drawer_mycar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取Fragment的布局
        fragmentView = inflater.inflate(R.layout.fragment_mycar, container, false);

        initView();
        return fragmentView;
    }

    private void initView() {
        lvMycar = (ListView) fragmentView.findViewById(R.id.lvMycar);
        myCarAdapter = new MyCarAdapter(MyCarCache.getMyCarCache().getMyCars(), getActivity(), this);
        lvMycar.setAdapter(myCarAdapter);

        final MyUser currentUser = BmobUser.getCurrentUser(getActivity(), MyUser.class);

        if (MyCarCache.getMyCarCache().getMyCars().size() == 0) {
            loadingdialog = new ProgressDialog(getActivity());
            loadingdialog.setMessage(getResources().getString(R.string.data_loading));
            loadingdialog.show();
            loadData(currentUser);
        }

        materialRefreshLayout = (MaterialRefreshLayout) fragmentView.findViewById(R.id.refresh_layout);
        materialRefreshLayout.setLoadMore(false);
        materialRefreshLayout.finishRefreshLoadMore();
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                loadData(currentUser);
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                    }
                }, 1000);
            }

            @Override
            public void onfinish() {
//                Toast.makeText(getActivity(), "finish", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
//                Toast.makeText(getActivity(), "load more", Toast.LENGTH_LONG).show();

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                }, 1000);
            }
        });

        lvMycar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyCarDetailActivity.startActivity(getActivity(), MyCarCache.getMyCarCache().getMyCars().get(position - 1).getId());
            }
        });
    }

    private void loadData(MyUser owner) {

        BmobQuery<MyCar> query = new BmobQuery<MyCar>();
        query.addWhereEqualTo("owner", owner);    // 查询当前用户的所有汽车
        query.order("-updatedAt");
        query.include("owner");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(getActivity(), new FindListener<MyCar>() {
            @Override
            public void onSuccess(List<MyCar> object) {
                MyCarCache.getMyCarCache().clear();
                for (int i = 0; i < object.size(); i++) {
                    MyCarCache.getMyCarCache().add(object.get(i));
                }
                if (loadingdialog != null)
                    loadingdialog.dismiss();
            }

            @Override
            public void onError(int code, String msg) {


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果
        if (resultCode == Activity.RESULT_OK) {
            loadingdialog.show();
            Bundle bundle = data.getExtras();
            String scanResult[] = bundle.getString("result").split(";");
            //宝马;Z4;2013款 sDrive20i领先型;川A 66666;DHFHFJDH787878FD;3.6;45.6;四川 成都;1;1;1;2015-04-12 00:00:00;3466197373;11111;57.8;2.0L;8.8-12.1L;自动;跑车;2门2座
            MyCar myCar = new MyCar();
            myCar.setId(MyCarCache.getMyCarCache().getMyCars().size() + 1);
            myCar.setBrand(scanResult[0]);
            myCar.setCar(scanResult[1]);
            myCar.setModel(scanResult[2]);
            myCar.setLicense_plate_number(scanResult[3]);
            myCar.setEngine_number(scanResult[4]);
            myCar.setMileage(Float.parseFloat(scanResult[5]));
            myCar.setRemaining_oil(Float.parseFloat(scanResult[6]));
            myCar.setCar_location(scanResult[7]);
            myCar.setEngine_statu(true);
            myCar.setSpeed_changing_box_statu(true);
            myCar.setAntomative_lighting_statu(true);
            myCar.setRegistration_date(new BmobDate(new Date(2015, 4, 12, 0, 0)));
            myCar.setOwner(UserCache.getUserCache().getCurrentUser());
            myCar.setImg(scanResult[13]);
            myCar.setPrice(scanResult[14]);
            myCar.setDisplacement(scanResult[15]);
            myCar.setOil_consumption(scanResult[16]);
            myCar.setSpeed_changing_box(scanResult[17]);
            myCar.setCar_type(scanResult[18]);
            myCar.setBody_structure(scanResult[19]);
            myCar.setOil_day(new ArrayList<Float>());
            myCar.setMileage_day(new ArrayList<Float>());
            MyCarCache.getMyCarCache().add(myCar);

            myCar.save(getActivity(), new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getActivity(), getResources().getString(R.string.data_add_succeed), Toast.LENGTH_LONG).show();
                    loadingdialog.dismiss();
                }

                @Override
                public void onFailure(int code, String arg0) {
                    // 添加失败
                    Toast.makeText(getActivity(), getResources().getString(R.string.data_load_failed), Toast.LENGTH_LONG).show();
                    loadingdialog.dismiss();
                }
            });
        }
    }
}
