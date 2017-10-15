package com.junlong.windseeker.domain.search;

import java.security.CodeSource;
import java.util.List;

/**
 * Created by niujunlong on 17/9/30.
 */
public class ClassEntity {
    private String classInfo;
    private CodeSource codeSource;
    private Boolean isInterface;
    private Boolean isAnnotation;
    private Boolean isEnum;
    private Boolean isAnonymousClass;
    private Boolean isArray;
    private Boolean isLocalClass;
    private Boolean isMemberClass;
    private Boolean isPrimitive;
    private Boolean isSynthetic;
    private String simpleName;
    private String modifier;
    private String annotation;
    private String interfaces;
    private String superClass;
    private String classLoader;
    private List<Field> fieldList;

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public CodeSource getCodeSource() {
        return codeSource;
    }

    public void setCodeSource(CodeSource codeSource) {
        this.codeSource = codeSource;
    }

    public Boolean getInterface() {
        return isInterface;
    }

    public void setInterface(Boolean anInterface) {
        isInterface = anInterface;
    }

    public Boolean getAnnotation() {
        return isAnnotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public String getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(String classLoader) {
        this.classLoader = classLoader;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public void setAnnotation(Boolean annotation) {
        isAnnotation = annotation;
    }

    public Boolean getEnum() {
        return isEnum;
    }

    public void setEnum(Boolean anEnum) {
        isEnum = anEnum;
    }

    public Boolean getAnonymousClass() {
        return isAnonymousClass;
    }

    public void setAnonymousClass(Boolean anonymousClass) {
        isAnonymousClass = anonymousClass;
    }

    public Boolean getArray() {
        return isArray;
    }

    public void setArray(Boolean array) {
        isArray = array;
    }

    public Boolean getLocalClass() {
        return isLocalClass;
    }

    public void setLocalClass(Boolean localClass) {
        isLocalClass = localClass;
    }

    public Boolean getMemberClass() {
        return isMemberClass;
    }

    public void setMemberClass(Boolean memberClass) {
        isMemberClass = memberClass;
    }

    public Boolean getPrimitive() {
        return isPrimitive;
    }

    public void setPrimitive(Boolean primitive) {
        isPrimitive = primitive;
    }

    public Boolean getSynthetic() {
        return isSynthetic;
    }

    public void setSynthetic(Boolean synthetic) {
        isSynthetic = synthetic;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
