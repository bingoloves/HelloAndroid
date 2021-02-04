package cn.cqs.helloandroid;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.taobao.sophix.SophixManager;

import cn.cqs.common.base.BaseApplication;
import cn.cqs.common.log.LogUtils;
import cn.cqs.common.view.floatview.FloatView;
import cn.cqs.common.view.floatview.FloatViewController;
import cn.cqs.logcat.LogcatDialog;

/**
 * Created by bingo on 2021/1/28.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/28
 */

public class App extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("App onCreate");
        //queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();

        //悬浮框debug模式配置
        FloatViewController.getInstance().init(false, this, R.drawable.ic_debug, new FloatView.OnFloatViewIconClickListener() {
            @Override
            public void onFloatViewClick(Context context) {
                if (context instanceof FragmentActivity){
                    //设置点击悬浮框事件，这里打开logcat
                    new LogcatDialog().show(((FragmentActivity)context).getSupportFragmentManager(),"logcat");
                }
            }
        });
    }
}
