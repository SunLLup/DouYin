package com.example.springbootdouy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 视频地址
     */
    private String playUrl;

    /**
     * 视频第一帧图片地址
     */
    private String coverUrl;

    /**
     * 视频名称
     */
    private String title;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;


}
