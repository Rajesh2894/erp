package com.abm.mainet.intranet.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.intranet.domain.IntranetMaster;


@Repository
public interface IntranetRepository extends JpaRepository<IntranetMaster, Long> {
	//@Query("select d from IntranetMaster d where d.docToDate<=:docToDate and d.orgid=:orgid order by docCateType, docName, lmoddate desc")
	//List<IntranetMaster> findDocs(@Param("docToDate") Date docToDate, @Param("orgid")Long orgid);
	@Query("select d from IntranetMaster d where d.orgid=:orgid and d.docStatus='A' and d.attPath<>'' and date(d.docToDate) >= date(:docToDate) and d.deptId=:deptId or d.deptId=0 order by docCateType, docName, lmoddate desc")
	List<IntranetMaster> findDocs(@Param("orgid")Long orgid, @Param("docToDate") Date docToDate,@Param("deptId")Long deptId);
		
}

