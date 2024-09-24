package com.example.houmi_backend.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    private Long id;

    private String username;

    private String userAccount;

    private String userPassword;

    private String avatarUrl;

    private Integer gender;

    private String phone;

    private String email;

    private Integer userRole;

    private Date creatTime;

    private Date updateTIme;

    private Integer isDelete;

    private String tags;

    private static final long serialVersionUID = 1L;
}