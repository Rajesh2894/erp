package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.TaxDefinationDto;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WmsRaBillTaxDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorksRABillService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
@Scope("session")
public class RaBillGenerationModel extends AbstractFormModel {

	private static final long serialVersionUID = 2801889042870331067L;

	@Autowired
	private WorksRABillService raBillService;
	
	@Autowired
	private WorkDefinitionService workDefinitionService;
	
	@Autowired
    private ILocationMasService locationMasService;

	private WorkDefinitionDto wmsDto;
	private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();
	private List<WorkDefinitionDto> workDefList = new ArrayList<>();
	private List<LookUp> valueTypeList = new ArrayList<LookUp>();
	private WorkDefinitionDto workDefDto = new WorkDefinitionDto();
	private WmsProjectMasterDto projMasDto = new WmsProjectMasterDto();
	private List<LookUp> taxList = new ArrayList<LookUp>();
	private List<WorksRABillDto> raBillList = new ArrayList<>();
	private List<MeasurementBookMasterDto> mbList = new ArrayList<>();
	private WorkOrderDto orderDto = new WorkOrderDto();
	private WorksRABillDto billMasDto = new WorksRABillDto();
	private List<WorkDefinationYearDetDto> yearDtos = new ArrayList<>();
	private List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();

	private String saveMode;
	private String status;
	private TenderWorkDto tndWorkDto = new TenderWorkDto();
	private WmsRaBillTaxDetailsDto raBillTaxDet = new WmsRaBillTaxDetailsDto();
	private String approvalMode;
	private Long contractbgDate;
	
	public WorkDefinitionDto getWmsDto() {
		return wmsDto;
	}

	public void setWmsDto(WorkDefinitionDto wmsDto) {
		this.wmsDto = wmsDto;
	}
	
	public Long getContractbgDate() {
		return contractbgDate;
	}

	public void setContractbgDate(Long contractbgDate) {
		this.contractbgDate = contractbgDate;
	}

	public WmsRaBillTaxDetailsDto getRaBillTaxDet() {
		return raBillTaxDet;
	}

	public void setRaBillTaxDet(WmsRaBillTaxDetailsDto raBillTaxDet) {
		this.raBillTaxDet = raBillTaxDet;
	}

	public TenderWorkDto getTndWorkDto() {
		return tndWorkDto;
	}

	public void setTndWorkDto(TenderWorkDto tndWorkDto) {
		this.tndWorkDto = tndWorkDto;
	}
	
	public List<AccountHeadSecondaryAccountCodeMasterEntity> getBudgetList() {
		return budgetList;
	}

	public void setBudgetList(List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList) {
		this.budgetList = budgetList;
	}

	private String removeTaxDetIds;
	private String removeTaxWithIds;

	// bill posting

	private VendorBillApprovalDTO approvalDTO = new VendorBillApprovalDTO();
	private List<VendorBillExpDetailDTO> expDetListDto = new ArrayList<>();
	private VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();
	private WorkOrderDto workOrder = null;
	private TenderWorkDto tenderWorkDto = null;
	private WorkDefinitionDto definitionDto = null;
	private boolean raBill;
	private WmsProjectMasterDto projectMasterDto = new WmsProjectMasterDto();
	private TbDepartment department = new TbDepartment();
	private MeasurementBookMasterDto masterDto;
	private List<TbDepartment> departmentsList;
	private String raBillDateStr;
	private String workBillNumber;
	private List<MeasurementBookMasterDto> measureMentBookMastDtosList = new ArrayList<>();
	private Long deptId;
	private List<TaxDefinationDto> listTaxDefinationDto = new ArrayList<>();

	public List<WmsProjectMasterDto> getProjectMasterList() {
		return projectMasterList;
	}

	public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
		this.projectMasterList = projectMasterList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<WorkDefinitionDto> getWorkDefList() {
		return workDefList;
	}

	public void setWorkDefList(List<WorkDefinitionDto> workDefList) {
		this.workDefList = workDefList;
	}

	public WorkDefinitionDto getWorkDefDto() {
		return workDefDto;
	}

	public void setWorkDefDto(WorkDefinitionDto workDefDto) {
		this.workDefDto = workDefDto;
	}

	public WmsProjectMasterDto getProjMasDto() {
		return projMasDto;
	}

	public void setProjMasDto(WmsProjectMasterDto projMasDto) {
		this.projMasDto = projMasDto;
	}

