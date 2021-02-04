package cn.cqs.helloandroid;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.sophix.SophixManager;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import cn.cqs.aop.annotation.AppLifecycle;
import cn.cqs.aop.utils.AppLifecycleProcessor;
import cn.cqs.common.AppLifecycleHelper;
import cn.cqs.common.log.LogUtils;
import cn.cqs.common.utils.ToastUtils;
import cn.cqs.common.view.floatview.FloatView;
import cn.cqs.common.view.floatview.FloatViewController;
import cn.cqs.http.HttpConfig;
import cn.cqs.http.ResponseListener;
import cn.cqs.http.ResponseResult;
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

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("App onCreate");
        //queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();

        //悬浮框debug模式配置
        FloatViewController.getInstance().init(BuildConfig.DEBUG, this, R.drawable.ic_debug, new FloatView.OnFloatViewIconClickListener() {
            @Override
            public void onFloatViewClick(Context context) {
                if (context instanceof FragmentActivity){
                    //设置点击悬浮框事件，这里打开logcat
                    new LogcatDialog().show(((FragmentActivity)context).getSupportFragmentManager(),"logcat");
                }
            }
        });
        //网络请求框架
        HttpConfig.getHttpConfig().setHost("http://www.baidu.com");
        HttpConfig.getHttpConfig().addInterceptor(new TokenInterceptor());
        HttpConfig.getHttpConfig().setResponseHandler(new ResponseListener() {
            @Override
            public ResponseResult handle(Object o) {
//                return new ResponseResult(ResponseResult.TYPE_PASS,0,null);//这种是不拦截直接通行
//                return new ResponseResult(ResponseResult.TYPE_ERROR,0,"测试拦截处理");//这种是给出一些统一处理
                return new ResponseResult(ResponseResult.TYPE_INTERCEPT,100,null);//这种是统一拦截
            }

            @Override
            public void intercept(int code) {
                if (code == 100){
                    ToastUtils.show("拦截到数据异常情况");
//                    Intent intent = new Intent(App.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    App.this.startActivity(intent);
                }
            }
        });
        //检测内存泄漏
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        AppLifecycleHelper.init(this);
        // 初始化 ARouter
        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();    // 打印日志
            ARouter.openDebug();  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }
}
