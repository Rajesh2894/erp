package com.abm.mainet.bnd.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.bnd.service.IRtsService")
@Api(value = "/rts-service")
@Path("/rts-service")
@Service("RtsService")
public class RtsServiceImpl implements IRtsService {

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ILocationMasService locationMasterService;

	@Autowired
	DepartmentService departmentService;


	@Autowired
	private IChecklistVerificationService ichckService;
	@Autowired
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	


	@Override
	@POST
	@ApiOperation(value = "fetch wardZones", notes = "fetch wardZones")
	@Path("/fetch-wardZone/{orgId}")
	@Transactional
	public Map<Long, String> fetchWardZone(Long orgId) {

		List<WardZoneDTO> wardList = new ArrayList<WardZoneDTO>();

		wardList = locationMasterService.findlocationByOrgId(orgId);

		Map<Long, String> wardMapList = new LinkedHashMap<Long, String>();
		for (WardZoneDTO ward : wardList) {
			wardMapList.put(ward.getLocationId(), ward.getLocationName());
		}
		return wardMapList;
	}

	@Override
	@POST
	@ApiOperation(value = "fetch rts service", notes = "fetch rts service")
	@Path("/fetch-rts-services/{orgId}")
	@Transactional
	public Map<String, String> fetchRtsService(Long orgId) {
		List<Object[]> service = null;
		List<Object[]> service2 = null;
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode("RTS");
		final Map<String, String> serviceMap = new LinkedHashMap<String, String>();
		 Long activeStatusId= CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.Common_Constant.ACTIVE_FLAG, MainetConstants.Common_Constant.ACN,orgId);
		service2 = serviceMasterService.findAllActiveServicesByDepartment(orgId, deptId,activeStatusId);
		List<String[]> serviceString = new ArrayList<String[]>();
		String[] tempService;

