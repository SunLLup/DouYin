package com.example.springbootdouy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springbootdouy.dao.UserDao;
import com.example.springbootdouy.entity.Favorite;
import com.example.springbootdouy.mapper.FavoriteMapper;
import com.example.springbootdouy.service.IFavoriteService;
import com.example.springbootdouy.until.JWTUtils;
import com.example.springbootdouy.until.Result;
import com.example.springbootdouy.until.VideolistResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/douyin")
public class FavoriteController {

    @Resource
    IFavoriteService iFavoriteService;
    @Resource
    FavoriteMapper favoriteMapper;

    @RequestMapping("/favorite/list")
    public VideolistResult videolist(String user_id, String token){
        return iFavoriteService.getFavoriteList(user_id,token);
    }


    @RequestMapping("/favorite/action")
    public Result action(String token, String video_id, String action_type){
        //todo 通过视频id查找
        UserDao userDao = JWTUtils.checkToken(token);
        Favorite favorite = favoriteMapper.selectOne(new QueryWrapper<Favorite>().eq("video_id", video_id).eq("user_id", userDao.getUserId()));
        if (favorite==null){
            Favorite favorite1 = new Favorite();
            favorite1.setUserId(Integer.parseInt(userDao.getUserId()));
            favorite1.setVideoId(Integer.parseInt(video_id));
            favorite1.setCreateTime(LocalDateTime.now());
            iFavoriteService.save(favorite1);
        }
        Favorite favorite1 = new Favorite();

        if (Integer.parseInt(action_type) ==1){
            favorite1.setDeleted(1);
        }else{
            favorite1.setDeleted(0);
        }
        int update = favoriteMapper.update(favorite1, new UpdateWrapper<Favorite>().eq("video_id", video_id).eq("user_id", userDao.getUserId()));
        System.out.println("点赞更新："+update);

        return Result.ok();
    }



}
