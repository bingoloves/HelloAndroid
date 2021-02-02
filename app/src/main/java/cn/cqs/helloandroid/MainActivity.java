package cn.cqs.helloandroid;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.permissionx.guolindev.callback.RequestCallback;
import java.util.List;
import cn.cqs.aop.annotation.AspectAnalyze;
import cn.cqs.aop.annotation.SingleClick;
import cn.cqs.aop.aspect.ActivityAspect;
import cn.cqs.aop.navigation.Navigator;
import cn.cqs.base.DensityUtils;
import cn.cqs.common.log.LogUtils;
import cn.cqs.common.utils.PermissionUtils;
import cn.cqs.common.utils.ToastUtils;
import cn.cqs.common.view.floatview.FloatViewController;
import cn.cqs.workflow.Node;
import cn.cqs.workflow.WorkFlow;
import cn.cqs.workflow.WorkNode;
import cn.cqs.workflow.Worker;

public class MainActivity extends AppCompatActivity{

    private int i = 0;
    private TextView infoTv;
    @AspectAnalyze(name = "MainActivity.onCreate")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoTv = findViewById(R.id.tv_info);
        infoTv.setText("当前设备分辨率:"+ DensityUtils.getScreenWidth(this)+":"+DensityUtils.getScreenHeight(this));
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
//                    int level = i % 5;
//                    if (level == 0){
//                        Log.v("TAG1","i = "+ MainActivity.this.i);
//                    } else if (level == 1){
//                        Log.d("TAG2","i = "+ MainActivity.this.i);
//                    }else if (level == 2){
//                        Log.i("TAG3","i = "+ MainActivity.this.i);
//                    } else if (level == 3){
//                        Log.w("TAG4","i = "+ MainActivity.this.i);
//                    }else {
//                        Log.e("TAG5","i = "+ MainActivity.this.i);
//                    }
//                }
//            }
//        }).start();
//CrashHelper.init(true,0,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int activityStackSize = ActivityAspect.getActivityStackSize();
        Activity currentActivity = ActivityAspect.getCurrentActivity();
        LogUtils.e("activityStackSize = "+activityStackSize);
        if (currentActivity != null){
            LogUtils.e("currentActivity = "+currentActivity.getClass().getName());
        }
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
    @SingleClick(value = 1000)
    public void toastTest(View view){
        LogUtils.e("测试bug"+1/0);
            i++;
//          ToastUtils.show("哈哈哈 i = " + i);
//          ToastUtils.show(R.drawable.ic_check_24dp,"哈哈哈");
//        ToastUtils.show(R.drawable.ic_check_24dp,"哈哈哈,恭喜啦 你中奖啦,快来领奖啊按时不领作废处理,你一定要早点来兑换啊，不然就没机会了");
    }
    public void showFloat(View view){
        FloatViewController.getInstance().show(this);
    }
    public void hideFloat(View view){
        FloatViewController.getInstance().dismiss(this);
    }
    public void showWorkflow(final View view){
        WorkFlow workFlow = new WorkFlow.Builder()
                .withNode(WorkNode.build(1, new Worker() {
                    @Override
                    public void doWork(final Node node) {
                        ToastUtils.show("我是工作流1,两秒后执行下一工作流");
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                node.onCompleted();
                            }
                        },5000);
                    }
                }))
                .withNode(WorkNode.build(2, new Worker() {
                    @Override
                    public void doWork(final Node node) {
                        ToastUtils.show("我是工作流2,两秒后执行下一工作流");
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                node.onCompleted();
                            }
                        },2000);
                    }
                }))
                .withNode(WorkNode.build(3, new Worker() {
                    @Override
                    public void doWork(Node node) {
                        ToastUtils.show("我是工作流3");
                        node.onCompleted();
                    }
                }))
                .create();
        workFlow.start();
        workFlow.setCallBack(new WorkFlow.FlowCallBack() {
            @Override
            public void onNodeChanged(int nodeId) {
                LogUtils.e("nodeId : "+nodeId);
            }

            @Override
            public void onFlowFinish() {
                LogUtils.e("onFlowFinish");
            }
        });
    }
    public void navigateTwo(View view){
//        Intent intent = new Intent(this, TwoActivity.class);
//        intent.putExtra("name","xuebing");
//        startActivity(intent);
        Navigator.create(NavigationServer.class).moveTwo(this, "xuebing");
        finish();
    }
}
