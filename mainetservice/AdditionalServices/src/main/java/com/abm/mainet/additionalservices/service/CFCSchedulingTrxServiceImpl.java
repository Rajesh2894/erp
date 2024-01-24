package com.abm.mainet.additionalservices.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.additionalservices.dao.CFCSchedulingDao;
import com.abm.mainet.additionalservices.domain.CFCCollectionMasterEntity;
import com.abm.mainet.additionalservices.domain.CFCCollectionMasterHistEntity;
import com.abm.mainet.additionalservices.domain.CFCCounterMasterEntity;
import com.abm.mainet.additionalservices.domain.CFCCounterMasterHistEntity;
import com.abm.mainet.additionalservices.domain.CFCCounterScheduleEntity;
import com.abm.mainet.additionalservices.domain.CFCCounterScheduleHistEntity;
import com.abm.mainet.additionalservices.dto.CFCCollectionMasterDto;
import com.abm.mainet.additionalservices.dto.CFCCounterMasterDto;
import com.abm.mainet.additionalservices.dto.CFCCounterScheduleDto;
import com.abm.mainet.additionalservices.dto.CFCSchedularSummaryDto;
import com.abm.mainet.additionalservices.repository.CFCCounterScheduleRepo;
import com.abm.mainet.additionalservices.repository.CFCSchedulingForTrxRepo;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Utility;

@Service
public class CFCSchedulingTrxServiceImpl implements CFCSchedulingTrxService {

	private static final Logger LOGGER = Logger.getLogger(CFCSchedulingTrxServiceImpl.class);

	@Autowired
	private CFCSchedulingForTrxRepo cfcSchedulingForTrxRepo;

	@Autowired
	private IEmployeeService employeeScheduleService;

	@Autowired
	private CFCCounterScheduleRepo cfcCounterScheduleRepo;

	@Resource
	private AuditService auditService;

