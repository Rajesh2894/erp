package com.abm.mainet.care.ui.controller;

import java.io.File;
import java.net.URI;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.DepartmentDTO;
import com.abm.mainet.care.dto.GrievanceReqDTO;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.ui.model.ComplaintRegistrationModel;
import com.abm.mainet.care.ui.validator.GrievanceRequestValidator;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.ServiceCareCommon.GRIEVANCEDEPARTMENTREGISTRATION)
public class GrievanceDepartmentRegistrationController extends AbstractFormController<ComplaintRegistrationModel> {

    private static final Logger log = LoggerFactory.getLogger(GrievanceDepartmentRegistrationController.class);

    @Autowired
    private ICareRequestService careRequestService;

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;

    @Resource
    IFileUploadService fileUpload;

    @Autowired
    private IComplaintTypeService iComplaintService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private CommonService commonService;

    @Resource
    private IOrganisationService organisationService;

    @Autowired
    TbServicesMstService tbServicesMstService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @InitBinder
    public void initBinder(WebDataBinder binder) {

    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView viewGrievanceDepartmentRegistration(@RequestParam(value = "id", required = false) Long flowId,
            @RequestParam(value = "type", required = false) String modeType,
            @ModelAttribute(MainetConstants.ServiceCareCommon.CARE_REQUEST) CareRequest careRequest,
            BindingResult result, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws EntityNotFoundException, Exception {
        log.info("From Grievance Department Registration Controller viewGrievanceDepartmentRegistration");
        // Session Cleanup
        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().bind(request);
        StringBuffer applicantType = new StringBuffer();
        StringBuffer complaintLabel = new StringBuffer();
        Employee emp = UserSession.getCurrent().getEmployee();
        ComplaintRegistrationModel model1 = this.getModel();
        log.info("Fetching Department List for Drop downs by Organization Id"
                + UserSession.getCurrent().getOrganisation().getOrgid());

        List<LookUp> lookUpList = CommonMasterUtility.lookUpListByPrefix("AC",
                UserSession.getCurrent().getOrganisation().getOrgid());

        lookUpList.forEach(lookup -> {
            if (applicantType != null && !applicantType.toString().isEmpty()) {
                applicantType.append(",");
            }
            if (lookup.getOtherField()!=null && lookup.getOtherField().equalsIgnoreCase(MainetConstants.FlagY)) {
                applicantType.append(lookup.getLookUpCode());
                complaintLabel.append(lookup.getDescLangFirst());
                complaintLabel.append(",");
            }
        });

        model1.setCommonHelpDocs(MainetConstants.ServiceCareCommon.GRIEVANCEDEPARTMENTREGISTRATION);
        model1.setApplicationType(applicantType.toString());
        model1.setLabelType(complaintLabel.toString());
        model1.setKdmcEnv(MainetConstants.FlagN);
        // U#110065
        if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())) {
            model1.setKdmcEnv(MainetConstants.FlagY);
            LookUp rfmLookup = CommonMasterUtility.getValueFromPrefixLookUp("BH", "RFM",
                    UserSession.getCurrent().getOrganisation());
            LookUp rfcLookup = CommonMasterUtility.getValueFromPrefixLookUp("GP", "RFC",
                    UserSession.getCurrent().getOrganisation());
            // hidden value set only in case of KDMC - Mode and category and reference date applicationType
            model1.getCareRequest().setReferenceMode(rfmLookup.getLookUpId() + "");
            model1.getCareRequest().setReferenceCategory(rfcLookup.getLookUpId() + "");
            model1.getCareRequest().setReferenceDate(new Date());
            model1.getCareRequest().setApplnType("C");
            model1.getCareRequest().setLocation(1272L);
        }

        model.put(MainetConstants.ServiceCareCommon.EMPLOYEE, emp);
        model.put(MainetConstants.ServiceCareCommon.TASKNAME, MainetConstants.ServiceCareCommon.REQUEST_CARE);
        model.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.BLANK);

