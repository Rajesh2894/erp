package com.abm.mainet.water.ui.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.service.WaterReconnectionService;

/**
 * @author Arun.Chavda
 *
 */
@Component
@Scope("session")
public class WaterExecuteReconnectionFormModel extends AbstractFormModel {

	private static final long serialVersionUID = -2379102628750075021L;

	@Autowired
	private WaterReconnectionService waterReconnectionService;

	private String serviceName;
	private Long applicationId;
	private String applicantFullName;
	private Date applicationDate;
	private Date reconnExecutionDate;
	private String approvedBy;
	private Date approvalDate;
	private String executeReconnection;
	private Long empId;
	private Long orgId;
	private Long serviceId;

	private String levelValue;
	private Long levelId;
	private Long level;
	private Long taskId;
	private String addressLine1;
	private String cityName;
	private String loiNo;
	private Date loiDate;
	private String receiptNo;
	private Date paymentDate;
	private String connectionNo;
	private Date startDate;
	private String connectionSize;
	private String customString; 
	
	private ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();

	@Autowired
    NewWaterRepository newWaterRepository;
	    
	@Autowired
	private TbLoiMasService iTbLoiMasService;
		
	@Autowired
	private IReceiptEntryService iReceiptEntryService;
		
	@Autowired
	private ServiceMasterService serviceMasterService;
		
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteChangeOfUsageModel.class);

	@Override
	public boolean saveForm() {

		final boolean result = validateFormData(getReconnExecutionDate());

		if (result) {
			return false;
		}

		final TbWaterReconnection reconnection = waterReconnectionService.getReconnectionDetails(applicationId, orgId);
		SimpleDateFormat ddMmYyyyFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
		try {
			reconnection.setRcnExedate(ddMmYyyyFormat.parse(ddMmYyyyFormat.format(getReconnExecutionDate())));
		} catch (ParseException e) {
			logger.error("Exception while parsing date: " + getReconnExecutionDate());
		}
		reconnection.setCcnFlag(executeReconnection);
		reconnection.setRcnGranted(MainetConstants.Common_Constant.YES);
		reconnection.setUpdatedBy(getEmpId());
		reconnection.setUpdatedDate(new Date());
		reconnection.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
		lableValueDTO.setApplicationId(applicationId);
		lableValueDTO.setLableId(levelId);
		lableValueDTO.setLableValue(levelValue);
		lableValueDTO.setLevel(level);

		lableValueDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
		lableValueDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		lableValueDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setApplicationId(applicationId);
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		taskAction.setTaskId(taskId);

		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName("scrutiny");
		workflowProcessParameter.setWorkflowTaskAction(taskAction);

		waterReconnectionService.updateUserTaskAndReconnectionExecutionDetails(reconnection, getLableValueDTO());
		
		 try { 
			  ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
			  Organisation org = new Organisation();
			  org.setOrgid(orgId);
			  if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
				 printWorkOrder(orgId, serviceId, taskAction.getTaskId(), applicationId);
			  }
			
		}
		 catch (Exception exception) { 
			  throw new FrameworkException("Exception  Occured when Update Workflow methods",  exception); 
		}
	setSuccessMessage("Your application for Water Reconnection Updated successfully!");
		return true;
	}

	private void printWorkOrder(long orgid, Long serviceId, Long taskId, Long applicationId) {
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgid);
		try {
			ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, Long.valueOf(orgid));
			if(serviceMaster!=null) {
				setServiceName(serviceName!=null && !serviceName.isEmpty()? serviceName : serviceMaster.getSmServiceName());
			}
			try {
				TbWaterReconnection reconnectionDetails = waterReconnectionService.getReconnectionDetails(applicationId, orgid);
				if(reconnectionDetails!=null) {
					try {
						TbKCsmrInfoMH waterConnectionDetailsById = newWaterRepository.getWaterConnectionDetailsById(reconnectionDetails.getCsIdn(), orgid);
						if(waterConnectionDetailsById!=null){
							setAddressLine1(waterConnectionDetailsById.getCsAdd());
							setCityName(waterConnectionDetailsById.getCsCcityName());
							setConnectionSize(CommonMasterUtility.findLookUpDesc(PrefixConstants.WATERMODULEPREFIX.CSZ, orgid,
								waterConnectionDetailsById.getCsCcnsize()));
							setConnectionNo(waterConnectionDetailsById.getCsCcn());
						}
						
						try {
							List<TbLoiMas> getloiByApplicationId = iTbLoiMasService.getloiByApplicationId(applicationId, serviceId, orgid);
							if(CollectionUtils.isNotEmpty(getloiByApplicationId)) {
								TbLoiMas tbLoiMas = getloiByApplicationId.get(getloiByApplicationId.size()-1);
								setLoiDate(tbLoiMas.getLoiDate());
								setLoiNo(tbLoiMas.getLoiNo());
							}
							
							try {
								TbServiceReceiptMasEntity receiptDetails = iReceiptEntryService.getReceiptDetailsByAppId(applicationId, orgid);
								if(receiptDetails!=null) {
									setPaymentDate(receiptDetails.getRmDate());
									setReceiptNo(String.valueOf(receiptDetails.getRmRcptno()));
									setStartDate(receiptDetails.getRmDate());
								}
								
								setCustomString("With reference to Point No. 2, the amount to be paid regarding the Water Reconnection of connection no." 
										+ getConnectionNo() +", you have paid the amount with reference to LOI No.: "+  getLoiNo() + " to Kalyan Dombivli Municipal "
										+ "Corporation (Receipt No.: <b>" + getReceiptNo() + "</b> Date: <b>"+  getPaymentDate() + "</b>). Connection No.<b> " +
										getConnectionNo() + "</b> was use of <b>" + getConnectionSize() + "</b> inch sized, by this order it is now being approved "
										+ "to reconnect the service from <b>" + getStartDate() + "</b>."
								);
							}catch(Exception ex) {
								LOGGER.error("Receipt details not found for applicationId: "+ applicationId + " " + ex.getMessage());
							}
							
						}catch(Exception ex) {
							LOGGER.error("Loi details not found for applicationId: "+ applicationId + " " + ex.getMessage());
						}
						
						
						
					}catch(Exception ex) {
						LOGGER.error("Connection obj not found for csIdn: "+ reconnectionDetails.getCsIdn() + " " + ex.getMessage());
					}
					
				}
			}catch(Exception ex) {
				LOGGER.error("Reconnection details not found for application id: "+ applicationId + " " + ex.getMessage());
			}
		}catch(Exception ex) {
			LOGGER.error("Service not found for service id: "+ serviceId + " " + ex.getMessage());
		}
		
	}		

	private boolean validateFormData(final Date reconnExecuteDate) {

		boolean status = false;

		if ((reconnExecuteDate == null) || reconnExecuteDate.equals(MainetConstants.BLANK)) {
			addValidationError(getAppSession().getMessage("recon.date"));
			status = true;
		}
		return status;

	}

	/**
	 * @return the serviceName
	 */
	@Override
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	@Override
	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the applicantFullName
	 */
	public String getApplicantFullName() {
		return applicantFullName;
	}

	/**
	 * @param applicantFullName
	 *            the applicantFullName to set
	 */
	public void setApplicantFullName(final String applicantFullName) {
		this.applicantFullName = applicantFullName;
	}

	/**
	 * @return the applicationDate
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @param applicationDate
	 *            the applicationDate to set
	 */
	public void setApplicationDate(final Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	/**
	 * @return the reconnExecutionDate
	 */
	public Date getReconnExecutionDate() {
		return reconnExecutionDate;
	}

	/**
	 * @param reconnExecutionDate
	 *            the reconnExecutionDate to set
	 */
	public void setReconnExecutionDate(final Date reconnExecutionDate) {
		this.reconnExecutionDate = reconnExecutionDate;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy
	 *            the approvedBy to set
	 */
	public void setApprovedBy(final String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvalDate
	 */
	public Date getApprovalDate() {
		return approvalDate;
	}

	/**
	 * @param approvalDate
	 *            the approvalDate to set
	 */
	public void setApprovalDate(final Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	/**
	 * @return the executeReconnection
	 */
	public String getExecuteReconnection() {
		return executeReconnection;
	}

	/**
	 * @param executeReconnection
	 *            the executeReconnection to set
	 */
	public void setExecuteReconnection(final String executeReconnection) {
		this.executeReconnection = executeReconnection;
	}

	/**
	 * @return the empId
	 */
	public Long getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(final Long empId) {
		this.empId = empId;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the serviceId
	 */
	@Override
	public Long getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId
	 *            the serviceId to set
	 */
	@Override
	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	public ScrutinyLableValueDTO getLableValueDTO() {
		return lableValueDTO;
	}

	public void setLableValueDTO(ScrutinyLableValueDTO lableValueDTO) {
		this.lableValueDTO = lableValueDTO;
	}

	public String getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public WaterReconnectionService getWaterReconnectionService() {
		return waterReconnectionService;
	}

	public void setWaterReconnectionService(WaterReconnectionService waterReconnectionService) {
		this.waterReconnectionService = waterReconnectionService;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public Date getLoiDate() {
		return loiDate;
	}

	public void setLoiDate(Date loiDate) {
		this.loiDate = loiDate;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getConnectionSize() {
		return connectionSize;
	}

	public void setConnectionSize(String connectionSize) {
		this.connectionSize = connectionSize;
	}

	public String getCustomString() {
		return customString;
	}

	public void setCustomString(String customString) {
		this.customString = customString;
	}
}
