package scu.mingyuan.com.carmanager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.adapter.MyCarAdapter;
import scu.mingyuan.com.carmanager.bean.MyCar;
import scu.mingyuan.com.carmanager.bean.MyUser;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class MyCarFragment extends Fragment {

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

        final ArrayList<MyCar> myCars = new ArrayList<>();

        MyUser currentUser = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        BmobQuery<MyCar> query = new BmobQuery<MyCar>();
        query.addWhereEqualTo("owner", currentUser);    // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("owner");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(getActivity(), new FindListener<MyCar>() {
            @Override
            public void onSuccess(List<MyCar> object) {
                for (int i = 0; i < object.size(); i++) {
                    myCars.add(object.get(i));
                }
                myCarAdapter = new MyCarAdapter(myCars, getActivity());
                lvMycar.setAdapter(myCarAdapter);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }
}
