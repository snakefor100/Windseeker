package com.junlong.windseeker.utils;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.IOException;

/**
 * Created by niujunlong on 17/9/5.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        VirtualMachine vm = null;

        final String agentJarUrl = "/Users/didi/workspace/Windseeker/windseeker-agent/target/windseeker-agent-jar-with-dependencies.jar";

        try {
            File f = new File("/Users/didi/workspace/Azzinoth/azzinoth-agent/build/libs/azzinoth-agent.jar");

            vm = VirtualMachine.attach("6264");
            System.out.println(vm.list());
//            vm.loadAgent("F:\\\\workspace\\github\\Azzinoth\\azzinoth-agent\\build\\libs\\azzinoth-agent.jar","ff");
            vm.loadAgent(agentJarUrl, "toagent");
            System.out.println(11);
        } catch (Exception e) {
            e.printStackTrace();
//            vm.detach();
        }
    }
}
