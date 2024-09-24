package com.example.houmi_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.houmi_backend.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;


/**
 * @author ASUS
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2024-09-24 13:25:52
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long UserRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户脱敏
     * @param origin
     * @return
     */
    User getSafetyUser (User origin);

    /**
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

}
