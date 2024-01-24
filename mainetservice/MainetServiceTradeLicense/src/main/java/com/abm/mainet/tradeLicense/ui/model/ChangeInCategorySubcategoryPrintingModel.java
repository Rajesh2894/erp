package com.abm.mainet.tradeLicense.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetail;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class ChangeInCategorySubcategoryPrintingModel extends AbstractFormModel {
	
	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;
	
	@Autowired
	IWorkflowExecutionService iWorkflowExecutionService;
	
	@Autowired
	TradeLicenseApplicationRepository tradeLicenseApplicationRepository;
	
	@Autowired
	AuditService auditService;

	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;
	
    private static final long serialVersionUID = 1L;
    
    private TradeMasterDetailDTO tradeDto = new TradeMasterDetailDTO();
    private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
    private String issuanceDateDesc;
    private String fromDateDesc;
    private String toDateDesc;
    private String viewMode;
    private String ward1Level;
	private String ward2Level;
	private String ward3Level;
	private String ward4Level;
	private String ward5Level;
    private String trdWard1Desc;
	private String trdWard2Desc;
	private String trdWard3Desc;
	private String trdWard4Desc;
	private String trdWard5Desc;
	private String dateDesc;
	private String licenseFromDateDesc;
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String imagePath;
	private List<TbApprejMas> apprejMasList = new ArrayList<>();
	private Department department;
	private String categoryDesc;
	private ServiceMaster serviceMaster = new ServiceMaster();
	private Long taskId;
	private List<TbLoiMas> tbLoiMas = new ArrayList<>();
	private String loiDateDesc;
	private Long rmRcptno;
	private BigDecimal rmAmount;

    public TradeMasterDetailDTO getTradeDto() {
        return tradeDto;
    }

    public TradeMasterDetailDTO getTradeDetailDTO() {
        return tradeDetailDTO;
    }

    public String getIssuanceDateDesc() {
        return issuanceDateDesc;
    }

    public String getFromDateDesc() {
        return fromDateDesc;
    }

    public String getToDateDesc() {
        return toDateDesc;
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setTradeDto(TradeMasterDetailDTO tradeDto) {
        this.tradeDto = tradeDto;
    }

    public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
        this.tradeDetailDTO = tradeDetailDTO;
    }

    public void setIssuanceDateDesc(String issuanceDateDesc) {
        this.issuanceDateDesc = issuanceDateDesc;
    }

    public void setFromDateDesc(String fromDateDesc) {
        this.fromDateDesc = fromDateDesc;
    }

    public void setToDateDesc(String toDateDesc) {
        this.toDateDesc = toDateDesc;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
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

	public void setWard2Level(String ward2Level) {
		this.ward2Level = ward2Level;
	}

	public String getWard3Level() {
		return ward3Level;
	}

	public void setWard3Level(String ward3Level) {
		this.ward3Level = ward3Level;
	}

	public String getWard4Level() {
		return ward4Level;
	}

	public void setWard4Level(String ward4Level) {
		this.ward4Level = ward4Level;
	}

	public String getWard5Level() {
		return ward5Level;
	}

	public void setWard5Level(String ward5Level) {
		this.ward5Level = ward5Level;
	}

	public String getTrdWard1Desc() {
		return trdWard1Desc;
	}

	public void setTrdWard1Desc(String trdWard1Desc) {
		this.trdWard1Desc = trdWard1Desc;
	}

	public String getTrdWard2Desc() {
		return trdWard2Desc;
	}

	public void setTrdWard2Desc(String trdWard2Desc) {
		this.trdWard2Desc = trdWard2Desc;
	}

	public String getTrdWard3Desc() {
		return trdWard3Desc;
	}

	public void setTrdWard3Desc(String trdWard3Desc) {
		this.trdWard3Desc = trdWard3Desc;
	}

	public String getTrdWard4Desc() {
		return trdWard4Desc;
	}

	public void setTrdWard4Desc(String trdWard4Desc) {
		this.trdWard4Desc = trdWard4Desc;
	}

	public String getTrdWard5Desc() {
		return trdWard5Desc;
	}

	public void setTrdWard5Desc(String trdWard5Desc) {
		this.trdWard5Desc = trdWard5Desc;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public String getLicenseFromDateDesc() {
		return licenseFromDateDesc;
	}

	public void setLicenseFromDateDesc(String licenseFromDateDesc) {
		this.licenseFromDateDesc = licenseFromDateDesc;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<TbApprejMas> getApprejMasList() {
		return apprejMasList;
	}

	public void setApprejMasList(List<TbApprejMas> apprejMasList) {
		this.apprejMasList = apprejMasList;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	
	public List<TbLoiMas> getTbLoiMas() {
		return tbLoiMas;
	}

	public void setTbLoiMas(List<TbLoiMas> tbLoiMas) {
		this.tbLoiMas = tbLoiMas;
	}

	public String getLoiDateDesc() {
		return loiDateDesc;
	}

	public void setLoiDateDesc(String loiDateDesc) {
		this.loiDateDesc = loiDateDesc;
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

	@Override
	public boolean saveForm() {

		TradeMasterDetailDTO masDto = getTradeDetailDTO();

		String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();

		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();

		Date newDate = new Date();
		Long orgid= UserSession.getCurrent().getOrganisation().getOrgid();

		iTradeLicenseApplicationService.updateTradeLicenseStatus(masDto,
				UserSession.getCurrent().getOrganisation().getOrgid(), masDto.getTrdStatus(), lgIpMacUpd);

		masDto = iTradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(masDto.getApmApplicationId());

		masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {

			if (MainetConstants.FlagY.equalsIgnoreCase(itemDto.getTriStatus())
					|| MainetConstants.FlagM.equalsIgnoreCase(itemDto.getTriStatus())) {
				itemDto.setTriStatus(MainetConstants.FlagA);
			}
			if (itemDto.getCreatedBy() == null) {
				itemDto.setCreatedBy(createdBy);
				itemDto.setCreatedDate(newDate);
				itemDto.setLgIpMac(lgIp);

			} else {
				itemDto.setUpdatedBy(createdBy);
				itemDto.setUpdatedDate(newDate);
				itemDto.setLgIpMacUpd(lgIp);

			}

		});

		TbMlTradeMast masEntity = mapDtoToEntity(masDto);
		tradeLicenseApplicationRepository.save(masEntity);
		//save history data for defect#115171
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
		workflowProcessParameter.setProcessName("scrutiny");
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
		iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}

		this.setSuccessMessage(getAppSession().getMessage("trade.successLicenseMsg") + masDto.getTrdLicno());
		sendSmsEmail(masDto);
		if(masDto.getPmPropNo() != null ) {
			TradeMasterDetailDTO trdDto = iTradeLicenseApplicationService.getPropertyDetailsByPropertyNumber(masDto);
			trdDto.setPmPropNo(masDto.getPmPropNo());
		    LookUp licCat = CommonMasterUtility.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), orgid);
		    LookUp licSubCat = CommonMasterUtility.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod2(),orgid);
		    trdDto.setLiceCategory(licCat.getLookUpDesc());
		    trdDto.setLiceSubCategory(licSubCat.getLookUpDesc());
		    trdDto.setTrdBusnm(masDto.getTrdBusnm());
		    trdDto.setTrdOwnerNm(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
			if(trdDto != null && trdDto.getpTaxCollEmpId() != null)
			this.sendSmsEmail(trdDto, "ChangeInCategorySubcategoryPrinting.html",PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG);
		}
		return true;
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
    
	
	//Us#97147
		private void sendSmsEmail(TradeMasterDetailDTO trdDto, String menuUrl, String msgType) {
			Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();		
			ServiceMaster sm = serviceMasterService.getServiceByShortName(MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE,orgId);
			SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			smsDto.setOrgId(orgId);
			smsDto.setLangId(UserSession.getCurrent().getLanguageId());
			smsDto.setServName(sm.getSmServiceName());
			smsDto.setAppNo(String.valueOf(trdDto.getTrdLicno()));
			smsDto.setPropertyNo(trdDto.getPmPropNo());
			smsDto.setAppName(trdDto.getpTaxCollEmpName());
			smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			smsDto.setMobnumber(trdDto.getpTaxCollEmpMobNo());
			smsDto.setEmail(trdDto.getpTaxCollEmpEmailId());
			smsDto.setDate(new Date());
			smsDto.setType(trdDto.getLiceCategory());
			smsDto.setSubtype(trdDto.getLiceSubCategory());
			smsDto.setBusinessName(trdDto.getTrdBusnm());
			smsDto.setOwnerName(trdDto.getTrdOwnerNm());
			iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, menuUrl, msgType, smsDto,
					UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
		}
		
		
		   //126164
		private void sendSmsEmail(TradeMasterDetailDTO masDto) {
			final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			Organisation org = UserSession.getCurrent().getOrganisation();
			smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
			smsDto.setAppNo(masDto.getApmApplicationId().toString());
			smsDto.setAppName(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE,
							UserSession.getCurrent().getOrganisation().getOrgid());
			setServiceMaster(sm);
			smsDto.setServName(sm.getSmServiceName());
			smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
			String url = "ChangeInCategorySubcategoryPrinting.html";
			org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			int langId = UserSession.getCurrent().getLanguageId();
			smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, smsDto, org, langId);
		}
}
