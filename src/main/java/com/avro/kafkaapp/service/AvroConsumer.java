package com.avro.kafkaapp.service;

import com.avro.kafkaapp.model.CustomerResponse;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author sunil
 * @project KafkaAvroApp
 * @created 2021/06/18 10:04 PM
 */

@Service
public class AvroConsumer {

    public List<CustomerResponse> consumeMessage () {
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers","localhost:9092");
        prop.setProperty("acks","1");
        prop.setProperty("retries","10");
        prop.setProperty("key.deserializer", StringDeserializer.class.getName());
        prop.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
        prop.setProperty("schema.registry.url","http://localhost:8081");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleAvroConsumer");
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        prop.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);

        KafkaConsumer<String, GenericRecord> kafkaConsumer = new KafkaConsumer<String, GenericRecord>(prop);

        String topic = "avro-topic";

        kafkaConsumer.subscribe(Collections.singletonList(topic));

        List<CustomerResponse> cusumerMessageList = new ArrayList<>();


        try {
            while (true) {
                final ConsumerRecords<String, GenericRecord> consumerRecords = kafkaConsumer.poll(100);

                if ( consumerRecords.count()==0 ) {
                    Thread.sleep(1000);
                    break;
                }

                consumerRecords.forEach(record -> {

                    GenericRecord rec = record.value();

                    CustomerResponse customer = new CustomerResponse();
                    customer.setFirstName(rec.get("first_name").toString());
                    customer.setLastName(rec.get("last_name").toString());
                    customer.setAge((Integer) rec.get("age"));

                    cusumerMessageList.add(customer);
                });

                kafkaConsumer.commitAsync();
            }

        } catch(Exception e) {
            System.out.println("Exception occured while consuing messages :" +e);
        }finally {
            kafkaConsumer.close();
        }

        return cusumerMessageList;
    }
}
