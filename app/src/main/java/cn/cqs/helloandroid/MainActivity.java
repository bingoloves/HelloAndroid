package cn.cqs.helloandroid;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.List;

import cn.cqs.base.DensityUtils;
import cn.cqs.base.ToastUtils;
import cn.cqs.common.dialog.LogcatDialog;
import cn.cqs.common.log.LogUtils;
import cn.cqs.common.utils.PermissionUtils;
import cn.cqs.common.view.float_view.FloatView;
import cn.cqs.common.view.float_view.FloatViewController;

public class MainActivity extends AppCompatActivity implements FloatView.OnFloatViewIconClickListener {

    private int i = 0;
    private TextView infoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoTv = findViewById(R.id.tv_info);
        infoTv.setText("当前设备分辨率:"+ DensityUtils.getScreenW(this)+":"+DensityUtils.getScreenH(this));
        FloatViewController.getInstance().initFloatLayout(this,this);
        FloatViewController.getInstance().show();
        initPermissionX();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    i++;
//                    Log.e("TAG","i = "+i);
//                }
//            }
//        }).start();
    }

    private void initPermissionX() {
        PermissionUtils.request(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
//                ToastUtils.show(MainActivity.this,allGranted?"已通过":"未通过");
//                cn.cqs.common.utils.ToastUtils.show(allGranted?"已通过":"未通过");
            }
        });
    }


    public void toastTest(View view){
//        LogUtils.e("测试bug"+1/0);
        ToastUtils.show(this,"哈哈哈修复了bug");
//        cn.cqs.common.utils.ToastUtils.show("哈哈哈");
//        cn.cqs.common.utils.ToastUtils.show(R.drawable.ic_check_24dp,"哈哈哈");
//        cn.cqs.common.utils.ToastUtils.show(R.drawable.ic_check_24dp,"哈哈哈,恭喜啦 你中奖啦,快来领奖啊按时不领作废处理,你一定要早点来兑换啊，不然就没机会了");
    }
    public void showFloat(View view){
        //FloatViewController.getInstance().show();
        cn.cqs.common.utils.ToastUtils.show("哈哈哈");
    }
    public void hideFloat(View view){
        FloatViewController.getInstance().dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FloatViewController.getInstance().destroy();
    }

    @Override
    public void onFloatViewClick() {
        new LogcatDialog().show(getSupportFragmentManager(),"logcat");
    }
}
