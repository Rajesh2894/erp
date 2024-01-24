package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.VigilanceDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.abm.mainet.workManagement.service.VigilanceService;

/**
 * @author vishwajeet.kumar
 * @since 23 May 2018
 */

@Component
@Scope("session")
public class WorkVigilanceModel extends AbstractFormModel {

    private static final long serialVersionUID = 6714040329291197202L;

    @Autowired
    private VigilanceService vigilanceService;
    
   
    
    @Autowired
    public IFileUploadService fileUpload;
    private String saveMode;
    private Long parentOrgId;
    private String removeFileById;
    private String referenceDetailsById;
    private List<Long> fileCountUpload;
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    List<WorksRABillDto> worksrabilldto=new ArrayList<>();
    private  List<WmsProjectMasterDto> wmsprojectDto=new ArrayList<>();
    private WmsProjectMasterDto wmsProjectMasterDto=new WmsProjectMasterDto();
    public List<WorkDefinitionDto> workDefinitionDto = new ArrayList();
    private List<MeasurementBookMasterDto> measureMentBookMastDtosList = new ArrayList<>();
    private List<ScheduleOfRateDetDto> sorDetailsList = new ArrayList<>();
    private List<ScheduleOfRateMstDto> scheduleOfrateMstList;
    private String estimateMode;
    private WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();
    private List<DocumentDetailsVO> inspectionAttachment = new ArrayList<>();
    List<Object[]> employeeList = new ArrayList<>();
    private VigilanceDto vigilanceDto;
    private WorkOrderDto workOrderDto;
    private Long sorCommonId;
	private List<ContractAgreementSummaryDTO> summaryDTOList = new ArrayList<>();
    private ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
   private TenderMasterDto tendermasterdto;
   private List<WorkOrderDto> workOrderDtoList = new ArrayList<>();
   private Date contractFromDate;
   private Date contractToDate;
   private Long workMbId;
    @Override
    public boolean saveForm() {
        
        Boolean status = true;
        getVigilanceDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getAttachments();
        setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
       

        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }

        if (getSaveMode().equals(MainetConstants.WorksManagement.ADD)) {

            vigilanceDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            vigilanceDto.setCreatedDate(new Date());
            vigilanceDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            setSorCommonId(vigilanceDto.getWorkId());
                vigilanceService.saveVigilance(vigilanceDto, requestDTO, getAttachments());
          
            setSuccessMessage(getAppSession().getMessage("work.Vigilance.creation.successfull"));
        } else {

            List<Long> removeFileId = removeFileByIdAsList();
            vigilanceDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            vigilanceDto.setUpdatedDate(new Date());
            vigilanceDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            setSorCommonId(vigilanceDto.getWorkId());
              //Defect #92671
            if(vigilanceDto.getStatus().equalsIgnoreCase("N")) {
            	vigilanceDto.setInspectionDate(null);
            }
            vigilanceService.updateVigilance(vigilanceDto, getAttachments(), requestDTO, removeFileId);

            setSuccessMessage(getAppSession().getMessage("work.Vigilance.updation.successfull"));
        }

