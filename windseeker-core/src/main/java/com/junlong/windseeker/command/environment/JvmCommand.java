package com.junlong.windseeker.command.environment;

import com.junlong.windseeker.command.Action;
import com.junlong.windseeker.command.Command;
import com.junlong.windseeker.domain.AppConstants;
import com.junlong.windseeker.domain.search.JvmEntity;
import com.junlong.windseeker.server.session.DefaultSessionManager;
import com.junlong.windseeker.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Created by niujunlong on 17/9/15.
 */
public class JvmCommand implements Command {
    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    private final ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
    private final CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
    private final Collection<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
    private final Collection<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    private final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();


    @Override
    public Action getAction() {
        return new Action() {
            @Override
            public void doAction(DefaultSessionManager.Session session) {
                JvmEntity jvmEntity = new JvmEntity();
                jvmEntity.setRunTimeEntity(getRunTimeEntity(jvmEntity));
                jvmEntity.setClassLoadEntity(getClassLoadEntity(jvmEntity));
                jvmEntity.setCompilationEntity(getCompilationEntity(jvmEntity));
                jvmEntity.setGarbageCollectorsEntityList(getGarbageCollectorsEntity(jvmEntity));
                jvmEntity.setMemoryManagersEntityList(getMemoryManagersEntity(jvmEntity));
                jvmEntity.setMemoryEntity(getMemoryEntity(jvmEntity));
                jvmEntity.setOperatingSystemEntity(getOperatingSystemEneity(jvmEntity));
                jvmEntity.setThreadCountEntity(getThreadCountEntity(jvmEntity));
                session.getChannel().writeAndFlush(new TextWebSocketFrame(JsonUtils.toString(jvmEntity)));


            }


        };
    }

    private JvmEntity.ThreadCountEntity getThreadCountEntity(JvmEntity jvmEntity) {
        JvmEntity.ThreadCountEntity threadCountInstance = jvmEntity.getThreadCountInstance();
        threadCountInstance.setCount(threadMXBean.getThreadCount());
        threadCountInstance.setDaemonCount(threadMXBean.getDaemonThreadCount());
        threadCountInstance.setLiveCount(threadMXBean.getPeakThreadCount());
        threadCountInstance.setStartedCount(threadMXBean.getTotalStartedThreadCount());
        return threadCountInstance;
    }

    private JvmEntity.OperatingSystemEntity getOperatingSystemEneity(JvmEntity jvmEntity) {
        JvmEntity.OperatingSystemEntity operatingInstance = jvmEntity.getOperatingInstance();
        operatingInstance.setOs(operatingSystemMXBean.getName());
        operatingInstance.setArch(operatingSystemMXBean.getArch());
        operatingInstance.setProcessorsCount(operatingSystemMXBean.getAvailableProcessors());
        operatingInstance.setLoadAverage(operatingSystemMXBean.getSystemLoadAverage());
        operatingInstance.setVersion(operatingSystemMXBean.getVersion());
        return operatingInstance;
    }

    private JvmEntity.MemoryEntity getMemoryEntity(JvmEntity jvmEntity) {
        JvmEntity.MemoryEntity memoryInstance = jvmEntity.getMemoryInstance();
        memoryInstance.setHeadMemoryCommitted(memoryMXBean.getHeapMemoryUsage().getCommitted());
        memoryInstance.setHeadMemoryInit(memoryMXBean.getHeapMemoryUsage().getCommitted());
        memoryInstance.setHeadMemoryMax(memoryMXBean.getHeapMemoryUsage().getCommitted());
        memoryInstance.setHeadMemoryUsed(memoryMXBean.getHeapMemoryUsage().getCommitted());
        memoryInstance.setNoheadMemoryCommitted(memoryMXBean.getNonHeapMemoryUsage().getCommitted());
        memoryInstance.setNoheadMemoryInit(memoryMXBean.getNonHeapMemoryUsage().getCommitted());
        memoryInstance.setNoheadMemoryMax(memoryMXBean.getNonHeapMemoryUsage().getCommitted());
        memoryInstance.setNoheadMemoryUsed(memoryMXBean.getNonHeapMemoryUsage().getCommitted());
        memoryInstance.setPendingFinalizeCount(memoryMXBean.getObjectPendingFinalizationCount());
        return memoryInstance;
    }

