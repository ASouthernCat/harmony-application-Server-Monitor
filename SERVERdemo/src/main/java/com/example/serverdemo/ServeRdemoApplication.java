package com.example.serverdemo;

import com.example.serverdemo.serviceImpl.downloadJSON;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling //开启调度任务
@MapperScan("com.example.serverdemo.dao")
public class ServeRdemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServeRdemoApplication.class, args);

    }

}
