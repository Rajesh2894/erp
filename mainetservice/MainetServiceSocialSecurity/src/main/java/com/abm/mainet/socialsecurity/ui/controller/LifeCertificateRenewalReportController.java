package com.abm.mainet.socialsecurity.ui.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

import com.abm.mainet.socialsecurity.service.IPensionSchemeMasterService;
import com.abm.mainet.socialsecurity.ui.model.LifeCertificateRenewalReportModel;

@Controller
@RequestMapping("/LifeCertificate.html")
public class LifeCertificateRenewalReportController extends AbstractFormController<LifeCertificateRenewalReportModel> {

	@Autowired
	private ServiceMasterService iServiceMasterService;

	@Autowired
	TbDepartmentService tbDepartmentService;

	@Autowired
	private IPensionSchemeMasterService iPensionSchemeMasterService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		LifeCertificateRenewalReportModel model = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long DepartId = tbDepartmentService
				.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		int langId = UserSession.getCurrent().getLanguageId();

		final com.abm.mainet.common.utility.LookUp lookUpFieldstatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A, PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldstatus.getLookUpId();
		model.setViewList(iPensionSchemeMasterService.getAllData(currentOrgId, DepartId, activeStatusId));
		model.setServiceList(iServiceMasterService.findAllActiveServicesWhichIsNotActual(currentOrgId, DepartId,
				activeStatusId, "N"));

		return index();
	}

	@RequestMapping(params = "GetLifeCertificate", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewForm(@RequestParam("pSchemeName") Long pSchemeName,
			@RequestParam("pStatus") String pStatus, final HttpServletRequest request) {
		sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL
				+ "=LifeCertificateRenewalReport.rptdesign&__title=&false&OrgId=" + currentOrgId + "&PenSchemeName="
				+ pSchemeName + "&Status=" + pStatus;
	}
}