    private List<JvmEntity.MemoryManagersEntity> getMemoryManagersEntity(JvmEntity jvmEntity) {
        if (memoryManagerMXBeans.isEmpty()) {
            return null;
        }
        List<JvmEntity.MemoryManagersEntity> memoryManagersEntityList = new ArrayList<JvmEntity.MemoryManagersEntity>(memoryManagerMXBeans.size());
        for (MemoryManagerMXBean memoryManagerMXBean : memoryManagerMXBeans) {
            JvmEntity.MemoryManagersEntity memoryManagersInstance = jvmEntity.getMemoryManagersInstance();
            memoryManagersInstance.setName(memoryManagerMXBean.isValid() ? memoryManagerMXBean.getName() : memoryManagerMXBean.getName() + "(Invalid)");
            memoryManagersInstance.setValue(memoryManagerMXBean.getMemoryPoolNames());
            memoryManagersEntityList.add(memoryManagersInstance);
        }
        return memoryManagersEntityList;
    }

    private List<JvmEntity.GarbageCollectorsEntity> getGarbageCollectorsEntity(JvmEntity jvmEntity) {
        if (garbageCollectorMXBeans.isEmpty()) {
            return null;
        }
        List<JvmEntity.GarbageCollectorsEntity> garbageCollectorsEntityList = new ArrayList<JvmEntity.GarbageCollectorsEntity>(garbageCollectorMXBeans.size());
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
            JvmEntity.GarbageCollectorsEntity garbageCollectorsInstance = jvmEntity.getGarbageCollectorsInstance();
            garbageCollectorsInstance.setPsMarkSweep(garbageCollectorMXBean.getName() + "\n[count/time]");
            garbageCollectorsInstance.setPsScavenge(garbageCollectorMXBean.getCollectionCount() / garbageCollectorMXBean.getCollectionTime() + "(ms)");
            garbageCollectorsEntityList.add(garbageCollectorsInstance);
        }
        return garbageCollectorsEntityList;
    }

    private JvmEntity.CompilationEntity getCompilationEntity(JvmEntity jvmEntity) {
        JvmEntity.CompilationEntity compilationInstance = jvmEntity.getCompilationInstance();
        compilationInstance.setName(compilationMXBean.getName());
        if (compilationMXBean.isCompilationTimeMonitoringSupported()) {
            compilationInstance.setTotalCompileTime(compilationMXBean.getTotalCompilationTime() + "(ms)");
        }
        return compilationInstance;
    }

    private JvmEntity.ClassLoadEntity getClassLoadEntity(JvmEntity jvmEntity) {
        JvmEntity.ClassLoadEntity classLoadInstance = jvmEntity.getClassLoadInstance();
        classLoadInstance.setLoadedClassCount(classLoadingMXBean.getLoadedClassCount());
        classLoadInstance.setTotalLoadedClassCount(classLoadingMXBean.getTotalLoadedClassCount());
        classLoadInstance.setUnloadedClassCount(classLoadingMXBean.getUnloadedClassCount());
        classLoadInstance.setVerbose(classLoadingMXBean.isVerbose());
        return classLoadInstance;
    }


    private JvmEntity.RunTimeEntity getRunTimeEntity(JvmEntity jvmEntity) {
        JvmEntity.RunTimeEntity runTimeInstance = jvmEntity.getRunTimeInstance();
        runTimeInstance.setMachineName(runtimeMXBean.getName());
        runTimeInstance.setJvmStartTime(new DateTime(runtimeMXBean.getStartTime()).toString(AppConstants.DATA_FORMAT_TIME));
        runTimeInstance.setManagementSpecVersion(runtimeMXBean.getManagementSpecVersion());
        runTimeInstance.setSpecName(runtimeMXBean.getSpecName());
        runTimeInstance.setSpecVendor(runtimeMXBean.getSpecVendor());
        runTimeInstance.setSpecVersion(runtimeMXBean.getSpecVersion());
        runTimeInstance.setVmName(runtimeMXBean.getVmName());
        runTimeInstance.setVmVendor(runtimeMXBean.getVmVendor());
        runTimeInstance.setVmVersion(runtimeMXBean.getVmVersion());
        runTimeInstance.setInputArguments(StringUtils.join(runTimeInstance.getInputArguments(), ","));
        runTimeInstance.setClassPath(runtimeMXBean.getClassPath());
        runTimeInstance.setBootClassPath(runtimeMXBean.getBootClassPath());
        runTimeInstance.setLibraryPath(runTimeInstance.getLibraryPath());
        return runTimeInstance;
    }
}
