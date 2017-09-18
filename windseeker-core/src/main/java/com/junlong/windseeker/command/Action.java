package com.junlong.windseeker.command;

import java.lang.instrument.Instrumentation;

/**
 * 执行动作
 * Created by niujunlong on 17/9/15.
 */
public interface Action {

    /**
     * 需要类增强的动作
     * 注意:需要改动类的字节码
     * TODO: 注意关闭监控后,原方法字节码文件是否还原
     */
    interface EnHancerAction extends Action {
        void doAction(Instrumentation instrumentation);
    }

    interface SlientAction extends Action {
        void doAction(Instrumentation instrumentation);
    }
}
