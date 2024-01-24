package com.abm.mainet.water.ui.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
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
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.service.WaterDisconnectionService;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ExecuteDisConnectionProcessModel extends AbstractFormModel {

	private static final long serialVersionUID = 4143057066191035064L;

	@Autowired
	private WaterDisconnectionService disconnectionService;

	private Long applicationId;
	private Long serviceId;
	private String levelValue;
	private Long levelId;
	private Long level;

	private Date applicationDate;
	private String applicanttName;
	private String serviceName;
	private String approveBy;
	private Date approveDate;
	private boolean homeGrid;

	private ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();
	private TBWaterDisconnectionDTO commonDto = new TBWaterDisconnectionDTO();

	 @Resource
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteDisConnectionProcessModel.class);

    @Autowired
    NewWaterRepository newWaterRepository;
    
	@Autowired
	private TbLoiMasService iTbLoiMasService;
	
	@Autowired
	private IReceiptEntryService iReceiptEntryService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Override
	public boolean saveForm() {
		if ((null == commonDto.getDiscGrantFlag()) || commonDto.getDiscGrantFlag().isEmpty()) {
			addValidationError(getAppSession().getMessage("dis.execute"));
		}
		if (null == commonDto.getDiscExecdate()) {
			addValidationError(getAppSession().getMessage("dis.execute.date"));
		}
		if (hasValidationErrors()) {

			return false;
		}
		final WaterDeconnectionRequestDTO dto = new WaterDeconnectionRequestDTO();
		commonDto.setDiscAprvdate(approveDate);
		commonDto.setDiscApprovedby(UserSession.getCurrent().getEmployee().getEmpId());
		dto.setDisconnectionDto(commonDto);

		lableValueDTO.setApplicationId(applicationId);
		lableValueDTO.setLableId(levelId);
		lableValueDTO.setLableValue(levelValue);
		lableValueDTO.setLevel(level);
		lableValueDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
		lableValueDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		lableValueDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		boolean status = disconnectionService.updateDisconnectionDetails(dto, getLableValueDTO(), UserSession.getCurrent().getOrganisation());
		
		
		  if (status) { 
			  WorkflowTaskAction taskAction = new WorkflowTaskAction();
			  taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			  taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
			  taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
			  taskAction.setDateOfAction(new Date()); taskAction.setCreatedDate(new
			  Date());
			  taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			  taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
			  taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
			  taskAction.setApplicationId(applicationId);
			  taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
			  taskAction.setTaskId(getTaskId());
			  
			  WorkflowProcessParameter workflowProcessParameter = new
			  WorkflowProcessParameter();
			  workflowProcessParameter.setProcessName("scrutiny");
			  workflowProcessParameter.setWorkflowTaskAction(taskAction);
		  
		  try { 
			  ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class) .updateWorkflow(workflowProcessParameter);
			  if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
					printWorkOrder(getDisconnectionDTO(), UserSession.getCurrent().getOrganisation().getOrgid(),
							 taskAction.getTaskId(), getDisconnectionDTO().getApmApplicationId());
				} 
		  }
		  catch (Exception exception) { 
			  	throw new FrameworkException("Exception  Occured when Update Workflow methods",exception);
			 }
		  
		  }
		 
		setSuccessMessage(getAppSession().getMessage("dis.execute.success"));
		return true;
	}

	private void printWorkOrder(TBWaterDisconnectionDTO commonDto, long orgid, Long taskId,
			Long apmApplicationId) {

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgid);
		commonDto.setExecutionDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(new Date()));
		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, Long.valueOf(orgid));
		if(serviceMaster!=null) {
			commonDto.setServiceName(serviceMaster.getSmServiceName());
		}
		TbKCsmrInfoMH waterConnectionDetailsById = newWaterRepository.getWaterConnectionDetailsById(commonDto.getCsIdn(), orgid);
		if(waterConnectionDetailsById!=null){
			String name = (waterConnectionDetailsById.getCsName()!=null && !waterConnectionDetailsById.getCsName().isEmpty() ? 
					waterConnectionDetailsById.getCsName()+" " : "") + (waterConnectionDetailsById.getCsMname()!=null && 
					!waterConnectionDetailsById.getCsMname().isEmpty() ? 
							waterConnectionDetailsById.getCsMname()+" " : "") + (waterConnectionDetailsById.getCsLname()!=null && 
							!waterConnectionDetailsById.getCsLname().isEmpty() ? 
									waterConnectionDetailsById.getCsLname() : "");
			commonDto.setName(applicanttName!=null && !applicanttName.isEmpty() ? applicanttName : name);
			commonDto.setAddress(waterConnectionDetailsById.getCsAdd());
			commonDto.setCity(waterConnectionDetailsById.getCsCcityName());
			commonDto.setConnectionNo(waterConnectionDetailsById.getCsCcn());
			commonDto.setConnectionInch(CommonMasterUtility.findLookUpDesc(PrefixConstants.WATERMODULEPREFIX.CSZ, orgid,
					waterConnectionDetailsById.getCsCcnsize()));
		}
		
		TbCfcApplicationMstEntity applicationEntity = cfcApplicationMasterDAO.getCFCApplicationMasterByApplicationId(
				applicationId, orgid);
		if(applicationEntity!=null) {
			commonDto.setApplicationDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(
					commonDto.getDiscAprvdate()));
			commonDto.setApmApplicationId(applicationEntity.getApmApplicationId());
		}
		
		List<TbLoiMas> getloiByApplicationId = iTbLoiMasService.getloiByApplicationId(applicationId, serviceMaster.getSmServiceId(), orgid);
		if(CollectionUtils.isNotEmpty(getloiByApplicationId)) {
			TbLoiMas tbLoiMas = getloiByApplicationId.get(getloiByApplicationId.size()-1);
			commonDto.setLoiDate(tbLoiMas.getLoiDate());
			commonDto.setLoiNo(tbLoiMas.getLoiNo());
		}
		
		TbServiceReceiptMasEntity receiptDetails = iReceiptEntryService.getReceiptDetailsByAppId(applicationId, orgid);
		if(receiptDetails!=null) {
			commonDto.setPaymentDate(receiptDetails.getRmDate());
			commonDto.setReceiptNo(receiptDetails.getRmRcptno());
			commonDto.setStartDate(receiptDetails.getRmDate());
		}

		commonDto.setCustomString("With reference to Point No. 2, the amount to be paid regarding the Water Disconnection of connection no."+ commonDto.getConnectionNo() +", you have paid the amount " + 
				"with reference to LOI No.:"+ commonDto.getLoiNo()+ "to Kalyan Dombivli Municipal Corporation (Receipt No.: <b>" + commonDto.getReceiptNo() + "</b> Date: "
				+ "<b>"+ commonDto.getPaymentDate() + "</b>). Connection No.<b> " + commonDto.getConnectionNo() + "</b> was"
				+ " use of <b>" + commonDto.getConnectionInch() + "</b> inch sized, by this order it is now being approved to disconnect the service" 
				+ " from <b>" + commonDto.getStartDate() + "</b>."
		);
		
	}

	public ScrutinyLableValueDTO getLableValueDTO() {
		return lableValueDTO;
	}

	public TBWaterDisconnectionDTO getDisconnectionDTO() {
		return commonDto;
	}

	public void setDisconnectionDTO(final TBWaterDisconnectionDTO disconnectionDTO) {
		this.commonDto = disconnectionDTO;
	}

	public void setLableValueDTO(final ScrutinyLableValueDTO lableValueDTO) {
		this.lableValueDTO = lableValueDTO;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(final Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getApplicanttName() {
		return applicanttName;
	}

	public void setApplicanttName(final String applicanttName) {
		this.applicanttName = applicanttName;
	}

	@Override
	public Long getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}

	public String getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(final String levelValue) {
		this.levelValue = levelValue;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(final Long levelId) {
		this.levelId = levelId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(final Long level) {
		this.level = level;
	}

	public String getApproveBy() {
		if (null == approveBy) {
			approveBy = UserSession.getCurrent().getEmployee().getFullName();
		}
		return approveBy;
	}

	public Date getApproveDate() {
		if (null == approveDate) {
			approveDate = new Date();
		}
		return approveDate;
	}

	/**
	 * @param applicationId2
	 * @param orgid
	 */
	public void setCommonDto(final Long applicationId2, final long orgId) {
		commonDto = disconnectionService.getDisconnectionAppDetails(orgId, applicationId2);

	}

	public TBWaterDisconnectionDTO getCommonDto() {
		return commonDto;
	}

	public boolean isHomeGrid() {
		return homeGrid;
	}

	public void setHomeGrid(final boolean homeGrid) {
		this.homeGrid = homeGrid;
	}
}
