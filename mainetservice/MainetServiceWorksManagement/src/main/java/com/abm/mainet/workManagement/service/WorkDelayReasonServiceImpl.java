package com.abm.mainet.workManagement.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.SequenceConfigDetEntity;
import com.abm.mainet.common.dto.SequenceConfigDetDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.workManagement.dao.IWorkDelayReasonDao;
import com.abm.mainet.workManagement.domain.WorkDelayReasonEntity;
import com.abm.mainet.workManagement.domain.WorkDelayReasonHistEntity;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkDelayReasonDto;
import com.abm.mainet.workManagement.repository.WorkDelayReasonRepo;

/**
 * @author sadik.shaikh
 *
 */
@Service
public class WorkDelayReasonServiceImpl implements IWorkDelayReasonService {

	@Resource
	@Autowired
	WorkDelayReasonRepo delayReasonRepo;

	@Autowired
	private WmsProjectMasterService projectMasterService;

	@Autowired
	private WorkDefinitionService workDefinationService;

	@Autowired
	private AuditService auditService;

	private static final Logger LOGGER = Logger.getLogger(WorkDelayReasonServiceImpl.class);

	@Transactional
	public boolean saveWorkDelayReason(WorkDelayReasonDto delayReasonDto) {
		WorkDelayReasonEntity delayReasonEntity = new WorkDelayReasonEntity();

		BeanUtils.copyProperties(delayReasonDto, delayReasonEntity);

		try {
			delayReasonEntity = delayReasonRepo.save(delayReasonEntity);
			createHistory(delayReasonEntity, delayReasonDto.getFlagForHist());
			return true;

		} catch (Exception e) {
			LOGGER.error("Exception occur while saving the Detail of delay reason ", e);
			throw new FrameworkException("Exception occur while saving the detail of delay reason", e);
		}

	}

	private void createHistory(WorkDelayReasonEntity delayReasonEntity, String status) {

		try {
			WorkDelayReasonHistEntity delayReasonHistEntity = new WorkDelayReasonHistEntity();
			delayReasonHistEntity.setHistStatus(status);
			auditService.createHistory(delayReasonEntity, delayReasonHistEntity);
		} catch (Exception e) {
			LOGGER.error("Could not make audit entry for " + delayReasonEntity, e);
		}
	}

	@Override
	public List<WorkDelayReasonDto> getAllData(Long orgId, Long projId, Long workId, Date date, String status) {

		List<WorkDelayReasonDto> delayReasondtos = new ArrayList<WorkDelayReasonDto>();
		try {
			List<WorkDelayReasonEntity> delayReasonEntities = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkDelayReasonDao.class).getAllData(orgId, projId, workId, date, status);

			if (delayReasonEntities != null) {
				delayReasonEntities.forEach(entity -> {
					WmsProjectMasterDto dto = projectMasterService.getProjectMasterByProjId(entity.getProjId());
					WorkDefinitionDto definitionDto = workDefinationService
							.findWorkDefinitionByWorkId(entity.getWorkId(), orgId);

					WorkDelayReasonDto masterDto = new WorkDelayReasonDto();
					BeanUtils.copyProperties(entity, masterDto);

					if (StringUtils.isNotBlank(masterDto.getStatus())) {
						if (StringUtils.equalsIgnoreCase(masterDto.getStatus(), MainetConstants.FlagA)) {
							masterDto.setStatus(MainetConstants.Common_Constant.ACTIVE);
						}
						if (StringUtils.equalsIgnoreCase(masterDto.getStatus(), MainetConstants.FlagI)) {
							masterDto.setStatus(MainetConstants.Common_Constant.INACTIVE);
						}
					}
					masterDto.setWorkName(definitionDto.getWorkName());
					masterDto.setProjName(dto.getProjNameEng());
					masterDto.setDateOccuranceDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getDateOccurance()));
					masterDto.setDateClearanceDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(entity.getDateClearance()));
					delayReasondtos.add(masterDto);
				});
			}
		} catch (

		Exception e) {
			// TODO: handle exception
		}
		return delayReasondtos;
	}

	@Override
	public WorkDelayReasonDto getDelayReasonById(Long orgId, Long delresId) {

		WorkDelayReasonDto delayReasonDto = new WorkDelayReasonDto();
		try {
			WorkDelayReasonEntity delayReasonEntity = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkDelayReasonDao.class).getDelayResById(orgId, delresId);

			delayReasonDto.setDateOccuranceDesc(
                     new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(delayReasonEntity.getDateOccurance()));
			delayReasonDto.setDateClearanceDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(delayReasonEntity.getDateClearance()));
			WorkDefinitionDto definitionDto = workDefinationService
					.findWorkDefinitionByWorkId(delayReasonEntity.getWorkId(), orgId);
			WmsProjectMasterDto dto = projectMasterService.getProjectMasterByProjId(delayReasonEntity.getProjId());
			if (delayReasonEntity != null) {
				BeanUtils.copyProperties(delayReasonEntity, delayReasonDto);
			}
			delayReasonDto.setWorkName(definitionDto.getWorkName());
			delayReasonDto.setProjName(dto.getProjNameEng());

		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the Advertiser Master Data", exception);
		}
		return delayReasonDto;
	}

}
