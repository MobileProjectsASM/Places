package com.applications.asm.domain.use_cases.base;

public abstract class UseCase<Stream, Params> {

    protected abstract Stream build(Params params);

    public final Stream execute(Params params) {
        return execute(params, false);
    }

    public final Stream executeFromAnotherUseCase(Params params) {
        return execute(params, true);
    }

    protected Stream execute(Params params, Boolean fromUseCase) {
        return build(params);
    }
}
