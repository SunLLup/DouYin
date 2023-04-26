package com.example.springbootdouy.controller;

import com.example.springbootdouy.mapper.FavoriteMapper;
import com.example.springbootdouy.service.IFavoriteService;
import com.example.springbootdouy.until.Result;
import com.example.springbootdouy.until.VideolistResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

        return Result.ok();
    }


}
