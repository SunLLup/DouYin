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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 最近登陆时间
     */
    private LocalDateTime loginTime;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户首页背景图
     */
    private String backgroundImage;

    /**
     * 用户个人简介
     */
    private String signature;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;


}
