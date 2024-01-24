package com.abm.mainet.rti.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WorkFlow;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbComparentDet;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.rti.dao.IRtiApplicationServiceDAO;
import com.abm.mainet.rti.domain.TbRtiApplicationDetails;
import com.abm.mainet.rti.domain.TbRtiFwdEmployeeEntity;
import com.abm.mainet.rti.domain.TbRtiMediaDetails;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.dto.RtiFrdEmployeeDetails;
import com.abm.mainet.rti.dto.RtiLoiReportDTO;
import com.abm.mainet.rti.dto.RtiMediaListDTO;
import com.abm.mainet.rti.repository.RtiForwardRepository;
import com.abm.mainet.rti.repository.RtiHistoryRepository;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.validator.RtiPioResponseValidator;
import com.abm.mainet.rti.utility.RtiUtility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class RtiPioResponseModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Resource
	private IFileUploadService iFileUploadService;

	@Autowired
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Resource
	private MessageSource messageSource;

	@Autowired
	private IWorkflowExecutionService workflowExecutionService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private RtiUtility rtiUtility;

	@Autowired
	private TbLoiMasService iTbLoiMasService;

	@Autowired
	private TbDepartmentService iTbDepartmentService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;
	@Autowired
	private RtiHistoryRepository histRepo;

	@Autowired
	private IRtiApplicationServiceDAO irtiDetails;

	@Autowired
	private IWorkflowTyepResolverService iworkFlowResolveService;
	
	@Autowired
	private RtiForwardRepository rtiForwardRepository;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<Long> fileCountUpload;
	private RtiApplicationFormDetailsReqDTO reqDTO = new RtiApplicationFormDetailsReqDTO();
	private List<RtiApplicationFormDetailsReqDTO> reqDtoList = new ArrayList<RtiApplicationFormDetailsReqDTO>();
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	private List<CFCAttachment> fetchApplnUpload = new ArrayList<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private ServiceMaster serviceMaster = new ServiceMaster();
	private List<MediaChargeAmountDTO> chargeAmountList = new ArrayList<>();
	private Boolean isValidationError = false;
	private Double finalAmountToPay = 0.0;
	private Set<LookUp> employeeList = new HashSet<>();
	private Set<LookUp> departments = new HashSet<>();
	private List<TbApprejMas> apprejMasList = new ArrayList<>();
	private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	private List<RtiMediaListDTO> rtiMediaListDTO = new ArrayList<>();
	private List<DocumentDetailsVO> pioDoc = new ArrayList<>();
	private String pioName;
	private String pioNumber;
	private String pioEmail;
	private String dateDesc;
	private Department department = new Department();
	private long a3a4Quantity;
	private long flopCopy;
	private Long largeCopy;
	private long Inspection;
	private long photo;
	private long inspectioncharges;
	private Double pageQuantity1;
	private Double pageQuantity2;
	private Double pageQuantity3;
	private Double pageQuantity4;
	private Double pageQuantityForPhoto;
	private Double pageQuantityForCharges;
	private BigDecimal grandTotal;
	private String amountInWords;
	private List<TbLoiMas> loidata = new ArrayList<>();
	private long page;
	private Double page1;
	private Double quantityforA4;
	private Double quantityforA3;
	private RtiLoiReportDTO rtiLoiReportDTO = new RtiLoiReportDTO();

	private Long loiId;
	private Double totalBeforeSave;
	private Double totalAfterSave;
	private String editableLOIflag;
	private String workflowFlag;
	private String editableworkflowflag;
	private BigDecimal loiMasterappId;
	private String envFlag;
	private List<Organisation> listOrg;

	private String applicationStatus;
	private List<CFCAttachment> fetchPioUploadDoc = new ArrayList<>();
	private Set<LookUp> fdlDepartments = new HashSet<>();
	private String PrefixName;
	private WorkflowTaskAction careDepartmentAction;
	private List<CFCAttachment> fetchStampDoc = new ArrayList<>();
	private List<TbComparentDet> wardList = new ArrayList<>();
	private List<TbComparentDet> zoneList = new ArrayList<>();
	private List<CFCAttachment> fetchPostalDoc = new ArrayList<>();
	private List<RtiFrdEmployeeDetails> rtiFrdEmpDet=new ArrayList<RtiFrdEmployeeDetails>();
	private List<Long> sliDaysList=new ArrayList<>();
	private List<Object[]> rtiEmployee=new ArrayList<>();

	private long pageA3;
	private double pageQuantityA3;
	private List<RtiMediaListDTO> loidet=new ArrayList<>();
	private List<LookUp> rtiAction = new ArrayList<>();
	private List<LookUp> partialInfoFlag = new ArrayList<>();

	public List<Long> getFileCountUpload() {
		return fileCountUpload;
	}

	public List<LookUp> getRtiAction() {
		return rtiAction;
	}

	public void setRtiAction(List<LookUp> rtiAction) {
		this.rtiAction = rtiAction;
	}

	public List<LookUp> getPartialInfoFlag() {
		return partialInfoFlag;
	}

	public void setPartialInfoFlag(List<LookUp> partialInfoFlag) {
		this.partialInfoFlag = partialInfoFlag;
	}

	public List<RtiFrdEmployeeDetails> getRtiFrdEmpDet() {
		return rtiFrdEmpDet;
	}

	public void setRtiFrdEmpDet(List<RtiFrdEmployeeDetails> rtiFrdEmpDet) {
		this.rtiFrdEmpDet = rtiFrdEmpDet;
	}

	public void setFileCountUpload(List<Long> fileCountUpload) {
		this.fileCountUpload = fileCountUpload;
	}

	public RtiApplicationFormDetailsReqDTO getReqDTO() {
		return reqDTO;
	}

	public void setReqDTO(RtiApplicationFormDetailsReqDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public boolean validateDuplicateMediaType() {
		validateBean(this, RtiPioResponseValidator.class);

		if (hasValidationErrors()) {
			isValidationError = true;
			return false;
		}
		return true;

	}

	public List<MediaChargeAmountDTO> getChargeAmountList() {
		return chargeAmountList;
	}

	public void setChargeAmountList(List<MediaChargeAmountDTO> chargeAmountList) {
		this.chargeAmountList = chargeAmountList;
	}

	public Boolean getIsValidationError() {
		return isValidationError;
	}

	public void setIsValidationError(Boolean isValidationError) {
		this.isValidationError = isValidationError;
	}

	public Double getFinalAmountToPay() {
		return finalAmountToPay;
	}

	public void setFinalAmountToPay(Double finalAmountToPay) {
		this.finalAmountToPay = finalAmountToPay;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public Set<LookUp> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(Set<LookUp> employeeList) {
		this.employeeList = employeeList;
	}

	@Override
	protected void initializeModel() {

		List<LookUp> depts = rtiApplicationDetailService
				.getActiveDepartment(UserSession.getCurrent().getOrganisation().getOrgid());
		depts.forEach(d -> {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(d.getDescLangFirst());
			detData.setDescLangSecond(d.getDescLangSecond());
			detData.setLookUpId(d.getLookUpId());
			departments.add(detData);
		});

	}

	@Override
	public boolean saveForm() {

		List<RtiMediaListDTO> mediaDto = getRtiMediaListDTO();
		TbRtiMediaDetails tbRtiMediaDetails = null;
		TbLoiDet loiDet = null;
		TbLoiMas tbLoiMas = new TbLoiMas();
		final UserSession session = UserSession.getCurrent();
		RtiApplicationFormDetailsReqDTO requestDTO = getReqDTO();
		final Date sysDate = UtilityService.getSQLDate(new Date());
		final List<TbLoiDet> loiDetails = new ArrayList<>();
		Map<Long, DocumentDetailsVO> fileUploadMap = new LinkedHashMap<>();
		List<DocumentDetailsVO> docList = getPioDoc();

		Employee emp = UserSession.getCurrent().getEmployee();
		requestDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
//for setting updated user details
		requestDTO.setUserId(emp.getEmpId());
		requestDTO.setUpdateDate(new Date());
		final Long payType = CommonMasterUtility
				.getValueFromPrefixLookUp(MainetConstants.FlagC, PrefixConstants.LookUpPrefix.LPT).getLookUpId();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final String fileNetPath = messageSource.getMessage("upload.physicalPath", new Object[] {}, StringUtils.EMPTY,
				Locale.ENGLISH);
		/* updating the PIO response */
		// Code for reset ward zone incase of wardzone is null
		TbRtiApplicationDetails rtiDet = irtiDetails.getRtiApplicationDetailsForDSCL(requestDTO.getApmApplicationId(),
				requestDTO.getOrgId());
		if (requestDTO.getTrdWard1() == null) {
			requestDTO.setTrdWard1(rtiDet.getTrdWard1());
		}
		if (requestDTO.getTrdWard2() == null) {
			requestDTO.setTrdWard2(rtiDet.getTrdWard2());
		}
		if(((requestDTO.getRtiAction()!=null && rtiUtility.getPrefixCode(PrefixConstants.ACTION, Long.valueOf(requestDTO.getRtiAction())).equals(MainetConstants.AuthStatus.FORWARDTODEPARTMENT))&&requestDTO.getRtiDeptidFdate()==null)) {
			requestDTO.setRtiDeptidFdate(new Date());
		}
			rtiApplicationDetailService.saveRtiApplication(requestDTO);

		// code for saving history data
		rtiApplicationDetailService.saveRtiApplicationHistory(requestDTO);

		/* end */
		try {
			saveRtiFwdInfo(requestDTO);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final ServiceMaster service = serviceMasterService
				.getServiceByShortName(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE, orgId);
		final TbDepartment department = iTbDepartmentService.findById(service.getTbDepartment().getDpDeptid());

		// For saving Rti Pio uploaded document at the time for FDE
		// US#111591
		RequestDTO requestdtos = setApplicantRequestDto(requestDTO);

		if ((this.getAttachments() != null) && !this.getAttachments().isEmpty()) {
			requestdtos.setReferenceId(requestDTO.getRtiNo() + MainetConstants.DEPT_SHORT_NAME.RTI);
			requestdtos.setApplicationId(Long.valueOf(requestDTO.getRtiNo()));

			iFileUploadService.doFileUpload(this.getAttachments(), requestdtos);

		}
		// US#111591 end
		/*
		 * Media List and LOI generated only when LOI is Applicable and Decision is
		 * Approved
		 */
		if (requestDTO.getLoiApplicable() != null
				&& MainetConstants.CommonMasterUi.LOI_APPLICABLE.equalsIgnoreCase(rtiUtility
						.getPrefixCode(PrefixConstants.LOI_APPLICABLE, Long.valueOf(requestDTO.getLoiApplicable())))
				&& MainetConstants.AuthStatus.APPROVED.equalsIgnoreCase(
						rtiUtility.getPrefixCode(PrefixConstants.ACTION, Long.valueOf(requestDTO.getRtiAction())))
				&& getRtiMediaListDTO() != null && !getRtiMediaListDTO().isEmpty()) {

			/* file upload for media type */

			try {
				prepareFileUploadForPio(docList);
			} catch (IOException e) {
				logger.error("Exception has been occurred in file byte to string conversions", e);
			}
			/* End */

			/* Inactives Loi number */
			iTbLoiMasService.inactiveLoi(requestDTO.getApmApplicationId(), orgId);

			/* Inactives media pf inactiveLoi */

			rtiApplicationDetailService.inactiveMedia(Long.valueOf(requestDTO.getRtiId()), orgId);

			for (int i = 0; i < this.getChargeAmountList().size(); i++) {

				/* Media Type save */
				tbRtiMediaDetails = new TbRtiMediaDetails();
				Integer rtiId = requestDTO.getRtiId();
				tbRtiMediaDetails.setRtiMedId(rtiId.longValue());
				tbRtiMediaDetails.setLangId((long) session.getLanguageId());
				tbRtiMediaDetails.setLgIpMac(session.getEmployee().getEmppiservername());
				tbRtiMediaDetails.setlModDate(new Date());
				tbRtiMediaDetails.setOrgId(session.getOrganisation().getOrgid());
				tbRtiMediaDetails.setUpdatedBy(session.getEmployee().getEmpId());
				tbRtiMediaDetails.setUpdatedDate(sysDate);
				tbRtiMediaDetails.setUserId(session.getEmployee().getEmpId());
				tbRtiMediaDetails.setMediaType(rtiUtility.getPrefixId(PrefixConstants.MEDIA_TYPE,
						this.getChargeAmountList().get(i).getMediaType()));
				tbRtiMediaDetails.setMediaQuantity(this.getChargeAmountList().get(i).getQuantity().longValue());
				tbRtiMediaDetails.setMediaAmount(this.getChargeAmountList().get(i).getTotal());
				tbRtiMediaDetails.setMediaDesc(getRtiMediaListDTO().get(i).getMediaDesc());
				tbRtiMediaDetails.setMediastatus(MainetConstants.FlagA);
				// tbRtiMediaDetails.setChargeAmount(this.getChargeAmountList().get(i).getChargeAmount());

				RequestDTO requestDto = setApplicantRequestDto(requestDTO);

				if ((getPioDoc() != null) && !getPioDoc().isEmpty()) {
					List<DocumentDetailsVO> getImgList = null;
					getImgList = new ArrayList<>();
					requestDto.setReferenceId(requestDTO.getRtiNo());
					requestDto.setApplicationId(Long.valueOf(requestDTO.getRtiNo()));
					List<DocumentDetailsVO> getList = getPioDoc();
					for (int j = 0; j < getList.size(); j++) {
						DocumentDetailsVO img = getList.get(i);

						getImgList.add(img);
						break;
					}

					iFileUploadService.doFileUpload(getImgList, requestDto);

				}
				rtiApplicationDetailService.saveRtiMediaList(tbRtiMediaDetails);

				/* ends */

				/* LOI details Data Setting */
				loiDet = new TbLoiDet();
				loiDet.setLoiChrgid(this.getChargeAmountList().get(i).getTaxId());
				loiDet.setLoiAmount(BigDecimal.valueOf(this.getChargeAmountList().get(i).getTotal()));
				loiDet.setLgIpMac(Utility.getMacAddress());
				loiDet.setOrgid(session.getOrganisation().getOrgid());
				loiDet.setUserId(session.getEmployee().getEmpId());
				loiDet.setLmoddate(new Date());
				loiDet.setLoiCharge(MainetConstants.Common_Constant.YES);
				loiDet.setLoiPaytype(payType);
				loiDet.setLoiDetN1(new Double(this.getChargeAmountList().get(i).getChargeAmount()).longValue());
				loiDet.setLoiDetN2(this.getChargeAmountList().get(i).getQuantity().longValue());
				loiDetails.add(loiDet);
				/* ends */

				/* final Amount to pay as LOI */
				setFinalAmountToPay(finalAmountToPay + this.getChargeAmountList().get(i).getTotal());
				// Us#116366

			}
			/* LOI Number Generation */
			tbLoiMas.setLgIpMac(Utility.getMacAddress());
			tbLoiMas.setOrgid(session.getOrganisation().getOrgid());
			tbLoiMas.setUserId(session.getEmployee().getEmpId());
			tbLoiMas.setLoiDate(new Date());
			tbLoiMas.setLmoddate(new Date());
			tbLoiMas.setLoiPaid(MainetConstants.Common_Constant.NO);
			tbLoiMas.setLoiStatus(MainetConstants.FlagA);
			final Calendar calendar = Calendar.getInstance();
			tbLoiMas.setLoiYear(calendar.get(Calendar.YEAR));
			tbLoiMas.setLoiApplicationId(requestDTO.getApmApplicationId());
			tbLoiMas.setLoiRefId(requestDTO.getApmApplicationId());
			tbLoiMas.setLoiAmount(BigDecimal.valueOf(this.getFinalAmountToPay()));
			tbLoiMas.setLoiServiceId(service.getSmServiceId());
			tbLoiMas.setServiceShortCode(service.getSmShortdesc());
			tbLoiMas.setDeptShortCode(department.getDpDeptcode());

			iTbLoiMasService.saveLoiDetails(tbLoiMas, loiDetails, null);

			/* Us#116366 Sending SMS to Customer after LOI generation */
			sendSmsAndEmail(tbLoiMas, requestDTO);

			/* ends */

		}
		List<LookUp> envLookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.ENV,
				UserSession.getCurrent().getOrganisation().getOrgid());
		boolean dsclEnv = envLookUpList.stream().anyMatch(env -> env.getLookUpCode().equals(MainetConstants.ENV_DSCL)
				&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		for (int j = 0; j < getRtiMediaListDTO().size(); j++) {
			if (this.getRtiMediaListDTO().get(j).getQuantity() != null)
				if (dsclEnv
						&& this.getRtiMediaListDTO().get(j).getQuantity().longValue() <= MainetConstants.NUMBERS.FIVE) {
					sendSmsAndEmailToApplicant(reqDTO);
				}
		}

		/* Update Workflow */
		try {

			WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
			workflowdto.setProcessName(MainetConstants.URLBasedOnShortCode
					.valueOf(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE).getProcessName());
			WorkflowTaskAction workflowAction = new WorkflowTaskAction();
			if (requestDTO.getLoiApplicable() != null
					&& MainetConstants.CommonMasterUi.LOI_APPLICABLE.equalsIgnoreCase(rtiUtility
							.getPrefixCode(PrefixConstants.LOI_APPLICABLE, Long.valueOf(requestDTO.getLoiApplicable())))
					&& this.getFinalAmountToPay() > 0 && MainetConstants.AuthStatus.APPROVED.equalsIgnoreCase(rtiUtility
							.getPrefixCode(PrefixConstants.ACTION, Long.valueOf(requestDTO.getRtiAction())))) {
				workflowAction.setIsLoiGenerated(true);
			} else {
				workflowAction.setIsLoiGenerated(false);
			}
			workflowAction.setApplicationId(requestDTO.getApmApplicationId());
			workflowAction.setDateOfAction(new Date());
			workflowAction.setOrgId(session.getOrganisation().getOrgid());
			workflowAction.setEmpType(emp.getEmplType());
			workflowAction.setEmpName(emp.getEmpname());
			workflowAction.setCreatedBy(emp.getEmpId());
			workflowAction.setCreatedDate(new Date());
			workflowAction.setTaskId(requestDTO.getTaskId());
			workflowAction.setModifiedBy(emp.getEmpId());
			String decision = rtiUtility.getPrefixCode(PrefixConstants.ACTION, Long.valueOf(requestDTO.getRtiAction()));

			switch (decision) {

			case MainetConstants.AuthStatus.APPROVED:

				requestDTO.setRtiAction(MainetConstants.WorkFlow.Decision.APPROVED);
				workflowAction.setEmpId(emp.getEmpId());
				this.setApplicationStatus("A");

				break;

			case MainetConstants.AuthStatus.REJECTED:
				requestDTO.setRtiAction(MainetConstants.WorkFlow.Decision.REJECTED);
				workflowAction.setEmpId(emp.getEmpId());
				break;
			case MainetConstants.AuthStatus.FORWARDTOOTHERORGANISATION:
				requestDTO.setRtiAction(MainetConstants.WorkFlow.Decision.FORWARD_TO_OTHER_ORGANISATION);
				workflowAction.setEmpId(emp.getEmpId());
				workflowAction.setOrgId(requestDTO.getFrdOrgId());
				// for setting workflow details
				Boolean flag = rtiApplicationDetailService.setWorkflowData(workflowAction, workflowdto, requestDTO);
				if (!flag) {

					return false;
				}
				break;
			case MainetConstants.AuthStatus.FORWARDTODEPARTMENT:
				requestDTO.setRtiAction(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE);
				workflowAction.setEmpId(emp.getEmpId());
				workflowAction.setForwardToEmployeeType(WorkFlow.EMPLOYEE);
				workflowAction.setForwardToEmployee(requestDTO.getEmpname());
				// added for User Story #85006
				if (requestDTO.getEmpList() != null && !requestDTO.getEmpList().isEmpty()) {
					String val = "";
					for (Long l : requestDTO.getEmpList()) {
						if (l != null) {
							val = val + l + ",";
						}
					}
					
					workflowAction.setForwardToEmployee(val);
					
				}
				
				break;
			// US#109003
			case MainetConstants.AuthStatus.FORWARDTOOTHELOCATION:
				requestDTO.setRtiAction(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE);
				// Code added for workflow will be according to Forwarded location
				// start
				WorkflowMas mas = null;
				try {
					Long deptId = iTbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
					Long serviceId = serviceMasterService.getServiceIdByShortName(orgId,
							MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
					if (deptId != null && serviceId != null) {
						mas = iworkFlowResolveService.resolveServiceWorkflowType(orgId, deptId, serviceId,
								getCareDepartmentAction().getCodIdOperLevel1(),
								getCareDepartmentAction().getCodIdOperLevel2(),
								getCareDepartmentAction().getCodIdOperLevel3(),
								getCareDepartmentAction().getCodIdOperLevel4(),
								getCareDepartmentAction().getCodIdOperLevel5());
						workflowAction.setWorkflowId(mas.getWfId());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				// end
				workflowAction.setEmpId(emp.getEmpId());
				workflowAction.setForwardToEmployeeType("LOCATION");
				workflowAction.setForwardDepartment(Long.valueOf(requestDTO.getRtiDeptId()).toString());
				workflowAction.setServiceId(service.getSmServiceId());
				if (getCareDepartmentAction() != null) {
					if (getCareDepartmentAction().getCodIdOperLevel1() != null) {
						workflowAction.setCodIdOperLevel1(getCareDepartmentAction().getCodIdOperLevel1());
					}
					if (getCareDepartmentAction().getCodIdOperLevel2() != null) {
						workflowAction.setCodIdOperLevel2(getCareDepartmentAction().getCodIdOperLevel2());
					}
					if (getCareDepartmentAction().getCodIdOperLevel3() != null) {
						workflowAction.setCodIdOperLevel3(getCareDepartmentAction().getCodIdOperLevel3());
					}
					if (getCareDepartmentAction().getCodIdOperLevel4() != null) {
						workflowAction.setCodIdOperLevel4(getCareDepartmentAction().getCodIdOperLevel4());
					}
					if (getCareDepartmentAction().getCodIdOperLevel5() != null) {
						workflowAction.setCodIdOperLevel5(getCareDepartmentAction().getCodIdOperLevel5());
					}
				}
				break;

			default:
				requestDTO.setRtiAction(null);
			}
			workflowAction.setDecision(requestDTO.getRtiAction());
			workflowdto.setWorkflowTaskAction(workflowAction);
			//if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)||!StringUtils.equals("Deemed PIO", UserSession.getCurrent().getRoleCode()))
			if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL)||!decision.equals(MainetConstants.AuthStatus.FORWARDTODEPARTMENT))			
			workflowExecutionService.updateWorkflow(workflowdto);

			/* end */

			/* Sending SMS to Customer after LOI generation */
			// sendSmsAndEmail(tbLoiMas, requestDTO);
			/* end */

			/* Setting Success Message */
			/*
			 * setSuccessMessage( getAppSession().getMessage("rti.pio.message") +
			 * " Application Number : " + requestDTO.getApmApplicationId() +
			 * " ,RTI Number : " + requestDTO.getRtiNo() +
			 * Optional.ofNullable(tbLoiMas.getLoiNo()).map(result -> " ,Loi Number : " +
			 * tbLoiMas.getLoiNo()) .orElse(MainetConstants.BLANK) +
			 * Optional.ofNullable(tbLoiMas.getLoiAmount()) .map(result -> " ,Loi Amount : "
			 * + tbLoiMas.getLoiAmount()) .orElse(MainetConstants.BLANK));
			 */
			/* end */

			/* requestDTO.setTotalamteditableview(tbLoiMas.getLoiAmount()); */

		} catch (Exception e) {
			throw new FrameworkException(e);
		}

		return true;

	}

	private void saveRtiFwdInfo(RtiApplicationFormDetailsReqDTO reqDto) {
		List<TbRtiFwdEmployeeEntity> frdDet = new ArrayList<>();
		int i = 0;
		if (!CollectionUtils.isEmpty(reqDto.getEmpList())) {
			for (Long emp : reqDto.getEmpList()) {
				if(emp!=null) {
				TbRtiFwdEmployeeEntity rtfrdDept = new TbRtiFwdEmployeeEntity();
				rtfrdDept.setFwdEmpId(emp);
				if (reqDto.getSliDaysList().get(i) != null)
					rtfrdDept.setSlADays(reqDto.getSliDaysList().get(i).toString());
				rtfrdDept.setEmpFwdRemark(reqDto.getRemList().get(i));
				rtfrdDept.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				rtfrdDept.setLangId(1l);
				rtfrdDept.setRtiId((long) reqDto.getRtiId());
				rtfrdDept.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				rtfrdDept.setlModDate(new Date());
				rtfrdDept.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				rtfrdDept.setApplicationId(reqDto.getApmApplicationId());
				frdDet.add(rtfrdDept);
			}
			i = i + 1;
			}
		}

		else {
			{
				if (reqDto.getEmpname() != null) {
					TbRtiFwdEmployeeEntity rtfrdDept = new TbRtiFwdEmployeeEntity();
					rtfrdDept.setFwdEmpId(Long.valueOf(reqDto.getEmpname()));
					if (reqDto.getSliDays() != null)
						rtfrdDept.setSlADays(reqDto.getSliDays().toString());
					rtfrdDept.setEmpFwdRemark(reqDto.getEmpRmk());
					rtfrdDept.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					rtfrdDept.setLangId(1l);
					rtfrdDept.setRtiId((long) reqDto.getRtiId());
					rtfrdDept.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					rtfrdDept.setlModDate(new Date());
					rtfrdDept.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					rtfrdDept.setApplicationId(reqDto.getApmApplicationId());
					frdDet.add(rtfrdDept);

				}

			}
		}
		if (!CollectionUtils.isEmpty(frdDet)) {
			rtiForwardRepository.save(frdDet);
		}

	}

	private void sendSmsAndEmail(TbLoiMas tbLoiMas, RtiApplicationFormDetailsReqDTO requestDTO) {

		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(requestDTO.getEmail());
		dto.setAppName(String.join(" ", Arrays.asList(getCfcEntity().getApmFname(), getCfcEntity().getApmMname(),
				getCfcEntity().getApmLname())));
		dto.setMobnumber(getCfcAddressEntity().getApaMobilno());
		dto.setAppNo(requestDTO.getApmApplicationId().toString());
		dto.setServName(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICENAME);
		String paymentUrl = MainetConstants.RTISERVICE.RTI_PIO_SMS_EMAIL;
		Organisation org = UserSession.getCurrent().getOrganisation();
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		dto.setOrgName(org.getONlsOrgname());
		int langId = UserSession.getCurrent().getLanguageId();
		dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		if (MainetConstants.AuthStatus.APPROVED.equalsIgnoreCase(
				rtiUtility.getPrefixCode(PrefixConstants.ACTION, Long.valueOf(requestDTO.getRtiAction())))) {

			if (requestDTO.getLoiApplicable() != null
					&& MainetConstants.CommonMasterUi.LOI_APPLICABLE.equalsIgnoreCase(rtiUtility.getPrefixCode(
							PrefixConstants.LOI_APPLICABLE, Long.valueOf(requestDTO.getLoiApplicable())))) {
				dto.setLoiAmt(tbLoiMas.getLoiAmount().toString());
				dto.setLoiNo(tbLoiMas.getLoiNo());

				iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.RTI,
						MainetConstants.SMS_EMAIL_URL.LOI_GENERATION, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL,
						dto, org, langId);

			} else if (requestDTO.getLoiApplicable() != null
					&& MainetConstants.CommonMasterUi.LOI_NOTAPPLICABLE.equalsIgnoreCase(rtiUtility.getPrefixCode(
							PrefixConstants.LOI_APPLICABLE, Long.valueOf(requestDTO.getLoiApplicable())))) {

				iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.RTI, paymentUrl,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, org, langId);

			}
		} else if (MainetConstants.WorkFlow.Decision.REJECTED.equalsIgnoreCase(requestDTO.getRtiAction())) {

			iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.RTI, paymentUrl,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED, dto, org, langId);

		} else if (MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE.equalsIgnoreCase(requestDTO.getRtiAction())) {

			dto.setAppDate(Utility.dateToString(requestDTO.getRtiDeptidFdate()));
			iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.RTI, paymentUrl,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);

		}

	}

	private RequestDTO setApplicantRequestDto(RtiApplicationFormDetailsReqDTO rtiDto) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(rtiDto.getSmServiceId());
		requestDto.setUserId(rtiDto.getUserId());
		requestDto.setOrgId(rtiDto.getOrgId());
		requestDto.setLangId(rtiDto.getLangId());
		// requestDto.setDeptId(rtiDto.getRtiDeptId());
		requestDto.setfName(rtiDto.getfName());
		requestDto.setfName(rtiDto.getlName());
		requestDto.setEmail(rtiDto.getEmail());
		requestDto.setMobileNo(rtiDto.getMobileNo());
		requestDto.setAreaName(rtiDto.getAddress());
		return requestDto;
	}

	public List<DocumentDetailsVO> prepareFileUploadForPio(List<DocumentDetailsVO> doc) throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						// LOGGER.error("Exception has been occurred in file byte to string
						// conversions", e);
					}
				}
			}
		}
		if (doc != null && !doc.isEmpty() && !listOfString.isEmpty()) {
			long count = 0;
			for (final DocumentDetailsVO d : doc) {
				if (d.getDocumentSerialNo() != null) {
					count = d.getDocumentSerialNo() - 1;
				}
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));

				}
				count++;
			}
		}

		return doc;
	}

	public Set<LookUp> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<LookUp> departments) {
		this.departments = departments;
	}

	public List<TbApprejMas> getApprejMasList() {
		return apprejMasList;
	}

	public void setApprejMasList(List<TbApprejMas> apprejMasList) {
		this.apprejMasList = apprejMasList;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public CFCApplicationAddressEntity getCfcAddressEntity() {
		return cfcAddressEntity;
	}

	public void setCfcAddressEntity(CFCApplicationAddressEntity cfcAddressEntity) {
		this.cfcAddressEntity = cfcAddressEntity;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public List<RtiMediaListDTO> getRtiMediaListDTO() {
		return rtiMediaListDTO;
	}

	public void setRtiMediaListDTO(List<RtiMediaListDTO> rtiMediaListDTO) {
		this.rtiMediaListDTO = rtiMediaListDTO;
	}

	public List<CFCAttachment> getFetchApplnUpload() {
		return fetchApplnUpload;
	}

	public void setFetchApplnUpload(List<CFCAttachment> fetchApplnUpload) {
		this.fetchApplnUpload = fetchApplnUpload;
	}

	public List<DocumentDetailsVO> getPioDoc() {
		return pioDoc;
	}

	public void setPioDoc(List<DocumentDetailsVO> pioDoc) {
		this.pioDoc = pioDoc;
	}

	public String getPioName() {
		return pioName;
	}

	public void setPioName(String pioName) {
		this.pioName = pioName;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public long getA3a4Quantity() {
		return a3a4Quantity;
	}

	public long getFlopCopy() {
		return flopCopy;
	}

	public long getInspection() {
		return Inspection;
	}

	public void setA3a4Quantity(long a3a4Quantity) {
		this.a3a4Quantity = a3a4Quantity;
	}

	public void setFlopCopy(long flopCopy) {
		this.flopCopy = flopCopy;
	}

	public void setInspection(long inspection) {
		Inspection = inspection;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public List<TbLoiMas> getLoidata() {
		return loidata;
	}

	public void setLoidata(List<TbLoiMas> loidata) {
		this.loidata = loidata;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Long getLargeCopy() {
		return largeCopy;
	}

	public void setLargeCopy(Long largeCopy) {
		this.largeCopy = largeCopy;
	}

	public String getPioNumber() {
		return pioNumber;
	}

	public void setPioNumber(String pioNumber) {
		this.pioNumber = pioNumber;
	}

	public String getPioEmail() {
		return pioEmail;
	}

	public void setPioEmail(String pioEmail) {
		this.pioEmail = pioEmail;
	}

	public long getPhoto() {
		return photo;
	}

	public void setPhoto(long photo) {
		this.photo = photo;
	}

	public long getInspectioncharges() {
		return inspectioncharges;
	}

	public void setInspectioncharges(long inspectioncharges) {
		this.inspectioncharges = inspectioncharges;
	}

	public Double getPageQuantity1() {
		return pageQuantity1;
	}

	public Double getPageQuantity2() {
		return pageQuantity2;
	}

	public Double getPageQuantity3() {
		return pageQuantity3;
	}

	public Double getPageQuantity4() {
		return pageQuantity4;
	}

	public void setPageQuantity1(Double pageQuantity1) {
		this.pageQuantity1 = pageQuantity1;
	}

	public void setPageQuantity2(Double pageQuantity2) {
		this.pageQuantity2 = pageQuantity2;
	}

	public void setPageQuantity3(Double pageQuantity3) {
		this.pageQuantity3 = pageQuantity3;
	}

	public void setPageQuantity4(Double pageQuantity4) {
		this.pageQuantity4 = pageQuantity4;
	}

	public Double getPage1() {
		return page1;
	}

	public Double getQuantityforA4() {
		return quantityforA4;
	}

	public Double getQuantityforA3() {
		return quantityforA3;
	}

	public void setPage1(Double page1) {
		this.page1 = page1;
	}

	public void setQuantityforA4(Double quantityforA4) {
		this.quantityforA4 = quantityforA4;
	}

	public void setQuantityforA3(Double quantityforA3) {
		this.quantityforA3 = quantityforA3;
	}

	public Double getPageQuantityForPhoto() {
		return pageQuantityForPhoto;
	}

	public Double getPageQuantityForCharges() {
		return pageQuantityForCharges;
	}

	public void setPageQuantityForPhoto(Double pageQuantityForPhoto) {
		this.pageQuantityForPhoto = pageQuantityForPhoto;
	}

	public void setPageQuantityForCharges(Double pageQuantityForCharges) {
		this.pageQuantityForCharges = pageQuantityForCharges;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public RtiLoiReportDTO getRtiLoiReportDTO() {
		return rtiLoiReportDTO;
	}

	public void setRtiLoiReportDTO(RtiLoiReportDTO rtiLoiReportDTO) {
		this.rtiLoiReportDTO = rtiLoiReportDTO;
	}

	public Long getLoiId() {
		return loiId;
	}

	public void setLoiId(Long loiId) {
		this.loiId = loiId;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Double getTotalBeforeSave() {
		return totalBeforeSave;
	}

	public void setTotalBeforeSave(Double totalBeforeSave) {
		this.totalBeforeSave = totalBeforeSave;
	}

	public String getEditableLOIflag() {
		return editableLOIflag;
	}

	public void setEditableLOIflag(String editableLOIflag) {
		this.editableLOIflag = editableLOIflag;
	}

	public String getWorkflowFlag() {
		return workflowFlag;
	}

	public void setWorkflowFlag(String workflowFlag) {
		this.workflowFlag = workflowFlag;
	}

	public String getEditableworkflowflag() {
		return editableworkflowflag;
	}

	public void setEditableworkflowflag(String editableworkflowflag) {
		this.editableworkflowflag = editableworkflowflag;
	}

	public Double getTotalAfterSave() {
		return totalAfterSave;
	}

	public void setTotalAfterSave(Double totalAfterSave) {
		this.totalAfterSave = totalAfterSave;
	}

	public BigDecimal getLoiMasterappId() {
		return loiMasterappId;
	}

	public void setLoiMasterappId(BigDecimal loiMasterappId) {
		this.loiMasterappId = loiMasterappId;
	}

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	// Us#116366
	public void sendSmsAndEmailToApplicant(RtiApplicationFormDetailsReqDTO reqDto) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(reqDto.getEmail());
		dto.setAppName(String.join(" ", Arrays.asList(getCfcEntity().getApmFname(), getCfcEntity().getApmMname(),
				getCfcEntity().getApmLname())));
		dto.setMobnumber(getCfcAddressEntity().getApaMobilno());
		dto.setAppNo(reqDto.getApmApplicationId().toString());
		dto.setEmail(reqDto.getEmail());
		dto.setServName(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICENAME);
		String menuUrl = MainetConstants.RTISERVICE.RTI_PIO_SMS_EMAIL;
		Organisation org = UserSession.getCurrent().getOrganisation();
		org.setOrgid(reqDto.getOrgId());
		int langId = UserSession.getCurrent().getLanguageId();
		dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.RTI, menuUrl,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, dto, org, langId);

	}

	public List<Organisation> getListOrg() {
		return listOrg;
	}

	public void setListOrg(List<Organisation> listOrg) {
		this.listOrg = listOrg;
	}

	public List<CFCAttachment> getFetchPioUploadDoc() {
		return fetchPioUploadDoc;
	}

	public void setFetchPioUploadDoc(List<CFCAttachment> fetchPioUploadDoc) {
		this.fetchPioUploadDoc = fetchPioUploadDoc;
	}

	public Set<LookUp> getFdlDepartments() {
		return fdlDepartments;
	}

	public void setFdlDepartments(Set<LookUp> fdlDepartments) {
		this.fdlDepartments = fdlDepartments;
	}

	public WorkflowTaskAction getCareDepartmentAction() {
		return careDepartmentAction;
	}

	public void setCareDepartmentAction(WorkflowTaskAction careDepartmentAction) {
		this.careDepartmentAction = careDepartmentAction;
	}

	public String getPrefixName() {
		return PrefixName;
	}

	public void setPrefixName(String prefixName) {
		PrefixName = prefixName;
	}

	public List<TbComparentDet> getZoneList() {
		return zoneList;
	}

	public void setZoneList(List<TbComparentDet> zoneList) {
		this.zoneList = zoneList;
	}

	public List<CFCAttachment> getFetchStampDoc() {
		return fetchStampDoc;
	}

	public void setFetchStampDoc(List<CFCAttachment> fetchStampDoc) {
		this.fetchStampDoc = fetchStampDoc;
	}

	public List<TbComparentDet> getWardList() {
		return wardList;
	}

	public void setWardList(List<TbComparentDet> wardList) {
		this.wardList = wardList;
	}

	public long getPageA3() {
		return pageA3;
	}

	public void setPageA3(long pageA3) {
		this.pageA3 = pageA3;
	}

	public double getPageQuantityA3() {
		return pageQuantityA3;
	}

	public void setPageQuantityA3(double pageQuantityA3) {
		this.pageQuantityA3 = pageQuantityA3;
	}

	public List<CFCAttachment> getFetchPostalDoc() {
		return fetchPostalDoc;
	}

	public void setFetchPostalDoc(List<CFCAttachment> fetchPostalDoc) {
		this.fetchPostalDoc = fetchPostalDoc;
	}

	public List<Long> getSliDaysList() {
		return sliDaysList;
	}

	public void setSliDaysList(List<Long> sliDaysList) {
		this.sliDaysList = sliDaysList;
	}

	public IWorkflowExecutionService getWorkflowExecutionService() {
		return workflowExecutionService;
	}

	public void setWorkflowExecutionService(IWorkflowExecutionService workflowExecutionService) {
		this.workflowExecutionService = workflowExecutionService;
	}

	public List<Object[]> getRtiEmployee() {
		return rtiEmployee;
	}

	public void setRtiEmployee(List<Object[]> rtiEmployee) {
		this.rtiEmployee = rtiEmployee;
	}

	public List<RtiMediaListDTO> getLoidet() {
		return loidet;
	}

	public void setLoidet(List<RtiMediaListDTO> loidet) {
		this.loidet = loidet;
	}
}
