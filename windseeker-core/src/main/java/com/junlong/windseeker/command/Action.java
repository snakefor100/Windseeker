package com.junlong.windseeker.command;

import com.junlong.windseeker.server.session.DefaultSessionManager;

/**
 * 执行动作
 * Created by niujunlong on 17/9/15.
 */
public interface Action {


    void doAction(DefaultSessionManager.Session session);

}
