package com.applications.asm.data.exception;

import com.applications.asm.data.model.Error;

public class ClientException extends WebServiceException {
    private Error error;

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Error error) {
        super("");
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
