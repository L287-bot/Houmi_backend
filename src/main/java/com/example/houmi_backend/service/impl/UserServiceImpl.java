package com.example.houmi_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.houmi_backend.common.ErrorCode;
import com.example.houmi_backend.exception.BusinessException;
import com.example.houmi_backend.mapper.UserMapper;
import com.example.houmi_backend.model.domain.User;
import com.example.houmi_backend.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.houmi_backend.constant.UserConstant.ADMIN_ROLE;
import static com.example.houmi_backend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author ASUS
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-09-24 13:25:52
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;


    /**
     * 盐值
     */
    private static final String  SALT="lsc";




    @Override
    public long UserRegister(String userAccount, String userPassword, String checkPassword) {
        if(StringUtils.isAnyBlank(userAccount,userAccount,checkPassword))
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }
        if(userAccount.length()<4)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号长度过短");
        }
        if(userPassword.length()<8||checkPassword.length()<8)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"密码长度过短");

        }
//账号不能存在非法字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher= Pattern.compile(validRule).matcher(userAccount);
        if(matcher.find())
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号存在非法字符");
        }
        if (!userPassword.equals(checkPassword))
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"两次输入密码不一致");
        }

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        Long count=userMapper.selectCount(queryWrapper);
        if(count>0)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号重复");
        }

        String encryptPassword= DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean result=this.save(user);
        if(!result)
        {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统错误，用户注册失败");
        }
        return  user.getId();
    }

    @Override
    public User getSafetyUser(User origin) {
        //用户脱敏
        User user = new User();
        user.setId(origin.getId());
        user.setUsername(origin.getUsername());
        user.setUserAccount(origin.getUserAccount());
        user.setAvatarUrl(origin.getAvatarUrl());
        user.setGender(origin.getGender());
        user.setPhone(origin.getPhone());
        user.setEmail(origin.getEmail());
        user.setUserRole(origin.getUserRole());
        user.setCreatTime(origin.getCreatTime());
        return user;
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAnyBlank(userAccount,userPassword))
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }
        if(userAccount.length()<4)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号长度过短");
        }
        if(userPassword.length()<8)
        {
            throw  new BusinessException(ErrorCode.PARAM_ERROR,"密码长度过短");
        }

//账号不能存在非法字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher= Pattern.compile(validRule).matcher(userAccount);
        if(matcher.find())
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号存在非法字符");
        }
        String encryptPassword=DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user=userMapper.selectOne(queryWrapper);
        if(user==null)
        {
            log.info("login failed,userAccount can not match userPassword");
            return null;
        }
        User safetyUser=getSafetyUser(user);
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        return safetyUser;

    }

    @Override
    public int userLogout(HttpServletRequest request)
    {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        Object userObj=request.getSession().getAttribute(USER_LOGIN_STATE);
        User user=(User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

    @Override
    public boolean isAdmin(User loginUser) {

        return loginUser.getUserRole() == ADMIN_ROLE && loginUser != null;
    }

    @Override
    public int  updateUser(User user, User loginUser) {
        long userId=user.getId();
        if(userId<=0)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        if (!isAdmin(loginUser)&&userId!=loginUser.getId())
        {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User oldUser=userMapper.selectById(userId);
        if(oldUser==null)
        {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return userMapper.updateById(user);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request==null)
        {
            throw  new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Object userObj=request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj==null)
        {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        return (User)userObj;
    }
}




