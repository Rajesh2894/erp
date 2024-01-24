
package com.abm.mainet.firemanagement.ui.controller;

import java.util.Arrays;
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
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.service.IOccuranceBookService;
import com.abm.mainet.firemanagement.ui.model.OccuranceBookModel;

@Controller

@RequestMapping(value = "/OccuranceBook.html")
public class OccuranceBookController extends AbstractFormController<OccuranceBookModel> {

	@Autowired
	IOccuranceBookService occurrancebookserice;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {

		// 1 Session Cleanup this.sessionCleanup(httpServletRequest); // 2 help doc
		this.getModel().setCommonHelpDocs("OccuranceBook.html");
	//	List<FireCallRegisterDTO> occbooks = occurrancebookserice.getAllOccLogBook(UserSession.getCurrent().getOrganisation().getOrgid(), "O,SB");
		List<FireCallRegisterDTO> occbooks = occurrancebookserice.getAllOccLogBook(UserSession.getCurrent().getOrganisation().getOrgid(), Constants.OPEN_STATUS);
		List<FireCallRegisterDTO> occbook = getFires(occbooks);
		model.addAttribute("occbooks", occbook);
		return new ModelAndView("OccuranceBook", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "occuranceBook", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addOccForm(@RequestParam("cmplntNo") String cmplntNo, @RequestParam("id") Long id,
			final HttpServletRequest request) {
		this.sessionCleanup(request);
		OccuranceBookModel model = this.getModel(); 
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		model.getOccuranceBookDTO().setEnterComplaintNumber(cmplntNo);
		model.getOccuranceBookDTO().setOccId(id);
					
		return new ModelAndView("OccuranceBookAdd", MainetConstants.FORM_NAME, model);

	}

	@RequestMapping(params = "searchOccurranceBook", method = RequestMethod.POST)
	public @ResponseBody List<FireCallRegisterDTO> findOccBook(@RequestParam("fromDate") Date fromDate,

			@RequestParam("toDate") Date toDate, @RequestParam("fireStation") String fireStation,
			HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<FireCallRegisterDTO> occDtos = occurrancebookserice.searchFireCallRegisterwithDate(toDate, fromDate,
				fireStation, orgid);
		 return occDtos;

	}

	
	private List<FireCallRegisterDTO> getFires(List<FireCallRegisterDTO> occbooks) {
		String cpdDesc="";
		for(FireCallRegisterDTO fireCallRegisterDTO:occbooks) {
			if(null!=fireCallRegisterDTO.getCpdFireStation()) {
			if( fireCallRegisterDTO.getCpdFireStation().contains(",")) {
				String [] cpdStnArr=fireCallRegisterDTO.getCpdFireStation().split(",");
					fireCallRegisterDTO.setCpdFireStationList(Arrays.asList(cpdStnArr));
					
					
					for(String cpdStn:cpdStnArr) {
						cpdDesc+=CommonMasterUtility.getCPDDescription(Long.valueOf(cpdStn),MainetConstants.BLANK)+",";
					}
					cpdDesc=cpdDesc.substring(Constants.FIRE_ZERO, cpdDesc.length()-Constants.FIRE_ZERO);
					fireCallRegisterDTO.setFsDesc(cpdDesc);
					cpdDesc="";
			}
			else {
				fireCallRegisterDTO.setFsDesc(CommonMasterUtility.getCPDDescription(Long.valueOf(fireCallRegisterDTO.getCpdFireStation()),MainetConstants.BLANK));
			}
			}
			else {
				fireCallRegisterDTO.setFsDesc("-");
			}
		}
		return occbooks;
	}
	
	@RequestMapping(params = "validateDate", method = RequestMethod.POST)	
	public @ResponseBody String validateDateTime(@RequestParam("cmplntId") String cmplntId) {
		String msg=occurrancebookserice.getRecordByTime(UserSession.getCurrent().getOrganisation().getOrgid(), cmplntId);
	return msg;

	}
	
}
	


    /*
	 * @RequestMapping(params = "validateTime", method = RequestMethod.POST)
	 * public @ResponseBody String validateTime(@RequestParam("inputTime") String
	 * inputTime,
	 * 
	 * @RequestParam("cmplntId") String cmplntId) { String msg =
	 * occurrancebookserice.getRecordByTime(UserSession.getCurrent().getOrganisation
	 * ().getOrgid(), inputTime, cmplntId); return msg; }
	 */

	
	/*
	 * @RequestMapping(params = "validateDate", method = RequestMethod.POST)
	 * public @ResponseBody String validateDate(@RequestParam("inputDate") Date
	 * inputDate,
	 * 
	 * @RequestParam("cmplntId") String cmplntId) { String msg =
	 * occurrancebookserice.getRecordByDate(UserSession.getCurrent().getOrganisation
	 * ().getOrgid(), inputDate, cmplntId); return msg; }
	 */







