package com.example.springbootdouy.until;


import cn.hutool.core.bean.BeanUtil;
import com.example.springbootdouy.entity.ResultUser;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class GetUserRedis {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    
    public  ResultUser getUser(String userId){
        Map<Object, Object> author = stringRedisTemplate.opsForHash().entries(RedisKey.USER_MESSAGE + userId);
        ResultUser resultUser = BeanUtil.fillBeanWithMap(author, new ResultUser(), false);
        if (resultUser.getId()==null){
            return null;
        }

        return resultUser;
    }

    public  void setUser(String userId,ResultUser resultUser){
        if (userId == null){
            return;
        }
        stringRedisTemplate.opsForHash().putAll(RedisKey.USER_MESSAGE+userId,BeanUtil.beanToMap(resultUser));
    }
}
