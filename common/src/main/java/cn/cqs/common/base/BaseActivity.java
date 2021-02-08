package cn.cqs.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;
    protected WeakReference<Activity> activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = new WeakReference<Activity>(this);
        ARouter.getInstance().inject(this);
    }

    protected void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 初始化沉浸式状态
     */
    protected void initImmersionbar() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        unbinder = ButterKnife.bind(this);
        initImmersionbar();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
        initImmersionbar();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
