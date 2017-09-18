package com.junlong.windseeker.server;

import java.util.List;

/**
 * JVM整体统计信息
 * Created by niujunlong on 17/9/15.
 */
public class JvmEntity {
    private RunTimeEntity runTimeEntity;
    private ClassLoadEntity classLoadEntity;
    private CompilationEntity compilationEntity;
    private List<GarbageCollectorsEntity> garbageCollectorsEntityList;
    private List<MemoryManagersEntity> memoryManagersEntityList;
    private MemoryEntity memoryEntity;
    private OperatingSystemEntity operatingSystemEntity;
    private ThreadCountEntity threadCountEntity;


    public RunTimeEntity getRunTimeInstance(){
       return this.new RunTimeEntity();
    }
    public ClassLoadEntity getClassLoadInstance(){
        return this.new ClassLoadEntity();
    }
    public CompilationEntity getCompilationInstance(){
        return this.new CompilationEntity();
    }
    public GarbageCollectorsEntity getGarbageCollectorsInstance(){
        return this.new GarbageCollectorsEntity();
    }
    public MemoryManagersEntity getMemoryManagersInstance(){
        return this.new MemoryManagersEntity();
    }
    public MemoryEntity getMemoryInstance(){
        return this.new MemoryEntity();
    }
    public OperatingSystemEntity getOperatingInstance(){
        return this.new OperatingSystemEntity();
    }
    public ThreadCountEntity getThreadCountInstance(){
        return this.new ThreadCountEntity();
    }

    public RunTimeEntity getRunTimeEntity() {
        return runTimeEntity;
    }

    public void setRunTimeEntity(RunTimeEntity runTimeEntity) {
        this.runTimeEntity = runTimeEntity;
    }

    public ClassLoadEntity getClassLoadEntity() {
        return classLoadEntity;
    }

    public void setClassLoadEntity(ClassLoadEntity classLoadEntity) {
        this.classLoadEntity = classLoadEntity;
    }

    public CompilationEntity getCompilationEntity() {
        return compilationEntity;
    }

    public void setCompilationEntity(CompilationEntity compilationEntity) {
        this.compilationEntity = compilationEntity;
    }

    public List<GarbageCollectorsEntity> getGarbageCollectorsEntityList() {
        return garbageCollectorsEntityList;
    }

    public void setGarbageCollectorsEntityList(List<GarbageCollectorsEntity> garbageCollectorsEntityList) {
        this.garbageCollectorsEntityList = garbageCollectorsEntityList;
    }

    public List<MemoryManagersEntity> getMemoryManagersEntityList() {
        return memoryManagersEntityList;
    }

    public void setMemoryManagersEntityList(List<MemoryManagersEntity> memoryManagersEntityList) {
        this.memoryManagersEntityList = memoryManagersEntityList;
    }

    public MemoryEntity getMemoryEntity() {
        return memoryEntity;
    }

    public void setMemoryEntity(MemoryEntity memoryEntity) {
        this.memoryEntity = memoryEntity;
    }

    public OperatingSystemEntity getOperatingSystemEntity() {
        return operatingSystemEntity;
    }

    public void setOperatingSystemEntity(OperatingSystemEntity operatingSystemEntity) {
        this.operatingSystemEntity = operatingSystemEntity;
    }

    public ThreadCountEntity getThreadCountEntity() {
        return threadCountEntity;
    }

    public void setThreadCountEntity(ThreadCountEntity threadCountEntity) {
        this.threadCountEntity = threadCountEntity;
    }

    public class ThreadCountEntity{
        private int count;
        private int daemonCount;
        private int liveCount;
        private long startedCount;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getDaemonCount() {
            return daemonCount;
        }

        public void setDaemonCount(int daemonCount) {
            this.daemonCount = daemonCount;
        }

        public int getLiveCount() {
            return liveCount;
        }

        public void setLiveCount(int liveCount) {
            this.liveCount = liveCount;
        }

        public long getStartedCount() {
            return startedCount;
        }

        public void setStartedCount(long startedCount) {
            this.startedCount = startedCount;
        }
    }

    public class OperatingSystemEntity{
        private String os;
        private String arch;
        private int processorsCount;
        private double loadAverage;
        private String version;

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getArch() {
            return arch;
        }

        public void setArch(String arch) {
            this.arch = arch;
        }

        public int getProcessorsCount() {
            return processorsCount;
        }

        public void setProcessorsCount(int processorsCount) {
            this.processorsCount = processorsCount;
        }

        public double getLoadAverage() {
            return loadAverage;
        }

