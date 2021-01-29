package cn.cqs.workflow;


/**
 * 工作节点
 */
public class WorkNode implements Node {

    /**
     * 节点id
     */
    private int nodeId;

    /**
     * 节点工作者
     */
    private Worker worker;

    private WorkCallBack callBack;

    /**
     * @param nodeId 工作流优先级 值越小优先级越高
     * @param worker
     * @return
     */
    public static WorkNode build(int nodeId, Worker worker) {
        return new WorkNode(nodeId, worker);
    }

    /**
     * @param nodeId 工作流优先级 值越小优先级越高
     * @param worker
     */
    public WorkNode(int nodeId, Worker worker) {
        this.nodeId = nodeId;
        this.worker = worker;
    }

    void doWork(WorkCallBack callBack) {
        this.callBack = callBack;
        worker.doWork(this);
    }

    void removeCallBack() {
        this.callBack = null;
    }

    @Override
    public int getId() {
        return nodeId;
    }

    @Override
    public void onCompleted() {
        if (null != callBack) {
            callBack.onWorkCompleted();
        }
    }

    @Override
    public String toString() {
        return "nodeId : " + getId();
    }

    interface WorkCallBack {

        /**
         * 当前任务完成
         */
        void onWorkCompleted();

    }
}
