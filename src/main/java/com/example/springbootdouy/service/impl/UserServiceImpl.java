package com.example.springbootdouy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootdouy.dao.UserDao;
import com.example.springbootdouy.entity.*;
import com.example.springbootdouy.mapper.*;
import com.example.springbootdouy.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdouy.until.GetUserRedis;
import com.example.springbootdouy.until.JWTUtils;
import com.example.springbootdouy.until.RedisKey;
import com.example.springbootdouy.until.UserResult;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    GetUserRedis getUserRedis;


    @Override
    public UserResult getAuthor(String userId, String token) {

        //todo 根据token来查找主页数据
        //token 登录用户的id
        UserDao userDao = JWTUtils.checkToken(token);

        //todo 登录的用户id
        int userid=Integer.parseInt(userDao.getUserId());
        //todo 先到Redis 里面查找
        ResultUser user1 = getUserRedis.getUser(userDao.getUserId());
        System.out.println("hash获取到的用户信息："+user1);
        if (user1!=null){
            System.out.println("沃州的");
            return UserResult.ok(user1);
        }

        //根据用户id查找所有视频
        List<Video> videoList = videoMapper.selectList(new QueryWrapper<Video>().eq("user_id", userid));

        //todo 获取作者信息
        ResultUser resultUser = new ResultUser();

//        User user = iUserService.getById(userid);
        User user = userMapper.selectById(userId);
        System.out.println(user+"用户信息");
        resultUser.setId(user.getId());
        resultUser.setName(user.getUsername());
        //todo 用户关注/粉丝


        //关注--粉丝
        int follow = followMapper.selectObjs(new QueryWrapper<Follow>().eq("user_id", user.getId())).size();
        int follower = followMapper.selectObjs(new QueryWrapper<Follow>().eq("followed_user_id",user.getId())).size();

        resultUser.setFollower_count(follower);
        resultUser.setFollow_count(follow);
        resultUser.setIs_follow(true);

        resultUser.setAvatar(user.getAvatar());
        resultUser.setBackground_image(user.getBackgroundImage());
        resultUser.setSignature(user.getSignature());

        //todo 获赞数量  作品数 喜欢数
        //获赞数据量
        int f_count=0;
        for (Video video1 : videoList) {
            //获取视频信息
            f_count +=favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("video_id", video1.getId())).size();

        }
        System.out.println("用户获赞数量："+f_count);
        //喜欢数
        int userlike_count = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("user_id", userid)).size();

        resultUser.setTotal_favorited(f_count);
        //作品数量
        resultUser.setWork_count(videoList.size());
        resultUser.setFavorite_count(userlike_count);

        //将登陆用户信息 以hash方式 存入redis
        //todo hash
//        stringRedisTemplate.opsForValue().set(RedisKey.USER_LOGIN+userid, JSONUtil.toJsonStr(resultUser));
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(resultUser,new HashMap<>(),
        CopyOptions.create().setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName,fieldValue) -> fieldValue+""));

        stringRedisTemplate.opsForHash().putAll(RedisKey.USER_MESSAGE+userid, stringObjectMap);

        return UserResult.ok(resultUser);
    }

    @Override
    public UserResult getAuthorVisit(String userId, String token) {

        //todo 登录的用户id
        int userid=Integer.parseInt(userId);

        //todo 先到Redis 里面查找
        ResultUser user1 = getUserRedis.getUser(userId);
        if (user1!=null){
            return UserResult.ok(user1);
        }

        //根据用户id查找所有视频
        List<Video> videoList = videoMapper.selectList(new QueryWrapper<Video>().eq("user_id", userid));

        //todo 获取作者信息
        ResultUser resultUser = new ResultUser();

//        User user = iUserService.getById(userid);
        User user = userMapper.selectById(userId);
        System.out.println(user+"用户信息");
        resultUser.setId(user.getId());
        resultUser.setName(user.getUsername());
        //todo 用户关注/粉丝


        //关注--粉丝
        int follow = followMapper.selectObjs(new QueryWrapper<Follow>().eq("user_id", user.getId())).size();
        int follower = followMapper.selectObjs(new QueryWrapper<Follow>().eq("followed_user_id",user.getId())).size();

        resultUser.setFollower_count(follower);
        resultUser.setFollow_count(follow);
        resultUser.setIs_follow(true);

        resultUser.setAvatar(user.getAvatar());
        resultUser.setBackground_image(user.getBackgroundImage());
        resultUser.setSignature(user.getSignature());

        //todo 获赞数量  作品数 喜欢数
        //获赞数据量
        int f_count=0;
        for (Video video1 : videoList) {
            //获取视频信息
            f_count +=favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("video_id", video1.getId())).size();

        }
        System.out.println("用户获赞数量："+f_count);
        //喜欢数
        int userlike_count = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("user_id", userid)).size();

        resultUser.setTotal_favorited(f_count);
        //作品数量
        resultUser.setWork_count(videoList.size());
        resultUser.setFavorite_count(userlike_count);

//        //将登陆用户信息存入redis
//
//        stringRedisTemplate.opsForValue().set(RedisKey.USER_LOGIN+userid, JSONUtil.toJsonStr(resultUser));
        //todo hash
//        stringRedisTemplate.opsForValue().set(RedisKey.USER_LOGIN+userid, JSONUtil.toJsonStr(resultUser));
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(resultUser,new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName,fieldValue) -> fieldValue+""));

        stringRedisTemplate.opsForHash().putAll(RedisKey.USER_MESSAGE+userid, stringObjectMap);


        return UserResult.ok(resultUser);
    }
}
