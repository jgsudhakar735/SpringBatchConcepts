package com.jgsudhakar.springboot.batch.exception;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.exception.NameStartsWithException
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
public class NameStartsWithException extends BaseRuntimeException{

    /**
     * Default Serial ID
     */
    private static final long serialVersionUID = 1L;

    public NameStartsWithException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public NameStartsWithException(String errorCode,String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public NameStartsWithException(String errorCode,String errorMessage,Throwable throwable) {
        super(errorMessage,errorCode,throwable);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessage();
    }
}
