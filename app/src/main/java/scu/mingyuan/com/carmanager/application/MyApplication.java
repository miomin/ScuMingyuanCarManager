package scu.mingyuan.com.carmanager.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.baidu.mapapi.SDKInitializer;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;

/**
 * Created by 莫绪旻 on 16/2/29.
 */
public class MyApplication extends Application {

    private static Context context;
    public static MyApplication mInstance;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        context = getApplicationContext();
        // 初始化Imageloader
        initImageLoader(this);
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "57ef776fe1958b4bf7175041a5005202");

        //初始化BaiduMapSDK
        SDKInitializer.initialize(this);

        mInstance = this;
    }

    // 获取全局的context
    public static Context getContext() {
        return context;
    }


    public static MyApplication getInstance() {
        return mInstance;
    }


    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                        // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                        // You can pass your own memory cache
                        // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                        // 缓存的文件数量
                        // .discCache(
                        // new UnlimitedDiscCache(new File(Environment
                        // .getExternalStorageDirectory()
                        // + "/myApp/imgCache")))
                        // 自定义缓存路径
                .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                // .showImageForEmptyUri(R.drawable.defaul_head)// 设置图片Uri为空或是错误的时候显示的图片
                // .showImageOnFail(R.drawable.defaul_head) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                        // .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
                        // 设置图片以如何的编码方式显示
                        // .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        // .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                        // .displayer(new RoundedBitmapDisplayer(10))// 是否设置为圆角，弧度为多少
                        // .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();// 构建完成
        return options;
    }
}
