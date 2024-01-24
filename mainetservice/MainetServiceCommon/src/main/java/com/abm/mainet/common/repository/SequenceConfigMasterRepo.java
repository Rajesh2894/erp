package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.SequenceConfigMasterEntity;
import com.abm.mainet.common.domain.TbComparamMasEntity;

/**
 * @author sadik.shaikh
 *
 */
public interface SequenceConfigMasterRepo extends JpaRepository<SequenceConfigMasterEntity, Long> {

	@Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
			+ "where tbComparamMasEntity.cpmModuleName =:department " + "order by tbComparamMasEntity.cpmPrefix")
	List<TbComparamMasEntity> findByDepartment(@Param("department") String department);
}
