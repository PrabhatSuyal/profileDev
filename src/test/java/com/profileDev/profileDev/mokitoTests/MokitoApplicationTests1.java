package com.profileDev.profileDev.mokitoTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profileDev.profileDev.Auditing.ProfileDtoAuditingRepository;
import com.profileDev.profileDev.Configuration.CustomControllerAdvice;
import com.profileDev.profileDev.email.EmailServiceImpl;
import com.profileDev.profileDev.repository.CredentialRepository;
import com.profileDev.profileDev.repository.ProfileImgRepository;
import com.profileDev.profileDev.service.ProfileDevService;
import org.junit.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
public class MokitoApplicationTests1 {

    @MockBean
    public ProfileDevService profileDevService;
    @MockBean
    public CustomControllerAdvice customControllerAdvice;          //need to be Autowired to make CustomControllerAdvice workable
    @MockBean
    public EmailServiceImpl emailService;
    @Mock
    Logger logger;

    @Autowired
    private Environment env;

    @MockBean
    private CredentialRepository credentialRepository;
    @MockBean
    private ProfileImgRepository profileImgRepository;
    @MockBean
    private ProfileDtoAuditingRepository profileDtoAuditingRepository;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private RabbitTemplate rabbitTemplate;
    //@MockBean
    //private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testgettest() throws Exception {
//logger.info("MokitoApplicationTests1 >> testgettest() > start");
        //Mockito.when(logger.isInfoEnabled()).thenReturn(false);
        //when(profileDevService.gettest()).thenReturn("abc");
        //when("abcd").thenReturn("abcd");
        //assertEquals("abc", "abc", "abc");
        //logger.info("MokitoApplicationTests1 >> testgettest() >> end");

        //Mockito.when(logger.isInfoEnabled()).thenReturn(false);

        //Mockito.when(mRepo.save(movie)).thenReturn(movie);

        //Mockito.when(movieImpl.insertMovie(ArgumentMatchers.any())).thenReturn(movie);

        //String json = mapper.writeValueAsString(movie);

        MvcResult requestResult = mockMvc.perform(get("/gettest").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andReturn();

        String result = requestResult.getResponse().getContentAsString();

        Assert.notNull(result);
    }


}
