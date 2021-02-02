# workflow
工作流工具
#### 使用
```gradle
  //添加依赖
  implementation 'com.github.bingoloves:workflow:1.0.0'
```
```java
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
```