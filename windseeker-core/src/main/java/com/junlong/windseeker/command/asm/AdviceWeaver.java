package com.junlong.windseeker.command.asm;

import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.JSRInlinerAdapter;
import org.objectweb.asm.commons.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by niujunlong on 2017/10/21.
 */
public class AdviceWeaver extends ClassVisitor implements Opcodes {
    private static final Logger LOG = LoggerFactory.getLogger(AdviceWeaver.class);
    private final String adviceId;
    private final String internalClassName;
    private final String javaClassName;

    /**
     * 构建通知编织器
     * @param adviceId
     * @param internalClassName
     * @param cv
     */
    public AdviceWeaver(
            final String adviceId,
            final String internalClassName,
            final ClassVisitor cv) {
        super(ASM5, cv);
        this.adviceId = adviceId;
        this.internalClassName = internalClassName;
        this.javaClassName = StringUtils.replace(internalClassName, "/", ".");
    }


    @Override
    public MethodVisitor visitMethod(
            final int access,
            final String name,
            final String desc,
            final String signature,
            final String[] exceptions) {
        LOG.info("adviceweaver: visiMethod");
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


            @Override
            public void visitEnd() {
                LOG.info("visitEnd");
                super.visitEnd();
            }

            @Override
            public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
                LOG.info("visitTryCatchBlock");
                super.visitTryCatchBlock(label, label1, label2, s);
            }

            @Override
            public void visitMethodInsn(int i, String s, String s1, String s2, boolean b) {
                LOG.info("visitMethodInsn");
                super.visitMethodInsn(i, s, s1, s2, b);
            }

            @Override
            public void visitLineNumber(int i, Label label) {
                LOG.info("visitLineNumber {} {}",i,label.getClass().getCanonicalName());
                super.visitLineNumber(i, label);
            }

            @Override
            public void visitMaxs(int i, int i1) {
                LOG.info("visitMaxs {}");
                super.visitMaxs(i, i1);
            }

            @Override
            public void visitInsn(int i) {
                LOG.info("visitInsn {}");
                super.visitInsn(i);
            }

            @Override
            protected void onMethodEnter() {
                LOG.info("onMethodEnter 1");
                // 加载before方法
                loadAdviceMethod(KEY_GREYS_ADVICE_BEFORE_METHOD);
                LOG.info("onMethodEnter 2");
                // 推入Method.invoke()的第一个参数
                push((Type) null);
                LOG.info("onMethodEnter 3");
                // 方法参数
                loadArrayForBefore();
                LOG.info("onMethodEnter 4");
                // 调用方法
                invokeVirtual(ASM_TYPE_METHOD, ASM_METHOD_METHOD_INVOKE);
                LOG.info("onMethodEnter 5");
                pop();
                LOG.info("onMethodEnter 6");
                mark(beginLabel);
                LOG.info("onMethodEnter 7");
            }

            /**
             * 加载before通知参数数组
             */
            private void loadArrayForBefore() {
                push(7);
                newArray(ASM_TYPE_OBJECT);

                dup();
                push(0);
                push(adviceId);
                box(ASM_TYPE_INT);
                arrayStore(ASM_TYPE_INTEGER);

                dup();
                push(1);
                loadClassLoader();
                arrayStore(ASM_TYPE_CLASS_LOADER);

                dup();
                push(2);
                push(javaClassName);
                arrayStore(ASM_TYPE_STRING);

                dup();
                push(3);
                push(name);
                arrayStore(ASM_TYPE_STRING);

                dup();
                push(4);
                push(desc);
                arrayStore(ASM_TYPE_STRING);

                dup();
                push(5);
                loadThisOrPushNullIfIsStatic();
                arrayStore(ASM_TYPE_OBJECT);

                dup();
                push(6);
                loadArgArray();
                arrayStore(ASM_TYPE_OBJECT_ARRAY);
            }


            /**
             * 加载this/null
             */
            private void loadThisOrPushNullIfIsStatic() {
                if (isStaticMethod()) {
                    push((Type) null);
                } else {
                    loadThis();
                }
            }


            /**
             * 是否静态方法
             * @return true:静态方法 / false:非静态方法
             */
            private boolean isStaticMethod() {
                return (methodAccess & ACC_STATIC) != 0;
            }

            /**
             * 加载ClassLoader<br/>
             * 这里分开静态方法中ClassLoader的获取以及普通方法中ClassLoader的获取
             * 主要是性能上的考虑
             */
            private void loadClassLoader() {

                if (this.isStaticMethod()) {

//                    // fast enhance
//                    if (GlobalOptions.isEnableFastEnhance) {
//                        visitLdcInsn(Type.getType(String.format("L%s;", internalClassName)));
//                        visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getClassLoader", "()Ljava/lang/ClassLoader;", false);
//                    }

                    // normal enhance
//                    else {

                    // 这里不得不用性能极差的Class.forName()来完成类的获取,因为有可能当前这个静态方法在执行的时候
                    // 当前类并没有完成实例化,会引起JVM对class文件的合法性校验失败
                    // 未来我可能会在这一块考虑性能优化,但对于当前而言,功能远远重要于性能,也就不打算折腾这么复杂了
                    visitLdcInsn(javaClassName);
                    invokeStatic(ASM_TYPE_CLASS, Method.getMethod("Class forName(String)"));
                    invokeVirtual(ASM_TYPE_CLASS, Method.getMethod("ClassLoader getClassLoader()"));
//                    }

                } else {
                    loadThis();
                    invokeVirtual(ASM_TYPE_OBJECT, Method.getMethod("Class getClass()"));
                    invokeVirtual(ASM_TYPE_CLASS, Method.getMethod("ClassLoader getClassLoader()"));
                }

            }

            @Override
            protected void onMethodExit(int i) {
                LOG.info("onMethodExit");
                super.onMethodExit(i);
            }

            /**
             * 加载通知方法
             * @param keyOfMethod 通知方法KEY
             */
            private void loadAdviceMethod(int keyOfMethod) {

                switch (keyOfMethod) {

                    case KEY_GREYS_ADVICE_BEFORE_METHOD: {
                        LOG.info("loadAdviceMethod");
                        break;
                    }

                    case KEY_GREYS_ADVICE_RETURN_METHOD: {
                        LOG.info("loadAdviceMethod");
                        break;
                    }

                    case KEY_GREYS_ADVICE_THROWS_METHOD: {
                        LOG.info("loadAdviceMethod");
                        break;
                    }

                    case KEY_GREYS_ADVICE_BEFORE_INVOKING_METHOD: {
                        LOG.info("loadAdviceMethod");
                        break;
                    }

                    case KEY_GREYS_ADVICE_AFTER_INVOKING_METHOD: {
                        LOG.info("loadAdviceMethod");
                        break;
                    }

                    case KEY_GREYS_ADVICE_THROW_INVOKING_METHOD: {
                        LOG.info("loadAdviceMethod");
                        break;
                    }

                    default: {
                        throw new IllegalArgumentException("illegal keyOfMethod=" + keyOfMethod);
                    }

                }

            }
        };
    }
}