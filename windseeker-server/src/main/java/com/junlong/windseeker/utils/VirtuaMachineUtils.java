package com.junlong.windseeker.utils;

import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by niujunlong on 17/9/5.
 */
public class VirtuaMachineUtils {
    public static volatile Map<Integer, VirtualMachine> vmMap = new ConcurrentHashMap<Integer, VirtualMachine>();


    public static VirtualMachine loadAgent(int port) throws Exception{
//        VirtualMachine vm = null;
//        try {
//            vm = vmMap.get(port);
//            if (vm != null) {
//                return vm;
//            }
//            final String agentJarUrl = "/Users/didi/workspace/Windseeker/windseeker-agent/target/windseeker-agent-jar-with-dependencies.jar";
//            vm = VirtualMachine.attach(String.valueOf(port));
//            vm.loadAgent(agentJarUrl, "test");
//            vmMap.put(port, vm);
//
//        } catch (Exception e) {
//            if (vm != null) {
//                try {
//                    vm.detach();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//        return vm;

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Class<?> vmdClass = loader.loadClass("com.sun.tools.attach.VirtualMachineDescriptor");
        final Class<?> vmClass = loader.loadClass("com.sun.tools.attach.VirtualMachine");

        Object attachVmdObj = null;
        for (Object obj : (List<?>) vmClass.getMethod("list", (Class<?>[]) null).invoke(null, (Object[]) null)) {
            if ((vmdClass.getMethod("id", (Class<?>[]) null).invoke(obj, (Object[]) null))
                    .equals(Integer.toString(port))) {
                attachVmdObj = obj;
            }
        }

//        if (null == attachVmdObj) {
//            // throw new IllegalArgumentException("pid:" + configure.getJavaPid() + " not existed.");
//        }

        Object vmObj = null;
        try {
            if (null == attachVmdObj) { // 使用 attach(String pid) 这种方式
                vmObj = vmClass.getMethod("attach", String.class).invoke(null, "" + port);
            } else {
                vmObj = vmClass.getMethod("attach", vmdClass).invoke(null, attachVmdObj);
            }
            vmClass.getMethod("loadAgent", String.class, String.class).invoke(vmObj, configure.getGreysAgent(), configure.getGreysCore() + ";" + configure.toString());
        } finally {
            if (null != vmObj) {
                vmClass.getMethod("detach", (Class<?>[]) null).invoke(vmObj, (Object[]) null);
            }
            return null;
        }
        return null;

    }

    public static void close(int port){
        try {
            VirtualMachine vm = vmMap.get(port);
            if (vm != null) {
                return;
            }
            vm.detach();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
