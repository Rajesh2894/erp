/**
 * 
 */
package com.abm.mainet.common.ui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.dao.ICheckListVerificationReportDAO;
import com.abm.mainet.cfc.checklist.domain.CheckListReportEntity;
import com.abm.mainet.cfc.checklist.dto.CheckListVerificationReportChildDTO;
import com.abm.mainet.cfc.checklist.dto.CheckListVerificationReportParentDTO;
import com.abm.mainet.cfc.checklist.service.ICheckListVerificationReportService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.ui.model.CommonRejectionLetterModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

/**
 * @author anwarul.hassan
 * @since 22-Dec-2020
 */
@Controller
@RequestMapping("/CommonRejectionLetter.html")
public class CommonRejectionLetterController extends AbstractFormController<CommonRejectionLetterModel> {

    @Autowired
    private ICheckListVerificationReportDAO iCheckListVerificationReportDAO;
    @Autowired
    private ICheckListVerificationReportService reportService;
    @Autowired
    private IChecklistVerificationService checklistVerificationService;
    @Autowired
    private IWorkflowActionService workflowActionService;

    @Transactional
    @RequestMapping(params = "printCommonRejectionLetter", method = RequestMethod.POST)
    public ModelAndView printCommonRejectionLetter(@RequestParam("appNo") final Long appNo,
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        CommonRejectionLetterModel model = getModel();
        // Long applicationId = (Long) httpServletRequest.getSession().getAttribute("APP_NO");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        final long rejno = iCheckListVerificationReportDAO
                .updateApplicationMastrRejection(appNo, UserSession.getCurrent().getOrganisation());
        SimpleDateFormat tf1 = new SimpleDateFormat(MainetConstants.TIME_FORMAT);
        final DateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT);

