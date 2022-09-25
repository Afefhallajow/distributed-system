package com.first.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

@RestController

@RequestMapping("/YoutubeSearch")
public class controller {

    private final static Logger LOGGER = LoggerFactory.getLogger(controller.class);


    @Value("${eureka.instance.metadataMap.zone}")
    private String zone;

    @Value("${server.port}")
    private String port;

    RestTemplate restTemplate=new RestTemplate();


    @GetMapping("/search")
    public
    Object search(@RequestParam String key,
                  @RequestParam String part,
                  @RequestParam String q,
                  @RequestParam String type,
                  @RequestParam String maxResults
    ) throws InterruptedException {
        LOGGER.info("Inside microservice2 search() method and returning response for the requested resource.");

        System.out.println("I'm search youtube API server from zone : "+zone+" , work on port : "+port);

        String url="https://www.googleapis.com/youtube/v3/search?key={key}&part={part}&q={q}&type={type}&maxResults={maxResults}";

        Map<String,String> params=new HashMap<String,String>();
        params.put("key",key);
        params.put("part",part);
        params.put("q",q);
        params.put("type",type);
        params.put("maxResults",maxResults);
        return restTemplate.getForObject(url,Object.class,params);
    }


    @GetMapping("/search_B")
    public
    Object search_B() throws InterruptedException {
        LOGGER.info("Inside microservice2 search_B() method and returning response for the requested resource.");

        System.out.println("I'm search youtube API server from zone : "+zone+" , work on port : "+port);


        return "I'm search youtube API server from zone : "+zone+" , work on port : "+port;
    }

}
