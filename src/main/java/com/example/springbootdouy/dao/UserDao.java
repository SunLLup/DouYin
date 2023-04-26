package com.example.springbootdouy.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDao {
    private boolean valid;
    private String userId;
    private String password;

    public UserDao(){
        this.valid = false;
    }
}
