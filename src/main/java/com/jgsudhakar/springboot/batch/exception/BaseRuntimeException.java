package com.jgsudhakar.springboot.batch.exception;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.exception.BaseRuntimeException
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
public class BaseRuntimeException extends RuntimeException{

    /**
     * Default Serial ID
     */
    private static final long serialVersionUID = 1L;

    protected String errorMessage;

    protected String errorCode;

    public BaseRuntimeException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BaseRuntimeException(String errorCode,String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(Throwable throwable){
        super(throwable);
        this.errorMessage = throwable.getMessage();
    }

    public BaseRuntimeException(String errorMessage,Throwable throwable){
        super(errorMessage,throwable);
        this.errorMessage = errorMessage;
    }

    public BaseRuntimeException(String errorMessage,String errorCode, Throwable throwable){
        super(errorMessage,throwable);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }
}
