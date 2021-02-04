package cn.cqs.login;

import cn.cqs.common.base.BaseApplication;
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
public class LoginApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("LoginApp");
    }
}
