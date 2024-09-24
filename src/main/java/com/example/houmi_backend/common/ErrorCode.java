package com.example.houmi_backend.common;

public enum ErrorCode {
    SUCCESS(2000,"ok","操作成功"),
    PARAM_ERROR(4000,"请求参数错误",""),
    NULL_ERROR(4001,"请求数据为空",""),
    NO_LOGIN(4010,"未登录",""),
    NO_AUTH(4011,"没有权限",""),
    SYSTEM_ERROR(5000,"系统内部错误","")
    ;


    private  int code;
    private  String message;
    private  String description;


    ErrorCode(int code,String message,String description)
    {
        this.code=code;
        this.message=message;
        this.description=description;
    }

    public int getCode() {
        return code;
    }
    public String getMessage()
    {
        return message;
    }
    public String getDescription()
    {
        return description;
    }


}
