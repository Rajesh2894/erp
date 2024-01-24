package com.abm.mainet.council.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.service.ICouncilProposalMasterService;

/**
 * @author aarti.paan
 * @since 6th May 2019
 */
@Component
@Scope("session")
public class CouncilProposalMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = -6270033092206912011L;
    private String saveMode;
    private CouncilProposalMasterDto couProposalMasterDto = new CouncilProposalMasterDto();
    private List<TbDepartment> departmentsList;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private Long deleteFileId;
    private List<CouncilProposalMasterDto> couProposalMasterDtoList = new ArrayList<>();
    List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
    private String proposalNumber;
    private Long proposalDepId;
    private String proposalDeptName;
    private List<TbFinancialyear> faYears = new ArrayList<>();
    private String cpdMode;
    private BigDecimal invoiceAmount;
    private List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
    private List<DocumentDetailsVO> documentDtos = new ArrayList<>();
    private String removeYearIds;
    List<AccountFundMasterBean> fundList =new ArrayList<>();
    Map<Long, String> fieldList = new HashMap<>();
    private String proposalPurpose;
    private String proposalDetails;
    private String removeFileById;
    private String resolutionComments;
    
    
    @Autowired
    private IFileUploadService fileUpload;

	@Override
    public boolean saveForm() {
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			if (!Objects.isNull(proposalPurpose)) {
				couProposalMasterDto.setPurposeremark(proposalPurpose);
			}
		}

    	if(!saveMode.equals("E")) {
        if (couProposalMasterDto.getCreatedBy() != null) {
        	
            // add proposal case
            // check attachDocList is empty or not if empty than do validation by other way
            if (attachDocsList.isEmpty()) {
                addValidationError(ApplicationSession.getInstance().getMessage("council.proposal.validation.document"));
                return false;
            }
        } else {
            // below code check for document validation in case of add
            if (!checkDocumentList()) {
                return false;
            }
        }
    	}
    	 removeUploadedFiles(this.getRemoveFileById());
    	 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
    	 if (StringUtils.isNotEmpty(resolutionComments))
	            this.getCouProposalMasterDto().setProposalDetails(resolutionComments);
    	 }
        FileUploadDTO uploadDTO = new FileUploadDTO();
        uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        uploadDTO.setStatus(MainetConstants.FlagA);
        uploadDTO.setDepartmentName(MainetConstants.Council.COUNCIL_MANAGEMENT);
        uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        couProposalMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        
        List<Long> removeYearIdList = getRemovedYearIdAsList();

        // checking amount is available against Head Code
        int chcekAmt = 0;
        if (cpdMode.equals('L')) {
            VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
            budgetHeadDTO.setBillAmount(couProposalMasterDto.getProposalAmt());
            budgetHeadDTO.setDepartmentId(couProposalMasterDto.getProposalDepId());
            budgetHeadDTO.setFaYearid(couProposalMasterDto.getYearId());
            budgetHeadDTO.setBudgetCodeId(couProposalMasterDto.getSacHeadId());
            budgetHeadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            VendorBillApprovalDTO dto = ApplicationContextProvider.getApplicationContext()
                    .getBean(ICouncilProposalMasterService.class).getBudgetExpenditureDetails(budgetHeadDTO);
            setInvoiceAmount(dto.getInvoiceAmount());
            chcekAmt = invoiceAmount.compareTo(couProposalMasterDto.getProposalAmt());
        }
        if (chcekAmt != -1) {
            if (couProposalMasterDto.getCreatedBy() == null) {
                couProposalMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                couProposalMasterDto.setCreatedDate(new Date());
                couProposalMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                ApplicationContextProvider.getApplicationContext().getBean(ICouncilProposalMasterService.class).saveCouncil(
                        couProposalMasterDto, getAttachments(),
                        uploadDTO, null,removeYearIdList);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("council.proposal.savesuccessmsg")+couProposalMasterDto.getProposalNo());
            } else {
                couProposalMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                couProposalMasterDto.setUpdatedDate(new Date());
                couProposalMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                ApplicationContextProvider.getApplicationContext().getBean(ICouncilProposalMasterService.class).saveCouncil(
                        couProposalMasterDto,
                        getAttachments(), uploadDTO, deleteFileId,removeYearIdList);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("council.proposal.updatesuccessmsg"));
            }
        } else {
            addValidationError(getAppSession().getMessage("not.sufficient.amount"));
            return false;
        }
        return true;
    }

    // set all relevant Work flow Task Action Data For initiating Work Flow
    public WorkflowTaskAction prepareWorkFlowTaskAction() {
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
        taskAction.setReferenceId(this.getProposalNumber());
        taskAction.setPaymentMode(MainetConstants.FlagF);
        taskAction.setDecision(MainetConstants.Council.Proposal.SET_DECISION_SUBMITTED);
        return taskAction;
    }

    // Validation for File Upload
    public boolean checkDocumentList() {
        boolean flag = true;
        final List<DocumentDetailsVO> docList = fileUpload.prepareFileUpload(getAttachments());
        if ((docList != null) && !docList.isEmpty()) {
            for (final DocumentDetailsVO doc : docList) {
                if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
                    addValidationError(
                            ApplicationSession.getInstance().getMessage("council.proposal.validation.document"));
                    flag = false;
                }
            }
        }
        return flag;
    }
    
    private void removeUploadedFiles(String removeFileByIds) {		
		List<Long> removeFileById = null;
		String fileId =this.getRemoveFileById();
		if (fileId != null && !fileId.isEmpty()) {
			removeFileById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeFileById.add(Long.valueOf(fields));
			}
		}
		if (removeFileById != null && !removeFileById.isEmpty()) {				
			ApplicationContextProvider.getApplicationContext()
            .getBean(ICouncilProposalMasterService.class).updateUploadedFileDeleteRecords(removeFileById, UserSession.getCurrent().getEmployee().getEmpId());
		}
	}
    
    private List<Long> getRemovedYearIdAsList() {
        List<Long> removeYearIdList = null;
        String yearIds = getRemoveYearIds();
        if (yearIds != null && !yearIds.isEmpty()) {
            removeYearIdList = new ArrayList<>();
            String yearArray[] = yearIds.split(MainetConstants.operator.COMMA);
            for (String yearId : yearArray) {
                removeYearIdList.add(Long.valueOf(yearId));
            }
        }
        return removeYearIdList;
    }

    public CouncilProposalMasterDto getCouProposalMasterDto() {
        return couProposalMasterDto;
    }

    public void setCouProposalMasterDto(CouncilProposalMasterDto couProposalMasterDto) {
        this.couProposalMasterDto = couProposalMasterDto;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public List<LookUp> getLookupListLevel1() {
        return lookupListLevel1;
    }

    public void setLookupListLevel1(List<LookUp> lookupListLevel1) {
        this.lookupListLevel1 = lookupListLevel1;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public Long getDeleteFileId() {
        return deleteFileId;
    }

    public void setDeleteFileId(Long deleteFileId) {
        this.deleteFileId = deleteFileId;
    }

    public List<CouncilProposalMasterDto> getCouProposalMasterDtoList() {
        return couProposalMasterDtoList;
    }

    public void setCouProposalMasterDtoList(List<CouncilProposalMasterDto> couProposalMasterDtoList) {
        this.couProposalMasterDtoList = couProposalMasterDtoList;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public Long getProposalDepId() {
        return proposalDepId;
    }

    public void setProposalDepId(Long proposalDepId) {
        this.proposalDepId = proposalDepId;
    }

    public String getProposalDeptName() {
        return proposalDeptName;
    }

    public void setProposalDeptName(String proposalDeptName) {
        this.proposalDeptName = proposalDeptName;
    }

    public List<TbFinancialyear> getFaYears() {
        return faYears;
    }

    public void setFaYears(List<TbFinancialyear> faYears) {
        this.faYears = faYears;
    }

    public String getCpdMode() {
        return cpdMode;
    }

    public void setCpdMode(String cpdMode) {
        this.cpdMode = cpdMode;
    }

    public List<AccountHeadSecondaryAccountCodeMasterEntity> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList) {
        this.budgetList = budgetList;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

	public List<DocumentDetailsVO> getDocumentDtos() {
		return documentDtos;
	}

	public void setDocumentDtos(List<DocumentDetailsVO> documentDtos) {
		this.documentDtos = documentDtos;
	}

	public String getRemoveYearIds() {
		return removeYearIds;
	}

	public void setRemoveYearIds(String removeYearIds) {
		this.removeYearIds = removeYearIds;
	}

	public List<AccountFundMasterBean> getFundList() {
		return fundList;
	}

	public void setFundList(List<AccountFundMasterBean> fundList) {
		this.fundList = fundList;
	}

	public Map<Long, String> getFieldList() {
		return fieldList;
	}

	public void setFieldList(Map<Long, String> fieldList) {
		this.fieldList = fieldList;
	}

	public String getProposalPurpose() {
		return proposalPurpose;
	}

	public void setProposalPurpose(String proposalPurpose) {
		this.proposalPurpose = proposalPurpose;
	}

	public String getProposalDetails() {
		return proposalDetails;
	}

	public void setProposalDetails(String proposalDetails) {
		this.proposalDetails = proposalDetails;
	}

	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}
	
	

}
