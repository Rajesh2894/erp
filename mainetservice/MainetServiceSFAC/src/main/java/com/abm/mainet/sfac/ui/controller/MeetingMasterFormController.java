/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.MeetingDetailDto;
import com.abm.mainet.sfac.dto.MeetingMOMDto;
import com.abm.mainet.sfac.dto.MeetingMasterDto;
import com.abm.mainet.sfac.dto.PrintingDto;
import com.abm.mainet.sfac.service.MeetingMasterService;
import com.abm.mainet.sfac.ui.model.MeetingMasterFormModel;

/**
 * @author pooja.maske
 *
 */
@RequestMapping(MainetConstants.Sfac.MEETING_MASTER_FORM_HTML)
@Controller
public class MeetingMasterFormController extends AbstractFormController<MeetingMasterFormModel> {

	private static final Logger logger = Logger.getLogger(MeetingMasterFormController.class);

	@Autowired
	private MeetingMasterService meetingMasterService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private DesignationService designationService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		return new ModelAndView(MainetConstants.Sfac.MEETING_MASTER_SUMMARY_FORM, MainetConstants.FORM_NAME,
				getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchMeetingDetails", method = RequestMethod.POST)
	public List<MeetingMasterDto> getMeetingDetails(@RequestParam("meetingTypeId") final Long meetingTypeId,
			@RequestParam("meetingNumber") final String meetingNumber,
			/*
			 * @RequestParam("fromDate") final Date fromDate,
			 * 
			 * @RequestParam("toDate") final Date toDate,
			 */final HttpServletRequest request) {
		getModel().bind(request);
		List<MeetingMasterDto> meetingDtoList = new ArrayList<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String orderBy = MainetConstants.Sfac.ORDER_BY_MEETING_DATE;
		meetingDtoList = meetingMasterService.searchMeetingMasterData(meetingTypeId, meetingNumber, orgId, orderBy,null);
		this.getModel().setMeetingMasterDtoList(meetingDtoList);
		return meetingDtoList;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(params = MainetConstants.Sfac.ADD_MEETING, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addCouncilMeeting(final HttpServletRequest request) {
		getModel().bind(request);
		fileUpload.sessionCleanUpForFileUpload();
		MeetingMasterFormModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.C);
		this.getModel().setShowHideFlag(MainetConstants.FlagN);
		this.getModel().setDesignlist(
				designationService.getDesignByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView(MainetConstants.Sfac.MEETING_MASTER_FORM, MainetConstants.FORM_NAME, model);
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.Sfac.EDIT_AND_VIEW_FORM, method = RequestMethod.POST)
	public ModelAndView editMeetingDetails(@RequestParam("meetingId") final Long meetingId,
			@RequestParam(name = MainetConstants.FORM_MODE, required = false) String formMode,
			HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().bind(request);
		logger.info("editMeetingDetails Started");
		MeetingMasterFormModel model = this.getModel();
		if (formMode.equals(MainetConstants.FlagE))
			model.setSaveMode(MainetConstants.FlagE);
		else
			model.setSaveMode(MainetConstants.FlagV);
		model.setMasterDto(meetingMasterService.findById(meetingId));
		 @SuppressWarnings("deprecation")
		LookUp  look = CommonMasterUtility.getNonHierarchicalLookUpObject(this.getModel().getMasterDto().getMeetingTypeId());
			if (look.getLookUpCode().equals("SLCC") || look.getLookUpCode().equals("DMC")) {
				this.getModel().setShowHideFlag(MainetConstants.FlagY);
				this.getModel().setComMemberList(model.getMasterDto().getMeetingDetailDto());
			}
			else {
				this.getModel().setShowHideFlag(MainetConstants.FlagN);
			}
			
		 addAttachedDoc(meetingId);
		this.getModel().setDesignlist(
				designationService.getDesignByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		logger.info("editMeetingDetails Ended");
		return new ModelAndView(MainetConstants.Sfac.MEETING_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	private void addAttachedDoc(Long meetingId) {
		final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
				.getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.Sfac.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH + meetingId);
		List<DocumentDetailsVO> documentDtos = new ArrayList<>();
		// iterate and set document details
		attachDocs.forEach(doc -> {
			DocumentDetailsVO docDto = new DocumentDetailsVO();
			docDto.setDocumentName(doc.getAttFname());
			docDto.setAttachmentId(doc.getAttId());
			docDto.setUploadedDocumentPath(doc.getAttPath());
			documentDtos.add(docDto);
		});
		this.getModel().setDocumentDtos(documentDtos);
	}

	@RequestMapping(params = "PrintReport")
	public ModelAndView printRTIStatusReport(final HttpServletRequest request) {
		Long meetingId = MeetingMasterFormModel.getPrintMeetingId();
		List<MeetingMOMDto> momList = new ArrayList<>();
		List<MeetingDetailDto> attendees = new ArrayList<>();
		Organisation organisation = new Organisation();

		PrintingDto printDto = new PrintingDto();
		if (meetingId != null) {
			// get meeting data by using meeting Id
			MeetingMasterDto meetingDto = meetingMasterService.findById(meetingId);
			organisation.setOrgid(meetingDto.getOrgId());
			BeanUtils.copyProperties(meetingDto, printDto);
			printDto.setMeetingNo(meetingDto.getMeetingNo());
			printDto.setMeetingTime(meetingDto.getMeetingTime());
			printDto.setMeetingTypeName(meetingDto.getMeetingTypeName());
			printDto.setMeetingDateDesc(meetingDto.getMeetingDateDesc());
			printDto.setMeetingInvitationMsg(meetingDto.getMeetingInvitationMsg());
			printDto.setMeetingPlace(meetingDto.getMeetingPlace());
			printDto.setTableAgenda(meetingDto.getTableAgenda());
			printDto.setTitle(meetingDto.getRemark());
			printDto.setConvenerOfMeeting(CommonMasterUtility
					.getNonHierarchicalLookUpObject(meetingDto.getConvenerofMeeting(), organisation).getLookUpDesc());
			// set attendance details
			for (MeetingDetailDto member : meetingDto.getMeetingDetailDto()) {
				MeetingDetailDto dto = new MeetingDetailDto();
				dto.setMemberName(member.getMemberName());
				dto.setDesignation(member.getDesignation());
				attendees.add(dto);
			}
			printDto.setAttendees(attendees);
			// set mom details
			for (MeetingMOMDto mom : meetingDto.getMeetingMOMDto()) {
				MeetingMOMDto dto = new MeetingMOMDto();
				dto.setMomComments(mom.getMomComments());
				dto.setActionable(mom.getActionable());
				momList.add(dto);
			}
			printDto.setMomDetList(momList);

			String subjects = "";
			int count = 1;
			for (int i = 0; i < meetingDto.getMeetingMOMDto().size(); i++) {
				int srNo = count++;
				String comments = meetingDto.getMeetingMOMDto().get(i).getMomComments();
				String actionable = meetingDto.getMeetingMOMDto().get(i).getActionable();
				subjects += "<tr>\r\n"
						+ "    <td class=\"text-center\" style=\"padding: 3px;font-size: 12px;border: 1px solid #eaeaea;text-align: center;\">"
						+ srNo + "</td>\r\n"
						+ "    <td class=\"text-center\" style=\"padding: 3px;font-size: 12px;border: 1px solid #eaeaea;text-align: center;\">"
						+ comments + "</td>\r\n"
						+ "    <td class=\"text-right\" style=\"padding: 3px;font-size: 12px;border: 1px solid #eaeaea;text-align: center;\">"
						+ actionable + "</td>\r\n" + "</tr>";
			}
			printDto.setMomDetails(subjects);
		} else {
			logger.error("meeting id not found to print MOM");
		}
		ModelAndView mv = new ModelAndView(MainetConstants.Sfac.MEETING_PRINT_PAGE, MainetConstants.FORM_NAME,
				this.getModel());
		mv.addObject("printDto", printDto);
		return mv;

	}
    @ResponseBody
	@RequestMapping(params = "getCommitteeMemDet", method = RequestMethod.POST)
	public List<MeetingDetailDto> getCommitteeMemDet(@RequestParam("meetingTypeId") final Long meetingTypeId,
			HttpServletRequest request) {
    	  getModel().bind(request);
		List<MeetingDetailDto> meetingDetDtolist = meetingMasterService.getCommitteeMemDetById(meetingTypeId);
		this.getModel().setComMemberList(meetingDetDtolist);
		return meetingDetDtolist;
	}
}
