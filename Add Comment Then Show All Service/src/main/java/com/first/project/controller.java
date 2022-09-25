package com.first.project;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/Comment")
public class controller {

    private final static Logger LOGGER = LoggerFactory.getLogger(controller.class);


    @Value("${eureka.instance.metadataMap.zone}")
    private String zone;

    @Value("${server.port}")
    private String port;

	
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;




//	@HystrixCommand(fallbackMethod = "alter",commandProperties = {
//            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
//            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
//            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "60000"),
//            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
//            //@HystrixProperty(name = "execution.timeout.enabled",value = "true"),
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "10000")
//
//    }
//    )
    @PostMapping("/AddShow")
    public Object add_sequence(
                           @RequestBody myBody B,
                           @RequestParam String part,
                           @RequestHeader(value="Authorization") String Authorization
                        ) throws InterruptedException {

        LOGGER.info("Inside microservice add_sequence() method and returning response for the requested resource.");

        System.out.println("I'm ADD + SHOW comments  server from zone : "+zone+" , work on port : "+port);
		 
         String url="http://YOUTUBEADDCOMMENT/YoutubeComment/add?part={part}&textOriginal={textOriginal}&videoId={videoId}";

        Map<String,String>params=new HashMap<String,String>();
        params.put("part",part);
        params.put("textOriginal",B.textOriginal);
        params.put("videoId",B.videoId);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Authorization);
        HttpEntity entity = new HttpEntity(headers);

       restTemplate.exchange(url, HttpMethod.POST,entity,Object.class,params);



        url="http://YOUTUBESHOWCOMMENTS/YoutubeShowComments/show?part={part}&videoId={videoId}";


       // Map<String,String>params=new HashMap<String,String>();
        params.remove("textOriginal");

        return restTemplate.exchange(url, HttpMethod.GET,entity,Object.class,params);

    }



    ///////////////////////////////////////////////////////////////////////////



//    	@HystrixCommand(fallbackMethod = "alter2",commandProperties = {
//            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
//            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
//            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "60000"),
//            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
//            //@HystrixProperty(name = "execution.timeout.enabled",value = "true"),
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "10000")
//
//    }
//    )
    @GetMapping("/AddShow_A")
    public Object add_sequence_A(         ///A->B ,A->C
        ) throws InterruptedException {

        LOGGER.info("Inside microservice AddShow_A() method and returning response for the requested resource.");

        System.out.println("I'm ADD + SHOW comments  server from zone : "+zone+" , work on port : "+port);

        String url="http://YOUTUBEADDCOMMENT/YoutubeComment/justadd";


        restTemplate.getForObject(url,String.class);


        url="http://YOUTUBESHOWCOMMENTS/YoutubeShowComments/justshow";


        return restTemplate.getForObject(url,String.class);
    }







    //////////////////////////////////////////////////////////////////////////////////////////

//    @HystrixCommand(fallbackMethod = "alter2",commandProperties = {
//            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
//            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "1"),
//            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "60000"),
//            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
//            //@HystrixProperty(name = "execution.timeout.enabled",value = "true"),
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "10000")
//
//    }
//    )

    @GetMapping("/AddShow_B") /// A->B->C

    public Object add_sequence_B(  ) throws InterruptedException {

        LOGGER.info("Inside microservice AddShow_B() method and returning response for the requested resource.");

        System.out.println("I'm ADD + SHOW comments  server from zone : "+zone+" , work on port : "+port);

        String url="http://YOUTUBEADDCOMMENT/YoutubeComment/add_B";



        return restTemplate.getForObject(url,String.class);
}









    //////////////////////////////////////////////////////////////////////////////////////////

//    @HystrixCommand(fallbackMethod = "alter2",commandProperties = {
//            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
//            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
//            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "60000"),
//            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
//            //@HystrixProperty(name = "execution.timeout.enabled",value = "true"),
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "10000")
//
//    }
//    )

    @GetMapping("/AddShow_C")
    public Object add_parellel( /// A->B & A->C
    ) throws InterruptedException, ExecutionException {

        LOGGER.info("Inside microservice AddShow_C() method and returning response for the requested resource.");

        System.out.println("I'm ADD + SHOW comments  server from zone : "+zone+" , work on port : "+port);




        send_add();
        send_show();

        sleep(500);
        return "done";

    }

    @Async
    private Object send_add(){

     return  webClientBuilder
                .baseUrl("http://YOUTUBEADDCOMMENT")
                .build()
                .get()
                .uri("/YoutubeComment/justadd")


                    .retrieve().bodyToMono(Void.class).subscribe();
             /*.exchange()
                .block()
                .bodyToMono(Mono.class)
                .block();
*/
        /*WebClient.create("http://YOUTUBEADDCOMMENT").get()
                .uri("/YoutubeComment/justadd")
                .retrieve()
                 .bodyToFlux(String.class);
    */}

    @Async
    private Object send_show(){

        return  webClientBuilder
                .baseUrl("http://YOUTUBESHOWCOMMENTS")
                .build()
                .get()
                .uri("/YoutubeShowComments/justshow")
                .retrieve().bodyToMono(Void.class).subscribe();


        /*.exchange()
                .block()
                .bodyToMono(Mono.class)
                .block();
*/
       /* WebClient.create("http://YOUTUBESHOWCOMMENTS").get()
                .uri("/YoutubeShowComments/justshow")
                .retrieve()
                 .bodyToFlux(String.class);
    */
    }



    public String alter(@RequestBody myBody B,
                        @RequestParam String part,
                        @RequestHeader(value="Authorization") String Authorization
    ){

        LOGGER.info("Inside microservice alter() method and returning response for the requested resource.");

        return "alter : service is down !! ";
    }

    public String alter2(

    ){

        LOGGER.info("Inside microservice alter() method and returning response for the requested resource.");

        return "alter : service is down !! ";
    }

}
