package cn.cqs.login;

import android.app.Application;

import cn.cqs.aop.annotation.AppLifecycle;
import cn.cqs.aop.utils.IApplicationProxy;
import cn.cqs.common.log.LogUtils;

/**
 * Created by bingo on 2021/2/3.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 用于作为Module被依赖时，感知生命周期，一般是module中初始化第三方的相关服务
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/3
 */
@AppLifecycle(priority = 1)
public class LoginAppLifecycle implements IApplicationProxy{
    @Override
    public void onCreate(Application application) {
        LogUtils.e("LoginAppLifecycle");
    }
}
