package com.abm.mainet.water.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Foreach;
import org.apache.velocity.runtime.directive.contrib.For;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.bill.dao.ChequeDishonorDao;
import com.abm.mainet.bill.service.ChequeDishonorService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.repository.TbServiceReceiptJpaRepository;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.repository.TbOrganisationJpaRepository;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.TbChargeMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.repository.TbCsmrInfoRepository;
import com.abm.mainet.water.rest.dto.ViewCsmrConnectionDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardBucketDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardConnectionsCreatedDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardMetricsDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardPendingConnectionsDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardRequestDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardResponseDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardTodaysCollectionDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardWaterConnectionsDTO;
import com.abm.mainet.water.rest.dto.WaterDashboardWaterSewerageConnectionsDTO;
import com.abm.mainet.water.rest.ui.controller.WaterDashboardRestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition.Bucket;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

@Service
@WebService(endpointInterface = "com.abm.mainet.water.service.WaterDashboardService")
@Api(value = "/waterDashboard")
@Path("/waterDashboard")

public class WaterDashboardServiceImpl implements WaterDashboardService{

	private static final Logger LOGGER = Logger.getLogger(WaterDashboardServiceImpl.class);
	
	@Autowired
	private TbCsmrInfoRepository tbCsmrInfoRepository;
	
	@Autowired
    private IOrganisationService iOrganisationService;
	 
	@Autowired
    private TbOrganisationService organisationService;

	@Resource
	private TbOrganisationJpaRepository tbOrganisationJpaRepository;
	
	@Autowired
    private ChequeDishonorDao ChequeDishonorDao;

	@Autowired
	private ChequeDishonorService chequeDishonorService;

	@Resource
	private TbTaxMasJpaRepository tbTaxMasJpaRepository;

	@Autowired
	private TbChargeMasterService tbChargeMasterService;

	@Autowired
	private TbServiceReceiptJpaRepository tbServiceReceiptJpaRepository;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasService;
	
	@Autowired
	private TbDepartmentService departmentService;

	@Autowired
	private WaterCommonService waterCommonService;

