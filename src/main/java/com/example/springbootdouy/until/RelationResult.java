package com.example.springbootdouy.until;

import com.example.springbootdouy.dao.CommentListDao;
import com.example.springbootdouy.entity.ResultUser;
import lombok.Data;

import java.util.ArrayList;
@Data
public class RelationResult {
    private Integer status_code;
    private String status_msg;
    private ArrayList<ResultUser> user_list;

    public static RelationResult ok(Integer status_code, String status_msg, ArrayList<ResultUser> user_list){
        RelationResult videolistResult = new RelationResult();
        videolistResult.setStatus_code(status_code);
        videolistResult.setStatus_msg(null);
        videolistResult.setUser_list(user_list);


        return videolistResult;
    }

}
