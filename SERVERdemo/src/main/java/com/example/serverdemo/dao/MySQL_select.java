package com.example.serverdemo.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MySQL_select {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String select(String type,String ip){
        switch (type){
            case "processlist":{
                String sql = "select processlist_json from processlist  where server_ip=\""+ip+"\" order by time desc limit 1";
                log.info("Processlist Request from ip: "+ip);
                return jdbcTemplate.queryForList(sql).get(0).get("processlist_json").toString();
            }
            case "cpu":{
                String sql = "select cpu_json from cpu where server_ip=\""+ip+"\" order by time desc limit 1";
                log.info("CPU Request from ip: "+ip);
                return jdbcTemplate.queryForList(sql).get(0).get("cpu_json").toString();
            }
            case "mem":{
                String sql = "select mem_json from mem where server_ip=\""+ip+"\" order by time desc limit 1";
                log.info("MEM Request from ip: "+ip);
                return jdbcTemplate.queryForList(sql).get(0).get("mem_json").toString();
            }
            case "system":{
                String sql = "select system_json from test.`system` where server_ip=\""+ip+"\" order by time desc limit 1";
                log.info("System Request from ip: "+ip);
                return jdbcTemplate.queryForList(sql).get(0).get("system_json").toString();
            }
            case "fs":{
                String sql = "select fs_json from fs where server_ip=\""+ip+"\" order by time desc limit 1";
                log.info("FS Request from ip: "+ip);
                return jdbcTemplate.queryForList(sql).get(0).get("fs_json").toString();
            }


        }return null;
    }
}
