package com.junlong.windseeker.command.asm;

import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.JSRInlinerAdapter;
import org.objectweb.asm.commons.Method;

/**
 * Created by niujunlong on 2017/10/21.
 */
public class AdviceWeaver extends ClassVisitor implements Opcodes {
    /**
     * 构建通知编织器
     *
     * @param adviceId          通知ID
     * @param isTracing         可跟踪方法调用
     * @param internalClassName 类名称(透传)
     * @param asmMethodMatcher  asm方法匹配
     *                          只有匹配上的方法才会被织入通知器
     * @param affect            影响计数
     * @param cv                ClassVisitor for ASM
     */
    public AdviceWeaver(
            final int adviceId,
            final boolean isTracing,
            final String internalClassName,
            final Matcher<AsmMethod> asmMethodMatcher,
            final EnhancerAffect affect,
            final ClassVisitor cv) {
        super(ASM5, cv);
        this.adviceId = adviceId;
        this.isTracing = isTracing;
        this.internalClassName = internalClassName;
        this.javaClassName = tranClassName(internalClassName);
        this.asmMethodMatcher = asmMethodMatcher;
        this.affect = affect;
    }
    /**
     * 是否需要忽略
     */
    private boolean isIgnore(MethodVisitor mv, int access, String name, String desc) {
        return null == mv
                || isAbstract(access)
                || !asmMethodMatcher.matching(new AsmMethod(name, desc))
                || isEquals(name, "<clinit>");
    }



    @Override
    public MethodVisitor visitMethod(
            final int access,
            final String name,
            final String desc,
            final String signature,
            final String[] exceptions) {

        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        return new AdviceAdapter(ASM5, new JSRInlinerAdapter(mv, access, name, desc, signature, exceptions), access, name, desc) {

            // -- Lebel for try...catch block
            private final Label beginLabel = new Label();
            private final Label endLabel = new Label();

            // -- KEY of advice --
            private final int KEY_GREYS_ADVICE_BEFORE_METHOD = 0;
            private final int KEY_GREYS_ADVICE_RETURN_METHOD = 1;
            private final int KEY_GREYS_ADVICE_THROWS_METHOD = 2;
            private final int KEY_GREYS_ADVICE_BEFORE_INVOKING_METHOD = 3;
            private final int KEY_GREYS_ADVICE_AFTER_INVOKING_METHOD = 4;
            private final int KEY_GREYS_ADVICE_THROW_INVOKING_METHOD = 5;


            // -- KEY of ASM_TYPE or ASM_METHOD --
            private final Type ASM_TYPE_SPY = Type.getType("Lcom/github/ompc/greys/agent/Spy;");
            private final Type ASM_TYPE_OBJECT = Type.getType(Object.class);
            private final Type ASM_TYPE_OBJECT_ARRAY = Type.getType(Object[].class);
            private final Type ASM_TYPE_CLASS = Type.getType(Class.class);
            private final Type ASM_TYPE_INTEGER = Type.getType(Integer.class);
            private final Type ASM_TYPE_CLASS_LOADER = Type.getType(ClassLoader.class);
            private final Type ASM_TYPE_STRING = Type.getType(String.class);
            private final Type ASM_TYPE_THROWABLE = Type.getType(Throwable.class);
            private final Type ASM_TYPE_INT = Type.getType(int.class);
            private final Type ASM_TYPE_METHOD = Type.getType(java.lang.reflect.Method.class);
            private final Method ASM_METHOD_METHOD_INVOKE = Method.getMethod("Object invoke(Object,Object[])");

            // 代码锁
            private final CodeLock codeLockForTracing = new TracingAsmCodeLock(this);


            private void _debug(final StringBuilder append, final String msg) {

                if (!isDebugForAsm) {
                    return;
                }

                // println msg
                visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                if (StringUtils.isBlank(append.toString())) {
                    visitLdcInsn(append.append(msg).toString());
                } else {
                    visitLdcInsn(append.append(" >> ").append(msg).toString());
                }

                visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
            }

            @Override
            public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
                super.visitTryCatchBlock(label, label1, label2, s);
            }

            @Override
            public void visitMethodInsn(int i, String s, String s1, String s2, boolean b) {
                super.visitMethodInsn(i, s, s1, s2, b);
            }

            @Override
            public void visitLineNumber(int i, Label label) {
                super.visitLineNumber(i, label);
            }

            @Override
            public void visitMaxs(int i, int i1) {
                super.visitMaxs(i, i1);
            }

            @Override
            public void visitInsn(int i) {
                super.visitInsn(i);
            }

            @Override
            protected void onMethodEnter() {
                super.onMethodEnter();
            }

            @Override
            protected void onMethodExit(int i) {
                super.onMethodExit(i);
            }
        };
    }}