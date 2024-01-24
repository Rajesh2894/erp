/**
 * 
 */
package com.abm.mainet.water.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.water.dao.DuplicatePlumberLicenseRepository;
import com.abm.mainet.water.dao.PlumberLicenseRepository;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.utility.WaterCommonUtility;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.water.service.DuplicatePlumberLicenseService")
@Api(value = "/duplicateplumberlicenseservice")
@Path("/duplicateplumberlicenseservice")
public class DuplicatePlumberLicenseServiceImpl implements DuplicatePlumberLicenseService {

	@Autowired
	DuplicatePlumberLicenseRepository duplicatePlumberLicenseRepository;

	@Resource
	private IWorkflowTyepResolverService iWorkflowTyepResolverService;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private IWorkflowExecutionService iWorkflowExecutionService;

	@Resource
	PlumberLicenseRepository plumberLicenseRepository;

	@Autowired
	private IFileUploadService fileUploadService;

	@Resource
	private CommonService commonService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;

	/*
	 * @Autowired private TbWtDuplicatePlumberLicenseRepository
	 * tbWtDuplicatePlumberLicenseRepository;
	 */

	@Autowired
	private PlumberLicenseService plumberLicenseService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	private static final Logger LOGGER = Logger.getLogger(DuplicatePlumberLicenseServiceImpl.class);

	@Override
	@Transactional
	@POST
	@Path("/saveduplicateplumberlicense")
	public PlumberLicenseResponseDTO saveDuplicatePlumberData(PlumberLicenseRequestDTO requestDTO) {
		Organisation organisation = new Organisation();
		PlumberLicenseRequestDTO saveDuplicateData = saveDuplicatePlumberLicenseAndAttachFile(requestDTO);
		organisation.setOrgid(requestDTO.getOrgId());
		final PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
		responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
		responseDTO.setApplicationId(saveDuplicateData.getApplicationId());
		if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(responseDTO.getStatus())) {
			plumberLicenseService.initiateWorkFlowForFreeService(saveDuplicateData);
		}

