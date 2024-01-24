package com.abm.mainet.vehiclemanagement.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dao.IVehicleMaintenanceMasterDAO;
import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceMast;
import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceMastHistory;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceMasterDTO;
import com.abm.mainet.vehiclemanagement.mapper.VehiclMaintenanceMasterMapper;
import com.abm.mainet.vehiclemanagement.repository.VehiclMaintenanceMasterRepository;

/**
 * @author Ajay.Kumar
 *
 */
@Service
public class VehicleMaintenanceMastService implements IVehicleMaintenanceMasterService {
	
	Logger logger = Logger.getLogger(VehicleMaintenanceMastService.class);

	@Autowired
	private VehiclMaintenanceMasterRepository vehicleMaintenanceMasterRepository;

	@Autowired
	private IVehicleMaintenanceMasterDAO vehicleMaintenanceMasterDAO;

	@Autowired
	private VehiclMaintenanceMasterMapper vehicleMaintenanceMasterMapper;

	@Autowired
	private AuditService auditService;

	@Autowired
	private IGenVehicleMasterService vehicleMasterService;

	@Resource
	private ILogBookService bookService;
	
	@Autowired
	private IVehicleMaintenanceService vehicleMaintenanceService;
	
	@Autowired
	ISMSAndEmailService iSMSAndEmailService;
	
