package com.profileDev.profileDev.entity;

import com.profileDev.profileDev.Auditing.Auditable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
public class ProfileImg {
    @Id
    private String name;

    @Lob                                            //if you want to store binary format data in DB
    @Column(name = "imageData", length = 1000)
    private byte[] img;
}
