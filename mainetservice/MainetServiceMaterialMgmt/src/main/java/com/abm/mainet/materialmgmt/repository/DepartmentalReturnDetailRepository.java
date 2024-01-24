package com.abm.mainet.materialmgmt.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.DeptReturnEntity;
import com.abm.mainet.materialmgmt.domain.DeptReturnEntityDetail;


@Repository
public interface DepartmentalReturnDetailRepository extends JpaRepository<DeptReturnEntityDetail, Long> {

	
}


