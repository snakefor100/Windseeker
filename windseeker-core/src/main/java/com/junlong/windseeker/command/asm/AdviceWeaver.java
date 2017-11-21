package com.junlong.windseeker.command.asm;

import com.junlong.windseeker.server.session.DefaultSessionManager;
import com.junlong.windseeker.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Created by niujunlong on 2017/10/21.
 */
public class AdviceWeaver extends ClassVisitor implements Opcodes {
    private static final Logger LOG = LoggerFactory.getLogger(AdviceWeaver.class);
    private final String adviceId;
    private final String internalClassName;
    private final String javaClassName;
    private final DefaultSessionManager.Session session;
    private final Channel channel;


    public AdviceWeaver(
            final DefaultSessionManager.Session session,
            final String internalClassName,
            final ClassVisitor cv) {
        super(ASM5, cv);
        this.session = session;
        this.channel = session.getChannel();
        this.adviceId = session.getSessionId();
        this.internalClassName = internalClassName;
        this.javaClassName = StringUtils.replace(internalClassName, "/", ".");
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        if("<init>".equals(name) || !name.equals("add")){
            System.out.println("非监控方法");
            return mv;
        }
        System.out.println("监控方法");
        return new AopMethod(this.api,mv);
    }

    class AopMethod extends MethodVisitor implements Opcodes {
        public AopMethod(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            System.out.println("方法开始!");
            this.visitMethodInsn(INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "setStartTime", "()V",false);
        }

        @Override
        public void visitInsn(int opcode) {
            System.out.println("方法结束 :"+opcode);
            if (opcode == RETURN) {//在返回之前安插after 代码。
                mv.visitMethodInsn(INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "setEndTime", "()V",false);
            }
            channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toString("ff")));
            super.visitInsn(opcode);

        }
    }

}