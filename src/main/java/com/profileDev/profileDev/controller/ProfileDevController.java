package com.profileDev.profileDev.controller;


import com.profileDev.profileDev.Configuration.CustomControllerAdvice;
import com.profileDev.profileDev.Exceptions.ControllerException;
import com.profileDev.profileDev.Exceptions.ServiceException;
import com.profileDev.profileDev.ProfileDevApplication;
import com.profileDev.profileDev.dto.CredentialDTO;
import com.profileDev.profileDev.dto.ProfileDTO;
import com.profileDev.profileDev.dto.ProfileImgDTO;
import com.profileDev.profileDev.entity.Credential;
import com.profileDev.profileDev.service.ProfileDevService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Profile")
public class ProfileDevController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileDevController.class);		//these logger belongs to import org.slf4j.Logger; give param as current class

    @Autowired
    public ProfileDevService profileDevService;
    @Autowired
    public CustomControllerAdvice customControllerAdvice;               //need to be Autowired to make CustomControllerAdvice workable

    @GetMapping("/gettest")
    public String gettest(HttpServletRequest httpServletRequest){
        return profileDevService.gettest();
    }

    @PostMapping("/createProfile")
    public CredentialDTO createProfile(ProfileDTO profileDTO, @RequestParam("image") MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {    //@RequestBody //@ModelAttribute ProfileDTO profileDTO
        profileDTO.setImg(file);
        logger.info("## URL \"localhost:8080/Profile/createProfile\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.createProfile(profileDTO);
    }

    @GetMapping("/getProfiles")
    @CachePut("ProfileCache")
    public List<CredentialDTO> getProfiles(HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/getProfiles\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.getProfiles();
    }

    @GetMapping("/getProfilesFromCache")
    @Cacheable("ProfileCache")
    public List<CredentialDTO> getProfilesFromCache(HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/getProfiles\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.getProfiles();
    }

    @GetMapping("/deleteProfilesInCache")
    @CacheEvict("ProfileCache")
    @Scheduled(fixedRateString = "${caching.spring.hotelListTTL}")
    public List<CredentialDTO> deleteProfilesInCache(HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/getProfiles\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.getProfiles();
    }

    @GetMapping("/getProfileByName/{profileName}")
    public ResponseEntity<?> getProfile(@PathVariable String profileName, HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/getProfile/\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        //return new ResponseEntity<CredentialDTO>(profileDevService.getProfileByName(profileName),HttpStatus.FOUND);
            try {
                System.out.println("## inside try");
                return new ResponseEntity<CredentialDTO>(profileDevService.getProfileByName(profileName),HttpStatus.FOUND);
            }catch (ServiceException se){
                System.out.println("## inside catch");
                ControllerException ce = new ControllerException(se.getErrorCode(),se.getErrorMessage());
                if(se.getErrorCode().equals("601")){
                    System.out.println("## 601");
                    return new ResponseEntity<ControllerException>(ce,HttpStatus.LENGTH_REQUIRED);
                } else if (se.getErrorCode().equals("602")) {
                    System.out.println("## 602");
                    throw ce;
                    //return new ResponseEntity<ControllerException>(ce,HttpStatus.NOT_FOUND);
                } else{
                    System.out.println("## 603");
                    return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
                }
            }
    }

    @GetMapping("/getProfileImgByName/{profileName}")
    public ProfileImgDTO getProfileImgByName(@PathVariable String profileName, HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/getProfileImgByName/\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.getProfileImgByName(profileName);
    }

    @GetMapping("/getProfilesByRole")
    public List<CredentialDTO> getProfilesByRole(@RequestParam String profileRole, HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/getProfiles?profileRole=\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.getProfilesByRole(profileRole);
    }

    @GetMapping("/getProfilesByRoleStoredProcedure")                    // custom JPQL stored procedure
    public List<CredentialDTO> getProfilesByRoleStoredProcedure(@RequestParam String profileRole, HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/getProfilesByRoleStoredProcedure?profileRole=\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.getProfilesByRoleStoredProcedure(profileRole);
    }

    @DeleteMapping("/deleteProfileByName/{profileName}")
    //@CacheEvict("ProfileCache")     //ideally it should be uncommented, for testing the cashing pattern comment it
    public CredentialDTO deleteProfileByName(@PathVariable String profileName, HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/deleteProfileByName/profileName=\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.deleteProfileByName(profileName);
    }

    @DeleteMapping("/deleteProfilesByRole")
    //@CacheEvict("ProfileCache")     //ideally it should be uncommented, for testing the cashing pattern comment it
    public List<CredentialDTO> deleteProfilesByRole(@RequestParam String profileRole, HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/deleteProfilesByRole?profileRole=\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.deleteProfilesByRole(profileRole);
    }

    @GetMapping("/admin")
    public String adminPage(HttpServletRequest httpServletRequest){
        logger.info("## URL \"localhost:8080/Profile/admin is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return "admin Page";
    }

    @GetMapping("/findAllCredsSortedByRole/{profileRole}")                  //custom JPQL query
    public List<Credential> findAllCredsSortedByRole(@PathVariable String profileRole, HttpServletRequest httpServletRequest) {
        logger.info("## URL \"localhost:8080/Profile/findAllCredsSortedByRole/profileRole\" is hit by this system : ipaddress : "+httpServletRequest.getRemoteHost()+" ## user : "+httpServletRequest.getRemoteUser());
        return profileDevService.findAllCredsSortedByRole();
    }

}
