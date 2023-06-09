package com.example.springbootdouy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdouy.dao.UserDao;
import com.example.springbootdouy.dao.VideoListDao;
import com.example.springbootdouy.entity.*;
import com.example.springbootdouy.mapper.CommentMapper;
import com.example.springbootdouy.mapper.FavoriteMapper;
import com.example.springbootdouy.mapper.FollowMapper;
import com.example.springbootdouy.mapper.VideoMapper;
import com.example.springbootdouy.service.IFollowService;
import com.example.springbootdouy.service.IUserService;
import com.example.springbootdouy.service.IVideoService;
import com.example.springbootdouy.until.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/douyin")
public class VoidePublistController {

    @Resource
    AliyunOss aliyunOss;
    @Resource
    IVideoService iVideoService;

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


    //用户上传视频
    @RequestMapping("/publish/action")
    public Result action(MultipartFile data, String token, String title){
        System.out.println("hahaha");

        UserDao userDao = JWTUtils.checkToken(token);


//        System.out.println("上传用户id:"+uid);
        String videoUrl = aliyunOss.fileUpload(data);
        if (videoUrl.isEmpty()){
            return Result.fail();
        }
        System.out.println("上传ok"+Integer.parseInt(userDao.getUserId()));

        Video video = new Video();
        video.setUserId(Integer.parseInt(userDao.getUserId()));
        video.setPlayUrl(videoUrl);
        video.setTitle(title);
        video.setCreateTime(LocalDateTime.now());


        iVideoService.save(video);

        System.out.println(data+"  "+token);
        return Result.ok();
    }

    /**
     *
     * @param token jwt令牌 里面包含 登录的用户id 登录的用户密码
     * @param user_id 刷视频访问到某用户的主页的id
     * @return
     */

    //用户发表过所有的视频
    @RequestMapping("/publish/list")
    public VideolistResult videolist(String token,String user_id){

        return iVideoService.getMyvideo(token,user_id);
    }


    @RequestMapping("/feed")
    public VideoAllListResult feed(String latest_time,String token){
        int videocount=0;
        Long next_time=0l;
        System.out.println("欢迎来到视频流---------------------");
//        //创建时间戳
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        long next_time = timestamp.getTime();
//        System.out.println("时间戳:"+next_time+"---传来的:"+latest_time);

        // todo 业务层
        //token 登录用户的id
        UserDao userDao = JWTUtils.checkToken(token);
        System.out.println("当前主页登录的id："+userDao.getUserId()+"--");

        // todo 查找所有视频
        List<Video> videoList = videoMapper.selectList(new QueryWrapper<Video>());

        //todo 对视频按添加时间排序
        videoList.sort((t1,t2) -> t2.getCreateTime().compareTo(t1.getCreateTime()));


        //用于存放前台所需要的数据
        ArrayList<VideoListDao> videoListDaos = new ArrayList<>();




        for (Video video : videoList) {
            long epochMilli = (video.getCreateTime()).toInstant(ZoneOffset.of("+8")).toEpochMilli();
            System.out.println("对象里转换的时间戳："+epochMilli);
            if (Long.parseLong(latest_time)<epochMilli){
                continue;
            }
            if(videocount>3){
                break;
            }
            next_time=epochMilli;

            videocount++;


            //todo 获取作者信息
            ResultUser resultUser = new ResultUser();

            User user = iUserService.getById(video.getUserId());
            System.out.println(user+"用户信息");
            resultUser.setId(user.getId());
            resultUser.setName(user.getUsername());
            //todo 用户关注/粉丝
            //关注--粉丝
            int follow = followMapper.selectObjs(new QueryWrapper<Follow>().eq("user_id", user.getId())).size();
            int follower = followMapper.selectObjs(new QueryWrapper<Follow>().eq("followed_user_id", user.getId())).size();

            //当前用户是否关注查看的用户主页的人
            Follow follow1 = followMapper.selectOne(new QueryWrapper<Follow>().eq("user_id", userDao.getUserId()).eq("followed_user_id", user.getId()));


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
            int userlike_count = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("user_id", user.getId())).size();


            resultUser.setTotal_favorited(f_count);
            //作品数量
            resultUser.setWork_count( videoMapper.selectList(new QueryWrapper<Video>().eq("user_id",video.getUserId())).size());
            resultUser.setFavorite_count(userlike_count);


            //todo 以下是视频信息
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

            //视频是否被自己点赞
            int Isfavorite = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("video_id", video.getId()).eq("user_id", user.getId())).size();
            if (Isfavorite == 0){
                videoListDao.setIs_favorite(false);
            }else{
                videoListDao.setIs_favorite(true);
            }

            videoListDao.setTitle(video.getTitle());

            videoListDaos.add(videoListDao);
        }

        return VideoAllListResult.ok(0,"wu",next_time,videoListDaos);





    }



}
