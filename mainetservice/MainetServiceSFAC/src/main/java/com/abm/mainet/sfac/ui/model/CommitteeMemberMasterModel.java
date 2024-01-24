/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CommitteeMemberMasterDto;
import com.abm.mainet.sfac.dto.StateInformationDto;
import com.abm.mainet.sfac.service.CommitteeMemberService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class CommitteeMemberMasterModel extends AbstractFormModel {

	@Autowired
	private CommitteeMemberService comMemberService;
	private static final long serialVersionUID = -4978885311975503445L;

	private String saveMode;

	private CommitteeMemberMasterDto comMemDto = new CommitteeMemberMasterDto();
	List<CommitteeMemberMasterDto> comMemDtoList = new ArrayList<>();

	List<CommitteeMemberMasterDto> comMemberDtoList = new ArrayList<>();

	/**
	 * @return the saveMode
	 */
	public String getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode the saveMode to set
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	/**
	 * @return the comMemDto
	 */
	public CommitteeMemberMasterDto getComMemDto() {
		return comMemDto;
	}

	/**
	 * @param comMemDto the comMemDto to set
	 */
	public void setComMemDto(CommitteeMemberMasterDto comMemDto) {
		this.comMemDto = comMemDto;
	}

	/**
	 * @return the comMemDtoList
	 */
	public List<CommitteeMemberMasterDto> getComMemDtoList() {
		return comMemDtoList;
	}

	/**
	 * @param comMemDtoList the comMemDtoList to set
	 */
	public void setComMemDtoList(List<CommitteeMemberMasterDto> comMemDtoList) {
		this.comMemDtoList = comMemDtoList;
	}

	/**
	 * @return the comMemberDtoList
	 */
	public List<CommitteeMemberMasterDto> getComMemberDtoList() {
		return comMemberDtoList;
	}

	/**
	 * @param comMemberDtoList the comMemberDtoList to set
	 */
	public void setComMemberDtoList(List<CommitteeMemberMasterDto> comMemberDtoList) {
		this.comMemberDtoList = comMemberDtoList;
	}

	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		CommitteeMemberMasterDto mastDto = getComMemDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(orgId);
			mastDto.setLgIpMac(lgIp);
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto = comMemberService.saveCommitteeMemDetails(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.com.mem.save.msg"));
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(orgId);
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			mastDto = comMemberService.updateCommitteeMemDetails(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.com.mem.update.msg"));
		}
		setComMemDto(mastDto);
		return true;

	}

}
