package com.junlong.windseeker.command;

/**
 * 命令
 * Created by niujunlong on 17/9/15.
 */
public interface Command {
    /**
     * 获取当前命令的执行动作
     * @return
     */
    Action getAction();
}