		for (final Object[] obj : service2) {
			if (obj[0] != null) {
				tempService = new String[3];
				if (obj[1].toString().compareTo("First Appeal") != 0
						&& obj[1].toString().compareTo("Second Appeal") != 0) {
					serviceMap.put(obj[2].toString(), obj[1].toString());
				}

			}
		}
		return serviceMap;
	}

	@Transactional
	@Override
	@POST
	@ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
	@Path("/fetch-rts-service-info/{orgId}")
	public Map<String, String> serviceInfo(@PathParam("orgId") Long orgId) {

		ServiceMaster serviceMasterData = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceByShortName(MainetConstants.RightToService.DCS, Long.valueOf(orgId));
		Map<String, String> serviceDataMap = new LinkedHashMap<String, String>();

		if (serviceMasterData != null) {
			if (StringUtils
					.equalsIgnoreCase(CommonMasterUtility
							.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify(),
									ApplicationContextProvider.getApplicationContext()
											.getBean(IOrganisationService.class).getOrganisationById(orgId))
							.getLookUpCode(), MainetConstants.FlagA)) {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagY);
			} else {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagN);
			}
			if (StringUtils.equalsIgnoreCase(serviceMasterData.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagY);

			} else {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagN);
			}

			serviceDataMap.put("serviceId", serviceMasterData.getSmServiceId().toString());
			serviceDataMap.put("serviceName", serviceMasterData.getSmServiceName());
			serviceDataMap.put("deptId", serviceMasterData.getTbDepartment().getDpDeptid().toString());
		}

		return serviceDataMap;
	}


	// This method is used for geting uploaded document for view
	@Override
	public List<DocumentDetailsVO> getRtsUploadedCheckListDocuments(Long applicationnId, Long orgId) {
		// TODO Auto-generated method stub
		List<DocumentDetailsVO> attachsList = new ArrayList<>();

		Optional<TbCfcApplicationMstEntity> tbmstEnt = tbCfcApplicationMstJpaRepository
				.findByApmApplicationId(applicationnId);
		List<CFCAttachment> cfcAtt = new ArrayList();
		cfcAtt = ichckService.findAttachmentsForAppId(applicationnId, null, orgId);
		if (tbmstEnt.isPresent()) {
			TbCfcApplicationMstEntity entity = tbmstEnt.get();
			if (entity != null) {
				Boolean isNewTask = false;
				if (entity.getApmChklstVrfyFlag() == null) {
					isNewTask = true;
				}
				if (isNewTask || (entity.getApmChklstVrfyFlag().equalsIgnoreCase(MainetConstants.FlagH)
						|| entity.getApmChklstVrfyFlag().equalsIgnoreCase(MainetConstants.FlagN))) {
					cfcAtt.forEach(att -> {
						DocumentDetailsVO dvo = new DocumentDetailsVO();
						dvo.setAttachmentId(att.getAttId());
						dvo.setCheckkMANDATORY(att.getMandatory());
						dvo.setDocumentSerialNo(att.getClmSrNo());
						dvo.setDocumentName(att.getAttFname());
						dvo.setUploadedDocumentPath(att.getAttPath());
						attachsList.add(dvo);

					});
				} else if (entity.getApmChklstVrfyFlag().equalsIgnoreCase(MainetConstants.FlagY)) {
					cfcAtt.forEach(att -> {
						if (att.getClmAprStatus() != null
								&& att.getClmAprStatus().equalsIgnoreCase(MainetConstants.FlagY)) {
							DocumentDetailsVO dvo = new DocumentDetailsVO();
							dvo.setAttachmentId(att.getAttId());
							dvo.setCheckkMANDATORY(att.getMandatory());
							dvo.setDocumentSerialNo(att.getClmSrNo());
							dvo.setDocumentName(att.getAttFname());
							dvo.setUploadedDocumentPath(att.getAttPath());
							attachsList.add(dvo);
						}

					});

				} else {
					cfcAtt.forEach(att -> {
						if ((att.getClmRemark() == null || att.getClmRemark().isEmpty())
								|| (att.getClmAprStatus() != null
										&& att.getClmAprStatus().equalsIgnoreCase(MainetConstants.FlagY))) {
							DocumentDetailsVO dvo = new DocumentDetailsVO();
							dvo.setAttachmentId(att.getAttId());
							dvo.setCheckkMANDATORY(att.getMandatory());
							dvo.setDocumentSerialNo(att.getClmSrNo());
							dvo.setDocumentName(att.getAttFname());
							dvo.setUploadedDocumentPath(att.getAttPath());
							attachsList.add(dvo);
						}

					});

				}
			}
		}

		return attachsList;
	}

	@Transactional
	@Override
	@POST
	@ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId and ServiceCode", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
	@Path("/fetch-rts-service-information/{orgId}/{serviceShortCode}")
	public LinkedHashMap<String, Object> serviceInformation(@PathParam("orgId") Long orgId,
			@PathParam("serviceShortCode") String serviceShortCode) {

		ServiceMaster serviceMasterData = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class).getServiceByShortName(serviceShortCode, Long.valueOf(orgId));
		LinkedHashMap<String, Object> serviceDataMap = new LinkedHashMap<String, Object>();

		if (serviceMasterData != null) {
			if (StringUtils
					.equalsIgnoreCase(CommonMasterUtility
							.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify(),
									ApplicationContextProvider.getApplicationContext()
											.getBean(IOrganisationService.class).getOrganisationById(orgId))
							.getLookUpCode(), MainetConstants.FlagA)) {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagY);
			} else {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagN);
			}
			if (StringUtils.equalsIgnoreCase(serviceMasterData.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagY);

			} else {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagN);
			}
			serviceDataMap.put("serviceNameEng", serviceMasterData.getSmServiceName());
			serviceDataMap.put("serviceNameReg", serviceMasterData.getSmServiceNameMar());
			serviceDataMap.put("deptNameEng", serviceMasterData.getTbDepartment().getDpDeptdesc());
			serviceDataMap.put("deptNameReg", serviceMasterData.getTbDepartment().getDpNameMar());
			serviceDataMap.put("smServiceDuration", serviceMasterData.getSmServiceDuration());
		}

		return serviceDataMap;
	}
	
	
	
	@Transactional
	@Override
	@POST
	@ApiOperation(value = "Fetch checkList And Charges Applicable Flag By OrgId", notes = "Fetch checkList And Charges Applicable Flag By OrgId", response = Object.class)
	@Path("/fetch-rts-service/{orgId}/{serviceShortCode}")
	public Map<String, String> serviceInfomation(@PathParam("orgId") Long orgId,@PathParam("serviceShortCode") String serviceShortCode) {

		ServiceMaster serviceMasterData = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceByShortName(serviceShortCode, Long.valueOf(orgId));
		Map<String, String> serviceDataMap = new LinkedHashMap<String, String>();

		if (serviceMasterData != null) {
			if (StringUtils
					.equalsIgnoreCase(CommonMasterUtility
							.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify(),
									ApplicationContextProvider.getApplicationContext()
											.getBean(IOrganisationService.class).getOrganisationById(orgId))
							.getLookUpCode(), MainetConstants.FlagA)) {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagY);
			} else {
				serviceDataMap.put("checkListApplFlag", MainetConstants.FlagN);
			}
			if (StringUtils.equalsIgnoreCase(serviceMasterData.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagY);

			} else {
				serviceDataMap.put("applicationchargeApplFlag", MainetConstants.FlagN);
			}

			serviceDataMap.put("serviceId", serviceMasterData.getSmServiceId().toString());
			serviceDataMap.put("serviceName", serviceMasterData.getSmServiceName());
			serviceDataMap.put("deptId", serviceMasterData.getTbDepartment().getDpDeptid().toString());
		}

		return serviceDataMap;
	}
	
	@Override
	public CommonChallanDTO createPushToPayApiRequest(CommonChallanDTO offlineDTO2, Long empId, Long orgId,
			String serviceName, String amount) throws BeansException, IOException, InterruptedException {
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(serviceName, orgId);
		offlineDTO2.setOrgId(orgId);
		offlineDTO2.setAmountToPay(amount);
		offlineDTO2.setDeptId(sm.getTbDepartment().getDpDeptid());
		offlineDTO2.setUserId(empId);
		final CommonChallanDTO master = ApplicationContextProvider.getApplicationContext()
				.getBean(IChallanService.class).callPushToPayApiForPayment(offlineDTO2);

		return master;
	}

}