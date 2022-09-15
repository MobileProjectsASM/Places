package com.applications.asm.data.framework.local.shared_preferences;

public class SharedPreferencesException extends Exception {
    private final Integer code;
    private final String message;

    public SharedPreferencesException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() { return code; }
}
