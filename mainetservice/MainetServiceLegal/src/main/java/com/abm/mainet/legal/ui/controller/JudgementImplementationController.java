/**
 * 
 */
package com.abm.mainet.legal.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.CaseEntryArbitoryFeeDto;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.JudgementDetailDTO;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.IJudgementDetailService;
import com.abm.mainet.legal.service.IJudgementMasterService;
import com.abm.mainet.legal.ui.model.JudgementImplementationmodel;

/**
 * @author cherupelli.srikanth
 *
 */
@Controller
@RequestMapping("/JudgementImplementation.html")
public class JudgementImplementationController extends AbstractFormController<JudgementImplementationmodel> {

    @Autowired
    private ICaseEntryService caseEntryService;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    IJudgementDetailService judgementDetailService;
    
    @Autowired
    IJudgementMasterService judgementMasterService;
    
    @Autowired
	TbOrganisationService  tbOrganisationService;

    
    String CASE_STATUS = "P";

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        Long parentOrgid = tbOrganisationService.findDefaultOrganisation().getOrgid();
        List<CaseEntryDTO> caseEntryDtoList = caseEntryService
                .getAllCaseEntrySummary(UserSession.getCurrent().getOrganisation().getOrgid());
        if (CollectionUtils.isNotEmpty(caseEntryDtoList)) {
            //List<CaseEntryDTO> caseEntryList = new ArrayList<>();
            /*for (CaseEntryDTO caseEntryDTO : caseEntryDtoList) {
                LookUp lookUpDto = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                        caseEntryDTO.getCseCaseStatusId(), UserSession.getCurrent().getOrganisation().getOrgid(),
                        PrefixConstants.LegalPrefix.CASE_STATUS);*/
                //#117750
                //145206
                /*if (StringUtils.equals(lookUpDto.getLookUpCode(), CASE_STATUS)) {*/
                    //caseEntryList.add(caseEntryDTO);
				/* } */
			/* } */
            this.getModel().setCaseEntryList(caseEntryDtoList);
        }
        this.getModel().setParentOrgid(parentOrgid);
        this.getModel().setDepartmentList(this.getModel().getAllDepartmentList());
        this.getModel().setCourtNameList(this.getModel().getAllCourtMasterList());
        this.getModel().setLocationList(this.getModel().getAllLocationList());
        this.getModel().setEmployeeList(this.getModel().getAllEmployee());
        this.getModel().setJudgeNameList(this.getModel().getAllJudgeNames());
        this.getModel().setAdvocateList(this.getModel().getAllAdvocateMasterList());
        return index();
    }

    /**
     * @param id
     * @param mode
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = MainetConstants.CommonConstants.EDIT, method = { RequestMethod.POST })
    public ModelAndView editOrViewJudgementImplementation(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
            @RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
            HttpServletRequest httpServletRequest) {
        this.getModel().setSaveMode(mode);
        this.getModel().bind(httpServletRequest);
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        JudgementImplementationmodel model = this.getModel();
        CaseEntryDTO caseEntry = caseEntryService.getCaseEntryById(id);
        if (orgId.equals(org.getOrgid())) {
			this.getModel().setOrgFlag(MainetConstants.FlagY);

		} else {
			this.getModel().setOrgFlag(MainetConstants.FlagN);
		}
      /*  if (orgId.equals(org.getOrgid())) {
        JudgementDetailDTO judgeDetail = judgementDetailService.getJudgeDetailsByCaseId(id);
        if (judgeDetail != null) {
             this.getModel().setJudgeDetailDto(judgeDetail);
             if (CollectionUtils.isNotEmpty(judgeDetail.getAttendeeDtoList())) {
                 this.getModel().setAttendeeList(judgeDetail.getAttendeeDtoList());
             }
         }
        }*/
        JudgementDetailDTO judgeDetail = judgementDetailService.getJudgeDetailsByCaseId(id);
       if (judgeDetail != null) {
            this.getModel().setJudgeDetailDto(judgeDetail);
            if (CollectionUtils.isNotEmpty(judgeDetail.getAttendeeDtoList())) {
                this.getModel().setAttendeeList(judgeDetail.getAttendeeDtoList());
            }
        }
        if (CollectionUtils.isNotEmpty(caseEntry.getTbLglArbitoryFee())) {
            List<CaseEntryArbitoryFeeDto> arbitotyFeeList = new ArrayList<>();
            caseEntry.getTbLglArbitoryFee().forEach(det -> {
                arbitotyFeeList.add(det);
            });
            this.getModel().setArbitoryFeeList(arbitotyFeeList);
        }

        //List<CaseHearingDTO> caseHearingList = this.getModel().getAllCaseHearingDetails(orgId, id);
        List<CaseHearingDTO> caseHearingDto = this.getModel().fetchAllCaseHearingDetails(id);
        
        caseHearingDto.forEach(dto->{
        	this.getModel()
            .setAttachDocsList(attachDocsService.findByCode(dto.getOrgid(),
                    MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + dto.getHrId()));
        	this.getModel().getCaseHearingListHistory().add(dto);
        });
        if (CollectionUtils.isNotEmpty(caseHearingDto)) {
            this.getModel().setCurrentCaseHearingList(caseHearingDto.get(caseHearingDto.size() - 1));
            this.getModel().setCaseHearingListHistory(caseHearingDto);
        }
        //Defect #106442
        List<AttachDocs> attach = new ArrayList<>();
        if(!caseHearingDto.isEmpty()) {
        	attach=attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + caseHearingDto.get(caseHearingDto.size() -1).getHrId());
        }
        
        List<AttachDocs> attachsList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(attach))
        attachsList.add(attach.get(attach.size() - 1));
        this.getModel().setAttachDocsList(attachsList);
        this.getModel().setCaseEntryDTO(caseEntry);
        this.getModel().setAdvocateList(model.getAdvocateList());
        this.getModel().setJudgeNameList(model.getJudgeNameList());
        this.getModel().setLocationList(model.getLocationList());
        this.getModel().setEmployeeList(model.getEmployeeList());
        this.getModel().setCourtNameList(model.getCourtNameList());
        this.getModel().setDepartmentList(model.getDepartmentList());
        //added by rahul.chaubey
        //sets the date from the judgementMaster service to the case entry dto
        this.getModel().setOfficerInchargeDetailDTOList(caseEntry.getTbLglCaseOICdetails());
       // caseEntry.setJudgementMasterDate(judgementMasterService.getJudgementMasterdate(orgId, id));
        caseEntry.setJudgementMasterDate(judgementMasterService.getJudgementMasterdateById(id));
       //#132275
        this.getModel().setCaseAttachDocsList(attachDocsService.findByCode(orgId,
                MainetConstants.Legal.CASE_ENTRY + MainetConstants.FILE_PATH_SEPARATOR + id));   
        return new ModelAndView("judgementImplementationForm", MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = "searchCaseEntry", method = RequestMethod.POST)
    public ModelAndView searchCaseEntry(final HttpServletRequest request,
            @RequestParam(required = false) String cseSuitNo, @RequestParam(required = false) Long cseDeptid,
            @RequestParam(required = false) Long cseCatId1, @RequestParam(required = false) Long cseCatId2,
            @RequestParam(required = false) Long cseTypId, @RequestParam(required = false) Date cseDate,
            @RequestParam(required = false) Long crtId) {

        getModel().bind(request);
        JudgementImplementationmodel model = getModel();

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

        ModelAndView mv = new ModelAndView("JudgementImplementationSearch", MainetConstants.FORM_NAME, this.getModel());

        this.getModel().setCaseEntryList(caseEntryService.searchCaseEntry(cseSuitNo, cseDeptid, cseCatId1, cseCatId2,
                cseTypId, cseDate, crtId, null, UserSession.getCurrent().getOrganisation().getOrgid(), null,null));
        this.getModel().setAdvocateList(model.getAdvocateList());
        this.getModel().setJudgeNameList(model.getJudgeNameList());
        this.getModel().setLocationList(model.getLocationList());
        this.getModel().setEmployeeList(model.getEmployeeList());
        this.getModel().setCourtNameList(model.getCourtNameList());
        this.getModel().setDepartmentList(model.getDepartmentList());

        return mv;
    }

    @ResponseBody
    @RequestMapping(params = "viewHearingHistory", method = { RequestMethod.POST })
    public ModelAndView viewCaseHearingHistory(HttpServletRequest request, @RequestParam Long caseId) {
    	Organisation org = tbOrganisationService.findDefaultOrganisation();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();        
        if(orgId.equals(org.getOrgid())) {
            List<CaseHearingDTO> caseHearingDtoList = this.getModel()
                    .fetchAllCaseHearingDetails(caseId);
            this.getModel().setCaseHearingListHistory(caseHearingDtoList);
        }else {
        	 List<CaseHearingDTO> caseHearingList = this.getModel()
                     .getAllCaseHearingDetails(UserSession.getCurrent().getOrganisation().getOrgid(), caseId);
             this.getModel().setCaseHearingListHistory(caseHearingList);
        }    
        //Defect #106442
        this.getModel()
        .setAttachDocsList(attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.Legal.HEARING_DATE + MainetConstants.FILE_PATH_SEPARATOR + caseId));
        return new ModelAndView("viewCaseHearingHistory", MainetConstants.FORM_NAME, getModel());

    }
}
