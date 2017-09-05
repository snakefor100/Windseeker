package com.junlong.windseeker.service;

import java.lang.instrument.Instrumentation;

/**
 * Created by niujunlong on 17/9/6.
 */
public class VmServer {
    private static volatile VmServer vmServer;

    public static VmServer getInstance(final int javaPid, final Instrumentation instrumentation){
        if(null == vmServer){
            synchronized (VmServer.class){
                if(null == vmServer){
                    vmServer = new VmServer();
                }
            }
        }
        System.out.println(instrumentation);
        return vmServer;
    }
}
