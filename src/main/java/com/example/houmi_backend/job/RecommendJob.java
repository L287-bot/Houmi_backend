//package com.example.houmi_backend.job;
//
//
//import cn.hutool.json.JSONUtil;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.example.houmi_backend.mapper.UserMapper;
//import com.example.houmi_backend.model.domain.User;
//import com.example.houmi_backend.service.UserService;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static com.mi.usercenter.constant.UserConstant.RECOMMEND_USER;
//
///**
// * @author mi11
// * @version 1.0
// * @project user-center
// * @description 定时更新推荐
// * @ClassName RecommendUserJop
// */
//@Component
//@Slf4j
//public  class RecommendJob {
//
//    private final Integer[] userIds = {4,5,6,7};
//
//    @Resource
//    private RedissonClient redissonClient;
//
//    @Resource
//    private UserService userService;
//
//    @Resource
//    private UserMapper userMapper;
//
//    @Resource
//    private RedisTemplate<String,Object> redisTemplate;
//    /**
//     * 每日24点更新
//     */
//    @Scheduled(cron = "0 58 23 * * ?")
//    public  void recommendUser(){
//        RLock lock = redissonClient.getLock("yupao:user:recommend");
//        try {
//            if (lock.tryLock(0,-1, TimeUnit.MILLISECONDS)){
//                log.info("开始执行条数:{}",userIds.length);
//                StopWatch stopWatch = new StopWatch();
//                stopWatch.start();
//                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//                List<User> users = userMapper.selectBatchIds(Arrays.asList(userIds));
//                for (int i = 0; i < users.size(); i++) {
//                    try {
//                        User user = users.get(i);
//                        String tagsStr = user.getTags();
//                        List<String> tags = JSONUtil.toList(tagsStr, String.class);
//                        queryWrapper.clear();
//                        tags.forEach(item -> queryWrapper.like("tags",item));
//                        Page<User> userPage = userMapper.selectPage(new Page<>(1, 8), queryWrapper);
//                        String recommendKey = String.format(RECOMMEND_USER+"%s",user.getId());
//                        redisTemplate.opsForValue().set(recommendKey,JSONUtil.toJsonStr(userPage),1, TimeUnit.DAYS);
//                    } catch (Exception e) {
//                        log.error("jop recommend error:}",e);
//                    }
//                }
//                stopWatch.stop();
//                log.info("执行成功耗时:{}",stopWatch.getTotalTimeMillis());
//            }
//        } catch (InterruptedException e) {
//            log.error("user-recommend job error: "+ e.getMessage());
//        }finally {
//            if (lock.isHeldByCurrentThread ()){
//                lock.unlock();
//            }
//        }
//    }
//}
