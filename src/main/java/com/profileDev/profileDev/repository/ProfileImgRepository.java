package com.profileDev.profileDev.repository;

import com.profileDev.profileDev.entity.Credential;
import com.profileDev.profileDev.entity.ProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, String> {
    ProfileImg findByName(String name);// input param should be same as that attribute of Entity

    @Transactional
    @Modifying
    @Query(value = "delete from ProfileImg where name in (select name from Credential where role = ?1)")
    public void deleteProfilesImgByRole(String profileRole);
}
