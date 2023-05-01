package com.profileDev.profileDev.service;


import com.profileDev.profileDev.Configuration.FileCompressor;
import com.profileDev.profileDev.dto.CredentialDTO;
import com.profileDev.profileDev.dto.ProfileDTO;
import com.profileDev.profileDev.entity.Credential;
import com.profileDev.profileDev.entity.ProfileImg;
import com.profileDev.profileDev.repository.CredentialRepository;
import com.profileDev.profileDev.repository.ProfileImgRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotesProfileDevService {

    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private ProfileImgRepository profileImgRepository;
    @Autowired
    private ModelMapper modelMapper;

    public String createProfile(ProfileDTO profileDTO) throws IOException {
        Credential credential = modelMapper.map(profileDTO,Credential.class);
        ProfileImg profileImg = modelMapper.map(profileDTO,ProfileImg.class);
        //ProfileImg profileImg = new ProfileImg();// = new ProfileImg(profileDTO.getName(), file);
        System.out.println("## before exception");
        System.out.println("##value of profileDTO.getImg().getBytes()"+profileDTO.getImg());
        System.out.println("##value of profileDTO.getImg().getBytes()"+profileDTO.getImg().getBytes());
        System.out.println("##value of FileCompressor.compressImage(profileDTO.getImg().getBytes())"+FileCompressor.compressImage(profileDTO.getImg().getBytes()));
        System.out.println("##value of profileDTO.getImg().getBytes()"+profileDTO.getImg().getBytes());

        try {
            byte[] imgByteArr = FileCompressor.compressImage(profileDTO.getImg().getBytes());
            //String img = new String(FileCompressor.compressImage(profileDTO.getImg().getBytes()));//
            System.out.println("##value of new String(FileCompressor.compressImage(profileDTO.getImg().getBytes()))"+new String(FileCompressor.compressImage(profileDTO.getImg().getBytes())));
            profileImg.setImg(imgByteArr); //(img.substring(0,10));     //getting this error if inserting all String "Data truncation: Data too long for column 'img' at row 1"
        }catch (Exception e){
            System.out.println("## exception is assigning image to entity");
            e.printStackTrace();
        }
        //profileImg.setName(profileDTO.getName());
        //profileImg.setImgfile(String.valueOf(file));//profileImg.setImgfile(Arrays.toString(file.getBytes()));

        //CredentialDTO credentialDTO = modelMapper.map(credentialRepository.save(credential),CredentialDTO.class); // this is working in reverse order we just need to use modelMapper.map()
        //credentialRepository.save(credential);      //we can directly save to Entity, no use of saving it to DTO first
        //ProfileDTO profileDTO = profileImgRepository.save(profileImg);       //#doubt this should work in reverse order
        credentialRepository.save(credential);      //we can directly save to Entity, no use of saving it to DTO first
        System.out.println("## saved credential to entity");
        profileImgRepository.save(profileImg);
        System.out.println("## saved profileImg to entity");
        return "##Profile Created";
    }

    //Convert Entity array to DTO array
    public List<CredentialDTO> getProfiles1() {
        return Arrays.asList(modelMapper.map(credentialRepository.findAll(), CredentialDTO[].class));  //method2 modelMapper.map(credentialRepository.findAll(), new TypeToken<List<CredentialDTO>>(){}.getType());// method3 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }
    public List<CredentialDTO> getProfiles(){
        return credentialRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    private CredentialDTO convertEntityToDto(Credential credential) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CredentialDTO credentialDTO = new CredentialDTO();
        credentialDTO = modelMapper.map(credential,CredentialDTO.class);
        return credentialDTO;
    }
}
