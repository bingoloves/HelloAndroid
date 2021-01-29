package cn.cqs.workflow;

public class UndefinedNodeException extends IllegalStateException {
    public UndefinedNodeException(int nodeId) {
        super("the id :" + nodeId + " did not found when work flow executed \n check your work flow build");
    }
}
