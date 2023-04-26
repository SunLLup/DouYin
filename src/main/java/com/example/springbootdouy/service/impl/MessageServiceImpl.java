package com.example.springbootdouy.service.impl;

import com.example.springbootdouy.entity.Message;
import com.example.springbootdouy.mapper.MessageMapper;
import com.example.springbootdouy.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-04-24
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
