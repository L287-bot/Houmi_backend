package com.example.houmi_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.houmi_backend.common.BaseResponse;
import com.example.houmi_backend.common.ErrorCode;
import com.example.houmi_backend.common.ResultUtils;
import com.example.houmi_backend.exception.BusinessException;
import com.example.houmi_backend.model.domain.User;
import com.example.houmi_backend.model.request.UserLoginRequest;
import com.example.houmi_backend.model.request.UserRegisterRequest;
import com.example.houmi_backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.houmi_backend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
@CrossOrigin("https://localhost:5173/")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest)
    {
        if (userRegisterRequest==null)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"数据为空");

        }
        String userAccount=userRegisterRequest.getUserAccount();
        String userPassword=userRegisterRequest.getUserPassword();
        String checkPassword=userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword))
        {

            return ResultUtils.error(ErrorCode.PARAM_ERROR,"参数为空");
        }
        long result=userService.UserRegister(userAccount,userPassword,checkPassword);
        return ResultUtils.success(result);
    }

@GetMapping("/search/tags")
public BaseResponse<List<User>> searchUserByTags(@RequestParam(required = false) List<String> tagNameList)
{
if (CollectionUtils.isEmpty(tagNameList))
{
throw new BusinessException(ErrorCode.PARAM_ERROR,"标签列表为空");

}
List<User> userList=userService.searchByTag(tagNameList);
//在service层脱敏过的
return ResultUtils.success(userList);
}



    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request)
    {
        if(userLoginRequest==null)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }
        String userAccount=userLoginRequest.getUserAccount();
        String userPassword=userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword))
        {
            return  ResultUtils.error(ErrorCode.PARAM_ERROR,"参数为空");
        }

        User user=userService.userLogin(userAccount,userPassword,request);
        if (user==null)
        {
            return ResultUtils.error(ErrorCode.NULL_ERROR,"用户名或密码错误，登录失败");
        }
        return ResultUtils.success(user);
    }

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogOut(HttpServletRequest request)
    {
        if (request==null)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }
        int result=userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 得到当前用户对应前端个人呢页
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request)
    {
        if (request==null)
        {
            throw  new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }
        Object userObj=request.getSession().getAttribute(USER_LOGIN_STATE);
        User user=(User) userObj;
        if (user==null)
        {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        long id=user.getId();
        User safetyUser=userService.getSafetyUser(userService.getById(id));
        return ResultUtils.success(safetyUser);
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @param request
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username ,HttpServletRequest request)
    {

        if(!userService.isAdmin(request))
        {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        if(StringUtils.isNotBlank(username))
        {
            queryWrapper.eq("username",username);

        }
        List<User> userList=userService.list(queryWrapper);
        System.out.println(userList);
        if (userList.size()>0)
        {
            //集合中的每一个用户脱敏
            List<User> list=userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
            return  ResultUtils.success(list);
        }
        return ResultUtils.error(ErrorCode.NULL_ERROR,"用户不存在，请检查输入名");

    }

    /**
     * 修改用户信息
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Integer> updateUser(@RequestBody User user,HttpServletRequest request)
    {  //可以吧鉴权写在这，但没必要
        if (user==null)
        {
            throw  new BusinessException(ErrorCode.PARAM_ERROR);
        }
        User loginUser=userService.getLoginUser(request);
        int result=userService.updateUser(user,loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 删除用户信息
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public  BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request)
    {
        if (!userService.isAdmin(request))
        {
            throw  new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id<=0)
        {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        boolean result =userService.removeById(id);
        return ResultUtils.success(result);
    }

    @GetMapping("/recommend")
    public BaseResponse<List<User>> recommendUsers(HttpServletRequest request)
    {
        QueryWrapper queryWrapper=new QueryWrapper<>();
        List<User> userList=userService.list(queryWrapper);
        List<User> list=userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }







}
