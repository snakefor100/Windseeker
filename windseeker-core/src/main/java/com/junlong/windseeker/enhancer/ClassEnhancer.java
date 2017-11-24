package com.junlong.windseeker.enhancer;


import com.junlong.windseeker.command.asm.AdviceWeaver;
import com.junlong.windseeker.server.session.DefaultSessionManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

/**
 * 参考文章</br>
 * http://www.iteye.com/topic/1116696
 * http://xj84.iteye.com/blog/1221105
 * http://blog.csdn.net/youling_lh/article/details/9738993
 * https://my.oschina.net/u/1166271/blog/163637 @import
 *http://blog.h5min.cn/axman/article/details/4285759
 * ASM文档:http://download.forge.objectweb.org/asm/asm4-guide.pdf
 * http://m.blog.csdn.net/axman/article/details/4268189
 *
 *
 *  addTransformer/ removeTransformer：注册/删除ClassFileTransformer
 *  retransformClasses：对于已经加载的类重新进行转换处理，即会触发重新加载类定义，需要注意的是，新加载的类不能修改旧有的类声明，譬如不能增加属性、不能修改方法声明
 *  redefineClasses：与如上类似，但不是重新进行转换处理，而是直接把处理结果(bytecode)直接给JVM
 *  getAllLoadedClasses：获得当前已经加载的Class，可配合retransformClasses使用
 *  getInitiatedClasses：获得由某个特定的ClassLoader加载的类定义
 *  getObjectSize：获得一个对象占用的空间，包括其引用的对象
 *  appendToBootstrapClassLoaderSearch/appendToSystemClassLoaderSearch：增加BootstrapClassLoader/SystemClassLoader的搜索路径
 *  isNativeMethodPrefixSupported/setNativeMethodPrefix：支持拦截Native Method


 *
 * Created by niujunlong on 17/9/12.
 */
public class ClassEnhancer implements ClassFileTransformer {
    private DefaultSessionManager.Session session;

    public static Map m = new HashMap();
    public ClassEnhancer(DefaultSessionManager.Session session) {
        this.session = session;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("转换:"+className);
        if(className != null && className.equals("com/junlong/test/TestImpl")){
            System.out.println("进入 "+className);



            //读取类的字节码流
            ClassReader reader = new ClassReader(classfileBuffer);
            //创建操作字节流值对象，ClassWriter.COMPUTE_MAXS:表示自动计算栈大小
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | COMPUTE_MAXS);
            //接受一个ClassVisitor子类进行字节码修改


          reader.accept(new AdviceWeaver(session,reader.getClassName(),writer),EXPAND_FRAMES);


            //返回修改后的字节码流
            return writer.toByteArray();
        }

        return null;
    }
}
