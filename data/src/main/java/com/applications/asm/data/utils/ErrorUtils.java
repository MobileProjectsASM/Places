package com.applications.asm.data.utils;

import android.util.Log;

import com.apollographql.apollo.api.Error;
import com.applications.asm.data.framework.local.database.DatabaseException;
import com.applications.asm.data.framework.local.hardware.HardwareException;
import com.applications.asm.data.framework.local.hardware.HardwareExceptionCodes;
import com.applications.asm.data.framework.local.shared_preferences.SharedPreferencesException;
import com.applications.asm.data.framework.local.shared_preferences.SharedPreferencesExceptionCodes;
import com.applications.asm.data.framework.network.api_rest.RestException;
import com.applications.asm.data.framework.network.api_rest.RestExceptionCodes;
import com.applications.asm.data.framework.network.api_rest.dto.APIError;
import com.applications.asm.data.framework.network.graphql.GraphqlException;
import com.applications.asm.data.framework.network.graphql.GraphqlExceptionCodes;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.exception.RepositoryException;
import com.applications.asm.domain.exception.RepositoryExceptionCodes;

import java.util.List;

public class ErrorUtils {

    public static <T> Response<T> getResponseError(Integer errorCode, APIError apiError) {
        String error = errorCode + ": " + apiError.error.code + ", " + apiError.error.description;
        return Response.error(error);
    }

    public static <T> Response<T> getResponseError(List<Error> errors) {
        Response<T> response;
        if (errors != null) response = Response.error(errors.get(0).getMessage());
        else response = Response.error("");
        return response;
    }

    public static <T> Exception resolveError(Throwable throwable, Class<T> classException) {
        Exception exception = (Exception) throwable;
        if(exception instanceof RestException) {
            RestException restException = (RestException) exception;
            Log.e(classException.getName(), restException.getMessage());
            return getRepositoryException(restException);
        }
        if(exception instanceof GraphqlException) {
            GraphqlException graphqlException = (GraphqlException) exception;
            Log.e(classException.getName(), graphqlException.getMessage());
            return getRepositoryException(graphqlException);
        }
        if(exception instanceof SharedPreferencesException) {
            SharedPreferencesException sharedPreferencesException = (SharedPreferencesException) exception;
            Log.e(classException.getName(), sharedPreferencesException.getMessage());
            return getRepositoryException(sharedPreferencesException);
        }
        if(exception instanceof DatabaseException) {
            DatabaseException databaseException = (DatabaseException) exception;
            Log.e(classException.getName(), databaseException.getMessage());
            return getRepositoryException(databaseException);
        }
        if(exception instanceof HardwareException) {
            HardwareException hardwareException = (HardwareException) exception;
            Log.e(classException.getName(), hardwareException.getMessage());
            return getRepositoryException(hardwareException);
        }
        Log.e(classException.getName(), exception.getMessage());
        return new RepositoryException(RepositoryExceptionCodes.REPOSITORY_ERROR, exception.getMessage());
    }

    public static RepositoryException getRepositoryException(GraphqlException graphqlException) {
        Integer codeException = graphqlException.getCode();
        if(codeException.equals(GraphqlExceptionCodes.NETWORK_ERROR)) return new RepositoryException(RepositoryExceptionCodes.PARSE_DATA, graphqlException.getMessage());
        else return new RepositoryException(RepositoryExceptionCodes.REPOSITORY_ERROR, graphqlException.getMessage());
    }

    public static RepositoryException getRepositoryException(RestException restException) {
        Integer codeException = restException.getCode();
        if(codeException.equals(RestExceptionCodes.NETWORK_CONNECTION)) return new RepositoryException(RepositoryExceptionCodes.NETWORK_CONNECTION, restException.getMessage());
        else return new RepositoryException(RepositoryExceptionCodes.PARSE_DATA, restException.getMessage());
    }

    public static RepositoryException getRepositoryException(SharedPreferencesException sharedPreferencesException) {
        Integer codeException = sharedPreferencesException.getCode();
        if(codeException.equals(SharedPreferencesExceptionCodes.NO_LOCATION_IN_PREFERENCES)) return new RepositoryException(RepositoryExceptionCodes.NO_DATA_IN_MEMORY, sharedPreferencesException.getMessage());
        else return new RepositoryException(RepositoryExceptionCodes.REPOSITORY_ERROR, sharedPreferencesException.getMessage());
    }

    public static RepositoryException getRepositoryException(HardwareException hardwareException) {
        Integer codeException = hardwareException.getCode();
        if(codeException.equals(HardwareExceptionCodes.GPS_DISABLED)) return new RepositoryException(RepositoryExceptionCodes.GPS_DISABLED, hardwareException.getMessage());
        else if(codeException.equals(HardwareExceptionCodes.LOCATION_UNREGISTERED)) return new RepositoryException(RepositoryExceptionCodes.LOCATION_UNREGISTERED, hardwareException.getMessage());
        else return new RepositoryException(RepositoryExceptionCodes.REPOSITORY_ERROR, hardwareException.getMessage());
    }

    public static RepositoryException getRepositoryException(DatabaseException databaseException) {
        return new RepositoryException(RepositoryExceptionCodes.REPOSITORY_ERROR, databaseException.getMessage());
    }
}
