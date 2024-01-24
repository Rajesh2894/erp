package com.abm.mainet.common.activity.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.activity.ui.model.ActivityManagementModel;
import com.abm.mainet.common.activity.ui.validator.ActivityManagementValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.dto.ActivityManagementDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.EmployeeBean;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IActivityManagementService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Controller
@RequestMapping("/ActivityManagement.html")
public class ActivityManagementController extends AbstractController<ActivityManagementModel> {

    private static final String ACTIVITY_MANAGEMENT = "ACTIVITY_MANAGEMENT/";

    @Autowired
    private IActivityManagementService activityManagementService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IFileUploadService fileUpload;

    @RequestMapping
    public ModelAndView activity(Model model, HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        UserSession session = UserSession.getCurrent();
        List<ActivityManagementDTO> allActivity = activityManagementService.getAllIndividualAndTeamActivity(
                session.getOrganisation().getOrgid(),
                session.getEmployee().getEmpId());
        List<EmployeeBean> employees = employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute("allActivity", allActivity);
        model.addAttribute("employees", employees);
        return new ModelAndView("ActivityManagementLIST");

    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public ModelAndView saveActivity(Model model, HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        model.addAttribute(MainetConstants.OrgMaster.MODE, MainetConstants.OrgMaster.MODE_CREATE);
        populateModel(model);
        return index();

    }

    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editActivity(Model model, HttpServletRequest httpServletRequest, @RequestParam("actId") Long actId,
            @RequestParam("mode") String mode) {
        this.sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        model.addAttribute(MainetConstants.OrgMaster.MODE, mode);
        this.getModel().setEntity(activityManagementService.getActivity(actId));

        populateModel(model);
        final List<AttachDocs> attachDocs = fileUpload
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), ACTIVITY_MANAGEMENT + actId.toString());
        this.getModel().setAttachDocsList(attachDocs);
        return new ModelAndView("ActivityManagementValidn", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public ModelAndView deleteActivity(Model model, HttpServletRequest httpServletRequest, @RequestParam("actId") Long actId) {
        this.sessionCleanup(httpServletRequest);
        activityManagementService.deleteActivity(actId);
        this.sessionCleanup(httpServletRequest);
        UserSession session = UserSession.getCurrent();
        List<ActivityManagementDTO> allActivity = activityManagementService.getAllIndividualAndTeamActivity(
                session.getOrganisation().getOrgid(),
                session.getEmployee().getEmpId());
        List<EmployeeBean> employees = employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute("allActivity", allActivity);
        model.addAttribute("employees", employees);
        return new ModelAndView("ActivityManagementLIST/FORM");

    }

    private void populateModel(Model model) {
        List<EmployeeBean> employees = employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
        List<ActivityManagementDTO> allActivity = activityManagementService
                .findAllActivityByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        final List<LookUp> activityType = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.AVT,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> activityStatus = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.ACS,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> activityPriority = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.PIR,
                UserSession.getCurrent().getOrganisation());

        String actWatcher = this.getModel().getEntity().getActWatcher();
        long currentEmployee = UserSession.getCurrent().getEmployee().getEmpId().longValue();
        if (this.getModel().getEntity().getActEmpid() != null && this.getModel().getEntity().getActEmpid() != currentEmployee
                && StringUtils.isNotBlank(actWatcher)) {
            if (Stream.of(actWatcher.split(","))
                    .map(Long::parseLong).anyMatch(x -> x == currentEmployee)) {
                model.addAttribute("watcher", "Y");
            } else {
                model.addAttribute("watcher", "N");
            }

        }

        if (StringUtils.isNotEmpty(actWatcher)) {
            List<Long> items = Stream.of(actWatcher.split(","))
                    .map(Long::parseLong).collect(Collectors.toList());
            model.addAttribute("actWatcherList", items);
        }

        model.addAttribute("employees", employees);
        model.addAttribute("allActivity", allActivity);
        model.addAttribute("activityType", activityType);
        model.addAttribute("activityStatus", activityStatus);
        model.addAttribute("activityPriority", activityPriority);

        if (this.getModel().getEntity().getActId() == null) {
            model.addAttribute(MainetConstants.OrgMaster.MODE, MainetConstants.OrgMaster.MODE_CREATE);
        }
    }

