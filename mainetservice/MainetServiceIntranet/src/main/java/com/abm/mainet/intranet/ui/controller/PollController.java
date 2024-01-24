package com.abm.mainet.intranet.ui.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.intranet.dao.PollDao;
import com.abm.mainet.intranet.domain.Poll;
import com.abm.mainet.intranet.domain.PollDetails;
import com.abm.mainet.intranet.service.PollDetService;
import com.abm.mainet.intranet.ui.model.PollModel;

@Controller
@RequestMapping(value = "/PollCreation.html")
public class PollController extends AbstractFormController<PollModel>{
	
    @Autowired
    private PollDao PollDao;
    
	@Autowired
	private PollDetService pollDetService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		PollModel appModel = this.getModel();
		model.addAttribute("departments", loadDepartmentList());
		return new ModelAndView("PollCreation", MainetConstants.FORM_NAME, appModel);
	}
	
	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class);
		List<Department> departments = departmentService.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}
	
	@RequestMapping(params = "getPollCreateQuestion", method = {RequestMethod.POST})
	public ModelAndView pollCreationQuestion(@RequestParam(value ="department",required = false) final Long department , @RequestParam("pollName") final String pollName, 
			@RequestParam("pollFromDate") final Date pollFromDate, @RequestParam("pollToDate") final Date pollToDate, @RequestParam("pollid") final Long pollid, @RequestParam("mode") final String mode,
			final HttpServletRequest request, final Model model) {
		PollModel appModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		appModel.savePollCreationData(department, pollName, pollFromDate, pollToDate, pollid, mode,orgId);
		return new ModelAndView("PollCreationQuestion", MainetConstants.FORM_NAME, appModel);
	}
	
	@RequestMapping(params = "getPollCreateQuestionPage", method = {RequestMethod.POST})
	public ModelAndView getPollCreateQuestionPage(final HttpServletRequest request, final Model model) {
		PollModel appModel = this.getModel();
		return new ModelAndView("PollCreationQuestion", MainetConstants.FORM_NAME, appModel);
	}
	
	@RequestMapping(params = "getPollCreateForm", method = {RequestMethod.POST})
	public ModelAndView pollCreationForm(final Model model, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, Long pollId) {
		PollModel appModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<PollDetails> pollDetId = pollDetService.getPollByIdFromPollDet(orgId);
		Long pollIdGetId = pollDetId.get(0).getId();
		Date FromDt = pollDetId.get(0).getPollFromDate();
		Date ToDt = pollDetId.get(0).getPollToDate();
		String pollNm = pollDetId.get(0).getPollName();
		
		if (pollIdGetId != null) {
			appModel.getPollRequest().setPollid(pollIdGetId);
			appModel.getPollRequest().setFromDate(FromDt);
			appModel.getPollRequest().setToDate(ToDt);
			appModel.getPollRequest().setPollName(pollNm);
		}
		
		appModel.getPollRequest().setMode("E");
		model.addAttribute("departments", loadDepartmentList());
		return new ModelAndView("PollCreation", MainetConstants.FORM_NAME, appModel);
	}
	
	
	@RequestMapping(params = "submitPollStatus", method = {RequestMethod.POST})
	public ModelAndView submitPollStatus(@RequestParam("pollStatus") final String pollStatus, final HttpServletRequest request, final Model model) {
		PollModel appModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.addAttribute("departments", loadDepartmentList());
		List<Poll> pollId = PollDao.getPollData(orgId);
		Long pollIdToUpdateStatus = pollId.get(0).getPollid();
		appModel.submitPollStatus(pollStatus, pollIdToUpdateStatus);
		return new ModelAndView("PollCreation", MainetConstants.FORM_NAME, appModel);
	}
	
	
}