	public List<LookUp> getValueTypeList() {
		return valueTypeList;
	}

	public void setValueTypeList(List<LookUp> valueTypeList) {
		this.valueTypeList = valueTypeList;
	}

	public List<WorksRABillDto> getRaBillList() {
		return raBillList;
	}

	public void setRaBillList(List<WorksRABillDto> raBillList) {
		this.raBillList = raBillList;
	}

	public List<MeasurementBookMasterDto> getMbList() {
		return mbList;
	}

	public void setMbList(List<MeasurementBookMasterDto> mbList) {
		this.mbList = mbList;
	}

	public WorksRABillDto getBillMasDto() {
		return billMasDto;
	}

	public void setBillMasDto(WorksRABillDto billMasDto) {
		this.billMasDto = billMasDto;
	}

	public List<LookUp> getTaxList() {
		return taxList;
	}

	public void setTaxList(List<LookUp> taxList) {
		this.taxList = taxList;
	}

	public WorkOrderDto getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(WorkOrderDto orderDto) {
		this.orderDto = orderDto;
	}
	
	public List<WorkDefinationYearDetDto> getYearDtos() {
		return yearDtos;
	}

	public void setYearDtos(List<WorkDefinationYearDetDto> yearDtos) {
		this.yearDtos = yearDtos;
	}

	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date newDate = new Date();
		List<Long> removeIds = null;
		WorksRABillDto masDto = getBillMasDto();
		List<WorkDefinationYearDetDto> budgetdto = getYearDtos();
		List<WmsRaBillTaxDetailsDto> billTaxDetailsDtos=masDto.getRaBillTaxDetails();
		
		masDto.setRaBillAmt(masDto.getRaBillAmt());
		masDto.getMbId().clear();
		masDto.getRaBillTaxDetails().addAll(masDto.getRaBillTaxDtoWith());
		if (masDto.getCreatedBy() == null) {
			masDto.setRaStatus(getStatus());
			masDto.setCreatedBy(createdBy);
			masDto.setCreatedDate(newDate);
			masDto.setOrgId(orgId);
			masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			masDto.setRaGeneratedDate(new Date());
			masDto.setProjId(getProjMasDto().getProjId());
			masDto.setWorkId(getWorkDefDto().getWorkId());
			for (WmsRaBillTaxDetailsDto taxDto : masDto.getRaBillTaxDetails()) {
				taxDto.setCreatedBy(createdBy);
				taxDto.setCreatedDate(newDate);
				taxDto.setOrgId(orgId);
				taxDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
			for (MeasurementBookMasterDto mbDto : mbList) {
				if (mbDto.isCheckBox()) {
					masDto.getMbId().add(mbDto.getWorkMbId());
				}
			}
		} else {
			if (getStatus() != null) {
				masDto.setRaStatus(getStatus());
			} else if (masDto.getRaStatus() != null) {
				masDto.setRaStatus(masDto.getRaStatus());
			}
			masDto.setUpdatedBy(createdBy);
			masDto.setUpdatedDate(newDate);
			masDto.setOrgId(orgId);
			masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			for (WmsRaBillTaxDetailsDto taxDto : masDto.getRaBillTaxDetails()) {
				taxDto.setUpdatedBy(createdBy);
				taxDto.setUpdatedDate(newDate);
				taxDto.setOrgId(orgId);
				taxDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
			for (MeasurementBookMasterDto mbDto : mbList) {
				if (mbDto.isCheckBox()) {
					masDto.getMbId().add(mbDto.getWorkMbId());
				}
			}
		}

		String ids = getRemoveTaxDetIds();
		if (ids != null && !ids.isEmpty()) {
			removeIds = new ArrayList<>();
			String array[] = ids.split(MainetConstants.operator.COMMA);
			for (String id : array) {
				removeIds.add(Long.valueOf(id));
			}
		}
		
		String withId = getRemoveTaxWithIds();
		if (withId != null && !withId.isEmpty()) {
			removeIds = new ArrayList<>();
			String array1[] = withId.split(MainetConstants.operator.COMMA);
			for (String id : array1) {
				removeIds.add(Long.valueOf(id));
			}
		}
		
		WorksRABillDto savedDto = raBillService.saveAndUpdateRaBill(masDto, removeIds, getDeptId(),billTaxDetailsDtos);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
		if(!wmsDto.getYearDtos().isEmpty())
		{
			workDefinitionService.updateBudgetHead(wmsDto.getYearDtos().get(0).getYearId(), wmsDto.getYearDtos().get(0).getSacHeadId(), wmsDto.getYearDtos().get(0).getOrgId());
		}
		}

		if (savedDto.getRaStatus().equals(MainetConstants.FlagS)) {
			senForApprovalForRaBill(savedDto.getRaCode());
		}
		if (savedDto.getRaStatus().equals(MainetConstants.FlagS)) {
			setSuccessMessage(ApplicationSession.getInstance().getMessage("work.mb.Ra.creation") + " "
					+ ApplicationSession.getInstance().getMessage("wms.RABillNo") + " " + savedDto.getRaCode());
		} else {
			setSuccessMessage(ApplicationSession.getInstance().getMessage("work.mb.apprval.creation.draft"));
		}

		return true;
	}

