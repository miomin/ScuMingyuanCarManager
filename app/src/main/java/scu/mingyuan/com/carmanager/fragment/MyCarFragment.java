package scu.mingyuan.com.carmanager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.activity.MyCarDetailActivity;
import scu.mingyuan.com.carmanager.adapter.MyCarAdapter;
import scu.mingyuan.com.carmanager.bean.MyCar;
import scu.mingyuan.com.carmanager.bean.MyUser;
import scu.mingyuan.com.carmanager.cache.MyCarCache;

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
        myCarAdapter = new MyCarAdapter(MyCarCache.getMyCarCache().getMyCars(), getActivity());
        lvMycar.setAdapter(myCarAdapter);

        final MyUser currentUser = BmobUser.getCurrentUser(getActivity(), MyUser.class);


        if (MyCarCache.getMyCarCache().getMyCars().size() == 0) {
            loadData(currentUser);
        }

        materialRefreshLayout = (MaterialRefreshLayout) fragmentView.findViewById(R.id.refresh_layout);
        materialRefreshLayout.setLoadMore(true);
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
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }
}
