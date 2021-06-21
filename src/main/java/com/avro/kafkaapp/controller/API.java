package com.avro.kafkaapp.controller;

import com.avro.kafkaapp.config.KafkaConfig;
import com.avro.kafkaapp.model.Customer;
import com.avro.kafkaapp.model.CustomerResponse;
import com.avro.kafkaapp.service.AvroConsumer;
import com.avro.kafkaapp.service.AvroProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sunil
 * @project KafkaAvroApp
 * @created 2021/06/18 11:11 PM
 */

@RestController
@RequestMapping("api")
public class API {

    @Autowired
    AvroProducer avroProducer;

    @Autowired
    AvroConsumer avroConsumer;

    @GetMapping("health")
    public String healthCheck(){
        return "OK";
    }

    @GetMapping(value = "send", produces=MediaType.APPLICATION_JSON_VALUE)
    public String sendMessage(@RequestParam("name") String name){
        Customer customer = Customer.newBuilder()
                .setFirstName(name)
                .setLastName("M")
                .setAge(22)
                .build() ;

        System.out.println(customer);

        String message = avroProducer.SendMessage(customer);

        return message;
    }

    @GetMapping(value = "consume")
    public List<CustomerResponse> consumeMesssage(){

        List<CustomerResponse> message = avroConsumer.consumeMessage();
        System.out.println(message);

        return message;
    }

    @Autowired
    KafkaConfig kafkaConfig;
    @GetMapping("test")
    public String test(){
        return kafkaConfig.getName();
    }
}
