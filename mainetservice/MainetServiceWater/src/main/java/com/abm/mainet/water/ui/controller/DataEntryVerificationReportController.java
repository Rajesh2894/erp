package com.abm.mainet.water.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.ui.model.WaterDataEntryVerificationReport;

/**
 * @author aarti.paan
 * @since 30/01/2019
 */
@Controller
@RequestMapping("/DataEntryVerificationReport.html")
public class DataEntryVerificationReportController extends AbstractFormController<WaterDataEntryVerificationReport> {
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {

		WaterDataEntryVerificationReport model = this.getModel();
		sessionCleanup(httpServletRequest);

		return index();
	}

	@RequestMapping(params = "GetWardZoneWiseDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewWardZonereportSheet(final HttpServletRequest request) {

		Long wzId1 = 0l;
		Long wzId2 = 0l;
		Long wzId3 = 0l;
		Long wzId4 = 0l;
		Long wzId5 = 0l;

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WaterDataEntryVerificationReport model = this.getModel();
		this.bindModel(request);
		final List<LookUp> lookupList = CommonMasterUtility.getListLookup("WWZ",
				UserSession.getCurrent().getOrganisation());
		int levelSize = lookupList.size();

		String status = MainetConstants.FAILURE_MSG;

		if (levelSize == 1) {
			if (model.getCsmrInfoDTO().getCodDwzid1() != 0) {
				status = MainetConstants.SUCCESS_MESSAGE;
			}
		} else if (levelSize == 2) {
			if (model.getCsmrInfoDTO().getCodDwzid1() != 0 && model.getCsmrInfoDTO().getCodDwzid2() != 0) {
				status = MainetConstants.SUCCESS_MESSAGE;
			}
		} else if (levelSize == 3) {
			if (model.getCsmrInfoDTO().getCodDwzid1() != 0 && model.getCsmrInfoDTO().getCodDwzid2() != 0
					&& model.getCsmrInfoDTO().getCodDwzid3() != 0) {
				status = MainetConstants.SUCCESS_MESSAGE;
			}
		} else if (levelSize == 4) {
			if (model.getCsmrInfoDTO().getCodDwzid1() != 0 && model.getCsmrInfoDTO().getCodDwzid2() != 0
					&& model.getCsmrInfoDTO().getCodDwzid3() != 0 && model.getCsmrInfoDTO().getCodDwzid4() != 0) {
				status = MainetConstants.SUCCESS_MESSAGE;
			}
		} else if (levelSize == 5) {
			if (model.getCsmrInfoDTO().getCodDwzid1() != 0 && model.getCsmrInfoDTO().getCodDwzid2() != 0
					&& model.getCsmrInfoDTO().getCodDwzid3() != 0 && model.getCsmrInfoDTO().getCodDwzid4() != 0
					&& model.getCsmrInfoDTO().getCodDwzid5() != 0) {
				status = MainetConstants.SUCCESS_MESSAGE;
			}
		}
		if (status.equals(MainetConstants.SUCCESS_MESSAGE)) {
			wzId1 = model.getCsmrInfoDTO().getCodDwzid1();
			wzId2 = model.getCsmrInfoDTO().getCodDwzid2();
			wzId3 = model.getCsmrInfoDTO().getCodDwzid3();
			wzId4 = model.getCsmrInfoDTO().getCodDwzid4();
			wzId5 = model.getCsmrInfoDTO().getCodDwzid5();

			if (levelSize == 1) {
				wzId2 = 0l;
				wzId3 = 0l;
				wzId4 = 0l;
				wzId5 = 0l;
			}
			if (levelSize == 2) {
				wzId3 = 0l;
				wzId4 = 0l;
				wzId5 = 0l;
			}
			if (levelSize == 3) {
				wzId4 = 0l;
				wzId5 = 0l;
			}
			if (levelSize == 4) {
				wzId5 = 0l;
			}
			/* setting zero when All option is selected */
			if (wzId1 != null && wzId1 == -1) {
				wzId1 = 0l;
			}
			if (wzId2 != null && wzId2 == -1) {
				wzId2 = 0l;
			}

			if (wzId3 != null && wzId3 == -1) {
				wzId3 = 0l;
			}
			if (wzId4 != null && wzId4 == -1) {
				wzId4 = 0l;
			}

			if (wzId5 != null && wzId5 == -1) {
				wzId5 = 0l;
			}

			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=Data_Entry_Verification_Report_WT.rptdesign&__title=&false&wzId1=" + wzId1 + "&wzId2=" + wzId2
					+ "&wzId3=" + wzId3 + "&wzId4=" + wzId4 + "&wzId5=" + wzId5 + "&OrgId=" + currentOrgId;
		} else {
			return status;
		}
	}
}
