package com.abm.mainet.council.ui.controller;

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
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.service.ICouncilMemberMasterService;
import com.abm.mainet.council.ui.model.CouncilMemberMasterModel;

/**
 * @author aarti.paan
 * @since 01 April 2019
 */
@Controller
@RequestMapping(MainetConstants.Council.MemberMaster.MEMBER_MASTER_URL)
public class CouncilMemberMasterController extends AbstractFormController<CouncilMemberMasterModel> {

    @Autowired
    private ICouncilMemberMasterService councilMemberMasterService;

    @Autowired
    private IFileUploadService fileUpload;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        setCommonFields(this.getModel());
        // getModel().setCommonHelpDocs("CouncilManagement.html");
        this.getModel().setCouMemMasterDtoList(councilMemberMasterService.searchCouncilMasterData(null,
                null, null, UserSession.getCurrent().getOrganisation().getOrgid(), null, null,
                UserSession.getCurrent().getLanguageId()));

        this.getModel().setCommonHelpDocs("CouncilMemberMaster.html");
        // Changes done as per RFA
        /*
         * this.getModel().setDesignationList(designationService.getDesignByDeptId(null, this.getModel().getOrgId()));
         * this.getModel().getCouMemMasterDtoList().forEach(ob -> { this.getModel().getDesignationList().forEach(obdep -> { if
         * (ob.getCouDesgId() == obdep.getDsgid()) { ob.setCouDesgName(obdep.getDsgname()); } }); });
         */
        return defaultResult();
    }

    private void setCommonFields(CouncilMemberMasterModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setOrgId(orgId);
    }

    // Add Council Member Master
    @RequestMapping(params = MainetConstants.Council.MemberMaster.ADD_COUNCIL_MEMBER, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView addCouncilMember(final HttpServletRequest request) {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().bind(request);
        CouncilMemberMasterModel model = this.getModel();
        model.setSaveMode(MainetConstants.Council.CREATE);
       //#135462
		if (UserSession.getCurrent().getOrganisation().getDefaultStatus()
				.equalsIgnoreCase(MainetConstants.Organisation.SUPER_ORG_STATUS)) 
		this.getModel().setParentOrgFlag(MainetConstants.FlagY);
		else
		 this.getModel().setParentOrgFlag(MainetConstants.FlagN);
        return new ModelAndView(MainetConstants.Council.MemberMaster.ADD_COUNCIL_MEMBER_MASTER, MainetConstants.FORM_NAME, model);
    }

    // Search Council Member Master
    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.MemberMaster.SEARCH_COUNCIL_MEMBER, method = RequestMethod.POST)
    public CouncilRestResponseDto getCouncilMemberList(
            @RequestParam(MainetConstants.Council.MemberMaster.MEMBER_TYPE) final Long couMemberType,
            @RequestParam(MainetConstants.Council.MemberMaster.MEMBER_NAME) final String couMemName,
            @RequestParam(MainetConstants.Council.MemberMaster.Party_AFFILATION) final Long couPartyAffilation,
            @RequestParam(MainetConstants.Council.MemberMaster.ELECTION_WARD_ZONE_ID1) final Long couEleWZId1,
            @RequestParam(MainetConstants.Council.MemberMaster.ELECTION_WARD_ZONE_ID2) final Long couEleWZId2,
            final HttpServletRequest request) {
        getModel().bind(request);
        CouncilMemberMasterModel model = this.getModel();
        CouncilRestResponseDto response = new CouncilRestResponseDto();

        List<CouncilMemberMasterDto> councilMemberDto = councilMemberMasterService.searchCouncilMasterData(couMemName,
                couMemberType, couPartyAffilation, UserSession.getCurrent().getOrganisation().getOrgid(), couEleWZId1,
                couEleWZId2, UserSession.getCurrent().getLanguageId());
        response.setCouncilMemberDto(councilMemberDto);
        response.setDesignation(model.getDesignationList());
        return response;
    }

    // Edit Council Member Master
    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.MemberMaster.EDIT_COUNCIL_MEMBER_MASTER, method = RequestMethod.POST)
    public ModelAndView editCouncilMemberMasterData(
            @RequestParam(MainetConstants.Council.MemberMaster.COUNCIL_ID) final Long couId) {
        this.getModel().setSaveMode(MainetConstants.Council.EDIT);
        CouncilMemberMasterDto memberMatserDto = councilMemberMasterService.getCouncilMemberMasterByCouId(couId);
        this.getModel().setCouMemMasterDto(memberMatserDto);
        // get attached document

        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.MemberMaster.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH + couId);
        this.getModel().setAttachDocsList(attachDocs);
       //#135462
		if (UserSession.getCurrent().getOrganisation().getDefaultStatus()
				.equalsIgnoreCase(MainetConstants.Organisation.SUPER_ORG_STATUS)) 
		this.getModel().setParentOrgFlag(MainetConstants.FlagY);
		else
		 this.getModel().setParentOrgFlag(MainetConstants.FlagN);
        return new ModelAndView(MainetConstants.Council.MemberMaster.ADD_COUNCIL_MEMBER_MASTER, MainetConstants.FORM_NAME,
                this.getModel());
    }

    // View Council Member Master
    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.MemberMaster.VIEW_COUNCIL_MEMBER, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView viewCouncilMemberMasterData(
            @RequestParam(MainetConstants.Council.MemberMaster.COUNCIL_ID) final Long couId, final HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.Council.VIEW);
        // get attached document

        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.MemberMaster.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH + couId);
        this.getModel().setAttachDocsList(attachDocs);
        CouncilMemberMasterDto memberMatserDto = councilMemberMasterService.getCouncilMemberMasterByCouId(couId);
        this.getModel().setCouMemMasterDto(memberMatserDto);
        return new ModelAndView(MainetConstants.Council.MemberMaster.ADD_COUNCIL_MEMBER_MASTER, MainetConstants.FORM_NAME,
                this.getModel());

    }

}
