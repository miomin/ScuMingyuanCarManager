package scu.mingyuan.com.carmanager.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import scu.mingyuan.com.carmanager.R;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class MusicFragment extends Fragment {

    // fragment的布局
    private View fragmentView;

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.drawer_music);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取Fragment的布局
        fragmentView = inflater.inflate(R.layout.fragment_music, container, false);
        return fragmentView;
    }
}
