package com.example.houmi_backend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
    @TableId(type = IdType.AUTO)
    private long id;
    private  String profile;
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
    @TableLogic
    private Integer isDelete;

    private String tags;

    private static final long serialVersionUID = 1L;
}