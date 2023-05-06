package com.profileDev.profileDev.mokitoTests;

import com.profileDev.profileDev.entity.Credential;
import com.profileDev.profileDev.repository.CredentialRepository;
import com.profileDev.profileDev.service.ProfileDevService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MokitoApplicationTests {
    @Autowired
    ProfileDevService profileDevService;
    @MockBean
    CredentialRepository credentialRepository;

    @Test
    public void testgetProfiles(){
        System.out.println("MokitoApplicationTests > testgetProfiles > pre.");
        Mockito.when(credentialRepository.findAll())
                .thenReturn(Stream.of(new Credential("name1","pwd1","Role1"))
                        .collect(Collectors.toList()));
        Assert.assertEquals(2, profileDevService.getProfiles().size());
        System.out.println("MokitoApplicationTests > testgetProfiles > post.");
    }


}
