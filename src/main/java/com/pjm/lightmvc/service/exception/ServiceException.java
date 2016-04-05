package com.pjm.lightmvc.service.exception;

/**
 * Created by PanJM on 2016/3/30.
 * Service层公用的Exception.
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 4641019032496065864L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
