package com.first.project;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/SearchService")
public class SearchController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Value("${eureka.instance.metadataMap.zone}")
    private String zone;

    @Value("${server.port}")
    private String port;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    searchService search_service;

	    @HystrixCommand(fallbackMethod = "alter",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "60000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
            //@HystrixProperty(name = "execution.timeout.enabled",value = "true"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")

    })
    @GetMapping("/search")
    public Object search(@RequestParam String key,
                           @RequestParam String part,
                           @RequestParam String q,
                           @RequestParam String type,
                           @RequestParam String maxResults
                        ) throws InterruptedException {
            LOGGER.info("Inside microservice2 search() method and returning response for the requested resource.");

            System.out.println("I'm search server from zone : " + zone + " , work on port : " + port);

            if (search_service.checkkey(key, q)) {


                String url = "http://YOUTUBESEARCH/YoutubeSearch/search?key={key}&part={part}&q={q}&type={type}&maxResults={maxResults}";

                Map<String, String> params = new HashMap<String, String>();
                params.put("key", key);
                params.put("part", part);
                params.put("q", q);
                params.put("type", type);
                params.put("maxResults", maxResults);

                search_service.addtoKey(key, q);

                return restTemplate.getForObject(url, Object.class, params);
            } else {

                search_service.addtoKey(key, q);

                return "I'm search server from zone : " + zone + " , work on port : " + port + "\n" + "Max search tries !! ";
            }
        }





    @HystrixCommand(fallbackMethod = "alter",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "60000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
            //@HystrixProperty(name = "execution.timeout.enabled",value = "true"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")

    })
    @GetMapping("/search_B")
    public Object search_B(@RequestParam String key,
                         @RequestParam String part,
                         @RequestParam String q,
                         @RequestParam String type,
                         @RequestParam String maxResults
    ) throws InterruptedException {
        LOGGER.info("Inside microservice2 search_B() method and returning response for the requested resource.");

        System.out.println("I'm search server from zone : " + zone + " , work on port : " + port);

        if (search_service.checkkey(key, q)) {


            String url = "http://YOUTUBESEARCH/YoutubeSearch/search_B";

            String result="I'm search server from zone : " + zone
                    + " , work on port : " + port
                    +"\n"+restTemplate.getForObject(url, String.class);

            return result;
        } else {

            search_service.addtoKey(key, q);

            return "I'm search server from zone : " + zone + " , work on port : " + port + "\n" + "Max search tries !! ";
        }
    }

    public String alter (@RequestParam String key,
                           @RequestParam String part,
                           @RequestParam String q,
                           @RequestParam String type,
                           @RequestParam String maxResults
                        ) {
                LOGGER.info("Inside microservice2 alter() method ");
                return "alter : YOUTUBESEARCH one is down !! ";
            }




}

