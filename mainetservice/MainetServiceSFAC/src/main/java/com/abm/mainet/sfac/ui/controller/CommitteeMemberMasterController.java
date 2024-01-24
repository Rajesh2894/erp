/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CommitteeMemberMasterDto;
import com.abm.mainet.sfac.service.CommitteeMemberService;
import com.abm.mainet.sfac.ui.model.CommitteeMemberMasterModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.COMMITTEE_MEMBER_MASTER_URL)
public class CommitteeMemberMasterController extends AbstractFormController<CommitteeMemberMasterModel> {

	private static final Logger log = Logger.getLogger(CommitteeMemberMasterController.class);

	@Autowired
	private CommitteeMemberService comMomService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setComMemDtoList(
				comMomService.findAllCommitteeDet(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView(MainetConstants.Sfac.COMMITTEE_MEMBER_MAST_SUMMARY, MainetConstants.FORM_NAME,
				getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchFpoDetails(@RequestParam("committeeTypeId") final Long committeeTypeId,
			@RequestParam("comMemberId") final Long comMemberId, final HttpServletRequest httpServletRequest) {
		List<CommitteeMemberMasterDto> comMemDtoList = new ArrayList<>();
		log.info("searchFpoDetails start");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		comMemDtoList = comMomService.getDetByCommiteeIdAndName(committeeTypeId, comMemberId, orgId);
		this.getModel().setComMemDtoList(comMemDtoList);
		this.getModel().getComMemDto().setCommitteeTypeId(committeeTypeId);
		this.getModel().getComMemDto().setComMemberId(comMemberId);
		log.info("searchFpoDetails end");
		return new ModelAndView(MainetConstants.Sfac.COMMITTEE_MEMBER_SUMMARY_VALIDN, MainetConstants.FORM_NAME,
				getModel());
	}

	@ResponseBody
	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public ModelAndView formForCreate(final HttpServletRequest httpServletRequest, Model model) {
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode("A");
		return new ModelAndView(MainetConstants.Sfac.COMMITTEE_MEMBER_MAST_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(@RequestParam("comMemberId") final Long comMemberId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		log.info("editOrView start");
		this.getModel().setSaveMode(mode);
		CommitteeMemberMasterDto dto = comMomService.findById(comMemberId);
		this.getModel().setComMemDto(dto);
		log.info("editOrView end");
		return new ModelAndView(MainetConstants.Sfac.COMMITTEE_MEMBER_MAST_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}
}
