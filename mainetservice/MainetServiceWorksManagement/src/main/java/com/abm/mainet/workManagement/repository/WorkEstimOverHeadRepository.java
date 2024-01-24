package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.workManagement.domain.WorkEstimOverHeadDetails;

public interface WorkEstimOverHeadRepository extends CrudRepository<WorkEstimOverHeadDetails, Long> {

    @Modifying
    @Query("update WorkEstimOverHeadDetails set active = 'N'  where overHeadId in :removeWorkIdList")
    void updateDeletedFlagForOverHeads(@Param("removeWorkIdList") List<Long> overHeadRemoveById);

}
