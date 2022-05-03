package com.applications.asm.domain.executor;

import io.reactivex.rxjava3.core.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}
