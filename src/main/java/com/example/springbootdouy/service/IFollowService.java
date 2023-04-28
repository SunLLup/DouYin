package com.example.springbootdouy.service;

import com.example.springbootdouy.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootdouy.until.RelationResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-04-24
 */
public interface IFollowService extends IService<Follow> {

    RelationResult getFollow(String userId,String token, List<Follow> userallFollow,Integer x);
}
