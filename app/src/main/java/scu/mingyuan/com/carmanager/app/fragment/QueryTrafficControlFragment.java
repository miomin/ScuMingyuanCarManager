package scu.mingyuan.com.carmanager.app.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.app.bean.TrafficControlResult;
import scu.mingyuan.com.carmanager.resource.MyUrl;
import scu.mingyuan.com.carmanager.http.httpparse.JsonRequestListener;
import scu.mingyuan.com.carmanager.http.httprequest.MioRequest;
import scu.mingyuan.com.carmanager.http.httprequest.MioRequestManager;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class QueryTrafficControlFragment extends Fragment {

    // fragment的布局
    private View fragmentView;

    private Spinner city_traffic_control;

    private Button btn_query;

    private String city = "";

    private ProgressDialog dialog;

    private TextView tv_xxweihao, tv_result;


    public static QueryTrafficControlFragment newInstance() {
        QueryTrafficControlFragment fragment = new QueryTrafficControlFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取Fragment的布局
        fragmentView = inflater.inflate(R.layout.fragment_query_traffic_control, container, false);

        tv_xxweihao = (TextView) fragmentView.findViewById(R.id.tv_xxweihao);
        tv_result = (TextView) fragmentView.findViewById(R.id.tv_result);

        city_traffic_control = (Spinner) fragmentView.findViewById(R.id.city_traffic_control);
        city_traffic_control.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] citys = getResources().getStringArray(R.array.city_traffic_control_pingying);
                city = citys[pos].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_query = (Button) fragmentView.findViewById(R.id.btn_query);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getResources().getString(R.string.wait));

        return fragmentView;
    }

    public void query() {
        dialog.show();

        String url = MyUrl.TrafficControlUrl + "&city=" + city + "&type=2";

        MioRequest request = new MioRequest(url, MioRequest.REQUSET_METHOD.GET, new JsonRequestListener<TrafficControlResult>() {
            @Override
            public void onSuccess(TrafficControlResult result) {
                dialog.dismiss();
                String xxweihao = "";
                for (int i = 0; i < result.getXxweihao().length; i++) {
                    xxweihao += result.getXxweihao()[i] + ",";
                }
                tv_xxweihao.setText(xxweihao);
                for (int i = 0; i < result.getDes().size(); i++) {
                    tv_result.setText(result.getDes().get(i).getTime() + "\n" +
                            result.getDes().get(i).getPlace() + "\n"
                            + result.getFine() + "\n" + result.getRemarks() + "\n");
                }
            }

            @Override
            public void onFaild(Exception exception) {
                dialog.dismiss();
            }
        }.setRootKey("result"));
        // 为request设置tag
        request.setTag(getActivity().toString());
        //执行request
        MioRequestManager.getInstance().excuteRequest(request);
    }
}
