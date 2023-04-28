package com.example.springbootdouy.until;


import com.example.springbootdouy.dao.CommentListDao;
import com.example.springbootdouy.dao.VideoListDao;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CommentListResult {
    private int status_code;
    private String status_msg;
    private ArrayList<CommentListDao> comment_list;

    public static CommentListResult ok(int status_code,String status_msg, ArrayList<CommentListDao> comment_list){
        CommentListResult videolistResult = new CommentListResult();
        videolistResult.setStatus_code(status_code);
        videolistResult.setStatus_msg(null);
        videolistResult.setComment_list(comment_list);


        return videolistResult;
    }

    public static CommentListResult fail(String status_code,String status_msg, ArrayList<CommentListDao> comment_list){
        CommentListResult videolistResult = new CommentListResult();
        videolistResult.setStatus_code(2);
        videolistResult.setStatus_msg("视频列表获取成功");
        videolistResult.setComment_list(comment_list);


        return videolistResult;
    }


}
