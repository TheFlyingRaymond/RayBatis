package com.raymond.exception;

public class RayReflectionException extends RuntimeException{
    public RayReflectionException() {
    }

    public RayReflectionException(String message) {
        super(message);
    }

    public RayReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RayReflectionException(Throwable cause) {
        super(cause);
    }

    public RayReflectionException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
