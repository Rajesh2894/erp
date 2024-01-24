package com.abm.mainet.socialsecurity.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.SocialSecurity;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.socialsecurity.service.IBeneficiaryPaymentOrderService;
import com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.BeneficiaryPaymentOrderDto;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BeneficiaryPaymentOrderModel extends AbstractFormModel {

	/**
	 * 
	 */
	@Autowired
	private TbDepartmentService tbDepartmentService;
	@Autowired
	private IBeneficiaryPaymentOrderService beneficiaryPaymentOrderService;
	@Autowired
	private ServiceMasterService serviceMasterService;
	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private ISchemeApplicationFormService schemeApplicationFormService;

	private static final long serialVersionUID = -3145640513295276675L;
	private List<Object[]> serviceList = new ArrayList<>();
	private List<LookUp> paymentList = new ArrayList<>();
	private List<BeneficiaryPaymentOrderDto> dto = new ArrayList<>();
	private BeneficiaryPaymentOrderDto bpoDto = new BeneficiaryPaymentOrderDto();
	private ApplicationFormDto applicationformdto = new ApplicationFormDto();
	private String envflag;
	private List<LookUp> subTypeList = new ArrayList<>();

	private List<CFCAttachment> cfcAttachment;

	public List<Object[]> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Object[]> serviceList) {
		this.serviceList = serviceList;
	}

	public List<LookUp> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<LookUp> paymentList) {
		this.paymentList = paymentList;
	}

	public BeneficiaryPaymentOrderDto getBpoDto() {
		return bpoDto;
	}

	public void setBpoDto(BeneficiaryPaymentOrderDto bpoDto) {
		this.bpoDto = bpoDto;
	}

	public List<BeneficiaryPaymentOrderDto> getDto() {
		return dto;
	}

	public void setDto(List<BeneficiaryPaymentOrderDto> dto) {
		this.dto = dto;
	}

	/**
	 * @return the fileUpload
	 */
	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * @return the cfcAttachment
	 */
	public List<CFCAttachment> getCfcAttachment() {
		return cfcAttachment;
	}

	/**
	 * @param cfcAttachment the cfcAttachment to set
	 */
	public void setCfcAttachment(List<CFCAttachment> cfcAttachment) {
		this.cfcAttachment = cfcAttachment;
	}


	public String getEnvflag() {
		return envflag;
	}

	public void setEnvflag(String envflag) {
		this.envflag = envflag;
	}

	public ApplicationFormDto getApplicationformdto() {
		return applicationformdto;
	}

	public void setApplicationformdto(ApplicationFormDto applicationformdto) {
		this.applicationformdto = applicationformdto;
	}

	public BeneficiaryPaymentOrderDto saveBeneficiaryDetails(Long orgId, int langId, String[] list, String remark,
			String ipMacAddress) {
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Long locId = UserSession.getCurrent().getLoggedLocId();
		Organisation org = UserSession.getCurrent().getOrganisation();
		boolean workflowflag = beneficiaryPaymentOrderService.checkWorkflowIsNotDefine(org, orgId,
				MainetConstants.SocialSecurity.BENEFICIARY_SERVICE_CODE);
		Stream<String> streamList = Arrays.stream(list);
		final List<BeneficiaryPaymentOrderDto> dtoList = new ArrayList<>();
		if (list != null) {
			streamList.forEachOrdered(k -> {
				dtoList.addAll(getDto().stream().filter(l -> l.getBeneficiaryNumber().equals(k)).map(f -> {
					f.setOrgId(orgId);
					f.setEmpId(empId);
					f.setIpAddress(ipMacAddress);
					f.setRemark(remark);
					if (workflowflag) {
						f.setRtgsStatus("A");
					} else {
						f.setRtgsStatus("P");
					}
					f.setLangId((long) langId);
					f.setLocId(locId);
					return f;
				}).collect(Collectors.toList()));
			});
		}
		if (dtoList != null && !dtoList.isEmpty()) {
			beneficiaryPaymentOrderService.saveBeneficiaryDetails(dtoList, org);
			bpoDto.setDate(new Date());
			bpoDto.setDepartmentName(tbDepartmentService
					.getDepartmentNameByDeptCode(SocialSecurity.DEPARTMENT_SORT_CODE, (long) langId));
			bpoDto.setDtoList(dtoList);
			bpoDto.setOrgId(orgId);
			bpoDto.setEmpId(empId);
			bpoDto.setIpAddress(ipMacAddress);
			bpoDto.setWorkOrderNumber(dtoList.get(0).getWorkOrderNumber());
			if (!workflowflag) {
				beneficiaryPaymentOrderService.initiateWorkFlowForFreeService(getBpoDto());
			}
		}
		return bpoDto;

	}

	public void getAndSetCommonValues() {
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		setServiceList(serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId, depId, activeStatusId, "N"));
		setPaymentList(CommonMasterUtility.getLookUps(MainetConstants.SocialSecurity.PAYMENT_PREFIX, org));
		List<LookUp> parentPxList = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId);
		
		parentPxList.stream().forEach(s -> {
			if (s.getLookUpCode().equalsIgnoreCase("SS")) {
				setSubTypeList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
						parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
								.collect(Collectors.toList()).get(0).getLookUpId(),
						2L));
			}
		});
		 

	}

	public boolean callWorkFlow() {
		{
			Organisation org = UserSession.getCurrent().getOrganisation();
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
			Employee emp = UserSession.getCurrent().getEmployee();
			workFlowActionDto.setOrgId(orgId);
			workFlowActionDto.setEmpId(emp.getEmpId());
			workFlowActionDto.setDecision(getWorkflowActionDto().getDecision());

			List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
					.getBean(IChecklistVerificationService.class)
					.fetchAllAttachIdByReferenceId(getWorkflowActionDto().getReferenceId(), orgId);

			prepareWorkFlowTaskAction(getWorkflowActionDto());

			workFlowActionDto.setAttachementId(attacheMentIds);

			// this code is for document upload start
			ServiceMaster sm = serviceMasterService
					.getServiceMasterByShortCode(MainetConstants.SocialSecurity.BENEFICIARY_SERVICE_CODE, orgId);
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
			requestDTO.setOrgId(orgId);
			requestDTO.setDepartmentName(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
			requestDTO.setServiceId(sm.getSmServiceId());
			requestDTO.setUserId(emp.getEmpId());
			if (getWorkflowActionDto().getApplicationId() != null) {
				requestDTO.setApplicationId(getWorkflowActionDto().getApplicationId());
			}
			if (getWorkflowActionDto().getApplicationId() == null) {
				requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
			}
			requestDTO.setDeptId(sm.getTbDepartment().getDpDeptid());

			List<DocumentDetailsVO> docs = new ArrayList<>();
			DocumentDetailsVO document = new DocumentDetailsVO();
			document.setDocumentSerialNo(1L);
			docs.add(document);
			docs = fileUpload.prepareFileUpload(docs);
			getBpoDto().setDocumentList(docs);

			return beneficiaryPaymentOrderService.saveDecision(getBpoDto(), orgId, emp, workFlowActionDto, requestDTO,
					org);
		}
	}

	public String accountBillPostingForthesocialSecurity() {
		try {
			if (bpoDto.getDtoList() != null && !bpoDto.getDtoList().isEmpty()) {
				Organisation org = UserSession.getCurrent().getOrganisation();
				Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
				int langId = UserSession.getCurrent().getLanguageId();
				Long locId = UserSession.getCurrent().getLoggedLocId();
				LookUp defaultVal = CommonMasterUtility.getDefaultValue(MainetConstants.SocialSecurity.RBM);
				if (defaultVal != null) {
					if (defaultVal.getLookUpCode().trim().equalsIgnoreCase("INV")) {
						bpoDto.getDtoList().stream().parallel().forEachOrdered(s -> {
							s.setOrgId(orgId);
							s.setLangId((long) langId);
							s.setLocId(locId);
							// added later for accpostbatchid when status was going null
							s.setAccPostBatchInd(defaultVal.getLookUpCode());

							beneficiaryPaymentOrderService.accountBillEntryforSocialSecurity(s, org);

						});
						return bpoDto.getDtoList().get(0).getBeneficiaryNumber();

					} else {
						BigDecimal totalamt = bpoDto.getDtoList().stream().filter(r -> r != null)
								.map(r -> r.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
						bpoDto.getDtoList().get(0).setOrgId(orgId);
						bpoDto.getDtoList().get(0).setLangId((long) langId);
						bpoDto.getDtoList().get(0).setLocId(locId);
						bpoDto.getDtoList().get(0).setAmount(totalamt);
						bpoDto.getDtoList().get(0).setAccPostBatchInd(defaultVal.getLookUpCode());
						beneficiaryPaymentOrderService.accountBillEntryforSocialSecurity(bpoDto.getDtoList().get(0),
								org);
						return bpoDto.getDtoList().get(0).getBeneficiaryNumber();

					}
				}

			}
		} catch (Exception e) {
			throw (e);
		}
		return null;
	}

	private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {

		getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());

		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		return workflowActionDto;

	}

	public List<LookUp> getSubTypeList() {
		return subTypeList;
	}

	public void setSubTypeList(List<LookUp> subTypeList) {
		this.subTypeList = subTypeList;
	}
	

}