package scu.mingyuan.com.carmanager.http.httpinterface;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;

import scu.mingyuan.com.carmanager.http.httperror.MioException;


/**
 * Created by 莫绪旻 on 16/3/15.
 */
public interface IMioRequestListener<T> {
    void onSuccess(T result);

    void onFaild(Exception exception);

    T parse(HttpURLConnection connection) throws IOException, JSONException, MioException;
}
