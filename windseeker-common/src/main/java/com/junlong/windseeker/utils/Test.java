package com.junlong.windseeker.utils;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by niujunlong on 17/9/5.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        System.out.println(11);
        System.out.println(22);
        System.out.println(33);
    }
}
