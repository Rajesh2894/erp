package com.abm.mainet.tradeLicense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.tradeLicense.domain.TbMtlNoticeMas;

public interface NoticeDetailRepository extends CrudRepository<TbMtlNoticeMas, Long>{
	
	@Query("select p from TbMtlNoticeMas p where p.trdId =:trdId and p.orgId =:orgId")
	List<TbMtlNoticeMas> getNoticeDataById(@Param("trdId") Long trdId, @Param("orgId") Long orgId);

	
	@Query("select p from TbMtlNoticeMas p where p.noticeNo =:noticeNo and p.orgId =:orgId")
	List<TbMtlNoticeMas> getNoticeDataByNoticeNo(@Param("noticeNo") Long noticeNo, @Param("orgId") Long orgId);
	
}
