package com.profileDev.profileDev.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileDevAuditDTO {

    Date createdDate;
    String createdBy;
    String lastModifiedBy;
    Date lastModifiedData;
    String name;
    String role;


}
