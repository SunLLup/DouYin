package com.example.springbootdouy.service.impl;

import com.example.springbootdouy.entity.User;
import com.example.springbootdouy.mapper.UserMapper;
import com.example.springbootdouy.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-04-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
