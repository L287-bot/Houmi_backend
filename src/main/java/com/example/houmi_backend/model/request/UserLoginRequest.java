package com.example.houmi_backend.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5117103993138897105L;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户密码
     */
    private String userPassword;


}
