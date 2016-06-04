package com.bupt.ktc;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by ntop on 15/7/8.
 */
public class App extends Application {
    public static String myprofile_image_url = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        //  myprofile_image_url = "w";

    }

    private void initImageLoader() {
        //   File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), AppDisk.appName+"/image");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .threadPoolSize(1)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new WeakMemoryCache())
                        // .denyCacheImageMultipleSizesInMemory()
                        //      .diskCacheFileNameGenerator(CCPAppManager.md5FileNameGenerator)
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .diskCache(new UnlimitedDiskCache(cacheDir))
                        //      .diskCache(new UnlimitedDiskCache(cacheDir ,null ,CCPAppManager.md5FileNameGenerator))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        // .writeDebugLogs() // Remove for release app
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }

    public static DisplayImageOptions initImageOptions(int drawable, boolean refresh) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(drawable)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(drawable)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                        //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
                        //设置图片加入缓存前，对bitmap进行设置
                        //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(refresh)//设置图片在下载前是否重置，复位
//    	.displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少  
//    	.displayer(new FadeInBitmapDisplayer(refresh?100:0))//是否图片加载好后渐入的动画时间  

                .build();//构建完成
        return options;
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信    wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
//        PlatformConfig.setWeixin("wxea4284ec6dc7d22d", "20e4eff76c2885c0e871de735d50198e");
        PlatformConfig.setWeixin("wxb0497ec7ddd27559", "04b425d6ba9df16b9b5c52d3056f1e82");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3220628575", "63b302b25cfef8969af03bcf0396acd4");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("1105361497", "IigxZ5FkicPKOFJG");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
    }
}
