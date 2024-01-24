package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.SystemModuleFunction;

/**
 * Repository : Sysmodfunction.
 */
public interface SysmodfunctionJpaRepository extends PagingAndSortingRepository<SystemModuleFunction, Long> {

    @Query("select sysmodfunction from SystemModuleFunction sysmodfunction "
            + " where sysmodfunction.smfcategory in ('U','S') and sysmodfunction.isdeleted=0 order by sysmodfunction.smfname")
    List<SystemModuleFunction> findAllMappingMenu();

    @Query("select s from SystemModuleFunction s where (s.smfaction=:smfaction or s.moduleFunction is null) and smfname=:smfname order by smfid")
    List<SystemModuleFunction> findBySmfaction(@Param("smfaction") String smfaction, @Param("smfname") String smfname);
   // US#34043
    @Query("select s.smfid from SystemModuleFunction s where (s.smfaction=:smfaction ) order by smfid")
    Long  findSmfIdBySmfaction(@Param("smfaction") String smfaction);
}
