package com.applications.asm.data.utils;

import com.apollographql.apollo3.api.Error;
import com.applications.asm.data.framework.local.database.DatabaseException;
import com.applications.asm.data.framework.local.hardware.HardwareException;
import com.applications.asm.data.framework.local.shared_preferences.SharedPreferencesException;
import com.applications.asm.data.framework.network.api_rest.RestException;
import com.applications.asm.data.framework.network.graphql.GraphqlException;
import com.applications.asm.domain.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class ErrorUtils {
    public static List<String> getErrors(List<Error> errors) {
        List<String> listErrors = new ArrayList<>();
        for(Error error: errors)
            listErrors.add(error.getMessage());
        return listErrors;
    }

    public static Exception resolveError(Throwable throwable) {
        Exception exception = (Exception) throwable;
        if(exception instanceof RestException) {
            RestException restException = (RestException) exception;
            return new RepositoryException(restException.getMessage());
        }
        if(exception instanceof GraphqlException) {
            GraphqlException graphqlException = (GraphqlException) exception;
            return new RepositoryException(graphqlException.getMessage());
        }
        if(exception instanceof SharedPreferencesException) {
            SharedPreferencesException sharedPreferencesException = (SharedPreferencesException) exception;
            return new RepositoryException(sharedPreferencesException.getMessage());
        }
        if(exception instanceof DatabaseException) {
            DatabaseException databaseException = (DatabaseException) exception;
            return new RepositoryException(databaseException.getMessage());
        }
        if(exception instanceof HardwareException) {
            HardwareException hardwareException = (HardwareException) exception;
            return new RepositoryException(hardwareException.getMessage());
        }
        return new RepositoryException(exception.getMessage());
    }
}
