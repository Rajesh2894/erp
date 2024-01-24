package com.abm.mainet.intranet.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.intranet.dao.PollDao;
import com.abm.mainet.intranet.domain.Choice;
import com.abm.mainet.intranet.domain.Poll;
import com.abm.mainet.intranet.domain.PollDetails;
import com.abm.mainet.intranet.domain.PollView;
import com.abm.mainet.intranet.dto.report.IntranetPollDTO;
import com.abm.mainet.intranet.dto.report.PollRequest;
import com.abm.mainet.intranet.service.ChoiceService;
import com.abm.mainet.intranet.service.PollDetService;
import com.abm.mainet.intranet.service.PollService;
import com.abm.mainet.intranet.service.PollViewService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PollModel extends AbstractFormModel{

	private static final long serialVersionUID = -4173237371394141017L;
	
	PollRequest pollRequest = new PollRequest();
	
	@Autowired
	private PollDetService pollDetService;

	@Autowired
	private PollService pollService;
	
	@Autowired 
	private PollDao pollDao;
	
	@Autowired
	private ChoiceService choiceService;
	
	@Autowired
	private PollViewService pollViewService;
	
	private List<Poll> intranetPollDTOList = new ArrayList<>();
	
	private List<Poll> intranetPollList = new ArrayList<>();
	
	private List<Choice> intranetChoiceList = new ArrayList<>();
	
	private List<Object> intranetPollViewDTOListObj = new ArrayList<>();
	
	private List<Object> intranetPollViewDTOListObjQue = new ArrayList<>();
	
	private List<PollView> intranetPollViewDTOList = new ArrayList<>();
	
	private String intranetPollObj;
	
	private String choiceText;
	
	private List<IntranetPollDTO> intranetPollViewDTOListObjDto = new ArrayList<>();

	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		pollRequest.setUpdatedBy(employee.getEmpId());
		pollRequest.setUpdatedDate(new Date());
		pollRequest.setLmodDate(new Date());
		pollRequest.setLgIpMac(employee.getEmppiservername());
		pollRequest.setLgIpMacUpd(employee.getEmppiservername());
		pollRequest.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		pollRequest.setLangId(langId);
		pollRequest.setUserId(employee.getOrganisation().getUserId());
		pollRequest.setPollStatus("D");
		pollService.createPoll(pollRequest);
		return true;
	}
	
	public boolean savePollCreationData(Long department, String pollName,Date pollFromDate, Date pollToDate, Long pollid, String mode, Long orgId ) {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		pollRequest.setUpdatedBy(employee.getEmpId());
		pollRequest.setUpdatedDate(new Date());
		pollRequest.setLmodDate(new Date());
		pollRequest.setLgIpMac(employee.getEmppiservername());
		pollRequest.setLgIpMacUpd(employee.getEmppiservername());
		pollRequest.setOrgId(orgId);
		pollRequest.setLangId(langId);
		pollRequest.setUserId(employee.getOrganisation().getUserId());
		
		pollRequest.setDeptId(department);
		pollRequest.setPollName(pollName);
		pollRequest.setFromDate(pollFromDate);
		pollRequest.setToDate(pollToDate);
		pollRequest.setPollid(pollid);
		pollRequest.setMode(mode);
		Long polldetId = pollRequest.getPollid();
		Poll pollObj1 = null;
		if(polldetId != null) {
			 List<Poll> pollObj11 = pollDao.getByPollDetId(pollRequest.getOrgId(), polldetId);
			 if(pollObj11!=null) {
				 PollDetails pollDt = pollObj11.get(0).getId();
			 	 if(pollDt !=null) {
			 		 pollRequest.setPollid(pollDt.getId());
			 	 }
			 }
		}
		
		pollService.createPollDetails(pollRequest);
		return true;
	}
	
	public boolean savePollViewData(Long pollid, String pollQue, String choiceRadio, Long pollAnsId, String choiceDesc) {
		Employee employee = getUserSession().getEmployee();
		Long empId = employee.getEmpId();
		int langId = UserSession.getCurrent().getLanguageId();
		pollRequest.setUpdatedBy(employee.getEmpId());
		pollRequest.setUpdatedDate(new Date());
		pollRequest.setLmodDate(new Date());
		pollRequest.setLgIpMac(employee.getEmppiservername());
		pollRequest.setLgIpMacUpd(employee.getEmppiservername());
		pollRequest.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		pollRequest.setLangId(langId);
		pollRequest.setUserId(employee.getOrganisation().getUserId());
		pollRequest.setLoggedInEmp(empId);
		pollRequest.setChoiceDescVal(choiceRadio);
		pollRequest.setChoiceid(pollAnsId);
		pollRequest.setChoiceDesc(choiceDesc);
		pollViewService.createPollViewDetails(pollRequest);
		return true;
	}
	
	public void getPollById(Long pollId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Poll pollObj = pollService.getPollById(25L);
		System.out.println(pollObj);
	}
	
	public void getPollByIdFromChoice() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Choice choiceObj = choiceService.getPollByIdFromChoice(orgId);
		System.out.println(choiceObj);
	}
	
	public void getPollByIdFromPollDet(Long pollId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PollDetails pollDetObj = pollDetService.getPollById(25L);
		System.out.println(pollDetObj);
	}
	
	public boolean submitPollStatus(String pollStatus, Long pollId) {
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		pollService.updatePollStatus(pollStatus, pollId, orgid);
		this.setSuccessMessage(getAppSession().getMessage("intranet.intPollSave"));
		return true;
	}
	
	public PollRequest getPollRequest() {
		return pollRequest;
	}

	public void setPollRequest(PollRequest pollRequest) {
		this.pollRequest = pollRequest;
	}

	public List<Poll> getIntranetPollDTOList() {
		return intranetPollDTOList;
	}

	public void setIntranetPollDTOList(List<Poll> intranetPollDTOList) {
		this.intranetPollDTOList = intranetPollDTOList;
	}
	
	public List<Poll> getIntranetPollList() {
		return intranetPollList;
	}

	public void setIntranetPollList(List<Poll> intranetPollList) {
		this.intranetPollList = intranetPollList;
	}

	public List<Choice> getIntranetChoiceList() {
		return intranetChoiceList;
	}

	public void setIntranetChoiceList(List<Choice> intranetChoiceList) {
		this.intranetChoiceList = intranetChoiceList;
	}

	public List<PollView> getIntranetPollViewDTOList() {
		return intranetPollViewDTOList;
	}

	public void setIntranetPollViewDTOList(List<PollView> intranetPollViewDTOList) {
		this.intranetPollViewDTOList = intranetPollViewDTOList;
	}

	public String getIntranetPollObj() {
		return intranetPollObj;
	}

	public void setIntranetPollObj(String intranetPollObj) {
		this.intranetPollObj = intranetPollObj;
	}
	
	public String getChoiceText() {
		return choiceText;
	}

	public void setChoiceText(String choiceText) {
		this.choiceText = choiceText;
	}
	
	public List<Object> getIntranetPollViewDTOListObj() {
		return intranetPollViewDTOListObj;
	}

	public void setIntranetPollViewDTOListObj(List<Object> intranetPollViewDTOListObj) {
		this.intranetPollViewDTOListObj = intranetPollViewDTOListObj;
	}

	public List<Object> getIntranetPollViewDTOListObjQue() {
		return intranetPollViewDTOListObjQue;
	}

	public void setIntranetPollViewDTOListObjQue(List<Object> intranetPollViewDTOListObjQue) {
		this.intranetPollViewDTOListObjQue = intranetPollViewDTOListObjQue;
	}

	public List<IntranetPollDTO> getIntranetPollViewDTOListObjDto() {
		return intranetPollViewDTOListObjDto;
	}

	public void setIntranetPollViewDTOListObjDto(List<IntranetPollDTO> intranetPollViewDTOListObjDto) {
		this.intranetPollViewDTOListObjDto = intranetPollViewDTOListObjDto;
	}

	
		

	
}
