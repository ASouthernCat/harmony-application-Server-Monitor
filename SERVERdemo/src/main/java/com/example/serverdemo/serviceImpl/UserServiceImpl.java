package com.example.serverdemo.serviceImpl;

import com.example.serverdemo.bean.UserBean;
import com.example.serverdemo.dao.UserMapper;
import com.example.serverdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    //将DAO注入Service层
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean loginIn(String name, String password) {
        return userMapper.getInfo(name,password);
    }
}