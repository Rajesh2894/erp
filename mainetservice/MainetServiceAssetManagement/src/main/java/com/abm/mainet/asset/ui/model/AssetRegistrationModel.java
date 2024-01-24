/**
 * 
 */
package com.abm.mainet.asset.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.service.IAssetWorkflowService;
import com.abm.mainet.asset.service.IDocumentUploadService;
import com.abm.mainet.asset.service.IInformationService;
import com.abm.mainet.asset.service.IInsuranceService;
import com.abm.mainet.asset.service.IMaintenanceService;
import com.abm.mainet.asset.ui.dto.AssetFunctionalLocationDTO;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.DocumentDto;
import com.abm.mainet.asset.ui.dto.ITAssetRegisterBulkDTO;
import com.abm.mainet.asset.ui.dto.SearchDTO;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInsuranceDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLeasingCompanyDTO;
import com.abm.mainet.common.integration.asset.dto.AssetLinearDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetRealEstateInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetServiceInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AssetRegistrationModel extends AbstractFormModel {

    @Autowired
    private IFileUploadService assetFileUpload;
    @Resource
    private IDocumentUploadService documentUploadService;
    @Autowired
    private IMaintenanceService service;
    @Autowired
    private IInformationService infoService;
    @Resource
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;
    @Resource
    private ServiceMasterService iServiceMasterService;
    @Resource
    private TbDepartmentService iTbDepartmentService;

    @Resource
    private IAssetWorkflowService assetWorkFlowService;
    
    @Autowired
    private ILocationMasService locationMasService;

    /*
     * @Autowired private WorkflowExecutionService workflowExecutionService;
     */
    @Autowired
    public IFileUploadService fileUpload;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Resource
    private IChecklistVerificationService checkListService;
    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    IInsuranceService insuranceService;
    @Autowired
    private TbFinancialyearService financialyearService;

    /**
     * 
     */

    private static final long serialVersionUID = -5918148262100726938L;
    private SearchDTO astSearchDTO = new SearchDTO();
    private AssetDetailsDTO astDetailsDTO = new AssetDetailsDTO();
    private String modeType;
    private String subModeType;
    private List<TbDepartment> departmentsList;
    private List<TbAcVendormaster> vendorList;
    private List<LookUp> chartList;
    private String deleteByAtdId;
    private List<Long> fileCountUpload;
    // Retrive details
    private List<AssetFunctionalLocationDTO> funcLocDTOList;
    private AuditDetailsDTO auditDTO = new AuditDetailsDTO();
    private List<LookUp> location = new ArrayList<>(0);
    private List<LookUp> acHeadCode = new ArrayList<>(0);
    private List<TbLocationMas> locList = new ArrayList<>();
    private List<EmployeeBean> empList = new ArrayList<>();
    private String saveMode;
    private String appovalStatus;
    private String approvalViewFlag;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private Set<String> serailNoSet = new HashSet<>();
    private boolean deprApplicable;
    private String approvalProcess = "N";
    private String addInsuStatusFlag = "N";
    private String bindingStatus;
    private Integer index;
    private String checkTransaction;
    private Boolean accountIsActiveOrNot;
    private Long taskId;
    private String uploadFileName;
    private List<ITAssetRegisterBulkDTO> itAssetRegisterBulkDTO = new ArrayList<>();

	private String gisValue;
    private String gISUri;
    private String completedFlag;

    public Boolean getAccountIsActiveOrNot() {
        return accountIsActiveOrNot;
    }

    public void setAccountIsActiveOrNot(Boolean accountIsActiveOrNot) {
        this.accountIsActiveOrNot = accountIsActiveOrNot;
    }

    /**
     * @return the location
     */
    public List<LookUp> getLocation() {
        return location;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the bindingStatus
     */
    public String getBindingStatus() {
        return bindingStatus;
    }

    /**
     * @param bindingStatus the bindingStatus to set
     */
    public void setBindingStatus(String bindingStatus) {
        this.bindingStatus = bindingStatus;
    }

    public String getApprovalProcess() {
        return approvalProcess;
    }

    public void setApprovalProcess(String approvalProcess) {
        this.approvalProcess = approvalProcess;
    }

    public String getAddInsuStatusFlag() {
        return addInsuStatusFlag;
    }

    public void setAddInsuStatusFlag(String addInsuStatusFlag) {
        this.addInsuStatusFlag = addInsuStatusFlag;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(List<LookUp> location) {
        this.location = location;
    }

    public SearchDTO getAstSearchDTO() {
        return astSearchDTO;
    }

    public void setAstSearchDTO(SearchDTO astSearchDTO) {
        this.astSearchDTO = astSearchDTO;
    }

    public AssetDetailsDTO getAstDetailsDTO() {
        return astDetailsDTO;
    }

    public void setAstDetailsDTO(AssetDetailsDTO astDetailsDTO) {
        this.astDetailsDTO = astDetailsDTO;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public List<TbAcVendormaster> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<TbAcVendormaster> vendorList) {
        this.vendorList = vendorList;
    }

    public String getDeleteByAtdId() {
        return deleteByAtdId;
    }

    public void setDeleteByAtdId(String deleteByAtdId) {
        this.deleteByAtdId = deleteByAtdId;
    }

    public List<Long> getFileCountUpload() {
        return fileCountUpload;
    }

    public void setFileCountUpload(List<Long> fileCountUpload) {
        this.fileCountUpload = fileCountUpload;
    }

    public List<LookUp> getChartList() {
        return chartList;
    }

    public void setChartList(List<LookUp> chartList) {
        this.chartList = chartList;
    }

    public List<AssetFunctionalLocationDTO> getFuncLocDTOList() {
        return funcLocDTOList;
    }

    public void setFuncLocDTOList(List<AssetFunctionalLocationDTO> funcLocDTOList) {
        this.funcLocDTOList = funcLocDTOList;
    }

    public AuditDetailsDTO getAuditDTO() {
        return auditDTO;
    }

    public void setAuditDTO(AuditDetailsDTO auditDTO) {
        this.auditDTO = auditDTO;
    }

    public String getAppovalStatus() {
        return appovalStatus;
    }

    public void setAppovalStatus(String appovalStatus) {
        this.appovalStatus = appovalStatus;
    }

    public boolean isDeprApplicable() {
        return deprApplicable;
    }

    public void setDeprApplicable(boolean deprApplicable) {
        this.deprApplicable = deprApplicable;
    }

    /**
     * @return the acHeadCode
     */
    public List<LookUp> getAcHeadCode() {
        return acHeadCode;
    }

    /**
     * @return the locList
     */
    public List<TbLocationMas> getLocList() {
        return locList;
    }

    /**
     * @param locList the locList to set
     */
    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

    /**
     * @return the empList
     */
    public List<EmployeeBean> getEmpList() {
        return empList;
    }

    /**
     * @param empList the empList to set
     */
    public void setEmpList(List<EmployeeBean> empList) {
        this.empList = empList;
    }

    /**
     * @param acHeadCode the acHeadCode to set
     */
    public void setAcHeadCode(List<LookUp> acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getApprovalViewFlag() {
        return approvalViewFlag;
    }

    public void setApprovalViewFlag(String approvalViewFlag) {
        this.approvalViewFlag = approvalViewFlag;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    /**
     * @return the serailNoSet
     */
    public Set<String> getSerailNoSet() {
        return serailNoSet;
    }

    /**
     * @param serailNoSet the serailNoSet to set
     */
    public void setSerailNoSet(Set<String> serailNoSet) {
        this.serailNoSet = serailNoSet;
    }

    public String getSubModeType() {
        return subModeType;
    }

    public void setSubModeType(String subModeType) {
        this.subModeType = subModeType;
    }

    /**
     * @return the checkTransaction
     */
    public String getCheckTransaction() {
        return checkTransaction;
    }

    /**
     * @param checkTransaction the checkTransaction to set
     */
    public void setCheckTransaction(String checkTransaction) {
        this.checkTransaction = checkTransaction;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getGisValue() {
        return gisValue;
    }

    public void setGisValue(String gisValue) {
        this.gisValue = gisValue;
    }

    public String getgISUri() {
        return gISUri;
    }

    public void setgISUri(String gISUri) {
        this.gISUri = gISUri;
    }

    @Override
    public boolean saveForm() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        prepareFileUpload();
        astDetailsDTO.setOrgId(orgId);
        astDetailsDTO.setAuditDTO(auditDTO);
        String sendBackflag = null;
        if (this.getApprovalProcess().equals("SEND")) {
            sendBackflag = MainetConstants.FlagU;
            service.updateDetailDto(astDetailsDTO, auditDTO);
        } else {
            sendBackflag = MainetConstants.FlagA;
            service.registerDetailDto(astDetailsDTO);
        }
        // Used to initiate Workflow
        initiateWorkFlow(orgId, astDetailsDTO.getAssetInformationDTO().getAstCode(), null, null,
                astDetailsDTO.getAssetInformationDTO().getAssetId(), true, sendBackflag,null);
        // D#34059
        // update the urlParm like null because any pending tab not present
        infoService.updateUrlParamNullById(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId);
        FileUploadDTO requestDTO = new FileUploadDTO();
         requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
         requestDTO.setStatus(MainetConstants.FlagA);
         requestDTO.setIdfId("IAST" + MainetConstants.WINDOWS_SLASH +astDetailsDTO.getAssetInformationDTO().getAssetId());
         requestDTO.setDepartmentName("IAST");
         requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        // setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
         fileUpload.doMasterFileUpload(astDetailsDTO.getAttachments(), requestDTO);
       
        return true;

    }

    /**
     * @param astId
     * @return this method returns true if and only if document save otherwise that returns false
     */
    public void prepareFileUpload() {
        List<DocumentDetailsVO> documentDetailsVOList = astDetailsDTO.getAttachments();
        astDetailsDTO.setAttachments(assetFileUpload.prepareFileUpload(documentDetailsVOList));
    }

    public boolean isDuplicateName(final Long orgId, final String assetName) {
        boolean nameStatus = false;
        nameStatus = infoService.isDuplicateName(orgId, assetName);
        return nameStatus;
    }

    public boolean isDuplicateSerialNo(final Long orgId, final String serialNo, final Long assetId) {
        boolean serialNoStatus = false;
        serialNoStatus = infoService.isDuplicateSerialNo(orgId, serialNo, assetId);
        return serialNoStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.ui.model.AbstractModel#findPropertyPathPrefix(java.lang .String)
     */
    @Override
    protected String findPropertyPathPrefix(String parentCode) {
        String result = super.findPropertyPathPrefix(parentCode);
        switch (parentCode) {
        case "CLS":
            result = "astDetailsDTO.assetInformationDTO.assetClass";
            break;
        }
        return result;
    }
    //T#101107
    public void editITASSETPage(AssetDetailsDTO astDTO,Long orgId) {
        //changes for bulk update --> starts 
    	/* String successMsgRevId ="";
    	for(Long assetId : astDTO.getAssetIds()) {
    		 astDTO.getAssetInformationDTO().setAssetId(assetId); */
    		//changes for bulk update --> ends
    	
    	 Long astfnRevId = service.saveInformationRev(astDTO.getAssetInformationDTO().getAssetId(), astDTO.getAssetInformationDTO(), auditDTO);
    	 Long purchRevId = service.savePurchaseInformationRev(astDTO.getAssetInformationDTO().getAssetId(), astDTO.getAssetPurchaseInformationDTO(), auditDTO);
    	 String successMsgRevId = astfnRevId+"-"+purchRevId;
    	 if (astDTO.getAstSerList() != null && !astDTO.getAstSerList().isEmpty()) {
    		 if(UserSession.getCurrent().getModuleDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE) &&
    	        		getAstDetailsDTO().getAstSerList() != null &&
    	        		getAstDetailsDTO().getAstSerList().size() > 0) {
    	        	getAstDetailsDTO().setAstSerList(getAstDetailsDTO().getAstSerList().stream().filter(sl -> sl.getServiceProvider() != null).collect(Collectors.toList()));
    	        	
    	        }
    		 for(AssetServiceInformationDTO a : astDTO.getAstSerList()) {
    			 a.setEditFlag(this.getModeType());
    		 }    		
             Long serviceInfoRevId = service.saveServiceInformationRev(astDTO.getAssetInformationDTO().getAssetId(),  astDTO.getAstSerList(), auditDTO, orgId);
             if(serviceInfoRevId !=null){
            	 //framing again because service change may or may not come
            	 successMsgRevId = successMsgRevId+"-"+serviceInfoRevId;
             }          
           
             //changes for bulk update --> starts
    	// }
    	//changes for bulk update --> ends
    	 infoService.updateURLParam(astDetailsDTO.getAssetInformationDTO().getAssetId(),
                 UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.AssetManagement.SHOW_AST_INFO_Page);
    	 //commenting because of initially revId is Long in case of IT Asset handle the code 
    	// initiateWorkFlowReq(astDTO.getAssetInformationDTO(),MainetConstants.ITAssetManagement.IT_ASSET_CODE, successMsgRevId);
    	}
    	 initiateWorkFlow(orgId, astDTO.getAssetInformationDTO().getAstCode(), MainetConstants.ITAssetManagement.IT_ASSET_CODE, successMsgRevId, astDTO.getAssetInformationDTO().getAssetId(), false,  MainetConstants.FlagA,null);
    	 //changes for bulk update --> starts
    	 //initiateWorkFlow(orgId, astDTO.getAssetInformationDTO().getAstCode(), MainetConstants.ITAssetManagement.IT_ASSET_CODE, successMsgRevId, null, false,  MainetConstants.FlagA,null);
    	 //changes for bulk update --> ends
    }
    public void updateInformation(final Long assetId, final AssetInformationDTO infoDTO,
            final AuditDetailsDTO auditDTO) {
        // service.updateInformation(assetId, infoDTO, auditDTO);
        Long astfnRevId = service.saveInformationRev(assetId, infoDTO, auditDTO);
        updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_INFO_Page,
                MainetConstants.AssetManagement.AST_INFO_URL_CODE, astfnRevId);
        // updateWorkFlowDetails("showAstInfoPage");
    }

    public void updateClassification(final Long assetId, final AssetClassificationDTO classDTO,
            final AuditDetailsDTO auditDTO) {
        Long classfnId = null;
        if (astDetailsDTO.getAssetClassificationDTO().getAssetClassificationId() == null)
            classfnId = service.updateClassification(assetId, classDTO, auditDTO);
        else
            classfnId = classDTO.getAssetClassificationId();

        classDTO.setAssetClassificationId(classfnId);
        Long classfnRevId = service.saveClassificationRev(assetId, classDTO, auditDTO);
        updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_CLASS_Page,
                MainetConstants.AssetManagement.AST_CLASS_URL_CODE, classfnRevId);
    }

    private void initiateWorkFlowReq(AssetInformationDTO asstInfo, String urlShortCode, Long revId) {
        String assetCode = astDetailsDTO.getAssetInformationDTO().getAstCode();
        // Long astsId = astDetailsDTO.getAssetInformationDTO().getAssetId();
       
        initiateWorkFlow(UserSession.getCurrent().getOrganisation().getOrgid(), assetCode, urlShortCode, revId.toString(),
                astDetailsDTO.getAssetInformationDTO().getAssetId(), false, MainetConstants.FlagA,null);

    }

    public void updatePurchaseInformation(final Long assetId, final AssetPurchaseInformationDTO purchaseDTO,
            final AuditDetailsDTO auditDTO) {
        Long purchId = null;

        if (astDetailsDTO.getAssetPurchaseInformationDTO().getAssetPurchaserId() == null)
            purchId = service.updatePurchaseInformation(assetId, purchaseDTO, auditDTO);
        else
            purchId = purchaseDTO.getAssetPurchaserId();
        // setting puchaseId in dto
        purchaseDTO.setAssetPurchaserId(purchId);
        // Save purchase in database retriving its id
        Long purchRevId = service.savePurchaseInformationRev(assetId, purchaseDTO, auditDTO);
        updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_PRUCH_Page,
                MainetConstants.AssetManagement.AST_PURCH_URL_CODE, purchRevId);
        // updateWorkFlowDetails("showAstPurchPage");

    }

    public void updateRealEstateInformation(final Long assetId, final AssetRealEstateInformationDTO realEstateDTO,
            final AuditDetailsDTO auditDTO, final Long orgId) {
        Long astRealEstateId = null;

        if (astDetailsDTO.getAssetRealEstateInfoDTO().getAssetRealEstId() == null)
            service.updateRealEstateInformation(assetId, realEstateDTO, auditDTO);
        else
            astRealEstateId = realEstateDTO.getAssetRealEstId();

        realEstateDTO.setAssetId(assetId);
        realEstateDTO.setAssetRealEstId(astRealEstateId);
        Long realEstateRevId = service.saveRealStateInfoRev(assetId, realEstateDTO, auditDTO, orgId);
        updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_REAL_ESTATE_Page,
                MainetConstants.AssetManagement.AST_REAL_ESTATE_URL_CODE, realEstateRevId);

    }

    public void updateLeaseInformation(final Long assetId, final AssetLeasingCompanyDTO leaseDTO,
            final AuditDetailsDTO auditDTO) {
        Long leaseId = null;

        if (astDetailsDTO.getAstLeaseDTO().getAssetLeasingId() == null)
            leaseId = service.updateLeaseInformation(assetId, leaseDTO, auditDTO);
        else
            leaseId = leaseDTO.getAssetLeasingId();
        leaseDTO.setAssetLeasingId(leaseId);

        leaseId = service.saveLeaseInformationRev(assetId, leaseDTO, auditDTO);
        updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_LEASE_Page,
                MainetConstants.AssetManagement.AST_LEASE_URL_CODE, leaseId);
        // updateWorkFlowDetails("showAstLeasePage");

    }

    public void updateServiceInformation(final Long assetId, final List<AssetServiceInformationDTO> serviceDTOList, Long orgId,
            final AuditDetailsDTO auditDTO) {
        if (serviceDTOList != null && !serviceDTOList.isEmpty()) {
            Long serviceInfoRevId = service.saveServiceInformationRev(assetId, serviceDTOList, auditDTO, orgId);
            if (serviceInfoRevId != null) {
                // service.saveRealStateInfoRev(assetId,astDetailsDTO.getAssetRealEstateInfoDTO(), auditDTO, orgId);
                updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_SERVICE_Page,
                        MainetConstants.AssetManagement.AST_SERVICE_URL_CODE, serviceInfoRevId);
            }
        }

    }

    public void updateInsuranceDetails(final Long assetId, final AssetInsuranceDetailsDTO insuranceDTO,
            final AuditDetailsDTO auditDTO) {
        /*
         * Long squenceNo = null; List<AssetInsuranceDetailsDTO> assetInsuDTOSave = new ArrayList<AssetInsuranceDetailsDTO>();
         * squenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.AssetManagement.ASSETCODE, "TB_AST_INSURANCE_REV",
         * "REV_GRP_ID", UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagC, null); for(int
         * i=0;i<astDetailsDTO.getAstInsuDTOList().size();i++) { AssetInsuranceDetailsDTO astInsuObj =
         * astDetailsDTO.getAstInsuDTOList().get(i); astInsuObj.setAssetId(assetId); if(astInsuObj.getRevGrpIdentity()!=null &&
         * (astInsuObj.getRevGrpIdentity().equalsIgnoreCase("N") || astInsuObj.getRevGrpIdentity().equalsIgnoreCase("O"))) {
         * astInsuObj.setRevGrpId(squenceNo); assetInsuDTOSave.add(astInsuObj); // service.saveInsuranceDetailsRev(assetId,
         * astDetailsDTO.getAstInsuDTOList(), auditDTO); } }
         */

        // This method is used to call all
        Long groupId = service.updateInsuranceDetailsListRev(astDetailsDTO.getAstInsuDTOList(), auditDTO, assetId);
        updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_INSU_Page_DATATABLE,
                MainetConstants.AssetManagement.AST_INSU_URL_CODE, groupId);

        // updateWorkFlowDetails("showAstInsuPage");
    }

    /*
     * public void updateInsuranceDetails11(final Long assetId, final AssetInsuranceDetailsDTO insuranceDTO, final AuditDetailsDTO
     * auditDTO) { Long squenceNo = null; Long insuDetailsId = null; if (astDetailsDTO.getAstInsuDTO().getAssetInsuranceId() ==
     * null) { if(subModeType.equalsIgnoreCase("Add")) // insuDetailsId = service.saveInsuranceDetails(assetId, insuranceDTO,
     * auditDTO); astDetailsDTO.getAstInsuDTOList().add(insuranceDTO); else if(subModeType.equalsIgnoreCase("DataGrid")) { for(int
     * i=0;i<astDetailsDTO.getAstInsuDTOList().size();i++) { AssetInsuranceDetailsDTO astInsuObj =
     * astDetailsDTO.getAstInsuDTOList().get(i); if(astInsuObj.getRevGrpIdentity().equalsIgnoreCase("N")) insuDetailsId =
     * service.saveInsuranceDetails(assetId, insuranceDTO, auditDTO); } } else insuDetailsId =
     * service.updateInsuranceDetails(assetId, insuranceDTO, auditDTO); } else insuDetailsId = insuranceDTO.getAssetInsuranceId();
     * List<AssetInsuranceDetailsDTO> insuDTOList = service.getAllInsuranceDetailsList(assetId, insuranceDTO);
     * if(insuDTOList.size()>=0) { squenceNo = insuDTOList.get(0).getRevGrpId(); } else squenceNo =
     * seqGenFunctionUtility.generateSequenceNo(MainetConstants.AssetManagement.ASSETCODE, "TB_AST_INSURANCE_REV", "REV_GRP_ID",
     * UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagC, null);
     * insuranceDTO.setAssetInsuranceId(insuDetailsId); //Added just for insurance insuranceDTO.setRevGrpId(squenceNo);
     * insuranceDTO.setRevGrpIdentity("N"); Long insuDetailRevId = service.saveInsuranceDetailsRev(assetId, insuDTOList,
     * auditDTO); // updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_INSU_Page, insuDetailRevId);
     * updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_INSU_Page_DATATABLE, insuDetailRevId); //
     * updateWorkFlowDetails("showAstInsuPage"); }
     */
    public void addInsurDetails(final Long assetId, final AssetInsuranceDetailsDTO insuranceDTO,
            final AuditDetailsDTO auditDTO) {
        insuranceService.saveInsurance(insuranceDTO);
    }

    public void updateDepreciationChart(final Long assetId, final AssetDepreciationChartDTO chartDTO,
            final AuditDetailsDTO auditDTO) {
        Long depChartId = null;

        if (astDetailsDTO.getAstDepreChartDTO().getAssetDeprChartId() == null)
            depChartId = service.updateDepreciationChart(assetId, chartDTO, auditDTO);
        else
            depChartId = chartDTO.getAssetDeprChartId();
        chartDTO.setAssetDeprChartId(depChartId);
        Long depChartRevId = service.saveDepreciationChartRev(assetId, chartDTO, auditDTO);
        updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_DEPRE_CHART_Page,
                MainetConstants.AssetManagement.AST_DEPRE_CHART_URL_CODE, depChartRevId);

        // updateWorkFlowDetails("showAstDepreChartPage");
    }

    public void updateWorkFlowDetails(String urlParam, String urlShortCode, Long revId) {
        infoService.updateURLParam(astDetailsDTO.getAssetInformationDTO().getAssetId(),
                UserSession.getCurrent().getOrganisation().getOrgid(), urlParam);
        initiateWorkFlowReq(astDetailsDTO.getAssetInformationDTO(), urlShortCode, revId);
    }

    public void updateLinearInformation(final Long assetId, final AssetLinearDTO lineDTO,
            final AuditDetailsDTO auditDTO) {
        Long linearInfoId = null;

        if (astDetailsDTO.getAstLinearDTO().getAssetLinearId() == null)
            linearInfoId = service.updateLinearInformation(assetId, lineDTO, auditDTO);
        else
            linearInfoId = lineDTO.getAssetLinearId();
        lineDTO.setAssetLinearId(linearInfoId);

        Long linearInfoRevId = service.saveLinearInformationRev(assetId, lineDTO, auditDTO);
        updateWorkFlowDetails(MainetConstants.AssetManagement.SHOW_AST_LINEAR_Page,
                MainetConstants.AssetManagement.AST_LINEAR_URL_CODE, linearInfoRevId);

        // updateWorkFlowDetails("showAstLinePage");

    }

    public void updateDocumentDetails(Long assetId, Long orgId, final AuditDetailsDTO auditDTO, String deleteByAtdId,
            List<DocumentDetailsVO> attachments) {

        List<Long> deAtdIdList = null;
        String fileId = deleteByAtdId;
        if (fileId != null && !fileId.isEmpty()) {
            deAtdIdList = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                deAtdIdList.add(Long.valueOf(fields));
            }
        }
        DocumentDto docDto = new DocumentDto();
        docDto.setDocumentId(deAtdIdList);
        docDto.setDocumentAttached(attachments);
        service.updateDocumentDetails(assetId, orgId, auditDTO, docDto, UserSession.getCurrent().getModuleDeptCode());
    }

    /**
     * It is used to initiate workflow
     * 
     * @param orgId
     * @param serialNo
     * @param asstId
     */

    public void initiateWorkFlow(Long orgId, String assetCode, String urlShortCode, String revId, Long assetId, boolean isNew,
            String sendBackflag,String groupRefId) {
        // T#92467
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(UserSession.getCurrent().getModuleDeptCode(),
                MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_ASSET_REG_SERVICE_CODE);
        TbDepartment deptObj = iTbDepartmentService.findDeptByCode(orgId, MainetConstants.FlagA,
                data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY));
        ServiceMaster sm = iServiceMasterService
                .getServiceMasterByShortCode(data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY),
                        orgId);
        WorkflowMas workFlowMas = null;
        
        // Code related to work flow
        if((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && sm.getSmShortdesc().equalsIgnoreCase(MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE))) {
        	
        	Long workFlowLevel1 = null;
 			Long workFlowLevel2 = null;
 			Long workFlowLevel3 = null;
 			Long workFlowLevel4 = null;
 			Long workFlowLevel5 = null;
 			
 			
 			
 			
 			LocOperationWZMappingDto wzMapping = locationMasService.findOperLocationAndDeptId(astDetailsDTO.getAssetClassificationDTO().getLocation(),deptObj.getDpDeptid());
			if (wzMapping.getCodIdOperLevel1() == null) {

				String prefixName = iTbDepartmentService.findDepartmentPrefixName(deptObj.getDpDeptid(), orgId);
				if (prefixName == null || prefixName.isEmpty()) {
					TbDepartment deptObject = ApplicationContextProvider.getApplicationContext()
							.getBean(TbDepartmentService.class).findDeptByCode(orgId, MainetConstants.FlagA, "CFC");
					wzMapping = locationMasService.findOperLocationAndDeptId(
							astDetailsDTO.getAssetClassificationDTO().getLocation(), deptObject.getDpDeptid());
				}
			}
 			  if (wzMapping != null) {
 	                if (wzMapping.getCodIdOperLevel1() != null) {
 	                	workFlowLevel1=wzMapping.getCodIdOperLevel1();
 	                }
 	                if (wzMapping.getCodIdOperLevel2() != null) {
 	                	workFlowLevel2=wzMapping.getCodIdOperLevel2();
 	                }
 	                if (wzMapping.getCodIdOperLevel3() != null) {
 	                	workFlowLevel3=wzMapping.getCodIdOperLevel3();
 	                }
 	                if (wzMapping.getCodIdOperLevel4() != null) {
 	                	workFlowLevel4=wzMapping.getCodIdOperLevel4();
 	                }
 	                if (wzMapping.getCodIdOperLevel5() != null) {
 	                	workFlowLevel5=wzMapping.getCodIdOperLevel5();
 	                }
 	            }
 			 
 	           
 			
         workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
                     .checkgetwmschcodeid2BasedWorkflowExist(orgId, deptObj.getDpDeptid(),
                             sm.getSmServiceId(),astDetailsDTO.getAssetInformationDTO().getAssetClass2(), workFlowLevel1, workFlowLevel2, workFlowLevel3, workFlowLevel4, workFlowLevel5,null);
        }
        else {
        	  workFlowMas = iWorkflowTyepResolverService.resolveServiceWorkflowType(orgId, deptObj.getDpDeptid(),
                     sm.getSmServiceId(), null, null, null, null, null);
        }
       

        if (revId == null) {
            revId = "";
        }
        if (assetCode == null || assetCode.isEmpty()) {
            // assetCode = MainetConstants.AssetManagement.ASSETCODE;
            assetCode = data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY);
        }
        String astIdStr = null;
       
        if (isNew) {
            astIdStr = assetCode + "/" + MainetConstants.AssetManagement.NEW + "/" +  ((assetId == null) ? groupRefId : assetId);
        } else {
            // code changes by ISRAT
            // problem occur when workflow maker checker initiate because reference id made duplicate
            // below code generation is not correct
            // EX: in case of asset location tab edit than generate like code BHPB/B/2019-20/4/UPD/3
            // here 3 is revId which is also similar to another table(related to Asset Entry Tabs)
            // astIdStr = assetCode + "/" + MainetConstants.AssetManagement.UPDATE + "/" + revId;
            astIdStr = assetCode + "/" + MainetConstants.AssetManagement.UPDATE + "/" + urlShortCode + "/" + revId;
        }

        WorkflowTaskAction prepareWorkFlowTaskAction = null;
        Long taskId = null;
        if (sendBackflag.equals(MainetConstants.FlagU)) {
            prepareWorkFlowTaskAction = prepareWorkFlowTaskActionUpdate(getWorkflowActionDto());
            prepareWorkFlowTaskAction.setDecision(MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED);
            taskId = getTaskId();
        } else {
            prepareWorkFlowTaskAction = prepareWorkFlowTaskActionCreate(astIdStr);
            taskId = workFlowMas.getWfId();
            List<Long> attacheMentIds = checkListService.fetchAllAttachIdByReferenceId(
                    assetCode+"/"+assetId, UserSession.getCurrent().getOrganisation().getOrgid());
            prepareWorkFlowTaskAction.setAttachementId(attacheMentIds);
        }

        WorkflowTaskActionResponse response = assetWorkFlowService.initiateWorkFlowAssetService(
                prepareWorkFlowTaskAction,
                taskId, "AssetRegistration.html", sendBackflag, data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));

        // It updates the flag if and only if task is created in workflow
        if (response != null) {
            // pass astIdStr for astAppNo
        	if(assetId != null) {
        		 infoService.updateAppStatusFlag(assetId, orgId, MainetConstants.AssetManagement.APPROVAL_STATUS_PENDING, astIdStr);
                 // #36987
                
        	}else {
        		 infoService.updateGroupRefId(this.astDetailsDTO.getAssetIds(), this.astDetailsDTO.getAssetInformationDTO().getGroupRefId(), MainetConstants.AssetManagement.APPROVAL_STATUS_PENDING, orgId);
                 // #36987
                
        	}
            setSuccessMessage(getAppSession().getMessage("asset.registration.successMessage",
                    new Object[] { astIdStr }));

        }

    }

    public boolean updateApprovalFlag(Long orgId, String moduleDeptCode) {

        // validateBean(getWorkflowActionDto(), AssetApprovalValidator.class);

        /*
         * if (hasValidationErrors()) { return false; }
         */
        // T#92467
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(moduleDeptCode,
                MainetConstants.AssetManagement.ASSET_REG_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_ASSET_REG_SERVICE_CODE);

        ServiceMaster serviceMast = serviceMaster.getServiceMasterByShortCode(
                data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY),
                UserSession.getCurrent().getOrganisation().getOrgid());

        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        // requestDTO.setDepartmentName(MainetConstants.AssetManagement.ASSETCODE);
        requestDTO.setDepartmentName(data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY));
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDTO.setApplicationId(astDetailsDTO.getAssetInformationDTO().getAssetId());

        requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());

        // astDetailsDTO
        // fileUpload.doFileUpload(getAttachments(), requestDTO);
        this.attachments = setDocumentsDetailVO();

        setAttachments(fileUpload.prepareFileUpload(getAttachments()));

        fileUpload.doFileUpload(getAttachments(), requestDTO);

        List<Long> attacheMentIds = checkListService.fetchAllAttachIdByReferenceId(
                getWorkflowActionDto().getReferenceId(), UserSession.getCurrent().getOrganisation().getOrgid());
        getWorkflowActionDto().setAttachementId(attacheMentIds);

        WorkflowTaskAction workFlowTaskAction = getWorkflowActionDto();
        WorkflowTaskAction workflowActionDtoTmp = prepareWorkFlowTaskActionUpdate(workFlowTaskAction);
        WorkflowTaskActionResponse response = assetWorkFlowService.initiateWorkFlowAssetService(workflowActionDtoTmp, getTaskId(),
                "AssetRegistration.html", MainetConstants.FlagU, data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));

        if (response != null && workFlowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
                && !response.getIsProcessAlive()) {
            if (getAstDetailsDTO().getAssetInformationDTO().getUrlParam() != null) {
            	if(getAstDetailsDTO().getAssetInformationDTO().getDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
            		updateCorrespondingDetails(MainetConstants.ITAssetManagement.IT_ASSET_CODE);
            	}else {
            		updateCorrespondingDetails(getAstDetailsDTO().getAssetInformationDTO().getUrlParam());
            	}
                
            } else {
                /* Task #5318 */

                /*
                 * Long squenceNo = seqGenFunctionUtility.generateSequenceNo(
                 * data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY), "TB_AST_INFO_MST", "ASSET_CODE", orgId,
                 * MainetConstants.FlagC, null);
                 */
                // T#92467
                Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                        .getDepartmentIdByDeptCode(data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY),
                                MainetConstants.STATUS.ACTIVE);

                final Long squenceNo = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                        .getNumericSeqNo(deptId.toString(),
                                "TB_AST_INFO_MST", "ASSET_CODE", orgId, "CNT"/* continues */, deptId.toString(), 1L, 999999L);

                LookUp lookUpObj = getNonHierarchicalLookUpObject(astDetailsDTO.getAssetInformationDTO().getAssetClass2());
                String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
                Date acquiDate = getAstDetailsDTO().getAssetPurchaseInformationDTO().getDateOfAcquisition();
                FinancialYear financialYear = financialyearService.getFinanciaYearByDate(acquiDate);
                String faYear = Utility.getFinancialYear(financialYear.getFaFromDate(), financialYear.getFaToDate());
                String astCode = ulbName + "/" + lookUpObj.getLookUpCode() + "/" + faYear + "/" + squenceNo;
                
                //infoService.updateAstCode(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId, astCode);
                //Here if it is of single ItAssetUpload then setting the asset id to list othere wise we are iterating over for each loop
               if( astDetailsDTO.getAssetInformationDTO().getDeptCode().equals(MainetConstants.ITAssetManagement.IT_ASSET_CODE)){
            	  if(astDetailsDTO.getAssetIds() !=null && astDetailsDTO.getAssetIds().isEmpty()) {
            		  List<Long> assetIds = new ArrayList<Long>();
            		  assetIds.add( astDetailsDTO.getAssetInformationDTO().getAssetId());
            		  astDetailsDTO.setAssetIds(assetIds);
            	  }
				 for(Long assetId : astDetailsDTO.getAssetIds()) {
					 astDetailsDTO.getAssetInformationDTO().setAssetId(assetId);
					 infoService.updateAstCode(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId, astCode);
                     infoService.updateAssetCode(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId, astCode);
            	    infoService.updateURLParam(getAstDetailsDTO().getAssetInformationDTO().getAssetId(), UserSession.getCurrent().getOrganisation().getOrgid(),
				    MainetConstants.AssetManagement.SHOW_AST_INFO_Page);
            	    infoService.updateAppStatusFlag(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId,
                    MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, getWorkflowActionDto().getReferenceId());
				 }
               
              }else {
            	   infoService.updateAstCode(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId, astCode);
                   infoService.updateAssetCode(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId, astCode);
            	   
               }
               
               //changes for Bulk starts
                Date date = new Date();
                AssetValuationDetailsDTO valuationDTO = new AssetValuationDetailsDTO();
                service.addValuationEntry(astDetailsDTO, date, valuationDTO, astDetailsDTO.getAssetInformationDTO().getAssetId());
                // D#37802
                // set asset code in below attribute and get at AssetRegisterController inside method approvalDecision
                setSuccessMessage(astCode);
            }
            // pass getWorkflowActionDto().getReferenceId() for astAppNo
            return infoService.updateAppStatusFlag(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId,
                    MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, getWorkflowActionDto().getReferenceId());
        } else if (response != null && !response.getIsProcessAlive()
                && workFlowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)
                && getAstDetailsDTO().getAssetInformationDTO().getUrlParam() == null) {
            // when the status is reject it will update the asset status as a reject
            List<LookUp> lookUp = CommonMasterUtility.getLookUps("AST", UserSession.getCurrent().getOrganisation());
            lookUp = lookUp.stream().filter(l -> l != null && l.getLookUpCode().equals("RJCT"))
                    .collect(Collectors.toList());
            // pass getWorkflowActionDto().getReferenceId() for astAppNo
            return infoService.updateStatusFlag(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId,
                    MainetConstants.AssetManagement.APPROVAL_STATUS_REJECTED, lookUp.get(0).getLookUpId(),
                    getWorkflowActionDto().getReferenceId());

        } else if (response != null && !response.getIsProcessAlive()
                && workFlowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)
                && getAstDetailsDTO().getAssetInformationDTO().getUrlParam() != null) {
            // pass getWorkflowActionDto().getReferenceId() for astAppNo
            return infoService.updateAppStatusFlag(astDetailsDTO.getAssetInformationDTO().getAssetId(), orgId,
                    MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, getWorkflowActionDto().getReferenceId());
        } else if (response != null && response.getIsProcessAlive()) {
            return true;
        }
        return false;
    }

    private boolean updateCorrespondingDetails(String urlParam) {
        if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_CLASS_Page)) {
            AssetClassificationDTO astClassDTO = getAstDetailsDTO().getAssetClassificationDTO();
            auditDTO.setEmpId(astClassDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(astClassDTO.getLgIpMacUpd());
            service.updateClassification(astClassDTO.getAssetId(), astClassDTO, auditDTO);
        } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_INFO_Page)) {
            AssetInformationDTO astInfoDTO = getAstDetailsDTO().getAssetInformationDTO();
            auditDTO.setEmpId(astInfoDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(astInfoDTO.getLgIpMacUpd());
            service.updateInformation(astInfoDTO.getAssetId(), astInfoDTO, auditDTO);
        } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_PRUCH_Page)) {
            AssetPurchaseInformationDTO purchInfoDTO = getAstDetailsDTO().getAssetPurchaseInformationDTO();
            auditDTO.setEmpId(purchInfoDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(purchInfoDTO.getLgIpMacUpd());
            service.updatePurchaseInformation(purchInfoDTO.getAssetId(), purchInfoDTO, auditDTO);
        } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_REAL_ESTATE_Page)) {
            AssetRealEstateInformationDTO realEstateInfoDTO = getAstDetailsDTO().getAssetRealEstateInfoDTO();
            auditDTO.setEmpId(realEstateInfoDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(realEstateInfoDTO.getLgIpMacUpd());
            service.updateRealEstateInformation(realEstateInfoDTO.getAssetId(), realEstateInfoDTO, auditDTO);
        } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_LEASE_Page)) {
            final AssetLeasingCompanyDTO astLeasingDTO = getAstDetailsDTO().getAstLeaseDTO();
            auditDTO.setEmpId(astLeasingDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(astLeasingDTO.getLgIpMacUpd());
            service.updateLeaseInformation(astLeasingDTO.getAssetId(), astLeasingDTO, auditDTO);
        } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_SERVICE_Page)) {
            List<AssetServiceInformationDTO> serviceDTOList = getAstDetailsDTO().getAstSerList();
            auditDTO.setEmpId(serviceDTOList.get(0).getUpdatedBy());
            auditDTO.setEmpIpMac(serviceDTOList.get(0).getLgIpMacUpd());
            service.updateServiceInformation(serviceDTOList.get(0).getAssetId(), serviceDTOList,
                    auditDTO);
        } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_DEPRE_CHART_Page)) {
            AssetDepreciationChartDTO deprChartDTO = getAstDetailsDTO().getAstDepreChartDTO();
            auditDTO.setEmpId(deprChartDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(deprChartDTO.getLgIpMacUpd());
            service.updateDepreciationChart(deprChartDTO.getAssetId(), deprChartDTO, auditDTO);
        } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_LINEAR_Page)) {
            AssetLinearDTO linearDTO = getAstDetailsDTO().getAstLinearDTO();
            auditDTO.setEmpId(linearDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(linearDTO.getLgIpMacUpd());
            service.updateLinearInformation(linearDTO.getAssetId(), linearDTO, auditDTO);
        } else if (urlParam.equalsIgnoreCase(MainetConstants.AssetManagement.SHOW_AST_INSU_Page_DATATABLE)) {
            List<AssetInsuranceDetailsDTO> insDTOList = getAstDetailsDTO().getAstInsuDTOList();
            // update assertInsuRev its flag to null and add a fresh entry in databse
            /*
             * if(insDTOList.size()>0) service.updateInsuranceDetailsListRev(insDTOList.get(0).getRevGrpId(),insDTOList,
             * auditDTO);
             */
            for (int i = 0; i < insDTOList.size(); i++) {
                AssetInsuranceDetailsDTO insuDTO = insDTOList.get(i);
                service.saveInsuranceDetails(insuDTO.getAssetId(), insuDTO, auditDTO);
            }

            /*
             * AssetInsuranceDetailsDTO insuranceDTO = getAstDetailsDTO().getAstInsuDTO();
             * auditDTO.setEmpId(insuranceDTO.getUpdatedBy()); auditDTO.setEmpIpMac(insuranceDTO.getLgIpMacUpd());
             * service.updateInsuranceDetails(insuranceDTO.getAssetId(), insuranceDTO, auditDTO);
             */
        }else if (urlParam.equalsIgnoreCase(MainetConstants.ITAssetManagement.IT_ASSET_CODE)) {
        	
            AssetInformationDTO astInfoDTO = getAstDetailsDTO().getAssetInformationDTO();
            auditDTO.setEmpId(astInfoDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(astInfoDTO.getLgIpMacUpd());
            service.updateInformation(astInfoDTO.getAssetId(), astInfoDTO, auditDTO);
            
            AssetPurchaseInformationDTO purchInfoDTO = getAstDetailsDTO().getAssetPurchaseInformationDTO();
            auditDTO.setEmpId(purchInfoDTO.getUpdatedBy());
            auditDTO.setEmpIpMac(purchInfoDTO.getLgIpMacUpd());
            service.updatePurchaseInformation(purchInfoDTO.getAssetId(), purchInfoDTO, auditDTO);
            if( getAstDetailsDTO().getAstSerList() != null && ! getAstDetailsDTO().getAstSerList().isEmpty() ) {
            	
            List<AssetServiceInformationDTO> serviceDTOList = getAstDetailsDTO().getAstSerList();   
            
            auditDTO.setEmpId(serviceDTOList.get(0).getUpdatedBy());
            auditDTO.setEmpIpMac(serviceDTOList.get(0).getLgIpMacUpd());
            service.updateServiceInformation(serviceDTOList.get(0).getAssetId(), serviceDTOList,
                    auditDTO);
            }
        } 
        return true;
    }

    public List<DocumentDetailsVO> setDocumentsDetailVO() {
        List<DocumentDetailsVO> docVOList = new ArrayList<DocumentDetailsVO>();

        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    DocumentDetailsVO docVO = new DocumentDetailsVO();
                    docVO.setDoc_DESC_ENGL(file.getName());
                    docVOList.add(docVO);
                }
            }
        }
        return docVOList;
    }

    /**
     * @param workflowActionDto
     */
    private WorkflowTaskAction prepareWorkFlowTaskActionUpdate(WorkflowTaskAction workflowActionDto) {

        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());

        workflowActionDto.setDateOfAction(new Date());
        workflowActionDto.setCreatedDate(new Date());
        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        workflowActionDto.setTaskId(getTaskId());
        return workflowActionDto;

    }

    // set all relevant Work flow Task Action Data For initiating Work Flow -initial
    // request
    public WorkflowTaskAction prepareWorkFlowTaskActionCreate(String serialNo) {
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
        taskAction.setReferenceId(serialNo);
        taskAction.setPaymentMode(MainetConstants.FlagF);
        return taskAction;
    }

    public boolean isDuplicateRfId(Long orgId, String rfiId) {
        boolean rfIdNoStatus = false;
        rfIdNoStatus = infoService.isDuplicateRfIdNo(orgId, rfiId);
        return rfIdNoStatus;
    }
    
    public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public List<ITAssetRegisterBulkDTO> getItAssetRegisterBulkDTO() {
		return itAssetRegisterBulkDTO;
	}

	public void setItAssetRegisterBulkDTO(List<ITAssetRegisterBulkDTO> itAssetRegisterBulkDTO) {
		this.itAssetRegisterBulkDTO = itAssetRegisterBulkDTO;
	}

	public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}
}
