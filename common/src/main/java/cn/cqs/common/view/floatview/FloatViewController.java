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
     * 添加悬浮组件
     * @param activity
     * @param floatIcon 悬浮Icon
     * @param iconClickListener
     */
    public void addFloatLayout(Activity activity, @DrawableRes int floatIcon,FloatView.OnFloatViewIconClickListener iconClickListener){
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
    private Map<Activity,FloatView> mFloatViewsMap = new HashMap<>();
    /**
     * 悬浮球显示
     */
    public void show(Activity activity) {
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
