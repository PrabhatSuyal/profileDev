package com.profileDev.profileDev.Auditing;

import com.profileDev.profileDev.entity.Credential;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
//@Entity
public class Auditable {            //need to Stores the delete and update actions/query response also in Audit table in DB

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //protected int id;
    @CreatedBy
    protected String createdBy;
    @Id                                         // put create time id bcoz due to other attr records are overriding in Audit table
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date createdDate;
    @LastModifiedBy
    protected String lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    protected Date lastModifiedData;
}
