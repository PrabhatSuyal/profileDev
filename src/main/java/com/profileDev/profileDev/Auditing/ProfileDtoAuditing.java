package com.profileDev.profileDev.Auditing;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
public class ProfileDtoAuditing extends Auditable { // created this class bcoz, it will maintain audits in separate table
                                                    // once try to merge Auditable and ProfileDTOAuditing classes
    //@Id
    private String name;
    private String password;
    private String role;
    @Lob                                            //if you want to store binary format data in DB
    @Column(name = "imageData", length = 1000)
    private byte[] img;
}
