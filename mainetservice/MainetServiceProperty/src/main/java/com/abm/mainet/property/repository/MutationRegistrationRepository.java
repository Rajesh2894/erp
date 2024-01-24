package com.abm.mainet.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.MutationRegistrationEntity;

public interface MutationRegistrationRepository extends JpaRepository<MutationRegistrationEntity, Long> {

    @Query("from MutationRegistrationEntity "

            + "  am  WHERE  am.orgId=:orgId and am.mutId=:mutId")
    MutationRegistrationEntity fetchMutationRegistrationById(@Param("mutId") long mutId, @Param("orgId") Long orgId);

    @Modifying
    @Transactional
    @Query("update MutationRegistrationEntity a set a.applicationNo=:applicationNo  where  a.mutId=:mutId and a.orgId=:orgId")
    void updateMutationRegistrationByMutId(@Param("applicationNo") Long applicationNo, @Param("mutId") long mutId,
            @Param("orgId") long orgId);

}
