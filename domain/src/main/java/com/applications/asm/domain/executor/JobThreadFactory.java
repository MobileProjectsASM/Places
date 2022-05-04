package com.applications.asm.domain.executor;

import java.util.concurrent.ThreadFactory;

public class JobThreadFactory implements ThreadFactory {
    private static int threadCounter = 0;

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "Thread_" + (threadCounter++) + " was created");
    }
}
