package com.example.springbootdouy.until;

import lombok.Data;

@Data
public class LoginResult {

    private Integer status_code;
    /**
     * 返回状态描述
     */
    private String status_msg;
    /**
     * 用户鉴权token
     */
    private String token;
    /**
     * 用户id
     */
    private Integer user_id;

    public static LoginResult  ok(Integer userid,String token){
        LoginResult loginResult = new LoginResult();
        loginResult.setStatus_code(0);
        loginResult.setStatus_msg(null);
        loginResult.setUser_id(userid);
        loginResult.setToken(token);

        return loginResult;
    }

    public static LoginResult  fail(Integer userid,String token){
        LoginResult loginResult = new LoginResult();
        loginResult.setStatus_code(2);
        loginResult.setStatus_msg(null);
        loginResult.setUser_id(userid);
        loginResult.setToken(token);

        return loginResult;
    }

}
