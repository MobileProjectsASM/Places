package com.applications.asm.data.framework.local.hardware;

public class HardwareException extends Exception {
    private final Integer code;
    private final String message;

    public HardwareException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
