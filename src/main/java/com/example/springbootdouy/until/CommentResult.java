package com.example.springbootdouy.until;

import com.example.springbootdouy.dao.CommentListDao;
import com.example.springbootdouy.entity.Comment;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CommentResult {
    private Integer status_code;
    private String status_msg;
    private CommentListDao comment;


    public static CommentResult ok(int status_code,String status_msg,CommentListDao comment_list){
        CommentResult videolistResult = new CommentResult();
        videolistResult.setStatus_code(status_code);
        videolistResult.setStatus_msg(null);
        videolistResult.setComment(comment_list);


        return videolistResult;
    }

    public static CommentResult fail(String status_code,String status_msg, CommentListDao comment_list){
        CommentResult videolistResult = new CommentResult();
        videolistResult.setStatus_code(2);
        videolistResult.setStatus_msg("视频列表获取成功");
        videolistResult.setComment(comment_list);


        return videolistResult;
    }
}
