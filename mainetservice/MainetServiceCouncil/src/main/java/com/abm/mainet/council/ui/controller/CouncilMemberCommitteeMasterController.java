package com.abm.mainet.council.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.service.ICouncilMemberCommitteeMasterService;
import com.abm.mainet.council.service.ICouncilMemberMasterService;
import com.abm.mainet.council.ui.model.CouncilMemberCommitteeMasterModel;

@Controller
@RequestMapping(MainetConstants.Council.COMMITTEE_MEMBER_MASTER_URL)
public class CouncilMemberCommitteeMasterController extends AbstractFormController<CouncilMemberCommitteeMasterModel> {

    @Autowired
    ICouncilMemberCommitteeMasterService councilMemberCommitteeMasterService;

    @Autowired
    private ICouncilMemberMasterService councilMemberMasterService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        CouncilMemberCommitteeMasterModel model = this.getModel();
        setCommonFields(this.getModel());
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setCouMemMasterDtoList(memberSequence());
        model.setCommonHelpDocs("CouncilMemberCommitteeMaster.html");
        /* this.getModel().setDesignationList(designationService.getDesignByDeptId(null, this.getModel().getOrgId())); */
        model.setCouncilMemberCommitteeMasterDtoList(
                councilMemberCommitteeMasterService.searchCouncilMemberCommitteeMasterData(null, null, orgId,
                        MainetConstants.Council.ACTIVE_STATUS));
        return defaultResult();
    }

    private void setCommonFields(CouncilMemberCommitteeMasterModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setOrgId(orgId);
    }

    @ResponseBody
    @RequestMapping(params = "searchCommitteeMember", method = RequestMethod.POST)
    public CouncilRestResponseDto getCouncilAgendaList(@RequestParam("memberId") final Long memberId,
            @RequestParam("committeeTypeId") Long committeeTypeId, final HttpServletRequest request) {
        getModel().bind(request);
        CouncilRestResponseDto response = new CouncilRestResponseDto();
        List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList = new ArrayList<>();
        councilMemberCommitteeMasterDtoList = councilMemberCommitteeMasterService
                .searchCouncilMemberCommitteeMasterData(memberId, committeeTypeId,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.Council.ACTIVE_STATUS);
        response.setCouncilMemberCommitteeMasterDtoList(councilMemberCommitteeMasterDtoList);
        return response;
    }

    @RequestMapping(params = "addCommittee", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addCommittee(final HttpServletRequest request) {
        getModel().bind(request);

        CouncilMemberCommitteeMasterModel model = this.getModel();
        List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList = new ArrayList<>();
        // model.setCouMemMasterDtoList(councilMemberMasterService.fetchAll());
        /*
         * List<CouncilMemberMasterDto> memberCommitteeMasterDtoList = new ArrayList<>(); memberCommitteeMasterDtoList =
         * councilMemberMasterService.fetchAll(); Comparator<CouncilMemberMasterDto> comparing =
         * Comparator.comparing(CouncilMemberMasterDto::getOtherField, Comparator.nullsLast(Comparator.naturalOrder()));
         * Collections.sort(memberCommitteeMasterDtoList, comparing);
         */
        model.setCouncilMemberCommitteeMasterDtoList(councilMemberCommitteeMasterDtoList);
        model.setCouMemMasterDtoList(memberSequence());
        model.setSaveMode(MainetConstants.CommonConstants.ADD);
        return new ModelAndView("addCouncilCommitteeMaster", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getMembers", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public List<CouncilMemberMasterDto> getMember(final HttpServletRequest request) {
        getModel().bind(request);
        CouncilMemberCommitteeMasterModel model = this.getModel();
        Organisation organisation = new Organisation();
        organisation.setOrgid(model.getOrgId());
        model.getCouMemMasterDtoList().forEach(dto -> {
            if (dto.getCouPartyAffilation() != 0)
                dto.setCouPartyAffDesc(CommonMasterUtility.getCPDDescription(dto.getCouPartyAffilation().longValue(),
                        MainetConstants.MENU.E));

            if (dto.getCouMemberType() != 0)
                dto.setCouMemberTypeDesc(CommonMasterUtility.getCPDDescription(dto.getCouMemberType().longValue(),
                        MainetConstants.MENU.E));
            /*
             * List<LookUp> lookupListLevel1 = new ArrayList<LookUp>(); lookupListLevel1
             * =CommonMasterUtility.getNextLevelData("EWZ", 1,model.getOrgId());
             */
            String lookUpdec = CommonMasterUtility.getHierarchicalLookUp(dto.getCouEleWZId1(), model.getOrgId())
                    .getLookUpDesc();
            dto.setCouEleWZ1Desc(lookUpdec);
        });
        return model.getCouMemMasterDtoList();
    }

    /*
     * @RequestMapping(params = "getDesignations", method = { RequestMethod.POST, RequestMethod.GET })
     * @ResponseBody public List<DesignationBean> getDesignations(final HttpServletRequest request) { getModel().bind(request);
     * CouncilMemberCommitteeMasterModel model = this.getModel(); return model.getDesignationList(); }
     */

    @RequestMapping(params = "getDesignations", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public List<LookUp> getDesignations(final HttpServletRequest request) {
        getModel().bind(request);
        List<LookUp> lookups = CommonMasterUtility.getLookUps("CDS", UserSession.getCurrent().getOrganisation());
        return lookups;
    }

    @RequestMapping(params = "editCouncilCommitteeMasterData", method = RequestMethod.POST)
    public ModelAndView editCouncilAgendaMasterData(@RequestParam("committeeTypeId") final Long committeeTypeId) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.EDIT);
        List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList = new ArrayList<>();
        Boolean dataForMeetingCommitteeMember = false;
        councilMemberCommitteeMasterDtoList = councilMemberCommitteeMasterService
                .fetchMappingMemberListByCommitteeTypeId(committeeTypeId, dataForMeetingCommitteeMember,
                        MainetConstants.Council.ACTIVE_STATUS, MainetConstants.STATUS.INACTIVE,
                        UserSession.getCurrent().getOrganisation().getOrgid());

        CouncilMemberCommitteeMasterDto councilMemberCommitteeMasterDto = councilMemberCommitteeMasterService
                .getCommitteeMemberData(committeeTypeId, UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.ACTIVE_STATUS);
        /* this.getModel().setCouMemMasterDtoList(councilMemberMasterService.fetchAll()); */
        this.getModel().setCouMemMasterDtoList(
                councilMemberMasterService.fetchAll(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setCouncilMemberCommitteeMasterDto(councilMemberCommitteeMasterDto);

        this.getModel().setCouncilMemberCommitteeMasterDtoList(councilMemberCommitteeMasterDtoList);

        return new ModelAndView("addCouncilCommitteeMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "viewCouncilCommitteeMasterData", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView viewCouncilAgendaMasterData(@RequestParam("committeeTypeId") final Long committeeTypeId,
            final HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.VIEW);
        List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList = new ArrayList<>();
        Boolean dataForMeetingCommitteeMember = false;
        councilMemberCommitteeMasterDtoList = councilMemberCommitteeMasterService
                .fetchMappingMemberListByCommitteeTypeId(committeeTypeId, dataForMeetingCommitteeMember,
                        MainetConstants.Council.ACTIVE_STATUS, MainetConstants.STATUS.INACTIVE,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        CouncilMemberCommitteeMasterDto councilMemberCommitteeMasterDto = councilMemberCommitteeMasterService
                .getCommitteeMemberData(committeeTypeId, UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.ACTIVE_STATUS);
        this.getModel().setCouMemMasterDtoList(
                councilMemberMasterService.fetchAll(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setCouncilMemberCommitteeMasterDto(councilMemberCommitteeMasterDto);
        this.getModel().setCouncilMemberCommitteeMasterDtoList(councilMemberCommitteeMasterDtoList);
        return new ModelAndView("addCouncilCommitteeMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "memberExistInDissolveDate", method = RequestMethod.POST)
    public List<String> getMemberDissolveDatePassed(@RequestParam("memberIds") String memberIds,
            @RequestParam("committeeTypeId") Long committeeTypeId) {
        // identify which one not exist in dissolve date from
        // TB_CMT_COUNCIL_MEMBER_COMMITTEE table
        String splitMemberIds[] = memberIds.split(MainetConstants.operator.COMMA);
        List<Long> memberIdsArray = new ArrayList<>();
        for (String id : splitMemberIds) {
            memberIdsArray.add(Long.valueOf(id));
        }
        List<String> messages = councilMemberCommitteeMasterService.fetchMemberNotExistInDissolveDate(memberIdsArray,
                committeeTypeId, MainetConstants.Council.ACTIVE_STATUS,
                this.getModel().getCouncilMemberCommitteeMasterDto().getOrgId());
        return messages;
    }

    // Method used to sort members based on MET prefix other value
    public List<CouncilMemberMasterDto> memberSequence() {
        List<CouncilMemberMasterDto> memberCommitteeMasterDtoList = new ArrayList<>();
        memberCommitteeMasterDtoList = councilMemberMasterService
                .fetchAll(UserSession.getCurrent().getOrganisation().getOrgid());
        Comparator<CouncilMemberMasterDto> comparing = Comparator.comparing(CouncilMemberMasterDto::getOtherField,
                Comparator.nullsLast(Comparator.naturalOrder()));
        Collections.sort(memberCommitteeMasterDtoList, comparing);
        return memberCommitteeMasterDtoList;
    }
    
    //Defect #40602
    @ResponseBody
    @RequestMapping(params = "checkCommitteeAlreadyPresent", method = RequestMethod.POST)
    public String checkCommitteeAlreadyPresent(final HttpServletRequest request) {
    	getModel().bind(request);
    	int memberCount ;
        this.getModel().getCouncilMemberCommitteeMasterDtoList().forEach(memberList -> {
            if (memberList.getMemberStatus() == null) {
            	this.getModel().getCommitteeActiveMemberList().add(memberList);
            } else if (memberList.getMemberStatus().equals(MainetConstants.STATUS.ACTIVE)) {
            	this.getModel().getCommitteeActiveMemberList().add(memberList);
            }
        });
        if(this.getModel().getSaveMode().equalsIgnoreCase(MainetConstants.CommonConstants.ADD)) {
        	 memberCount = this.getModel().getCouncilMemberCommitteeMasterDtoList().size();
        }else {
         memberCount = this.getModel().getCommitteeActiveMemberList().size();
        }
        LookUp lookupCPT = CommonMasterUtility.getNonHierarchicalLookUpObject(
        		this.getModel().getCouncilMemberCommitteeMasterDto().getCommitteeTypeId(), UserSession.getCurrent().getOrganisation());
        if(lookupCPT.getOtherField().isEmpty()) {       	       
        	return MainetConstants.FlagE;
        }
        int memberCPTCount = Integer.parseInt(lookupCPT.getOtherField());  
        if (memberCount > memberCPTCount) {
        this.getModel().setCouncilMemberCommitteeMasterDtoList(null);
        return MainetConstants.FlagY;       
        }      
		return MainetConstants.FlagT;          	
    }
}
