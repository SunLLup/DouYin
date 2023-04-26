package com.example.springbootdouy.until;

import lombok.Data;

@Data
public class Result {
    Integer status_code;
    String status_msg;

    public static Result  ok(){
        Result result = new Result();
        result.setStatus_code(0);
        result.setStatus_msg(null);

        return result;
    }

    public static Result  fail(){
        Result result = new Result();
        result.setStatus_code(2);
        result.setStatus_msg(null);

        return result;
    }

}
