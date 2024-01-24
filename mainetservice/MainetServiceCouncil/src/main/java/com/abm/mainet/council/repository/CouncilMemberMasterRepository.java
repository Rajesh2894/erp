package com.abm.mainet.council.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.council.domain.CouncilMemberMasterEntity;

@Repository
public interface CouncilMemberMasterRepository extends JpaRepository<CouncilMemberMasterEntity, Long> {

    @Query("select w from CouncilMemberMasterEntity w where w.orgId=:orgId")
    List<CouncilMemberMasterEntity> fetchAll(@Param("orgId") Long orgId);

    @Query("select m from CouncilMemberMasterEntity m where m.couMemName=:couMemName and m.couDOB=:couDOB and m.couMobNo =:couMobNo")
    List<CouncilMemberMasterEntity> validateMemberDoB(@Param("couMemName") String couMemName, @Param("couDOB") Date couDOB,
            @Param("couMobNo") Long couMobNo);

    @Query("select m from CouncilMemberMasterEntity m where m.couMemName=:couMemName and m.couDOB=:couDOB and m.couMobNo =:couMobNo and m.couId !=:couId")
    List<CouncilMemberMasterEntity> checkMemebrExist(@Param("couMemName") String couMemName, @Param("couDOB") Date couDOB,
            @Param("couMobNo") Long couMobNo, @Param("couId") Long couId);

}