        return status;

    }
 

    // Deleted File Documents with the help of Id's
    private List<Long> removeFileByIdAsList() {
        List<Long> removeFileIdList = null;
        String fileIdList = getRemoveFileById();
        if (fileIdList != null && !fileIdList.isEmpty()) {
            removeFileIdList = new ArrayList<>();
            String fileArray[] = fileIdList.split(MainetConstants.operator.COMMA);
            for (String fileId : fileArray) {
                removeFileIdList.add(Long.valueOf(fileId));
            }
        }
        return removeFileIdList;
    }

    public void prepareSaveResponseData(VigilanceDto vigilanceUpdatedDto) {

       

    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getRemoveFileById() {
        return removeFileById;
    }

    public void setRemoveFileById(String removeFileById) {
        this.removeFileById = removeFileById;
    }

    public String getReferenceDetailsById() {
        return referenceDetailsById;
    }

    public void setReferenceDetailsById(String referenceDetailsById) {
        this.referenceDetailsById = referenceDetailsById;
    }

    public List<Long> getFileCountUpload() {
        return fileCountUpload;
    }

    public void setFileCountUpload(List<Long> fileCountUpload) {
        this.fileCountUpload = fileCountUpload;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<Object[]> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Object[]> employeeList) {
        this.employeeList = employeeList;
    }

  

	public List<WmsProjectMasterDto> getWmsprojectDto() {
        return wmsprojectDto;
    }


    public void setWmsprojectDto(List<WmsProjectMasterDto> wmsprojectDto) {
        this.wmsprojectDto = wmsprojectDto;
    }


    public VigilanceDto getVigilanceDto() {
        return vigilanceDto;
    }

    public List<WorkDefinitionDto> getWorkDefinitionDto() {
		return workDefinitionDto;
	}

	public void setWorkDefinitionDto(List<WorkDefinitionDto> workDefinitionDto) {
		this.workDefinitionDto = workDefinitionDto;
	}

	public void setVigilanceDto(VigilanceDto vigilanceDto) {
        this.vigilanceDto = vigilanceDto;
    }

	public VigilanceService getVigilanceService() {
		return vigilanceService;
	}

	public void setVigilanceService(VigilanceService vigilanceService) {
		this.vigilanceService = vigilanceService;
	}

	public List<MeasurementBookMasterDto> getMeasureMentBookMastDtosList() {
		return measureMentBookMastDtosList;
	}

	public void setMeasureMentBookMastDtosList(List<MeasurementBookMasterDto> measureMentBookMastDtosList) {
		this.measureMentBookMastDtosList = measureMentBookMastDtosList;
	}

	public WorkflowTaskAction getWorkflowActionDto() {
		return workflowActionDto;
	}

	public void setWorkflowActionDto(WorkflowTaskAction workflowActionDto) {
		this.workflowActionDto = workflowActionDto;
	}

	public List<WorksRABillDto> getWorksrabilldto() {
		return worksrabilldto;
	}

	public void setWorksrabilldto(List<WorksRABillDto> worksrabilldto) {
		this.worksrabilldto = worksrabilldto;
	}

    public String getEstimateMode() {
        return estimateMode;
    }

    public void setEstimateMode(String estimateMode) {
        this.estimateMode = estimateMode;
    }

    public Long getSorCommonId() {
        return sorCommonId;
    }

    public void setSorCommonId(Long sorCommonId) {
        this.sorCommonId = sorCommonId;
    }

    public List<ScheduleOfRateDetDto> getSorDetailsList() {
        return sorDetailsList;
    }

    public void setSorDetailsList(List<ScheduleOfRateDetDto> sorDetailsList) {
        this.sorDetailsList = sorDetailsList;
    }

    public List<ScheduleOfRateMstDto> getScheduleOfrateMstList() {
        return scheduleOfrateMstList;
    }

    public void setScheduleOfrateMstList(List<ScheduleOfRateMstDto> scheduleOfrateMstList) {
        this.scheduleOfrateMstList = scheduleOfrateMstList;
    }


    public Date getContractFromDate() {
		return contractFromDate;
	}


	public void setContractFromDate(Date contractFromDate) {
		this.contractFromDate = contractFromDate;
	}


	public Date getContractToDate() {
		return contractToDate;
	}


	public void setContractToDate(Date contractToDate) {
		this.contractToDate = contractToDate;
	}


	public List<DocumentDetailsVO> getInspectionAttachment() {
        return inspectionAttachment;
    }

    public void setInspectionAttachment(List<DocumentDetailsVO> inspectionAttachment) {
        this.inspectionAttachment = inspectionAttachment;
    }


    public WmsProjectMasterDto getWmsProjectMasterDto() {
        return wmsProjectMasterDto;
    }


    public void setWmsProjectMasterDto(WmsProjectMasterDto wmsProjectMasterDto) {
        this.wmsProjectMasterDto = wmsProjectMasterDto;
    }


    public WorkOrderDto getWorkOrderDto() {
        return workOrderDto;
    }


    public void setWorkOrderDto(WorkOrderDto workOrderDto) {
        this.workOrderDto = workOrderDto;
    }



	public ContractAgreementSummaryDTO getContractAgreementSummaryDTO() {
		return contractAgreementSummaryDTO;
	}


	public void setContractAgreementSummaryDTO(ContractAgreementSummaryDTO contractAgreementSummaryDTO) {
		this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
	}


	public List<ContractAgreementSummaryDTO> getSummaryDTOList() {
		return summaryDTOList;
	}


	public void setSummaryDTOList(List<ContractAgreementSummaryDTO> summaryDTOList) {
		this.summaryDTOList = summaryDTOList;
	}


	public TenderMasterDto getTendermasterdto() {
		return tendermasterdto;
	}


	public void setTendermasterdto(TenderMasterDto tendermasterdto) {
		this.tendermasterdto = tendermasterdto;
	}


	public List<WorkOrderDto> getWorkOrderDtoList() {
		return workOrderDtoList;
	}


	public void setWorkOrderDtoList(List<WorkOrderDto> workOrderDtoList) {
		this.workOrderDtoList = workOrderDtoList;
	}


	public Long getParentOrgId() {
		return parentOrgId;
	}


	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}


	public Long getWorkMbId() {
		return workMbId;
	}


	public void setWorkMbId(Long workMbId) {
		this.workMbId = workMbId;
	}


   

}
