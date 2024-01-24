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
import com.abm.mainet.water.domain.CsmrInfoHistEntity;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.ChangeOfUsageDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.service.ChangeOfUsageService;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ExecuteChangeOfUsageModel extends AbstractFormModel {

	private static final long serialVersionUID = 4143057066191035064L;

	@Autowired
	private ChangeOfUsageService changeOfUsageService;

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
	private ChangeOfUsageDTO commonDto = new ChangeOfUsageDTO();

	@Resource
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteChangeOfUsageModel.class);

    @Autowired
    NewWaterRepository newWaterRepository;
    
	@Autowired
	private TbLoiMasService iTbLoiMasService;
	
	@Autowired
	private IReceiptEntryService iReceiptEntryService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean saveForm() {
		if ((null == commonDto.getChanGrantFlag()) || commonDto.getChanGrantFlag().isEmpty()) {
			addValidationError(getAppSession().getMessage("cou.execute"));
		}
		if (null == commonDto.getChanExecdate()) {
			addValidationError(getAppSession().getMessage("cou.execute.date"));
		}
		if (hasValidationErrors()) {

			return false;
		}
		final ChangeOfUsageRequestDTO dto = new ChangeOfUsageRequestDTO();
		commonDto.setChanAprvdate(approveDate);
		commonDto.setChanApprovedby(UserSession.getCurrent().getEmployee().getEmpId());
		commonDto.setChanGrantFlag(MainetConstants.Y_FLAG);
		dto.setChangeOfUsage(commonDto);
		lableValueDTO.setApplicationId(applicationId);
		lableValueDTO.setLableId(levelId);
		lableValueDTO.setLableValue(levelValue);
		lableValueDTO.setLevel(level);

		lableValueDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
		lableValueDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		lableValueDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		boolean status = changeOfUsageService.updateChangeOfUsageDetails(dto, getLableValueDTO());

		if (status) {
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
			taskAction.setTaskId(getTaskId());

			WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
			workflowProcessParameter.setProcessName("scrutiny");
			workflowProcessParameter.setWorkflowTaskAction(taskAction);
			try {
				ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
						.updateWorkflow(workflowProcessParameter);
				   //For all Environments Defect #148738
					printWorkOrder(commonDto, UserSession.getCurrent().getOrganisation().getOrgid(),
							serviceId, taskAction.getTaskId(), applicationId);
				
			} catch (Exception exception) {
				throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
			}
		}
		setSuccessMessage(getAppSession().getMessage("cou.execute.success"));
		return true;
	}

	@SuppressWarnings("deprecation")
	private void printWorkOrder(ChangeOfUsageDTO commonDto, long orgid, Long serviceId, Long taskId, Long applicationId) {
		
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgid);
		commonDto.setExecutionDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(
					commonDto.getChanExecdate()));
		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(serviceId, Long.valueOf(orgid));
		if(serviceMaster!=null) {
			commonDto.setServiceName(serviceName!=null && !serviceName.isEmpty()? serviceName : serviceMaster.getSmServiceName());
		}
		
		CsmrInfoHistEntity csmrHistory = newWaterRepository.getCsmrHistByCsIdAndOrgId(commonDto.getCsIdn(), orgid);
		String oldMeterType = null;
		if(csmrHistory!=null) {
			final String oldUsage = CommonMasterUtility
                    .getHierarchicalLookUp(csmrHistory.getTrmGroup1(), organisation).getDescLangFirst();
			final String oldUsageSubType2 = CommonMasterUtility
                    .getHierarchicalLookUp(csmrHistory.getTrmGroup2(), organisation).getDescLangFirst();
			
			oldMeterType = CommonMasterUtility.
					getNonHierarchicalLookUpObject(csmrHistory.getCsMeteredccn(), organisation).getLookUpDesc();
			commonDto.setCcnTypeFrom(oldUsage + " (" + oldMeterType +") "+ oldUsageSubType2);

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
			
			final String newUsage = CommonMasterUtility
                    .getHierarchicalLookUp(waterConnectionDetailsById.getTrmGroup1(), organisation).getDescLangFirst();
			String newUsageSubType2 = null;
			if(waterConnectionDetailsById.getTrmGroup2() != null) {
			  newUsageSubType2 = CommonMasterUtility
	                    .getHierarchicalLookUp(waterConnectionDetailsById.getTrmGroup2(), organisation).getDescLangFirst();
			}
			String newMeterType = null;
			if(waterConnectionDetailsById.getCsMeteredccn() != null) {
				newMeterType = CommonMasterUtility.
						getNonHierarchicalLookUpObject(waterConnectionDetailsById.getCsMeteredccn(), organisation).getLookUpDesc();
				
			}
			commonDto.setCcnTypeTo(newUsage + " (" + oldMeterType + "-"+ newMeterType +") " + newUsageSubType2);
			commonDto.setConnectionInch(CommonMasterUtility.findLookUpDesc(PrefixConstants.WATERMODULEPREFIX.CSZ, orgid,
					waterConnectionDetailsById.getCsCcnsize()));
		}
		
		TbCfcApplicationMstEntity applicationEntity = cfcApplicationMasterDAO.getCFCApplicationMasterByApplicationId(
				applicationId, orgid);
		if(applicationEntity!=null) {
			commonDto.setApplicationDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(
					commonDto.getChanExecdate()));
			commonDto.setApmApplicationId(applicationEntity.getApmApplicationId());
		}
		
		List<TbLoiMas> getloiByApplicationId = iTbLoiMasService.getloiByApplicationId(applicationId, serviceId, orgid);
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
		
		commonDto.setCustomString("With reference to Point No. 2, the amount to be paid regarding the change of type of water usage, you have paid " + 
				"amount to Kalyan Dombivli Municipal Corporation (Receipt No.: <b>" + commonDto.getReceiptNo() + "</b> Date: "
				+ "<b>"+ commonDto.getPaymentDate() + "</b>). Connection No.<b> " + commonDto.getConnectionNo() + "</b> was"
				+ " a <b>" + commonDto.getCcnTypeFrom() + "</b> use of <b>" + commonDto.getConnectionInch() + "</b> inch sized, "
				+ "by this order it is now being approved to change the type of <b>" + commonDto.getCcnTypeFrom() +" </b> use to"
				+ "<b> " + commonDto.getCcnTypeTo() + " </b> from <b>" + commonDto.getStartDate() + "</b>."
		);
				
		LOGGER.info("change of usage dto " + commonDto.toString());
	}

	public ScrutinyLableValueDTO getLableValueDTO() {
		return lableValueDTO;
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
		commonDto = changeOfUsageService.getChangeOfUses(orgId, applicationId2);

	}

	public boolean isHomeGrid() {
		return homeGrid;
	}

	public void setHomeGrid(final boolean homeGrid) {
		this.homeGrid = homeGrid;
	}

	public ChangeOfUsageDTO getCommonDto() {
		return commonDto;
	}

	public void setCommonDto(ChangeOfUsageDTO commonDto) {
		this.commonDto = commonDto;
	}

	public void setApproveBy(final String approveBy) {
		this.approveBy = approveBy;
	}

	public void setApproveDate(final Date approveDate) {
		this.approveDate = approveDate;
	}

}
