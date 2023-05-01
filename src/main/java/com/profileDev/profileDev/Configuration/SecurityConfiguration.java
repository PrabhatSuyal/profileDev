package com.profileDev.profileDev.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            // enable in memory based authentication with a user named
            // "user" and "admin"
            .inMemoryAuthentication().withUser("prabhatuser").password("user").roles("USER").and()
            .withUser("prabhatadmin").password("admin").roles("USER", "ADMIN");
    }

    //Security based on ROLE
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()//spring app checks sequencelly the configuration. if initial config is defined for that user then checks it else move to next config and so on
            .antMatchers("/Profile/admin/**").hasAnyRole("ADMIN")               // allow only ADMIN
            .antMatchers("/Profile/**").hasAnyRole("ADMIN","USER").anyRequest() // allow both
            .fullyAuthenticated().and().httpBasic();                                             //prompting for authentication

        //http.authorizeRequests().antMatchers("/").permitAll();		                         // not prompting for authentication
    }

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
