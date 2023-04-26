package com.example.springbootdouy.service;

import com.example.springbootdouy.entity.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootdouy.until.VideolistResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-04-24
 */
public interface IFavoriteService extends IService<Favorite> {

    VideolistResult getFavoriteList(String token, String userId);
}
