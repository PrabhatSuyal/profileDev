package com.profileDev.profileDev.entity;

import com.profileDev.profileDev.Auditing.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Credential{

    @Id
    private String name;
    private String password;
    private String role;
}
