package com.applications.asm.domain.use_cases.base;

import io.reactivex.rxjava3.core.Scheduler;

public class UseCaseScheduler {
    private Scheduler run;
    private Scheduler post;

    public UseCaseScheduler(Scheduler run, Scheduler post) {
        this.run = run;
        this.post = post;
    }

    public Scheduler getRun() {
        return run;
    }

    public void setRun(Scheduler run) {
        this.run = run;
    }

    public Scheduler getPost() {
        return post;
    }

    public void setPost(Scheduler post) {
        this.post = post;
    }
}
