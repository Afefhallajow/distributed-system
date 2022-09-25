package com.first.project.rabbitMQ;

import com.first.project.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class Receiver {

    private final static Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Value("${eureka.instance.metadataMap.zone}")
    private String zone;

    @Value("${server.port}")
    private String port;

    RestTemplate restTemplate=new RestTemplate();


    @RabbitListener(queues = MessageConfig.QUEUE)
    public void consumeMessageFromQueue(Message message) throws InterruptedException {
        System.out.println("Message received from queue : " + message.id);
        if(message.getType().equals("A")) {
            add(message.id, message.rating, message.author);
        }
        else {
            LOGGER.info("Inside microservice add_B() method and returning response for the requested resource.");
            System.out.println("Message received from queue : " + message.id);
            System.out.println("I'm Add Like Youtube API from zone : " + zone + " , work on port : " + port);
        }
    }



    public
    Object add( String id,
                String rating,
                String Authorization
    ) throws InterruptedException {
        LOGGER.info("Inside microservice add() method and returning response for the requested resource.");

//        System.out.println(Authorization);
        String url="https://www.googleapis.com/youtube/v3/videos/rate?id={id}&rating={rating}";

        Map<String,String> params=new HashMap<String,String>();
        params.put("id",id);
        params.put("rating",rating);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Authorization);
        HttpEntity entity = new HttpEntity(headers);
        //return"";
        return restTemplate.exchange(url, HttpMethod.POST,entity,Object.class,params);
    }

}
