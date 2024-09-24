package com.example.houmi_backend.common;

public class ResultUtils {

    /**
     * SUCCESS
     * @param data
     * @return
     * @param <T>
     */
    public  static <T> BaseResponse<T> success(T data)
    {
        return new BaseResponse<>(200,data,"ok","操作成功");
    }

    /**
     * ERROR
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static  BaseResponse error(int code,String message,String description)
    {
        return new BaseResponse<>(code,null,message,description);
    }

    public  static  BaseResponse error(ErrorCode errorCode)
    {
        return new BaseResponse<>(errorCode);
    }



    public static  BaseResponse error(ErrorCode errorCode,String message,String description)
    {
        return new BaseResponse<>(errorCode.getCode(),null,message,description);
    }
    public static  BaseResponse error(ErrorCode errorCode,String description)

    {
        return new BaseResponse<>(errorCode.getCode(),null,errorCode.getMessage(),description);
    }




}
