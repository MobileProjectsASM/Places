package com.applications.asm.data.framework.local.hardware;

public class HardwareException extends Exception {
    private final String message;

    public HardwareException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
