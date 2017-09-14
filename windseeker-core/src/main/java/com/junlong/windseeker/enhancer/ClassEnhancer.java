package com.junlong.windseeker.enhancer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 参考文章</br>
 * http://www.iteye.com/topic/1116696
 * http://xj84.iteye.com/blog/1221105
 * http://blog.csdn.net/youling_lh/article/details/9738993
 * https://my.oschina.net/u/1166271/blog/163637 @import
 *
 * ASM文档:http://download.forge.objectweb.org/asm/asm4-guide.pdf
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
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new byte[0];
    }
}
