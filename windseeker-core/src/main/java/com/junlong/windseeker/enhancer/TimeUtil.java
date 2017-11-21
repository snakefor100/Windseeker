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

    public static long getStartTime(String key) {

        return startTimes.get(key);
    }

    public static void setStartTime() {
        LOG.info("设置开始时间: {}",11);
        System.out.println("QQQQQQQQQQ");
        startTimes.put("11", System.currentTimeMillis());
    }

    public static long getEndTime(String key) {
        return endTimes.get(key);
    }

    public static void setEndTime() {
        LOG.info("设置结束时间: {}",11);
        endTimes.put("11", System.currentTimeMillis());
    }

    public static long getExclusiveTime(String key) {

        long exclusive = getEndTime(key) - getStartTime(key);
        LOG.info("总时间: {}",exclusive);
        return exclusive;
    }


}
