package com.abm.mainet.bpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bpm.domain.BpmDeployment;

@Repository
public interface BpmDeploymentRepository extends JpaRepository<BpmDeployment, Long> {

    @Query("Select d from BpmDeployment d where d.artifactId=:artifactId and d.bpmRuntime=:bpmRuntime and d.status='Y'")
    BpmDeployment findByArtifactIdAndBpmRuntime(@Param("artifactId") String artifactId, @Param("bpmRuntime") String bpmRuntime);

    @Query("Select d from BpmDeployment d where d.artifactId=:artifactId and d.status='Y'")
    BpmDeployment findByArtifactId(@Param("artifactId") String artifactId);

    // getting deployment by deployment Id
    @Query("Select d from BpmDeployment d where d.id=:bpmDeploymentId")
    BpmDeployment findById(@Param("bpmDeploymentId") Long bpmDeploymentId);
}
