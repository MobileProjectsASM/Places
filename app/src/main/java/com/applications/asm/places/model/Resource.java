package com.applications.asm.places.model;

import static com.applications.asm.places.model.ResourceStatus.ERROR;
import static com.applications.asm.places.model.ResourceStatus.LOADING;
import static com.applications.asm.places.model.ResourceStatus.SUCCESS;

public class
Resource<T> {
    private ResourceStatus status;
    private T data;
    private Integer message;

    private Resource(ResourceStatus status, T data, Integer message) {
        this.status = status;
        this.data = data;
        this.message = message;
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

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Integer message) {
        return new Resource<>(ERROR, null, message);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(LOADING, null, null);
    }
}
