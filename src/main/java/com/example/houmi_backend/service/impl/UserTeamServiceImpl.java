package com.example.houmi_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.houmi_backend.mapper.UserTeamMapper;
import com.example.houmi_backend.model.domain.UserTeam;

import com.example.houmi_backend.service.UserTeamService;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-09-29 00:09:36
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService {

}




