package cn.cqs.helloandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.permissionx.guolindev.callback.RequestCallback;
import com.squareup.haha.perflib.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.cqs.adapter.recyclerview.CommonAdapter;
import cn.cqs.adapter.recyclerview.MultiItemTypeAdapter;
import cn.cqs.adapter.recyclerview.base.ViewHolder;
import cn.cqs.aop.annotation.SingleClick;
import cn.cqs.aop.navigation.Navigator;
import cn.cqs.base.DensityUtils;
import cn.cqs.common.bean.SimpleItem;
import cn.cqs.common.log.LogUtils;
import cn.cqs.common.utils.PermissionUtils;
import cn.cqs.common.view.floatview.FloatView;
import cn.cqs.common.view.floatview.FloatViewController;
import cn.cqs.dialog.DialogUtils;
import cn.cqs.http.BaseObserver;
import cn.cqs.http.BaseResponse;
import cn.cqs.http.RetrofitUtil;
import cn.cqs.language.MultiLanguages;
import cn.cqs.logcat.LogcatDialog;
import cn.cqs.toast.ToastUtils;
import cn.cqs.workflow.Node;
import cn.cqs.workflow.WorkFlow;
import cn.cqs.workflow.WorkNode;
import cn.cqs.workflow.Worker;
import cn.cqs.xlogcat.LogcatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity{

    private int i = 0;
    private RecyclerView mRecyclerView;
    private CommonAdapter<SimpleItem> adapter;
    private List<SimpleItem> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<SimpleItem>(this,R.layout.layout_simple_item,list) {
            @Override
            protected void convert(ViewHolder holder, SimpleItem simpleItem, int position) {
                holder.setText(R.id.tv_name,simpleItem.name);
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                SimpleItem simpleItem = list.get(position);
                if (simpleItem.clazz != null){
                    Intent intent = new Intent(MainActivity.this,simpleItem.clazz);
                    startActivity(intent);
                } else if (!TextUtils.isEmpty(simpleItem.routePath)){
                    ARouter.getInstance().build(simpleItem.routePath).navigation(MainActivity.this);
                } else if (simpleItem.onClickListener != null){
                    simpleItem.onClickListener.onClick(view);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        //悬浮框配置
        FloatViewController.getInstance().addFloatLayout(MainActivity.this, R.drawable.ic_debug, new FloatView.OnFloatViewIconClickListener() {
            @Override
            public void onFloatViewClick(View view) {
                //设置点击悬浮框事件，这里打开logcat
                new LogcatDialog().show(getSupportFragmentManager(),"logcat");
            }
        });
        TextView infoTv = findViewById(R.id.tv_info);
        infoTv.setText("当前设备分辨率:"+ DensityUtils.getScreenWidth(this)+":"+DensityUtils.getScreenHeight(this));
        ((TextView) findViewById(R.id.tv_language_info)).setText("当前系统语言："+MultiLanguages.getLanguageString(this, MultiLanguages.getSystemLanguage(),R.string.i18n_1007));
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        // 国际化适配（绑定语种）
        super.attachBaseContext(MultiLanguages.attach(newBase));
    }
    private void initData() {
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1000), "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.request(MainActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        ToastUtils.show(allGranted?"已通过":"未通过");
                    }
                });
            }
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1001), "toast库中的Toast工具,可以自定义类型", new View.OnClickListener() {
            @SingleClick(value = 1000)
            @Override
            public void onClick(View v) {
                i++;
                ToastUtils.show("哈哈哈 i = " + i);
            }
        }));
        list.add(new SimpleItem("toast", "DialogUtils中的Toast，这种不好看，哈哈", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showToast(MainActivity.this,"测试Toast");
            }
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1002), "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatViewController.getInstance().show(MainActivity.this);
            }
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1002) + " dismiss", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatViewController.getInstance().dismiss(MainActivity.this);
            }
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1003), "WorkFlow 工作流", new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
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
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1004), "Navigator基于Retrofit动态代理模式的封装", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.create(NavigationServer.class).moveTwo(MainActivity.this, "xuebing");
            }
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1008), "基于Retrofit2+RxJava", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("id:"+Thread.currentThread().getId()+";线程名:"+Thread.currentThread().getName());
                RetrofitUtil.getService(ApiService.class).testApi("top")
                        .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<BaseResponse>(MainActivity.this,true) {

                            @Override
                            public void onSuccess(BaseResponse baseResponse) {
                                LogUtils.e("onSuccess");
                            }

                            @Override
                            public void onFailure(String s) {
                                LogUtils.e("onFailure");
                                ToastUtils.show(s);
                            }
                        });
            }
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1005), "ARouter路由跳转", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/login/login")
                        .navigation(MainActivity.this);//需要跳转动画需要配置Context
            }
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1006), "ARouter路由跳转", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showAlertDialog("提示", "你好啊", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showToast(MainActivity.this,"哈哈哈");
                        DialogUtils.showDrawDialog(per.goweii.anylayer.DragLayout.DragStyle.Top,R.layout.dialog_drag,null);
                    }
                });
            }
        }));
        list.add(new SimpleItem(getResources().getString(R.string.i18n_1009), "切换中英文", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switchLanguage();
            }
        }));
        list.add(new SimpleItem("xlogcat", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogcatActivity.class));
            }
        }));
    }

    /**
     * 切换语言(中英文切换)
     */
    private void switchLanguage() {
        Locale locale = MultiLanguages.getAppLanguage(MainActivity.this);
        boolean isEnglish;
        if (MultiLanguages.equalsCountry(locale, Locale.CHINA)) {
            isEnglish = false;
        } else if (MultiLanguages.equalsLanguage(locale, Locale.ENGLISH)) {
            isEnglish = true;
        } else {
            isEnglish = false;
        }
        boolean restart;
        if (isEnglish){
            restart = MultiLanguages.setAppLanguage(this, Locale.CHINA);
        } else {
            restart = MultiLanguages.setAppLanguage(this, Locale.ENGLISH);
        }
        // 是否需要重启
        if (restart){
            // 1.使用recreate来重启Activity的效果差，有闪屏的缺陷
            // recreate();
            // 2.我们可以充分运用 Activity 跳转动画，在跳转的时候设置一个渐变的效果，相比前面的这种带来的体验是最佳的
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }
}
