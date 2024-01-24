package com.abm.mainet.care.ui.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.dto.CareFeedbackDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.DepartmentDTO;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@Component
@Scope("session")
public class ComplaintRegistrationModel extends AbstractFormModel {

    private static final long serialVersionUID = 7459121617001255856L;

    @Resource
    private ILocationMasService iLocationMasService;

    @Resource
    private ICareRequestService careRequestService;

    @Autowired
    private GroupMasterService groupMasterService;

    private TbLocationMas tbLocationMas = new TbLocationMas();
    private LinkedHashSet<LookUp> departments = new LinkedHashSet<>();
    private Set<LookUp> complaintTypes = new HashSet<>();
    private Set<LookUp> locations = new HashSet<>();
    private Set<LookUp> organisations = new HashSet<>();

    private String prefixName;
    private String modeType;
    private Map<Long, String> mapRenewalList;
    private CareRequestDTO careRequest;
    private RequestDTO applicantDetailDto;
    private WorkflowTaskAction careDepartmentAction;
    private ComplaintAcknowledgementModel complaintAcknowledgementModel;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<CareRequestDTO> careRequests = new ArrayList<>();
    private CareFeedbackDTO CareFeedback;
    private GroupMaster currentEmployeeGroup;
    private long ageOfRequest;
    private String requestType = "C";
    private String applicationType;
    private String labelType;
    private String kdmcEnv;
    private String operationPrefix;
    private List<String> flatNoList = new ArrayList<>();

