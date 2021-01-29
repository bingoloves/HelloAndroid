package cn.cqs.helloandroid;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.taobao.sophix.SophixManager;

import cn.cqs.common.log.LogUtils;

/**
 * Created by bingo on 2021/1/28.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/28
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("App onCreate");
        //queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
        //检测内存泄漏
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
