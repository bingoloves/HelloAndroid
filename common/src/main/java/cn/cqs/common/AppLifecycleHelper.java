package cn.cqs.common;

import android.app.Application;

import cn.cqs.aop.utils.AppLifecycleProcessor;

/**
 * Created by bingo on 2021/2/3.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 管理全局的相关module 生命周期的表
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/3
 */

public class AppLifecycleHelper {
    private static final String[] modulePackage = {
           "cn.cqs.helloandroid",//主模块
           "cn.cqs.login",//登录模块
    };

    /**
     * 初始化当前需要的模块生命感知
     * @param application
     */
    public static void init(Application application){
        AppLifecycleProcessor.init(application,modulePackage);
    }
}
