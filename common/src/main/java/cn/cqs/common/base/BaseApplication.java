package cn.cqs.common.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;

import cn.cqs.aop.aspect.ActivityAspect;
import cn.cqs.common.AppLifecycleHelper;
import cn.cqs.common.R;
import cn.cqs.common.utils.ToastUtils;
import cn.cqs.common.utils.TokenInterceptor;
import cn.cqs.http.HttpConfig;
import cn.cqs.http.ResponseListener;
import cn.cqs.http.ResponseResult;

/**
 * Created by bingo on 2021/2/4.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/4
 */
public class BaseApplication extends Application {

    private static final boolean DEBUG = true;
    @Override
    public void onCreate() {
        super.onCreate();
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
        if (DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();    // 打印日志
            ARouter.openDebug();  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        //全局页面跳转动画
        ActivityAspect.setDefaultAnimation(new int[]{R.anim.slide_in_from_right,R.anim.slide_out_from_left },new int[]{R.anim.slide_in_from_left,R.anim.slide_out_from_right});
    }
}