        public void setLoadAverage(double loadAverage) {
            this.loadAverage = loadAverage;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public class MemoryEntity{
        private long headMemoryCommitted;
        private long headMemoryInit;
        private long headMemoryMax;
        private long headMemoryUsed;

        private long noheadMemoryCommitted;
        private long noheadMemoryInit;
        private long noheadMemoryMax;
        private long noheadMemoryUsed;

        private long pendingFinalizeCount;

        public long getHeadMemoryCommitted() {
            return headMemoryCommitted;
        }

        public void setHeadMemoryCommitted(long headMemoryCommitted) {
            this.headMemoryCommitted = headMemoryCommitted;
        }

        public long getHeadMemoryInit() {
            return headMemoryInit;
        }

        public void setHeadMemoryInit(long headMemoryInit) {
            this.headMemoryInit = headMemoryInit;
        }

        public long getHeadMemoryMax() {
            return headMemoryMax;
        }

        public void setHeadMemoryMax(long headMemoryMax) {
            this.headMemoryMax = headMemoryMax;
        }

        public long getHeadMemoryUsed() {
            return headMemoryUsed;
        }

        public void setHeadMemoryUsed(long headMemoryUsed) {
            this.headMemoryUsed = headMemoryUsed;
        }

        public long getNoheadMemoryCommitted() {
            return noheadMemoryCommitted;
        }

        public void setNoheadMemoryCommitted(long noheadMemoryCommitted) {
            this.noheadMemoryCommitted = noheadMemoryCommitted;
        }

        public long getNoheadMemoryInit() {
            return noheadMemoryInit;
        }

        public void setNoheadMemoryInit(long noheadMemoryInit) {
            this.noheadMemoryInit = noheadMemoryInit;
        }

        public long getNoheadMemoryMax() {
            return noheadMemoryMax;
        }

        public void setNoheadMemoryMax(long noheadMemoryMax) {
            this.noheadMemoryMax = noheadMemoryMax;
        }

        public long getNoheadMemoryUsed() {
            return noheadMemoryUsed;
        }

        public void setNoheadMemoryUsed(long noheadMemoryUsed) {
            this.noheadMemoryUsed = noheadMemoryUsed;
        }

        public long getPendingFinalizeCount() {
            return pendingFinalizeCount;
        }

        public void setPendingFinalizeCount(long pendingFinalizeCount) {
            this.pendingFinalizeCount = pendingFinalizeCount;
        }
    }


    public class MemoryManagersEntity{
        private String name;
        private String[] value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getValue() {
            return value;
        }

        public void setValue(String[] value) {
            this.value = value;
        }
    }


    public class GarbageCollectorsEntity{
        private String psScavenge;
        private String psMarkSweep;

        public String getPsScavenge() {
            return psScavenge;
        }

        public void setPsScavenge(String psScavenge) {
            this.psScavenge = psScavenge;
        }

        public String getPsMarkSweep() {
            return psMarkSweep;
        }

        public void setPsMarkSweep(String psMarkSweep) {
            this.psMarkSweep = psMarkSweep;
        }
    }


    public class CompilationEntity{
        private String name;
        private String totalCompileTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTotalCompileTime() {
            return totalCompileTime;
        }

        public void setTotalCompileTime(String totalCompileTime) {
            this.totalCompileTime = totalCompileTime;
        }
    }


    /**
     * 类加载信息
     */
    public class ClassLoadEntity{
        private int loadedClassCount;
        private long totalLoadedClassCount;
        private long unloadedClassCount;
        private Boolean verbose;

        public int getLoadedClassCount() {
            return loadedClassCount;
        }

        public void setLoadedClassCount(int loadedClassCount) {
            this.loadedClassCount = loadedClassCount;
        }

        public long getTotalLoadedClassCount() {
            return totalLoadedClassCount;
        }

        public void setTotalLoadedClassCount(long totalLoadedClassCount) {
            this.totalLoadedClassCount = totalLoadedClassCount;
        }

        public long getUnloadedClassCount() {
            return unloadedClassCount;
        }

        public void setUnloadedClassCount(long unloadedClassCount) {
            this.unloadedClassCount = unloadedClassCount;
        }

        public Boolean getVerbose() {
            return verbose;
        }

        public void setVerbose(Boolean verbose) {
            this.verbose = verbose;
        }
    }
    /**
     * 运行时信息
     */
    public class RunTimeEntity{
        /**
         * 机器名
         */
        private String MachineName;
        /**
         * jvm启动时间
         */
        private String JvmStartTime;
        /**
         * 接口规范版本
         */
        private String managementSpecVersion;

        private String specName;
        private String specVendor;
        private String specVersion;
        private String vmName;
        private String vmVendor;
        private String vmVersion;
        private String inputArguments;
        private String classPath;
        private String bootClassPath;
        private String libraryPath;

        public String getMachineName() {
            return MachineName;
        }

        public void setMachineName(String machineName) {
            MachineName = machineName;
        }

        public String getJvmStartTime() {
            return JvmStartTime;
        }

        public void setJvmStartTime(String jvmStartTime) {
            JvmStartTime = jvmStartTime;
        }

        public String getManagementSpecVersion() {
            return managementSpecVersion;
        }

        public void setManagementSpecVersion(String managementSpecVersion) {
            this.managementSpecVersion = managementSpecVersion;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public String getSpecVendor() {
            return specVendor;
        }

        public void setSpecVendor(String specVendor) {
            this.specVendor = specVendor;
        }

        public String getSpecVersion() {
            return specVersion;
        }

        public void setSpecVersion(String specVersion) {
            this.specVersion = specVersion;
        }

        public String getVmName() {
            return vmName;
        }

        public void setVmName(String vmName) {
            this.vmName = vmName;
        }

        public String getVmVendor() {
            return vmVendor;
        }

        public void setVmVendor(String vmVendor) {
            this.vmVendor = vmVendor;
        }

        public String getVmVersion() {
            return vmVersion;
        }

        public void setVmVersion(String vmVersion) {
            this.vmVersion = vmVersion;
        }

        public String getInputArguments() {
            return inputArguments;
        }

        public void setInputArguments(String inputArguments) {
            this.inputArguments = inputArguments;
        }

        public String getClassPath() {
            return classPath;
        }

        public void setClassPath(String classPath) {
            this.classPath = classPath;
        }

        public String getBootClassPath() {
            return bootClassPath;
        }

        public void setBootClassPath(String bootClassPath) {
            this.bootClassPath = bootClassPath;
        }

        public String getLibraryPath() {
            return libraryPath;
        }

        public void setLibraryPath(String libraryPath) {
            this.libraryPath = libraryPath;
        }
    }


}
