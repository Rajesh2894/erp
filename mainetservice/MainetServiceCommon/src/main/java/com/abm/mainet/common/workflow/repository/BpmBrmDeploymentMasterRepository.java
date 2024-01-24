package com.abm.mainet.common.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.workflow.domain.BpmBrmDeploymentMaster;

public interface BpmBrmDeploymentMasterRepository extends JpaRepository<BpmBrmDeploymentMaster, Long> {

    List<BpmBrmDeploymentMaster> findByGroupIdAndArtifactIdAndVersionAndStatus(String groupId, String artifactId, String version, String status);
   
    @Modifying
    @Query("UPDATE BpmBrmDeploymentMaster b SET b.status ='N' where b.artifactId =:artifactId and b.groupId =:groupId")
    void updateDeploymentMasterStatus(@Param("artifactId") String artifactId, @Param("groupId") String groupId);
}
