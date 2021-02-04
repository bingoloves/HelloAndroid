package cn.cqs.helloandroid;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.permissionx.guolindev.callback.RequestCallback;
import java.util.ArrayList;
import java.util.List;
import cn.cqs.adapter.recyclerview.CommonAdapter;
import cn.cqs.adapter.recyclerview.MultiItemTypeAdapter;
import cn.cqs.adapter.recyclerview.base.ViewHolder;
import cn.cqs.aop.annotation.SingleClick;
import cn.cqs.aop.navigation.Navigator;
import cn.cqs.base.DensityUtils;
import cn.cqs.common.bean.SimpleItem;
import cn.cqs.common.log.LogUtils;
import cn.cqs.common.utils.PermissionUtils;
import cn.cqs.common.utils.ToastUtils;
import cn.cqs.common.view.floatview.FloatViewController;
import cn.cqs.dialog.DialogUtils;
import cn.cqs.http.BaseObserver;
import cn.cqs.http.BaseResponse;
import cn.cqs.http.RetrofitUtil;
import cn.cqs.workflow.Node;
import cn.cqs.workflow.WorkFlow;
import cn.cqs.workflow.WorkNode;
import cn.cqs.workflow.Worker;
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
        TextView infoTv = findViewById(R.id.tv_info);
        infoTv.setText("当前设备分辨率:"+ DensityUtils.getScreenWidth(this)+":"+DensityUtils.getScreenHeight(this));
    }

    private void initData() {
        list.add(new SimpleItem("权限申请", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.request(MainActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        ToastUtils.show(MainActivity.this,allGranted?"已通过":"未通过");
                    }
                });
            }
        }));
        list.add(new SimpleItem("ToastUtils", "Common库中的Toast工具", new View.OnClickListener() {
            @SingleClick(value = 1000)
            @Override
            public void onClick(View v) {
                i++;
                ToastUtils.show("哈哈哈 i = " + i);
//                ToastUtils.show(R.drawable.ic_check_24dp,"哈哈哈");
            }
        }));
        list.add(new SimpleItem("toast", "DialogUtils中的Toast", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showToast(MainActivity.this,"测试Toast");
            }
        }));
        list.add(new SimpleItem("FloatView show", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatViewController.getInstance().show(MainActivity.this);
            }
        }));
        list.add(new SimpleItem("FloatView dismiss", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatViewController.getInstance().dismiss(MainActivity.this);
            }
        }));
        list.add(new SimpleItem("WorkFlow", "WorkFlow 工作流", new View.OnClickListener() {
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
        list.add(new SimpleItem("Navigator 跳转", "Navigator基于Retrofit动态代理模式的封装", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.create(NavigationServer.class).moveTwo(MainActivity.this, "xuebing");
            }
        }));
        list.add(new SimpleItem("Http网络库", "基于Retrofit2+RxJava", new View.OnClickListener() {
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
        list.add(new SimpleItem("ARouter 跳转", "ARouter路由跳转", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/login/login")
                        .navigation(MainActivity.this);//需要跳转动画需要配置Context
            }
        }));
        list.add(new SimpleItem("DialogUtils", "ARouter路由跳转", new View.OnClickListener() {
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
    }
}