	@Override
	@Transactional
	public boolean saveCollectionDetail(CFCCollectionMasterDto cfcCollectionMasterDto) {
		// SequenceConfigMasterEntity configMasterEntity =
		// mapDtoToEntity(configMasterDTO);
		// BeanUtils.copyProperties(configMasterDTO, configMasterEntity);

		CFCCollectionMasterEntity cfcCollectionMasterEntity = new CFCCollectionMasterEntity();
		List<CFCCounterMasterEntity> cfcCounterMasterEntities = new ArrayList<CFCCounterMasterEntity>();

		BeanUtils.copyProperties(cfcCollectionMasterDto, cfcCollectionMasterEntity);
		cfcCollectionMasterEntity.setDwzId(cfcCollectionMasterDto.getCfcWard1());
       //User Story #147721
		cfcCollectionMasterEntity.setDeviceId(cfcCollectionMasterDto.getDeviceId());
		if (!cfcCollectionMasterDto.getCfcCounterMasterDtos().isEmpty()) {
			for (CFCCounterMasterDto counterDto : cfcCollectionMasterDto.getCfcCounterMasterDtos()) {
				CFCCounterMasterEntity cfcCounterMasterEntity = new CFCCounterMasterEntity();
				List<CFCCounterScheduleEntity> cfcCounterScheduleEntities = new ArrayList<CFCCounterScheduleEntity>();
				BeanUtils.copyProperties(counterDto, cfcCounterMasterEntity);
				cfcCounterMasterEntity.setCfcCollectionMasterEntity(cfcCollectionMasterEntity);

				for (CFCCounterScheduleDto cfcCounterScheduleDto : counterDto.getCfcCounterScheduleDtos()) {
					CFCCounterScheduleEntity cfcCounterScheduleEntity = new CFCCounterScheduleEntity();
					BeanUtils.copyProperties(cfcCounterScheduleDto, cfcCounterScheduleEntity);
					cfcCounterScheduleEntity.setCsFromTime(stringToDateConvert(cfcCounterScheduleDto.getCsFromTime()));
					cfcCounterScheduleEntity.setCsToTime(stringToDateConvert(cfcCounterScheduleDto.getCsToTime()));
					cfcCounterScheduleEntity.setCfcCounterMasterEntity(cfcCounterMasterEntity);
					cfcCounterScheduleEntity.setCsStatus(MainetConstants.FlagA);
					cfcCounterScheduleEntities.add(cfcCounterScheduleEntity);
				}
				cfcCounterMasterEntity.setCfcCounterScheduleEntities(cfcCounterScheduleEntities);
				cfcCounterMasterEntities.add(cfcCounterMasterEntity);
			}
			cfcCollectionMasterEntity.setCfcCounterMasterEntities(cfcCounterMasterEntities);
		}

		try {
			cfcCollectionMasterEntity = cfcSchedulingForTrxRepo.save(cfcCollectionMasterEntity);

			CFCCollectionMasterHistEntity cfcCollectionMasterHistEntity = new CFCCollectionMasterHistEntity();
			cfcCollectionMasterHistEntity.sethSTATUS(MainetConstants.FlagC);
			try {
				auditService.createHistory(cfcCollectionMasterEntity, cfcCollectionMasterHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + cfcCollectionMasterEntity, ex);
			}

			List<Object> cfcCounterMasterHistEntities = new ArrayList<Object>();
			for (CFCCounterMasterEntity entity : cfcCollectionMasterEntity.getCfcCounterMasterEntities()) {
				CFCCounterMasterHistEntity cfcCounterMasterHistEntity = new CFCCounterMasterHistEntity();
				BeanUtils.copyProperties(entity, cfcCounterMasterHistEntity);
				cfcCounterMasterHistEntity.setCmCollnid(cfcCollectionMasterEntity.getCmCollnid());
				cfcCounterMasterHistEntity.sethSTATUS(MainetConstants.FlagC);
				cfcCounterMasterHistEntities.add(cfcCounterMasterHistEntity);
			}

			try {
				auditService.createHistoryForListObj(cfcCounterMasterHistEntities);

			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + cfcCounterMasterHistEntities, ex);
			}

			List<Object> cfcCounterSchedule = new ArrayList<Object>();
			cfcCollectionMasterEntity.getCfcCounterMasterEntities().forEach(entity -> {
				entity.getCfcCounterScheduleEntities().forEach(scheduleEntity -> {
					CFCCounterScheduleHistEntity cfcCounterScheduleHistEntity = new CFCCounterScheduleHistEntity();
					BeanUtils.copyProperties(scheduleEntity, cfcCounterScheduleHistEntity);
					cfcCounterScheduleHistEntity.setCuCounterid(entity.getCuCounterid());
					cfcCounterScheduleHistEntity.sethSTATUS(MainetConstants.FlagC);
					cfcCounterSchedule.add(cfcCounterScheduleHistEntity);
				});

			});

			try {
				auditService.createHistoryForListObj(cfcCounterSchedule);

			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + cfcCounterSchedule, ex);
			}

