package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbResource;


@Repository
public interface TbResourceRepository extends PagingAndSortingRepository<TbResource, Long> {

	@Query("SELECT mas FROM TbResource mas")
	List<TbResource> getAllResources();

	@Query("SELECT mas FROM TbResource mas where mas.resId=:res_id")
	TbResource getResourceMasById(@Param("res_id") Long resId);
	
	@Query("SELECT mas FROM TbResource mas where mas.pageId=:pageId")
	List<TbResource> getSingleResourceDtls(@Param("pageId")String pageId);

}