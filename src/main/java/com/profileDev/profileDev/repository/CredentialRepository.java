package com.profileDev.profileDev.repository;

import com.profileDev.profileDev.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, String> {
    Credential findByName(String name);// input param should be same as that attribute of Entity
    Credential[] findAllByRole(String role);
    //Credential deleteByName(String name);
    //Credential deleteAllByRole(String role);

    @Query(value = "SELECT e FROM Credential e ORDER BY role")
    public List<Credential> findAllCredsSortedByRole();

    @Query(nativeQuery = true, value = "call getProfilesByRole(:profileRole)")
    List<Credential> getProfilesByRoleStoredProcedure(@Param("profileRole")String profileRole);
}
