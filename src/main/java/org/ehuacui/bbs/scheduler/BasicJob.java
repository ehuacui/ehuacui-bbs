package org.ehuacui.bbs.scheduler;

/**
 * 定时任务抽象
 *
 * @author jianwei.zhou on 2016/2/19.
 */
public abstract class BasicJob {
    /**
     * 任务执行方法
     *
     * @throws RuntimeException 拋出异常
     */
    public abstract void execute() throws RuntimeException;

}
