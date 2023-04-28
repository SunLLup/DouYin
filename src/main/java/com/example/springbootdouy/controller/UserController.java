package com.example.springbootdouy.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdouy.dao.UserDao;
import com.example.springbootdouy.dao.VideoListDao;
import com.example.springbootdouy.entity.*;
import com.example.springbootdouy.mapper.*;
import com.example.springbootdouy.service.IUserService;
import com.example.springbootdouy.until.JWTUtils;
import com.example.springbootdouy.until.LoginResult;
import com.example.springbootdouy.until.UserResult;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/douyin")
public class UserController {
    @Resource
    IUserService iUserService;
    @Resource
    UserMapper userMapper;
    @Resource
    VideoMapper videoMapper;

    @Resource
    FollowMapper followMapper;

    @Resource
    FavoriteMapper favoriteMapper;
    @Resource
    CommentMapper commentMapper;


    //用户注册
    @RequestMapping("/user/register")
    public LoginResult register(String username, String password){

        //生成验证码
        UUID uuid = UUID.randomUUID(true);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAvatar("https://www.shunvzhi.com/uploads/allimg/180731/1TF952E-3.jpg");
        user.setBackgroundImage("https://inews.gtimg.com/newsapp_bt/0/13250363674/1000.jpg");
        user.setCreateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());

        iUserService.save(user);

        return LoginResult.ok(user.getId(), uuid.toString());
    }

//    用户登录
    @RequestMapping("/user/login")
    public LoginResult login(String username, String password){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", password));
        if (ObjectUtil.isEmpty(user)){
            System.out.println(user+"：用户名密码错误");
            return LoginResult.fail(0,"0");

        }
        System.out.println("登录login:"+user);
        //todo 利用jwt 生成token
        String token = JWTUtils.createToken(user.getId().toString(), "1", user.getPassword());
        return LoginResult.ok(user.getId(),token);
    }

//    用户信息
    @RequestMapping("/user")
    public UserResult user(String user_id, String token){

        return iUserService.getAuthor(user_id,token);
    }



}
