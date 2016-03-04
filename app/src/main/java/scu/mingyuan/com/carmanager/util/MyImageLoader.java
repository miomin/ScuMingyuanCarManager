package scu.mingyuan.com.carmanager.util;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 描述：存放ImageLoader的加载器 创建日期：2015/5/1
 *
 * @author 莫绪旻
 */
public class MyImageLoader {

    private ImageLoader loader;
    private static MyImageLoader instance;

    private MyImageLoader() {
        loader = ImageLoader.getInstance();
    }

    public static MyImageLoader getInstance() {
        if (instance == null) {
            instance = new MyImageLoader();
        }
        return instance;
    }

    public ImageLoader getLoader() {
        return loader;
    }

    public void displayFromNet(String imageUrl, ImageView imageView) {
        loader.displayImage(imageUrl, imageView);
    }

    public void displayFromDrawable(int imageId, ImageView imageView) {
        loader.displayImage("drawable://" + imageId,
                imageView);
    }

    public void dispalyFromAssets(String imageName, ImageView imageView) {
        ImageLoader.getInstance().displayImage("assets://" + imageName,
                imageView);
    }

    /**
     * 从内容提提供者中抓取图片
     */
    public void displayFromContent(String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        ImageLoader.getInstance().displayImage("content://" + uri, imageView);
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public void displayFromSDCard(String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
    }
}
