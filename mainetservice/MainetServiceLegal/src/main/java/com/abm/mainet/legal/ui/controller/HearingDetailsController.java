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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.TbLglComntRevwDtlDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICaseHearingService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.IJudgeMasterService;
import com.abm.mainet.legal.ui.model.HearingDetailsModel;

@Controller
@RequestMapping("/HearingDetails.html")
public class HearingDetailsController extends AbstractFormController<HearingDetailsModel> {

    @Autowired
    private ICaseEntryService caseEntryService;

    @Autowired
    private ICaseHearingService caseHearingService;

    @Autowired
    private ICourtMasterService courtMasterService;

    @Autowired
    private ILocationMasService locationMasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IJudgeMasterService judgeMasterService;

    @Autowired
    private IAdvocateMasterService advocateMasterService;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
	TbOrganisationService  tbOrganisationService;

    
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setHearing(false);
        Long parentOrgid = tbOrganisationService.findDefaultOrganisation().getOrgid();
        Long closedCaseStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagC,
                PrefixConstants.LegalPrefix.CASE_STATUS, UserSession.getCurrent().getOrganisation()).getLookUpId();
        this.getModel().setCaseEntryDTOList(caseEntryService.searchCaseEntry(null, null, null, null, null, null,
                null, closedCaseStatus, UserSession.getCurrent().getOrganisation().getOrgid(), null,MainetConstants.FlagH));
        this.getModel().setCommonHelpDocs("HearingDetails.html");
        this.getModel().setParentOrgid(parentOrgid);
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "searchCaseEntry", method = RequestMethod.POST)
    public ModelAndView searchCaseEntry(final HttpServletRequest request,
            @RequestParam(required = false) String cseSuitNo,
            @RequestParam(required = false) Long cseDeptid,
            @RequestParam(required = false) Long cseCatId1,
            @RequestParam(required = false) Long cseCatId2,
            @RequestParam(required = false) Long cseTypId,
            @RequestParam(required = false) Date cseDate,
            @RequestParam(required = false) Long crtId) {

        sessionCleanup(request);
        if (crtId != null) {
            this.getModel().getCaseEntryDTO().setCrtId(crtId);
        }
        if (cseDeptid != null) {
            this.getModel().getCaseEntryDTO().setCseDeptid(cseDeptid);
        }
        if (StringUtils.isNotEmpty(cseSuitNo)) {
            this.getModel().getCaseEntryDTO().setCseSuitNo(cseSuitNo);
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

        ModelAndView mv = new ModelAndView("searchCaseEntry",
                MainetConstants.FORM_NAME, this.getModel());
        Long closedCaseStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagC,
                PrefixConstants.LegalPrefix.CASE_STATUS, UserSession.getCurrent().getOrganisation()).getLookUpId();
        this.getModel().setCaseEntryDTOList(
                caseEntryService.searchCaseEntry(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId, cseDate,
                        crtId, closedCaseStatus, UserSession.getCurrent().getOrganisation().getOrgid(), null,MainetConstants.FlagH));

        mv.addObject("departments", loadDepartmentList());
        mv.addObject("locations", getLocationList());
        mv.addObject("courtMasterDTOList", getCourtMasterList());
        mv.addObject("advocates", getAdvocateMasterList());
        return mv;
    }

    @RequestMapping(params = MainetConstants.Legal.HEARING_DATE, method = RequestMethod.POST)
    public ModelAndView ediHearingDate(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
            @RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setHearingMode(mode);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        CaseHearingDTO dto = caseHearingService.getCaseHearingById(id);
        List<CaseHearingDTO> hearingDetail = null;
        if(orgId.equals(org.getOrgid())) {
            hearingDetail = caseHearingService.getHearingDetailsByCaseId(dto.getCseId());
        }else {
        	hearingDetail = caseHearingService.getHearingDetailByCaseId(orgId,dto.getCseId());
        }
     
        List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList=null;
        tbLglComntRevwDtlDTOList  = caseHearingService.fetchHearingComntsDetailsByHearingId(id, orgId);
        
        this.getModel().setCaseHearingAttendeeDetailsDTOList(caseHearingService.fetchHearingAttendeeDetailsByHearingId(id,orgId));
        //Set comment and review and hearing details for setting hearing date against each comment
        //sort to get latest hearing details record to set for comnt and review object on jsp
		Collections.sort(hearingDetail, (h1, h2) -> {
			return Long.compare(h2.getHrId(), h1.getHrId());
		});
		this.getModel().setHearingEntry(hearingDetail);
		
        
       // if(orgId.equals(org.getOrgid())) {this.getModel().setOrgFlag(MainetConstants.FlagY);}else {this.getModel().setOrgFlag(MainetConstants.FlagN);}        
        this.getModel().setHearingEntity(dto);
        this.getModel().setHearing(true);
        /*
         * List<CaseHearingDTO> hearingDetail = caseHearingService.getHearingDetailByCaseId(orgId, dto.getCseId());
         * this.getModel().setHearingEntry(hearingDetail);
         */
        this.getModel().setTbLglComntRevwDtlDTOList(tbLglComntRevwDtlDTOList);
        this.getModel().setCaseHearingAttendeeDetailsDTOList(caseHearingService.fetchHearingAttenDetailsByHearingId(id));
        ModelAndView mv = new ModelAndView(MainetConstants.Legal.HEARING_DETAILS_FORM, MainetConstants.FORM_NAME,
                this.getModel());
        mv.addObject("departments", loadDepartmentList());
        mv.addObject("judges", judgeMasterService.getAllJudgeMaster(dto.getOrgid()));
        mv.addObject("advocates", advocateMasterService.getAllAdvocateMasterByOrgid(dto.getOrgid()));
        mv.addObject("hrDtlForComntRevw",dto);
        mv.addObject("courtMasterDTOList", getCourtMasterList());
        return mv;
    }

    @RequestMapping(params = MainetConstants.CommonConstants.EDIT, method = RequestMethod.POST)
    public ModelAndView editOrViewCaseEntry(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
            @RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(mode);
        this.getModel().setHearingMode("HV");
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        CaseEntryDTO dto = caseEntryService.getCaseEntryById(id);
        List<CaseHearingDTO> hearingDetail = null;
        List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList=null;
        this.getModel().setCaseEntryDTO(dto);
        if(orgId.equals(org.getOrgid())) {
        hearingDetail = caseHearingService.getHearingDetailsByCaseId(dto.getCseId());
        Map<Long, AttachDocs> attachDocsMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(hearingDetail)) {
        hearingDetail.forEach(hearingDto ->{
              this.getModel().setOrgFlag(MainetConstants.FlagY);              
        	 List<AttachDocs> list = (attachDocsService.findByCode(hearingDto.getOrgid(),
                     MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + hearingDto.getHrId()));
        	 if(list == null)
        		 attachDocsMap.put(hearingDto.getHrId(), null);
        	 else
        		 attachDocsMap.put(hearingDto.getHrId(),list.get(0));
             this.getModel().setCaseAttachDocsList(attachDocsService.findByCode(dto.getOrgid(),
                     MainetConstants.Legal.CASE_ENTRY + MainetConstants.FILE_PATH_SEPARATOR + id));         
             this.getModel().getHearingEntry().add(hearingDto);
        });
        this.getModel().setAttachDocsMap(attachDocsMap);
        }else {this.getModel().setCaseAttachDocsList(attachDocsService.findByCode(dto.getOrgid(),
                MainetConstants.Legal.CASE_ENTRY + MainetConstants.FILE_PATH_SEPARATOR + id));}
        this.getModel().setHearingEntry(hearingDetail);
        // fetch data from TB_LGL_HEARINGATTENDEE_DETAILS table by cseId
        // here cseId = id
        this.getModel().setCaseHearingId(id);
        this.getModel().setCaseHearingAttendeeDetailsDTOList(caseHearingService.fetchHearingAttenDetailsByCaseIdUad(id));
        //Set comment and review
        //tbLglComntRevwDtlDTOList = caseHearingService.fetchHearingComntsDetailsByCaseId(id, null);
        }else {
        hearingDetail = caseHearingService.getHearingDetailByCaseId(orgId,dto.getCseId());
        this.getModel().setOrgFlag(MainetConstants.FlagN);   
        this.getModel().setHearingEntry(hearingDetail);
       /// List<AttachDocs> attachDocsList = new ArrayList<>();
        Map<Long, AttachDocs> attachDocsMap = new HashMap<>();
        for(CaseHearingDTO hearingDate : hearingDetail) {
        	List<AttachDocs> attachDocsList = attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + hearingDate.getHrId());
        	if(attachDocsList.isEmpty())
        		attachDocsMap.put(hearingDate.getHrId(), null);
        	else {
        		attachDocsMap.put(hearingDate.getHrId(), attachDocsList.get(0));
        	}
        		
        }
        this.getModel().setAttachDocsMap(attachDocsMap);
        this.getModel().setCaseAttachDocsList(attachDocsService.findByCode(dto.getOrgid(),
                MainetConstants.Legal.CASE_ENTRY + MainetConstants.FILE_PATH_SEPARATOR + id)); 
        
        
       
        
        
        // fetch data from TB_LGL_HEARINGATTENDEE_DETAILS table by cseId
        // here cseId = id
        this.getModel().setCaseHearingId(id);
        //this.getModel().setCaseHearingAttendeeDetailsDTOList(caseHearingService.fetchHearingAttendeeDetailsByCaseId(id,orgId));
        //Set comment and review and hearing details for setting hearing date against each comment
        //sort to get latest hearing details record to set for comnt and review object on jsp
		Collections.sort(hearingDetail, (h1, h2) -> {
			return Long.compare(h2.getHrId(), h1.getHrId());
		});
	
		
		if (!CollectionUtils.isEmpty(this.getModel().getAttachDocsList())) {
				Collections.sort(this.getModel().getAttachDocsList(), (h1, h2) -> {
					return Long.compare(h1.getAttId(), h2.getAttId());
				});
			}
		//tbLglComntRevwDtlDTOList  = caseHearingService.fetchHearingComntsDetailsByCaseId(id, orgId);
        
        }
        //#137591
        this.getModel().getCaseEntryDTO().setNoteShowFlag(MainetConstants.FlagY);
        this.getModel().setTbLglComntRevwDtlDTOList(tbLglComntRevwDtlDTOList);
        ModelAndView mv = new ModelAndView(MainetConstants.Legal.HEARING_DETAILS_FORM, MainetConstants.FORM_NAME,
                this.getModel());
        mv.addObject("departments", loadDepartmentList());
        mv.addObject("locations", getLocationList());
        mv.addObject("courtMasterDTOList", getCourtMasterList());
        return mv;
    }

    private List<Department> loadDepartmentList() {
        List<Department> departments = departmentService.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.FlagA); // Active = "A"
        return departments;
    }

    private List<CourtMasterDTO> getCourtMasterList() {
        List<CourtMasterDTO> courtMasterDTOList = courtMasterService
                .getAllActiveCourtMaster(UserSession.getCurrent().getOrganisation().getOrgid());
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

    private List<AdvocateMasterDTO> getAdvocateMasterList() {
        List<AdvocateMasterDTO> advocateMasterDTOList = advocateMasterService
                .getAllAdvocateMasterByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> advocateMap = advocateMasterDTOList.stream()
                .collect(Collectors.toMap(AdvocateMasterDTO::getAdvId, AdvocateMasterDTO::getAdvFirstNm));
        if (CollectionUtils.isNotEmpty(this.getModel().getCaseEntryDTOList())) {
            this.getModel().getCaseEntryDTOList().forEach(master -> {
                master.setCrtName(advocateMap.get(master.getAdvId()));
            });
        }
        return advocateMasterDTOList;
    }
    
    @RequestMapping(params = "HearingDateForCase", method = RequestMethod.POST)
    public ModelAndView ediHearingDateForCase(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
            @RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
            final HttpServletRequest httpServletRequest) {
        this.getModel().setHearingMode(mode);
        List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList=null;
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        CaseHearingDTO dto = caseHearingService.getCaseHearingById(id);
        List<CaseHearingDTO> hearingDetail = null;
        if(orgId.equals(org.getOrgid())) {
            hearingDetail = caseHearingService.getHearingDetailsByCaseId(dto.getCseId());
            this.getModel().setCaseHearingAttendeeDetailsDTOList(caseHearingService.fetchHearingAttenDetailsByCaseIdUad(dto.getCseId()));
            tbLglComntRevwDtlDTOList = caseHearingService.fetchHearingComntsDetailsByCaseId(dto.getCseId(), null);
            this.getModel().setAttachDocsList(attachDocsService.findByCode(hearingDetail.get(0).getOrgid(),
                    MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + dto.getCseId()));
        }else {
        	hearingDetail = caseHearingService.getHearingDetailByCaseId(orgId,dto.getCseId());
        	this.getModel().setCaseHearingAttendeeDetailsDTOList(caseHearingService.fetchHearingAttendeeDetailsByCaseId(dto.getCseId(),orgId));
        	tbLglComntRevwDtlDTOList  = caseHearingService.fetchHearingComntsDetailsByCaseId(dto.getCseId(), orgId);
        	this.getModel().setAttachDocsList(attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + dto.getCseId()));
        }
        //Set comment and review and hearing details for setting hearing date against each comment
        //sort to get latest hearing details record to set for comnt and review object on jsp
		Collections.sort(hearingDetail, (h1, h2) -> {
			return Long.compare(h2.getHrId(), h1.getHrId());
		});
		if (!CollectionUtils.isEmpty(this.getModel().getAttachDocsList())) {
			Collections.sort(this.getModel().getAttachDocsList(), (h1, h2) -> {
				return Long.compare(h2.getAttId(), h1.getAttId());
			});
		}
		this.getModel().setHearingEntry(hearingDetail);
		this.getModel().setCaseHearingFlag("Y");
        this.getModel().setHearingEntity(dto);
        this.getModel().setHearing(true);
        this.getModel().setTbLglComntRevwDtlDTOList(tbLglComntRevwDtlDTOList);
        ModelAndView mv = new ModelAndView("CaseHearingDetails", MainetConstants.FORM_NAME,
                this.getModel());
        mv.addObject("departments", loadDepartmentList());
        mv.addObject("judges", judgeMasterService.getAllJudgeMaster(dto.getOrgid()));
        mv.addObject("advocates", advocateMasterService.getAllAdvocateMasterByOrgid(dto.getOrgid()));
        mv.addObject("hrDtlForComntRevw",hearingDetail.get(0));
        mv.addObject("courtMasterDTOList", getCourtMasterList());
        return mv;
    }
    
    
  
}
