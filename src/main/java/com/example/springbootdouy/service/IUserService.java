package com.example.springbootdouy.service;

import com.example.springbootdouy.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootdouy.until.UserResult;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-04-24
 */
public interface IUserService extends IService<User> {

    UserResult getAuthor(String userId, String token);

    UserResult getAuthorVisit(String userId, String token);
}
