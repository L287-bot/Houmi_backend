package com.example.houmi_backend.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2796685921204766771L;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 校验码
     */
    private  String checkPassword;


}
