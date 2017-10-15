package com.junlong.windseeker.command.session;

import com.junlong.windseeker.command.Action;
import com.junlong.windseeker.command.Command;
import com.junlong.windseeker.server.session.DefaultSessionManager;

/**
 * Created by niujunlong on 17/9/29.
 */
public class SessionCommand implements Command {
    @Override
    public Action getAction() {
        return new Action() {
            @Override
            public void doAction(DefaultSessionManager.Session session) {

            }
        };
    }
}
