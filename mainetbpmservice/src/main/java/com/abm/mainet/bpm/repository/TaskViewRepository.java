package com.abm.mainet.bpm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bpm.domain.TaskView;

@Repository
public interface TaskViewRepository extends CrudRepository<TaskView, Long> {

    @Query("select t from TaskView t where t.applicationId =:applicationId or t.referenceId =:referenceId")
    List<TaskView> getAllTaskById(@Param("applicationId") Long applicationId, @Param("referenceId") String referenceId);

}
