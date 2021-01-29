package cn.cqs.crash;

import android.app.Activity;
import android.support.annotation.DrawableRes;

/**
 * Created by bingo on 2021/1/29.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 配置全局异常崩溃操作
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/29
 */

public class CrashHelper {
    /**
     * 初始化
     * @param enabled   是否开启全局异常捕获 默认是开启的，就是可以不调用此方法
     * @param errorIcon 默认异常捕获页面显示的图标，一般是应用图标
     * @param restartActivity  需要配置的重启页面,可为空，为空则为重启App
     */
    public static void init(boolean enabled, @DrawableRes int errorIcon, Class<? extends Activity> restartActivity){
        CrashConfig.Builder.create()
                .backgroundMode(CrashConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(enabled)          //是否启动全局异常捕获
                .showErrorDetails(enabled) //是否显示错误详细信息
                .showRestartButton(enabled)//是否显示重启按钮
                .trackActivities(enabled)  //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(errorIcon)         //错误图标
                .restartActivity(restartActivity) //重新启动后的activity
                //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
                //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }
}
