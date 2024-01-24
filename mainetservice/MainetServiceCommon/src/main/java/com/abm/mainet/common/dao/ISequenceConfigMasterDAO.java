package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.SequenceConfigMasterEntity;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;

/**
 * @author sadik.shaikh
 *
 */
public interface ISequenceConfigMasterDAO {

	List<SequenceConfigMasterEntity> searchAdvertiserData(Long orgId, Long seqName, Long deptId, Long seqType,
			Long catId, String seqStatus);

	SequenceConfigMasterEntity searchSequenceById(Long seqConfigId, Long orgId);

	SequenceConfigMasterEntity loadSequenceData(Long orgId, Long deptId, Long seqNameId);

	boolean checkSequenceByPattern(Long orgId, Long deptId, Long seqNameId);
}