        return new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVANCE_DEPARTMENT_REGISTRATION,
                MainetConstants.CommonConstants.COMMAND, model1);

    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.FINDDEPARTMENTCOMPLAINTBYDEPARTMENTID, method = RequestMethod.POST)
    @ResponseBody
    public Set<DepartmentComplaintTypeDTO> findDepartmentComplaintByDepartmentId(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "_csrf", required = true) String _csrf) throws Exception {

        Set<DepartmentComplaintTypeDTO> complaintType = careRequestService.getDepartmentComplaintTypeByDepartmentId(id,
                UserSession.getCurrent().getOrganisation().getOrgid());
        return complaintType;
    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.GRIEVANCE_ORGANISATIONS, method = RequestMethod.POST)
    public ModelAndView getOrganisations(
            @RequestParam(MainetConstants.Common_Constant.DISTRICT_ID) Long districtCpdId) {

        ModelAndView modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVANCE_ORGANISATIONS,
                MainetConstants.CommonConstants.COMMAND, getModel());
        Set<LookUp> organisations = new HashSet<>();
        List<OrganisationDTO> organisationList = careRequestService.getOrganisationByDistrict(districtCpdId);
        if (organisationList != null && !organisationList.isEmpty()) {
            organisationList.forEach(o -> {
                LookUp detData = new LookUp();
                detData.setDescLangFirst(o.getONlsOrgname());
                detData.setDescLangSecond(o.getONlsOrgnameMar());
                detData.setLookUpId(o.getOrgid());
                organisations.add(detData);
            });
        }
        this.getModel().setOrganisations(organisations);
        return modelAndView;
    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.GRIEVANCE_DEPARTMENTS, method = RequestMethod.POST)
    public ModelAndView getDepartments(@RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId) {

        ModelAndView modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVANCE_DEPARTMENTS,
                MainetConstants.CommonConstants.COMMAND, getModel());
        LinkedHashSet<LookUp> departments = new LinkedHashSet<>();
        Set<DepartmentDTO> dpts = careRequestService.getCareWorkflowMasterDefinedDepartmentListByOrgId(orgId);
        
        Organisation organisation = organisationService.getOrganisationById(orgId);
        
        if(organisation!=null && organisation.getOrgShortNm().equalsIgnoreCase(MainetConstants.Common_Constant.Doon_Smart_City)){
        	if (dpts != null && !dpts.isEmpty()) {
                dpts.forEach(d ->  {
                    if(d.getDpDeptcode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER)){
                    LookUp detData = new LookUp();
                    detData.setDescLangFirst(d.getDpDeptdesc());
                    detData.setDescLangSecond(d.getDpNameMar());
                    detData.setLookUpId(d.getDpDeptid());
                    departments.add(detData);
                    }
                });
        	}
        }	
       
        else{
        if (dpts != null && !dpts.isEmpty()) {
            dpts.forEach(d -> {
                LookUp detData = new LookUp();
                detData.setDescLangFirst(d.getDpDeptdesc());
                detData.setDescLangSecond(d.getDpNameMar());
                detData.setLookUpId(d.getDpDeptid());
                departments.add(detData);
            });
        }
        }
        this.getModel().setDepartments(departments);
        return modelAndView;
    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.GRIEVANCE_COMPLAINTTYPES, method = RequestMethod.POST)
    public ModelAndView getComplaintTypes(@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
            @RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId) {
        orgId = (orgId == null) ? UserSession.getCurrent().getOrganisation().getOrgid() : orgId;
        ModelAndView modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVANCE_COMPLAINTTYPES,
                MainetConstants.CommonConstants.COMMAND, getModel());
        TbDepartment dept = tbDepartmentService.findById(deptId);
        if (dept != null) {
        	LinkedHashSet<LookUp> complaints = new LinkedHashSet<>();
            // below code is for SUDA specific
            Boolean sudaENV = CareUtility.isENVCodePresent(MainetConstants.ENV_SUDA, orgId);
            if (sudaENV && dept.getDpDeptcode().equalsIgnoreCase("CFC")) {
                List<TbServicesMst> serviceList = tbServicesMstService.findByDeptId(deptId, orgId);
                if (!CollectionUtils.isEmpty(serviceList)) {
                    serviceList.forEach(c -> {
                        LookUp detData = new LookUp();
                        detData.setDescLangFirst(c.getSmServiceName());
                        detData.setDescLangSecond(c.getSmServiceNameMar());
                        detData.setLookUpId(c.getSmServiceId());
                        complaints.add(detData);
                    });
                }
            } else {
            	 Set<DepartmentComplaintTypeDTO> complaintTypes = careRequestService
                         .getDepartmentComplaintTypeByDepartmentId(deptId, orgId);
                 
                 if (complaintTypes != null && !complaintTypes.isEmpty()) {
                     
                     complaintTypes.stream().sorted(Comparator.comparing(DepartmentComplaintTypeDTO::getComplaintDesc)).forEach(
                             c-> {
                                 LookUp detData = new LookUp();
                                 detData.setDescLangFirst(c.getComplaintDesc());
                                 detData.setDescLangSecond(c.getComplaintDescReg());
                                 detData.setLookUpId(c.getCompId());
                                 complaints.add(detData);
                             });
                 }
            }
            this.getModel().setComplaintTypes(complaints);
        }

        return modelAndView;
    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.GRIEVANCE_LOCATIONS, method = RequestMethod.POST)
    public ModelAndView getLocations(@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
            @RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId) {

        orgId = (orgId == null) ? UserSession.getCurrent().getOrganisation().getOrgid() : orgId;
        ModelAndView modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVANCE_LOCATIONS,
                MainetConstants.CommonConstants.COMMAND, getModel());
        Set<LookUp> locations = new HashSet<>();
        List<TbLocationMas> locs = iLocationMasService.getlocationByDeptId(deptId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        locs.forEach(l -> {
            LookUp detData = new LookUp();
            detData.setDescLangFirst(l.getLocNameEng());
            detData.setDescLangSecond(l.getLocNameReg());
            detData.setLookUpId(l.getLocId());
            locations.add(detData);
        });
        this.getModel().setLocations(locations);
        return modelAndView;
    }

    @RequestMapping(params = "grievanceOpWardZone", method = RequestMethod.POST)
    public ModelAndView getGrievanceOpWardZone(@RequestParam(MainetConstants.CommonConstants.LOCID) Long locId,
            @RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId) {

        orgId = (orgId == null) ? UserSession.getCurrent().getOrganisation().getOrgid() : orgId;

        // here set Operational ward zone of CWZ
        // deptId of CFC and locId selected from Form
        // get DEPT id of CFC
        Department dept = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
        LocOperationWZMappingDto locOperationWZMappingDto = iLocationMasService.findOperLocationAndDeptId(locId,
                dept.getDpDeptid());
        // set in careRequest OBJ of ward
        if (locOperationWZMappingDto != null) {
            getModel().getCareRequest().setWard1(locOperationWZMappingDto.getCodIdOperLevel1());
            if (locOperationWZMappingDto.getCodIdOperLevel2() != null)
                getModel().getCareRequest().setWard2(locOperationWZMappingDto.getCodIdOperLevel2());

            if (locOperationWZMappingDto.getCodIdOperLevel3() != null)
                getModel().getCareRequest().setWard3(locOperationWZMappingDto.getCodIdOperLevel3());
            if (locOperationWZMappingDto.getCodIdOperLevel4() != null)
                getModel().getCareRequest().setWard4(locOperationWZMappingDto.getCodIdOperLevel4());

            if (locOperationWZMappingDto.getCodIdOperLevel5() != null)
                getModel().getCareRequest().setWard5(locOperationWZMappingDto.getCodIdOperLevel5());
        } else {
            getModel().getCareRequest().setWard1(null);
            getModel().getCareRequest().setWard2(null);
            getModel().getCareRequest().setWard3(null);
            getModel().getCareRequest().setWard4(null);
            getModel().getCareRequest().setWard5(null);
        }
        return new ModelAndView("grievanceLocationMappingWardZone",
                MainetConstants.CommonConstants.COMMAND, getModel());
    }

    // U#113577
    @RequestMapping(params = "findWardZone", method = RequestMethod.POST)
    public ModelAndView getOperationalWardZonePrefixName(
            @RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId,
            @RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId) {
        String prefixName = tbDepartmentService.findDepartmentPrefixName(deptId, orgId);

        if (prefixName != null && !prefixName.isEmpty()) {
            this.getModel().setPrefixName(prefixName);
            if (StringUtils.isNotEmpty(this.getModel().getOperationPrefix())
                    && this.getModel().getOperationPrefix().equals(prefixName)) {
                this.getModel().setOperationPrefix(prefixName);
            } else if (prefixName != null && !prefixName.isEmpty()) {
                // D#130416 check at least one level define or not in prefix
                // if not than it throws exception which is handle using try catch and inside catch pass prefix CWZ
                // below code only work if CFC department define in ULB otherwise it throws exception
                try {
                    CommonMasterUtility.getLevelData(prefixName, 1, UserSession.getCurrent().getOrganisation());
                    this.getModel().setPrefixName(prefixName);
                } catch (FrameworkException e) {
                    this.getModel().setPrefixName(MainetConstants.COMMON_PREFIX.CWZ);
                }
                this.getModel().setOperationPrefix(prefixName);
                getModel().getCareRequest().setWard1(null);
                getModel().getCareRequest().setWard2(null);
                getModel().getCareRequest().setWard3(null);
                getModel().getCareRequest().setWard4(null);
                getModel().getCareRequest().setWard5(null);
            }

        } else {
            this.getModel().setPrefixName(MainetConstants.COMMON_PREFIX.CWZ);
        }
        return new ModelAndView("grievanceLocationMappingWardZone",
                MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(params = "getAllLocationsByPinCode", method = RequestMethod.POST)
    @ResponseBody
    public List<TbLocationMas> getAllLocationsByPinCodeAjax(
            @RequestParam(value = "pinCode", required = true) Integer pinCode,
            @RequestParam(value = "_csrf", required = true) String _csrf) throws Exception {
        List<TbLocationMas> locations = iLocationMasService.getlocationByPinCode(Long.valueOf(pinCode));
        return locations;
    }

    @RequestMapping(params = "getAllLocationsByOrgId", method = RequestMethod.POST)
    @ResponseBody
    public List<TbLocationMas> getAllLocationsByOrgIdAjax(@RequestParam(value = "_csrf", required = true) String _csrf)
            throws Exception {
        List<TbLocationMas> locations = iLocationMasService
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        return locations;
    }

    @RequestMapping(params = "getPincodeByLocationId", method = RequestMethod.POST)
    @ResponseBody
    public Long getPincodeByLocationId(@RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "_csrf", required = true) String _csrf) throws Exception {
        Long pinCode = 0L;
        if (id == null)
            return pinCode;
        List<TbLocationMas> locations = iLocationMasService
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        for (TbLocationMas loc : locations) {
            if (loc.getLocId().intValue() == id) {
                pinCode = loc.getPincode();
                break;
            }
        }
        return pinCode;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveDetails")
    public ModelAndView submit(HttpServletRequest request, ModelMap modelMap)
            throws EntityNotFoundException, Exception {
        getModel().bind(request);
        ComplaintRegistrationModel model = getModel();

        RequestDTO applicantDetailDto = model.getApplicantDetailDto();
        CareRequestDTO crdto = model.getCareRequest();
        // D#127037 CONCAT property no and flat no if present in extRefereNo
        if (StringUtils.isNotBlank(crdto.getPropFlatNo())) {
            crdto.setExtReferNumber(crdto.getExtReferNumber() + "-" + crdto.getPropFlatNo());
        }
       crdto.setLatitude(crdto.getLatitude());
       crdto.setLongitude(crdto.getLongitude());
        
        GrievanceReqDTO grievanceReq = new GrievanceReqDTO();
        grievanceReq.setApplicantDetailDto(applicantDetailDto);
        grievanceReq.setCareRequest(crdto);
        model.validateBean(grievanceReq, GrievanceRequestValidator.class);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PRODUCT)){
        if(applicantDetailDto.getPincodeNum()!=  null && !applicantDetailDto.getPincodeNum().isEmpty()){
        applicantDetailDto.setPincodeNo(Long.valueOf(applicantDetailDto.getPincodeNum()));
        }
        }
        // check sequence master configure or not with Prefix Base and CWZ
        // D#121823,D#123812 check here loggedIn locationId is Mapped with Location Master Operational Ward ZONE (CFC DEPT - CWZ)
        Long deptId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
        SequenceConfigMasterDTO configMasterDTO = seqGenFunctionUtility.loadSequenceData(crdto.getOrgId(), deptId,
                MainetConstants.ServiceCareCommon.SQLTables.TB_CARE_REQUEST,
                MainetConstants.ServiceCareCommon.SQLTables.COMPLAINT_NO);

        if (configMasterDTO != null && CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, crdto.getOrgId())
                && UserSession.getCurrent().getLoggedLocId() != null) {
            Department dept = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                    .findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
            LocOperationWZMappingDto locOperationWZMappingDto = iLocationMasService.findOperLocationAndDeptId(
                    UserSession.getCurrent().getLoggedLocId(),
                    dept.getDpDeptid());
            if (locOperationWZMappingDto == null) {
                LocationMasEntity locMas = iLocationMasService.findbyLocationId(UserSession.getCurrent().getLoggedLocId());
                String locName = UserSession.getCurrent().getLanguageId() == 1 ? locMas.getLocNameEng() : locMas.getLocNameReg();
                getModel().addValidationError(locName + "" + getApplicationSession().getMessage("care.form.validn.mappedDept"));
            }
        }
        if (model.hasValidationErrors()) {
            return customDefaultMyResult(MainetConstants.ServiceCareCommon.GRIEVANCE_DEPARTMENT_REGISTRATION);
        }

        WorkflowMas workflowType = null;
        try {
            // D#126904 it also worked on All type workflow define and multiple level also
            // discussed with RJ
            if (model.getCareRequest().getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
                workflowType = workflowTyepResolverService.resolveServiceWorkflowType(crdto.getOrgId(),
                        crdto.getDepartmentComplaint(), crdto.getComplaintType(), crdto.getWard1(), crdto.getWard2(),
                        crdto.getWard3(), crdto.getWard4(), crdto.getWard5());
            } else {
                workflowType = workflowTyepResolverService.resolveComplaintWorkflowType(crdto.getOrgId(),
                        crdto.getDepartmentComplaint(), crdto.getComplaintType(), crdto.getWard1(), crdto.getWard2(),
                        crdto.getWard3(), crdto.getWard4(), crdto.getWard5());
            }
        } catch (Exception e) {
            getModel().addValidationError(getApplicationSession()
                    .getMessage(MainetConstants.ServiceCareCommon.WORKFL_DEFINITION_NOT_FOUND_LOCATION));
            return customDefaultMyResult(MainetConstants.ServiceCareCommon.GRIEVANCE_DEPARTMENT_REGISTRATION);
        }

        if (workflowType == null) {
            getModel().addValidationError(getApplicationSession()
                    .getMessage(MainetConstants.ServiceCareCommon.WORKFL_DEFINITION_NOT_FOUND_LOCATION));
            return customDefaultMyResult(MainetConstants.ServiceCareCommon.GRIEVANCE_DEPARTMENT_REGISTRATION);
        }

        applicantDetailDto.setServiceId(workflowType.getService().getSmServiceId());

        applicantDetailDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        applicantDetailDto.setOrgId(crdto.getOrgId());
        applicantDetailDto.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
        applicantDetailDto.setReferenceId("SUBMITED");
        Long applicationNuber = applicationService.createApplication(applicantDetailDto);
        applicantDetailDto.setApplicationId(applicationNuber);

        model.setAttachments(fileUploadService.setFileUploadMethod(model.getAttachments()));
        applicantDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        fileUploadService.doFileUpload(model.getAttachments(), applicantDetailDto);
        List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(applicationNuber,
                applicantDetailDto.getOrgId());

        crdto.setApplicationId(applicationNuber);
        // crdto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        crdto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        crdto.setCreatedDate(new Date());
        crdto.setDateOfRequest(new Date());
        crdto.setRequestType(MainetConstants.BLANK);
        CareRequest careRequest = CareUtility.toCareRequest(crdto);
        if (model.getCareRequest().getApplnType().equalsIgnoreCase(MainetConstants.FlagC)) {
            careRequest.setDepartmentComplaint(crdto.getDepartmentComplaint());
            careRequest.setComplaintType(crdto.getComplaintType());
            careRequest.setApplnType(crdto.getApplnType());

        } else {
            careRequest.setDepartmentComplaint(crdto.getDepartmentComplaint());
            careRequest.setSmServiceId(crdto.getComplaintType());
            careRequest.setApplnType(crdto.getApplnType());
        }
        /*
         * careRequest.setDepartmentComplaint(crdto.getDepartmentComplaint());
         * careRequest.setComplaintType(crdto.getComplaintType());
         * careRequest.setLocation(iLocationMasService.findbyLocationId(crdto. getLocation()));
         */
        careRequest.setLocation(iLocationMasService.findbyLocationId(crdto.getLocation()));
        WorkflowTaskAction startAction = new WorkflowTaskAction();
        startAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        startAction.setComments(careRequest.getDescription());
        startAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        startAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        startAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        startAction.setAttachementId(attachmentId);

        ActionResponse actResponse = careRequestService.startCareProces(careRequest, startAction,
                UserSession.getCurrent().getLoggedLocId());
        if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(actResponse.getResponse())) {
            if (actResponse != null) {
                if (null != actResponse.getError() && !MainetConstants.BLANK.equals(actResponse.getError())) {
                    request.getSession().setAttribute(MainetConstants.DELETE_ERROR, actResponse.getError());
                } else {
                    String actionKey = actResponse.getResponseData(MainetConstants.RESPONSE);
                    if (null != actionKey) {
                        String requestNo = actResponse.getResponseData(MainetConstants.REQUEST_NO);
                        if (null != requestNo) {
                            String message = MainetConstants.REQUEST_NUMBER + requestNo
                                    + MainetConstants.SUBMITTED_SUCCESSFULLY;
                            modelMap.addAttribute(MainetConstants.SUCCESS_MESSAGE, message);
                        }
                    }
                }
            }
            modelMap.put("kdmcEnv", model.getKdmcEnv());
            modelMap.addAttribute(MainetConstants.ServiceCareCommon.COMPLAINT_ACKNOWLEDGEMENT_MODEL, careRequestService
                    .getComplaintAcknowledgementModel(careRequest, UserSession.getCurrent().getLanguageId()));
            return new ModelAndView(MainetConstants.ServiceCareCommon.REGISTRATION_ACKNOWLEDGEMENT_RECEIPT,
                    MainetConstants.ServiceCareCommon.CARE_REQUEST, careRequest);
        }
        return null;
    }

    @RequestMapping(params = "getWorkflowTypeListByDepartmentAndcomplaintsubType", method = RequestMethod.POST)
    @ResponseBody
    public String getWorkflowTypeListByDepartmentAndcomplaintsubType(
            @RequestParam(value = "deptId", required = true) Long deptId,
            @RequestParam(value = "compId", required = true) Long compId,
            @RequestParam(value = "_csrf", required = true) String _csrf) throws Exception {
        ApplicationSession appSession = ApplicationSession.getInstance();
        String response = appSession.getMessage(MainetConstants.ServiceCareCommon.EMPTY);
        List<WorkflowMas> workflowTypeList = iWorkFlowTypeService.getWorkFlowTypeByOrgDepartmentAndComplaintType(
                UserSession.getCurrent().getOrganisation().getOrgid(), deptId, compId);
        if (!workflowTypeList.isEmpty()) {
            response = appSession.getMessage(MainetConstants.ServiceCareCommon.NOT_EMPTY);
        }
        return response;
    }

    @RequestMapping(params = "getUploaddedFiles", method = { RequestMethod.POST })
    @ResponseBody
    public Map<Long, Set<File>> getUploaddedCount(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return FileUploadUtility.getCurrent().getFileMap();
    }

    @RequestMapping(params = "getApplicationDetails", method = RequestMethod.POST)
    @ResponseBody
    public RequestDTO getApplicationDetails(@RequestParam(value = "mobileNumber", required = true) String mobileNumber)
            throws Exception {
        return careRequestService.getApplicationDetailsByMobile(mobileNumber);
    }

    @RequestMapping(method = RequestMethod.POST, params = "requestTypeComplaint")
    public ModelAndView getRequestTypeComplaint(final HttpServletRequest request) {
        bindModel(request);
        ComplaintRegistrationModel model = this.getModel();

        if (model.getCareRequest().getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
            model.setRequestType(MainetConstants.FlagR);
            LinkedHashSet<LookUp> departments = new LinkedHashSet<>();
            model.getDepartments().clear();
            Department dpts = tbDepartmentService.findDepartmentByCode("CFC");
            if (dpts != null) {
                LookUp detData = new LookUp();
                detData.setDescLangFirst(dpts.getDpDeptdesc());
                detData.setDescLangSecond(dpts.getDpNameMar());
                detData.setLookUpId(dpts.getDpDeptid());
                departments.add(detData);
            }
            model.setDepartments(departments);
        } else {
            model.setRequestType(MainetConstants.FlagC);
            LinkedHashSet<LookUp> departments = new LinkedHashSet<>();
            CareRequestDTO careRequest = new CareRequestDTO();
            if (!UserSession.getCurrent().getOrganisation().getDefaultStatus()
                    .equalsIgnoreCase(MainetConstants.Organisation.SUPER_ORG_STATUS)) {
                careRequest.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                careRequest.setDistrict(UserSession.getCurrent().getOrganisation().getOrgCpdIdDis());
                Set<DepartmentDTO> dpts = careRequestService.getCareWorkflowMasterDefinedDepartmentListByOrgId(
                        UserSession.getCurrent().getOrganisation().getOrgid());
                dpts.forEach(d -> {
                    LookUp detData = new LookUp();
                    detData.setDescLangFirst(d.getDpDeptdesc());
                    detData.setDescLangSecond(d.getDpNameMar());
                    detData.setLookUpId(d.getDpDeptid());
                    departments.add(detData);
                });
            }
            model.setDepartments(departments);
        }
        return new ModelAndView("GrievanceDepartmentRegistrationValidn", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "checkComplaintTypeData", method = RequestMethod.POST)
    public ComplaintType checkComplaintTypeData(@RequestParam("complaintTypeId") final Long complaintTypeId,
            final HttpServletRequest request) {
        getModel().bind(request);
        ComplaintType complaintType = new ComplaintType();
        //143636
        if(complaintTypeId == 0){
        	return complaintType;
        }
        complaintType = iComplaintService.findComplaintTypeById(complaintTypeId);
        return complaintType;
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(params = "fetchPropertyFlatNo", method = RequestMethod.POST)
    public List<String> fetchPropertyFlatNo(@RequestParam("refNo") final String refNo,
            final HttpServletRequest request) {
        getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        // call to GET_BILLING_MEHOD_BY_PROP_NO to identify its a billingMethod(I/W)
        Map<String, String> requestParam = new HashMap<>();
        if (StringUtils.isNotBlank(refNo)) {
            requestParam.put("propNo", refNo.replaceAll("\\s", ""));
        } else {
            log.info("reference no can't be empty");
        }

        requestParam.put(MainetConstants.Common_Constant.ORGID, orgId.toString());
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        dd.setParsePath(true);
        URI uri = dd.expand(ServiceEndpoints.GET_BILLING_MEHOD_BY_PROP_NO, requestParam);
        String billingMethod = "";
        /*
         * try { responseEntity = RestClient.callRestTemplateClient(null, uri.toString()); HttpStatus statusCode =
         * responseEntity.getStatusCode(); if (statusCode == HttpStatus.OK) { billingMethod = (String) new
         * JSONObject(responseEntity).get("billingMethod"); } } catch (Exception ex) { log.info("calling for property dues" + ex);
         * }
         */
        // next call for flat no list
        uri = dd.expand(ServiceEndpoints.GET_FLAT_LIST_BY_PROP_NO, requestParam);
        ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(requestParam, uri.toString());
        List<String> flatNoList = (List<String>) responseEntity.getBody();
        if (flatNoList != null && !flatNoList.isEmpty()) {
            getModel().setFlatNoList(flatNoList);
            return flatNoList;
        } else {
            getModel().setFlatNoList(flatNoList);
        }
        return flatNoList;
    }

    @ResponseBody
    @RequestMapping(params = "checkDues", method = RequestMethod.POST)
    public String checkDues(@RequestParam("refNo") final String refNo, @RequestParam("deptId") final Long deptId,
            @RequestParam(value = "flatNo", required = false) String flatNo, final HttpServletRequest request) {
        getModel().bind(request);
        String message = "";
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // by using deptId and reference no search for dues
        // get DEPT code using deptId
        String deptCode = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDepartmentShortCodeByDeptId(deptId, orgId);
        if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)) {
            // rest call to property
            PropertyDetailDto detailDTO = null;
            PropertyInputDto propInputDTO = new PropertyInputDto();
            propInputDTO.setPropertyNo(refNo);
            propInputDTO.setOrgId(orgId);
            propInputDTO.setFlatNo(flatNo);
            final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                    ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
            if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
                detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
                if (detailDTO.getStatus().equalsIgnoreCase("Fail")) {
                    log.info("invalid property no" + detailDTO);
                } else {
                    message = detailDTO.getTotalOutsatandingAmt() > 0 ? getApplicationSession().getMessage("care.dueAmt.exist")
                            : "";
                }
            }
        } else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)) {

            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("CsCcn", refNo);
            requestParam.put(MainetConstants.Common_Constant.ORGID, orgId.toString());
            TbBillMas tbBillMas = null;
            ResponseEntity<?> responseEntity = null;
            DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
            dd.setParsePath(true);
            URI uri = dd.expand(ServiceEndpoints.BRMSMappingURL.WATER_BILL_BY_CONN_NO, requestParam);

            try {
                responseEntity = RestClient.callRestTemplateClient(tbBillMas, uri.toString());
                HttpStatus statusCode = responseEntity.getStatusCode();
                if (statusCode == HttpStatus.OK) {
                    tbBillMas = (TbBillMas) RestClient.castResponse(responseEntity, TbBillMas.class);
                    if (tbBillMas != null) {
                        message = tbBillMas.getBmTotalOutstanding() > 0 ? getApplicationSession().getMessage("care.dueAmt.exist")
                                : "";
                    }
                }
            } catch (Exception ex) {
                log.info("calling for water dues" + ex);
            }

        }
        return message;
    }

    @RequestMapping(params = "findOpWardZoneByReferenceNo", method = RequestMethod.POST)
    public ModelAndView findOpWardZoneByReferenceNo(@RequestParam("refNo") final String refNo,
            @RequestParam("deptId") final Long deptId, @RequestParam(value = "flatNo", required = false) String flatNo,
            final HttpServletRequest request) {

        bindModel(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        // get DEPT code using deptId
        String deptCode = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDepartmentShortCodeByDeptId(deptId, orgId);
        if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)) {
            // rest call to property
            PropertyDetailDto detailDTO = null;
            PropertyInputDto propInputDTO = new PropertyInputDto();
            propInputDTO.setPropertyNo(refNo);
            propInputDTO.setOrgId(orgId);
            propInputDTO.setFlatNo(flatNo);
            final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                    ServiceEndpoints.PROP_BY_PROP_NO_AND_FLATNO);
            if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
                detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
                if (detailDTO.getStatus().equalsIgnoreCase("Fail")) {
                    log.info("invalid property no" + detailDTO);
                    getModel().getCareRequest().setWard1(null);
                    getModel().getCareRequest().setWard2(null);
                    getModel().getCareRequest().setWard3(null);
                    getModel().getCareRequest().setWard4(null);
                    getModel().getCareRequest().setWard5(null);
                    getModel().getApplicantDetailDto().setMobileNo(null);
                } else {
                    getModel().getCareRequest()
                            .setWard1(detailDTO.getWd1() != null && detailDTO.getWd1() != 0 ? detailDTO.getWd1() : null);
                    getModel().getCareRequest()
                            .setWard2(detailDTO.getWd2() != null && detailDTO.getWd2() != 0 ? detailDTO.getWd2() : null);
                    getModel().getCareRequest()
                            .setWard3(detailDTO.getWd3() != null && detailDTO.getWd3() != 0 ? detailDTO.getWd3() : null);
                    getModel().getCareRequest()
                            .setWard4(detailDTO.getWd4() != null && detailDTO.getWd4() != 0 ? detailDTO.getWd4() : null);
                    getModel().getCareRequest()
                            .setWard5(detailDTO.getWd5() != null && detailDTO.getWd5() != 0 ? detailDTO.getWd5() : null);
                    getModel().getApplicantDetailDto().setMobileNo(detailDTO.getPrimaryOwnerMobNo());
                }
            }
        } else if (deptCode.equals(MainetConstants.DEPT_SHORT_NAME.WATER)) {

            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("CsCcn", refNo);
            requestParam.put(MainetConstants.Common_Constant.ORGID, orgId.toString());
            TbBillMas tbBillMas = null;
            ResponseEntity<?> responseEntity = null;
            DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
            dd.setParsePath(true);
            URI uri = dd.expand(ServiceEndpoints.BRMSMappingURL.WATER_BILL_BY_CONN_NO, requestParam);

            try {
                responseEntity = RestClient.callRestTemplateClient(tbBillMas, uri.toString());
                HttpStatus statusCode = responseEntity.getStatusCode();
                if (statusCode == HttpStatus.OK) {
                    tbBillMas = (TbBillMas) RestClient.castResponse(responseEntity, TbBillMas.class);
                    if (tbBillMas != null) {
                        // water ward zone
                        getModel().getCareRequest().setWard1(tbBillMas.getCodDwzid1());
                        getModel().getCareRequest().setWard2(tbBillMas.getCodDwzid2());
                        getModel().getCareRequest().setWard3(tbBillMas.getCodDwzid3());
                        //#149396 In complaint we created WF for three levels
                        getModel().getCareRequest().setWard4(null);
                        getModel().getCareRequest().setWard5(null);
                        getModel().getApplicantDetailDto().setMobileNo(tbBillMas.getMobileNo());
                    } else {
                        getModel().getCareRequest().setWard1(null);
                        getModel().getCareRequest().setWard2(null);
                        getModel().getCareRequest().setWard3(null);
                        getModel().getCareRequest().setWard4(null);
                        getModel().getCareRequest().setWard5(null);
                        getModel().getApplicantDetailDto().setMobileNo(null);
                    }
                }
            } catch (Exception ex) {
                log.info("calling for water dues " + ex);
            }

        }

        return new ModelAndView("grievanceLocationMappingWardZone",
                MainetConstants.CommonConstants.COMMAND, getModel());
    }

}