	/*
	 * (non-Javadoc)
	 * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#updateVehicleMaintenance(com.abm.mainet.swm.dto.
	 * VehicleMaintenanceMasterDTO)
	 */
	@Override
	@Transactional
	public VehicleMaintenanceMasterDTO updateVehicleMaintenance(
			VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {

		VehicleMaintenanceMast master = vehicleMaintenanceMasterMapper
				.mapVehicleMaintenanceMasterDTOToVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
		master = vehicleMaintenanceMasterRepository.save(master);
		VehicleMaintenanceMastHistory masterHistory = new VehicleMaintenanceMastHistory();
		masterHistory.setStatus(MainetConstants.Transaction.Mode.UPDATE);
		auditService.createHistory(master, masterHistory);
		return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterToVehicleMaintenanceMasterDTO(master);
	}

	/*
	 * (non-Javadoc)
	 * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#saveVehicleMaintenanceMaster(com.abm.mainet.swm.dto.
	 * VehicleMaintenanceMasterDTO)
	 */
	@Override
	@Transactional
	public VehicleMaintenanceMasterDTO saveVehicleMaintenanceMaster(
			VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {
		VehicleMaintenanceMast master = vehicleMaintenanceMasterMapper
				.mapVehicleMaintenanceMasterDTOToVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
		master = vehicleMaintenanceMasterRepository.save(master);
		VehicleMaintenanceMastHistory masterHistory = new VehicleMaintenanceMastHistory();
		masterHistory.setStatus(MainetConstants.Transaction.Mode.ADD);
		auditService.createHistory(master, masterHistory);
		return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterToVehicleMaintenanceMasterDTO(master);
	}

	@Override
	@Transactional
	public void savePSCLVehicleMaintenanceMaster(VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {
		List<Long> vehicleIdList = new ArrayList<>();
		final String vehicleIdArray[] = vehicleMaintenanceMasterDTO.getVehicleIdList()
				.split(MainetConstants.operator.COMMA);
		for (final String vehicleId : vehicleIdArray)
			vehicleIdList.add(Long.valueOf(vehicleId));

		List<VehicleMaintenanceMast> maintenanceMastList = new ArrayList<>();
		List<Object> maintenanceMastHistoryList = new ArrayList<>();
		vehicleIdList.forEach(vehicleId -> {
			vehicleMaintenanceMasterDTO.setVeId(vehicleId);
			VehicleMaintenanceMast vehicleMaintenanceMast = new VehicleMaintenanceMast();
			BeanUtils.copyProperties(vehicleMaintenanceMasterDTO, vehicleMaintenanceMast);
			maintenanceMastList.add(vehicleMaintenanceMast);

			VehicleMaintenanceMastHistory masterHistory = new VehicleMaintenanceMastHistory();
			BeanUtils.copyProperties(vehicleMaintenanceMasterDTO, masterHistory);
			masterHistory.setStatus(MainetConstants.Transaction.Mode.ADD);
			maintenanceMastHistoryList.add(masterHistory);
		});
		vehicleMaintenanceMasterRepository.save(maintenanceMastList);
		auditService.createHistoryForListObj(maintenanceMastHistoryList);
	}

	/*
	 * (non-Javadoc)
	 * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#deleteVehicleMaintenanceMaster(java.lang.Long,
	 * java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteVehicleMaintenanceMaster(Long vehicleMaintenanceId, Long empId, String ipMacAdd) {
		VehicleMaintenanceMast master = vehicleMaintenanceMasterRepository.findOne(vehicleMaintenanceId);
		master.setVeMeActive(MainetConstants.IsDeleted.DELETE);
		master.setUpdatedBy(empId);
		master.setUpdatedDate(new Date());
		master.setLgIpMacUpd(ipMacAdd);
		vehicleMaintenanceMasterRepository.save(master);
		VehicleMaintenanceMastHistory masterHistory = new VehicleMaintenanceMastHistory();
		masterHistory.setStatus(MainetConstants.Transaction.Mode.DELETE);
		auditService.createHistory(master, masterHistory);

	}

	/*
	 * (non-Javadoc)
	 * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#getVehicleMaintenanceMaster(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public VehicleMaintenanceMasterDTO getVehicleMaintenanceMaster(Long vehicleMaintenanceId) {
		return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterToVehicleMaintenanceMasterDTO(
				vehicleMaintenanceMasterRepository.findOne(vehicleMaintenanceId));
	}

	/*
	 * (non-Javadoc)
	 * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#getAllVehicleMaintenance(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<VehicleMaintenanceMasterDTO> getAllVehicleMaintenance(Long vehicleType, Long orgId) {
		return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(
				vehicleMaintenanceMasterRepository.findAllByVeVetype(vehicleType, orgId));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#serchVehicleMaintenanceByveDowntimeAndveDowntimeUnit(java.lang.
	 * Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<VehicleMaintenanceMasterDTO> serchVehicleMaintenanceByveDowntimeAndveDowntimeUnit(Long vehicleType,
			Long orgId) {
		return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(
				vehicleMaintenanceMasterDAO.serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(
						MainetConstants.FlagY, vehicleType, null, null, orgId));
	}


	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public List<VehicleMaintenanceMasterDTO> serchVehicleMaintenance(Long vehicleType, Long vehId, Long orgId) {
		return vehicleMaintenanceMasterMapper.mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(
				vehicleMaintenanceMasterDAO.serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(null,
						vehicleType, vehId, null, orgId));
	}

	/*
	 * (non-Javadoc)
	 * @see com.abm.mainet.swm.service.IVehicleMaintenanceMasterService#validateVehicleMaintenanceMaster(com.abm.mainet.swm.dto.
	 * VehicleMaintenanceMasterDTO)
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean validateVehicleMaintenanceMaster(VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {
		Assert.notNull(vehicleMaintenanceMasterDTO.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
		Assert.notNull(vehicleMaintenanceMasterDTO.getVeVetype(), MainetConstants.VEHICLE_TYPE_NOT_NULL);
		Assert.notNull(vehicleMaintenanceMasterDTO.getVeDowntime(), MainetConstants.VEHICLE_ESTIMATED_DOWNTIME_NULL);
		Assert.notNull(vehicleMaintenanceMasterDTO.getVeMainday(), MainetConstants.VEHICLE_MAINTENANCE_AFTER_NOT_NULL);
		List<VehicleMaintenanceMast> vehicleMaintenanceList = vehicleMaintenanceMasterDAO
				.serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(null,
						vehicleMaintenanceMasterDTO.getVeVetype(), null, vehicleMaintenanceMasterDTO.getVeMeId(),
						vehicleMaintenanceMasterDTO.getOrgid());
		return vehicleMaintenanceList.isEmpty();

	}

	@Override
	@Transactional
	public void preventiveVehicleMaintenanceAlert(QuartzSchedulerMaster runtimeBean, List<Object> parameterList){
		logger.info("Entered in preventiveMaintainanceAlert");
		Long orgId = runtimeBean.getOrgId().getOrgid();

		Set<String> maintenanceDueVehicleNumbers = new HashSet<>(); /** to collect unique Vehicle Numbers */

		/** Getting Active Maintenance Master Data */
		List<VehicleMaintenanceMasterDTO> maintenanceMasterDTOList = vehicleMaintenanceMasterMapper
				.mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(vehicleMaintenanceMasterRepository
						.getActiveVehicleMaintenanceMas(MainetConstants.Y_FLAG, orgId));
		Map<Long, VehicleMaintenanceMasterDTO> maintenanceMasterDTOMap = new HashMap<>();
		maintenanceMasterDTOList.forEach(maintenanceMas -> maintenanceMasterDTOMap.put(maintenanceMas.getVeId(), maintenanceMas));

		/** Getting Active Maintenance Master Vehicle Ids */
		List<Long> activeMaintMasVehicleIdList = maintenanceMasterDTOList.stream()
				.map(vehicleMaintenanceMast -> vehicleMaintenanceMast.getVeId()).collect(Collectors.toList());

		/** Getting Vehicle Master List based on Maintenance Master */
		List<GenVehicleMasterDTO> vehicleMasterDTOList = vehicleMasterService
				.getActiveVehiclesForMaintenanceAlert(activeMaintMasVehicleIdList, orgId);
		Map<Long, GenVehicleMasterDTO> vehicleMasterDTOMap = new HashMap<>();
		vehicleMasterDTOList.forEach(vehicleMasterDTO -> vehicleMasterDTOMap.put(vehicleMasterDTO.getVeId(), vehicleMasterDTO));

		/** Getting Vehicle LogBook List based on Maintenance Master */
		List<VehicleLogBookDTO> vehicleLogBookDTOList = bookService
				.getLogBookForMaintenanceAlert(activeMaintMasVehicleIdList, orgId);
		Map<Long, VehicleLogBookDTO> vehicleLogBookDTOMap = new HashMap<>();
		vehicleLogBookDTOList.forEach(logBookDTO -> vehicleLogBookDTOMap.put(logBookDTO.getVeNo(), logBookDTO));

		/** Getting Vehicle Maintenance service List based on Maintenance Master */
		List<VehicleMaintenanceDTO> vehicleMaintenanceDTOList = vehicleMaintenanceService
				.getMaintenanceDataForMaintenanceAlert(activeMaintMasVehicleIdList, orgId);
		Map<Long, VehicleMaintenanceDTO> vehicleMaintenanceDTOMap = new HashMap<>();
		vehicleMaintenanceDTOList.forEach(maintenanceDTO -> vehicleMaintenanceDTOMap.put(maintenanceDTO.getVeId(), maintenanceDTO));

		VehicleMaintenanceMasterDTO maintenanceMasterDto;
		VehicleLogBookDTO logBookDTO;
		VehicleMaintenanceDTO maintenanceDTO;
		Long maintenanceAfter;
		String uomLookupCode;
		Long dueReadingChecker;
		LocalDate dueDateChecker;
		LocalDate logBookDateChecker;
		Long maintenanceDuePeriod = null;
		LocalDate currentDate = LocalDate.now();
		
		logger.info("Lists Are Ready for Maintainance Due Check");
		
		try {
			/** This loop is of Active Vehicles from Vehicle Master which are present in Maintenance Master*/
			for (GenVehicleMasterDTO vehicleMasterDTO : vehicleMasterDTOList) {
				maintenanceMasterDto = maintenanceMasterDTOMap.get(vehicleMasterDTO.getVeId());
				logBookDTO = vehicleLogBookDTOMap.get(vehicleMasterDTO.getVeId());
				maintenanceDTO = vehicleMaintenanceDTOMap.get(vehicleMasterDTO.getVeId());

				/** Check if Vehicle is Not Under Maintenance */
				if (null == maintenanceDTO || !MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(maintenanceDTO.getWfStatus())) {

					/** Check for Maintenance After KM and LogBook not Null*/
					if (null != maintenanceMasterDto.getVeMainKM() && null != logBookDTO) {
						maintenanceAfter = maintenanceMasterDto.getVeMainKM();
						if (null != maintenanceDTO.getMaintEndReading())
							maintenanceAfter = maintenanceAfter + maintenanceDTO.getMaintEndReading().longValue();
						else if (null != maintenanceDTO.getVemReading())
							maintenanceAfter = maintenanceAfter + maintenanceDTO.getVemReading().longValue();
							
						dueReadingChecker = (null != logBookDTO.getDayEndMeterReading())
								? logBookDTO.getDayEndMeterReading().longValue() : logBookDTO.getDayStartMeterReading().longValue();
						if (maintenanceAfter <= dueReadingChecker)
							maintenanceDueVehicleNumbers.add(vehicleMasterDTO.getVeNo());
					}

					/** Check for Maintenance After Time */
					if (null != maintenanceMasterDto.getVeMainday()) {
						maintenanceAfter = maintenanceMasterDto.getVeMainday();
						uomLookupCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
								maintenanceMasterDto.getVeMainUnit(), orgId, MainetConstants.MEASUREMENT_UNIT).getLookUpCode();
						
						if(null != maintenanceDTO)
							dueDateChecker = (null != maintenanceDTO.getMaintCompDate()) ? Utility.convertDateToLocalDate(maintenanceDTO.getMaintCompDate())
									: Utility.convertDateToLocalDate(maintenanceDTO.getRequestDate());
						else
							dueDateChecker = Utility.convertDateToLocalDate(vehicleMasterDTO.getVePurDate());

						/** Subtract Buffer Time from Current date */
						if (null != maintenanceMasterDto.getVeBufferTime())
							currentDate.minusDays(maintenanceMasterDto.getVeBufferTime());
						
						if (MainetConstants.DAYS.equalsIgnoreCase(uomLookupCode))
							maintenanceDuePeriod = ChronoUnit.DAYS.between(dueDateChecker, currentDate);
						else if (PrefixConstants.WATERMODULEPREFIX.MONTH.equalsIgnoreCase(uomLookupCode)) {
							maintenanceDuePeriod = ChronoUnit.MONTHS.between(dueDateChecker, currentDate);
						} else if (MainetConstants.YEARS.equalsIgnoreCase(uomLookupCode))
							maintenanceDuePeriod = ChronoUnit.YEARS.between(dueDateChecker, currentDate);

						if (maintenanceAfter <= maintenanceDuePeriod)
							maintenanceDueVehicleNumbers.add(vehicleMasterDTO.getVeNo());
					}
					
					/** Check for DownTime */
					if (null != maintenanceMasterDto.getVeDowntime()) {
						maintenanceAfter = maintenanceMasterDto.getVeDowntime();
						uomLookupCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
								maintenanceMasterDto.getVeDowntimeUnit(), orgId, MainetConstants.MEASUREMENT_UNIT).getLookUpCode();

						if (null != maintenanceDTO) {
							dueDateChecker = (maintenanceDTO.getMaintCompDate() != null) ? Utility.convertDateToLocalDate(maintenanceDTO.getMaintCompDate())
									: Utility.convertDateToLocalDate(maintenanceDTO.getRequestDate());
							/** to get latest date from Maintenance and LogBook */
							if (null != logBookDTO) {
						        logBookDateChecker = (logBookDTO.getInDate() != null) ? Utility.convertDateToLocalDate(logBookDTO.getInDate()) 
						        		: Utility.convertDateToLocalDate(logBookDTO.getOutDate());
						        dueDateChecker = (dueDateChecker.compareTo(logBookDateChecker) > 0) ? dueDateChecker : logBookDateChecker;
						    }
						} else if (null != logBookDTO)
							dueDateChecker = (logBookDTO.getInDate() != null) ? Utility.convertDateToLocalDate(logBookDTO.getInDate()) 
									: Utility.convertDateToLocalDate(logBookDTO.getOutDate());
						else
							dueDateChecker = Utility.convertDateToLocalDate(vehicleMasterDTO.getVePurDate());

						if (MainetConstants.DAYS.equalsIgnoreCase(uomLookupCode))
							maintenanceDuePeriod = ChronoUnit.DAYS.between(dueDateChecker, currentDate);
						else if (PrefixConstants.WATERMODULEPREFIX.MONTH.equalsIgnoreCase(uomLookupCode))
							maintenanceDuePeriod = ChronoUnit.MONTHS.between(dueDateChecker, currentDate);
						else if (MainetConstants.YEARS.equalsIgnoreCase(uomLookupCode))
							maintenanceDuePeriod = ChronoUnit.YEARS.between(dueDateChecker, currentDate);

						if (maintenanceAfter <= maintenanceDuePeriod)
							maintenanceDueVehicleNumbers.add(vehicleMasterDTO.getVeNo());
					}
				}
			}			
		} catch (Exception e) {
			logger.error("Exception occur during Maintainance Due Check ", e);
		}
		
		logger.info("Maintainance Due Check Completed "+ String.join(" , ", maintenanceDueVehicleNumbers));
		if(!maintenanceDueVehicleNumbers.isEmpty())
			triggerPreventiveMaintenanceAlert(maintenanceDueVehicleNumbers, runtimeBean);
	}

