package com.first.project;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

@RestController

@RequestMapping("/YoutubeComment")
public class controller {


    private final static Logger LOGGER = LoggerFactory.getLogger(controller.class);

    @Value("${eureka.instance.metadataMap.zone}")
    private String zone;

    @Value("${server.port}")
    private String port;


    //RestTemplate restTemplate=new RestTemplate();





@Autowired
    RestTemplate restTemplate;


    @PostMapping("/add")
    public
    Object add(
                @RequestParam String videoId,
                @RequestParam String textOriginal,
                 @RequestParam String part,
                  @RequestHeader(value="Authorization") String Authorization
    ) throws InterruptedException {

        LOGGER.info("Inside microservice add() method and returning response for the requested resource.");

        System.out.println("I'm ADD comment Youtube API  server from zone : "+zone+" , work on port : "+port);
		
        String url="https://www.googleapis.com/youtube/v3/commentThreads?part={part}";


        Map<String,String>params=new HashMap<String,String>();
        params.put("part",part);




        String body="{\"snippet\": \n" +
                "{\n" +
                "    \"videoId\": \""+videoId+"\",\n" +
                "    \"topLevelComment\": {\n" +
                "                    \"snippet\": {\n" +
                "                        \"textOriginal\": \""+textOriginal+"\"\n" +
                "                    }\n" +
                "}\n" +
                "}\n" +
                "}";



        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Authorization);
        headers.set("Content-Type","application/raw");
      // headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity entity = new HttpEntity(body,headers);

        return body;
//test        return restTemplate.exchange(url, HttpMethod.POST,entity,Object.class,params);
    }






    ///////////////////////////////////////////////////////////////

    @GetMapping("/add_B")
    public
    Object add_sequence_B() throws InterruptedException {

        LOGGER.info("Inside microservice add_B() method and returning response for the requested resource.");

        System.out.println("I'm ADD_B comment Youtube API  server from zone : "+zone+" , work on port : "+port);


        String   url="http://YOUTUBESHOWCOMMENTS/YoutubeShowComments/justshow";


         restTemplate.getForObject(url,String.class);

        return "I'm ADD_B comment Youtube API  server from zone : "+zone+" , work on port : "+port;

    }




    @GetMapping("/justadd")
    public
    void add_c() throws InterruptedException {

        LOGGER.info("Inside microservice add_c() method and returning response for the requested resource.");

        System.out.println("/justadd");
        sleep(1000);
       // return  "true";

    }


}
