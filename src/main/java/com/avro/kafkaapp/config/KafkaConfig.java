package com.avro.kafkaapp.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * @author sunil
 * @project kafka-avro-app-main
 * @created 2021/06/22 12:54 AM
 */

@Configuration
public class KafkaConfig {

    @Value("${kafka.topics}")
    String topicList;

    @Value("${kafka.bootstrap.servers}")
    String bootStrapServer;
    
    @Bean
    public void createTopic() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);

        AdminClient admin = AdminClient.create(config);

        Collection<NewTopic> topics = new ArrayList<>();

        Arrays.stream(topicList.split(",")).forEach( topic ->{
            NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
            topics.add(newTopic);
        });
        admin.createTopics(topics);
    }

    @Bean
    public String getName() {
        return "Sunil";
    }
}
