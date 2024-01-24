package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ISequenceConfigMasterDAO;
import com.abm.mainet.common.domain.SequenceConfigDetEntity;
import com.abm.mainet.common.domain.SequenceConfigMasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.domain.TbComparamMasEntity;
import com.abm.mainet.common.dto.SequenceConfigDetDTO;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbComparamDet;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.mapper.TbComparamMasServiceMapper;
import com.abm.mainet.common.master.repository.TbComparamMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.repository.SequenceConfigMasterRepo;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author sadik.shaikh
 *
 */
@Service
public class SequenceConfigMasterServiceImpl implements ISequenceConfigMasterService {

	@Resource
	@Autowired
	SequenceConfigMasterRepo configMasterRepo;

	@Resource
	private TbComparamMasServiceMapper tbComparamMasServiceMapper;

	@Resource
	private TbDepartmentService tbDepartmentService;
	
	/*
	 * @Autowired CommonMasterUtility commonMasterUtility;
	 */

	@Resource
	private TbComparamMasJpaRepository tbComparamMasJpaRepository;

	private static final Logger LOGGER = Logger.getLogger(SequenceConfigMasterServiceImpl.class);

	@Transactional
	public boolean saveSequenceConfig(SequenceConfigMasterDTO configMasterDTO) {
		SequenceConfigMasterEntity configMasterEntity = mapDtoToEntity(configMasterDTO);
		// BeanUtils.copyProperties(configMasterDTO, configMasterEntity);
		
		try {
			configMasterEntity = configMasterRepo.save(configMasterEntity);
			return true;

		} catch (Exception e) {
			LOGGER.error("Exception occur while saving the squence configuration ", e);
			throw new FrameworkException("Exception occur while saving the squence configuration", e);
		}
	}

	private SequenceConfigMasterEntity mapDtoToEntity(SequenceConfigMasterDTO entryDto) {
		SequenceConfigMasterEntity entryEntity = new SequenceConfigMasterEntity();
		List<SequenceConfigDetEntity> detEntities = new ArrayList<>();
		BeanUtils.copyProperties(entryDto, entryEntity);
		if (CollectionUtils.isNotEmpty(entryDto.getConfigDetDTOs())) {
			entryDto.getConfigDetDTOs().forEach(det -> {
				SequenceConfigDetEntity detDto = new SequenceConfigDetEntity();
				BeanUtils.copyProperties(det, detDto);
				detDto.setSeqConfigMasterEntity(entryEntity);
				detEntities.add(detDto);
			});
		}
		entryEntity.setConfigDetEntities(detEntities);
		return entryEntity;
	}

