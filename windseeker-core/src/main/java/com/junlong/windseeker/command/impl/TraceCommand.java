package com.junlong.windseeker.command.impl;

import com.junlong.windseeker.command.Action;
import com.junlong.windseeker.command.Command;
import com.junlong.windseeker.enhancer.ClassEnhancer;
import com.junlong.windseeker.server.session.DefaultSessionManager;
import com.junlong.windseeker.utils.WsClassUtils;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;

/**
 * Created by niujunlong on 17/10/16.
 */
public class TraceCommand implements Command {
    @Override
    public Action getAction() {
        return new Action() {
            @Override
            public void doAction(DefaultSessionManager.Session session) {
                System.out.println(111);
                Instrumentation inst = session.getInst();
                inst.addTransformer(new ClassEnhancer(),true);
                System.out.println(222);
                List<Class> classList = WsClassUtils.matchClass(session.getInst().getAllLoadedClasses(), "TestImpl");

                Class aClass = classList.get(0);
                System.out.println("获取到的class"+aClass);
                try {
                    inst.retransformClasses(aClass);
                } catch (UnmodifiableClassException e) {
                    e.printStackTrace();
                }
//                List<Class> classList = WsClassUtils.matchClass(session.getInst().getAllLoadedClasses(), "TestImpl");
//                Class[] arr = new Class[]{classList.get(0)};
//                System.out.println("获取到的class"+classList);
//                try {
//                    session.getInst().retransformClasses(arr);
//                } catch (UnmodifiableClassException e) {
//                    System.out.println("出错啦."+e.getMessage());
//                }
            }
        };
    }
}
