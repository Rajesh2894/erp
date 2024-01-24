package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.EventDatecalendarDTO;
import com.abm.mainet.legal.service.IAdvocateMasterService;

@Component
@Scope("session")
public class EventDateModel extends AbstractFormModel {

	private static final long serialVersionUID = 5187586811352444521L;

	private List<CaseHearingDTO> hearingEntry = new ArrayList<>();

	private List<AdvocateMasterDTO> advocates = new ArrayList<>();

	private List<EventDatecalendarDTO> EventDatecalendarDTOList = new ArrayList<>();
	
	private List<CaseEntryDTO> caseEntryDtoList = new ArrayList<>();
	

	@Autowired
	private IAdvocateMasterService advocateMasterService;

	public List<AdvocateMasterDTO> getAdvocateMasterList() {
		List<AdvocateMasterDTO> advocateMasterDTOList = advocateMasterService
				.getAllAdvocateMasterByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

		return advocateMasterDTOList;
	}

	public List<CaseHearingDTO> getHearingEntry() {
		return hearingEntry;
	}

	public void setHearingEntry(List<CaseHearingDTO> hearingEntry) {
		this.hearingEntry = hearingEntry;
	}

	public List<AdvocateMasterDTO> getAdvocates() {
		return advocates;
	}

	public void setAdvocates(List<AdvocateMasterDTO> advocates) {
		this.advocates = advocates;
	}

	public List<CaseEntryDTO> getCaseEntryDtoList() {
		return caseEntryDtoList;
	}

	public void setCaseEntryDtoList(List<CaseEntryDTO> caseEntryDtoList) {
		this.caseEntryDtoList = caseEntryDtoList;
	}

	public List<EventDatecalendarDTO> getEventDatecalendarDTOList() {
		return EventDatecalendarDTOList;
	}

	public void setEventDatecalendarDTOList(List<EventDatecalendarDTO> eventDatecalendarDTOList) {
		EventDatecalendarDTOList = eventDatecalendarDTOList;
	}

}
