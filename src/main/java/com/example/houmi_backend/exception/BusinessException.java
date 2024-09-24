package com.example.houmi_backend.exception;

import com.example.houmi_backend.common.ErrorCode;

public class BusinessException extends RuntimeException{

    private final int code;
    private final String description;


    public BusinessException(int code,String message,String description)
    {
        super(message);
        this.code=code;
        this.description=description;
    }

    public BusinessException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.code=errorCode.getCode();
        this.description=errorCode.getDescription();
    }

    /**
     * 这个用的比较多
     * @param errorCode
     * @param description
     */
    public BusinessException(ErrorCode errorCode ,String description)
    {
        super(errorCode.getMessage());
        this.code=errorCode.getCode();
        this.description=description;
    }


    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
