package cn.cqs.common.view.floatview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Des:与悬浮窗交互的控制类，真正的实现逻辑不在这
 */
public class FloatViewController {

    private FloatViewController() {
    }

    public static FloatViewController getInstance() {
        return LittleMonkProviderHolder.sInstance;
    }

    private static class LittleMonkProviderHolder {
        private static final FloatViewController sInstance = new FloatViewController();
    }

    /**
     * 初始化
     * @param debug       是否需要悬浮debug组件
     * @param application 对Activity生命周期拦截处理相关的逻辑
     * @param icon         图标
     * @param listener     点击图标时间
     */
    public void init(final boolean debug, Application application, @DrawableRes final int icon, final FloatView.OnFloatViewIconClickListener listener){
        if (debug){
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
                    //排除crash库崩溃页面，不用显示Logcat
                    if (activity instanceof FragmentActivity && !"cn.cqs.crash.DefaultErrorActivity".equals(activity.getClass().getCanonicalName())){
                        FloatViewController.getInstance().addFloatLayout((FragmentActivity) activity, icon, listener);
                        if (activity.hasWindowFocus()){
                            FloatViewController.getInstance().show((FragmentActivity) activity);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    FloatViewController.getInstance().show((FragmentActivity) activity);
                                }
                            },800);
                        }
                    }
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (debug && activity instanceof FragmentActivity){
                        FloatViewController.getInstance().destroy((FragmentActivity) activity);
                    }
                }
            });
        }
    }
    /**
     * 添加悬浮组件
     * @param activity
     * @param floatIcon 悬浮Icon
     * @param iconClickListener
     */
    public void addFloatLayout(FragmentActivity activity, @DrawableRes int floatIcon,FloatView.OnFloatViewIconClickListener iconClickListener){
        FloatView floatView = new FloatView(activity,floatIcon);
        floatView.setOnFloatViewIconClickListener(iconClickListener);
        WindowManager.LayoutParams floatLayoutParams = floatView.getFloatLayoutParams();
        floatLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
//        int floatLayoutParamsX = ACache.get(floatView.getContext()).getAsInt("floatLayoutParamsX");
//        int floatLayoutParamsY = ACache.get(floatView.getContext()).getAsInt("floatLayoutParamsY");
//        LogUtils.e(floatLayoutParamsX+"---3---"+floatLayoutParamsY);
//        floatLayoutParams.x = floatLayoutParamsX == -1 ? 0 : floatLayoutParamsX;
//        floatLayoutParams.y = floatLayoutParamsY == -1 ? (int)(floatView.mScreenHeight * 0.4) :  ;
        floatLayoutParams.x = 0;
        floatLayoutParams.y = (int)(floatView.mScreenHeight * 0.4);
        floatView.setFloatLayoutParams(floatLayoutParams);
        mFloatViewsMap.put(activity,floatView);
    }

    /**
     * 全局缓存表
     */
    private Map<FragmentActivity,FloatView> mFloatViewsMap = new HashMap<>();
    /**
     * 悬浮球显示
     */
    public void show(FragmentActivity activity) {
        if (mFloatViewsMap.containsKey(activity)){
            FloatView floatView = mFloatViewsMap.get(activity);
            if (!floatView.isAttachedToWindow()){
                floatView.getFloatWindowManager().addView(floatView, floatView.getFloatLayoutParams());
            }
            if (floatView.getVisibility() == View.GONE){
                floatView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * TODO 更新位置
     * @param activity
     */
//    public void updateFloatPosition(FragmentActivity activity){
//        if (mFloatViewsMap.containsKey(activity)){
//            FloatView floatView = mFloatViewsMap.get(activity);
//            if (floatView != null && floatView.isAttachedToWindow()){
//                WindowManager.LayoutParams floatLayoutParams = floatView.getFloatLayoutParams();
//                int floatLayoutParamsX = ACache.get(floatView.getContext()).getAsInt("floatLayoutParamsX");
//                int floatLayoutParamsY = ACache.get(floatView.getContext()).getAsInt("floatLayoutParamsY");
//                LogUtils.e(floatLayoutParamsX+"---3---"+floatLayoutParamsY);
//                floatLayoutParams.x = floatLayoutParamsX == -1 ? 0 : floatLayoutParamsX;
//                floatLayoutParams.y = floatLayoutParamsY == -1 ? (int)(floatView.mScreenHeight * 0.4) : floatLayoutParamsY;
//                floatView.setFloatLayoutParams(floatLayoutParams);
//                floatView.mWindowManager.updateViewLayout(floatView, floatLayoutParams);
//            }
//        }
//    }
    /**
     * 隐藏
     */
    public void dismiss(FragmentActivity activity) {
        if (mFloatViewsMap.containsKey(activity)){
            FloatView floatView = mFloatViewsMap.get(activity);
            if (floatView.getVisibility() == View.VISIBLE){
                floatView.setVisibility(View.GONE);
            }
        }
    }
    /**
     * 销毁 释放内存 防止内存泄漏
     */
    public void destroy(FragmentActivity activity) {
        if (mFloatViewsMap.containsKey(activity)){
            FloatView floatView = mFloatViewsMap.get(activity);
            if (floatView != null && floatView.isAttachedToWindow()){
                floatView.getFloatWindowManager().removeViewImmediate(floatView);
                mFloatViewsMap.remove(activity);
            }
        }
    }
}
