package com.profileDev.profileDev.entity;

import com.profileDev.profileDev.Auditing.Auditable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Credential{

    @Id
    private String name;
    private String password;
    private String role;
}
