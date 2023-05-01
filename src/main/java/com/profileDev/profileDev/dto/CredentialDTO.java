package com.profileDev.profileDev.dto;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;

@Data
public class CredentialDTO {
    private String name;
    private String password;
    private String role;

}
