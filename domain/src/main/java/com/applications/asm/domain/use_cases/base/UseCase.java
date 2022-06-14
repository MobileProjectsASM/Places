package com.applications.asm.domain.use_cases.base;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class UseCase<Stream, Params> {
    private static final Logger logger = Logger.getLogger("com.applications.asm.domain.use_cases.base.UseCase");

    protected abstract Stream build(Params params);

    public final Stream execute(Params params) {
        return execute(params, false);
    }

    public final Stream executeFromAnotherUseCase(Params params) {
        return execute(params, true);
    }

    protected Stream execute(Params params, Boolean fromUseCase) {
        logger.log(Level.INFO, getClass().getName() + ": " + params);
        return build(params);
    }
}
