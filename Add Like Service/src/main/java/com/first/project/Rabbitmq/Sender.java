package com.first.project.Rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    @Autowired
    private RabbitTemplate template;

    public String sendtoQueue(String id, String rating,String author) {

    Message message=new Message();
    message.setId(id);
    message.setRating(rating);
    message.setAuthor(author);
    message.setType("A");


    template.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY, message);

        return "Success !!";
    }



    public String sendtoQueue_B(String id, String rating,String author) {

        Message message=new Message();
        message.setId(id);
        message.setRating(rating);
        message.setAuthor(author);
        message.setType("B");


        template.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY, message);

        return "Success !!";
    }

}
