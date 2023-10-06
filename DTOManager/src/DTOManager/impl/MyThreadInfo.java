package DTOManager.impl;

public class MyThreadInfo {

    private int queueSize;

    private int workingThreads;

    private int finishedThread;

    private int threadPoolSize;

    public MyThreadInfo(int queueSize, int workingThreads, int finishedThread, int threadPoolSize) {
        this.queueSize = queueSize;
        this.workingThreads = workingThreads;
        this.finishedThread = finishedThread;
        this.threadPoolSize = threadPoolSize;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public int getWorkingThreads() {
        return workingThreads;
    }

    public int getFinishedThread() {
        return finishedThread;
    }
}
