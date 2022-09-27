package com.applications.asm.places.view_model.exception;

import android.util.Log;

import com.applications.asm.domain.exception.UseCaseException;
import com.applications.asm.places.model.Resource;

public class ErrorUtils {
    public static <R,T> Resource<R> resolveError(Throwable throwable, Class<T> classException) {
        Resource<R> dataResource;
        Exception exception = (Exception) throwable;
        if(exception instanceof UseCaseException) {
            UseCaseException useCaseException = (UseCaseException) exception;
            Log.e(classException.getName(), useCaseException.getMessage());
            dataResource = Resource.error(useCaseException.getMessage());
        } else {
            Log.e(classException.getName(), exception.getMessage());
            dataResource = Resource.error(exception.getMessage());
        }
        return dataResource;
    }
}
