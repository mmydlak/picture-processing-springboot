package com.example.imageprocessing.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class PictureNotFoundException extends Exception {

    public PictureNotFoundException() {
        super();
    }

    public PictureNotFoundException(String message) {
        super(message);
    }

    public PictureNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PictureNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PictureNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
