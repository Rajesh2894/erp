
package com.abm.mainet.disastermanagement.ui.controller;

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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.service.IDisasterOccuranceBookService;
import com.abm.mainet.disastermanagement.ui.model.DisasterOccuranceBookModel;

@Controller
@RequestMapping(value = "/DisasterOccuranceBook.html")
public class DisasterOccuranceBookController extends AbstractFormController<DisasterOccuranceBookModel> {

	@Autowired
	IDisasterOccuranceBookService occurrancebookserice;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {

		this.getModel().setCommonHelpDocs("DisasterOccuranceBook.html");
		List<ComplainRegisterDTO> occDtos = occurrancebookserice
				.disasterSummaryDate(UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("occbooks", occDtos);
		return new ModelAndView("DisasterOccuranceBook", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "occuranceBook", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addOccForm(@RequestParam("cmplntNo") String cmplntNo, @RequestParam("id") Long id,
			final HttpServletRequest request) {
		this.sessionCleanup(request);
		DisasterOccuranceBookModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		model.getOccuranceBookDTO().setEnterComplaintNumber(cmplntNo);
		model.getOccuranceBookDTO().setOccId(id);

		return new ModelAndView("DisasterOccuranceBookAdd", MainetConstants.FORM_NAME, model);

	}

	@RequestMapping(params = "searchOccurranceBook", method = RequestMethod.POST)
	public @ResponseBody List<ComplainRegisterDTO> findOccBook(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate, @RequestParam(required = false) Long callType, @RequestParam(required = false) Long  callSubType , HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<ComplainRegisterDTO> occDtos = occurrancebookserice.searchFireCallRegisterwithDate(toDate, fromDate,
				orgid, callType, callSubType);
		return occDtos;

	}
	
	@RequestMapping(params = "validateDate", method = RequestMethod.POST)	
	public @ResponseBody String validateDateTime(@RequestParam("cmplntId") String cmplntId) {
		String msg=occurrancebookserice.getRecordByTime(UserSession.getCurrent().getOrganisation().getOrgid(), cmplntId);
	return msg;

	}

}
