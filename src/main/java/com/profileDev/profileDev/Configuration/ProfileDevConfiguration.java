package com.profileDev.profileDev.Configuration;

import com.profileDev.profileDev.Auditing.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableJpaAuditing
@EnableCaching
@EnableWebSecurity
public class ProfileDevConfiguration {

    @Bean
    public ModelMapper modelMapper() {   //add this otherwise get error "Consider defining a bean of type 'org.modelmapper.ModelMapper' in your configuration."
        return new ModelMapper();
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }



}
