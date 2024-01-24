package com.abm.mainet.property.ui.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.ui.model.DetailOutstandingRegisterModel;

@Controller
@RequestMapping("/mutationRegisterReport.html")
public class MutationRegisterReportController extends AbstractFormController<DetailOutstandingRegisterModel> {

	/*
	 * @Autowired private ViewPropertyDetailsService viewPropertyDetailsService;
	 */

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);

		return index();
	}

	@RequestMapping(params = "GetMutationReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("mnFromdt") Date mnFromdt, @RequestParam("mnTodt") Date mnTodt,
			@RequestParam("propNo") String propNo, @RequestParam("oldpropNo") String oldpropNo,
			final HttpServletRequest request) {
		sessionCleanup(request);

		/*
		 * model.getPropSearchDto().setOldPid(OldPropNo);
		 * model.getPropSearchDto().setProertyNo(proertyNo);
		 * model.getPropSearchDto().setOrgId(currentOrgId); boolean propertyExist =
		 * viewPropertyDetailsService.checkPropertyExitOrNot(model.getPropSearchDto());
		 */
		DetailOutstandingRegisterModel model = this.getModel();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String fromdt = Utility.dateToString(mnFromdt, "yyyy-MM-dd");
		String todt = Utility.dateToString(mnTodt, "yyyy-MM-dd");

		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=MutationRegister_PT.rptdesign&OrgId=" + currentOrgId
				+ "&Zone=" + wardZone1 + "&Ward=" + wardZone2 + "&Block=" + wardZone3 + "&Route=" + wardZone4
				+ "&Subroute=" + wardZone5 + "&PropertyNo=" + propNo + "&OldPropNo=" + oldpropNo + "&Fromdate=" + fromdt
				+ "&Todate=" + todt;

	}
}