package com.profileDev.profileDev.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileDTO {
    private String name;
    private String password;
    private String role;
    private MultipartFile img;
}