        final CheckListVerificationReportParentDTO dto = new CheckListVerificationReportParentDTO();
        final ApplicationSession appSession = ApplicationSession.getInstance();
        dto.setTletterno(String.valueOf(rejno));
        dto.setLletterNo(appSession.getMessage("cfc.letterNo"));
        dto.setLdate(appSession.getMessage("cfc.date"));
        dto.setlTo(appSession.getMessage("cfc.To"));
        dto.setlRejSub(appSession.getMessage("cfc.RejSub"));
        dto.setlRejRef(appSession.getMessage("cfc.RejRef"));
        dto.setlSirMadam(appSession.getMessage("cfc.Sir/Madam"));
        dto.setlSrNo(appSession.getMessage("cfc.SrNo"));
        dto.setlDocName(appSession.getMessage("cfc.DocName"));
        dto.setlObsDetail(appSession.getMessage("cfc.ObsDetail"));
        dto.setPage(appSession.getMessage("cfc.page"));
        dto.setOf(appSession.getMessage("cfc.of"));
        dto.setColon(":-");
        if (UserSession.getCurrent().getLanguageId() == 1) {
            tf1 = new SimpleDateFormat(MainetConstants.TIME_FORMAT);
            final Date date = new Date();
            dto.setParent(ApplicationSession.getInstance().getMessage("uwms.rep.bottomTitle") + " "
                    + dateFormat.format(date).toString() + " " + tf1.format(date.getTime()).toString());
        } else {
            tf1 = new SimpleDateFormat(MainetConstants.CommonConstants.DATE_FORMAT_HH_mm_ss);
            final Date date = new Date();
            dto.setParent(ApplicationSession.getInstance().getMessage("uwms.rep.bottomTitle") + " "
                    + dateFormat.format(date).toString() + " " + tf1.format(date.getTime()).toString() + " "
                    + ApplicationSession.getInstance().getMessage("uwms.rnwl.a"));
        }
        if (model.getAppSession().getLangId() == 1) {
            dto.setOrgLabel(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            dto.setOrgLabel(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }
        if (model.getAppSession().getLangId() == 1) {
            dto.setlUlbAddr(UserSession.getCurrent().getOrganisation().getOrgAddress());
        } else {
            dto.setlUlbAddr(UserSession.getCurrent().getOrganisation().getOrgAddressMar());
        }
        dto.setlHeading(appSession.getMessage("cfc.reportheading"));
        List<CheckListReportEntity> rejAppList = reportService.getRejLetterList(appNo,
                MainetConstants.CommonConstant.BLANK,
                UserSession.getCurrent().getOrganisation());
        if ((rejAppList != null) && !rejAppList.isEmpty()) {
            final CheckListReportEntity list = rejAppList.get(0);
            if (list.getAname() != null) {
                final String applicantName = list.getAname();
                dto.setApplicantName(applicantName);
            } else {
                dto.setApplicantName(MainetConstants.CommonConstant.BLANK);
            }
            if (list.object1.get(1) != null) {
                dto.setApplicationAdd((String) list.object1.get(1));
            } else {
                dto.setApplicationAdd(MainetConstants.CommonConstant.BLANK);
            }
            dto.setSubject(appSession.getMessage("cfc.Subject"));
            if (list.getService() != null) {
                dto.setSubject1(list.getService());
            } else {
                dto.setSubject1(MainetConstants.CommonConstant.BLANK);
            }
            dto.setSubject2(appSession.getMessage("cfc.subject1"));
            dto.setRefFill(appSession.getMessage("cfc.refFillmsg1"));
            if (list.getApmApplicationId() != null) {
                dto.setRefFill1(list.getApmApplicationId().toString());
            } else {
                dto.setRefFill1(MainetConstants.CommonConstant.BLANK);
            }
            dto.setRefFill2(appSession.getMessage("cfc.refFillmsg2"));
            if (list.getAdate() != null) {
                dto.setRefFill3(list.getAdate());
            } else {
                dto.setRefFill3(MainetConstants.CommonConstant.BLANK);
            }
            dto.setlRefLine(appSession.getMessage("cfc.RefLinemsg1"));
            if (list.getService() != null) {
                dto.setlRefLine1(list.getService());
            } else {
                dto.setlRefLine1(MainetConstants.CommonConstant.BLANK);
            }
            dto.setlRefLine2(appSession.getMessage("cfc.RefLinemsg2"));
            dto.setlLastLine(appSession.getMessage("cfc.LastLine"));
            if (list.object.get(0) != null) {
                dto.setSignPath((String) list.object.get(0));
            } else {
                dto.setSignPath(MainetConstants.CommonConstant.BLANK);
            }
        }
        final List<CheckListVerificationReportChildDTO> ChildDTOList = new ArrayList<>(0);
        List<CFCAttachment> attachmentList = checklistVerificationService.getDocumentUploadedForAppId(appNo, orgId);
        List<String> comments = workflowActionService.getWorkflowActionCommentsByAppIdAndEmpId(appNo,
                UserSession.getCurrent().getEmployee().getEmpId(), orgId);
        String comment = comments.get(comments.size() - 1);
        if ((attachmentList != null) && !attachmentList.isEmpty()) {
            int srNo = 1;
            CheckListVerificationReportChildDTO ChildDTO = null;
            for (final CFCAttachment attachment : attachmentList) {
                ChildDTO = new CheckListVerificationReportChildDTO();
                if (attachment.getClmDesc() != null) {
                    ChildDTO.settDocName(attachment.getClmDesc());
                } else {
                    ChildDTO.settDocName(MainetConstants.CommonConstant.BLANK);
                }
                if (comment != null) {
                    ChildDTO.settObsDetail(comment);
                } else {
                    ChildDTO.settObsDetail(MainetConstants.CommonConstant.BLANK);
                }
                ChildDTO.settSrNo(String.valueOf(srNo));
                srNo = srNo + 1;
                ChildDTOList.add(ChildDTO);
            }
            dto.setChildDTO(ChildDTOList);
        }
        httpServletRequest.setAttribute("dto", dto);
        return new ModelAndView("RejectionLetter", MainetConstants.FORM_NAME, model);

    }
}