	@POST
	@Path("/fetchWaterData")
	@Transactional(readOnly = true)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public List<WaterDashboardResponseDTO> fetchWaterData(WaterDashboardRequestDTO requestDto) {
		Date date1 = requestDto.getDate();
		 LOGGER.info("Water API DATE:"+date1);
		String dateN = new SimpleDateFormat(MainetConstants.DATE_FORMATS).format(date1);
		List<WaterDashboardResponseDTO> waterResponseDtoList = new ArrayList<>();
		List<TbOrganisation> orgList = organisationService.findAll();
		orgList = orgList.stream().filter(or -> !or.getOrgid().equals(Long.valueOf(MainetConstants.NUMBER_ONE))).collect(Collectors.toList());
		for (final TbOrganisation orgData : orgList) {
			if (orgData.getOrgid() != null && !orgData.getoNlsOrgname().isEmpty() && !orgData.getoNlsOrgname().contains("test")) {
				String billingMethod = MainetConstants.BLANK;
				LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " fetchWaterData() method");
				WaterDashboardResponseDTO waterResponse = new WaterDashboardResponseDTO();
				final String orgName = tbOrganisationJpaRepository.findOrgNameById(orgData.getOrgid());
				final Organisation org = new Organisation();
				org.setOrgid(orgData.getOrgid());
				LOGGER.info("Water API OrgId:"+org.getOrgid());
				LookUp stateLookup = null;
				String state = null;
				if (org.getOrgid() != 0l && orgData.getOrgCpdIdState() != null) {
					stateLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(orgData.getOrgCpdIdState(), org);
					state = (stateLookup.getDescLangFirst().toLowerCase()).substring(0, 2);
					LOGGER.info("Water API State Name ---------------->" + state);
				}
				
				Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
				if (orgData.getOrgid() != 0l) {
					long transactions=0;
					long todaysClosedApplicationsCount =0;
					
					transactions = cfcApplicationMasService.getTransctionsData(orgData.getOrgid(), deptId, date1);

					todaysClosedApplicationsCount = cfcApplicationMasService
							.getCountClosedApplications(orgData.getOrgid(), deptId, dateN);
					
					List<WaterDashboardConnectionsCreatedDTO> ccnCreatedDtoList = null;
					ccnCreatedDtoList = getWaterDashboardConnCreated(orgData.getOrgid(), dateN);
					
					List<WaterDashboardWaterConnectionsDTO> waterCcnDtoList = null;
					waterCcnDtoList = getWaterDashboardWaterConnList(orgData.getOrgid(), dateN);

					List<WaterDashboardTodaysCollectionDTO> todyCollecDtoList = null;
					todyCollecDtoList = getWaterDashboardtodyCollecDtoList(orgData.getOrgid(), dateN);

					List<WaterDashboardPendingConnectionsDTO> pendingDtoList = null;
					pendingDtoList = getDashboardPendingConnectionsDTOs(orgData.getOrgid(), dateN);

					List<WaterDashboardWaterSewerageConnectionsDTO> sewerageDtoList = new ArrayList<>();

					WaterDashboardMetricsDTO metricsDto = new WaterDashboardMetricsDTO();
					WaterDashboardMetricsDTO metricsDtoList;

					List<Object[]> totalCompletedApplicationsWithinSLA = tbCsmrInfoRepository.totalCompletedApplicationsWithinSLA(orgData.getOrgid(), dateN);
					if (!totalCompletedApplicationsWithinSLA.isEmpty()) {
						for (final Object appl[] : totalCompletedApplicationsWithinSLA) {
							WaterDashboardMetricsDTO master = new WaterDashboardMetricsDTO();
							master.setTodaysCompletedApplicationsWithinSLA(
									appl[1] != null ? Long.valueOf(appl[1].toString()) : 0);
							metricsDto.setTodaysCompletedApplicationsWithinSLA(
									master.getTodaysCompletedApplicationsWithinSLA() != null
											? master.getTodaysCompletedApplicationsWithinSLA()
											: 0l);
						}
					}

					metricsDto.setTransactions(transactions);
					metricsDto.setTodaysTotalApplications(transactions);
					metricsDto.setTodaysClosedApplications(todaysClosedApplicationsCount);
					metricsDto.setConnectionsCreated(ccnCreatedDtoList);
					metricsDto.setTodaysCollection(todyCollecDtoList);
					metricsDto.setPendingConnections(pendingDtoList);
					metricsDto.setSlaCompliance(0l);
					metricsDto.setAvgDaysForApplicationApproval(0l);
					metricsDto.setStipulatedDays(0l);
					metricsDto.setSewerageConnections(sewerageDtoList);
					metricsDto.setWaterConnections(waterCcnDtoList);

					waterResponse.setMetrics(metricsDto);
					waterResponse.setUlb(StringUtils.deleteWhitespace(state + "." + orgData.getoNlsOrgname().toLowerCase()));
					waterResponse.setDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(date1));
					waterResponse.setModule("WS");
					waterResponse.setRegion("CityA");
					waterResponse.setState(stateLookup != null ? stateLookup.getDescLangFirst() : "Chhattisgarh");
					waterResponse.setWard("Ward 1");
					waterResponseDtoList.add(waterResponse);
					LOGGER.info("Water API Data added for ulb ---------------->"+StringUtils.deleteWhitespace(state+"."+orgData.getoNlsOrgname().toLowerCase())+" " + waterResponse);
				}
			}
		}
		LOGGER.info("Water API Data for all ulb is added to list for Date:"+date1);
		return waterResponseDtoList;
	}

	@Override
	public List<WaterDashboardConnectionsCreatedDTO> getWaterDashboardConnCreated(Long orgId, String dateN) {

		List<Object[]> meterTypeList = waterCommonService.fetchCountMeterTypeCcn(orgId, dateN);
		List<WaterDashboardBucketDTO> bucketList = new ArrayList<>();

		WaterDashboardBucketDTO bucketDto = null;
		if (meterTypeList != null) {
			for (Object[] mType : meterTypeList) {
				bucketDto = new WaterDashboardBucketDTO();
				bucketDto.setName(mType[0]!=null?String.valueOf(mType[0]):MainetConstants.WHITE_SPACE);
				bucketDto.setValue(((BigInteger) mType[1]).longValue());
				bucketList.add(bucketDto);

			}
		}
		WaterDashboardConnectionsCreatedDTO ccnCreateddto = new WaterDashboardConnectionsCreatedDTO();
		ccnCreateddto.setGroupBy(MainetConstants.waterDashboard.CONNECTION_TYPE);
		ccnCreateddto.setBuckets(bucketList);

		List<WaterDashboardConnectionsCreatedDTO> ccnCreatedDtoList = new ArrayList<>();
		ccnCreatedDtoList.add(ccnCreateddto);
		LOGGER.info("Response of getWaterDashboardConnCreated ends");
		return ccnCreatedDtoList;
	}
	
	@Override
	public List<WaterDashboardTodaysCollectionDTO> getWaterDashboardtodyCollecDtoList(Long orgId, String dateN) {
		List<WaterDashboardBucketDTO> bucketList = new ArrayList<>();
		List<WaterDashboardBucketDTO> bucketList_1 = new ArrayList<>();
		List<WaterDashboardBucketDTO> bucketList_2 = new ArrayList<>();
		List<WaterDashboardBucketDTO> bucketList_3 = new ArrayList<>();
	
		List<Object[]> usageTypeList = waterCommonService.fetchCountUsageTypeCcn(orgId, dateN);
		WaterDashboardBucketDTO bucketDto = null;
		if (usageTypeList != null) {
			for (Object[] uType : usageTypeList) {
				bucketDto = new WaterDashboardBucketDTO();
				bucketDto.setName(uType[0]!=null?(String) uType[0]:MainetConstants.WHITE_SPACE);
				bucketDto.setValue(((BigDecimal) uType[1]).longValue());
				bucketList.add(bucketDto);
			}
		}
		List<Object[]> payType = chequeDishonorService.fetchPaymentMode(orgId, dateN);
		WaterDashboardBucketDTO bucketDtoPay = null;
		if(payType!=null) {
		for (Object[] paymode : payType) {
			bucketDtoPay = new WaterDashboardBucketDTO();
			bucketDtoPay.setName(paymode[0]!=null?paymode[0].toString():MainetConstants.WHITE_SPACE);
			bucketDtoPay.setValue(((BigDecimal) paymode[1]).longValue());
			bucketList_1.add(bucketDtoPay);
		 }
		}
		List<Object[]> taxTypes = tbChargeMasterService.countTaxCollection(orgId, dateN);
		WaterDashboardBucketDTO bucketDtoTax = null;
		if (taxTypes != null) {
			for (Object[] taxType : taxTypes) {
				bucketDtoTax = new WaterDashboardBucketDTO();
				bucketDtoTax.setName(taxType[0]!=null?taxType[0].toString():MainetConstants.WHITE_SPACE);
				bucketDtoTax.setValue(((BigDecimal) taxType[1]).longValue());
				bucketList_2.add(bucketDtoTax);
			}
		}

		List<Object[]> connTypes = waterCommonService.fetchCountCcnType(orgId, dateN);
		WaterDashboardBucketDTO bucketDtocnn = null;
		if (connTypes != null) {
			for (Object[] cnnType : connTypes) {
				bucketDtocnn = new WaterDashboardBucketDTO();
				bucketDtocnn.setName(cnnType[0]!=null?cnnType[0].toString():MainetConstants.WHITE_SPACE);
				bucketDtocnn.setValue(cnnType[1]!=null?((BigDecimal) cnnType[1]).longValue():0);
				bucketList_3.add(bucketDtocnn);
				}
			}
		
		WaterDashboardTodaysCollectionDTO todyCollectDtoList1 = new WaterDashboardTodaysCollectionDTO();

		todyCollectDtoList1.setGroupBy(MainetConstants.waterDashboard.USAGE_TYPE);
		todyCollectDtoList1.setBuckets(bucketList);

		WaterDashboardTodaysCollectionDTO todyCollectDtoList2 = new WaterDashboardTodaysCollectionDTO();

		todyCollectDtoList2.setGroupBy(MainetConstants.waterDashboard.PAY_MODE);
		todyCollectDtoList2.setBuckets(bucketList_1);

		WaterDashboardTodaysCollectionDTO todyCollectDtoList3 = new WaterDashboardTodaysCollectionDTO();

		todyCollectDtoList3.setGroupBy(MainetConstants.waterDashboard.TAX_GROUP);
		todyCollectDtoList3.setBuckets(bucketList_2);

		WaterDashboardTodaysCollectionDTO todyCollectDtoList4 = new WaterDashboardTodaysCollectionDTO();

		todyCollectDtoList4.setGroupBy(MainetConstants.waterDashboard.CONNECTION_TYPE);
		todyCollectDtoList4.setBuckets(bucketList_3);

		List<WaterDashboardTodaysCollectionDTO> todayList = new ArrayList<WaterDashboardTodaysCollectionDTO>();

		todayList.add(todyCollectDtoList1);
		todayList.add(todyCollectDtoList2);
		todayList.add(todyCollectDtoList3);
		todayList.add(todyCollectDtoList4);

		 LOGGER.info("Response of getWaterDashboardtodyCollecDtoList ends");
		return todayList;
	}

	@Override
	public List<WaterDashboardPendingConnectionsDTO> getDashboardPendingConnectionsDTOs(Long orgId, String dateN) {
		WaterDashboardPendingConnectionsDTO pendingCcnDto = new WaterDashboardPendingConnectionsDTO();
		List<Object[]> pendingList = waterCommonService.totalPendingApplications(orgId, dateN);
		List<WaterDashboardBucketDTO> bucketList = new ArrayList<>();

		Map<String, Long> mapList = new HashMap<>();

		WaterDashboardBucketDTO bucketDto = null;

		mapList.put("0to3Days", 0L);
		mapList.put("3to7Days", 0L);
		mapList.put("7to10Days", 0L);
		mapList.put("10toMoreDays", 0L);

		if (pendingList != null) {
			for (Object[] plist : pendingList) {

				Date miliToDate = Utility.getStartOfDay((Date) plist[1]); // Date of request
				LocalDate appDate = miliToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				Period periDate = Period.between(LocalDate.now(), appDate); // diff betwn dor and currnt date

				// get no of days between two dates
				int subDays =Utility.getDaysBetDates(miliToDate, Utility.getStartOfDay(new Date()));
				// diff betwn dor and currnt date in days

				if (subDays <= 3) {
					mapList.put("0to3Days", mapList.get("0to3Days") + 1);
				} else if (subDays > 3 && subDays <= 7) {
					mapList.put("3to7Days", mapList.get("3to7Days") + 1);
				} else if (subDays > 7 && subDays <= 10) {
					mapList.put("7to10Days", mapList.get("7to10Days") + 1);
				} else {
					mapList.put("10toMoreDays", mapList.get("10toMoreDays") + 1);
				}
			}
		}

		for (String noOfDays : mapList.keySet()) {
			bucketDto = new WaterDashboardBucketDTO();
			bucketDto.setName(noOfDays);
			bucketDto.setValue(mapList.get(noOfDays));
			bucketList.add(bucketDto);
		}

		pendingCcnDto.setGroupBy(MainetConstants.DURATION);
		pendingCcnDto.setBuckets(bucketList);

		List<WaterDashboardPendingConnectionsDTO> pendingCcnDtoList = new ArrayList<>();
		pendingCcnDtoList.add(pendingCcnDto);
		 LOGGER.info("Response of getDashboardPendingConnectionsDTOs ends");
		return pendingCcnDtoList;
	}

	@Override
	public List<WaterDashboardWaterConnectionsDTO> getWaterDashboardWaterConnList(Long orgId, String dateN) {

		List<Object[]> meterTypeList = waterCommonService.fetchCountMeterTypeCcn(orgId, dateN);
		List<WaterDashboardBucketDTO> bucketList = new ArrayList<>();
		List<WaterDashboardBucketDTO> bucketList1 = new ArrayList<>();
		
		WaterDashboardBucketDTO bucketDto = null;
		if (meterTypeList != null) {
			for (Object[] mType : meterTypeList) {
				bucketDto = new WaterDashboardBucketDTO();
				bucketDto.setName(mType[0]!=null?String.valueOf(mType[0]):MainetConstants.WHITE_SPACE);
				bucketDto.setValue(((BigInteger) mType[1]).longValue());
				bucketList.add(bucketDto);

			}
		}
		List<Object[]> usageTypeList = waterCommonService.fetchCountUsageTypeCcn(orgId, dateN);
		WaterDashboardBucketDTO bucketDto1 = null;
		if (usageTypeList != null) {
			for (Object[] uType : usageTypeList) {
				bucketDto1 = new WaterDashboardBucketDTO();
				bucketDto1.setName(uType[0]!=null?(String) uType[0]:MainetConstants.WHITE_SPACE);
				bucketDto1.setValue(((BigInteger) uType[2]).longValue());
				bucketList1.add(bucketDto1);
			}
		}
		WaterDashboardWaterConnectionsDTO waterCcnDto = new WaterDashboardWaterConnectionsDTO();
		waterCcnDto.setGroupBy(MainetConstants.waterDashboard.METER_TYPE);
		waterCcnDto.setBuckets(bucketList);
		
		WaterDashboardWaterConnectionsDTO waterCcnDto1 = new WaterDashboardWaterConnectionsDTO();

		waterCcnDto1.setGroupBy(MainetConstants.waterDashboard.USAGE_TYPE);
		waterCcnDto1.setBuckets(bucketList1);

		List<WaterDashboardWaterConnectionsDTO> waterCcnDtoList = new ArrayList<>();
		waterCcnDtoList.add(waterCcnDto);
		waterCcnDtoList.add(waterCcnDto1);
		 LOGGER.info("Response of getWaterDashboardWaterConnList ends");
		return waterCcnDtoList;
	}

}