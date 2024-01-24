package com.abm.mainet.rti.ui.controller;

import java.util.Date;

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

import com.abm.mainet.rti.ui.model.RtiStatusReportModel;

@Controller
@RequestMapping("/RtiStatusReport.html")
public class RtiStatusReportController extends AbstractFormController<RtiStatusReportModel> {

	@Autowired
	private TbDepartmentService departmentService;

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		RtiStatusReportModel model = this.getModel();
		model.setDeparmnetDtoList(departmentService.findAllMappedDepartments(currentOrgId));
		return index();
	}

	@RequestMapping(params = "GetRtiReports", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewReport(@RequestParam("DepartId") Long DepartId,
			@RequestParam("rtiMode") Long rtiMode, @RequestParam("rtiStatus") String rtiStatus,
			@RequestParam("rtifromDate") Date rtifromDate, @RequestParam("rtitoDate") Date rtitoDate,
			@RequestParam("SLAid") String SLAid, final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		Object[] ob = null;
		ob = iFinancialYearService.getFinacialYearByDate(rtifromDate);

		String error = null;
		int comparision1 = rtifromDate.compareTo((Date) ob[1]);
		int comparision2 = rtitoDate.compareTo((Date) ob[2]);
		if (comparision1 == -1 || comparision2 == 1) {
			error = "f";
		}

		String fmDate = Utility.dateToString(rtifromDate, "yyyy-MM-dd");
		String tooDate = Utility.dateToString(rtitoDate, "yyyy-MM-dd");
		if (error == null) {
			return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=RTIReportStatus.rptdesign&__title=&false&OrgId="
					+ currentOrgId + "&Department=" + DepartId + "&Mode=" + rtiMode + "&RTIStatus=" + rtiStatus
					+ "&FromDate=" + fmDate + "&ToDate=" + tooDate + "&SLA=" + SLAid;

		}
		return error;
	}
}
