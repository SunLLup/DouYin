package com.example.springbootdouy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdouy.dao.UserDao;
import com.example.springbootdouy.dao.VideoListDao;
import com.example.springbootdouy.entity.*;
import com.example.springbootdouy.mapper.CommentMapper;
import com.example.springbootdouy.mapper.FavoriteMapper;
import com.example.springbootdouy.mapper.FollowMapper;
import com.example.springbootdouy.mapper.VideoMapper;
import com.example.springbootdouy.service.IUserService;
import com.example.springbootdouy.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdouy.until.*;
import javafx.scene.control.TableView;
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
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    @Resource
    AliyunOss aliyunOss;

    @Resource
    VideoMapper videoMapper;
    @Resource
    IUserService iUserService;

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
    public VideolistResult getMyvideo(String token, String userId) {


        //token 登录用户的id
        UserDao userDao = JWTUtils.checkToken(token);

        //todo 未登录时状态

        System.out.println("当前主页登录的id："+userDao.getUserId()+"--"+userId);
        int user_id=Integer.parseInt(userId);
        // todo 判断是否为游客
        if (user_id==0){
            user_id = Integer.parseInt(userDao.getUserId());
        }

        //根据用户id查找所有视频
        List<Video> videoList = videoMapper.selectList(new QueryWrapper<Video>().eq("user_id", user_id));


        //用于存放前台所需要的数据
        ArrayList<VideoListDao> videoListDaos = new ArrayList<>();

        for (Video video : videoList) {
            //todo 获取作者信息

            //todo 去redis查找
            ResultUser resultUser = new ResultUser();

            ResultUser user1 = getUserRedis.getUser(userId);
            if (user1!=null){
                resultUser=user1;
            }else {
                User user = iUserService.getById(video.getUserId());
                System.out.println(user+"用户信息");
                resultUser.setId(user.getId());
                resultUser.setName(user.getUsername());
                //todo 用户关注/粉丝


                //关注--粉丝
                int follow = followMapper.selectObjs(new QueryWrapper<Follow>().eq("user_id", user.getId())).size();
                int follower = followMapper.selectObjs(new QueryWrapper<Follow>().eq("followed_user_id",user.getId())).size();

                //当前用户是否关注查看的用户主页的人
                Follow follow1 = followMapper.selectOne(new QueryWrapper<Follow>().eq("user_id",userDao.getUserId()).eq("followed_user_id", userId));


                resultUser.setFollower_count(follower);
                resultUser.setFollow_count(follow);
                resultUser.setIs_follow(follow1==null?false:true);

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
                int userlike_count = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("user_id", video.getUserId())).size();


                resultUser.setTotal_favorited(f_count);
                //作品数量
                resultUser.setWork_count(videoList.size());
                resultUser.setFavorite_count(userlike_count);
            }



            //todo 获取视频的信息
            VideoListDao videoListDao = new VideoListDao();
            //获取视频信息
            videoListDao.setId(video.getId());
            videoListDao.setAuthor(resultUser);
            videoListDao.setPlay_url(video.getPlayUrl());
            videoListDao.setCover_url(video.getCoverUrl());

            //视频点赞总数
            int video_cout = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("video_id", video.getId())).size();
            videoListDao.setFavorite_count(video_cout);
            //视频的评论总数
            int comment_count = commentMapper.selectList(new QueryWrapper<Comment>().eq("video_id", video.getId()).eq("deleted", video.getDeleted())).size();

            videoListDao.setComment_count(comment_count);

            //视频是否点赞
            int Isfavorite = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("video_id", video.getId()).eq("user_id", userDao.getUserId())).size();
            if (Isfavorite == 0){
                videoListDao.setIs_favorite(false);
            }else{
                videoListDao.setIs_favorite(true);
            }

            videoListDao.setTitle(video.getTitle());

            videoListDaos.add(videoListDao);
        }

        return VideolistResult.ok(0,"wu",videoListDaos);
    }
}
