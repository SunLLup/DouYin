package com.example.springbootdouy.until;

import com.example.springbootdouy.dao.VideoListDao;
import lombok.Data;

import java.util.ArrayList;

@Data
public class VideoAllListResult {
    private Integer status_code;
    private String status_msg;
    private Integer next_time;
    private ArrayList<VideoListDao> video_list;

    public static VideoAllListResult ok(int status_code,String status_msg,Integer next_time, ArrayList<VideoListDao> video_list){
        VideoAllListResult videolistResult = new VideoAllListResult();
        videolistResult.setStatus_code(status_code);
        videolistResult.setStatus_msg(null);
        videolistResult.setNext_time(next_time);
        videolistResult.setVideo_list(video_list);


        return videolistResult;
    }

    public static VideoAllListResult fail(String status_code,String status_msg,Integer next_time, ArrayList<VideoListDao> video_list){
        VideoAllListResult videolistResult = new VideoAllListResult();
        videolistResult.setStatus_code(2);
        videolistResult.setStatus_msg("视频列表获取视频");
        videolistResult.setNext_time(next_time);
        videolistResult.setVideo_list(video_list);

        return videolistResult;
    }




}