			return true;

		} catch (Exception e) {
			LOGGER.error("Exception occur while saving the squence configuration ", e);
			throw new FrameworkException("Exception occur while saving the squence configuration", e);
		}
	}
	//121825  Method added to convert String value to Date 
	public Date stringToDateConvert(String time) {
		DateFormat formatter = new SimpleDateFormat(MainetConstants.WorksManagement.DATE_FORMAT);
		Date timeValue = null;
		if (time != null) {
			try {
				timeValue = formatter.parse(time);
				// timeValue = new Date(formatter.parse(time).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				throw new FrameworkException(e);
			}
		}
		return timeValue;

	}
	
	@Transactional
	@Override
	public List<CFCSchedularSummaryDto> searchCollectionInfo(String collectionnNo, String counterNo, Long userId,
			String status, Long orgId) {

		List<CFCSchedularSummaryDto> cfcSchedularSummaryDtos = new ArrayList<CFCSchedularSummaryDto>();
		try {
			List<CFCCounterScheduleEntity> counterEntities = ApplicationContextProvider.getApplicationContext()
					.getBean(CFCSchedulingDao.class)
					.searchCollectionData(collectionnNo, counterNo, userId, status, orgId);

			if (!counterEntities.isEmpty()) {
				counterEntities.forEach(entity -> {
					CFCSchedularSummaryDto cfcSchedularSummaryDto = new CFCSchedularSummaryDto();
					cfcSchedularSummaryDto.setCollectionnNo(
							entity.getCfcCounterMasterEntity().getCfcCollectionMasterEntity().getCmCollncentreno());
					cfcSchedularSummaryDto.setCounterNo(entity.getCfcCounterMasterEntity().getCuCountcentreno());
					cfcSchedularSummaryDto.setStartDate(entity.getCsFromTime());
					cfcSchedularSummaryDto.setEndDate(entity.getCsToTime());
					cfcSchedularSummaryDto.setStatus(entity.getCsStatus());
					cfcSchedularSummaryDto.setUserId(userId);
					EmployeeBean emp = employeeScheduleService.findById(entity.getCsUserId());
					//121825 Null check for the employee
					if(emp != null) {
						StringBuilder empName = new StringBuilder();
						if (emp.getEmpname() != null)
							empName.append(emp.getEmpname());
						if (emp.getEmpmname() != null)
							empName.append(" " + emp.getEmpmname());
						if (emp.getEmplname() != null)
							empName.append(" " + emp.getEmplname());
						cfcSchedularSummaryDto.setUserName(empName.toString());
					}
					/*
					 * cfcSchedularSummaryDto.setCollectionId(
					 * entity.getCfcCounterMasterEntity().getCfcCollectionMasterEntity().
					 * getCmCollnid());
					 */
					cfcSchedularSummaryDto.setCounterScheduleId(entity.getCsScheduleid());
					cfcSchedularSummaryDtos.add(cfcSchedularSummaryDto);
				});
			}

		} catch (Exception exception) {
			throw new FrameworkException("Error Occured While fetching the Advertiser Master Data", exception);
		}
		return cfcSchedularSummaryDtos;

	}

	@Override
	public List<String> getCounterNos(String collectionNo) {
		List<String> countereIds = new ArrayList<String>();
		countereIds = cfcSchedulingForTrxRepo.getCounterNosByCollectionNo(collectionNo);
		return countereIds;
	}

	@Override
	@Transactional
	public CFCCollectionMasterDto getCFCCollectionInfoById(Long collectionId) {
		CFCCollectionMasterDto cfcCollectionMasterDto = new CFCCollectionMasterDto();

		CFCCollectionMasterEntity cfcCollectionMasterEntity = cfcSchedulingForTrxRepo
				.getCFCCollectionInfoById(collectionId);
		if (cfcCollectionMasterEntity != null) {
			List<CFCCounterMasterDto> cfcCounterMasterDtos = new ArrayList<CFCCounterMasterDto>();
			BeanUtils.copyProperties(cfcCollectionMasterEntity, cfcCollectionMasterDto);

			for (CFCCounterMasterEntity cfcCounterMasterEntity : cfcCollectionMasterEntity
					.getCfcCounterMasterEntities()) {
				CFCCounterMasterDto cfcCounterMasterDto = new CFCCounterMasterDto();
				BeanUtils.copyProperties(cfcCounterMasterEntity, cfcCounterMasterDto);
				List<CFCCounterScheduleDto> cfcCounterScheduleDtos = new ArrayList<CFCCounterScheduleDto>();

				for (CFCCounterScheduleEntity cfcCounterScheduleEntity : cfcCounterMasterEntity
						.getCfcCounterScheduleEntities()) {
					CFCCounterScheduleDto cfcCounterScheduleDto = new CFCCounterScheduleDto();
					BeanUtils.copyProperties(cfcCounterScheduleDto, cfcCounterScheduleEntity);
					cfcCounterScheduleDtos.add(cfcCounterScheduleDto);
				}
				cfcCounterMasterDtos.add(cfcCounterMasterDto);
			}
			cfcCollectionMasterDto.setCfcCounterMasterDtos(cfcCounterMasterDtos);
		}

		return cfcCollectionMasterDto;
	}

	@Override
	@Transactional
	public boolean updateCounterScheduleDetail(CFCCounterScheduleDto cfcCounterScheduleDto) {

		CFCCounterScheduleEntity cfcCounterScheduleEntity = new CFCCounterScheduleEntity();
		BeanUtils.copyProperties(cfcCounterScheduleDto, cfcCounterScheduleEntity);
		CFCCounterMasterEntity cfcCounterMasterEntity = new CFCCounterMasterEntity();
		BeanUtils.copyProperties(cfcCounterScheduleDto.getCfcCounterMasterDto(), cfcCounterMasterEntity);
		CFCCollectionMasterEntity cfcCollectionMasterEntity = new CFCCollectionMasterEntity();
		BeanUtils.copyProperties(cfcCounterScheduleDto.getCfcCounterMasterDto().getCfcCollectionMasterDto(),
				cfcCollectionMasterEntity);
		cfcCounterMasterEntity.setCfcCollectionMasterEntity(cfcCollectionMasterEntity);
		cfcCounterScheduleEntity.setCfcCounterMasterEntity(cfcCounterMasterEntity);
		cfcCounterScheduleEntity.setCsFromTime(stringToDateConvert(cfcCounterScheduleDto.getCsFromTime()));
		cfcCounterScheduleEntity.setCsToTime(stringToDateConvert(cfcCounterScheduleDto.getCsToTime()));
		//User Story #147721
       cfcCounterScheduleEntity.getCfcCounterMasterEntity().getCfcCollectionMasterEntity().setDeviceId(cfcCounterScheduleDto.getDeviceId());
		try {
			cfcCounterScheduleRepo.save(cfcCounterScheduleEntity);

		} catch (Exception e) {
			LOGGER.info("Error while updating Counter Schedule data :" + e);
		}

		CFCCounterScheduleHistEntity cfcCounterScheduleHistEntity = new CFCCounterScheduleHistEntity();
		try {

			cfcCounterScheduleHistEntity
					.setCuCounterid(cfcCounterScheduleEntity.getCfcCounterMasterEntity().getCuCounterid());
			cfcCounterScheduleHistEntity.sethSTATUS(MainetConstants.FlagU);
			auditService.createHistory(cfcCounterScheduleEntity, cfcCounterScheduleHistEntity);

		} catch (Exception e) {

			LOGGER.error("Could not make audit entry for " + cfcCounterScheduleHistEntity, e);
		}

		return true;
	}

	@Override
	@Transactional
	public CFCCounterScheduleDto searchCounterScheduleBuId(Long orgId, Long counterScheduleId) {

		CFCCounterScheduleDto cfcCounterScheduleDto = new CFCCounterScheduleDto();

		CFCCounterScheduleEntity cfcCounterScheduleEntity = ApplicationContextProvider.getApplicationContext()
				.getBean(CFCSchedulingDao.class).searchCounterScheduleBuId(orgId, counterScheduleId);

		BeanUtils.copyProperties(cfcCounterScheduleEntity, cfcCounterScheduleDto);
		String fromDate = new SimpleDateFormat(MainetConstants.DATE_AND_TIME_FORMAT_PAYRECIEPT).format(cfcCounterScheduleEntity.getCsFromTime());
		cfcCounterScheduleDto.setCsFromTime(fromDate.toString().replace("-", "/"));
		String toDate = new SimpleDateFormat(MainetConstants.DATE_AND_TIME_FORMAT_PAYRECIEPT).format(cfcCounterScheduleEntity.getCsToTime());
		cfcCounterScheduleDto.setCsToTime(toDate.toString().replace("-", "/"));
		CFCCounterMasterDto cfcCounterMasterDto = new CFCCounterMasterDto();
		BeanUtils.copyProperties(cfcCounterScheduleEntity.getCfcCounterMasterEntity(), cfcCounterMasterDto);

		CFCCollectionMasterDto cfcCollectionMasterDto = new CFCCollectionMasterDto();
		BeanUtils.copyProperties(cfcCounterScheduleEntity.getCfcCounterMasterEntity().getCfcCollectionMasterEntity(),
				cfcCollectionMasterDto);
		cfcCounterMasterDto.setCfcCollectionMasterDto(cfcCollectionMasterDto);
		cfcCounterScheduleDto.setCfcCounterMasterDto(cfcCounterMasterDto);

		cfcCounterScheduleDto
				.setCuCountcentreno(cfcCounterScheduleEntity.getCfcCounterMasterEntity().getCuCountcentreno());
		cfcCounterScheduleDto.setCmCollncentreno(cfcCounterScheduleEntity.getCfcCounterMasterEntity()
				.getCfcCollectionMasterEntity().getCmCollncentreno());
		cfcCounterScheduleDto.setCmDescription(
				cfcCounterScheduleEntity.getCfcCounterMasterEntity().getCfcCollectionMasterEntity().getCmDescription());
		cfcCounterScheduleDto.setCfcWard1(
				cfcCounterScheduleEntity.getCfcCounterMasterEntity().getCfcCollectionMasterEntity().getDwzId());
		cfcCounterScheduleDto.setCuDescription(cfcCounterScheduleEntity.getCfcCounterMasterEntity().getCuDescription());
		//User Story #147721
        cfcCounterScheduleDto.setDeviceId(cfcCounterScheduleEntity.getCfcCounterMasterEntity().getCfcCollectionMasterEntity().getDeviceId());

		return cfcCounterScheduleDto;
	}

	@Override
	@Transactional
	public CFCSchedulingCounterDet getScheduleDetByEmpiId(Long empId, Long orgId) {
		CFCSchedulingCounterDet cfcSchedulingCounterDet = null;
		//119534  Setting the values dto which is required

		List<CFCCounterScheduleEntity> cfcCounterScheduleEntity = cfcCounterScheduleRepo.getcounterDetByempId(empId, orgId,new Date());
		if (cfcCounterScheduleEntity != null && !cfcCounterScheduleEntity.isEmpty()) {
			cfcSchedulingCounterDet=new CFCSchedulingCounterDet();
			cfcSchedulingCounterDet.setCollcntrno(cfcCounterScheduleEntity.get(0).getCfcCounterMasterEntity()
					.getCfcCollectionMasterEntity().getCmCollncentreno());

			cfcSchedulingCounterDet.setCounterno(cfcCounterScheduleEntity.get(0).getCfcCounterMasterEntity().getCuCountcentreno());
			
			cfcSchedulingCounterDet.setEmpId(empId);
			cfcSchedulingCounterDet.setFromTime(cfcCounterScheduleEntity.get(0).getCsFromTime());
			cfcSchedulingCounterDet.setToTime(cfcCounterScheduleEntity.get(0).getCsToTime());
			cfcSchedulingCounterDet.setFrequencySts(cfcCounterScheduleEntity.get(0).getFrequencySts());
			cfcSchedulingCounterDet.setScheduleSts(cfcCounterScheduleEntity.get(0).getCsStatus());
			//D#145761
			cfcSchedulingCounterDet.setDwzId1(cfcCounterScheduleEntity.get(0).getCfcCounterMasterEntity().getCfcCollectionMasterEntity().getDwzId());
			//User Story #147721
            cfcSchedulingCounterDet.setDeviceId(cfcCounterScheduleEntity.get(0).getCfcCounterMasterEntity().getCfcCollectionMasterEntity().getDeviceId());
			cfcSchedulingCounterDet.setOrgId(orgId);
		}

		return cfcSchedulingCounterDet;

	}
	
	//Defect #127209 - to check duplicate counter scheduling
	@Override
	public Boolean getScheduleDetails(String cuCountcentreno, String cmCollncentreno, Long orgid,
			Date fromDate, Date toTime) {
		boolean result = cfcCounterScheduleRepo.getcounterDet(cuCountcentreno,cmCollncentreno,orgid,fromDate,toTime);
		return result;
	}
}
