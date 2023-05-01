package com.profileDev.profileDev.Configuration;

import com.profileDev.profileDev.dto.CredentialDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RmqConsumer {              // as soon as rabbitTemplate.convertAndSend() fun run it will send object into RMQ queue

    @Value("${RMQ.Queue.Queue1}")
    public String queue;
    @Value("${RMQ.Exchange.Excahange1}")
    public String exchange1;
    @Value("${RMQ.Key.Key1}")
    public String key1;

    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange1);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(key1);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    //Consumer for RabbitMQ. So that queue will always be empty bcoz as soon object comes into queue it got consumed. so disable it to stay objects in queue
    @RabbitListener(queues = {"${RMQ.Queue.Queue1}"})
    public void consumeMessagefromQueue(CredentialDTO credentialDTO){
        System.out.println("#### the value from RMQ queue is : "+credentialDTO);
    }

}
