package com.abm.mainet.socialsecurity.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.socialsecurity.dao.ConfigurationMasterDao;
import com.abm.mainet.socialsecurity.domain.ConfigurationMasterEntity;
import com.abm.mainet.socialsecurity.domain.ConfigurationMasterHistoryEntity;
import com.abm.mainet.socialsecurity.repository.ConfigurationMasterRepository;
import com.abm.mainet.socialsecurity.repository.SchemeApplicationFormRepository;
import com.abm.mainet.socialsecurity.ui.dto.ConfigurationMasterDto;

/**
 * @author rahul.chaubey
 * @since 11 Jan 2020
 */

@Service
public class ConfigurationMasterServiceImpl implements ConfigurationMasterService {

	@Autowired
	ConfigurationMasterRepository configurationMasterRepository;

	@Autowired
	AuditService auditService;

	@Autowired
	ConfigurationMasterDao configurationMasterDao;

	@Resource
	ServiceMasterService serviceMaster;

	@Autowired
	SchemeApplicationFormRepository schemeApplicationFormRepository;

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void saveConfigurationMaster(ConfigurationMasterDto masterDto) {
		ConfigurationMasterEntity entity = new ConfigurationMasterEntity();
		ConfigurationMasterHistoryEntity historyEntity = new ConfigurationMasterHistoryEntity();
		try {
			BeanUtils.copyProperties(masterDto,entity);
			configurationMasterRepository.save(entity);
			historyEntity.sethStatus(MainetConstants.FlagA);
			auditService.createHistory(entity, historyEntity);
		} catch (Exception e) {
			throw new FrameworkException("Unable to Save the Scheme Configuration Details", e);
		}

	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void updateConfigurationMaster(ConfigurationMasterDto masterDto) {

		ConfigurationMasterEntity entity = new ConfigurationMasterEntity();
		ConfigurationMasterHistoryEntity historyEntity = new ConfigurationMasterHistoryEntity();
		try {
			BeanUtils.copyProperties( masterDto,entity);
			configurationMasterRepository.save(entity);
			historyEntity.sethStatus(MainetConstants.FlagU);
			auditService.createHistory(entity, historyEntity);
		} catch (Exception e) {
			throw new FrameworkException("Unable to Update the Scheme Configuration Details", e);
		}

	}

	@Override
	public ConfigurationMasterDto findSchemeById(Long configurationId, Long orgId) {
		ConfigurationMasterEntity entity = configurationMasterRepository.findSchemeById(configurationId, orgId);
		ConfigurationMasterDto dto = new ConfigurationMasterDto();

		// BeanUtils.copyProperties(dto, entity);
		if (entity != null) {
			org.springframework.beans.BeanUtils.copyProperties(entity, dto);
		}

		return dto;
	}

	@Override
	public List<ConfigurationMasterDto> loadData(Long orgId) {
		List<ConfigurationMasterEntity> entityList = configurationMasterRepository.loadData(orgId);
		List<ConfigurationMasterDto> dtoList = new ArrayList<>();
		entityList.forEach(entity -> {
			ConfigurationMasterDto dto = new ConfigurationMasterDto();
				BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public List<ConfigurationMasterDto> searchData(Long schemeMstId, Long orgId) {
		List<ConfigurationMasterEntity> entityList = configurationMasterRepository.searchData(schemeMstId, orgId);
		List<ConfigurationMasterDto> dtoList = new ArrayList<>();
		entityList.forEach(entity -> {
			ConfigurationMasterDto dto = new ConfigurationMasterDto();
				BeanUtils.copyProperties(entity,dto);
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public List<ConfigurationMasterDto> getData(Long configurationId, Long schemeMstId, Long orgId) {
		List<ConfigurationMasterEntity> entityList = configurationMasterDao.getData(configurationId, schemeMstId,
				orgId);
		List<ConfigurationMasterDto> dtoList = new ArrayList<>();

		entityList.forEach(entity -> {
			ConfigurationMasterDto dto = new ConfigurationMasterDto();
				BeanUtils.copyProperties( entity,dto);
				dto.setSchemeCode(serviceMaster.fetchServiceShortCode(dto.getSchemeMstId(), dto.getOrgId()));
				dto.setSchemeName(serviceMaster.getServiceNameByServiceId(dto.getSchemeMstId()));
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public List<Object[]> unconfiguredList(Long orgId, Long depId, Long activeStatusId, String notActualFlag) {
		List<Object[]> schemeList = configurationMasterRepository.unconfiguredList(orgId, depId, activeStatusId,
				notActualFlag);
		return schemeList;
	}

	@Override
	public boolean validateOnSave(Long schemeMstId, Long orgId, boolean workflowFlag) {
		boolean validate = false;
		List<ConfigurationMasterDto> dataList = searchData(schemeMstId, orgId);
		if (workflowFlag) {
			
			Object count = schemeApplicationFormRepository.getApprovedCount(schemeMstId, orgId);
			int count_value;
			if (count == null) {
				count_value = 0;
			} else {
				count_value = Integer.parseInt(count.toString());
			}
			if (dataList.size() > 0 && dataList.get(0).getBeneficiaryCount()!=null) {
				if (dataList.get(0).getBeneficiaryCount()!=null && count_value < dataList.get(0).getBeneficiaryCount() ) { // and below the count limit.
					validate = true;
				}
				else {
					// scheme's max count has been reached.
					validate = false;
				}

			} else {
				validate = true; // No scheme configured
			}
		} else {
			if (dataList.size() > 0) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								
				if (dataList.get(0).getToDate().compareTo(Calendar.getInstance().getTime())>0 || sdf.format(dataList.get(0).getToDate()).equals(sdf.format(new Date()))) {
					validate = true;
				} else {
					validate = false; // scheme's date has been expired
				}

			} else {
				validate = true; // No scheme configured
			}
		}

		return validate;
	}

	@Override
	public ConfigurationMasterDto getConfigMstDataBySchemeId(Long schemeMstId, Long orgId) {
		ConfigurationMasterEntity entity = configurationMasterRepository.getConfigMstDataBySchemeId(schemeMstId, orgId);
		ConfigurationMasterDto dto = new ConfigurationMasterDto();
		if (entity != null) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}
}
