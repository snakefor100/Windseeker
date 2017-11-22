package com.junlong.windseeker.enhancer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by niujunlong on 17/11/22.
 */
public class MethodAspect {
    private static final Logger LOG = LoggerFactory.getLogger(MethodAspect.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    }


    public static void beforeMethod(String className,String method,Object[] args){
        try {
            LOG.info("进入方法切面beforeMethod:"+className+"-"+method+"-"+objectMapper.writeValueAsString(args));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public static void afterMethod(String className,String method,String opCode,Object[] args){
        try {
            LOG.info("进入方法切面 After:"+className+"-"+method+"-"+objectMapper.writeValueAsString(args));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void methodError(String className,String method,String opCode,Object[] args){
        try {
            LOG.info("进入方法切面 Error:"+className+"-"+method+"-"+objectMapper.writeValueAsString(args));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public static void send(String className,String method,String opCode,Object[] localVar,Object[] args){
        LOG.info("进入方法切面:"+className+"-"+method+"-"+opCode+"-"+localVar+"-"+args);
        try {
            if(null != args){
                LOG.info("参数长度:"+args.length);
            }
            if(null != localVar){
                LOG.info("参数长度22:"+localVar.length);
                LOG.info("wq"+objectMapper.writeValueAsString(localVar));
            }
            LOG.info(objectMapper.writeValueAsString(args));
        }catch (Exception e){
            LOG.error("错误:",e);
            e.printStackTrace();
        }


    }
}