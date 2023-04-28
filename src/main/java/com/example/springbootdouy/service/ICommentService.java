package com.example.springbootdouy.service;

import com.example.springbootdouy.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootdouy.until.CommentListResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-04-24
 */
public interface ICommentService extends IService<Comment> {

    CommentListResult getCommentList(String token, String videoId);
}