	private void senForApprovalForRaBill(String raCode) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkflowMas workFlowMas = null;
		WorksRABillDto raBillDto = raBillService.getRABillDetailsByRaCode(raCode, orgId);
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.WorksManagement.MBA, orgId);
		try {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) ) {
				Long billTypeId=null;

				if (raBillDto.getRaBillType().equals("F")) {
					billTypeId = CommonMasterUtility.getLookUpFromPrefixLookUpValue("WFB", "ABT", 1,
							UserSession.getCurrent().getOrganisation()).getLookUpId();
				} else if (raBillDto.getRaBillType().equals("R")) {
					billTypeId = CommonMasterUtility.getLookUpFromPrefixLookUpValue("W", "ABT", 1,
							UserSession.getCurrent().getOrganisation()).getLookUpId();
				}

				LocOperationWZMappingDto wzMapping = locationMasService.findOperLocationAndDeptId(UserSession.getCurrent().getLoggedLocId(),sm.getTbDepartment().getDpDeptid());
				Long codIdOperLevel1 =null;
				Long codIdOperLevel2 =null;
				Long codIdOperLevel3= null;
				Long codIdOperLevel4= null;
				Long codIdOperLevel5= null;

				if(wzMapping !=null) {
					if(wzMapping.getCodIdOperLevel1()!=null) {
						codIdOperLevel1 = wzMapping.getCodIdOperLevel1();
					}
					if(wzMapping.getCodIdOperLevel2()!=null) {
						codIdOperLevel2 = wzMapping.getCodIdOperLevel2();
					}
					if(wzMapping.getCodIdOperLevel3()!=null) {
						codIdOperLevel3 = wzMapping.getCodIdOperLevel3();
					}
					if(wzMapping.getCodIdOperLevel4()!=null) {
						codIdOperLevel4 = wzMapping.getCodIdOperLevel4();
					}
					if(wzMapping.getCodIdOperLevel5()!=null) {
						codIdOperLevel5 = wzMapping.getCodIdOperLevel5();
					}

				}
				
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				workFlowMas = ApplicationContextProvider.getApplicationContext()
						.getBean(IWorkflowTyepResolverService.class).checkgetwmschcodeid2BasedWorkflowExist(
								UserSession.getCurrent().getOrganisation().getOrgid(),
								sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), billTypeId,
								codIdOperLevel1,null,codIdOperLevel3,
								codIdOperLevel4,codIdOperLevel5,raBillDto.getRaBillAmt());
				}
				else {
					workFlowMas = ApplicationContextProvider.getApplicationContext()
							.getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
									sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(),null,null,billTypeId, null, null, null, null, null);
			 
				}
				
			}
			else {
				workFlowMas = ApplicationContextProvider.getApplicationContext()
						.getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
								sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), null, null, null, null, null);
			}

			String flag = null;
			if (workFlowMas != null) {
				try {
					flag = ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
							.initiateWorkFlowWorksService(prepareWorkFlowTaskAction(raCode), workFlowMas,
									"WorkMBApproval.html", MainetConstants.FlagA);
				} catch (Exception exception) {
					raBillService.updateStatusByRaId(raBillDto.getRaId(), MainetConstants.FlagD);
					throw new FrameworkException("Exception Occured when Initiate Work Flow Methods", exception);
				}
				if (raBillDto != null && raBillDto.getRaId() != null && !flag.equals(MainetConstants.FAILURE_MSG)) {
					raBillService.updateStatusByRaId(raBillDto.getRaId(), MainetConstants.FlagP);
				} else {
					raBillService.updateStatusByRaId(raBillDto.getRaId(), MainetConstants.FlagD);
				}
			} else {
				raBillService.updateStatusByRaId(raBillDto.getRaId(), MainetConstants.FlagD);
			}
		} catch (Exception exception) {
			raBillService.updateStatusByRaId(raBillDto.getRaId(), MainetConstants.FlagD);
			throw new FrameworkException("Workflow Not Found");
		}

	}

	public WorkflowTaskAction prepareWorkFlowTaskAction(String raCode) {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setReferenceId(raCode);
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision("SUBMITED");
		return taskAction;

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemoveTaxDetIds() {
		return removeTaxDetIds;
	}

	public void setRemoveTaxDetIds(String removeTaxDetIds) {
		this.removeTaxDetIds = removeTaxDetIds;
	}

	public VendorBillApprovalDTO getApprovalDTO() {
		return approvalDTO;
	}

	public void setApprovalDTO(VendorBillApprovalDTO approvalDTO) {
		this.approvalDTO = approvalDTO;
	}

	public List<VendorBillExpDetailDTO> getExpDetListDto() {
		return expDetListDto;
	}

	public void setExpDetListDto(List<VendorBillExpDetailDTO> expDetListDto) {
		this.expDetListDto = expDetListDto;
	}

	public VendorBillExpDetailDTO getBillExpDetailDTO() {
		return billExpDetailDTO;
	}

	public void setBillExpDetailDTO(VendorBillExpDetailDTO billExpDetailDTO) {
		this.billExpDetailDTO = billExpDetailDTO;
	}

	public WorkOrderDto getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrderDto workOrder) {
		this.workOrder = workOrder;
	}

	public TenderWorkDto getTenderWorkDto() {
		return tenderWorkDto;
	}

	public void setTenderWorkDto(TenderWorkDto tenderWorkDto) {
		this.tenderWorkDto = tenderWorkDto;
	}

	public WorkDefinitionDto getDefinitionDto() {
		return definitionDto;
	}

	public void setDefinitionDto(WorkDefinitionDto definitionDto) {
		this.definitionDto = definitionDto;
	}

	public boolean isRaBill() {
		return raBill;
	}

	public void setRaBill(boolean raBill) {
		this.raBill = raBill;
	}

	public WmsProjectMasterDto getProjectMasterDto() {
		return projectMasterDto;
	}

	public void setProjectMasterDto(WmsProjectMasterDto projectMasterDto) {
		this.projectMasterDto = projectMasterDto;
	}

	public TbDepartment getDepartment() {
		return department;
	}

	public void setDepartment(TbDepartment department) {
		this.department = department;
	}

	public MeasurementBookMasterDto getMasterDto() {
		return masterDto;
	}

	public void setMasterDto(MeasurementBookMasterDto masterDto) {
		this.masterDto = masterDto;
	}

	public List<TbDepartment> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<TbDepartment> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public String getRaBillDateStr() {
		return raBillDateStr;
	}

	public void setRaBillDateStr(String raBillDateStr) {
		this.raBillDateStr = raBillDateStr;
	}

	public String getWorkBillNumber() {
		return workBillNumber;
	}

	public void setWorkBillNumber(String workBillNumber) {
		this.workBillNumber = workBillNumber;
	}

	public List<MeasurementBookMasterDto> getMeasureMentBookMastDtosList() {
		return measureMentBookMastDtosList;
	}

	public void setMeasureMentBookMastDtosList(List<MeasurementBookMasterDto> measureMentBookMastDtosList) {
		this.measureMentBookMastDtosList = measureMentBookMastDtosList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<TaxDefinationDto> getListTaxDefinationDto() {
		return listTaxDefinationDto;
	}

	public void setListTaxDefinationDto(List<TaxDefinationDto> listTaxDefinationDto) {
		this.listTaxDefinationDto = listTaxDefinationDto;
	}

	public String getApprovalMode() {
		return approvalMode;
	}

	public void setApprovalMode(String approvalMode) {
		this.approvalMode = approvalMode;
	}

	public String getRemoveTaxWithIds() {
		return removeTaxWithIds;
	}

	public void setRemoveTaxWithIds(String removeTaxWithIds) {
		this.removeTaxWithIds = removeTaxWithIds;
	}

}
