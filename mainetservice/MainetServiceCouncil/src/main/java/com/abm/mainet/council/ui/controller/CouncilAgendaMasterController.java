package com.abm.mainet.council.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilAgendaMasterDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.service.ICouncilAgendaMasterService;
import com.abm.mainet.council.service.ICouncilMeetingMasterService;
import com.abm.mainet.council.service.ICouncilMemberCommitteeMasterService;
import com.abm.mainet.council.service.ICouncilProposalMasterService;
import com.abm.mainet.council.ui.model.CouncilAgendaMasterModel;

@Controller
@RequestMapping(MainetConstants.Council.AGENDA_MASTER_URL)
public class CouncilAgendaMasterController extends AbstractFormController<CouncilAgendaMasterModel> {

    @Autowired
    private ICouncilAgendaMasterService councilAgendaMasterService;

    @Autowired
    private ICouncilProposalMasterService councilProposalMasterService;

    @Autowired
    private ICouncilMeetingMasterService councilMeetingMasterService;

    @Autowired
    private ICouncilMemberCommitteeMasterService memberCommitteeService;
    
    @Autowired
    private TbDepartmentService iTbDepartmentService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        setCommonFields(this.getModel());
        this.getModel().setCommonHelpDocs("CouncilAgendaMaster.html");
        /*
         * Date month[] = Utility.getMonthFromToDates(new Date()); String fromDate = Utility.dateToString(month[0]); String toDate
         * = Utility.dateToString(month[1]);
         */
        this.getModel().setCouAgendaMasterDtoList(councilAgendaMasterService.searchCouncilAgendaMasterData(null, null,
                null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        String proposalApproveStatus = MainetConstants.Council.Proposal.APPROVE_STATUS;
        List<CouncilProposalMasterDto> proposalList = councilProposalMasterService.fetchProposalListByStatus(
                proposalApproveStatus, UserSession.getCurrent().getOrganisation().getOrgid());
        if (!proposalList.isEmpty()) {
            this.getModel().setProposalPresent(true);
        }
        return defaultResult();
    }

    private void setCommonFields(CouncilAgendaMasterModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setOrgId(orgId);
    }

    @ResponseBody
    @RequestMapping(params = "searchCouncilAgenda", method = RequestMethod.POST)
    public CouncilRestResponseDto getCouncilAgendaList(@RequestParam("committeeTypeId") final Long committeeTypeId,
            @RequestParam("agendaNo") final String agendaNo, @RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, @RequestParam("meetingInvitation") final Boolean meetingInvitation,
            final HttpServletRequest request) {

        getModel().bind(request);
        CouncilRestResponseDto response = new CouncilRestResponseDto();
        List<CouncilAgendaMasterDto> councilAgendaMemberDtoList = new ArrayList<>();
        List<CouncilAgendaMasterDto> meetingCouncilAgendaMemberDtoList = new ArrayList<>();
        councilAgendaMemberDtoList = councilAgendaMasterService
                .searchCouncilAgendaMasterData(committeeTypeId, agendaNo, fromDate, toDate,
                        UserSession.getCurrent().getOrganisation().getOrgid());

        if (meetingInvitation) {
            // if meeting invitation request coming than search agenda Id in TB_CMT_COUNCIL_MEETING_MAST and ignore if found
            councilAgendaMemberDtoList.forEach(agenda -> {
                boolean agendaExist = councilMeetingMasterService.checkAgendaPresentInMeeting(agenda.getAgendaId(),
                        MainetConstants.Common_Constant.ACTIVE_FLAG, UserSession.getCurrent().getOrganisation().getOrgid());
                if (!agendaExist) {
                    // check committeeType is in dissolve date or not in table TB_CMT_COUNCIL_MEMBER_COMMITTEE
                    Boolean committeePresent = memberCommitteeService.checkCommitteeTypeInDissolveDateByOrg(
                            agenda.getCommitteeTypeId(),
                            UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.Council.ACTIVE_STATUS);
                    if (committeePresent) {
                        CouncilAgendaMasterDto dto = new CouncilAgendaMasterDto();
                        BeanUtils.copyProperties(agenda, dto);
                        meetingCouncilAgendaMemberDtoList.add(dto);
                    }
                }

            });
            response.setCouncilAgendaMasterDtoList(meetingCouncilAgendaMemberDtoList);
        } else {
            response.setCouncilAgendaMasterDtoList(councilAgendaMemberDtoList);
        }
        return response;
    }

    @RequestMapping(params = "addCouncilAgenda", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addCouncilAgenda(final HttpServletRequest request) {
        getModel().bind(request);
        CouncilAgendaMasterModel model = this.getModel();
        this.getModel().setSaveMode(MainetConstants.CommonConstants.ADD);
        // fetch proposal list with approve one only
        String proposalApproveStatus = MainetConstants.Council.Proposal.APPROVE_STATUS;
        List<CouncilProposalMasterDto> proposalList = councilProposalMasterService.fetchProposalListByStatus(
                proposalApproveStatus, UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setProposalDtoList(proposalList);
        this.getModel().setCommitteeTypeFilterList(fetchCommitteeTypePrefixList());
        model.setDepartmentsList(iTbDepartmentService.findMappedDepartments( UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("addCouncilAgendaMaster", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "editCouncilAgendaMasterData", method = RequestMethod.POST)
    public ModelAndView editCouncilAgendaMasterData(@RequestParam("agendaId") final Long agendaId) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.EDIT);
        List<CouncilProposalMasterDto> finalProposalList = new ArrayList<>();
        CouncilAgendaMasterDto councilAgendaMasterDto = councilAgendaMasterService
                .getCouncilAgendaMasterByAgendaId(agendaId);
        this.getModel().setCouAgendaMasterDto(councilAgendaMasterDto);
        String proposalApproveStatus = MainetConstants.Council.Proposal.APPROVE_STATUS;
        // List<CouncilProposalMasterDto> proposalList = new ArrayList<>();
        List<CouncilProposalMasterDto> proposalDtoList = councilProposalMasterService.fetchProposalListByStatus(
                proposalApproveStatus, UserSession.getCurrent().getOrganisation().getOrgid());
        proposalDtoList.forEach(dto -> {
			if (dto.getCommitteeType().equals(councilAgendaMasterDto.getCommitteeTypeId())) {
				finalProposalList.add(dto);
			}
		});

        List<CouncilProposalMasterDto> agendaProposalDtoList = new ArrayList<>();
        agendaProposalDtoList = councilProposalMasterService.findAllProposalByAgendaId(agendaId);
        this.getModel().setProposalDtoList(finalProposalList);
        this.getModel().setAgendaProposalDtoList(agendaProposalDtoList);
        this.getModel().setDepartmentsList(iTbDepartmentService.findMappedDepartments( UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("addCouncilAgendaMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "viewCouncilAgendaMasterData", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView viewCouncilAgendaMasterData(@RequestParam("agendaId") final Long agendaId,
            final HttpServletRequest request) {
    	 List<CouncilProposalMasterDto> finalProposalList = new ArrayList<>();
        this.getModel().setSaveMode(MainetConstants.CommonConstants.VIEW);
        CouncilAgendaMasterDto councilAgendaMasterDto = councilAgendaMasterService
                .getCouncilAgendaMasterByAgendaId(agendaId);
        String proposalApproveStatus = MainetConstants.Council.Proposal.APPROVE_STATUS;
        // List<CouncilProposalMasterDto> proposalList = new ArrayList<>();
        List<CouncilProposalMasterDto> proposalDtoList = councilProposalMasterService.fetchProposalListByStatus(
                proposalApproveStatus, UserSession.getCurrent().getOrganisation().getOrgid());
        
        proposalDtoList.forEach(dto -> {
			if (dto.getCommitteeType().equals(councilAgendaMasterDto.getCommitteeTypeId())) {
				finalProposalList.add(dto);
			}
		});
        /* Long committeeTypeId = councilAgendaMasterDto.getCommitteeTypeId(); */
        this.getModel().setProposalDtoList(finalProposalList);
        this.getModel().setCouAgendaMasterDto(councilAgendaMasterDto);
        List<CouncilProposalMasterDto> agendaProposalDtoList = new ArrayList<>();
        agendaProposalDtoList = councilProposalMasterService.findAllProposalByAgendaId(agendaId);
        this.getModel().setAgendaProposalDtoList(agendaProposalDtoList);
        this.getModel().setDepartmentsList(iTbDepartmentService.findMappedDepartments( UserSession.getCurrent().getOrganisation().getOrgid()));

        return new ModelAndView("addCouncilAgendaMaster", MainetConstants.FORM_NAME, this.getModel());

    }

    // Defect #34123 ->this is to check member present against that committee or not
    public List<LookUp> fetchCommitteeTypePrefixList() {
        List<LookUp> committeeTypeList = CommonMasterUtility.getLookUps("CPT", UserSession.getCurrent().getOrganisation());
        List<LookUp> committeeTypeFilterList = new ArrayList<>();
        committeeTypeList.forEach(committeeTypePrefix -> {
            Boolean checkMemberPresentInCommitteeByOrg = memberCommitteeService.checkMemberPresentInCommitteeByOrg(
                    committeeTypePrefix.getLookUpId(), UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.FlagA);
            if (checkMemberPresentInCommitteeByOrg)
                committeeTypeFilterList.add(committeeTypePrefix);
        });
        return committeeTypeFilterList;
    }
    
    //120544 -> to check committee Dissolve or not
    @ResponseBody
    @RequestMapping(params = "checkCommitteeDissolve", method = { RequestMethod.POST, RequestMethod.GET })
    Boolean checkCommitteeDissolve(@RequestParam("committeeTypeId") final Long committeeTypeId,
            final HttpServletRequest request) {
    	 // check committeeType is in dissolve date or not in table TB_CMT_COUNCIL_MEMBER_COMMITTEE
    	Boolean committeePresent = memberCommitteeService.checkCommitteeTypeInDissolveDateByOrg(committeeTypeId,
                UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.Council.ACTIVE_STATUS);
        if (committeePresent == true) {
        	return true;
        }
        return false;
    }
    
    @RequestMapping(params = "getproposalByDept", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView getproposalByDept(@RequestParam("deptId") final Long deptId,final HttpServletRequest request) {
        getModel().bind(request);
        CouncilAgendaMasterModel model = this.getModel();
        model.setDeptId(deptId);
        //this.getModel().setSaveMode(MainetConstants.CommonConstants.ADD);
        List<CouncilProposalMasterDto> finalProposalList = new ArrayList<>();
        String proposalApproveStatus = MainetConstants.Council.Proposal.APPROVE_STATUS;
        List<CouncilProposalMasterDto> proposalList = councilProposalMasterService.fetchProposalListByStatus(
                proposalApproveStatus, UserSession.getCurrent().getOrganisation().getOrgid());
        proposalList.forEach(dto -> {
			if (dto.getProposalDepId() == deptId) {
				finalProposalList.add(dto);
			}
		});
        this.getModel().setProposalDtoList(finalProposalList);
        this.getModel().setCommitteeTypeFilterList(fetchCommitteeTypePrefixList());
        model.setDepartmentsList(iTbDepartmentService.findMappedDepartments( UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("addCouncilAgendaMaster", MainetConstants.FORM_NAME, model);
    }
    
    
    @RequestMapping(params = "getproposalByCommittee", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView getproposalByCommittee(@RequestParam("committeeTypeId") final Long committeeTypeId,final HttpServletRequest request) {
        getModel().bind(request);
        CouncilAgendaMasterModel model = this.getModel();
        CouncilProposalMasterDto dt= new CouncilProposalMasterDto();
        
        //this.getModel().setSaveMode(MainetConstants.CommonConstants.ADD);
        List<CouncilProposalMasterDto> finalProposalList = new ArrayList<>();
        String proposalApproveStatus = MainetConstants.Council.Proposal.APPROVE_STATUS;
        List<CouncilProposalMasterDto> proposalList = councilProposalMasterService.fetchProposalListbyCommiteeStatus(
                proposalApproveStatus, UserSession.getCurrent().getOrganisation().getOrgid());
        
        proposalList.forEach(dto -> {
        	Long comm=committeeTypeId;
			if (dto.getCommitteeType().equals(comm)) {
				finalProposalList.add(dto);
			}
		});
        this.getModel().setProposalDtoList(finalProposalList);
        this.getModel().setCommitteeTypeFilterList(fetchCommitteeTypePrefixList());
        this.getModel().getCouAgendaMasterDto().setCommitteeTypeId(committeeTypeId);
      
        return new ModelAndView("addCouncilAgendaMaster", MainetConstants.FORM_NAME, model);
    }


}
