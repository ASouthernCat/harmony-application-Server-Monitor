package com.example.serverdemo.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Slf4j
@Repository
public class MySQL_save {
    Date date;
    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void saveProcesslist(String ip, String json){
        String sql = "";
        jdbcTemplate.update("insert into processlist(server_ip, processlist_json) values(?,?) ",
                ip,json);
        log.info("Save ip & json to MySQL table_processlist: "+ip+"：  "+json);
    }
    public void saveCPU(String ip, String json){
        jdbcTemplate.update("insert into cpu(server_ip, cpu_json) values(?,?) ",
                ip,json);
        log.info("Save ip & json to MySQL table_cpu: "+ip+"：  "+json);
    }
    public void saveMEM(String ip, String json){
        jdbcTemplate.update("insert into mem(server_ip, mem_json) values(?,?) ",
                ip,json);
        log.info("Save ip & json to MySQL table_mem: "+ip+"：  "+json);
    }

    public void saveSYS(String ip, String json) {
        jdbcTemplate.update("insert into test.`system`(server_ip, system_json) values(?,?) ",
                ip,json);
        log.info("Save ip & json to MySQL table_system: "+ip+"：  "+json);
    }

    public void saveFS(String ip, String json) {
        jdbcTemplate.update("insert into fs(server_ip, fs_json) values(?,?) ",
                ip,json);
        log.info("Save ip & json to MySQL table_fs: "+ip+"：  "+json);
    }
}
