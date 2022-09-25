package com.first.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

@RestController

@RequestMapping("/YoutubeLike")
public class controller {


    @Value("${eureka.instance.metadataMap.zone}")
    private String zone;

    @Value("${server.port}")
    private String port;

    RestTemplate restTemplate=new RestTemplate();


	
    @PostMapping("/add")
    public
    Object add(@RequestParam String id,
                  @RequestParam String rating,
                  @RequestHeader(value="Authorization") String Authorization
    ) throws InterruptedException {
		
		System.out.println("I'm ADD Like Youtube API server from zone : "+zone+" , work on port : "+port);

        String url="https://www.googleapis.com/youtube/v3/videos/rate?id={id}&rating={rating}";

        Map<String,String> params=new HashMap<String,String>();
        params.put("id",id);
        params.put("rating",rating);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Authorization);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(url, HttpMethod.POST,entity,Object.class,params);
    }


}
