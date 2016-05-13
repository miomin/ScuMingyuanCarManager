package scu.mingyuan.com.carmanager.app.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.resource.MyUrl;
import scu.mingyuan.com.carmanager.http.httpparse.StringRequestListener;
import scu.mingyuan.com.carmanager.http.httprequest.MioRequest;
import scu.mingyuan.com.carmanager.http.httprequest.MioRequestManager;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class QueryBreakRuleFragment extends Fragment {

    // fragment的布局
    private View fragmentView;
    private String province;
    private String city;
    private EditText et_listener_number, et_engine_no, et_class_no;
    private TextView tv_not_found;
    private ProgressDialog dialog;

    private Button btn_query;

    public static QueryBreakRuleFragment newInstance() {
        QueryBreakRuleFragment fragment = new QueryBreakRuleFragment();
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
        fragmentView = inflater.inflate(R.layout.fragment_query_break_rule, container, false);
        et_listener_number = (EditText) fragmentView.findViewById(R.id.et_listener_number);
        et_engine_no = (EditText) fragmentView.findViewById(R.id.et_engine_no);
        et_class_no = (EditText) fragmentView.findViewById(R.id.et_class_no);
        tv_not_found = (TextView) fragmentView.findViewById(R.id.tv_not_found);
        btn_query = (Button) fragmentView.findViewById(R.id.btn_query);

        province = getResources().getStringArray(R.array.province)[0];
        city = getResources().getStringArray(R.array.city)[0];

        Spinner provinceSpinner = (Spinner) fragmentView.findViewById(R.id.provinceSpinner);
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] provinces = getResources().getStringArray(R.array.province);
                province = provinces[pos].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner citySpinner = (Spinner) fragmentView.findViewById(R.id.citySpinner);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] citys = getResources().getStringArray(R.array.city);
                city = citys[pos].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getResources().getString(R.string.wait));

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });

        return fragmentView;
    }

    public void query() {
        dialog.show();

        String listener_number = et_listener_number.getText().toString().trim();
        String engine_no = et_engine_no.getText().toString().trim();
        String class_no = et_class_no.getText().toString().trim();

        String url = MyUrl.PetrolStationUrl + "&city=" + city + "&hphm=" +
                listener_number + "&engineno=" + engine_no + "&classno=" + class_no;

        MioRequest request = new MioRequest(url, MioRequest.REQUSET_METHOD.GET, new StringRequestListener() {
            @Override
            public void onSuccess(String result) {
                if (result.substring(result.length() - 6, result.length() - 2).equals("10001")) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.query_not_found), Toast.LENGTH_LONG).show();
                    tv_not_found.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.query_not_found), Toast.LENGTH_LONG).show();
                    tv_not_found.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
            }

            @Override
            public void onFaild(Exception exception) {
                Toast.makeText(getActivity(), getResources().getString(R.string.query_failed), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        // 为request设置tag
        request.setTag(getActivity().toString());
        //执行request
        MioRequestManager.getInstance().excuteRequest(request);
    }
}
