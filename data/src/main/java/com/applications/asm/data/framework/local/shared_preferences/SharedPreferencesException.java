package com.applications.asm.data.framework.local.shared_preferences;

public class SharedPreferencesException extends Exception {
    private final String message;

    public SharedPreferencesException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
