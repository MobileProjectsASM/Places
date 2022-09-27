package com.applications.asm.places.model;

import static com.applications.asm.places.model.ResourceStatus.ERROR;
import static com.applications.asm.places.model.ResourceStatus.LOADING;
import static com.applications.asm.places.model.ResourceStatus.SUCCESS;
import static com.applications.asm.places.model.ResourceStatus.WARNING;

import java.util.List;

public class
Resource<T> {
    private ResourceStatus status;
    private T data;
    private String warning;
    private String errorMessage;

    private Resource(ResourceStatus status, T data, String warning, String errorMessage) {
        this.status = status;
        this.data = data;
        this.warning = warning;
        this.errorMessage = errorMessage;
    }

    public ResourceStatus getStatus() {
        return status;
    }

    public void setStatus(ResourceStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(SUCCESS, data, null, null);
    }

    public static <T> Resource<T> warning(String warning) {
        return new Resource<>(WARNING, null, warning, null);
    }

    public static <T> Resource<T> error(String message) {
        return new Resource<>(ERROR, null, null, message);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(LOADING, null, null, null);
    }
}