    @RequestMapping(params = "saveActivity", method = RequestMethod.POST)
    public ModelAndView saveform(Model model1, final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final ActivityManagementModel model = getModel();

        model.validateBean(model.getEntity(), ActivityManagementValidator.class);
        UserSession session = UserSession.getCurrent();
        if (model.hasValidationErrors()) {
            populateModel(model1);
            return defaultMyResult();
        } else {

            if (StringUtils.isNotEmpty(model.getEntity().getEditActNote())) {
                StringBuilder notes = null;
                if (StringUtils.isEmpty(model.getEntity().getActNote())) {
                    notes = new StringBuilder((session.getEmployee().getFullName())).append(" : ")
                            .append(model.getEntity().getEditActNote());
                    model.getEntity().setActNote(notes.toString());
                } else {
                    notes = new StringBuilder(model.getEntity().getActNote())
                            .append(MainetConstants.operator.ORR)
                            .append(session.getEmployee().getFullName()).append(" : ")
                            .append(model.getEntity().getEditActNote());
                    model.getEntity().setActNote(notes.toString());
                }
            }

            if (model.getEntity().getActId() != null) {
                model.getEntity().setLgIpMacUpd(session.getEmployee().getEmppiservername());
                model.getEntity().setUpdatedBy(session.getEmployee().getEmpId());
                model.getEntity().setUpdatedDate(new Date());

            } else {
                model.getEntity().setCreatedBy(session.getEmployee().getEmpId());
                model.getEntity().setCreatedDate(new Date());
                model.getEntity().setLgIpMac(session.getEmployee().getEmppiservername());
                model.getEntity().setOrgid(session.getOrganisation().getOrgid());
            }

            ActivityManagementDTO actDto = activityManagementService.saveOrUpdateActivity(model.getEntity());

            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestDTO.setStatus("A");
            requestDTO.setIdfId(ACTIVITY_MANAGEMENT + actDto.getActId().toString());
            requestDTO.setDepartmentName("EIP");
            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            List<DocumentDetailsVO> dto = this.getModel().getAttachments();

            this.getModel().setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

            int i = 0;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                this.getModel().getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
                i++;
            }

            fileUpload.doMasterFileUpload(this.getModel().getAttachments(), requestDTO);
            if (StringUtils.isNotEmpty(this.getModel().getRemoveFileById())) {
                List<Long> attList = Stream.of(this.getModel().getRemoveFileById().split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                fileUpload.updateAllDocumentStatus(MainetConstants.Common_Constant.INACTIVE_FLAG,
                        session.getEmployee().getEmpId(), attList);
            }
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
        }

    }

    @RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
    public ModelAndView fileCountUpload(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().getFileCountUpload().clear();

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            this.getModel().getFileCountUpload().add(entry.getKey());
        }

        int fileCount = (int) FileUploadUtility.getCurrent().getFileMap().entrySet().size();
        this.getModel().getFileCountUpload().add(fileCount + 1L);

        List<DocumentDetailsVO> attachments = new ArrayList<>();
        for (int i = 0; i <= this.getModel().getAttachments().size(); i++)
            attachments.add(new DocumentDetailsVO());

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

            attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(
                    this.getModel().getAttachments().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
        }

        if (attachments.get(this.getModel().getAttachments().size()).getDoc_DESC_ENGL() == null) {
            attachments.get(this.getModel().getAttachments().size()).setDoc_DESC_ENGL(MainetConstants.BLANK);
        } else {
            DocumentDetailsVO ob = new DocumentDetailsVO();
            ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
            attachments.add(ob);
        }

        this.getModel().setAttachments(attachments);

        return new ModelAndView("ActivityManagementUpload", MainetConstants.FORM_NAME, this.getModel());
    }

}
