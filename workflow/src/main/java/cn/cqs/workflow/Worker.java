package cn.cqs.workflow;

public interface Worker {

    /**
     * 执行任务
     *
     * @param node 当前节点
     */
    void doWork(Node node);

}