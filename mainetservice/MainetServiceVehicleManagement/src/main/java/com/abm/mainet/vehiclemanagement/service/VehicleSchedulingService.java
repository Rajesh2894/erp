package com.abm.mainet.vehiclemanagement.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.dao.IVehicleScheduleDAO;
import com.abm.mainet.vehiclemanagement.domain.VehicleScheduleData;
import com.abm.mainet.vehiclemanagement.domain.VehicleScheduleDetHist;
import com.abm.mainet.vehiclemanagement.domain.VehicleScheduleDetails;
import com.abm.mainet.vehiclemanagement.domain.VehicleScheduleHist;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDetDTO;
import com.abm.mainet.vehiclemanagement.mapper.VehicleSchedullingMapper;
import com.abm.mainet.vehiclemanagement.repository.VehicleSchedulingRepository;

/**
 * The Class VehicleScheduleServiceImpl.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IVehicleScheduleService")
@Path(value = "/vehiclescheduleservice")
public class VehicleSchedulingService implements IVehicleScheduleService {

	/** The vehicle master repository. */
	@Autowired
	private VehicleSchedulingRepository vehicleMasterRepository;

	@Autowired
	private IVehicleScheduleDAO vehicleMasterDAO;

	/** The vehicle master mapper. */
	@Autowired
	private VehicleSchedullingMapper vehicleMasterMapper;

	/** The audit service. */
	@Autowired
	private AuditService auditService;

	/**
	 * The ISLRMEmployeeMasterService Service
	 */

	@Autowired
	private ISLRMEmployeeMasterService employeeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleSchedulingService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.VehicleScheduleService#
	 * getVehicleScheduleByVehicleScheduleId(java.lang.Long)
	 */
	@Override
	@GET
	@Path(value = "/get/{id}")
	@Transactional(readOnly = true)
	public VehicleScheduleDTO getVehicleScheduleByVehicleScheduleId(@PathParam("id") Long vehicleId) {
		return vehicleMasterMapper.mapVehicleScheduleToVehicleScheduleDTO(vehicleMasterRepository.findOne(vehicleId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.VehicleScheduleService#saveVehicleSchedule(com.abm
	 * .mainet.swm.dto.VehicleScheduleDTO)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public VehicleScheduleDTO saveVehicleSchedule(VehicleScheduleDTO vehicleIdDetails) {

		vehicleIdDetails.setOccEmpName(mapToString(vehicleIdDetails.getEmpNameList()));
		VehicleScheduleData master = vehicleMasterMapper.mapVehicleScheduleDTOToVehicleSchedule(vehicleIdDetails);
		master = vehicleMasterRepository.save(master);
		// saveHistory(master, MainetConstants.Transaction.Mode.ADD);
		return vehicleMasterMapper.mapVehicleScheduleToVehicleScheduleDTO(master);
	}

	@SuppressWarnings("unused")
	private List<String> mapToList(String comaSeparatedValue) {
		if (comaSeparatedValue != null)
			return Arrays.asList(comaSeparatedValue.split(","));
		return null;
	}

	private String mapToString(List<String> array) {
		if (CollectionUtils.isNotEmpty(array))
			return array.stream().collect(Collectors.joining(","));
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.VehicleScheduleService#updateVehicleSchedule(com.
	 * abm.mainet.swm.dto.VehicleScheduleDTO)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public VehicleScheduleDTO updateVehicleSchedule(VehicleScheduleDTO vehicleIdDetails) {
		VehicleScheduleData master = vehicleMasterMapper.mapVehicleScheduleDTOToVehicleSchedule(vehicleIdDetails);
		master = vehicleMasterRepository.save(master);
		saveHistory(master, MainetConstants.Transaction.Mode.UPDATE);
		return vehicleMasterMapper.mapVehicleScheduleToVehicleScheduleDTO(master);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.VehicleScheduleService#deleteVehicleSchedule(java.
	 * lang.Long)
	 */
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public void deleteVehicleSchedule(Long vehicleId, Long empId, String ipMacAdd) {
		VehicleScheduleData master = vehicleMasterRepository.findOne(vehicleId);
		// TODO: set delete flag
		master.setIsDeleted(MainetConstants.FlagY);
		master.setUpdatedBy(empId);
		master.setUpdatedDate(new Date());
		master.setLgIpMacUpd(ipMacAdd);
		vehicleMasterRepository.save(master);
		// saveHistory(master, MainetConstants.Transaction.Mode.DELETE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.VehicleScheduleService#
	 * searchVehicleScheduleByVehicleTypeAndVehicleRegNo(java.lang.Long,
	 * java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/search")
	public List<VehicleScheduleDTO> searchVehicleScheduleByVehicleTypeAndVehicleNo(
			@QueryParam(value = "vehicleType") Long vehicleType, @QueryParam(value = "vehicleNo") Long vehicleNo,
			@QueryParam(value = "orgId") Long orgId) {

		return vehicleMasterMapper.mapVehicleScheduleListToVehicleScheduleDTOList(vehicleMasterDAO
				.searchVehicleScheduleByVehicleTypeAndVehicleNo(vehicleType, vehicleNo, null, null, orgId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.IVehicleScheduleService#
	 * searchVehicleScheduleByorgId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<VehicleScheduleDTO> searchVehicleScheduleByorgId(@PathParam(value = "orgId") Long orgId) {

		return vehicleMasterMapper.mapVehicleScheduleListToVehicleScheduleDTOList(
				vehicleMasterDAO.searchVehicleScheduleByVehicleTypeAndVehicleNo(null, null, null, null, orgId));
	}

	/**
	 * @param master
	 * @param status
	 */
	private void saveHistory(VehicleScheduleData master, String status) {
		VehicleScheduleHist masterHistory = new VehicleScheduleHist();
		masterHistory.setHStatus(status);
		auditService.createHistory(master, masterHistory);

		master.getTbSwVehicleScheddets().forEach(det -> {
			VehicleScheduleDetHist detHist = new VehicleScheduleDetHist();
			detHist.setHStatus(status);
			detHist.setVesId(master.getVesId());
			try {
				auditService.createHistory(det, detHist);
			} catch (Exception e) {
				LOGGER.error(MainetConstants.ERROR_OCCURED, e);
			}

		});
	}

	/*
	 * List<VehicleScheduleDTO> searchVehicleScheduleByVehicleTypeAndVehicleNo(Long
	 * vehicleType, Long vehicleNo, Long orgId) { }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IVehicleScheduleService#validateVehicleSchedule(
	 * com.abm.mainet.swm.dto.VehicleScheduleDTO)
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean validateVehicleSchedule(VehicleScheduleDTO vehicleScheduleDto) {

		Assert.notNull(vehicleScheduleDto.getOrgid(), MainetConstants.ORGANISATION_ID_NOT_NULL);
		Assert.notNull(vehicleScheduleDto.getVeVetype(), MainetConstants.VEHICLE_TYPE_NOT_NULL);
		Assert.notNull(vehicleScheduleDto.getVeId(), MainetConstants.VEHICLE_RegNo_NOT_NULL);
		Assert.notNull(vehicleScheduleDto.getVesFromdt(), MainetConstants.VEHICLE_FROMDATE_NOT_NULL);
		Assert.notNull(vehicleScheduleDto.getVesTodt(), MainetConstants.VEHICLE_TODATE_NOT_NULL);

		List<VehicleScheduleData> vehicleScheduleList = vehicleMasterDAO.searchVehicleScheduleByVehicleTypeAndVehicleNo(
				vehicleScheduleDto.getVeVetype(), vehicleScheduleDto.getVeId(), null, null,
				vehicleScheduleDto.getOrgid());

		return vehicleScheduleList.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IVehicleScheduleService#findVehicleScheduleDetails
	 * (java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date,
	 * java.util.Date)
	 */
	@SuppressWarnings("null")
	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public VehicleScheduleDTO findVehicleScheduleDetails(Long orgId, Long veId, Long veNo, Date fromdate, Date todate) {
		List<Object[]> vehicleScheduleDetails = vehicleMasterRepository.findVehicleSchedulingDetails(orgId, veId, veNo,
				fromdate, todate);
		Map<Long, String> employeeMap = employeeService
				.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()).stream()
				.collect(Collectors.toMap(SLRMEmployeeMasterDTO::getEmpId, SLRMEmployeeMasterDTO::getFullName));
		VehicleScheduleDTO vehicleScheduleDTO = null;
		VehicleScheduleDTO vsdetdto = null;
		List<VehicleScheduleDTO> vehicleScheduleList = new ArrayList<>();
		ArrayList<String> empList = null;
		String empName1 = null;
		Long empId = 0L;
		if (vehicleScheduleDetails != null && !vehicleScheduleDetails.isEmpty()) {
			vehicleScheduleDTO = new VehicleScheduleDTO();
			for (Object[] vehicleDetails : vehicleScheduleDetails) {
				vsdetdto = new VehicleScheduleDTO();
				empList = new ArrayList<String>();
				if (vehicleDetails[0] != null) {
					vsdetdto.setVeVetype(Long.valueOf(vehicleDetails[0].toString()));
				}
				if (vehicleDetails[1] != null) {
					vsdetdto.setVeDesc(vehicleDetails[1].toString());
				}

				if (vehicleDetails[2] != null) {
					vsdetdto.setVehicleTypeMar(vehicleDetails[2].toString());
				}

				if (vehicleDetails[3] != null) {
					vsdetdto.setVeId(Long.valueOf(vehicleDetails[3].toString()));
				}
				if (vehicleDetails[4] != null) {
					vsdetdto.setVeRegnNo(vehicleDetails[4].toString());
				}
				if (vehicleDetails[5] != null) {
					vsdetdto.setRoadId(vehicleDetails[5].toString());
				}
				if (vehicleDetails[6] != null) {
					vsdetdto.setRoadName(vehicleDetails[6].toString());
				}
				if (vehicleDetails[7] != null) {
					vsdetdto.setRoadNameReg(vehicleDetails[7].toString());
				}
				if (vehicleDetails[8] != null) {
					vsdetdto.setVehicleTypeMar1(vehicleDetails[8].toString());
				}
				if (vehicleDetails[10] != null) {
					vsdetdto.setVehStartTym(new SimpleDateFormat("HH:mm a").format((Date) vehicleDetails[10]));
				}
				if (vehicleDetails[11] != null) {
					vsdetdto.setVehEndTym(new SimpleDateFormat("HH:mm a").format((Date) vehicleDetails[11]));
				}
				if (vehicleDetails[12] != null) {
					vsdetdto.setVehicleScheduledate(
							new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicleDetails[12]));
				}
				if (vehicleDetails[13] != null) {
					vsdetdto.setEmpId(vehicleDetails[13].toString());
					if (vsdetdto.getEmpId().length() <= 2) {
						empName1 = employeeMap.get(Long.valueOf(vsdetdto.getEmpId()));
						empList.add(empName1);
					} else {
						String[] empid = vsdetdto.getEmpId().split(",");
						if (empid.length > 1) {
							for (int j = 0; j < empid.length; j++) {
								empId = Long.valueOf(empid[j]);
								empName1 = employeeMap.get(empId);
								empList.add(empName1);
							}
						}
					}
				}
				vsdetdto.setEmpNameList(empList);
				vehicleScheduleList.add(vsdetdto);
			}
			vehicleScheduleDTO.setVehicleScheduleList(vehicleScheduleList);
		}
		return vehicleScheduleDTO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IVehicleScheduleService#findFuelExpenditureDetails
	 * (java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date,
	 * java.util.Date, java.lang.Long)
	 * 
	 * @Override
	 * 
	 * @Transactional(readOnly = true)
	 * 
	 * @WebMethod(exclude = true) public VehicleFuellingDTO
	 * findFuelExpenditureDetails(Long orgId, Long veVetype, Long veNo, Date
	 * fromdate, Date todate, Long pumpId) { List<Object[]> vehicleFuellingdet =
	 * vehicleMasterRepository.findfuelDetails(orgId, veVetype, veNo, fromdate,
	 * todate, pumpId); VehicleFuellingDTO vehicleFuelDTO = null; VehicleFuellingDTO
	 * vsfueldto = null; List<VehicleFuellingDTO> VehicleFuellingList = new
	 * ArrayList<>(); BigDecimal totalCost = new BigDecimal(0.00); if
	 * (vehicleFuellingdet != null && !vehicleFuellingdet.isEmpty()) {
	 * vehicleFuelDTO = new VehicleFuellingDTO(); for (Object[] vehicleDetails :
	 * vehicleFuellingdet) { vsfueldto = new VehicleFuellingDTO(); if
	 * (vehicleDetails[0] != null) { vsfueldto.setExpensesDate(new
	 * SimpleDateFormat("dd/MM/yyyy").format((Date) vehicleDetails[0])); } if
	 * (vehicleDetails[1] != null) {
	 * vsfueldto.setvName(vehicleDetails[1].toString()); } if (vehicleDetails[2] !=
	 * null) { vsfueldto.setVeNo(vehicleDetails[2].toString()); } if
	 * (vehicleDetails[3] != null) {
	 * vsfueldto.setDriverName(vehicleDetails[3].toString()); } if
	 * (vehicleDetails[4] != null) {
	 * vsfueldto.setVefDmno(Long.valueOf(vehicleDetails[4].toString())); } if
	 * (vehicleDetails[5] != null) { vsfueldto.setAdviceDate(new
	 * SimpleDateFormat("dd/MM/yyyy").format((Date) vehicleDetails[5])); } if
	 * (vehicleDetails[6] != null) {
	 * vsfueldto.setItemName(vehicleDetails[6].toString()); } if (vehicleDetails[8]
	 * != null) { vsfueldto.setQuantity(new
	 * BigDecimal(vehicleDetails[8].toString()).setScale(0,
	 * RoundingMode.HALF_EVEN)); } if (vehicleDetails[9] != null) {
	 * vsfueldto.setCost(new BigDecimal(vehicleDetails[9].toString()).setScale(3,
	 * RoundingMode.HALF_EVEN)); }
	 * 
	 * if (vehicleDetails[11] != null) {
	 * vsfueldto.setQuantityUnit(Long.valueOf(vehicleDetails[11].toString())); }
	 * 
	 * BigDecimal cost = new BigDecimal(vehicleDetails[8].toString()) .multiply(new
	 * BigDecimal(vehicleDetails[9].toString())); totalCost = totalCost.add(new
	 * BigDecimal(cost.toString())); vsfueldto.setSumofItemCost(new
	 * BigDecimal(cost.toString()).setScale(3, RoundingMode.HALF_EVEN));
	 * VehicleFuellingList.add(vsfueldto); } vehicleFuelDTO.setTotalCost(new
	 * BigDecimal(totalCost.toString()).setScale(3, RoundingMode.HALF_EVEN));
	 * vehicleFuelDTO.setVehicleFuellingList(VehicleFuellingList);
	 * 
	 * } return vehicleFuelDTO; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IVehicleScheduleService#vehicleScheduleValidate(
	 * com.abm.mainet.swm.dto.VehicleScheduleDTO)
	 */
	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public String vehicleScheduleValidate(VehicleScheduleDTO vehicleScheduleDto) {
		Long count = null;
		long veid = vehicleScheduleDto.getVeId();
		int dupCount = 0;
		String errMsg = "";
		for (VehicleScheduleDetDTO veschdet : vehicleScheduleDto.getTbSwVehicleScheddets()) {
			if (veschdet.getVesdId() == null) {
				veschdet.setVesdId(Long.parseLong("0"));
			}
			dupCount++;
			vehicleScheduleDto.setSheduleDate(Arrays.asList(veschdet.getVeScheduledate()));
			count = vehicleMasterRepository.vehicleSheduleExist(vehicleScheduleDto.getOrgid(), veid,
					vehicleScheduleDto.getSheduleDate(), veschdet.getVesStartime(), veschdet.getVesEndtime(),
					veschdet.getVesdId());
			if (veschdet.getVesdId().equals(Long.parseLong("0"))) {
				veschdet.setVesdId(null);
			}

			if (count > 0) {
				String errorDate = new SimpleDateFormat("dd/MM/yyyy").format(veschdet.getVeScheduledate());
				errMsg = errMsg + "SR.NO-" + dupCount + " with scheduledDate ->" + errorDate.toString() + " ," + "<br>";
			}
		}
		if (errMsg == "") {
			errMsg = MainetConstants.FlagN;
		} else {
			errMsg = errMsg.substring(0, errMsg.length() - 5);// removing coma and br
		}
		return errMsg.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.IVehicleScheduleService#
	 * updateCurrentVeheicleCodinates(com.abm.mainet.swm.dto. VehicleScheduleDTO)
	 */
	@Transactional
	@POST
	@Path(value = "/updateVeheicleCordinate")
	@Override
	public boolean updateCurrentVeheicleCodinates(@RequestBody VehicleScheduleDTO veheicleSchedule) {
		List<VehicleScheduleData> schedule = vehicleMasterRepository.getVeheicleSchedule(veheicleSchedule.getOrgid(),
				veheicleSchedule.getVeId());
		if (schedule.isEmpty()) {
			return false;
		} else {
			schedule.forEach(sch -> {
				sch.setLatitude(veheicleSchedule.getLatitude());
				sch.setLongitude(veheicleSchedule.getLongitude());
			});
			vehicleMasterRepository.save(schedule);
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IVehicleScheduleService#findMaintenanceExpDetails(
	 * java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date,
	 * java.util.Date, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	@WebMethod(exclude = true)
	public VehicleMaintenanceDTO findMaintenanceExpDetails(Long orgId, Long veId, Long veNo, Date fromdate, Date todate,
			Long vemMetype) {
		// TODO Auto-generated method stub
		List<Object[]> vehicleMntdet = vehicleMasterRepository.findMaintenanceDetails(orgId, veId, veNo, fromdate,
				todate, vemMetype);
		VehicleMaintenanceDTO vehicleMaintenanceDTO = null;
		VehicleMaintenanceDTO vsMntdto = null;
		BigDecimal totalCost = new BigDecimal(0.00);
		List<VehicleMaintenanceDTO> VehicleMaintenanceList = new ArrayList<>();
		if (vehicleMntdet != null && !vehicleMntdet.isEmpty()) {
			vehicleMaintenanceDTO = new VehicleMaintenanceDTO();
			for (Object[] vehicleMntDetails : vehicleMntdet) {
				vsMntdto = new VehicleMaintenanceDTO();
				if (vehicleMntDetails[0] != null) {
					vsMntdto.setMantenanceDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicleMntDetails[0]));
				}

				if (vehicleMntDetails[1] != null) {
					vsMntdto.setVeName(vehicleMntDetails[1].toString());
				}
				if (vehicleMntDetails[2] != null) {
					vsMntdto.setVeNo(vehicleMntDetails[2].toString());
				}
				if (vehicleMntDetails[3] != null) {
					vsMntdto.setVemDowntime(Long.valueOf(vehicleMntDetails[3].toString()));
				}
				if (vehicleMntDetails[4] != null) {
					vsMntdto.setVemDowntimeunit(Long.valueOf(vehicleMntDetails[4].toString()));
				}
				if (vehicleMntDetails[5] != null) {
					vsMntdto.setVemReading(new BigDecimal(vehicleMntDetails[5].toString()).setScale(3, RoundingMode.HALF_EVEN));
				}
				if (vehicleMntDetails[6] != null) {
					vsMntdto.setVemCostincurred(
							new BigDecimal(vehicleMntDetails[6].toString()).setScale(3, RoundingMode.HALF_EVEN));
				}
				totalCost = totalCost.add(vsMntdto.getVemCostincurred());
				VehicleMaintenanceList.add(vsMntdto);
			}
			vehicleMaintenanceDTO
					.setTotalCost(new BigDecimal(totalCost.toString()).setScale(3, RoundingMode.HALF_EVEN));
			vehicleMaintenanceDTO.setVehicleMaintenanceList(VehicleMaintenanceList);
		}
		return vehicleMaintenanceDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.service.IVehicleScheduleService#
	 * findscheduledvehicleDetails(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	@POST
	@Path(value = "/search/{orgId}")
	public List<Object[]> findscheduledvehicleDetails(@PathParam(value = "orgId") Long orgId) {
		List<Object[]> vehicleDetails = vehicleMasterRepository.findscheduledvehicleDetails(orgId);
		return vehicleDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IVehicleScheduleService#findSweepingDetails(java.
	 * lang.Long, java.lang.Long, java.lang.Long)
	 */

	@Override
	@Transactional(readOnly = true)
	public VehicleScheduleDTO findSweepingDetails(Long orgId, Long beatId, Long monthNo, String wasteTypeId) {
		// TODO Auto-generated method stub
		String shortCode1 = "SS";
		String shortCode2 = "DC";
		String s1;
		String shift1 = null;
		String shift2 = null;
		String sweeping;
		List<Object[]> sweepingdet = vehicleMasterRepository.findSweepingDetails(orgId, beatId, monthNo);
		VehicleScheduleDTO vehicleScheduleDTO = null;
		VehicleScheduleDTO vswepdto = null;
		VehicleScheduleDTO vdrainingdto = null;
		List<LookUp> shiftList = CommonMasterUtility.getLookUps("SHT", UserSession.getCurrent().getOrganisation());
		if (shiftList != null && !shiftList.isEmpty()) {
			if (shiftList.get(0) != null) {
				shift1 = shiftList.get(0).getLookUpDesc();
			}
			if (shiftList.get(1) != null) {
				shift2 = shiftList.get(1).getLookUpDesc();
			}
		}
		List<VehicleScheduleDTO> vehicleSweepingList = new ArrayList<>();
		if (sweepingdet != null && !sweepingdet.isEmpty()) {
			vehicleScheduleDTO = new VehicleScheduleDTO();
			for (Object[] sweepingDetails : sweepingdet) {
				vswepdto = new VehicleScheduleDTO();
				vdrainingdto = new VehicleScheduleDTO();

				if (sweepingDetails[7] != null) {
					sweeping = sweepingDetails[7].toString();
					s1 = sweepingDetails[10].toString();

					if (shortCode1.equalsIgnoreCase(wasteTypeId)) {
						if (sweeping.equalsIgnoreCase(shortCode1) && s1.equalsIgnoreCase(shift1)) {
							vswepdto.setVehicleScheduledate(
									new SimpleDateFormat("dd/MM/yyyy").format((Date) sweepingDetails[3]));
							vswepdto.setEmpName(sweepingDetails[4].toString());
							vswepdto.setVehStartTym(new SimpleDateFormat("HH:mm a").format((Date) sweepingDetails[5]));
							vehicleSweepingList.add(vswepdto);
						} else if (sweeping.equalsIgnoreCase(shortCode1) && s1.equalsIgnoreCase(shift2)) {
							vswepdto.setVehicleScheduledate(
									new SimpleDateFormat("dd/MM/yyyy").format((Date) sweepingDetails[3]));
							vswepdto.setSweepingemp(sweepingDetails[4].toString());
							vswepdto.setSweepingintime(
									new SimpleDateFormat("HH:mm a").format((Date) sweepingDetails[5]));
							vehicleSweepingList.add(vswepdto);
						}
					}
					if (shortCode2.equalsIgnoreCase(wasteTypeId)) {
						if (sweeping.equalsIgnoreCase(shortCode2) && s1.equalsIgnoreCase(shift1)) {
							vdrainingdto.setVehicleScheduledate(
									new SimpleDateFormat("dd/MM/yyyy").format((Date) sweepingDetails[3]));
							vdrainingdto.setDrainingempName(sweepingDetails[4].toString());
							vdrainingdto.setDariningvehStartTym(
									new SimpleDateFormat("HH:mm a").format((Date) sweepingDetails[5]));
							vehicleSweepingList.add(vdrainingdto);
						} else if (sweeping.equalsIgnoreCase(shortCode2) && s1.equalsIgnoreCase(shift2)) {
							vdrainingdto.setVehicleScheduledate(
									new SimpleDateFormat("dd/MM/yyyy").format((Date) sweepingDetails[3]));
							vdrainingdto.setDrainingemp(sweepingDetails[4].toString());
							vdrainingdto.setDrainingintime(
									new SimpleDateFormat("HH:mm a").format((Date) sweepingDetails[5]));
							vehicleSweepingList.add(vdrainingdto);
						}
					}
				}
			}
			vehicleScheduleDTO.setVehicleScheduleList(vehicleSweepingList);
		}
		return vehicleScheduleDTO;

	}

	@Override
	@Transactional(readOnly = true)
	public VehicleScheduleDTO findVehicleSchdetailsByVehNo(Long orgId, Long veId, Date date) {
		List<Object[]> vehicleScheduleDetails = vehicleMasterRepository.findVehicleSchdetailsByVehNo(orgId, veId, date);
		Map<Long, String> employeeMap = employeeService
				.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()).stream()
				.collect(Collectors.toMap(SLRMEmployeeMasterDTO::getEmpId, SLRMEmployeeMasterDTO::getFullName));
		Map<Long, String> employeeMap1 = employeeService
				.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()).stream()
				.collect(Collectors.toMap(SLRMEmployeeMasterDTO::getEmpId, SLRMEmployeeMasterDTO::getEmpUId));

		VehicleScheduleDTO vehicleScheduleDTO = null;
		VehicleScheduleDTO vsdetdto = null;
		List<VehicleScheduleDTO> vehicleScheduleList = new ArrayList<>();
		HashSet<String> employeeList = new HashSet<>();
		HashSet<String> employeeIdList = new HashSet<>();
		String empName1 = null;
		String empUId = null;
		Long empId = 0L;
		if (vehicleScheduleDetails != null && !vehicleScheduleDetails.isEmpty()) {
			vehicleScheduleDTO = new VehicleScheduleDTO();
			for (Object[] vehicleDetails : vehicleScheduleDetails) {
				vsdetdto = new VehicleScheduleDTO();
				if (vehicleDetails[0] != null) {
					vsdetdto.setEmpId(vehicleDetails[0].toString());
					if (vsdetdto.getEmpId().length() == 1) {
						empName1 = employeeMap.get(Long.valueOf(vsdetdto.getEmpId()));
						employeeList.add(empName1);
						empUId = employeeMap1.get(Long.valueOf(vsdetdto.getEmpId()));
						employeeIdList.add(empUId);
					} else {
						String[] empid = vsdetdto.getEmpId().split(",");
						if (empid.length > 1) {
							for (int j = 0; j < empid.length; j++) {
								empId = Long.valueOf(empid[j]);
								empName1 = employeeMap.get(empId);
								employeeList.add(empName1);
								empUId = employeeMap1.get(empId);
								employeeIdList.add(empUId);
							}
						}
					}
				}
				vehicleScheduleDTO.setEmployeeNameList(employeeList);
				vehicleScheduleDTO.setEmployeeUIdList(employeeIdList);
				vehicleScheduleList.add(vsdetdto);
			}
			vehicleScheduleDTO.setVehicleScheduleList(vehicleScheduleList);
		}
		return vehicleScheduleDTO;
	}
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public void deleteVehicleScheduleDet(Long vehicleId, Long vehicleScheduleDetId, Long empId,
			String ipMacAdd) {
		VehicleScheduleData master = vehicleMasterRepository.findOne(vehicleId);
		// TODO: set delete flag
		 master.getTbSwVehicleScheddets().forEach(det->{
	        	if(det.getVesdId().equals(vehicleScheduleDetId)) {
	        		det.setIsDeleted(MainetConstants.FlagY);
	        		det.setUpdatedBy(empId);
	        		det.setUpdatedDate(new Date());
	        		det.setLgIpMacUpd(ipMacAdd);
	        	}
	        });// TODO: set delete flag
		vehicleMasterRepository.save(master);

		// saveHistory(master, MainetConstants.Transaction.Mode.DELETE);
	}
	
	/*
	 * @Override public List<VehicleScheduleDTO> getVehicleByNumberVe(Long veid,
	 * Long orgid) { List<Object[]> list =
	 * vehicleMasterRepository.getVehicleByNumberVe(veid, orgid);
	 * List<VehicleScheduleDTO> listDto = new ArrayList<VehicleScheduleDTO>(); for
	 * (Object[] obj : list) { VehicleScheduleDTO dto = new VehicleScheduleDTO();
	 * dto.setVeId(Long.valueOf((Long)obj[0])); dto.setVeNo(obj[1].toString());
	 * listDto.add(dto); } return listDto; }
	 */

	/*
	 * @Override public List<VehicleScheduleDTO> getVehicleByRegNumber(Long orgid,
	 * Long veid) { List<Object[]> list =
	 * vehicleMasterRepository.getVehicleRegByNo(veid, orgid);
	 * List<VehicleScheduleDTO> listDto = new ArrayList<VehicleScheduleDTO>(); for
	 * (Object[] obj : list) { VehicleScheduleDTO dto = new VehicleScheduleDTO();
	 * dto.setVeId(Long.valueOf((Long)obj[0])); dto.setVeNo(obj[1].toString());
	 * listDto.add(dto); } return listDto; }
	 */

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public List<GenVehicleMasterDTO>
	 * getVehicleByNo(Long orgid, Long veNo) { List<GenVehicleMasterDTO> list =
	 * vehicleMasterService.getVehicleByNo(veNo, orgid); List<OEMWarrantyDTO>
	 * listDto = new ArrayList<OEMWarrantyDTO>(); for (Object[] obj : list) {
	 * GenVehicleMasterDTO dto = new GenVehicleMasterDTO();
	 * dto.setVeId(Long.valueOf((Long)obj[0])); dto.setVeNo(obj[1].toString());
	 * listDto.add(dto); } return listDto; }
	 */

}
