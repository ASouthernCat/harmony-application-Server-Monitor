package com.example.serverdemo.serviceImpl;

import com.example.serverdemo.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class savaData {
    @Autowired
    downloadJSON downloadJSON = new downloadJSON();
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    String length;

    //定时任务 每隔1秒执行一次
    @Scheduled(cron = "*/1 * * * * ?")
    public void save_json(){

        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String,String> params = new LinkedMultiValueMap<String, String>();

        String sql = "select ip from server_ip ";
        List<Map<String, Object>> s=jdbcTemplate.queryForList(sql);

        List<String> ipList = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : s) {
            //assert false;
            ipList.add(stringObjectMap.get("ip").toString());
        }
        log.info("server_ip表数据："+ipList);

        //String ip0="10.8.109.151";//被监控的服务器ip地址
        for (String ip:ipList
             ) {
            log.info("正在获取服务器 "+ip+" 的各状态信息");
            if(Objects.equals(downloadJSON.getJSON("cpu", ip, method, params), ""))continue;
            downloadJSON.getJSON("processlist",ip,method,params);
            downloadJSON.getJSON("mem",ip,method,params);
            downloadJSON.getJSON("fs",ip,method,params);
            downloadJSON.getJSON("system",ip,method,params);
        }

        //当表数据超过50行则清空
            length = jdbcTemplate.queryForList("select count(*) as length from processlist").get(0).get("length").toString();
            if (Integer.parseInt(length)>50){
                jdbcTemplate.execute("delete from processlist");
            }
            length = jdbcTemplate.queryForList("select count(*) as length from mem").get(0).get("length").toString();
            if (Integer.parseInt(length)>50){
                jdbcTemplate.execute("delete from mem");
            }
            length = jdbcTemplate.queryForList("select count(*) as length from cpu").get(0).get("length").toString();
            if (Integer.parseInt(length)>50){
                jdbcTemplate.execute("delete from cpu");
            }
        length = jdbcTemplate.queryForList("select count(*) as length from test.`system`").get(0).get("length").toString();
        if (Integer.parseInt(length)>50){
            jdbcTemplate.execute("delete from test.`system`");
        }
        length = jdbcTemplate.queryForList("select count(*) as length from fs").get(0).get("length").toString();
        if (Integer.parseInt(length)>50){
            jdbcTemplate.execute("delete from fs");
        }
    }
}
