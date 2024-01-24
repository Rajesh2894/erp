package com.abm.mainet.tradeLicense.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class CancellationLicenseModel extends AbstractFormModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1050288155666860043L;
	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
	private ServiceMaster serviceMaster = new ServiceMaster();
	private TbCfcApplicationMst cfcEntity = new TbCfcApplicationMst();
	private List<TbLoiMas> tbLoiMas = new ArrayList<>();
	private String viewMode;
	private Long rmRcptno;
	private BigDecimal rmAmount;
	private String payMode;
	private String loiDateDesc;
	private Long taskId;
	private String ownerName;
	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;
	@Autowired
	IWorkflowExecutionService iWorkflowExecutionService;
	
	 @Autowired
	 private ISMSAndEmailService iSMSAndEmailService;
	    

	public boolean saveForm() {

		TradeMasterDetailDTO masDto = getTradeDetailDTO();
		
		String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
		iTradeLicenseApplicationService.updateTradeLicenseStatus(masDto,
				UserSession.getCurrent().getOrganisation().getOrgid(), masDto.getTrdStatus(),lgIpMacUpd);

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

		this.setSuccessMessage(getAppSession().getMessage(getAppSession().getMessage("trd.cancel.licNo.succ")+" "+masDto.getTrdLicno() + " "
				+ getAppSession().getMessage("trd.cancel.success.msg")));
		sendSmsEmail(masDto);
		return true;
	}
	
	//126164
		private void sendSmsEmail(TradeMasterDetailDTO masDto) {
			final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			Organisation org = UserSession.getCurrent().getOrganisation();
			smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
			smsDto.setAppNo(masDto.getApmApplicationId().toString());
			smsDto.setAppName(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE,
							UserSession.getCurrent().getOrganisation().getOrgid());
			setServiceMaster(sm);
			smsDto.setServName(sm.getSmServiceName());
			smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
			String url = "CancellationLicense.html";
			org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			int langId = UserSession.getCurrent().getLanguageId();
			smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			smsDto.setLicNo(masDto.getTrdLicno());
			iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, smsDto, org, langId);
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

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getLoiDateDesc() {
		return loiDateDesc;
	}

	public void setLoiDateDesc(String loiDateDesc) {
		this.loiDateDesc = loiDateDesc;
	}

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public TbCfcApplicationMst getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMst cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

}
