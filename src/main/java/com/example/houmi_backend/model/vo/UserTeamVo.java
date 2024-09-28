package com.example.houmi_backend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 用户队伍联合返回给前端的类
 */
@Data
public class UserTeamVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 队伍名字
     */
    private String name;

    /**
     * 队伍人数上限
     */
    private Integer MaxUser;
    /**
     * 队伍状态  0-公开 1-私密 2-加密
     */
    private Integer status;
    /**
     * 队伍密码
     */
    private  String password;
    /**
     * 队长id
     */
    private  Long  userId;
    /**
     * 队伍创建者id
     */
    private Long createUserId;
    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Data expirationTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Data createTime;

    /**
     * 已经加入队伍人数
     */

    private  Integer hasJoinNumber;

    /**
     * 是否已加入队伍
     */
    private Boolean isSaveTeam;




}