	@Transactional
	@Override
	public List<SequenceConfigMasterDTO> searchSequenceData(Long orgId, Long seqName, Long deptId, Long seqType,
			Long catId, String seqStatus) {

		List<SequenceConfigMasterDTO> masterDtoList = new ArrayList<>();
		try {
			List<SequenceConfigMasterEntity> entityList = ApplicationContextProvider.getApplicationContext()
					.getBean(ISequenceConfigMasterDAO.class)
					.searchAdvertiserData(orgId, seqName, deptId, seqType, catId, seqStatus);
			if (entityList != null) {
				entityList.forEach(entity -> {
					SequenceConfigMasterDTO masterDto = new SequenceConfigMasterDTO();
					BeanUtils.copyProperties(entity, masterDto);
					
					List<SequenceConfigDetDTO> configDetDTOs = new ArrayList<SequenceConfigDetDTO>();
					for (SequenceConfigDetEntity configDetEntity : entity.getConfigDetEntities()) {

						SequenceConfigDetDTO configDetDTO = new SequenceConfigDetDTO();
						BeanUtils.copyProperties(configDetEntity, configDetDTO);

						configDetDTOs.add(configDetDTO);

					}
					if (StringUtils.isNotBlank(masterDto.getSeqStatus())) {
						if (StringUtils.equalsIgnoreCase(masterDto.getSeqStatus(), MainetConstants.FlagA)) {
							masterDto.setSeqStatus(MainetConstants.Common_Constant.ACTIVE);
						}
						if (StringUtils.equalsIgnoreCase(masterDto.getSeqStatus(), MainetConstants.FlagI)) {
							masterDto.setSeqStatus(MainetConstants.Common_Constant.INACTIVE);
						}
					}
					masterDto.setConfigDetDTOs(configDetDTOs);
					masterDtoList.add(masterDto);
				});
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the Advertiser Master Data", exception);
		}
		return masterDtoList;

	}

	@Transactional
	public SequenceConfigMasterDTO searchSequenceById(Long seqConfigId, Long orgId) {

		SequenceConfigMasterDTO configMasterDTO = new SequenceConfigMasterDTO();

		try {
			SequenceConfigMasterEntity sequenceConfigMasterEntity = ApplicationContextProvider.getApplicationContext()
					.getBean(ISequenceConfigMasterDAO.class).searchSequenceById(seqConfigId, orgId);

			List<SequenceConfigDetDTO> configDetDTOs = new ArrayList<SequenceConfigDetDTO>();

			if (sequenceConfigMasterEntity != null) {

				BeanUtils.copyProperties(sequenceConfigMasterEntity, configMasterDTO);

				for (SequenceConfigDetEntity configDetEntity : sequenceConfigMasterEntity.getConfigDetEntities()) {

					SequenceConfigDetDTO configDetDTO = new SequenceConfigDetDTO();
					BeanUtils.copyProperties(configDetEntity, configDetDTO);

					configDetDTOs.add(configDetDTO);

				}
				configMasterDTO.setConfigDetDTOs(configDetDTOs);
			}
		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the Advertiser Master Data", exception);
		}

		return configMasterDTO;

	}

	/*
	 * @Override
	 * 
	 * @Transactional public boolean checkSequenceByPattern(Long orgId, Long
	 * seqName, Long catId, Long deptId, Long secType, String status) {
	 * 
	 * boolean flag = false; try { flag =
	 * ApplicationContextProvider.getApplicationContext().getBean(
	 * ISequenceConfigMasterDAO.class) .checkSequenceByPattern(orgId, seqName,
	 * catId, deptId, secType, status);
	 * 
	 * } catch (Exception exception) { throw new
	 * FrameworkException("Error Occured While fetching the Advertiser Master Data",
	 * exception); }
	 * 
	 * return flag;
	 * 
	 * }
	 */
	@Override
	@Transactional
	public boolean checkSequenceByPattern(Long orgId,Long deptId,Long seqName) {

		boolean flag = false;
		try {
			flag = ApplicationContextProvider.getApplicationContext().getBean(ISequenceConfigMasterDAO.class)
					.checkSequenceByPattern(orgId,deptId,seqName);;

		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the Advertiser Master Data", exception);
		}

		return flag;

	}

	@Override
	@Transactional
	public List<TbComparamMas> findByDepartment(final String department) {
		List<TbComparamMasEntity> entities = new ArrayList<>();

		if (!StringUtils.isEmpty(department)) {
			entities = configMasterRepo.findByDepartment(department);
		}
		final List<TbComparamMas> beans = new ArrayList<>();

		for (final TbComparamMasEntity tbComparamMasEntity : entities) {
			TbComparamMas mapTbComparamMasEntityToTbComparamMas = tbComparamMasServiceMapper
					.mapTbComparamMasEntityToTbComparamMas(tbComparamMasEntity);
			List<TbComparamDetEntity> listOfTbComparamDet = tbComparamMasEntity.getListOfTbComparamDet();
			final List<TbComparamDet> tbComparamDetList = new ArrayList<>();
			for (TbComparamDetEntity tbComparamDetEntity : listOfTbComparamDet) {
				TbComparamDet tbComparamDet = new TbComparamDet();

				BeanUtils.copyProperties(tbComparamDetEntity, tbComparamDet);
				tbComparamDetList.add(tbComparamDet);

			}
			mapTbComparamMasEntityToTbComparamMas.setComparamDetList(tbComparamDetList);
			beans.add(mapTbComparamMasEntityToTbComparamMas);
		}
		return beans;
	}

	@Override
	@Transactional
	public List<TbComparamMas> findAll() {
		final Iterable<TbComparamMasEntity> entities = tbComparamMasJpaRepository.findAll();
		final List<TbComparamMas> beans = new ArrayList<>();
		final List<TbDepartment> departmentLst = tbDepartmentService.findAll();
		String deptName = null;
		for (final TbComparamMasEntity tbComparamMasEntity : entities) {
			deptName = findDepartmentByShortcode(tbComparamMasEntity.getCpmModuleName(), departmentLst);
			TbComparamMas tbComparamMas = tbComparamMasServiceMapper
					.mapTbComparamMasEntityToTbComparamMas(tbComparamMasEntity);
			tbComparamMas.setCpmModuleNameStr(deptName);
			beans.add(tbComparamMas);
		}
		return beans;
	}

	public String findDepartmentByShortcode(String deptCode, List<TbDepartment> deptList) {
		String deptName = null;
		for (TbDepartment dept : deptList) {
			if (dept.getDpDeptcode() != null && deptCode != null) {
				if (dept.getDpDeptcode().equalsIgnoreCase(deptCode)) {
					deptName = dept.getDpDeptdesc();
				}
			}
		}
		return deptName;
	}

	@Override
	@Transactional
	public SequenceConfigMasterDTO loadSequenceData(Long orgId, Long deptId, String tbName,String colName) {

		SequenceConfigMasterDTO configMasterDTO = new SequenceConfigMasterDTO();

		try {
			//To get the id of the sequence name selected base on prefix,lookup code and organization 
			Long seqNameId=CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(colName,MainetConstants.SEQ_PREFIXES.SQN,orgId);
			//#134523
			if (seqNameId != 0) {
			SequenceConfigMasterEntity sequenceConfigMasterEntity = ApplicationContextProvider.getApplicationContext()
					.getBean(ISequenceConfigMasterDAO.class).loadSequenceData(orgId, deptId,seqNameId);

			List<SequenceConfigDetDTO> configDetDTOs = new ArrayList<SequenceConfigDetDTO>();

			if (sequenceConfigMasterEntity != null) {

				BeanUtils.copyProperties(sequenceConfigMasterEntity, configMasterDTO);

				for (SequenceConfigDetEntity configDetEntity : sequenceConfigMasterEntity.getConfigDetEntities()) {

					SequenceConfigDetDTO configDetDTO = new SequenceConfigDetDTO();
					BeanUtils.copyProperties(configDetEntity, configDetDTO);

					configDetDTOs.add(configDetDTO);

				}
				configMasterDTO.setConfigDetDTOs(configDetDTOs);
			}
			}
		} catch (Exception exception) {
			throw new FrameworkException("Exception occure while fetching the data for SequenceConfigurationMaster",
					exception);
		}
		return configMasterDTO;
	}

	@Override
	public String getDeptName(Long id, SequenceConfigMasterDTO configMasterDTO) {

		configMasterDTO.setDeptName(ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.fetchDepartmentDescById(configMasterDTO.getDeptId()));

		String deptCode = configMasterDTO.getDeptName();
		return deptCode;
	}

	@Override
	public String getSeqTypeName(SequenceConfigMasterDTO configMasterDTO, Long orgId) {
		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(configMasterDTO.getSeqType(), orgId,
				"SQT");

		configMasterDTO.setSeqTypeName(lookUp.getLookUpDesc());
		String code = configMasterDTO.getSeqTypeName();
		return code;
	}

	@Override
	public String getSeqTBName(SequenceConfigMasterDTO configMasterDTO, Long orgId) {
		LookUp lookUp2 = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(configMasterDTO.getSeqName(), orgId,
				"SQN");

		configMasterDTO.setSeqtbName(lookUp2.getLookUpCode());
		String code = configMasterDTO.getSeqtbName();

		return code;

	}
}
