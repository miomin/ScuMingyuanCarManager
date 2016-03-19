package scu.mingyuan.com.carmanager.activity;

import android.content.Context;
import android.util.Log;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.bean.PetrolStationList;
import scu.mingyuan.com.carmanager.cache.PetrolStationCache;
import scu.mingyuan.com.carmanager.httpparse.JsonRequestListener;
import scu.mingyuan.com.carmanager.httprequest.MioRequest;
import scu.mingyuan.com.carmanager.httprequest.MioRequestManager;
import scu.mingyuan.com.carmanager.resource.MyUrl;

/**
 * Created by 莫绪旻 on 16/3/18.
 */
public class HttpSample {

    public void loadPetrolStations(final Context context) {
        String url = MyUrl.PetrolStationUrl + "&lon=116.403119&lat=39.916042&format=2&r=3000";
        MioRequest request = new MioRequest(url, MioRequest.REQUSET_METHOD.GET, new JsonRequestListener<PetrolStationList>() {
            @Override
            public void onSuccess(PetrolStationList result) {
                PetrolStationCache.getInstance().setPetrolStationList(result);
                Log.i("miomin", result.toString());
            }

            @Override
            public void onFaild(Exception exception) {
                Log.i("miomin", context.getResources().getString(R.string.data_load_failed));
            }
        }.setRootKey("result"));
        // 为request设置tag
        request.setTag(context.toString());

        //执行request
        MioRequestManager.getInstance().excuteRequest(request);
    }
}
