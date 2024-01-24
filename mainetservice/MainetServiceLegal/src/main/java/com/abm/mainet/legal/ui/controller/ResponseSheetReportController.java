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
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.ui.model.CasePendencyReportModel;

@Controller
@RequestMapping("/ResponseSheetReport.html")
public class ResponseSheetReportController extends AbstractFormController<CasePendencyReportModel> {

	@Autowired
	private TbDepartmentService departmentService;

	@Autowired
	private IAdvocateMasterService iadvocateMasterservice;

	@Autowired
	private ICaseEntryService caseEntryService;

	/*
	 * @Autowired private IFinancialYearService iFinancialYearService;
	 * 
	 * @Resource private SecondaryheadMasterService secondaryheadMasterService;
	 * 
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

		model.setCaseEntryListDto(caseEntryService.getAllCaseEntry(currentOrgId));

		/*
		 * model.setCourtMasterListDTO(courtMasterService.getAllActiveCourtMaster(
		 * currentOrgId));
		 */

		return index();
	}

	@RequestMapping(params = "GetResponseReports", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewCasePendencyReportSheet(@RequestParam("csedepartment") Long csedepartment,
			/* @RequestParam("courtType") Long courtType, */ @RequestParam("caseAdvName") Long caseAdvName,
			@RequestParam("caseCategory") Long caseCategory, @RequestParam("caseSubCategory") Long caseSubCategory,
			@RequestParam("csefrmDate") Date csefrmDate, @RequestParam("csetoDate") Date csetoDate,
			@RequestParam("cseSuitNo") String cseSuitNo, final HttpServletRequest request) {
		// sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		String fromdate = Utility.dateToString(csefrmDate, "yyyy-MM-dd");
		String todate = Utility.dateToString(csetoDate, "yyyy-MM-dd");

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL
				+ "=ResponseSheetReport.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId
				+ "&Department=" + csedepartment /* + "&CourtType=" + courtType */ + "&AdvocateName=" + caseAdvName
				+ "&CaseCategory=" + caseCategory + "&CaseSubCategory=" + caseSubCategory + "&ActiveFromDate="
				+ fromdate + "&ActiveToDate=" + todate + "&CaseNo=" + cseSuitNo;
	}

	/* CASE TRACKER REPORT */

	@RequestMapping(params = "GetCaseTrackerReport", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getPrefixReport(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		CasePendencyReportModel model = this.getModel();
		model.setCaseEntryListDto(caseEntryService.getAllCaseEntry(currentOrgId));

		return customResult("CaseTrackerReportForm");
	}

	@RequestMapping(params = "getCaseReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewCaseTrackerSheet(@RequestParam("casefrmDate") Date casefrmDate,
			@RequestParam("casetoDate") Date casetoDate, @RequestParam("caseSuitNo") String caseSuitNo,
			@RequestParam("casStatus") String casStatus, final HttpServletRequest request) {
		// sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		/*
		 * Object[] ob = null; ob =
		 * iFinancialYearService.getFinacialYearByDate(casefrmDate);
		 * 
		 * String error = null; int comparision1 = casefrmDate.compareTo((Date) ob[1]);
		 * int comparision2 = casetoDate.compareTo((Date) ob[2]); if (comparision1 == -1
		 * || comparision2 == 1) { error = "f"; }
		 */
		String fromdate = Utility.dateToString(casefrmDate, "yyyy-MM-dd");
		String todate = Utility.dateToString(casetoDate, "yyyy-MM-dd");

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=CaseTrackerReport.rptdesign&OrgId=" + currentOrgId
				+ "&ActiveFromDate=" + fromdate + "&ActiveToDate=" + todate + "&CaseNumber=" + caseSuitNo + "&Status="
				+ casStatus;

	}

}
