package com.example.springbootdouy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springbootdouy.dao.UserDao;
import com.example.springbootdouy.entity.Follow;
import com.example.springbootdouy.mapper.FollowMapper;
import com.example.springbootdouy.service.IFollowService;
import com.example.springbootdouy.until.JWTUtils;
import com.example.springbootdouy.until.RelationResult;
import com.example.springbootdouy.until.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/douyin/relation")
public class RelationController {
    @Resource
    IFollowService iFollowService;
    @Resource
    FollowMapper followMapper;


    @RequestMapping("/follow/list")
    public RelationResult relation(String user_id, String token){
        System.out.println("关注列表方法接收到的用户id："+user_id);
        if (Integer.parseInt(user_id)==0){
            UserDao userDao = JWTUtils.checkToken(token);
            user_id=userDao.getUserId();
        }
        List<Follow> userallFollow = followMapper.selectList(new QueryWrapper<Follow>().eq("user_id", user_id));

        return iFollowService.getFollow(user_id,token,userallFollow,0);
    }

    @RequestMapping("/follower/list")
    public RelationResult relationfollower(String user_id, String token){
        System.out.println("关注列表方法接收到的用户id："+user_id);
        if (Integer.parseInt(user_id)==0){
            UserDao userDao = JWTUtils.checkToken(token);
            user_id=userDao.getUserId();
        }
        List<Follow> userallFollower = followMapper.selectList(new QueryWrapper<Follow>().eq("followed_user_id", user_id));


        return iFollowService.getFollow(user_id,token,userallFollower,1);
    }

    @RequestMapping("/friend/list")
    public RelationResult friend(String user_id, String token){
        System.out.println("关注列表方法接收到的用户id："+user_id);
        if (Integer.parseInt(user_id)==0){
            UserDao userDao = JWTUtils.checkToken(token);
            user_id=userDao.getUserId();
        }
        List<Follow> userallFollow = followMapper.selectList(new QueryWrapper<Follow>().eq("user_id", user_id));
        List<Follow> userallFollower = followMapper.selectList(new QueryWrapper<Follow>().eq("followed_user_id", user_id));
        List<Follow> fairend = new ArrayList<>();

        for (Follow follow : userallFollow) {
            for (Follow follower : userallFollower) {
                if (follow.getFollowedUserId() == follower.getUserId()){
                    fairend.add(follow);
                }
            }
        }

        return iFollowService.getFollow(user_id,token,fairend,0);
    }

    @RequestMapping("/action")
    public Result action(String token,String to_user_id,String action_type){
        UserDao userDao = JWTUtils.checkToken(token);
        Follow follow = new Follow();

        follow.setCreateTime(LocalDateTime.now());
        follow.setUserId(Integer.parseInt(userDao.getUserId()));
        follow.setFollowedUserId(Integer.parseInt(to_user_id));
        follow.setDeleted(Integer.parseInt(action_type)==1?0:1);
        iFollowService.saveOrUpdate(follow,new UpdateWrapper<Follow>().eq("followed_user_id",to_user_id).eq("user_id",
                Integer.parseInt(userDao.getUserId())));



        return Result.ok();
    }



}
