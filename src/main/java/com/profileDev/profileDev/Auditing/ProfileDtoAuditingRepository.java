package com.profileDev.profileDev.Auditing;

import com.profileDev.profileDev.Auditing.ProfileDtoAuditing;
import com.profileDev.profileDev.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDtoAuditingRepository extends JpaRepository<ProfileDtoAuditing, String> {
}
