package com.raymond.raybatis.raybatis.exception;

public class RayConfigBindingException extends RuntimeException{
    public RayConfigBindingException() {
    }

    public RayConfigBindingException(String message) {
        super(message);
    }

    public RayConfigBindingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RayConfigBindingException(Throwable cause) {
        super(cause);
    }

    public RayConfigBindingException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
