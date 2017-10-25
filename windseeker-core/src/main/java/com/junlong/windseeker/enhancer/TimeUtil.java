package com.junlong.windseeker.enhancer;

import com.junlong.windseeker.command.asm.AdviceWeaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niujunlong on 17/10/16.
 */
public class TimeUtil {
    private static final Logger LOG = LoggerFactory.getLogger(AdviceWeaver.class);
    private static Map<String, Long> startTimes = new HashMap<String, Long>();
    private static Map<String, Long> endTimes   = new HashMap<String, Long>();

    private TimeUtil() {
    }

    public static long getStartTime(String key) {

        return startTimes.get(key);
    }

    public static void setStartTime(String key) {
        LOG.info("设置开始时间: {}",key);

        startTimes.put(key, System.currentTimeMillis());
    }

    public static long getEndTime(String key) {
        return endTimes.get(key);
    }

    public static void setEndTime(String key) {
        LOG.info("设置结束时间: {}",key);
        endTimes.put(key, System.currentTimeMillis());
    }

    public static long getExclusiveTime(String key) {

        long exclusive = getEndTime(key) - getStartTime(key);
        LOG.info("总时间: {}",exclusive);
        return exclusive;
    }

    public static void main(String[] args) {
        TimeUtil.setStartTime("11");
        TimeUtil.setEndTime("11");
        TimeUtil.getExclusiveTime("11");
    }
}
