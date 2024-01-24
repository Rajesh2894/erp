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

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
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
import com.abm.mainet.water.dao.ChangeOfOwnershipRepository;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.domain.ChangeOfOwnerMas;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.ChangeOfOwnershipDTO;
import com.abm.mainet.water.service.ChangeOfOwnerShipService;

/**
 * @author Vivek.Kumar
 * @since 15 June 2016
 */
@Component
@Scope("session")
public class ExecuteChangeOfOwnerModel extends AbstractFormModel {

    private static final long serialVersionUID = -6862654165271451201L;

    private ChangeOfOwnershipDTO commonDto;
    
	@Autowired
    private ChangeOfOwnerShipService changeOfOwnerShipService;

    @Resource
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteChangeOfOwnerModel.class);

    @Autowired
    NewWaterRepository newWaterRepository;
    
	@Autowired
	private TbLoiMasService iTbLoiMasService;
	
	@Autowired
	private IReceiptEntryService iReceiptEntryService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Resource
	private ChangeOfOwnershipRepository iChangeOfOwnershipRepository;
	
    @Override
    public boolean saveForm() {

	boolean status = false;
	getCommonDto().setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	status = changeOfOwnerShipService.executeChangeOfOwnership(getCommonDto());
	setSuccessMessage(status == true ? getAppSession().getMessage("water.approve")
		: getAppSession().getMessage("water.approve.fail"));
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
	    taskAction.setApplicationId(getCommonDto().getApmApplicationId());
	    taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
	    taskAction.setTaskId(getTaskId());

	    WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
	    workflowProcessParameter.setProcessName("scrutiny");
	    workflowProcessParameter.setWorkflowTaskAction(taskAction);
	    try {
				
				  ApplicationContextProvider.getApplicationContext().getBean(
				  IWorkflowExecutionService.class) .updateWorkflow(workflowProcessParameter);
				 
    	     //For all Environments Defect #148737
			printWorkOrder(getCommonDto(), UserSession.getCurrent().getOrganisation().getOrgid(),
					 taskAction.getTaskId());
				
	    } catch (Exception exception) {
		throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
	    }
	}
	return status;
    }

    public ChangeOfOwnershipDTO getCommonDto() {
    	return commonDto;
  	}

  	public void setCommonDto(final ChangeOfOwnershipDTO commonDto) {
  		this.commonDto = commonDto;
  	}

	private void printWorkOrder(ChangeOfOwnershipDTO  changeOfOwnershipDTO, long orgid, Long taskId) {
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgid);
		changeOfOwnershipDTO.setExecutionDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD).format(new Date()));
		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER, Long.valueOf(orgid));
		changeOfOwnershipDTO.setServiceName(changeOfOwnershipDTO.getServiceName());
		ChangeOfOwnerMas master = iChangeOfOwnershipRepository
				.fetchWaterConnectionOwnerDetail(changeOfOwnershipDTO.getApmApplicationId());
		changeOfOwnershipDTO.setChOldName(master.getChOldName()!=null ? master.getChOldName():" ");
		TbKCsmrInfoMH waterConnectionDetailsById = newWaterRepository.getWaterConnectionDetailsById(master.getCsIdn(), master.getOrgId());
		String name ="";
		if(waterConnectionDetailsById!=null){
			name = (waterConnectionDetailsById.getCsName()!=null && !waterConnectionDetailsById.getCsName().isEmpty() ? 
					waterConnectionDetailsById.getCsName()+" " : "") + (waterConnectionDetailsById.getCsMname()!=null && 
					!waterConnectionDetailsById.getCsMname().isEmpty() ? 
							waterConnectionDetailsById.getCsMname()+" " : "") + (waterConnectionDetailsById.getCsLname()!=null && 
							!waterConnectionDetailsById.getCsLname().isEmpty() ? 
									waterConnectionDetailsById.getCsLname() : "");
			changeOfOwnershipDTO.setName(changeOfOwnershipDTO.getApplicantFullName());
			changeOfOwnershipDTO.setAddress(waterConnectionDetailsById.getCsAdd());
			changeOfOwnershipDTO.setCity(waterConnectionDetailsById.getCsCcityName());
			changeOfOwnershipDTO.setConnectionNo(waterConnectionDetailsById.getCsCcn());
			changeOfOwnershipDTO.setConnectionInch(CommonMasterUtility.findLookUpDesc(PrefixConstants.WATERMODULEPREFIX.CSZ, orgid,
					waterConnectionDetailsById.getCsCcnsize()));
		}
		
		TbCfcApplicationMstEntity applicationEntity = cfcApplicationMasterDAO.getCFCApplicationMasterByApplicationId(
				changeOfOwnershipDTO.getApmApplicationId(), orgid);
		if(applicationEntity!=null) {
			changeOfOwnershipDTO.setApplicationDate(changeOfOwnershipDTO.getApprovedDate().replace("/", "-"));
			changeOfOwnershipDTO.setApmApplicationId(applicationEntity.getApmApplicationId());
		}
		if(serviceMaster != null) {
			List<TbLoiMas> getloiByApplicationId = iTbLoiMasService.getloiByApplicationId(changeOfOwnershipDTO.getApmApplicationId(), serviceMaster.getSmServiceId(), orgid);
			if(CollectionUtils.isNotEmpty(getloiByApplicationId)) {
				TbLoiMas tbLoiMas = getloiByApplicationId.get(getloiByApplicationId.size()-1);
				changeOfOwnershipDTO.setLoiDate(tbLoiMas.getLoiDate());
				changeOfOwnershipDTO.setLoiNo(tbLoiMas.getLoiNo());
			}
		}
		TbServiceReceiptMasEntity receiptDetails = iReceiptEntryService.getReceiptDetailsByAppId(changeOfOwnershipDTO.getApmApplicationId(), orgid);
		if(receiptDetails!=null) {
			changeOfOwnershipDTO.setPaymentDate(receiptDetails.getRmDate());
			changeOfOwnershipDTO.setReceiptNo(receiptDetails.getRmRcptno());
			changeOfOwnershipDTO.setStartDate(receiptDetails.getRmDate());

		}
		changeOfOwnershipDTO.setCustomString("With reference to Point No. 2, the amount to be paid regarding the Change of Ownership of connection no."+changeOfOwnershipDTO.getConnectionNo() +", you have paid the amount to "+
		"with reference to LOI No.:"+changeOfOwnershipDTO.getLoiNo()+" to Kalyan Dombivli Municipal Corporation (Receipt No.: <b>"+ changeOfOwnershipDTO.getReceiptNo() +"</b> Date: <b>" + 
		changeOfOwnershipDTO.getPaymentDate() +"</b>).Earlier the Connection No. <b>"+ changeOfOwnershipDTO.getConnectionNo() +"</b> was in the name of "
		+ "<b>" + changeOfOwnershipDTO.getChOldName() + "</b> use of <b>" + changeOfOwnershipDTO.getConnectionInch() + "</b> inch sized,"
		+ " by this order it is now being approved to change in the name of <b>" + name +".");
		
		commonDto.setServiceCode(serviceMaster.getSmShortdesc());

	LOGGER.info("change of ownership dto " + commonDto.toString());
	}
}
