package com.example.springbootdouy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdouy.dao.CommentListDao;
import com.example.springbootdouy.entity.Comment;
import com.example.springbootdouy.mapper.CommentMapper;
import com.example.springbootdouy.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdouy.service.IUserService;
import com.example.springbootdouy.until.CommentListResult;
import com.example.springbootdouy.until.UserResult;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    IUserService iUserService;

    @Override
    public CommentListResult getCommentList(String token, String videoId) {


        //todo 1.获取评论人的信息
        UserResult author = iUserService.getAuthor("2", token);
        //todo 2.获取视频的评论
        List<Comment> comments= commentMapper.selectList(new QueryWrapper<Comment>().eq("video_id", videoId));

        //todo 对评论日期进行排序
        comments.sort((t1,t2) -> t2.getCreateTime().compareTo(t1.getCreateTime()));
        ArrayList<CommentListDao> commentListDaos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentListDao commentListDao = new CommentListDao();
            commentListDao.setId(comment.getId());
            commentListDao.setUser(author.getUser());
            //按发布的日期
            commentListDao.setContent(comment.getComment());
            commentListDao.setCreate_date(comment.getCreateTime().toString());
            commentListDaos.add(commentListDao);
        }

        //todo 3.打包直接返回
        return CommentListResult.ok(0,null,commentListDaos);
    }
}