		/*
		 * ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); String
		 * fileName = MainetConstants.PlumberJasperFiles.PLUM_APP.getColDescription();
		 * // String jrxmlName = //
		 * MainetConstants.PlumberJasperFiles.PLUM_SUB_APP_REPORT.getColDescription();
		 * String menuURL = MainetConstants.URLBasedOnShortCode.DPL.getUrl(); String
		 * type = PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED; final String
		 * jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" +
		 * MainetConstants.FILE_PATH_SEPARATOR;
		 * 
		 * Map oParms = new HashMap(); oParms.put("plumber_id",
		 * saveDuplicateData.getPlumberId().toString()); oParms.put("SUBREPORT_DIR",
		 * jrxmlFileLocation); oParms.put("apm_application_id",
		 * saveDuplicateData.getApplicationId().toString());
		 * generateJasperReportPDF(saveDuplicateData, outputStream, oParms, fileName,
		 * menuURL, type);
		 */
		/*
		 * plumberLicenseService.sendSMSandEMail(requestDTO,
		 * saveDuplicateData.getApplicationId(), null,
		 * MainetConstants.WaterServiceShortCode.DUPLICATE_PLUMBER_LICENSE,
		 * organisation);
		 */
		return responseDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getPlumberMasterPlumId(Long applicationId, Long orgid) {
		Long plumId = duplicatePlumberLicenseRepository.findDuplicatePlumberIdByApplicationNumber(applicationId, orgid);
		PlumberLicenseRequestDTO plumDto = new PlumberLicenseRequestDTO();
		plumDto.setPlumberId(plumId);
		return plumId;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PlumberLicenseRequestDTO> getPlumberDetailsByPlumId(Long plumberId, Long orgId) {
		Organisation organisation = new Organisation();
		final List<PlumberMaster> list = duplicatePlumberLicenseRepository.getPlumberDetailsByPlumId(plumberId, orgId);
		final List<PlumberLicenseRequestDTO> plumRequestDto = new ArrayList<>();
		PlumberLicenseRequestDTO plumDto = new PlumberLicenseRequestDTO();
		if ((null != list) && !list.isEmpty()) {
			PlumberMaster plumberMaster = list.get(0);
			/*
			 * if (plumberMaster.getApplicantTitle() != null) { String titleCode =
			 * CommonMasterUtility
			 * .getNonHierarchicalLookUpObject(plumberMaster.getApplicantTitle(),
			 * organisation) .getLookUpCode(); plumDto.setPlumCpdTitle(titleCode); }
			 */
			/*
			 * if (plumberMaster.getPlumSex() != null) { String genderCode =
			 * CommonMasterUtility
			 * .getNonHierarchicalLookUpObject(plumberMaster.getPlumSex(),
			 * organisation).getLookUpCode(); plumDto.setPlumGender(genderCode); }
			 */
			plumDto.setPlumberFName(plumberMaster.getPlumFname());
			// plumDto.setPlumberFName(plumberMaster.getPlumFname());
			// plumDto.setApplicantTitle(plumberMaster.getApplicantTitle());
			plumDto.setPlumberLicenceNo(plumberMaster.getPlumLicNo());
			plumDto.setPlumberMName(plumberMaster.getPlumMname());
			plumDto.setPlumberLName(plumberMaster.getPlumLname());
			plumDto.setPlumberId(plumberMaster.getPlumId());
			plumDto.setPlumContactNo(plumberMaster.getPlumContactNo());
			// plumDto.setEmailId(plumberMaster.getEmailId());
			// plumDto.setAadharNo(plumberMaster.getAadharNo());
			plumDto.setApplicationId(plumberMaster.getApmApplicationId());
			plumDto.setPlumAddress(plumberMaster.getPlumAddress());
			plumDto.setPlumAppDate(new Date());
		}

		/*
		 * Object[] reciptMas =
		 * plumberLicenseRepository.getLicenseDetail(plumDto.getPlumberLicenceNo()); if
		 * (reciptMas != null) { if (reciptMas[0] != null) {
		 * plumDto.setPlumberId(Long.valueOf(reciptMas[0].toString())); } if
		 * (reciptMas[1] != null) {
		 * plumDto.setApplicationId(Long.valueOf(reciptMas[1].toString())); } if
		 * (reciptMas[2] != null) { plumDto.setPlumLicFromDate((Date) reciptMas[2]); }
		 * if (reciptMas[3] != null) {
		 * 
		 * plumDto.setPlumLicToDate((Date) reciptMas[3]); } if (plumDto.getPlumberId()
		 * != null) { Object[] renewMas =
		 * plumberLicenseRepository.getRenewDetail(plumDto.getPlumberId()); if (renewMas
		 * != null) { if (renewMas[0] != null) { plumDto.setPlumLicFromDate((Date)
		 * renewMas[0]); } if (renewMas[1] != null) { plumDto.setPlumLicToDate((Date)
		 * renewMas[1]); } } } }
		 */
		plumRequestDto.add(plumDto);
		return plumRequestDto;
	}

	@Override
	@Transactional
	public void updateApprovalStatus(Long applicationNumber, String appovalStatus, Date date) {
		duplicatePlumberLicenseRepository.updateApprovalStatus(applicationNumber, appovalStatus, date);
	}

	@Override
	@Transactional
	public Object[] getPlumberApplicationDetail(String licNo, PlumberLicenseRequestDTO plumDto) {
		// TODO Auto-generated method stub
		Object[] reciptMas = null;
		// Object[] reciptMas = plumberLicenseRepository.getLicenseDetail(licNo);
		if (reciptMas != null) {
			if (reciptMas[0] != null) {
				plumDto.setPlumberId(Long.valueOf(reciptMas[0].toString()));
			}
			if (reciptMas[1] != null) {
				plumDto.setApplicationId(Long.valueOf(reciptMas[1].toString()));
			}
			if (reciptMas[2] != null) {
				plumDto.setPlumLicFromDate((Date) reciptMas[2]);
			}
			if (reciptMas[3] != null) {
				plumDto.setPlumLicToDate((Date) reciptMas[3]);
			}
			if (plumDto.getPlumberId() != null) {
				Object[] renewMas = plumberLicenseRepository.getRenewDetail(plumDto.getPlumberId());
				if (renewMas != null) {
					if (renewMas[0] != null) {
						plumDto.setPlumRenewToDate((Date) renewMas[0]);
					}
					if (renewMas[1] != null) {
						plumDto.setPlumRenewFromDate((Date) renewMas[1]);
					}
				}
			}
		}
		return reciptMas;
	}

	@Override
	@Transactional
	public PlumberLicenseRequestDTO saveDuplicatePlumberLicenseAndAttachFile(PlumberLicenseRequestDTO requestDTO) {

		final RequestDTO applicantDetailDTO = new RequestDTO();
		final PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
		applicantDetailDTO.setTitleId(requestDTO.getApplicant().getApplicantTitle());
		applicantDetailDTO.setfName(requestDTO.getApplicant().getApplicantFirstName());
		applicantDetailDTO.setlName(requestDTO.getApplicant().getApplicantLastName());
		applicantDetailDTO.setMobileNo(requestDTO.getApplicant().getMobileNo());
		applicantDetailDTO.setEmail(requestDTO.getApplicant().getEmailId());
		applicantDetailDTO.setAreaName(requestDTO.getApplicant().getAreaName());
		applicantDetailDTO.setPincodeNo(Long.parseLong(requestDTO.getApplicant().getPinCode()));
		applicantDetailDTO.setServiceId(requestDTO.getServiceId());
		applicantDetailDTO.setDeptId(requestDTO.getDeptId());
		applicantDetailDTO.setUserId(requestDTO.getUserId());
		applicantDetailDTO.setOrgId(requestDTO.getOrgId());
		applicantDetailDTO.setLangId((long) requestDTO.getApplicant().getLangId());
		applicantDetailDTO.setReferenceId(requestDTO.getPlumberLicenceNo());
		if (requestDTO.getApplicant().getIsBPL().equals(MainetConstants.FlagY)) {
			applicantDetailDTO.setBplNo(requestDTO.getApplicant().getBplNo());
		}

		final Organisation organisation = new Organisation();
		organisation.setOrgid(requestDTO.getOrgId());

		/*
		 * final LookUp lookUp = CommonMasterUtility
		 * .getNonHierarchicalLookUpObject(Long.valueOf(requestDTO.getApplicant().
		 * getGender()), organisation);
		 */

		// applicantDetailDTO.setGender(lookUp.getLookUpCode());
		applicantDetailDTO.setCityName(requestDTO.getApplicant().getVillageTownSub());
		applicantDetailDTO.setBlockName(requestDTO.getApplicant().getBlockName());
		if ((null != requestDTO.getApplicant().getAadharNo())
				&& !MainetConstants.BLANK.equals(requestDTO.getApplicant().getAadharNo())) {
			final String aadhar = requestDTO.getApplicant().getAadharNo().replaceAll("\\s+", MainetConstants.BLANK);
			final Long aadhaarNo = Long.parseLong(aadhar);
			applicantDetailDTO.setUid(aadhaarNo);
		}
		if (null != requestDTO.getApplicant().getDwzid1()) {
			applicantDetailDTO.setZoneNo(requestDTO.getApplicant().getDwzid1());
		}
		if (null != requestDTO.getApplicant().getDwzid2()) {
			applicantDetailDTO.setWardNo(requestDTO.getApplicant().getDwzid2());
		}
		if (null != requestDTO.getApplicant().getDwzid3()) {
			applicantDetailDTO.setBlockNo(String.valueOf(requestDTO.getApplicant().getDwzid3()));
		}
		if (requestDTO.getAmount() == 0d) {
			applicantDetailDTO.setPayStatus("F");
		}
		try {
			final Long applicationId = applicationService.createApplication(applicantDetailDTO);
			applicantDetailDTO.setApplicationId(applicationId);

			responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
			if ((requestDTO.getDocumentList() != null) && !requestDTO.getDocumentList().isEmpty()) {
				fileUploadService.doFileUpload(requestDTO.getDocumentList(), applicantDetailDTO);
			}
			responseDTO.setApplicationId(applicationId);
			requestDTO.setApplicationId(applicationId);
		} catch (Exception e) {
			LOGGER.error("In saving new saveDuplicatePlumberLicenseAndAttachFile()", e);
			responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
			throw new FrameworkException("Error while saveDuplicatePlumberLicenseAndAttachFile() ", e);
		}
		WaterCommonUtility.sendSMSandEMail(requestDTO.getApplicant(), requestDTO.getApplicationId(), null, "DPL",
				organisation);
		return requestDTO;
	}

	@Override
	@Transactional
	public void updateAuthStatus(String authStatus, Long authBy, Date authDate, Long applicationId, Long orgId) {
		plumberLicenseRepository.updateAuthStatus(authStatus, authBy, authDate, applicationId, orgId);
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public boolean getchecklistStatusInApplicationMaster(Long plumId, Long orgid) {
		boolean checkListFlag = false;
		/*
		 * TbDuplicatePlumberLicenseEntity duplicatePlumberDetails =
		 * duplicatePlumberLicenseRepository .findLatestDuplicateLicenseByPlumId(plumId,
		 * orgid);// findLatestDuplicateLicenseByPlumId(plumId, orgid);
		 * 
		 * String chckFlag = null; if (duplicatePlumberDetails != null) { Object result
		 * = duplicatePlumberLicenseRepository.getchecklistStatusInApplicationMaster(
		 * duplicatePlumberDetails.getApmApplicationId(),
		 * duplicatePlumberDetails.getOrgId()); if (result != null) { chckFlag =
		 * result.toString(); if (duplicatePlumberDetails.getApprovalFlag() != null) {
		 * if (duplicatePlumberDetails.getApprovalFlag().equals("P") &&
		 * chckFlag.equals("N")) { checkListFlag = false; } else { if
		 * (duplicatePlumberDetails.getApprovalFlag().equals("P") &&
		 * chckFlag.equals("Y")) { checkListFlag = true; } } } } else { if
		 * (duplicatePlumberDetails.getApprovalFlag().equals("P") && chckFlag == null) {
		 * checkListFlag = true; } } }
		 */
		return checkListFlag;
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId,
			final Long orgId) {
		final CFCApplicationAddressEntity master = iCFCApplicationAddressService
				.getApplicationAddressByAppId(applicationId, orgId);
		WardZoneBlockDTO wardZoneDTO = null;
		if (master != null) {
			wardZoneDTO = new WardZoneBlockDTO();
			if (master.getApaZoneNo() != null) {
				wardZoneDTO.setAreaDivision1(master.getApaZoneNo());
			}
			if (master.getApaWardNo() != null) {
				wardZoneDTO.setAreaDivision2(master.getApaWardNo());
			}
			if ((null != master.getApaBlockno()) && !master.getApaBlockno().isEmpty()) {
				final Long blockNo = Long.parseLong(master.getApaBlockno());
				wardZoneDTO.setAreaDivision3(blockNo);
			}
		}
		return wardZoneDTO;
	}

}
