package scu.mingyuan.com.carmanager.http.httputil;

/**
 * Created by 莫绪旻 on 16/2/20.
 */
public class SDCardTool {
    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }
}
