package com.example.springbootdouy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdouy.dao.CommentListDao;
import com.example.springbootdouy.dao.UserDao;
import com.example.springbootdouy.entity.Comment;
import com.example.springbootdouy.entity.ResultUser;
import com.example.springbootdouy.mapper.CommentMapper;
import com.example.springbootdouy.service.ICommentService;
import com.example.springbootdouy.until.CommentListResult;
import com.example.springbootdouy.until.CommentResult;
import com.example.springbootdouy.until.JWTUtils;
import com.example.springbootdouy.until.RedisKey;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/douyin")
public class CommentController {
    @Resource
    ICommentService iCommentService;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    CommentMapper commentMapper;

    @RequestMapping("/comment/list")
    public CommentListResult commentlist(String token, String video_id){

        return iCommentService.getCommentList(token,video_id);
    }

    /**
     * 查看用户评论列表
     * @param token
     * @param video_id
     * @return
     */
    @RequestMapping("/comment/action")
    public CommentResult action(String token, String video_id,String action_type,String comment_text,String comment_id){
        UserDao userDao = JWTUtils.checkToken(token);

        //去缓存找登录用户的基本信息
        String s = stringRedisTemplate.opsForValue().get(RedisKey.USER_LOGIN + userDao.getUserId());
        ResultUser resultUser = BeanUtil.copyProperties(s, ResultUser.class);
        CommentListDao commentResult = new CommentListDao();

        //     添加评论
        if (Integer.parseInt(action_type) == 1){

            Comment comment = new Comment();
            comment.setVideoId(Integer.parseInt(video_id));
            comment.setComment(comment_text);
            comment.setCreateTime(LocalDateTime.now());
            comment.setUserId(Integer.parseInt(userDao.getUserId()));
            iCommentService.save(comment);
            System.out.println("评论发布成功："+comment);
            commentResult.setId(comment.getId());
            commentResult.setUser(resultUser);
            commentResult.setCreate_date(comment.getCreateTime().toString());
            commentResult.setContent(comment.getComment());



        }

        return CommentResult.ok(0,null,commentResult);
    }



}
