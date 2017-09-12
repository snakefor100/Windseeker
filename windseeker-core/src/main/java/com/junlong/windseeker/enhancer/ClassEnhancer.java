package com.junlong.windseeker.enhancer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 参考文章</br>
 * http://www.iteye.com/topic/1116696
 * http://xj84.iteye.com/blog/1221105
 * Created by niujunlong on 17/9/12.
 */
public class ClassEnhancer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new byte[0];
    }
}
