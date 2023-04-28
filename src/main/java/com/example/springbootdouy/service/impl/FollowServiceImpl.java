package com.example.springbootdouy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdouy.dao.UserDao;
import com.example.springbootdouy.entity.Follow;
import com.example.springbootdouy.entity.ResultUser;
import com.example.springbootdouy.mapper.FollowMapper;
import com.example.springbootdouy.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdouy.service.IUserService;
import com.example.springbootdouy.until.JWTUtils;
import com.example.springbootdouy.until.RedisKey;
import com.example.springbootdouy.until.RelationResult;
import com.example.springbootdouy.until.UserResult;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-04-24
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

    @Resource
    FollowMapper followMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    IUserService iUserService;
    @Override
    public RelationResult getFollow(String userId,String token,List<Follow> userallFollow,Integer x) {

        ArrayList<ResultUser> arrayList = new ArrayList<>();
        for (Follow follow : userallFollow) {
//            看是否被取消取消关注过
            if (follow.getDeleted()!=0){
                continue;
            }

            String s=null;
            //先去redis找
            if(x==0){
                s = stringRedisTemplate.opsForValue().get(RedisKey.USER_LOGIN + follow.getFollowedUserId());
            }else {
                s = stringRedisTemplate.opsForValue().get(RedisKey.USER_LOGIN + follow.getUserId());
            }

            if (s!=null){
                ResultUser resultUser = JSONUtil.toBean(s, ResultUser.class);
                System.out.println("存入的关注人的信息：----"+resultUser);
                arrayList.add(resultUser);
            }else {
                UserResult authorVisit = iUserService.getAuthorVisit(follow.getFollowedUserId().toString(), token);
                arrayList.add(authorVisit.getUser());
            }


        }

        return RelationResult.ok(0,null,arrayList);
    }



}
