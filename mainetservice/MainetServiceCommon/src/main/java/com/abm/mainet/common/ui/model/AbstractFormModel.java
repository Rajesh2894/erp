package com.abm.mainet.common.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public abstract class AbstractFormModel extends AbstractModel {

    private static final long serialVersionUID = -7620050348809883460L;

    private Employee agencyApplicantDetail = new Employee();

    private String agencyApplicantDetailViewMode;
    private String ownerName;
    private String prFlatNo;
    private String prPropertyNo;
    private String waterConnNO;
    private String serviceFlag;
    private Long serviceId;
    private String serviceName;
    private String encrptAmount;
    private String chargesAmount;
    private Double saveamountValue;
    private long saveNoofCopies;
    private int rowCount = 0;
    private String processStatus;
    private CommonChallanDTO offlineDTO = new CommonChallanDTO();
    private WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();
    private Set<LookUp> workflowEventEmpList = new HashSet<>();
    private Map<String, String> checkActMap = new HashMap<>();
    private List<DocumentDetailsVO> commonFileAttachment = new ArrayList<>();
    private String serviceCode;
    private String refNo = "";
    private PaymentRequestDTO paymentReqDto=new PaymentRequestDTO();
    
	private ChallanReceiptPrintDTO receiptDTO = null;

    /*-----------   New Added for checklist and charges----*/

    private List<ChargeDetailDTO> chargesInfo;
    private String customViewName;

    /*---- New added for #34143 ----*/
    private Long orgId;

    private ChecklistStatusView applicationDetails;
    private long currentServiceId;
    private List<CFCAttachment> attachmentList = new ArrayList<>(0);
    private boolean newApplication;
    private String[] listOfChkboxStatus;
    private List<LookUp> documentsList = new ArrayList<>();
    private String path;
    private String checkListVrfyFlag;
    private int fileSize;
    private String actionUrl;
    @Resource
    private IFileUploadService fileUpload;
    @Autowired
    private IChecklistSearchService checklistSearchService;
    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;
    @Autowired
    private ServiceMasterService serviceMaster;
    //#112656
    private CFCSchedulingCounterDet cfcSchedulingCounterDet = new CFCSchedulingCounterDet();
    
    private String departmentCode;
    
    private String assType; // Assessment Type: MUT-Mutation
    
    private String illegal;

    public CFCSchedulingCounterDet getCfcSchedulingCounterDet() {
		return cfcSchedulingCounterDet;
	}

	public void setCfcSchedulingCounterDet(CFCSchedulingCounterDet cfcSchedulingCounterDet) {
		this.cfcSchedulingCounterDet = cfcSchedulingCounterDet;
	}

	public void setDocumentsList(List<LookUp> documentsList) {
        this.documentsList = documentsList;
    }

    public List<LookUp> getDocumentsList() {
        final List<LookUp> documentDetailsList = new ArrayList<>(0);
        setPath(attachmentList.get(0).getAttPath());
        LookUp lookUp = null;
        for (final CFCAttachment temp : attachmentList) {
            lookUp = new LookUp(temp.getAttId(), temp.getAttPath());
            lookUp.setDescLangFirst(temp.getClmDescEngl());
            lookUp.setDescLangSecond(temp.getClmDesc());
			if (temp.getClmId() != null)
				lookUp.setLookUpId(temp.getClmId());
            lookUp.setDefaultVal(temp.getAttPath());
            lookUp.setLookUpCode(temp.getAttFname());
            lookUp.setLookUpType(temp.getDmsDocId());
            lookUp.setLookUpParentId(temp.getAttId());
            lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
            lookUp.setOtherField(temp.getMandatory());
            lookUp.setExtraStringField1(temp.getClmRemark());// reject message
            documentDetailsList.add(lookUp);
        }
        fileSize = 0;
        if (!documentDetailsList.isEmpty()) {
            fileSize = documentDetailsList.size();
        }
        return documentDetailsList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCheckListVrfyFlag() {
        return checkListVrfyFlag;
    }

    public void setCheckListVrfyFlag(String checkListVrfyFlag) {
        this.checkListVrfyFlag = checkListVrfyFlag;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public void initializeApplicantDetail() {

    }

    public boolean saveForm() {
        return false;
    }

    public void initializeAgencyApplicantDetail() {

        final String loggedIn = getLoggedInUserType();
        if (((loggedIn != null) && loggedIn.equals(PrefixConstants.NEC.CITIZEN))
                || loggedIn.equals(PrefixConstants.NEC.ADVERTISE)) {

            getAgencyApplicantDetail().setCpdTtlId(UserSession.getCurrent().getEmployee().getCpdTtlId());
            getAgencyApplicantDetail().setEmpname(UserSession.getCurrent().getEmployee().getEmpname());
            getAgencyApplicantDetail().setEmpmname(UserSession.getCurrent().getEmployee().getEmpmname());
            getAgencyApplicantDetail().setEmplname(UserSession.getCurrent().getEmployee().getEmplname());
            getAgencyApplicantDetail().setEmpmobno(UserSession.getCurrent().getEmployee().getEmpmobno());
            getAgencyApplicantDetail().setEmpemail(UserSession.getCurrent().getEmployee().getEmpemail());
        }

    }

    public Employee getAgencyApplicantDetail() {
        return agencyApplicantDetail;
    }

    public void setAgencyApplicantDetail(final Employee agencyApplicantDetail) {
        this.agencyApplicantDetail = agencyApplicantDetail;
    }

    public String getAgencyApplicantDetailViewMode() {
        return agencyApplicantDetailViewMode;
    }

    public void setAgencyApplicantDetailViewMode(final String agencyApplicantDetailViewMode) {
        this.agencyApplicantDetailViewMode = agencyApplicantDetailViewMode;
    }

    /**
     * @return the saveamountValue
     */
    public Double getSaveamountValue() {
        return saveamountValue;
    }

    /**
     * @param saveamountValue the saveamountValue to set
     */
    public void setSaveamountValue(final Double saveamountValue) {
        this.saveamountValue = saveamountValue;
    }

    /**
     * @return the saveNoofCopies
     */
    public long getSaveNoofCopies() {
        return saveNoofCopies;
    }

    /**
     * @param saveNoofCopies the saveNoofCopies to set
     */
    public void setSaveNoofCopies(final long saveNoofCopies) {
        this.saveNoofCopies = saveNoofCopies;
    }

    public <T extends Object> void loadQualifire(final Class<T> classz) {

        final String className = classz.getSimpleName();

        getBeanNameGeneratedFromClassName(className);

    }

    private String getBeanNameGeneratedFromClassName(final String className) {
        final String init = className.charAt(0) + MainetConstants.BLANK;

        return className.replaceFirst(className.charAt(0) + MainetConstants.BLANK, init.toLowerCase());
    }

    /*
     * public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PayURequestDTO payURequestDTO) { }
     */

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void editForm(final long rowId) {

        fileUpload.sessionCleanUpForFileUpload();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        applicationDetails = checklistSearchService.getCheckListDataByApplication(orgId, rowId);
        if(applicationDetails.getSmServiceId()!=null) { //110157 By Arun
        	setCurrentServiceId(applicationDetails.getSmServiceId());
        }
        if (MainetConstants.ApplicationStatus.RESUBMIT.equals(applicationDetails.getApmChklstVrfyFlag())) {
            setAttachmentList(iChecklistVerificationService.getAttachDocumentByDocumentStatus(rowId,
                    MainetConstants.BLANK, orgId));
        } else {
            setAttachmentList(iChecklistVerificationService.findAttachmentsForAppId(rowId, null,
                    UserSession.getCurrent().getOrganisation().getOrgid()));
        }

        if (!getAttachmentList().isEmpty()) {
            newApplication = ((null == applicationDetails.getApmChklstVrfyFlag())
                    || MainetConstants.ApplicationStatus.RESUBMIT.equals(applicationDetails.getApmChklstVrfyFlag())
                    || MainetConstants.ApplicationStatus.PENDING.equals(applicationDetails.getApmChklstVrfyFlag())
                            ? true
                            : false);
        }
        listOfChkboxStatus = new String[getAttachmentList().size()];
    }

    public void checkListDecentralized(Long rowId, Long serviceId, Long orgId) {
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        String checkListRequiredFlag = service.getSmCheckListReq();
        if (StringUtils.equals(checkListRequiredFlag, MainetConstants.FlagN)) {
            editForm(rowId);
        }
    }

    public String getPrFlatNo() {
        return prFlatNo;
    }

    public void setPrFlatNo(final String prFlatNo) {
        this.prFlatNo = prFlatNo;
    }

    public String getWaterConnNO() {
        return waterConnNO;
    }

    public void setWaterConnNO(final String waterConnNO) {
        this.waterConnNO = waterConnNO;
    }

    public String getPrPropertyNo() {
        return prPropertyNo;
    }

    public void setPrPropertyNo(final String prPropertyNo) {
        this.prPropertyNo = prPropertyNo;
    }

    public String getServiceFlag() {
        return serviceFlag;
    }

    public void setServiceFlag(final String serviceFlag) {
        this.serviceFlag = serviceFlag;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(final int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * @return the encrptAmount
     */
    public String getEncrptAmount() {
        return encrptAmount;
    }

    /**
     * @param encrptAmount the encrptAmount to set
     */
    public void setEncrptAmount(final String encrptAmount) {
        this.encrptAmount = encrptAmount;
    }

    public String getChargesAmount() {
        return chargesAmount;
    }

    public void setChargesAmount(final String chargesAmount) {
        this.chargesAmount = chargesAmount;
    }

    public CommonChallanDTO getOfflineDTO() {
        return offlineDTO;
    }

    public void setOfflineDTO(final CommonChallanDTO offlineDTO) {
        this.offlineDTO = offlineDTO;
    }

    public boolean getOfflinePay() {
        if ((getOfflineDTO().getOnlineOfflineCheck() != null)
                && getOfflineDTO().getOnlineOfflineCheck().equalsIgnoreCase(MainetConstants.MENU.N)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getBank() {
        if (getOfflineDTO().getOflPaymentMode() != 0) {
            processStatus = getNonHierarchicalLookUpObject(getOfflineDTO().getOflPaymentMode()).getLookUpCode();
            if (processStatus.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PCB_MODE)) {
                return true;
            }
        }
        return false;
    }

    public boolean getUlb() {
        if (getOfflineDTO().getOflPaymentMode() != 0) {
            processStatus = getNonHierarchicalLookUpObject(getOfflineDTO().getOflPaymentMode()).getLookUpCode();
            if (processStatus.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PCU_MODE)) {
                return true;
            }
        }
        return false;
    }

    public boolean getDd() {
        if (getOfflineDTO().getOflPaymentMode() != 0) {
            processStatus = getNonHierarchicalLookUpObject(getOfflineDTO().getOflPaymentMode()).getLookUpCode();
            if (processStatus.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PDD_MODE)) {
                return true;
            }
        }
        return false;
    }

    public boolean getPostal() {
        if (getOfflineDTO().getOflPaymentMode() != 0) {
            processStatus = getNonHierarchicalLookUpObject(getOfflineDTO().getOflPaymentMode()).getLookUpCode();
            if (processStatus.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PPO_MODE)) {
                return true;
            }
        }
        return false;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(final String processStatus) {
        this.processStatus = processStatus;
    }

    public List<ChargeDetailDTO> getChargesInfo() {
        return chargesInfo;
    }

    public void setChargesInfo(final List<ChargeDetailDTO> chargesInfo) {
        this.chargesInfo = chargesInfo;
    }

    public String getCustomViewName() {
        return customViewName;
    }

    public void setCustomViewName(final String customViewName) {
        this.customViewName = customViewName;
    }

    public ChallanReceiptPrintDTO getReceiptDTO() {
        return receiptDTO;
    }

    public void setReceiptDTO(final ChallanReceiptPrintDTO receiptDTO) {
        this.receiptDTO = receiptDTO;
    }

    public WorkflowTaskAction getWorkflowActionDto() {
        return workflowActionDto;
    }

    public void setWorkflowActionDto(WorkflowTaskAction workflowActionDto) {
        this.workflowActionDto = workflowActionDto;
    }

    public Set<LookUp> getWorkflowEventEmpList() {
        return workflowEventEmpList;
    }

    public void setWorkflowEventEmpList(Set<LookUp> workflowEventEmpList) {
        this.workflowEventEmpList = workflowEventEmpList;
    }

    public Map<String, String> getCheckActMap() {
        return checkActMap;
    }

    public void setCheckActMap(Map<String, String> checkActMap) {
        this.checkActMap = checkActMap;
    }

    public List<DocumentDetailsVO> getCommonFileAttachment() {
        return commonFileAttachment;
    }

    public void setCommonFileAttachment(List<DocumentDetailsVO> commonFileAttachment) {
        this.commonFileAttachment = commonFileAttachment;
    }

    public ChecklistStatusView getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(ChecklistStatusView applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public long getCurrentServiceId() {
        return currentServiceId;
    }

    public void setCurrentServiceId(long currentServiceId) {
        this.currentServiceId = currentServiceId;
    }

    public List<CFCAttachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<CFCAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public boolean isNewApplication() {
        return newApplication;
    }

    public void setNewApplication(boolean newApplication) {
        this.newApplication = newApplication;
    }

    public String[] getListOfChkboxStatus() {
        return listOfChkboxStatus;
    }

    public void setListOfChkboxStatus(String[] listOfChkboxStatus) {
        this.listOfChkboxStatus = listOfChkboxStatus;
    }

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getAssType() {
		return assType;
	}

	public void setAssType(String assType) {
		this.assType = assType;
	}

	public String getIllegal() {
		return illegal;
	}

	public void setIllegal(String illegal) {
		this.illegal = illegal;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	

	public PaymentRequestDTO getPaymentReqDto() {
		return paymentReqDto;
	}

	public void setPaymentReqDto(PaymentRequestDTO paymentReqDto) {
		this.paymentReqDto = paymentReqDto;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	 public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
	            final PaymentRequestDTO paymentRequestDTO) {

	    }
}
