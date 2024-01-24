package com.abm.mainet.legal.ui.controller;

import java.util.ArrayList;
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
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICaseHearingService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.ui.model.HearingDateModel;

@Controller
@RequestMapping("/HearingDate.html")
public class HearingDateController extends AbstractFormController<HearingDateModel> {

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
    private IAdvocateMasterService advocateMasterService;

    @Autowired
    private IFileUploadService fileUpload;
    
    @Autowired
	TbOrganisationService  tbOrganisationService;

    @Autowired
    private IAttachDocsService attachDocsService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long closedCaseStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagC,
                PrefixConstants.LegalPrefix.CASE_STATUS, UserSession.getCurrent().getOrganisation()).getLookUpId();
        if(orgId.equals(org.getOrgid())) {
        	this.getModel().setCaseEntryDTOList(caseEntryService.searchCaseEntry(null, null, null, null, null, null,
                    null, closedCaseStatus, org.getOrgid(), null, MainetConstants.FlagH));
        }else {
        	this.getModel().setCaseEntryDTOList(caseEntryService.searchCaseEntry(null, null, null, null, null, null,
                    null, closedCaseStatus, orgId, null,null));
        }     
        this.getModel().setParentOrgid(org.getOrgid());
        this.getModel().setCommonHelpDocs("HearingDate.html");
        return defaultResult();
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

    @RequestMapping(params = MainetConstants.CommonConstants.ADD, method = RequestMethod.POST)
    public ModelAndView addCaseEntry(final HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
        CaseHearingDTO hearing = new CaseHearingDTO();
        hearing.setHrDate(new Date());
        hearing.setHrStatus(CommonMasterUtility.getValueFromPrefixLookUp("SH", "HSC", UserSession.getCurrent().getOrganisation())
                .getLookUpId());
        this.getModel().getHearingEntry().add(hearing);
        return new ModelAndView(MainetConstants.Legal.HEARING_DATE_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = MainetConstants.CommonConstants.EDIT, method = RequestMethod.POST)
    public ModelAndView editOrViewCaseEntry(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
            @RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
            final HttpServletRequest httpServletRequest) {
         this.getModel().setSaveMode(mode);
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        CaseEntryDTO dto = caseEntryService.getCaseEntryById(id);
        if (orgId.equals(org.getOrgid())) {
			this.getModel().setOrgFlag(MainetConstants.FlagY);

		} else {
			this.getModel().setOrgFlag(MainetConstants.FlagN);
		}
        this.getModel().setCaseEntryDTO(dto);
        if(orgId.equals(org.getOrgid())) {
        List<CaseHearingDTO> hearingDetail = caseHearingService.getHearingDetailsByCaseId(dto.getCseId());
        if(CollectionUtils.isNotEmpty(hearingDetail)) {
				
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
        }else {
        this.getModel().setCaseAttachDocsList(attachDocsService.findByCode(dto.getOrgid(),
                MainetConstants.Legal.CASE_ENTRY + MainetConstants.FILE_PATH_SEPARATOR + id));
        }
        this.getModel().setHearingEntry(hearingDetail);
        }else {
        	 List<CaseHearingDTO> hearingDetail = caseHearingService.getHearingDetailByCaseId(orgId,dto.getCseId());
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
           this.getModel().setHearingEntry(hearingDetail);
        }
         ModelAndView mv = new ModelAndView(MainetConstants.Legal.HEARING_DATE_FORM, MainetConstants.FORM_NAME, this.getModel());
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

}
