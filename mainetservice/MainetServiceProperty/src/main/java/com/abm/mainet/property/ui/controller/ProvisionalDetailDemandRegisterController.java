package com.abm.mainet.property.ui.controller;

import java.util.Date;
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
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.ui.model.ProvisionalDetailDemandRegisterModel;

@Controller
@RequestMapping("/ProvisionalDemand.html")
public class ProvisionalDetailDemandRegisterController
		extends AbstractFormController<ProvisionalDetailDemandRegisterModel> {

	/*
	 * @Autowired private ViewPropertyDetailsService viewPropertyDetailsService;
	 */
	@Autowired
	private TbFinancialyearService iFinancialYearService;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private IProvisionalBillService billService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		ProvisionalDetailDemandRegisterModel model = this.getModel();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		FinancialYear finanYear = iFinancialYearService.getFinanciaYearByDate(new Date());
		if (finanYear != null) {
			/* this.getModel().setMinFinDate(finanYear.getFaToDate()); */
			this.getModel().setMaxFinDate(finanYear.getFaFromDate());
		}
		return index();
	}

	@RequestMapping(params = "GetProvision", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("mnFromdt") Date mnFromdt/* , @RequestParam("mnTodt") Date mnTodt */,
			final HttpServletRequest request) {
		ProvisionalDetailDemandRegisterModel model = this.getModel();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		String fromdt = Utility.dateToString(mnFromdt, "yyyy-MM-dd");
		/* String todt = Utility.dateToString(mnTodt, "yyyy-MM-dd"); */

		/*
		 * model.getPropertySearchdto().setProertyNo(proertyNo);
		 * model.getPropertySearchdto().setOrgId(currentOrgId); boolean propertyExist =
		 * viewPropertyDetailsService.checkPropertyExitOrNot(model.getPropertySearchdto(
		 * )); if (propertyExist) {}
		 */

		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=ProvisionalDetailDemandRegister.rptdesign&OrgId="
				+ currentOrgId + "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4="
				+ wardZone4 + "&WZB5=" + wardZone5 + "&FromDate=" + fromdt /* + "&ToDate=" + todt */;
	}

	@RequestMapping(params = "generateBillAndSave", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String generateBillAndSave(final HttpServletRequest request) {
		getModel().bind(request);
		ProvisionalDetailDemandRegisterModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		String ipAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
		billService.deleteTemporaryProvisionalBillsWithEntityById(empId, orgId);
		List<Long> bmIdNo = propertyService.generateProvisionalBillForReport(model.getPropertyDto(), orgId, empId,
				ipAddress);
		model.setBmIdNoList(bmIdNo);
		return null;
	}

	@RequestMapping(params = "viewDemandRegisterReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewReportSheet(final HttpServletRequest request) {
		getModel().bind(request);
		PropertyReportRequestDto dto = this.getModel().getPropertyDto();
		if (dto.getMnassward1() == null || dto.getMnassward1() == -1) {
			dto.setMnassward1(0l);
		}
		if (dto.getMnassward2() == null || dto.getMnassward2() == -1) {
			dto.setMnassward2(0l);
		}
		if (dto.getMnassward3() == null || dto.getMnassward3() == -1) {
			dto.setMnassward3(0l);
		}
		if (dto.getMnassward4() == null || dto.getMnassward4() == -1) {
			dto.setMnassward4(0l);
		}
		if (dto.getMnassward5() == null || dto.getMnassward5() == -1) {
			dto.setMnassward5(0l);
		}
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String fromdt = Utility.dateToString(dto.getMnFromdt(), MainetConstants.DATE_FORMATS);
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=ProvisionalDetailDemandRegister.rptdesign&OrgId="
				+ currentOrgId + "&WZB1=" + dto.getMnassward1() + "&WZB2=" + dto.getMnassward2() + "&WZB3="
				+ dto.getMnassward3() + "&WZB4=" + dto.getMnassward4() + "&WZB5=" + dto.getMnassward5() + "&FromDate="
				+ fromdt;
	}

}
