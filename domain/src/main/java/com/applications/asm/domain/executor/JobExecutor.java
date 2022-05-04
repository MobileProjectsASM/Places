package com.applications.asm.domain.executor;

import java.util.concurrent.Executor;

public class JobExecutor implements ThreadExecutor {
    private final Executor executor;

    public JobExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
