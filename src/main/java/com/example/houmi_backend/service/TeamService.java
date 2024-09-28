package com.example.houmi_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.houmi_backend.model.domain.Team;
import com.example.houmi_backend.model.domain.User;
import com.example.houmi_backend.model.request.QueryTeamPageRequest;
import com.example.houmi_backend.model.vo.UserTeamVo;

import java.util.List;


/**
* @author ASUS
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-09-28 19:40:08
*/
public interface TeamService extends IService<Team> {
    /**
     * 推荐队伍
     * @param queryTeamPageRequest
     * @param currentUser
     * @return
     */
    List<UserTeamVo> recommendTeam(QueryTeamPageRequest queryTeamPageRequest, User currentUser);

    /**
     * 创建队伍
     * @param team
     * @param currentUser
     * @return
     */
    long createTeam(Team team,User currentUser);

    /**
     * 加入队伍
     * @param teamId
     * @param currentUser
     * @param password
     * @return
     */
    long joinTeam(Long teamId,User currentUser,String password);

    /**
     * 退出队伍
     * @param team
     * @param CurrentUser
     * @return
     */
    boolean outTeam(Team team,User CurrentUser);

    /**
     * 修改队伍信息
     * @param team
     * @param CurrentUser
     * @return
     */
    boolean editTeam(Team team,User CurrentUser);

    /**
     * 查询我创建的队伍
     * @param userId
     * @return
     */
    List<UserTeamVo> myCreateTeam(Long userId);

    /**
     * 查询我加入的队伍
     */
    List<UserTeamVo> myJoinTeam (Long id);





}
