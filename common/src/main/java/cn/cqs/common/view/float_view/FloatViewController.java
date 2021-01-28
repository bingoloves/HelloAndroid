package cn.cqs.common.view.float_view;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:xishuang
 * Date:2017.08.01
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

    private WindowManager mWindowManager;
    private FloatView floatView;
    /**
     * 屏幕高宽
     */
    private int mScreenWidth, mScreenHeight;

    /**
     * 初始化悬浮组件 只初始化一次
     * @param view
     */
    public void initFloatLayout(FloatView view){
        this.floatView = view;
        Context context = floatView.getContext();
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 初始化组件
     * @param context
     * @param iconClickListener
     */
    public void initFloatLayout(Context context,FloatView.OnFloatViewIconClickListener iconClickListener){
        FloatView floatView = new FloatView(context);
        floatView.setOnFloatViewIconClickListener(iconClickListener);
        initFloatLayout(floatView);
    }
    /**
     * 用户缓存当前悬浮View
     */
    private List<View> floatViews = new ArrayList<>();
    /**
     * 悬浮球显示
     */
    public void show() {
        if (floatView == null) return;
        if (!floatViews.contains(floatView)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    WindowManager.LayoutParams floatLayoutParams = floatView.getFloatLayoutParams();
                    floatLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                    floatLayoutParams.x = 0;
                    floatLayoutParams.y = (int)(mScreenHeight * 0.4);
                    floatView.setFloatLayoutParams(floatLayoutParams);
                    mWindowManager = floatView.getFloatWindowManager();
                    mWindowManager.addView(floatView, floatLayoutParams);
                    floatViews.add(floatView);
                }
            },800);
        }
        if (floatView.getVisibility() == View.GONE){
            floatView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏
     */
    public void dismiss() {
        if (floatView == null) return;
        if (floatView.getVisibility() == View.VISIBLE){
            floatView.setVisibility(View.GONE);
        }
    }
    /**
     * 销毁 释放内存 防止内存泄漏
     */
    public void destroy() {
        if (floatView != null){
            boolean attachedToWindow = floatView.isAttachedToWindow();
            if (mWindowManager != null && attachedToWindow){
                mWindowManager.removeViewImmediate(floatView);
            }
            floatView = null;
        }
    }
}
