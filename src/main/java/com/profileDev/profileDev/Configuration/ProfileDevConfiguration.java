package com.profileDev.profileDev.Configuration;

import com.profileDev.profileDev.Auditing.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



}
