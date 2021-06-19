package com.avro.kafkaapp.service;

import com.avro.kafkaapp.model.Customer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * @author sunil
 * @project KafkaAvroApp
 * @created 2021/06/18 7:11 PM
 */

@Service
public class AvroProducer {


    public String SendMessage(Customer customer) {
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers","localhost:9092");
        prop.setProperty("acks","1");
        prop.setProperty("retries","10");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        prop.setProperty("schema.registry.url","http://localhost:8081");


        KafkaProducer<String, Customer> kafkaProducer = new KafkaProducer<String, Customer>(prop);

        String topic = "avro-topic";

        ProducerRecord<String, Customer> rec = new ProducerRecord<String, Customer>(topic, customer);

        kafkaProducer.send(rec, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                e.printStackTrace();
                if(e == null){
                    System.out.println("Success");
                    System.out.println(recordMetadata);
                } else  {
                    e.printStackTrace();
                    System.out.println("hereh");
                }
            }
        });

        kafkaProducer.flush();
        kafkaProducer.close();


        return "Sent successfully";
    }
}
