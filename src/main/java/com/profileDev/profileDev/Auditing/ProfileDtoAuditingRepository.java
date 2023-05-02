package com.profileDev.profileDev.Auditing;

import com.profileDev.profileDev.Auditing.ProfileDtoAuditing;
import com.profileDev.profileDev.dto.ProfileDevAuditDTO;
import com.profileDev.profileDev.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileDtoAuditingRepository extends JpaRepository<ProfileDtoAuditing, String> {


}
