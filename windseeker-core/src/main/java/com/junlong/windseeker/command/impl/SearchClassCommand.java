package com.junlong.windseeker.command.impl;

import com.junlong.windseeker.command.Action;
import com.junlong.windseeker.command.Command;
import com.junlong.windseeker.domain.search.ClassEntity;
import com.junlong.windseeker.domain.search.Field;
import com.junlong.windseeker.server.session.DefaultSessionManager;
import com.junlong.windseeker.utils.JsonUtils;
import com.junlong.windseeker.utils.WsClassUtils;

import java.lang.annotation.Annotation;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Created by niujunlong on 17/9/30.
 */
public class SearchClassCommand implements Command {

    private String classPattern;

    public SearchClassCommand(String classPattern) {
        this.classPattern = classPattern;
    }

    @Override
    public Action getAction() {
        return new Action() {
            @Override
            public void doAction(DefaultSessionManager.Session session) {
                Instrumentation inst = session.getInst();
                Class[] allLoadedClasses = inst.getAllLoadedClasses();
                List<Class> classList = WsClassUtils.matchClass(allLoadedClasses, classPattern);
                List<ClassEntity> result = new ArrayList<ClassEntity>(classList.size());
                for (Class clazz : classList) {
                    ClassEntity classEntity = new ClassEntity();
                    classEntity.setClassInfo(clazz.getCanonicalName());
                    classEntity.setCodeSource(clazz.getProtectionDomain().getCodeSource());
                    classEntity.setInterface(clazz.isInterface());
                    classEntity.setAnnotation(clazz.isAnnotation());
                    classEntity.setEnum(clazz.isEnum());
                    classEntity.setAnonymousClass(clazz.isAnonymousClass());
                    classEntity.setArray(clazz.isArray());
                    classEntity.setLocalClass(clazz.isLocalClass());
                    classEntity.setMemberClass(clazz.isMemberClass());
                    classEntity.setPrimitive(clazz.isPrimitive());
                    classEntity.setSynthetic(clazz.isSynthetic());
                    classEntity.setSimpleName(clazz.getSimpleName());
                    classEntity.setModifier(WsClassUtils.tranModifier(clazz.getModifiers()));
                    classEntity.setAnnotation(getAnnotation(clazz));
                    classEntity.setInterfaces(getInterface(clazz));
                    classEntity.setSuperClass(getSuperClass(clazz));
                    classEntity.setClassLoader(getClassLoader(clazz));
                    classEntity.setFieldList(getFieldList(clazz));
                    result.add(classEntity);
                }
                session.getChannel().writeAndFlush(new TextWebSocketFrame(JsonUtils.toString(result)));

            }
        };
    }

    //// TODO: 17/10/15 静态变量,带annotation变量注意
    private List<Field> getFieldList(Class clazz) {
        java.lang.reflect.Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) {
            return new ArrayList<Field>(0);
        }
        List<Field> result = new ArrayList<>(declaredFields.length);
        for (java.lang.reflect.Field field : declaredFields) {
            Field showField = new Field();
            showField.setModefier(WsClassUtils.tranModifier(field.getModifiers()));
            showField.setName(field.getName());
            showField.setType(field.getType().getCanonicalName());
            result.add(showField);
        }
        return result;
    }

    private String getClassLoader(Class clazz) {
        final StringBuilder classloaderSB = new StringBuilder();
        ClassLoader loader = clazz.getClassLoader();
        if (null != loader) {
            classloaderSB.append(loader.toString() + "\r\n");
            while (true) {
                loader = loader.getParent();
                if (null == loader) {
                    break;
                }
                classloaderSB.append(loader.toString() + "\r\n");
            }
        }
        return classloaderSB.toString();
    }

    private String getAnnotation(Class clazz) {
        final StringBuilder annotationSB = new StringBuilder();
        final Annotation[] annotationArray = clazz.getDeclaredAnnotations();

        if (null != annotationArray && annotationArray.length > 0) {
            for (Annotation annotation : annotationArray) {
                annotationSB.append(annotation.annotationType().getCanonicalName()).append(",");
            }
            if (annotationSB.length() > 0) {
                annotationSB.deleteCharAt(annotationSB.length() - 1);
            }
        } else {
            annotationSB.append(EMPTY);
        }

        return annotationSB.toString();
    }

    private String getInterface(Class clazz) {
        final StringBuilder interfaceSB = new StringBuilder();
        final Class<?>[] interfaceArray = clazz.getInterfaces();
        if (null == interfaceArray || interfaceArray.length == 0) {
            interfaceSB.append(EMPTY);
        } else {
            for (Class<?> i : interfaceArray) {
                interfaceSB.append(i.getName()).append(",");
            }
            if (interfaceSB.length() > 0) {
                interfaceSB.deleteCharAt(interfaceSB.length() - 1);
            }
        }
        return interfaceSB.toString();
    }

    private String getSuperClass(Class clazz) {
        final StringBuilder superClassSB = new StringBuilder();
        Class<?> superClass = clazz.getSuperclass();
        if (null != superClass) {
            superClassSB.append(superClass.getCanonicalName() + "\r\n");
            while (true) {
                superClass = superClass.getSuperclass();
                if (null == superClass) {
                    break;
                }
                superClassSB.append(superClass.getCanonicalName() + "\r\n");
            }
        }
        return superClassSB.toString();
    }


}
