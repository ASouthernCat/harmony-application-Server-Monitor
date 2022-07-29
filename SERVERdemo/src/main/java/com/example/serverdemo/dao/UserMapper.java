package com.example.serverdemo.dao;
import com.example.serverdemo.bean.UserBean;
//DAO层访问数据库接口文件
public interface UserMapper {
    UserBean getInfo(String name, String password);
}
