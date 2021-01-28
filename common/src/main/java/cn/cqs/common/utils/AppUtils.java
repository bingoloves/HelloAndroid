package cn.cqs.common.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2021/1/9 0009.
 */

public class AppUtils {

    private AppUtils utils;
    private Application mGlobalApplication;
    /**
     * 禁止外部创建 ServiceFactory 对象
     */
    private AppUtils() {
    }

    /**
     * 通过静态内部类方式实现 ServiceFactory 的单例
     */
    public static AppUtils getInstance() {
        return Inner.applicationUtils;
    }

    private static class Inner {
        private static AppUtils applicationUtils = new AppUtils();
    }

    /**
     * 可外部传入
     * @param application
     */
    public void init(Application application){
        this.mGlobalApplication = application;
    }

    /**
     * 获取当前的Application
     * @return
     */
    public Application getApplication(){
        if (mGlobalApplication == null){
            Application curApplication = getCurApplication();
            if (curApplication != null){
                mGlobalApplication = curApplication;
            }
        }
        return mGlobalApplication;
    }
    /**
     * 获取应用名称
     * @return
     */
    public String getAppName() {
        try {
            PackageManager packageManager = getApplication().getPackageManager();
            return String.valueOf(packageManager.getApplicationLabel(getApplication().getApplicationInfo()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return String.valueOf(packageManager.getApplicationLabel(context.getApplicationInfo()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取当前应用的Application
     * 先使用ActivityThread里获取Application的方法，如果没有获取到，
     * 再使用AppGlobals里面的获取Application的方法
     * @return
     */
    private static Application getCurApplication(){
        Application application = null;
        try{
            Class atClass = Class.forName("android.app.ActivityThread");
            Method currentApplicationMethod = atClass.getDeclaredMethod("currentApplication");
            currentApplicationMethod.setAccessible(true);
            application = (Application) currentApplicationMethod.invoke(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(application != null) return application;
        try{
            Class atClass = Class.forName("android.app.AppGlobals");
            Method currentApplicationMethod = atClass.getDeclaredMethod("getInitialApplication");
            currentApplicationMethod.setAccessible(true);
            application = (Application) currentApplicationMethod.invoke(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return application;
    }
}
