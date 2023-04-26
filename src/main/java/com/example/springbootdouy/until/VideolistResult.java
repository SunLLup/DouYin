package com.example.springbootdouy.until;

import com.example.springbootdouy.dao.VideoListDao;
import lombok.Data;

import java.util.ArrayList;
@Data
public class VideolistResult {
    private Integer status_code;
    private String status_msg;
    private ArrayList<VideoListDao> video_list;

    public static VideolistResult ok(int status_code,String status_msg, ArrayList<VideoListDao> video_list){
        VideolistResult videolistResult = new VideolistResult();
        videolistResult.setStatus_code(status_code);
        videolistResult.setStatus_msg(null);
        videolistResult.setVideo_list(video_list);


        return videolistResult;
    }

    public static VideolistResult fail(String status_code,String status_msg, ArrayList<VideoListDao> video_list){
        VideolistResult videolistResult = new VideolistResult();
        videolistResult.setStatus_code(2);
        videolistResult.setStatus_msg("视频列表获取成功");
        videolistResult.setVideo_list(video_list);


        return videolistResult;
    }

}
