/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.MeetingDetailDto;
import com.abm.mainet.sfac.dto.MeetingMasterDto;
import com.abm.mainet.sfac.service.MeetingMasterService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class MeetingMasterFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6142366619805533113L;

	@Autowired
	private MeetingMasterService meetingMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	MeetingMasterDto masterDto = new MeetingMasterDto();

	private String saveMode;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();

	List<MeetingMasterDto> meetingMasterDtoList = new ArrayList<>();

	List<MeetingDetailDto> comMemberList = new ArrayList<>();

	List<DesignationBean> designlist = new ArrayList<>();

	private List<DocumentDetailsVO> documentDtos = new ArrayList<>();

	private String removeYearIds;

	private Long deleteFileId;

	private String removeMomDetIds;

	private String removeMemberIds;

	private static Long printMeetingId;

	private String showHideFlag;

	private String removeAgendaIds;

	/**
	 * @return the masterDto
	 */
	public MeetingMasterDto getMasterDto() {
		return masterDto;
	}

	/**
	 * @param masterDto the masterDto to set
	 */
	public void setMasterDto(MeetingMasterDto masterDto) {
		this.masterDto = masterDto;
	}

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
	 * @return the attachments
	 */
	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the attachDocsList
	 */
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	/**
	 * @param attachDocsList the attachDocsList to set
	 */
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	/**
	 * @return the meetingMasterDtoList
	 */
	public List<MeetingMasterDto> getMeetingMasterDtoList() {
		return meetingMasterDtoList;
	}

	/**
	 * @param meetingMasterDtoList the meetingMasterDtoList to set
	 */
	public void setMeetingMasterDtoList(List<MeetingMasterDto> meetingMasterDtoList) {
		this.meetingMasterDtoList = meetingMasterDtoList;
	}

	/**
	 * @return the designlist
	 */
	public List<DesignationBean> getDesignlist() {
		return designlist;
	}

	/**
	 * @param designlist the designlist to set
	 */
	public void setDesignlist(List<DesignationBean> designlist) {
		this.designlist = designlist;
	}

	/**
	 * @return the removeYearIds
	 */
	public String getRemoveYearIds() {
		return removeYearIds;
	}

	/**
	 * @param removeYearIds the removeYearIds to set
	 */
	public void setRemoveYearIds(String removeYearIds) {
		this.removeYearIds = removeYearIds;
	}

	/**
	 * @return the deleteFileId
	 */
	public Long getDeleteFileId() {
		return deleteFileId;
	}

	/**
	 * @param deleteFileId the deleteFileId to set
	 */
	public void setDeleteFileId(Long deleteFileId) {
		this.deleteFileId = deleteFileId;
	}

	/**
	 * @return the documentDtos
	 */
	public List<DocumentDetailsVO> getDocumentDtos() {
		return documentDtos;
	}

	/**
	 * @param documentDtos the documentDtos to set
	 */
	public void setDocumentDtos(List<DocumentDetailsVO> documentDtos) {
		this.documentDtos = documentDtos;
	}

	/**
	 * @return the removeMomDetIds
	 */
	public String getRemoveMomDetIds() {
		return removeMomDetIds;
	}

	/**
	 * @param removeMomDetIds the removeMomDetIds to set
	 */
	public void setRemoveMomDetIds(String removeMomDetIds) {
		this.removeMomDetIds = removeMomDetIds;
	}

	/**
	 * @return the removeMemberIds
	 */
	public String getRemoveMemberIds() {
		return removeMemberIds;
	}

	/**
	 * @param removeMemberIds the removeMemberIds to set
	 */
	public void setRemoveMemberIds(String removeMemberIds) {
		this.removeMemberIds = removeMemberIds;
	}

	/**
	 * @return the printMeetingId
	 */
	public static Long getPrintMeetingId() {
		return printMeetingId;
	}

	/**
	 * @param printMeetingId the printMeetingId to set
	 */
	public static void setPrintMeetingId(Long printMeetingId) {
		MeetingMasterFormModel.printMeetingId = printMeetingId;
	}

	/**
	 * @return the comMemberList
	 */
	public List<MeetingDetailDto> getComMemberList() {
		return comMemberList;
	}

	/**
	 * @param comMemberList the comMemberList to set
	 */
	public void setComMemberList(List<MeetingDetailDto> comMemberList) {
		this.comMemberList = comMemberList;
	}

	/**
	 * @return the showHideFlag
	 */
	public String getShowHideFlag() {
		return showHideFlag;
	}

	/**
	 * @param showHideFlag the showHideFlag to set
	 */
	public void setShowHideFlag(String showHideFlag) {
		this.showHideFlag = showHideFlag;
	}

	/**
	 * @return the removeAgendaIds
	 */
	public String getRemoveAgendaIds() {
		return removeAgendaIds;
	}

	/**
	 * @param removeAgendaIds the removeAgendaIds to set
	 */
	public void setRemoveAgendaIds(String removeAgendaIds) {
		this.removeAgendaIds = removeAgendaIds;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String emppiservername = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date createdDate = new Date();
		// date setup
		String meetingDateDesc = this.masterDto.getMeetingDateDesc();
		DateFormat formatter = new SimpleDateFormat(MainetConstants.Council.Meeting.MEETING_DATE_FORMATE);
		if (!meetingDateDesc.contains(" ")) {
			formatter = new SimpleDateFormat("dd/MM/yyyy");
		}

		Date meetingDate = null;
		if (meetingDateDesc != null) {
			try {
				meetingDate = formatter.parse(meetingDateDesc);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		masterDto.setMeetingDate(meetingDate);
		if (!saveMode.equals("E")) {
			if (masterDto.getCreatedBy() != null) {
				// add proposal case
				// check attachDocList is empty or not if empty than do validation by other way
				if (attachDocsList.isEmpty()) {
					addValidationError(ApplicationSession.getInstance().getMessage("sfac.meeting.validation.document"));
					return false;
				}
			} else {
				// below code check for document validation in case of add
				if (!checkDocumentList()) {
					return false;
				}
			}
		}

		FileUploadDTO uploadDTO = new FileUploadDTO();
		uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		uploadDTO.setStatus(MainetConstants.FlagA);
		uploadDTO.setDepartmentName(MainetConstants.Sfac.SFAC);
		uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		masterDto.setOrgId(orgId);
		setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		LookUp look = CommonMasterUtility.getNonHierarchicalLookUpObject(this.masterDto.getMeetingTypeId());
		List<Long> removeYearIdList = getRemovedYearIdAsList();
		masterDto.setMeetingTypeName(CommonMasterUtility.getCPDDescription(masterDto.getMeetingTypeId().longValue(),
				MainetConstants.MENU.E));
		if (masterDto.getCreatedBy() == null) {
			masterDto.setCreatedBy(createdBy);
			masterDto.setCreatedDate(new Date());
			masterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			masterDto.getMeetingMOMDto().forEach(momDto -> {
				momDto.setCreatedBy(createdBy);
				momDto.setCreatedDate(new Date());
				momDto.setOrgId(orgId);
				momDto.setLgIpMac(emppiservername);
				momDto.setStatus(MainetConstants.FlagA);

			});
			if (look.getLookUpCode().equals("SLCC") || look.getLookUpCode().equals("DMC")) {
				if (CollectionUtils.isNotEmpty(masterDto.getMeetingDetailDto()))
					masterDto.getMeetingDetailDto().removeAll(masterDto.getMeetingDetailDto());
				masterDto.getMeetingDetailDto().addAll(this.getComMemberList());
			}
			masterDto.getMeetingDetailDto().forEach(mDetDto -> {
				mDetDto.setCreatedBy(createdBy);
				mDetDto.setCreatedDate(new Date());
				mDetDto.setOrgId(orgId);
				mDetDto.setLgIpMac(emppiservername);
				mDetDto.setStatus(MainetConstants.FlagA);
			});
			
			masterDto.getMeetinAgendaDto().forEach(agenDto -> {
				agenDto.setCreatedBy(createdBy);
				agenDto.setCreatedDate(new Date());
				agenDto.setOrgId(orgId);
				agenDto.setLgIpMac(emppiservername);
				agenDto.setStatus(MainetConstants.FlagA);
			});

		} else {
			masterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			masterDto.setUpdatedDate(new Date());
			masterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

			masterDto.getMeetingMOMDto().forEach(momDto -> {
				if (momDto.getCreatedBy() == null) {
					momDto.setCreatedBy(createdBy);
					momDto.setCreatedDate(createdDate);
					momDto.setOrgId(orgId);
					momDto.setLgIpMac(emppiservername);
					momDto.setStatus(MainetConstants.FlagA);
				} else {
					momDto.setUpdatedBy(createdBy);
					momDto.setUpdatedDate(createdDate);
					momDto.setOrgId(orgId);
					momDto.setLgIpMac(emppiservername);
				}
			});

			/*
			 * if (look.getLookUpCode().equals("SLCC") ||
			 * look.getLookUpCode().equals("DMC")) {
			 * masterDto.getMeetingDetailDto().addAll(this.getComMemberList()); }
			 */
			masterDto.getMeetingDetailDto().forEach(meetingDto -> {
				if (meetingDto.getCreatedBy() == null) {
					meetingDto.setCreatedBy(createdBy);
					meetingDto.setCreatedDate(createdDate);
					meetingDto.setOrgId(orgId);
					meetingDto.setLgIpMac(emppiservername);
					meetingDto.setStatus(MainetConstants.FlagA);
				} else {
					meetingDto.setUpdatedBy(createdBy);
					meetingDto.setUpdatedDate(createdDate);
					meetingDto.setOrgId(orgId);
					meetingDto.setLgIpMac(emppiservername);
				}
			});
			
			masterDto.getMeetinAgendaDto().forEach(agenDto -> {
				if (agenDto.getCreatedBy() == null) {
					agenDto.setCreatedBy(createdBy);
					agenDto.setCreatedDate(createdDate);
					agenDto.setOrgId(orgId);
					agenDto.setLgIpMac(emppiservername);
					agenDto.setStatus(MainetConstants.FlagA);
				} else {
					agenDto.setUpdatedBy(createdBy);
					agenDto.setUpdatedDate(createdDate);
					agenDto.setOrgId(orgId);
					agenDto.setLgIpMac(emppiservername);
				}
			});
		}

		List<Long> removedMemtDetIdsList = getRemovedMemberDetList();
		List<Long> removedMomDetIdsList = getRemovedMomDetList();
		
		List<Long> removedAgendaDetIdsList = getRemovedAgendaDetList();

		masterDto = meetingMasterService.saveMeetingDetails(masterDto, getAttachments(), uploadDTO, deleteFileId,
				removeYearIdList);
		if (removedMemtDetIdsList != null && !removedMemtDetIdsList.isEmpty()) {
			meetingMasterService.inactiveRemovedMemDetails(getMasterDto(), removedMemtDetIdsList);
		}
		if (removedMomDetIdsList != null && !removedMomDetIdsList.isEmpty()) {
			meetingMasterService.inactiveRemovedMomDetails(getMasterDto(), removedMomDetIdsList);
		}
		if (removedAgendaDetIdsList != null && !removedAgendaDetIdsList.isEmpty()) {
			meetingMasterService.inactiveRemovedAgendaDetails(getMasterDto(), removedAgendaDetIdsList);
		}
		setMasterDto(masterDto);
		if (this.saveMode.equals(MainetConstants.CommonConstants.C))
			setSuccessMessage(ApplicationSession.getInstance().getMessage("sfac.meeting.savesuccessmsg") + " "
					+ masterDto.getMeetingNo());
		else
			setSuccessMessage(ApplicationSession.getInstance().getMessage("sfac.meeting.updatesuccessmsg"));
		return true;

	}

	private List<Long> getRemovedYearIdAsList() {
		List<Long> removeYearIdList = null;
		String yearIds = getRemoveYearIds();
		if (yearIds != null && !yearIds.isEmpty()) {
			removeYearIdList = new ArrayList<>();
			String yearArray[] = yearIds.split(MainetConstants.operator.COMMA);
			for (String yearId : yearArray) {
				removeYearIdList.add(Long.valueOf(yearId));
			}
		}
		return removeYearIdList;
	}

	// Validation for File Upload
	public boolean checkDocumentList() {
		boolean flag = true;
		final List<DocumentDetailsVO> docList = fileUpload.prepareFileUpload(getAttachments());
		if ((docList != null) && !docList.isEmpty()) {
			for (final DocumentDetailsVO doc : docList) {
				if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
					addValidationError(
							ApplicationSession.getInstance().getMessage("sfac.proposal.validation.document"));
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * @return
	 */
	private List<Long> getRemovedMemberDetList() {
		List<Long> removeMemList = null;
		String memIds = getRemoveMemberIds();
		if (memIds != null && !memIds.isEmpty()) {
			removeMemList = new ArrayList<>();
			String contArray[] = memIds.split(MainetConstants.operator.COMMA);
			for (String id : contArray) {
				removeMemList.add(Long.valueOf(id));
			}
		}
		return removeMemList;
	}

	/**
	 * @return
	 */
	private List<Long> getRemovedMomDetList() {
		List<Long> removeMomList = null;
		String momIds = getRemoveMomDetIds();
		if (momIds != null && !momIds.isEmpty()) {
			removeMomList = new ArrayList<>();
			String contArray[] = momIds.split(MainetConstants.operator.COMMA);
			for (String id : contArray) {
				removeMomList.add(Long.valueOf(id));
			}
		}
		return removeMomList;
	}
	

	/**
	 * @return
	 */
	private List<Long> getRemovedAgendaDetList() {
		List<Long> removeAgendaList = null;
		String agenIds = getRemoveAgendaIds();
		if (agenIds != null && !agenIds.isEmpty()) {
			removeAgendaList = new ArrayList<>();
			String contArray[] = agenIds.split(MainetConstants.operator.COMMA);
			for (String id : contArray) {
				removeAgendaList.add(Long.valueOf(id));
			}
		}
		return removeAgendaList;
	}
}
