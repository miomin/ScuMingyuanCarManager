package scu.mingyuan.com.carmanager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import scu.mingyuan.com.carmanager.R;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class MapFragment extends Fragment {

    // fragment的布局
    private View fragmentView;

    private EditText edit_inputSourceAdd;
    private EditText edit_inputDesAdd;
    private LinearLayout btn_swap;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name);
    }

    private void findView() {

//        edit_inputSourceAdd = (EditText) fragmentView.findViewById(R.id.edit_inputSourceAdd);
//        edit_inputDesAdd = (EditText) fragmentView.findViewById(R.id.edit_inputSourceAdd);
//        btn_swap = (LinearLayout) fragmentView.findViewById(R.id.btn_swap);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取Fragment的布局
        //yjk
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false);

//        MapView view = new MapView(getContext());

        findView();

        return fragmentView;
    }
}
