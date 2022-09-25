package com.first.project;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.first.project.Rabbitmq.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/Like")
public class controller {


    private final static Logger LOGGER = LoggerFactory.getLogger(controller.class);

    @Value("${eureka.instance.metadataMap.zone}")
    private String zone;

    @Value("${server.port}")
    private String port;
	
	
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Sender sender;




    @PostMapping("/Add")
    public Object add(    @RequestParam String id,
                          @RequestParam String rating,
                          @RequestHeader(value="Authorization") String Authorization
                        ) throws InterruptedException {


        LOGGER.info("Inside microservice add() method and returning response for the requested resource.");

        System.out.println("I'm Add Like server from zone : "+zone+" , work on port : "+port);
		

		return sender.sendtoQueue(id, rating, Authorization);
/*
         String url="http://YOUTUBEADDLIKE/YoutubeLike/add?id={id}&rating={rating}";

        Map<String,String>params=new HashMap<String,String>();
        params.put("id",id);
        params.put("rating",rating);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Authorization);
        HttpEntity entity = new HttpEntity(headers);

        return restTemplate.exchange(url, HttpMethod.POST,entity,Object.class,params);
*/
 }



    @PostMapping("/Add_B")
    public Object add_B(    @RequestParam String id,
                          @RequestParam String rating,
                          @RequestHeader(value="Authorization") String Authorization
    ) throws InterruptedException {


        LOGGER.info("Inside microservice add_B() method and returning response for the requested resource.");

        System.out.println("I'm Add Like server from zone : " + zone + " , work on port : " + port);


         sender.sendtoQueue_B(id, rating, Authorization);

        return "I'm Add Like server from zone : " + zone + " , work on port : " + port;
    }

    }
