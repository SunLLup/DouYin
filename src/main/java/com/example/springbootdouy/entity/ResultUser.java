package com.example.springbootdouy.entity;

import lombok.Data;

@Data
public class ResultUser {
    private Integer id;
    private String name;

    private Integer follow_count;

    private Integer follower_count;

    private Boolean is_follow;

    private String avatar;

    private String background_image;

    private String signature;

    private int total_favorited;

    private Integer work_count;

    private Integer favorite_count;



}
