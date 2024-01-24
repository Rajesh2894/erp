package com.abm.mainet.tradeLicense.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetail;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlRenewalMast;
import com.abm.mainet.tradeLicense.domain.TbMlRenewalMastHist;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;
import com.abm.mainet.tradeLicense.dto.RenewalHistroyDetails;
import com.abm.mainet.tradeLicense.dto.RenewalMasterDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.RenewalLicenseApplicationRepository;
import com.abm.mainet.tradeLicense.repository.RenewalMasterHistoryRepository;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class RenewalLicenseApprovalReprintingModel extends AbstractFormModel {

	private static final long serialVersionUID = -6937979940278247217L;

	private ServiceMaster serviceMaster = new ServiceMaster();
	private List<TbLoiMas> tbLoiMas = new ArrayList<>();
	private List<TradeMasterDetailDTO> tradeMasterDetailDTO = new ArrayList<>();
	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
	private String viewMode;
	private String saveMode;
	private Long rmRcptno;
	private BigDecimal rmAmount;
	private String dateDesc;
	private String loiDateDesc;
	private String issuanceDateDesc;
	private String licenseFromDateDesc;
	private Long taskId;
	private String filePath;
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String imagePath;
	private String categoryDesc;
	private String payMode;
	private List<TbTaxMasEntity> taxesMaster = new ArrayList<>();

	private Map<String, Double> chargeDescAndAmount = new LinkedHashMap<>();

	private List<TbApprejMas> apprejMasList = new ArrayList<>();
	private String trdWard1Desc;
	private String trdWard2Desc;
	private String trdWard3Desc;
	private String trdWard4Desc;
	private String trdWard5Desc;
	private String ward1Level;
	private String ward2Level;
	private String ward3Level;
	private String ward4Level;
	private String ward5Level;
	private List<RenewalHistroyDetails> renHistList;
	private Department department;
	private String skdclEnvFlag;
	private String trdBusDate;
	private String tsclEnvFlag;

	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;
	@Autowired
	IWorkflowExecutionService iWorkflowExecutionService;
	@Autowired
	TradeLicenseApplicationRepository tradeLicenseApplicationRepository;
	@Autowired
	IRenewalLicenseApplicationService iRenewalLicenseApplicationService;
	@Autowired
	private RenewalLicenseApplicationRepository renewalLicenseApplicationRepository;

	@Autowired
	private RenewalMasterHistoryRepository renHistortRepo;
	@Autowired
	private AuditService auditService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Override
	public boolean saveForm() {

		TradeMasterDetailDTO masDto = getTradeDetailDTO();

		String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();

		iTradeLicenseApplicationService.updateTradeLicenseStatus(masDto,
				UserSession.getCurrent().getOrganisation().getOrgid(), masDto.getTrdStatus(), lgIpMacUpd);

		masDto = iTradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(masDto.getApmApplicationId());

		Long updatedBy = UserSession.getCurrent().getEmployee().getEmpId();

		Date newDate = new Date();
		masDto.setUpdatedBy(updatedBy);
		masDto.setUpdatedDate(newDate);
		masDto.setLgIpMacUpd(lgIpMacUpd);
		masDto.setTrdLicfromDate(newDate);

		RenewalMasterDetailDTO renewalLicenseDetailsDTO = iRenewalLicenseApplicationService
				.getRenewalLicenseDetailsByApplicationId(masDto.getApmApplicationId());
       //User Story #133712
		boolean isSkdcl = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
				MainetConstants.ENV_SKDCL);

		LookUp lookUpObject = CommonMasterUtility.getNonHierarchicalLookUpObject(
				Long.valueOf(renewalLicenseDetailsDTO.getRenewalPeriod()), UserSession.getCurrent().getOrganisation());

		long renewalPeriod = Long.valueOf(lookUpObject.getLookUpCode());
		//User Story #133712
        double renPendCycle = 0d;
		if (isSkdcl) {

			double renPendDays = Utility.getDaysBetDates(masDto.getTrdLictoDate(), new Date());
			renPendCycle = Long.valueOf(Math.round(renPendDays / MainetConstants.DAS_IN_YEAR));
			if (renPendCycle != masDto.getRenewCycle()) {
				renewalPeriod = masDto.getRenewCycle();
			}
		}
		Long calculateLicMaxTenureDays = null;
		TbDepartment department = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA, "ML");
			calculateLicMaxTenureDays = iRenewalLicenseApplicationService.calculateLicMaxTenureDays(
					department.getDpDeptid(), serviceMaster.getSmServiceId(), masDto.getTrdLictoDate(),
					UserSession.getCurrent().getOrganisation().getOrgid(), masDto, renewalPeriod);
	
		if (calculateLicMaxTenureDays == null || calculateLicMaxTenureDays == 0l) {

			throw new FrameworkException("No range found in License Validity Master");
		}

		Date renwalToDate = Utility.getAddedDateBy2(masDto.getTrdLictoDate(), calculateLicMaxTenureDays.intValue());

		TbMlRenewalMast tbMlRenewalMast = new TbMlRenewalMast();
		BeanUtils.copyProperties(renewalLicenseDetailsDTO, tbMlRenewalMast);
		tbMlRenewalMast.setUpdatedBy(updatedBy);
		tbMlRenewalMast.setUpdatedDate(newDate);
		tbMlRenewalMast.setLgIpMacUpd(lgIpMacUpd);
		tbMlRenewalMast.setTreLicfromDate(Utility.getAddedDateBy2(masDto.getTrdLictoDate(),1));
        //User Story #133712
		/*
		 * if (isSkdcl) { if (renPendCycle != masDto.getRenewCycle()) { renwalToDate =
		 * Utility.getAddedDateBy2(masDto.getTrdLictoDate(),
		 * calculateLicMaxTenureDays.intValue()); }
		 * tbMlRenewalMast.setTreLicfromDate(masDto.getTrdLictoDate());
		 * masDto.setTrdLicfromDate(masDto.getTrdLictoDate()); }
		 */
		masDto.setTrdLicfromDate(Utility.getAddedDateBy2(masDto.getTrdLictoDate(),1));
		masDto.setTrdLictoDate(renwalToDate);
		tbMlRenewalMast.setTreLictoDate(renwalToDate);

		TbMlTradeMast masEntity = mapDtoToEntity(masDto);

		tradeLicenseApplicationRepository.save(masEntity);
		renewalLicenseApplicationRepository.save(tbMlRenewalMast);
		saveRenewalHistoryData(tbMlRenewalMast);
		saveHistoryData(masEntity);

		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setApplicationId(masDto.getApmApplicationId());
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		taskAction.setTaskId(taskId);

		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		// 127361 -> set bpm process as maker-checker for skdcl project
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
			taskAction.setPaymentMode(MainetConstants.FlagY);
			taskAction.setIsFinalApproval(true);
		} else {
			workflowProcessParameter.setProcessName("scrutiny");
		}
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}

		this.setSuccessMessage(masDto.getTrdLicno() + " " + getAppSession().getMessage("trade.ren.successLicenseMsg"));
		sendSmsEmail(masDto);
		return true;
	}

	// 126164
	private void sendSmsEmail(TradeMasterDetailDTO masDto) {
		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		smsDto.setAppNo(masDto.getApmApplicationId().toString());
		smsDto.setAppName(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		setServiceMaster(sm);
		smsDto.setServName(sm.getSmServiceName());
		smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		String url = "RenewalLicenseApprovalReprinting.html";
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		int langId = UserSession.getCurrent().getLanguageId();
		smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, smsDto, org, langId);
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<TbLoiMas> getTbLoiMas() {
		return tbLoiMas;
	}

	public void setTbLoiMas(List<TbLoiMas> tbLoiMas) {
		this.tbLoiMas = tbLoiMas;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public Long getRmRcptno() {
		return rmRcptno;
	}

	public void setRmRcptno(Long rmRcptno) {
		this.rmRcptno = rmRcptno;
	}

	public BigDecimal getRmAmount() {
		return rmAmount;
	}

	public void setRmAmount(BigDecimal rmAmount) {
		this.rmAmount = rmAmount;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<TradeMasterDetailDTO> getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(List<TradeMasterDetailDTO> tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
	}

	public String getLoiDateDesc() {
		return loiDateDesc;
	}

	public void setLoiDateDesc(String loiDateDesc) {
		this.loiDateDesc = loiDateDesc;
	}

	public String getIssuanceDateDesc() {
		return issuanceDateDesc;
	}

	public void setIssuanceDateDesc(String issuanceDateDesc) {
		this.issuanceDateDesc = issuanceDateDesc;
	}

	public String getLicenseFromDateDesc() {
		return licenseFromDateDesc;
	}

	public void setLicenseFromDateDesc(String licenseFromDateDesc) {
		this.licenseFromDateDesc = licenseFromDateDesc;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public List<TbTaxMasEntity> getTaxesMaster() {
		return taxesMaster;
	}

	public void setTaxesMaster(List<TbTaxMasEntity> taxesMaster) {
		this.taxesMaster = taxesMaster;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public Map<String, Double> getChargeDescAndAmount() {
		return chargeDescAndAmount;
	}

	public void setChargeDescAndAmount(Map<String, Double> chargeDescAndAmount) {
		this.chargeDescAndAmount = chargeDescAndAmount;
	}

	public List<TbApprejMas> getApprejMasList() {
		return apprejMasList;
	}

	public void setApprejMasList(List<TbApprejMas> apprejMasList) {
		this.apprejMasList = apprejMasList;
	}

	public String getTrdWard1Desc() {
		return trdWard1Desc;
	}

	public String getTrdWard2Desc() {
		return trdWard2Desc;
	}

	public String getTrdWard3Desc() {
		return trdWard3Desc;
	}

	public String getTrdWard4Desc() {
		return trdWard4Desc;
	}

	public String getTrdWard5Desc() {
		return trdWard5Desc;
	}

	public void setTrdWard1Desc(String trdWard1Desc) {
		this.trdWard1Desc = trdWard1Desc;
	}

	public void setTrdWard2Desc(String trdWard2Desc) {
		this.trdWard2Desc = trdWard2Desc;
	}

	public void setTrdWard3Desc(String trdWard3Desc) {
		this.trdWard3Desc = trdWard3Desc;
	}

	public void setTrdWard4Desc(String trdWard4Desc) {
		this.trdWard4Desc = trdWard4Desc;
	}

	public void setTrdWard5Desc(String trdWard5Desc) {
		this.trdWard5Desc = trdWard5Desc;
	}

	public String getWard1Level() {
		return ward1Level;
	}

	public void setWard1Level(String ward1Level) {
		this.ward1Level = ward1Level;
	}

	public String getWard2Level() {
		return ward2Level;
	}

	public String getWard3Level() {
		return ward3Level;
	}

	public String getWard4Level() {
		return ward4Level;
	}

	public String getWard5Level() {
		return ward5Level;
	}

	public void setWard2Level(String ward2Level) {
		this.ward2Level = ward2Level;
	}

	public void setWard3Level(String ward3Level) {
		this.ward3Level = ward3Level;
	}

	public void setWard4Level(String ward4Level) {
		this.ward4Level = ward4Level;
	}

	public void setWard5Level(String ward5Level) {
		this.ward5Level = ward5Level;
	}

	public String getSkdclEnvFlag() {
		return skdclEnvFlag;
	}

	public void setSkdclEnvFlag(String skdclEnvFlag) {
		this.skdclEnvFlag = skdclEnvFlag;
	}

	public List<RenewalHistroyDetails> getRenHistList() {
		return renHistList;
	}

	public void setRenHistList(List<RenewalHistroyDetails> renHistList) {
		this.renHistList = renHistList;
	}
	
	

	public String getTrdBusDate() {
		return trdBusDate;
	}

	public void setTrdBusDate(String trdBusDate) {
		this.trdBusDate = trdBusDate;
	}
	
	

	public String getTsclEnvFlag() {
		return tsclEnvFlag;
	}

	public void setTsclEnvFlag(String tsclEnvFlag) {
		this.tsclEnvFlag = tsclEnvFlag;
	}
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	private TbMlTradeMast mapDtoToEntity(TradeMasterDetailDTO tradeMasterDto) {
		TbMlTradeMast masEntity = new TbMlTradeMast();
		List<TbMlItemDetail> itemdDetailsList = new ArrayList<>();
		List<TbMlOwnerDetail> ownerDetailsList = new ArrayList<>();

		BeanUtils.copyProperties(tradeMasterDto, masEntity);
		tradeMasterDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
			TbMlItemDetail itemEntity = new TbMlItemDetail();
			BeanUtils.copyProperties(itemdDetails, itemEntity);
			itemEntity.setMasterTradeId(masEntity);
			itemdDetailsList.add(itemEntity);
		});
		tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(ownerDetails -> {
			TbMlOwnerDetail ownerEntity = new TbMlOwnerDetail();
			BeanUtils.copyProperties(ownerDetails, ownerEntity);
			ownerEntity.setMasterTradeId(masEntity);
			ownerDetailsList.add(ownerEntity);
		});

		masEntity.setOwnerDetails(ownerDetailsList);
		masEntity.setItemDetails(itemdDetailsList);
		masEntity.setTrdLicno(tradeMasterDto.getTrdLicno());
		return masEntity;
	}

	private void saveRenewalHistoryData(TbMlRenewalMast masEntity) {
		// save history
		TbMlRenewalMastHist renewalMastHist = new TbMlRenewalMastHist();

		BeanUtils.copyProperties(masEntity, renewalMastHist);
		renewalMastHist.setHistoryStatus(MainetConstants.FlagU);
		renHistortRepo.save(renewalMastHist);

	}



	private void saveHistoryData(TbMlTradeMast masEntity) {
		// save history
		TbMlTradeMastHist TbMlTradeMastHist = new TbMlTradeMastHist();

		BeanUtils.copyProperties(masEntity, TbMlTradeMastHist);

		List<TbMlOwnerDetailHist> tbMlOwnerDetailHistList = new ArrayList<>();
		List<TbMlItemDetailHist> tbMlItemDetailHistList = new ArrayList<>();
		masEntity.getOwnerDetails().forEach(ownerEntity -> {

			TbMlOwnerDetailHist tbMlOwnerDetailHistEntity = new TbMlOwnerDetailHist();

			BeanUtils.copyProperties(ownerEntity, tbMlOwnerDetailHistEntity);

			tbMlOwnerDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlOwnerDetailHistList.add(tbMlOwnerDetailHistEntity);

		});
		masEntity.getItemDetails().forEach(itemEntity -> {

			TbMlItemDetailHist TbMlItemDetailHistEntity = new TbMlItemDetailHist();

			BeanUtils.copyProperties(itemEntity, TbMlItemDetailHistEntity);

			TbMlItemDetailHistEntity.setMasterTradeId(TbMlTradeMastHist);
			tbMlItemDetailHistList.add(TbMlItemDetailHistEntity);

		});

		TbMlTradeMastHist.setItemDetails(tbMlItemDetailHistList);
		TbMlTradeMastHist.setOwnerDetails(tbMlOwnerDetailHistList);
		auditService.createHistoryForObject(TbMlTradeMastHist);
	}

}
