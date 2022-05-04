package com.applications.asm.places.di.modules;

import com.applications.asm.domain.executor.JobExecutor;
import com.applications.asm.domain.executor.JobThreadFactory;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.places.UiThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ThreadModule {

    @Singleton
    @Provides
    PostExecutionThread providePostExecutionThread() {
        return new UiThread();
    }

    @Singleton
    @Provides
    ThreadExecutor provideThreadExecutor(Executor executor) {
        return new JobExecutor(executor);
    }

    @Provides
    Executor provideThreadPoolExecutor(BlockingQueue<Runnable> queue, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(8, 12, 2, TimeUnit.SECONDS, queue, threadFactory);
    }

    @Provides
    BlockingQueue<Runnable> provideBlockingQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Provides
    ThreadFactory provideThreadFactory() {
        return new JobThreadFactory();
    }
}
