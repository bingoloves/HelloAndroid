package cn.cqs.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.cqs.common.utils.ToastUtils;

/**
 * Created by bingo on 2021/2/3.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/3
 */
@Route(path = "/login/login")
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void toast(View view){
        //ToastUtils.show("假货就是");
        startActivity(new Intent(this,UserInfoActivity.class));
    }
}
