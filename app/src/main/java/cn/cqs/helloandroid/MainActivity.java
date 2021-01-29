package cn.cqs.helloandroid;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.permissionx.guolindev.callback.RequestCallback;
import java.util.List;
import cn.cqs.base.DensityUtils;
import cn.cqs.common.utils.PermissionUtils;
import cn.cqs.common.utils.ToastUtils;
import cn.cqs.common.view.floatview.FloatView;
import cn.cqs.common.view.floatview.FloatViewController;
import cn.cqs.logcat.LogcatDialog;

public class MainActivity extends AppCompatActivity implements FloatView.OnFloatViewIconClickListener {

    private int i = 0;
    private TextView infoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoTv = findViewById(R.id.tv_info);
        infoTv.setText("当前设备分辨率:"+ DensityUtils.getScreenW(this)+":"+DensityUtils.getScreenH(this));
        FloatViewController.getInstance().initFloatLayout(this,R.mipmap.ic_launcher,this);
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
        //CrashHelper.init(true,0,null);
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
//          ToastUtils.show("哈哈哈修复了bug");
          ToastUtils.show(R.drawable.ic_check_24dp,"哈哈哈");
//        ToastUtils.show(R.drawable.ic_check_24dp,"哈哈哈,恭喜啦 你中奖啦,快来领奖啊按时不领作废处理,你一定要早点来兑换啊，不然就没机会了");
    }
    public void showFloat(View view){
        //FloatViewController.getInstance().show();
        ToastUtils.show("哈哈哈");
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
