package com.applications.asm.domain.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorUtils {
    public static <T> Exception resolveError(Logger logger, Throwable throwable, Class<T> classException) {
        Exception exception = (Exception) throwable;
        if(exception instanceof ParameterException) {
            ParameterException parameterException = (ParameterException) exception;
            logger.log(Level.SEVERE, classException.getName() + ": " + parameterException.getMessage());
            return new UseCaseException(UseCaseExceptionCodes.PARAMETERS_ERROR, parameterException.getMessage());
        }
        if(exception instanceof RepositoryException) {
            RepositoryException repositoryException = (RepositoryException) exception;
            logger.log(Level.SEVERE, classException.getName() + ": " + repositoryException.getMessage());
            return getUseCaseException(repositoryException);
        }
        logger.log(Level.SEVERE, exception.getMessage());
        return new UseCaseException(UseCaseExceptionCodes.USE_CASE_ERROR, exception.getMessage());
    }

    public static UseCaseException getUseCaseException(RepositoryException repositoryException) {
        Integer code;
        if(repositoryException.getCode().equals(RepositoryExceptionCodes.GPS_DISABLED)) code = UseCaseExceptionCodes.GPS_DISABLED;
        else if(repositoryException.getCode().equals(RepositoryExceptionCodes.LOCATION_UNREGISTERED)) code = UseCaseExceptionCodes.LOCATION_UNREGISTERED;
        else if(repositoryException.getCode().equals(RepositoryExceptionCodes.PARSE_DATA)) code = UseCaseExceptionCodes.PARSE_DATA;
        else if(repositoryException.getCode().equals(RepositoryExceptionCodes.NETWORK_CONNECTION)) code = UseCaseExceptionCodes.NETWORK_CONNECTION;
        else if(repositoryException.getCode().equals(RepositoryExceptionCodes.NO_DATA_IN_MEMORY)) code = UseCaseExceptionCodes.NO_DATA_IN_MEMORY;
        else code = UseCaseExceptionCodes.USE_CASE_ERROR;
        return new UseCaseException(code, repositoryException.getMessage());
    }
}
