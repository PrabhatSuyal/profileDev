package com.profileDev.profileDev.Scheduled;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestScheduled {// need to create component of scheduled task separately bcoz it is giving warning with rest controller class

    @Scheduled(fixedRateString = "10000")
    @CacheEvict("ProfileCache")
    public void deleteProfilesInCache1(){
        System.out.println("TestScheduled >> .deleteProfilesInCache1()");
    }

}
