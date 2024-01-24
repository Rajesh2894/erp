package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.SequenceConfigMasterEntity;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.master.dto.TbComparamMas;

/**
 * @author sadik.shaikh
 *
 */
public interface ISequenceConfigMasterService {

	boolean saveSequenceConfig(SequenceConfigMasterDTO configMasterDTO);

	List<SequenceConfigMasterDTO> searchSequenceData(Long orgid, Long seqName, Long deptId, Long seqType, Long catId,
			String seqStatus);

	SequenceConfigMasterDTO searchSequenceById(Long seqConfigId, Long orgId);

	/*
	 * boolean checkSequenceByPattern(Long orgId,Long seqName,Long catId,Long
	 * deptId,Long secType, String status);
	 */
	boolean checkSequenceByPattern(Long orgId, Long deptId, Long seqName);

	List<TbComparamMas> findByDepartment(final String department);

	List<TbComparamMas> findAll();

	/*
	 * SequenceConfigMasterDTO loadSequenceData(Long orgId, Long seqName, Long
	 * catId, Long deptId, Long secType);
	 */
	
	SequenceConfigMasterDTO loadSequenceData(Long orgId, Long deptId, String tbName,String colName);

	String getDeptName(Long id, SequenceConfigMasterDTO configMasterDTO);

	String getSeqTypeName(SequenceConfigMasterDTO configMasterDTO, Long orgId);

	String getSeqTBName(SequenceConfigMasterDTO configMasterDTO, Long orgId);

}
