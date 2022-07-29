package com.example.serverdemo.controller;

import com.example.serverdemo.constant.Constant;
import com.example.serverdemo.dao.MySQL_select;
import com.example.serverdemo.serviceImpl.downloadJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
public class JsonRequest {

    @Autowired
    downloadJSON downloadJSON;
    @Autowired
    MySQL_select select;

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @ResponseBody
    @GetMapping("/{getTYPE}:{serverIP}")
    public String getJsonInfo(@PathVariable String serverIP, @PathVariable String getTYPE) throws IOException {
        log.info("serverIP:"+serverIP+"  getTYPE:"+getTYPE);
        String pattern = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";//ipv4正则表达式
        if(Objects.equals(getTYPE, "addserver")){//添加服务器
            if(serverIP.matches(pattern)){//符合ip格式
                log.info("正确的ip格式--->"+serverIP);
                String sql = "select count(*) as cn from server_ip where ip = \""+serverIP+"\"";
                //String sql = "select count(*) as cn from server_ip where ip = \"10.8.109.1\"";
            if(Objects.equals(jdbcTemplate.queryForList(sql).get(0).get("cn").toString(), "0")){
                log.info("新的ip--->"+serverIP);
                jdbcTemplate.update("insert into server_ip values (?)",serverIP);
                return "已成功添加服务器！";
                }else return "此服务器已存在";
            }
            else return "请正确输入ip！";
        }
        else {//获取状态信息
            //从数据库返回JSON数据
            String json = select.select(getTYPE, serverIP);
            if (!Objects.equals(json, "")) {
                log.info("json from MySQL:" + json);
                return json;
            } else return null;
        }
/*
        //从api URL 获取JSON并返回
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String,String> params = new LinkedMultiValueMap<String, String>();
        String json = downloadJSON.getJSON(getTYPE,serverIP,method,params);
        return  json;
*/
    }
}
