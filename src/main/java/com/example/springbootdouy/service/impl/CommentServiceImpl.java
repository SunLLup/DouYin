package com.example.springbootdouy.service.impl;

import com.example.springbootdouy.entity.Comment;
import com.example.springbootdouy.mapper.CommentMapper;
import com.example.springbootdouy.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
