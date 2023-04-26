package com.example.springbootdouy.service;

import com.example.springbootdouy.entity.Video;
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
public interface IVideoService extends IService<Video> {

    VideolistResult getMyvideo(String token, String userId);
}
