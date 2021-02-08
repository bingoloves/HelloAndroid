package cn.cqs.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.cqs.toast.ToastUtils;

/**
 * Created by bingo on 2021/2/3.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 用户信息页面
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/3
 */
@Route(path = "/login/userInfo")
public class UserInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }
    public void toast(View view){
        ToastUtils.show("假货就是");
    }
}
