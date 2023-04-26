package com.example.springbootdouy.dao;

import com.example.springbootdouy.entity.ResultUser;
import com.example.springbootdouy.until.UserResult;
import lombok.Data;

@Data
public class VideoListDao {
    private  Integer id;
    private ResultUser author;
    private String play_url;
    private String cover_url;
    private Integer favorite_count;
    private Integer comment_count;
    private Boolean is_favorite;
    private String title;
}
