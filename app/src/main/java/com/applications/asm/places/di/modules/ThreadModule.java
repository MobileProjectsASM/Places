package com.applications.asm.places.di.modules;

import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Module
public class ThreadModule {

    @Singleton
    @Provides
    UseCaseScheduler provideUseCaseScheduler(
        @Named("scheduler_run") Scheduler run,
        @Named("scheduler_post") Scheduler post
    ) {
        return new UseCaseScheduler(
            run,
            post
        );
    }

    @Named("scheduler_run")
    @Provides
    Scheduler provideSchedulerRun() {
        return Schedulers.io();
    }

    @Named("scheduler_post")
    @Provides
    Scheduler provideSchedulerPost() {
        return AndroidSchedulers.mainThread();
    }
}
