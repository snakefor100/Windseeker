package com.junlong.windseeker.enhancer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by niujunlong on 17/10/16.
 */
public class TraceClassVisitor extends ClassVisitor implements Opcodes {
    private String className;

    public TraceClassVisitor(ClassVisitor cv, String className) {
        super(ASM5, cv);
        this.className = className;
    }

    /**
     * 扫描到类的方法时候会调用
     *
     * @param access     修饰符
     * @param name       方法名 构造方法:<init> 静态代码块: <clinit>
     * @param desc       方法签名
     * @param signature  范型签名
     * @param exceptions 抛出的异常
     */
    @Override
    public MethodVisitor visitMethod(int access, final String name, final String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        System.out.println(name + "-" + desc + "-" + className);
        final String key = className + name + desc;
        if (!name.equals("<init>") && methodVisitor != null) {
            System.out.println("开始转换方法"+key);
            methodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {
                @Override
                protected void onMethodEnter() {
                    System.out.println("AA");
                    this.visitLdcInsn(key);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "setStartTime", "(Ljava/lang/String;)V", false);
                    System.out.println("AA1");
                }

                @Override
                protected void onMethodExit(int i) {
                    System.out.println("BB");
                    this.visitLdcInsn(key);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "setEndTime", "(Ljava/lang/String;)V", false);
                    //向栈中压入类名称
                    this.visitLdcInsn(className);
                    //向栈中压入方法名
                    this.visitLdcInsn(name);
                    //向栈中压入方法描述
                    this.visitLdcInsn(desc);
                    //相当于com.blueware.agent.TimeUtil.getExclusiveTime("com/blueware/agent/TestTime","testTime");
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "getExclusiveTime", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J", false);
                    System.out.println("BB1");
                }
            };
        }
        System.out.println("CC");
        return methodVisitor;
    }
}
