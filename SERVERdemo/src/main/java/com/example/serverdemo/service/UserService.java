package com.example.serverdemo.service;
import com.example.serverdemo.bean.UserBean;

public interface UserService {
    UserBean loginIn(String name,String password);
}
