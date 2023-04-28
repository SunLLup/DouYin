package com.example.springbootdouy.until;

import com.example.springbootdouy.entity.ResultUser;
import lombok.Data;

@Data
public class UserResult {
    private long status_code;
    /**
     * 返回状态描述
     */
    private String status_msg;
    /**
     * 用户信息
     */
    private ResultUser user;


    public static UserResult ok(ResultUser user){
        UserResult result = new UserResult();
        result.setStatus_code(0);
        result.setStatus_msg(null);
        result.setUser(user);

        return result;
    }

    public static UserResult fail(ResultUser user){
        UserResult result = new UserResult();
        result.setStatus_code(1);
        result.setStatus_msg(null);
        result.setUser(user);

        return result;
    }
}
