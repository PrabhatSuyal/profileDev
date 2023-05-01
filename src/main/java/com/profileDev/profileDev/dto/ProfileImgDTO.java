package com.profileDev.profileDev.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImgDTO {
    private String name;
    private MultipartFile img;
}
