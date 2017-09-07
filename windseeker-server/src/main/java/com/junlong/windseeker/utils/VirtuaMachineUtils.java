package com.junlong.windseeker.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junlong.windseeker.domain.Configure;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by niujunlong on 17/9/5.
 */
public class VirtuaMachineUtils {
    public static volatile Map<Integer, VirtualMachine> vmMap = new ConcurrentHashMap<Integer, VirtualMachine>();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void loadAgent(Configure configure) throws IOException {

        VirtualMachine attach = null;
        VirtualMachineDescriptor vm = null;
        try {
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

            attach.loadAgent(configure.getAgentJarUrl(), objectMapper.writeValueAsString(configure));
        } catch (Exception e) {
            attach.detach();
        }

    }

    public static void close(int port) {
        try {
            VirtualMachine vm = vmMap.get(port);
            if (vm != null) {
                return;
            }
            vm.detach();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
