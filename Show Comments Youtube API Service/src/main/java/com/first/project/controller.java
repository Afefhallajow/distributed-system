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
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

@RestController

@RequestMapping("/YoutubeShowComments")
public class controller {

    private final static Logger LOGGER = LoggerFactory.getLogger(controller.class);


    @Value("${eureka.instance.metadataMap.zone}")
    private String zone;

    @Value("${server.port}")
    private String port;

	
    RestTemplate restTemplate=new RestTemplate();


    @GetMapping("/show")
    public
    Object show(@RequestHeader(value="Authorization") String Authorization,
                  @RequestParam String part,
                  @RequestParam String videoId
    ) throws InterruptedException {

        LOGGER.info("Inside microservice show() method and returning response for the requested resource.");

        System.out.println("I'm show comments youtube API server from zone : "+zone+" , work on port : "+port);

			
        String url="https://www.googleapis.com/youtube/v3/commentThreads?part={part}&videoId={videoId}";

        Map<String,String> params=new HashMap<String,String>();
        params.put("part",part);
        params.put("videoId",videoId);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Authorization);

        // headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity entity = new HttpEntity(headers);

        return
                "{\"snippet\": \n" +
                "{\n" +
                "    \"videoId\": \""+videoId+"\",\n" +
                "    \"topLevelComment\": {\n" +
                "                    \"snippet\": {\n" +
                "                        \"textOriginal\": \""+part+"\"\n" +
                "                    }\n" +
                "}\n" +
                "}\n" +
                "}";

//test        return restTemplate.exchange(url, HttpMethod.GET,entity,Object.class,params);
    }


    @GetMapping("/justshow")
    public void show_c() throws InterruptedException {
        LOGGER.info("Inside microservice show_c() method and returning response for the requested resource.");

        System.out.println("/justshow");
        sleep(1000);
       // return  "true";
    }

}
