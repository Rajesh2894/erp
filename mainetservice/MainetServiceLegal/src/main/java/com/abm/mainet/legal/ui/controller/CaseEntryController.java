package com.abm.mainet.legal.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseEntryDetailDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.JudgeMasterDTO;
import com.abm.mainet.legal.dto.OfficerInchargeDetailsDTO;
import com.abm.mainet.legal.dto.TbLglComntRevwDtlDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICaseHearingService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.IJudgeMasterService;
import com.abm.mainet.legal.ui.model.CaseEntryModel;

@Controller
@RequestMapping("/CaseEntry.html")
public class CaseEntryController extends AbstractFormController<CaseEntryModel> {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ICourtMasterService courtMasterService;

    @Autowired
    private ICaseEntryService caseEntryService;

    @Autowired
    private ILocationMasService locationMasService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    IJudgeMasterService judgeMasterService;

    @Autowired
    private IAdvocateMasterService advocateMasterService;

    @Autowired
    private TbOrganisationService tbOrganisationService;
    
    @Autowired
    private ICaseHearingService caseHearingService;

    private List<Department> loadDepartmentList() {
        List<Department> departments = departmentService
                .getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA); // Active
        // = "A"
        return departments;
    }

    private List<EmployeeBean> loadEmployee() {
        IEmployeeService employeeService = (IEmployeeService) ApplicationContextProvider.getApplicationContext()
                .getBean("employeeService");
        List<EmployeeBean> employee = employeeService
                .getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
        return employee;
    }

    private List<CourtMasterDTO> getCourtMasterList() {

        List<CourtMasterDTO> courtMasterDTOList = courtMasterService
                .getAllActiveCourtMaster(UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> courtMap = courtMasterDTOList.stream().filter(k -> k.getCrtStatus().equals("Y"))
                .collect(Collectors.toMap(CourtMasterDTO::getId, CourtMasterDTO::getCrtName));
        
        if (CollectionUtils.isNotEmpty(this.getModel().getCaseEntryDTOList())) {
            this.getModel().getCaseEntryDTOList().forEach(master -> {
                master.setCourtName(courtMap.get(master.getCrtId()));
            });
        }
        return courtMasterDTOList;
    }

    private List<LocationDTO> getLocationList() {
        List<LocationMasEntity> locationMasEntityList = locationMasService
                .getlocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        List<LocationDTO> locationDTOList = new ArrayList<>();
        for (LocationMasEntity locationMasEntity : locationMasEntityList) {
            LocationDTO locationDTO = new LocationDTO();

            locationDTO.setLocId(locationMasEntity.getLocId());
            locationDTO.setLocName(locationMasEntity.getLocNameEng());
            locationDTO.setLocNameEng(locationMasEntity.getLocNameEng());
            locationDTO.setLocNameReg(locationMasEntity.getLocNameReg());
            locationDTO.setLandmark(locationMasEntity.getLandmark());
            locationDTO.setPincode(locationMasEntity.getPincode());

            locationDTOList.add(locationDTO);
        }
        return locationDTOList;
    }

    @ResponseBody
    @RequestMapping(params = "searchCaseEntry", method = RequestMethod.POST)
    public ModelAndView searchCaseEntry(final HttpServletRequest request,
            @RequestParam(required = false) String cseSuitNo, @RequestParam(required = false) Long cseDeptid,
            @RequestParam(required = false) Long cseCatId1, @RequestParam(required = false) Long cseCatId2,
            @RequestParam(required = false) Long cseTypId, @RequestParam(required = false) Date cseDate,
            @RequestParam(required = false) Long crtId, @RequestParam(required = false) String caseNo) {

        getModel().bind(request);
        CaseEntryModel model = getModel();
        //Task #82122
        //sessionCleanup(request);
        Long parentOrgid = tbOrganisationService.findDefaultOrganisation().getOrgid();
        model.setParentOrgid(parentOrgid);
        if (crtId != null) {
            this.getModel().getCaseEntryDTO().setCrtId(crtId);
        }
        if (cseDeptid != null) {
            this.getModel().getCaseEntryDTO().setCseDeptid(cseDeptid);
        }
        if (StringUtils.isNotEmpty(cseSuitNo)) {
            this.getModel().getCaseEntryDTO().setCseSuitNo(cseSuitNo);
        }
        if (StringUtils.isNotEmpty(caseNo)) {
            this.getModel().getCaseEntryDTO().setCaseNo(caseNo);
           // cseSuitNo = caseNo;
        }
        if (cseCatId1 != null) {
            this.getModel().getCaseEntryDTO().setCseCatId1(cseCatId1);
        }
        if (cseCatId2 != null) {
            this.getModel().getCaseEntryDTO().setCseCatId2(cseCatId2);
        }
        if (cseDate != null) {
            this.getModel().getCaseEntryDTO().setCseDate(cseDate);
        }
        if (cseTypId != null) {
            this.getModel().getCaseEntryDTO().setCseTypId(cseTypId);
        }

        ModelAndView mv = new ModelAndView("searchCaseEntry", MainetConstants.FORM_NAME, this.getModel());

        this.getModel().setCaseEntryDTOList(caseEntryService.searchCaseEntrys(cseSuitNo, cseDeptid, cseCatId1, cseCatId2,
                cseTypId, cseDate, crtId, null, UserSession.getCurrent().getOrganisation().getOrgid(), null, null,caseNo));     
        this.getModel().setAdvocates(model.getAdvocates());
        this.getModel().setDepartments(model.getDepartments());
        this.getModel().setCourtMasterDTOList(getCourtMasterList());
        this.getModel().setLocations(model.getLocations());
        this.getModel().setEmployee(model.getEmployee());
        return mv;
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("CaseEntry.html");
        this.getModel().setCaseEntryDTOList(
                caseEntryService.getAllCaseEntrySummary(UserSession.getCurrent().getOrganisation().getOrgid()));
        Long parentOrgid = tbOrganisationService.findDefaultOrganisation().getOrgid();
     
        this.getModel().setParentOrgid(parentOrgid);
        this.getModel().setLocations(getLocationList());
        this.getModel().setDepartments(loadDepartmentList());
        // check
        this.getModel().setCourtMasterDTOList(getCourtMasterList());
        this.getModel().setAdvocates(this.getModel().getAdvocateMasterList());
       
      
      //  this.getModel().setEmployee(loadEmployee());
        // this.getModel().setOrgList(tbOrganisationService.findAllOrganization(MainetConstants.Common_Constant.ACTIVE_FLAG));
        //List<JudgeMasterDTO> allJudgeMaster = judgeMasterService
              //  .getAllJudgeMaster(UserSession.getCurrent().getOrganisation().getOrgid());

        /*if (CollectionUtils.isNotEmpty(allJudgeMaster)) {
            allJudgeMaster.forEach(judgeDto -> {
                StringBuilder judgeName = new StringBuilder();
                if (StringUtils.isNotBlank(judgeDto.getJudgeFName())) {
                    judgeName.append(judgeDto.getJudgeFName());
                }
                if (StringUtils.isNotBlank(judgeDto.getJudgeMName())) {
                    judgeName.append(" " + judgeDto.getJudgeMName());
                }
                if (StringUtils.isNotBlank(judgeDto.getJudgeLName())) {
                    judgeName.append(" " + judgeDto.getJudgeLName());
                }
                judgeDto.setFulName(judgeName.toString());
            });
        }
*/        //#142899 ENV Flag for tscl env
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL))
			this.getModel().setEnvFlag(MainetConstants.FlagY);
		else
			this.getModel().setEnvFlag(MainetConstants.FlagN);
        //this.getModel().setJudgeNameList(allJudgeMaster);

        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.ADD)
    public ModelAndView addCaseEntry(final HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        CaseEntryModel model = getModel();
        sessionCleanup(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setSaveMode("A");
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
        long parentOrgid = tbOrganisationService.findDefaultOrganisation().getOrgid();
        
        if(orgId == parentOrgid) {
        LookUp valueFromPrefixLookUp = CommonMasterUtility.getValueFromPrefixLookUp("N", MainetConstants.Legal.CSS_PREFIX,UserSession.getCurrent().getOrganisation());
        this.getModel().getCaseEntryDTO().setCseCaseStatusId(valueFromPrefixLookUp.getLookUpId());
        }
        //120788 ENV Flag for tscl env
		 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			 this.getModel().setEnvFlag(MainetConstants.FlagY);
			 //#142357 -> Thane city name should be fetch by default
			 if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
					String [] s = UserSession.getCurrent().getOrganisation().getONlsOrgname().split(" ");
					this.getModel().getCaseEntryDTO().setCseCity(s[0]);
			 }else {
					String [] s = UserSession.getCurrent().getOrganisation().getoNlsOrgnameMar().split(" ");
					this.getModel().getCaseEntryDTO().setCseCity(s[0]); 
			 }
		 }else {
			 this.getModel().setEnvFlag(MainetConstants.FlagN);
		 }
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,
				UserSession.getCurrent().getOrganisation());
		boolean dsclEnv = envLookUpList.stream()
				.anyMatch(env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.DSCL)
						&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
       if (dsclEnv)
    	   this.getModel().setDsclEnv(MainetConstants.FlagY);
	    this.getModel().setAdvocates(model.getAdvocates());
        this.getModel().setDepartments(model.getDepartments());
        this.getModel().setCourtMasterDTOList(model.getCourtMasterDTOList());
        this.getModel().setLocations(model.getLocations());
        this.getModel().setEmployee(model.getEmployee());
        this.getModel().setJudgeNameList(model.getJudgeNameList());
        this.getModel().getCaseEntryDTO().setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setOrgList(tbOrganisationService.findAllOrganization(MainetConstants.Common_Constant.ACTIVE_FLAG));
        this.getModel().setSuitNoList(
                caseEntryService.findSuitNoByOrgid(UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView(MainetConstants.Legal.CASE_ENTRY_FORM, MainetConstants.FORM_NAME,
                this.getModel());
        return mv;

    }

    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
    public ModelAndView editOrViewCaseEntry(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
            @RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
            final HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        CaseEntryModel model = getModel();
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setSaveMode(mode);
        CaseEntryDTO caseEntry = caseEntryService.getCaseEntryById(id);
        if (caseEntry.getOfficeIncharge() != null) {
            IEmployeeService employeeService = (IEmployeeService) ApplicationContextProvider.getApplicationContext()
                    .getBean("employeeService");
            EmployeeBean emp = employeeService.findById(caseEntry.getOfficeIncharge());
            caseEntry.setOicDepartment(emp.getDeptName());
            caseEntry.setOicEmail(emp.getEmpemail());
            caseEntry.setOicMobile(emp.getEmpmobno());

        }
        this.getModel().setCaseEntryDTO(caseEntry);

        List<CaseEntryDetailDTO> caseEntryDetailDTOs = this.getModel().getCaseEntryDTO().getTbLglCasePddetails();
        List<CaseEntryDetailDTO> caseEntryDetailDTOsD = new ArrayList<CaseEntryDetailDTO>();
        List<CaseEntryDetailDTO> caseEntryDetailDTOsP = new ArrayList<CaseEntryDetailDTO>();
        List<OfficerInchargeDetailsDTO> officerInchargeDetailDTO = new ArrayList<OfficerInchargeDetailsDTO>();

        for (CaseEntryDetailDTO caseEntryDetailDTO : caseEntryDetailDTOs) {
            if (caseEntryDetailDTO.getCsedFlag().equalsIgnoreCase("P")) {
                caseEntryDetailDTOsP.add(caseEntryDetailDTO);
            } else if (caseEntryDetailDTO.getCsedFlag().equalsIgnoreCase("D")) {
                caseEntryDetailDTO.setCsedParty(caseEntryDetailDTO.getCsedPartyType());
                caseEntryDetailDTOsD.add(caseEntryDetailDTO);
            }
        }
        

        this.getModel().setOfficerInchargeDetailDTOList(caseEntry.getTbLglCaseOICdetails());

        this.getModel().setPlenfiffEntryDetailDTOList(caseEntryDetailDTOsP);
        this.getModel().setDefenderEntryDetailDTOList(caseEntryDetailDTOsD);

        this.getModel().setArbitoryFeeList(caseEntry.getTbLglArbitoryFee());
        this.getModel()
                .setAttachDocsList(attachDocsService.findByCode(caseEntry.getOrgid(),
                        MainetConstants.Legal.CASE_ENTRY + MainetConstants.FILE_PATH_SEPARATOR + id));
        this.getModel().setOrgList(tbOrganisationService.findAllOrganization(MainetConstants.Common_Constant.ACTIVE_FLAG));

      //120788 ENV Flag for tscl env
		 if(caseEntryService.isTSCLEnvPresent()) {
			 this.getModel().setEnvFlag(MainetConstants.FlagY);
		 }else {
			 this.getModel().setEnvFlag(MainetConstants.FlagN);
		 }
		
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,
				UserSession.getCurrent().getOrganisation());
		boolean dsclEnv = envLookUpList.stream()
				.anyMatch(env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.DSCL)
						&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		if (dsclEnv)
			this.getModel().setDsclEnv(MainetConstants.FlagY);
        ModelAndView mv = new ModelAndView(MainetConstants.Legal.CASE_ENTRY_FORM, MainetConstants.FORM_NAME,
                this.getModel());
      
        this.getModel().setAdvocates(model.getAdvocates());
        this.getModel().setDepartments(model.getDepartments());
        this.getModel().setCourtMasterDTOList(model.getCourtMasterDTOList());
        this.getModel().setLocations(model.getLocations());
        this.getModel().setEmployee(model.getEmployee());
        this.getModel().setJudgeNameList(model.getJudgeNameList());
        this.getModel().setSuitNoList(
                caseEntryService.findSuitNoByOrgid(UserSession.getCurrent().getOrganisation().getOrgid()));
     
        List<Long> result = courtMasterService.getCaseTypeByCourtId(this.getModel().getCaseEntryDTO().getCrtId(), UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> data = new HashMap<>();
            if (result != null && !result.isEmpty()) {
                result.forEach(vdata -> {
                    data.put(vdata, getLockUpCodeValue(vdata));//method to get values);
                });
            }
        
        this.getModel().setCaseTypeList(data);
        
        return mv;
    }

    @RequestMapping(method = { RequestMethod.POST }, params = { "PRINT" })
    public ModelAndView printCaseEntry(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
            @RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
            final HttpServletRequest httpServletRequest) {

        CaseEntryModel model = this.getModel();

        CaseEntryDTO caseEntry = caseEntryService.getCaseEntryById(id);

        CourtMasterDTO court = courtMasterService.getCourtMasterById(caseEntry.getCrtId());

        model.setCaseEntryDetailDTO(caseEntry.getTbLglCasePddetails());
        caseEntry.setCrtName(court.getCrtName());
        // D#76896
        /*
         * if (caseEntry.getOfficeIncharge() != null) { IEmployeeService employeeService = (IEmployeeService)
         * ApplicationContextProvider.getApplicationContext() .getBean("employeeService"); EmployeeBean emp =
         * employeeService.findById(caseEntry.getOfficeIncharge()); caseEntry.setOicEmail(emp.getEmpemail());
         * caseEntry.setOicMobile(emp.getEmpmobno()); model.setEmployeeBean(emp); }
         */

        List<JudgeMasterDTO> allJudgebyCase = caseEntryService.getAllJudgebyCaseId(caseEntry.getCseId(),
                caseEntry.getOrgid());

        model.setJudgeNameList(allJudgebyCase);
        model.setCaseEntryDTO(caseEntry);

        model.setAdvocateMasterDTO(advocateMasterService.getAdvocateMasterById(caseEntry.getAdvId()));

        ModelAndView mv = new ModelAndView("caseDetail", MainetConstants.FORM_NAME, model);

        return mv;

    }
    

    //#117454
    @ResponseBody
    @RequestMapping(params = "checkCaseNoAlreadyPresent", method = RequestMethod.POST)
     public Boolean checkCaseNoAlreadyPresent(final HttpServletRequest request,@RequestParam(required = false) String cseSuitNo) {    
    	  boolean result	= caseEntryService.checkCaseNoAlreadyPresent(cseSuitNo,UserSession.getCurrent().getOrganisation().getOrgid());
         if(result == true) {
			 return true;
		 }
			return false;   	    
     }
    
    @ResponseBody
    @RequestMapping(params = "getCaseAppeal", method = RequestMethod.POST)
    public ModelAndView getCaseAppeal(final HttpServletRequest request){
        getModel().bind(request);
        CaseEntryModel model = getModel();
        this.getModel().setCaseEntryDTOList(
                caseEntryService.getCaseByRefCaseNumber(model.getCaseEntryDTO().getCseRefsuitNo(),UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("CaseAppeal", MainetConstants.FORM_NAME, this.getModel());
        
    }
    
    @ResponseBody
    @RequestMapping(params = "getCaseByCaseNumber", method = RequestMethod.POST)
    public ModelAndView getCaseByCaseNumber(final HttpServletRequest request){
        getModel().bind(request);
        CaseEntryModel model = getModel();
        model.getCaseEntryDTO().setCseSuitNo(model.getCaseEntryDTO().getCseRefsuitNo());
        this.getModel().setCaseEntryDTO(
                caseEntryService.getCaseEntryByCaseNumber(model.getCaseEntryDTO()));
        return new ModelAndView(MainetConstants.Legal.CASE_ENTRY_FORM, MainetConstants.FORM_NAME,
                this.getModel());
        
    }
    
    @ResponseBody
    @RequestMapping(params = "getCaseDetails", method = RequestMethod.POST)
    public ModelAndView getCaseDetails(final HttpServletRequest request){
        getModel().bind(request);
        fileUpload.sessionCleanUpForFileUpload();
        CaseEntryModel model = getModel();
        model.getAttachDocsList().clear();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        List<CaseHearingDTO> hearingDetail = null;
        List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList=null;
        if(orgId.equals(org.getOrgid())) {
            hearingDetail = caseHearingService.getHearingDetailsByCaseId(model.getCaseEntryDTO().getCseId());
            this.getModel().setCaseHearingAttendeeDetailsDTOList(caseHearingService.fetchHearingAttenDetailsByCaseIdUad(model.getCaseEntryDTO().getCseId()));
            tbLglComntRevwDtlDTOList = caseHearingService.fetchHearingComntsDetailsByCaseId(model.getCaseEntryDTO().getCseId(), null);
            this.getModel().setAttachDocsList(attachDocsService.findByCode(hearingDetail.get(0).getOrgid(),
                    MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + model.getCaseEntryDTO().getCseId()));
        }else {
        	hearingDetail = caseHearingService.getHearingDetailByCaseId(orgId,model.getCaseEntryDTO().getCseId());
        	 this.getModel().setCaseHearingAttendeeDetailsDTOList(caseHearingService.fetchHearingAttendeeDetailsByCaseId(model.getCaseEntryDTO().getCseId(),orgId));
        	tbLglComntRevwDtlDTOList  = caseHearingService.fetchHearingComntsDetailsByCaseId(model.getCaseEntryDTO().getCseId(), orgId);
        	this.getModel().setAttachDocsList(attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + model.getCaseEntryDTO().getCseId()));
        }
        Collections.sort(hearingDetail, (h1, h2) -> {
			return Long.compare(h2.getHrId(), h1.getHrId());
		});
		
		if (!CollectionUtils.isEmpty(this.getModel().getAttachDocsList())) {
			Collections.sort(this.getModel().getAttachDocsList(), (h1, h2) -> {
				return Long.compare(h2.getAttId(), h1.getAttId());
			});
		}
		 
        this.getModel().setHearingEntry(hearingDetail);
         this.getModel().setHearing(false);
         this.getModel().setTbLglComntRevwDtlDTOList(tbLglComntRevwDtlDTOList);
         this.getModel().setHearingMode("HV");
         this.getModel().setCaseHearingFlag("Y");
         this.getModel().setSaveMode("V");
        return new ModelAndView("CaseHearingDetails", MainetConstants.FORM_NAME, this.getModel());
        
    }
    
    @RequestMapping(method = { RequestMethod.POST }, params = "getCaseType")
    public @ResponseBody Map<Long, String> getCaseType(@RequestParam("id") Long crtId,
            final HttpServletRequest httpServletRequest) {
        List<Long> result = courtMasterService.getCaseTypeByCourtId(crtId, UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> data = new HashMap<>();
            if (result != null && !result.isEmpty()) {
                result.forEach(vdata -> {
                    data.put(vdata, getLockUpCodeValue(vdata));//method to get values);
                });
            }
        return data;
    }
   
    private String getLockUpCodeValue(Long id) {
    	 LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(id, UserSession.getCurrent().getOrganisation().getOrgid(), "TOC");
         //String otherFielddowntimeLookup =  downtimeLookup.getOtherField();
         String caseTypeLookup = lookup.getLookUpDesc();
    	return caseTypeLookup;
    }
}
