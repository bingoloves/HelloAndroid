package cn.cqs.login;

import android.app.Application;

import cn.cqs.common.log.LogUtils;

/**
 * Created by bingo on 2021/2/3.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 独立运行的Application
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/3
 */
public class LoginApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("LoginApp");
    }
}
