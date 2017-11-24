package com.junlong.windseeker.utils;

import com.junlong.windseeker.domain.Configure;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by niujunlong on 17/9/5.
 */
public class VirtuaMachineUtils {
    private static final Logger LOG = LoggerFactory.getLogger(VirtuaMachineUtils.class);


    public static volatile Map<Integer, VirtualMachine> vmMap = new ConcurrentHashMap<Integer, VirtualMachine>();


    public static void loadAgent(Configure configure) throws IOException {

        VirtualMachine attach = null;
        VirtualMachineDescriptor vm = null;
        try {
            if (vmMap.get(configure.getTargetPort()) != null) {
                return;
            }

            List<VirtualMachineDescriptor> virtualMachineDescriptorList = VirtualMachine.list();
            for (VirtualMachineDescriptor obj : virtualMachineDescriptorList) {
                if (obj.id().equals(String.valueOf(configure.getJavaPid()))) {
                    vm = obj;
                }
            }

            if (vm == null) {
                attach = VirtualMachine.attach(String.valueOf(configure.getJavaPid()));

            } else {
                attach = VirtualMachine.attach(vm);
            }
            LOG.info("传递agent参数", JsonUtils.toString(configure));
            vmMap.put(configure.getTargetPort(), attach);
            attach.loadAgent(configure.getAgentJarUrl(), JsonUtils.toString(configure));

        } catch (Exception e) {
            attach.detach();
            LOG.error("VM load agent 出现异常", e);
        }

    }

    public static void close(int port) {
        try {
            VirtualMachine vm = vmMap.get(port);
            if (vm == null) {
                return;
            }
            vm.detach();
        } catch (Exception e) {
            LOG.error("VM close agent 出现异常", e);
        }
    }
}