	private void triggerPreventiveMaintenanceAlert(Set<String> maintenanceDueVehicleNumbers, QuartzSchedulerMaster runtimeBean) {
		logger.info("Starting Preventive Vehicle Maintenance SMS And Email Alerts");

		Organisation organisation = runtimeBean.getOrgId();
		Integer langId = Utility.getDefaultLanguageId(organisation);
		Date sysDate = new Date();
		String menuUrl = "vehicleMaintenanceMasController.html";
		
		List<LookUp> mobilesEmailsLookupList = CommonMasterUtility.getLookUps(Constants.VEHICLE_MAINT_MOBILES_EMAILS, organisation);
		List<String> mobilesLookupList = mobilesEmailsLookupList.stream()
				.map(mobilesEmailsLookup -> mobilesEmailsLookup.getLookUpCode()).collect(Collectors.toList());
		List<String> emailsLookupList = mobilesEmailsLookupList.stream()
				.map(mobilesEmailsLookup -> mobilesEmailsLookup.getOtherField()).collect(Collectors.toList());
		
		maintenanceDueVehicleNumbers.forEach(vehicleNumber -> {
			mobilesEmailsLookupList.forEach(mobilesEmailsLookup -> {
				final SMSAndEmailDTO smsEmailDto = new SMSAndEmailDTO();
				if(null != mobilesEmailsLookup.getLookUpCode())
					smsEmailDto.setMobnumber(mobilesEmailsLookup.getLookUpCode());
				if(null != mobilesEmailsLookup.getOtherField())
					smsEmailDto.setEmail(mobilesEmailsLookup.getOtherField());
				smsEmailDto.setRegNo(vehicleNumber);
				smsEmailDto.setDueDt(Utility.dateToString(sysDate));
				smsEmailDto.setServName(Constants.MAINT_SERVICE_CODE);				
				try {
					iSMSAndEmailService.sendEmailSMS(Constants.VEHICLE_DRPT_CODE, menuUrl,
							PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, smsEmailDto, organisation, langId);
				} catch (Exception e) {
					logger.error("Exception occur while sending SMS and Email for vehicle Number:"+" "+vehicleNumber, e);
				}
			});
		});
		logger.info("Preventive Vehicle Maintenance SMS And Email Alerts Sent SuccessFully To "
				+ String.join(" , ", mobilesLookupList) + " " + String.join(" , ", emailsLookupList));
	}


}
