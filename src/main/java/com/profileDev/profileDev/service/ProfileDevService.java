package com.profileDev.profileDev.service;


import com.profileDev.profileDev.Auditing.ProfileDtoAuditing;
import com.profileDev.profileDev.Auditing.ProfileDtoAuditingRepository;
import com.profileDev.profileDev.Configuration.FileCompressor;
import com.profileDev.profileDev.Exceptions.ServiceException;
import com.profileDev.profileDev.dto.CredentialDTO;
import com.profileDev.profileDev.dto.ProfileDTO;
import com.profileDev.profileDev.dto.ProfileImgDTO;
import com.profileDev.profileDev.entity.Credential;
import com.profileDev.profileDev.entity.ProfileImg;
import com.profileDev.profileDev.repository.CredentialRepository;
import com.profileDev.profileDev.repository.ProfileImgRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
public class ProfileDevService {

    /*@Value("${RMQ.Exchange.Excahange1}")
    public String exchange1;*/
    @Autowired
    private Environment env;

    private static final Logger logger = LoggerFactory.getLogger(ProfileDevService.class);
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private ProfileImgRepository profileImgRepository;
    @Autowired
    private ProfileDtoAuditingRepository profileDtoAuditingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //@Autowired
    //private KafkaTemplate<String, String> kafkaTemplate;


    public String gettest() {
        return " exc is : "+env.getProperty("RMQ.Exchange.Excahange1")+" key is : "+env.getProperty("RMQ.Key.Key1");
    }
    public CredentialDTO createProfile(ProfileDTO profileDTO) throws IOException {
        Credential credential = modelMapper.map(profileDTO,Credential.class);
        ProfileImg profileImg = modelMapper.map(profileDTO,ProfileImg.class);
        ProfileDtoAuditing profileDtoAuditing = modelMapper.map(profileDTO, ProfileDtoAuditing.class);
        logger.warn("### Creating a user : "+credential.toString());
        try {
            profileImg.setImg(FileCompressor.compressImage(profileDTO.getImg().getBytes())); //(img.substring(0,10));     //getting this error if inserting all String "Data truncation: Data too long for column 'img' at row 1"
            profileDtoAuditing.setImg(profileImg.getImg());                                  //assigning image to auditing Entity
        }catch (Exception e){
            logger.info("### exception in assigning compressed image to entity with error : "+e.toString()); //e.printStackTrace();
        }

        profileImgRepository.save(profileImg);
        profileDtoAuditingRepository.save(profileDtoAuditing);  // saving Entity for auditing purpose in db
        // publisher for rabbitMQ queue
        rabbitTemplate.convertAndSend(env.getProperty("RMQ.Exchange.Excahange1"), env.getProperty("RMQ.Key.Key1"), modelMapper.map(credential,CredentialDTO.class));//passing credentialDTO to RMQ
        //kafkaTemplate.send("kafka_Topic1", modelMapper.map(credential,CredentialDTO.class).toString());
        return modelMapper.map(credentialRepository.save(credential),CredentialDTO.class);
    }

    public List<CredentialDTO> getProfiles() {
        logger.info("### fetching value of credentialRepository.findAll() "+credentialRepository.findAll());
        return Arrays.asList(modelMapper.map(credentialRepository.findAll(), CredentialDTO[].class));  //method2 modelMapper.map(credentialRepository.findAll(), new TypeToken<List<CredentialDTO>>(){}.getType());// method3 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    public CredentialDTO getProfileByName(String profileName) {
        logger.info("### fetching value of credentialRepository.findByName(profileName) "+credentialRepository.findByName(profileName));
        //return modelMapper.map(credentialRepository.findByName(profileName), CredentialDTO.class);
        if(profileName.isEmpty() || profileName.length()==0) {throw new ServiceException("601", "name cant be empty. provide a name.");}
        try{
            System.out.println("### inside try service");
            return modelMapper.map(credentialRepository.findByName(profileName), CredentialDTO.class);
        }catch (IllegalArgumentException e){      //illegal args excep is used bcoz of error "source cannot be null" of ModelMapper
            System.out.println("### 602");
            throw new ServiceException("602","cant find any profile by name in DB to pass it in ModelMapper.");
        }catch (Exception e){
            System.out.println("### 603");
            throw new ServiceException("603","some unknown error occurred in getProfileByName in service."+e.getMessage());
        }
    }

    public List<CredentialDTO> getProfilesByRole(String profileRole) {
        logger.info("### fetching value of credentialRepository.findAllByRole(profileRole) "+credentialRepository.findAllByRole(profileRole));
        return Arrays.asList(modelMapper.map(credentialRepository.findAllByRole(profileRole), CredentialDTO[].class));
    }

    public List<CredentialDTO> getProfilesByRoleStoredProcedure(String profileRole) {
        logger.info("### fetching value of credentialRepository.findAllByRole(profileRole) "+credentialRepository.findAllByRole(profileRole));
        return Arrays.asList(modelMapper.map(credentialRepository.getProfilesByRoleStoredProcedure(profileRole), CredentialDTO[].class));
    }

    public CredentialDTO deleteProfileByName(String profileName) {
        CredentialDTO credentialDTO = modelMapper.map(credentialRepository.findByName(profileName),CredentialDTO.class);
        logger.info("### deleting value of credentialRepository.deleteByName(profileName) "+credentialDTO);
        credentialRepository.delete(credentialRepository.findByName(profileName));
        profileImgRepository.delete(profileImgRepository.findByName(profileName));
        return credentialDTO;
    }

    public List<CredentialDTO> deleteProfilesByRole(String profileRole) {
        List<CredentialDTO> credentialDTOList = Arrays.asList(modelMapper.map(credentialRepository.findAllByRole(profileRole),CredentialDTO[].class));
        logger.info("### deleting value of credentialRepository.deleteProfilesByRole(profileRole) "+credentialDTOList);
        profileImgRepository.deleteProfilesImgByRole(profileRole);// it is a custom query in Repo
        credentialRepository.deleteAll(Arrays.asList(credentialRepository.findAllByRole(profileRole)));
        return credentialDTOList;
    }

    public ProfileImgDTO getProfileImgByName(String profileName) {
        logger.info("### fetching value of profileImgRepository.getProfileImgByName(profileName) "+profileImgRepository.findByName(profileName));
        return modelMapper.map(profileImgRepository.findByName(profileName), ProfileImgDTO.class); // need to do decompress the image attribute
    }

    public List<Credential> findAllCredsSortedByRole() {
        return credentialRepository.findAllCredsSortedByRole();
    }


}
