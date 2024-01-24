package com.abm.mainet.legal.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;

import com.abm.mainet.legal.ui.model.CasePendencyReportModel;

@Controller
@RequestMapping("/CasePendencyReport.html")

public class CasePendencyReportController extends AbstractFormController<CasePendencyReportModel> {

	@Autowired
	private TbDepartmentService departmentService;

	@Autowired
	private IAdvocateMasterService iadvocateMasterservice;

	/*
	 * @Autowired private IFinancialYearService iFinancialYearService;
	 */

	/*
	 * @Autowired private ICourtMasterService courtMasterService;
	 */

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		CasePendencyReportModel model = this.getModel();
		model.setDepartmentList(departmentService.findMappedDepartments(currentOrgId));
		// model.setAdvocateName(iadvocateMasterservice.getAllAdvocateMaster(currentOrgId));
		List<AdvocateMasterDTO> advName = iadvocateMasterservice.getAllAdvocateMaster(currentOrgId);
		for (AdvocateMasterDTO advMasterDto : advName) {
			advMasterDto.setFullName(advMasterDto.getAdvFirstNm() + " " + advMasterDto.getAdvLastNm());
		}
		model.setAdvocateName(advName);

		/*
		 * model.setCourtMasterListDTO(courtMasterService.getAllActiveCourtMaster(
		 * currentOrgId));
		 */

		return index();
	}

	@RequestMapping(params = "GetCaseReports", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewCasePendencyReportSheet(@RequestParam("csedepartment") Long csedepartment,
			@RequestParam("courtType") Long courtType, @RequestParam("caseAdvName") Long caseAdvName,
			@RequestParam("caseStatus") Long caseStatus, @RequestParam("caseCategory") Long caseCategory,
			@RequestParam("caseSubCategory") Long caseSubCategory, @RequestParam("csefrmDate") Date csefrmDate,
			@RequestParam("csetoDate") Date csetoDate, @RequestParam("caseType") Long caseType,
			final HttpServletRequest request) {
		// sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		String fromdate = Utility.dateToString(csefrmDate, "yyyy-MM-dd");
		String todate = Utility.dateToString(csetoDate, "yyyy-MM-dd");

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=CasePendencyReport.rptdesign&__title=&false&OrgId="
				+ currentOrgId + "&Department=" + csedepartment + "&CourtType=" + courtType + "&AdvocateName="
				+ caseAdvName + "&CaseStatus=" + caseStatus + "&CaseCategory=" + caseCategory + "&CaseSubCategory="
				+ caseSubCategory + "&ActiveFromDate=" + fromdate + "&ActiveToDate=" + todate + "&CaseType=" + caseType;
	}

}