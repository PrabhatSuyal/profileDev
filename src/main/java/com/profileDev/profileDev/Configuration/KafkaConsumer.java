package com.profileDev.profileDev.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class KafkaConsumer {

    @KafkaListener(topics = "kafka_Topic1" , groupId = "kafka_Group1")
    public  void listenToTopic(String receiveMessage){
        System.out.println("#### the value from Kafka queue is : "+receiveMessage);
    }
}
