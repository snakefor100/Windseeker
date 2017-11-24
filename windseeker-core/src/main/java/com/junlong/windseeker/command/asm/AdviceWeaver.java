package com.junlong.windseeker.command.asm;

import com.junlong.windseeker.enhancer.MethodAspect;
import com.junlong.windseeker.server.session.DefaultSessionManager;
import com.junlong.windseeker.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;
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


        mv = new AdviceAdapter(ASM5, mv, access, name, desc) {


            @Override
            protected void onMethodEnter() {
//                System.out.println(access+"_"+name+"_"+desc+"_"+signature+"_"+exceptions);
//                mv.visitMethodInsn(INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "setStartTime", "()V",false);
//

//                System.out.println(access + "_" + name + "_" + desc + "_" + signature + "_" + exceptions);
//                mv.visitMethodInsn(INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "setStartTime", "()V",false);
                visitLdcInsn(javaClassName);
                visitLdcInsn(name);

                loadArgArray();

                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/junlong/windseeker/enhancer/MethodAspect", "beforeMethod", "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V", false);



                super.onMethodEnter();
            }



            @Override
            protected void onMethodExit(int opcode) {
                LOG.info("方法退出");



//                visitLdcInsn(javaClassName);
//                visitLdcInsn(name);
//                visitLdcInsn(String.valueOf(opcode));
//                loadArgArray();
//
//                if(opcode == Opcodes.RETURN){
//                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/junlong/windseeker/enhancer/MethodAspect", "afterMethod", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V", false);
//
//                }else if(opcode == Opcodes.ATHROW){
//                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/junlong/windseeker/enhancer/MethodAspect", "methodError", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V", false);
//
//                }else {
//
//                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/junlong/windseeker/enhancer/MethodAspect", "test", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V", false);
//
//                }










//                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/junlong/windseeker/enhancer/MethodAspect", "beforeMethod", "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V", false);





//                visitLdcInsn(javaClassName);
//                visitLdcInsn(name); //前一篇文章我们只用了方法的sortName,真正实现时应该用FullName,因为
//                //方法有重载，只凭sortName不能限定到某一个方法。
//                visitLdcInsn(String.valueOf(opcode));
//                int localVarCnt = 0;
//                LOG.info("nextLocal:"+nextLocal + ",firstLocal:"+firstLocal+",差:"+localVarCnt);
//                if(nextLocal > firstLocal) {
//                    localVarCnt = nextLocal - firstLocal;
//                    LOG.info("nextLocal:"+nextLocal + ",firstLocal:"+firstLocal+",差:"+localVarCnt);
//                    Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
//                    push(localVarCnt);
//                    newArray(OBJECT_TYPE);
//                    for (int i = 0; i < localVarCnt; i++)
//                    {
//                        int index = super.firstLocal + i;
//                        dup();
//                        push(i);
//                        loadLocal(index);
//                        box(getLocalType(index));
//                        arrayStore(OBJECT_TYPE);
//                    }
//                }else{
//                    visitInsn(ACONST_NULL); //为了占用一个栈位置。
//                }
//                int argsCnt = localVarCnt;
//
//                //从方法签名可以分析出参数个数。
//                if(argsCnt > 0){
//                    loadArgArray();
//                }
//                else{
//                    visitInsn(ACONST_NULL); //为了占用一个栈位置。
//                }
//                visitMethodInsn(Opcodes.INVOKESTATIC, "MethodAspect","send",
//                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;[Ljava/lang/Object;)V",false);

                super.onMethodExit(opcode);
            }


        };

        return mv;
    }

    class AopMethod extends MethodVisitor implements Opcodes {
        public AopMethod(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            System.out.println("方法开始!");
            this.visitMethodInsn(INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "setStartTime", "()V", false);
        }

        @Override
        public void visitInsn(int opcode) {
            System.out.println("方法结束 :" + opcode);
            if (opcode == RETURN) {//在返回之前安插after 代码。
                mv.visitMethodInsn(INVOKESTATIC, "com/junlong/windseeker/enhancer/TimeUtil", "setEndTime", "()V", false);
            }
            channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.toString("ff")));
            super.visitInsn(opcode);

        }
    }

}