    @Override
    protected void initializeModel() {
        initializeLookupFields(PrefixConstants.prefixName.ElectrolWZ);
        careRequest = new CareRequestDTO();
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PRODUCT) || !UserSession.getCurrent().getOrganisation().getDefaultStatus()
                .equalsIgnoreCase(MainetConstants.Organisation.SUPER_ORG_STATUS)) {
            careRequest.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            careRequest.setDistrict(UserSession.getCurrent().getOrganisation().getOrgCpdIdDis());
            Set<DepartmentDTO> dpts = careRequestService.getCareWorkflowMasterDefinedDepartmentListByOrgId(
                    UserSession.getCurrent().getOrganisation().getOrgid());
            dpts.stream().sorted(Comparator.comparing(DepartmentDTO::getDpDeptdesc)).forEach(d->
            {
                    LookUp detData = new LookUp();
                    detData.setDescLangFirst(d.getDpDeptdesc());
                    detData.setDescLangSecond(d.getDpNameMar());
                    detData.setLookUpId(d.getDpDeptid());
                    detData.setLookUpCode(d.getDpDeptcode());
                    departments.add(detData);
            });
            
            departments.forEach(a->{
                logger.info("Sorted Dept: " + a.getDescLangFirst());
            });
        }
        currentEmployeeGroup = groupMasterService.findByGmId(UserSession.getCurrent().getEmployee().getGmid(),
                UserSession.getCurrent().getOrganisation().getOrgid());
    }

    public void initializeModelEdit(String prefix) {
        initializeLookupFields(prefix);
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public WorkflowTaskAction getCareDepartmentAction() {
        return careDepartmentAction;
    }

    public void setCareDepartmentAction(WorkflowTaskAction careDepartmentAction) {
        this.careDepartmentAction = careDepartmentAction;
    }

    public TbLocationMas getTbLocationMas() {
        return tbLocationMas;
    }

    public void setTbLocationMas(TbLocationMas tbLocationMas) {
        this.tbLocationMas = tbLocationMas;
    }

    public Set<LookUp> getDepartments() {
        return departments;
    }

    public void setDepartments(LinkedHashSet<LookUp> departments) {
        this.departments = departments;
    }

    public Set<LookUp> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(Set<LookUp> organisations) {
        this.organisations = organisations;
    }

    public Set<LookUp> getLocations() {
        return locations;
    }

    public void setLocations(Set<LookUp> locations) {
        this.locations = locations;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public Map<Long, String> getMapRenewalList() {
        return mapRenewalList;
    }

    public void setMapRenewalList(Map<Long, String> mapRenewalList) {
        this.mapRenewalList = mapRenewalList;
    }

    public ILocationMasService getiLocationMasService() {
        return iLocationMasService;
    }

    public void setiLocationMasService(ILocationMasService iLocationMasService) {
        this.iLocationMasService = iLocationMasService;
    }

    public CareRequestDTO getCareRequest() {
        return careRequest;
    }

    public void setCareRequest(CareRequestDTO careRequest) {
        this.careRequest = careRequest;
    }

    public RequestDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(RequestDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public ComplaintAcknowledgementModel getComplaintAcknowledgementModel() {
        return complaintAcknowledgementModel;
    }

    public void setComplaintAcknowledgementModel(ComplaintAcknowledgementModel complaintAcknowledgementModel) {
        this.complaintAcknowledgementModel = complaintAcknowledgementModel;
    }

    public CareFeedbackDTO getCareFeedback() {
        return CareFeedback;
    }

    public void setCareFeedback(CareFeedbackDTO careFeedback) {
        CareFeedback = careFeedback;
    }

    public Set<LookUp> getComplaintTypes() {
        return complaintTypes;
    }

    public void setComplaintTypes(Set<LookUp> complaintTypes) {
        this.complaintTypes = complaintTypes;
    }

    public List<CareRequestDTO> getCareRequests() {
        return careRequests;
    }

    public void setCareRequests(List<CareRequestDTO> careRequests) {
        this.careRequests = careRequests;
    }

    public GroupMaster getCurrentEmployeeGroup() {
        return currentEmployeeGroup;
    }

    public void setCurrentEmployeeGroup(GroupMaster currentEmployeeGroup) {
        this.currentEmployeeGroup = currentEmployeeGroup;
    }

    @Override
    public boolean saveForm() {
        Long userId = UserSession.getCurrent().getEmployee().getEmpId();
        Organisation org = UserSession.getCurrent().getOrganisation();
        int langId = UserSession.getCurrent().getLanguageId();
        @SuppressWarnings("deprecation")
        String lgIPMacId = Utility.getMacAddress();
        if (this.modeType.equals(MainetConstants.MODE_CREATE)) {
            this.tbLocationMas.setLgIpMac(lgIPMacId);
            this.tbLocationMas.setLangId(langId);
            this.tbLocationMas.setUserId(userId);
            iLocationMasService.createLocationMas(this.getTbLocationMas(), org, null);
        } else {
            this.tbLocationMas.setUpdatedBy(userId);
            this.tbLocationMas.setUpdatedDate(new Date());
            iLocationMasService.createLocationMasEdit(this.getTbLocationMas(), org, null);
        }
        this.setSuccessMessage(ApplicationSession.getInstance().getMessage("care.grievance.update"));
        return true;
    }

    public long getAgeOfRequest() {
        return ageOfRequest;
    }

    public void setAgeOfRequest(long ageOfRequest) {
        this.ageOfRequest = ageOfRequest;
    }

    public void setAgeOfRequest(Date dateOfRequest) {
        Date currentdate = new Date();
        Long diff = Math.abs(currentdate.getTime() - dateOfRequest.getTime());
        ageOfRequest = diff / (24 * 60 * 60 * 1000);
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getKdmcEnv() {
        return kdmcEnv;
    }

    public void setKdmcEnv(String kdmcEnv) {
        this.kdmcEnv = kdmcEnv;
    }

    public String getOperationPrefix() {
        return operationPrefix;
    }

    public void setOperationPrefix(String operationPrefix) {
        this.operationPrefix = operationPrefix;
    }

    public List<String> getFlatNoList() {
        return flatNoList;
    }

    public void setFlatNoList(List<String> flatNoList) {
        this.flatNoList = flatNoList;
    }

}
