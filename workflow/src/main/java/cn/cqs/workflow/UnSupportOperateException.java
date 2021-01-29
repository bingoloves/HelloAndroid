package cn.cqs.workflow;

public class UnSupportOperateException extends IllegalStateException {
    public UnSupportOperateException() {
        super("you can not operate a disposed workflow");
    }
}
