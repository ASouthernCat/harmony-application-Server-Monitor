package com.example.serverdemo.serviceImpl;

import com.example.serverdemo.dao.MySQL_save;
import com.example.serverdemo.dao.MySQL_select;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class downloadJSON {
    private String url;
    RestTemplate template;
    ResponseEntity<String> response;

    String json;
    @Autowired
    MySQL_save save;
    @Autowired
    MySQL_select select;

    public String getJSON(String TYPE,String server_ip, HttpMethod method, MultiValueMap<String,String> params){
        switch (TYPE){
            case "processlist":
                url = "http://"+server_ip + ":61208/api/3/processlist";
                log.info("url:"+url);
                template = new RestTemplate();
                try {
                    response = template.getForEntity(url,String.class);
                    json = response.getBody();
                } catch (RestClientException e) {
                    return "";
                    //throw new RuntimeException(e);
                }
                JSONArray jsonArray = new JSONArray(json);
                int length = jsonArray.length();
                if(length>20){
                    JSONObject jsonObject = new JSONObject();
                    StringBuilder subJSON= new StringBuilder();
                    subJSON.append("[");
                    for(int i=0;i<20;i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        if(i==0){subJSON.append(jsonObject.toString());
                        }
                        else
                            subJSON.append(","+jsonObject.toString());
                    }
                    subJSON.append("]");
                    save.saveProcesslist(server_ip,subJSON.toString());
                    return subJSON.toString();
                }
                else{
                    save.saveProcesslist(server_ip,response.getBody());
                    return response.getBody();
                }
            case "fs":
                url = "http://"+server_ip + ":61208/api/3/fs";
                log.info("url:"+url);
                template = new RestTemplate();
                try{
                    response = template.getForEntity(url,String.class);
                } catch (RestClientException e) {
                    return "";
                    //throw new RuntimeException(e);
                }
                save.saveFS(server_ip, response.getBody());
                return response.getBody();
            case "cpu":
                url = "http://"+server_ip + ":61208/api/3/cpu/total";
                log.info("url:"+url);
                template = new RestTemplate();
                try{
                response = template.getForEntity(url,String.class);
                } catch (RestClientException e) {
                    return "";
                    //throw new RuntimeException(e);
                }
                save.saveCPU(server_ip, response.getBody());
                return response.getBody();
            case "mem":
                url = "http://"+server_ip + ":61208/api/3/mem";
                log.info("url:"+url);
                template = new RestTemplate();
                try{
                response = template.getForEntity(url,String.class);
                } catch (RestClientException e) {
                    return "";
                    //throw new RuntimeException(e);
                }
                save.saveMEM(server_ip, response.getBody());
                return response.getBody();
            case "system":
                url = "http://"+server_ip + ":61208/api/3/system";
                log.info("url:"+url);
                template = new RestTemplate();
                try {
                    response = template.getForEntity(url,String.class);
                } catch (RestClientException e) {
                    return "";
                }
                save.saveSYS(server_ip, response.getBody());
                return response.getBody();
        }
        return null;
    }
}
