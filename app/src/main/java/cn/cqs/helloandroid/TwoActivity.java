package cn.cqs.helloandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cn.cqs.aop.annotation.Autowired;
import cn.cqs.aop.aspect.ActivityAspect;
import cn.cqs.aop.utils.Injector;
import cn.cqs.common.log.LogUtils;
import cn.cqs.common.view.floatview.FloatViewController;

public class TwoActivity extends AppCompatActivity{
    @Autowired
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Injector.inject(this);
        LogUtils.e(name);
//        ActivityAspect.setExitAnimation(new int[]{R.anim.fade_in,R.anim.fade_out });
    }
    public void showFloat(View view){
        FloatViewController.getInstance().show(this);
    }
    public void hideFloat(View view){
        FloatViewController.getInstance().dismiss(this);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
