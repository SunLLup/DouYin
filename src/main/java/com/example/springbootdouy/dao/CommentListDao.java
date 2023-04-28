package com.example.springbootdouy.dao;

import com.example.springbootdouy.entity.ResultUser;
import lombok.Data;

@Data
public class CommentListDao {

    private Integer id;
    private ResultUser user;
    private String content;
    private String create_date;


}
