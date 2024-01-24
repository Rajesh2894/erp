package com.abm.mainet.legal.ui.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.EventDatecalendarDTO;
import com.abm.mainet.legal.dto.JudgementDetailDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICaseHearingService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.IJudgementDetailService;
import com.abm.mainet.legal.ui.model.EventDateModel;

@Controller
@RequestMapping("/EventDate.html")
public class EventDateController extends AbstractFormController<EventDateModel> {

	@Autowired
	private ICaseHearingService caseHearingService;
	@Autowired
	private ICaseEntryService iCaseEntryService;
	@Autowired
	IJudgementDetailService iJudgementDetailService;

	@Autowired
	private IAdvocateMasterService advocateMasterService;
	
	@Autowired
	private ICourtMasterService courtMasterService;
	
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		EventDateModel model = this.getModel();
		model.setAdvocates(model.getAdvocateMasterList());
      
     //#121048
	/*	List<CaseEntryDTO> caseEntryList = iCaseEntryService.getAllCaseEntry(orgid);
		model.setCaseEntryDtoList(caseEntryList);*/

		return defaultResult();
	}

	 // #88449-to get hearing details on load
	@RequestMapping(params = { "fetchHearingDetails" }, method = RequestMethod.POST)
	@ResponseBody
	public List<EventDatecalendarDTO> fetchAllhearingDetails(final HttpServletRequest httpServletRequest){
		bindModel(httpServletRequest);
		List<EventDatecalendarDTO> eventDetailList = new ArrayList<>();
		String advName = null;
		Long orgid= UserSession.getCurrent().getOrganisation().getOrgid();
		List<CaseHearingDTO> hearingDetailList = caseHearingService.getHearingDetailByOrgId(orgid, new Date());
		if (CollectionUtils.isNotEmpty(hearingDetailList)) {
			for (CaseHearingDTO caseHearingDTO : hearingDetailList) {
				EventDatecalendarDTO eventDatecalendarDTO = new EventDatecalendarDTO();
				eventDatecalendarDTO
						.setDate(Utility.dateToString(caseHearingDTO.getHrDate(), MainetConstants.DATE_FORMATS));
				CaseEntryDTO caseEntry = iCaseEntryService.getCaseEntryById(caseHearingDTO.getCseId());
				AdvocateMasterDTO advocateDto = advocateMasterService.getAdvocateMasterById(caseEntry.getAdvId());
				CourtMasterDTO courtDto = courtMasterService.getCourtMasterById(caseEntry.getCrtId());
				if (advocateDto != null && StringUtils.isNotEmpty(advocateDto.getAdvFirstNm())
						&& StringUtils.isNotEmpty(advocateDto.getAdvLastNm()))
					advName = advocateDto.getAdvFirstNm().concat(MainetConstants.WHITE_SPACE)
							+ advocateDto.getAdvLastNm();
				eventDatecalendarDTO.setAdvocateName(advName);
				if (courtDto != null) {
					if (UserSession.getCurrent().getLanguageId() == 1)
						eventDatecalendarDTO.setCourtName(courtDto.getCrtName());
					else
						eventDatecalendarDTO.setCourtName(courtDto.getCrtNameReg());
				}
				eventDatecalendarDTO.setCaseNo(caseEntry.getCseSuitNo());
				eventDatecalendarDTO.setDescription("Hearing of Case No" + " " + caseEntry.getCseSuitNo());
				eventDetailList.add(eventDatecalendarDTO);
			}
			this.getModel().setEventDatecalendarDTOList(eventDetailList);
		}
		return eventDetailList;
		
	}

	@RequestMapping(params = { "eventDate" }, method = RequestMethod.POST)
	@ResponseBody
	public List<EventDatecalendarDTO> getHearingDate(final HttpServletRequest httpServletRequest,
			@RequestParam(value = "caseId", required = false) Long caseId,
			@RequestParam(value = "advId", required = false) Long advId) {

		bindModel(httpServletRequest);
		this.sessionCleanup(httpServletRequest);
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		String advName = null;

		List<CaseHearingDTO> hearingDetailList = caseHearingService.getHearingDetailByCaseId(orgid, caseId);

		List<JudgementDetailDTO> JudgementDetail = iJudgementDetailService.getHearingDetailByCaseId(orgid, caseId);
		List<EventDatecalendarDTO> eventDetailList = new ArrayList<>();
	
		for (JudgementDetailDTO judgementDetailDTO : JudgementDetail) {
			EventDatecalendarDTO eventDatecalendarDTO = new EventDatecalendarDTO();
			CaseEntryDTO caseEntry = iCaseEntryService.getCaseEntryById(judgementDetailDTO.getCseId());
			AdvocateMasterDTO advocateDto = advocateMasterService.getAdvocateMasterById(caseEntry.getAdvId());
		    CourtMasterDTO courtDto = courtMasterService.getCourtMasterById(caseEntry.getCrtId());
		    if (advocateDto != null && StringUtils.isNotEmpty(advocateDto.getAdvFirstNm()) && StringUtils.isNotEmpty(advocateDto.getAdvLastNm()))
		    advName  = advocateDto.getAdvFirstNm().concat(MainetConstants.WHITE_SPACE)+advocateDto.getAdvLastNm();
		    eventDatecalendarDTO.setAdvocateName(advName);
		    if (courtDto != null) {
		    if (UserSession.getCurrent().getLanguageId() == 1)
		    	eventDatecalendarDTO.setCourtName(courtDto.getCrtName());
		    else
		    	eventDatecalendarDTO.setCourtName(courtDto.getCrtNameReg());
		    }
		    eventDatecalendarDTO.setCaseNo(caseEntry.getCseSuitNo());


			
			eventDatecalendarDTO.setDate(Utility.dateToString(judgementDetailDTO.getImplementationStartDate(),
					MainetConstants.DATE_FORMATS));
			eventDatecalendarDTO.setDescription("Judgement/case Result of Case No" + " " + caseId);
			eventDetailList.add(eventDatecalendarDTO);
			

		}

		for (CaseHearingDTO caseHearingDTO : hearingDetailList) {
			EventDatecalendarDTO eventDatecalendarDTO = new EventDatecalendarDTO();

			eventDatecalendarDTO
					.setDate(Utility.dateToString(caseHearingDTO.getHrDate(), MainetConstants.DATE_FORMATS));	
			CaseEntryDTO caseEntry = iCaseEntryService.getCaseEntryById(caseHearingDTO.getCseId());
			AdvocateMasterDTO advocateDto = advocateMasterService.getAdvocateMasterById(caseEntry.getAdvId());
		    CourtMasterDTO courtDto = courtMasterService.getCourtMasterById(caseEntry.getCrtId());
		    if (advocateDto != null && StringUtils.isNotEmpty(advocateDto.getAdvFirstNm()) && StringUtils.isNotEmpty(advocateDto.getAdvLastNm()))
		    advName  = advocateDto.getAdvFirstNm().concat(MainetConstants.WHITE_SPACE)+advocateDto.getAdvLastNm();
		    eventDatecalendarDTO.setAdvocateName(advName);
		    if (courtDto != null) {
		    if (UserSession.getCurrent().getLanguageId() == 1)
		    	eventDatecalendarDTO.setCourtName(courtDto.getCrtName());
		    else
		    	eventDatecalendarDTO.setCourtName(courtDto.getCrtNameReg());
		    }
		    eventDatecalendarDTO.setCaseNo(caseEntry.getCseSuitNo());

			eventDatecalendarDTO.setDescription("Hearing of Case No" + " " + caseEntry.getCseSuitNo());
			eventDetailList.add(eventDatecalendarDTO);

		}

		return eventDetailList;

	}

	@RequestMapping(params = { "viewCalender" }, method = RequestMethod.POST)
	public ModelAndView getDateCalender(final HttpServletRequest httpServletRequest) {

		bindModel(httpServletRequest);
		ModelAndView mv = new ModelAndView("eventDateCalender", MainetConstants.FORM_NAME, this.getModel());
		return mv;

	}
	
	//#121048
	@RequestMapping(params = { "getCaseNoList" }, method = RequestMethod.POST)
	@ResponseBody
	public List<CaseEntryDTO> getCaseNoByAdvId(final HttpServletRequest httpServletRequest,@RequestParam(value = "advId", required = false) Long advId){
	EventDateModel model = this.getModel();
	Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	List<CaseEntryDTO> caseEntryList = iCaseEntryService.getcaseEntryByAdvId(advId,orgId);
	model.setCaseEntryDtoList(caseEntryList);
	return model.getCaseEntryDtoList();
	}
}
