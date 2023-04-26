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
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送者id
     */
    private Integer fromUserId;

    /**
     * 接受者id
     */
    private Integer toUserId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;


}
