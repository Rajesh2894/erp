package com.abm.mainet.common.helpdesk.ui.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.EmployeeBean;
import com.abm.mainet.common.dto.HelpDeskDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.helpdesk.ui.model.HelpDeskModel;
import com.abm.mainet.common.helpdesk.ui.validator.HelpDeskValidator;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IGroupMasterService;
import com.abm.mainet.common.service.IHelpDeskService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Controller
@RequestMapping("/HelpDesk.html")
public class HelpDeskController extends AbstractController<HelpDeskModel> {

    private static final String WATCHER = "watcher";

    private static final String COLON = " : ";

    private static final String ACTIVITY_PRIORITY = "activityPriority";

    private static final String ACTIVITY_STATUS = "activityStatus";

    private static final String ACTIVITY_TYPE = "activityType";

    private static final String HELP_GROP = "helpGrop";

    private static final String ACT_WATCHER_LIST = "actWatcherList";

    private static final String EMPLOYEES = "employees";

    private static final String HELP_DESK_LIST = "HelpDeskLIST";

    private static final String ALL_CALL_LOG = "allCallLog";

    private static final String HELP_DESK_USER = "helpDeskUser";

    private static final String HELP_DESK = "HELP_DESK/";
    
    private static final String ORGANISATION = "organisation";
    
    private static final String IS_IPRD = "isIPRDHelpDeskUser";

    @Autowired
    private IHelpDeskService helpDeskService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IGroupMasterService groupMasterService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private ISMSAndEmailService smsAndEmailService;
    
    @Autowired
    private IOrganisationService iOrganisationService;

    @RequestMapping
    public ModelAndView helpDesk(Model model, HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        UserSession session = UserSession.getCurrent();
        List<HelpDeskDTO> allCallLog = null;
        List<EmployeeBean> employees = null;
        List<Organisation> organisationList = null;
        if(session.getOrganisation().getDefaultStatus().equalsIgnoreCase("Y")) {
        	GroupMaster empGroup=groupMasterService.findByGmId(session.getEmployee().getGmid(), session.getOrganisation().getOrgid());
        	if(empGroup.getGrCode().equalsIgnoreCase(MainetConstants.HELPDESK_GROUP)) {
        		model.addAttribute(IS_IPRD, MainetConstants.AUTH);
        allCallLog = helpDeskService.getAllCallLog();
        List<GroupMaster> group = groupMasterService.getGroupMasByGroupCode(MainetConstants.HELPDESK_GROUP);
        List<Long> helpDeskUserGroupId = new ArrayList<>();
        	if (group != null) {
        		for(GroupMaster grp:group) {
        			Long groupId = grp.getGmId();
        			helpDeskUserGroupId.add(groupId);
        		}
        	}
        employees = employeeService.getEmployeeByGroup(helpDeskUserGroupId);
        organisationList =iOrganisationService.findAllActiveOrganization("A");
        model.addAttribute(ORGANISATION, organisationList);
        }else {
        allCallLog = helpDeskService.getAllIndividualAndTeamCallLog(
                    session.getOrganisation().getOrgid(),
                    session.getEmployee().getEmpId());
        employees = employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
        }
        }else {
        allCallLog = helpDeskService.getAllIndividualAndTeamCallLog(
                    session.getOrganisation().getOrgid(),
                    session.getEmployee().getEmpId());
        employees = employeeService.getAllEmployee(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        }
        
        GroupMaster group = groupMasterService.getGroupMasterByGroupCode(session.getOrganisation().getOrgid(),
                MainetConstants.HELPDESK_GROUP);
        Long helpDeskUserGroupId = null;
        if (group != null && group.getGmId() > 0L) {
            helpDeskUserGroupId = group.getGmId();
            if (UserSession.getCurrent().getEmployee().getGmid().longValue() == helpDeskUserGroupId.longValue()) {

                model.addAttribute(HELP_DESK_USER, true);

            } else {

                model.addAttribute(HELP_DESK_USER, false);
            }
        }
        model.addAttribute(ALL_CALL_LOG, allCallLog);
        model.addAttribute(EMPLOYEES, employees);
        return new ModelAndView(HELP_DESK_LIST);

    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public ModelAndView saveCallLog(Model model, HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        model.addAttribute(MainetConstants.OrgMaster.MODE, MainetConstants.OrgMaster.MODE_CREATE);
        this.getModel().getEntity().setHelpStatus(
                CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.OPEN, PrefixConstants.Prefix.ACS).getLookUpId());
        this.getModel().getEntity().setHelpStartdt(new Date());
        this.getModel().setSaveMode(MainetConstants.OrgMaster.MODE_CREATE);
        populateModel(model);
        return index();

    }

    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editCallLog(Model model, HttpServletRequest httpServletRequest, @RequestParam("actId") Long actId,
            @RequestParam("mode") String mode) {
        this.sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        // model.addAttribute(MainetConstants.OrgMaster.MODE, mode);
        this.getModel().setEntity(helpDeskService.getCallLog(actId));
        this.getModel().setSaveMode(mode);
        populateModel(model);
        final List<AttachDocs> attachDocs = fileUpload
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), HELP_DESK + actId.toString());
        this.getModel().setAttachDocsList(attachDocs);
        return new ModelAndView("HelpDeskValidn", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public ModelAndView deleteCallLog(Model model, HttpServletRequest httpServletRequest, @RequestParam("actId") Long actId) {
        this.sessionCleanup(httpServletRequest);
        helpDeskService.deleteCallLog(actId);
        this.sessionCleanup(httpServletRequest);
        UserSession session = UserSession.getCurrent();
        List<HelpDeskDTO> allCallLog = helpDeskService.getAllIndividualAndTeamCallLog(
                session.getOrganisation().getOrgid(),
                session.getEmployee().getEmpId());
        List<EmployeeBean> employees = employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(ALL_CALL_LOG, allCallLog);
        model.addAttribute(EMPLOYEES, employees);
        return new ModelAndView("HelpDeskLIST/FORM");

    }

    private void populateModel(Model model) {
        Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        GroupMaster group = groupMasterService.getGroupMasterByGroupCode(orgid, MainetConstants.HELPDESK_GROUP);
        GroupMaster superOrgGroup = groupMasterService.getGroupMasterByGroupCode(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.HELPDESK_GROUP);
        
        Long helpDeskUserGroupId = null;
        List<Employee> helpGrop = null;
        if (group != null && group.getGmId() > 0L) {
            helpDeskUserGroupId = group.getGmId();
            helpGrop = employeeService.getEmployeeByGroupId(helpDeskUserGroupId, orgid);
            if (UserSession.getCurrent().getEmployee().getGmid().longValue() == helpDeskUserGroupId.longValue()) {

                model.addAttribute(HELP_DESK_USER, true);

            } else {

                model.addAttribute(HELP_DESK_USER, false);
            }
        }
        if(UserSession.getCurrent().getOrganisation().getDefaultStatus().equalsIgnoreCase("Y")) {
        	GroupMaster empGroup=groupMasterService.findByGmId(UserSession.getCurrent().getEmployee().getGmid(), UserSession.getCurrent().getOrganisation().getOrgid());
        	if(empGroup.getGrCode().equalsIgnoreCase(MainetConstants.HELPDESK_GROUP)) {
        getModel().setIsIPRDHelpDeskUser(MainetConstants.AUTH);
        if (superOrgGroup != null && superOrgGroup.getGmId() > 0L) {
            helpDeskUserGroupId = superOrgGroup.getGmId();
            helpGrop = employeeService.getEmployeeByGroupId(helpDeskUserGroupId, orgid);
        }
        }else {
        	helpDeskUserGroupId = group.getGmId();
            helpGrop = employeeService.getEmployeeByGroupId(helpDeskUserGroupId, orgid);
        }
        }else {
        	 if (superOrgGroup != null && superOrgGroup.getGmId() > 0L) {
        		 helpDeskUserGroupId = superOrgGroup.getGmId();
        		 helpGrop = employeeService.getEmployeeByGroupId(helpDeskUserGroupId, orgid);
        	 }
        }
        
        List<Organisation> organisationList =iOrganisationService.findAllActiveOrganization("A");
        if(organisationList!=null) {
        	model.addAttribute(ORGANISATION, organisationList);
        }
        	
        List<EmployeeBean> employees = employeeService.getAllEmployee(orgid);
        List<HelpDeskDTO> allCallLog = helpDeskService
                .findAllCallLogByOrgid(orgid);
        final List<LookUp> activityType = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.CAT,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> activityStatus = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.ACS,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> activityPriority = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.SEV,
                UserSession.getCurrent().getOrganisation());

        String actWatcher = this.getModel().getEntity().getHelpWatcher();
        long currentEmployee = UserSession.getCurrent().getEmployee().getEmpId().longValue();
        if (this.getModel().getEntity().getHelpEmpid() != null && this.getModel().getEntity().getHelpEmpid() != currentEmployee
                && StringUtils.isNotBlank(actWatcher)) {
            if (Stream.of(actWatcher.split(MainetConstants.operator.COMA))
                    .map(Long::parseLong).anyMatch(x -> x == currentEmployee)) {
                model.addAttribute(WATCHER, MainetConstants.Common_Constant.YES);
            } else {
                model.addAttribute(WATCHER, MainetConstants.Common_Constant.NO);
            }

        }

        if (StringUtils.isNotEmpty(actWatcher)) {
            List<Long> items = Stream.of(actWatcher.split(MainetConstants.operator.COMA))
                    .map(Long::parseLong).collect(Collectors.toList());
            model.addAttribute(ACT_WATCHER_LIST, items);
        }

        model.addAttribute(EMPLOYEES, employees);
        model.addAttribute(HELP_GROP, helpGrop);
        model.addAttribute(ALL_CALL_LOG, allCallLog);
        model.addAttribute(ACTIVITY_TYPE, activityType);
        model.addAttribute(ACTIVITY_STATUS, activityStatus);
        model.addAttribute(ACTIVITY_PRIORITY, activityPriority);

        /*
         * if (this.getModel().getEntity().getHelpId() == null) { model.addAttribute(MainetConstants.OrgMaster.MODE,
         * MainetConstants.OrgMaster.MODE_CREATE); }
         */
    }

    @RequestMapping(params = "saveHelpdesk", method = RequestMethod.POST)
    public ModelAndView saveform(Model model1, final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final HelpDeskModel model = getModel();

        model.validateBean(model.getEntity(), HelpDeskValidator.class);
        UserSession session = UserSession.getCurrent();
        if (model.hasValidationErrors()) {
            populateModel(model1);
            return defaultMyResult();
        } else {

            if (StringUtils.isNotEmpty(model.getEntity().getEditHelpNote())) {
                StringBuilder notes = null;
                if (StringUtils.isEmpty(model.getEntity().getHelpNote())) {
                    notes = new StringBuilder((session.getEmployee().getFullName())).append(COLON)
                            .append(model.getEntity().getEditHelpNote());
                    model.getEntity().setHelpNote(notes.toString());
                } else {
                    notes = new StringBuilder(model.getEntity().getHelpNote())
                            .append(MainetConstants.operator.ORR)
                            .append(session.getEmployee().getFullName()).append(COLON)
                            .append(model.getEntity().getEditHelpNote());
                    model.getEntity().setHelpNote(notes.toString());
                }
            }

            if (model.getEntity().getHelpId() != null) {
                model.getEntity().setLgIpMacUpd(session.getEmployee().getEmppiservername());
                model.getEntity().setUpdatedBy(session.getEmployee().getEmpId());
                model.getEntity().setUpdatedDate(new Date());
                if (null != model.getEntity().getHelpStatus()) {
                    if (CommonMasterUtility.getCPDDescription(model.getEntity().getHelpStatus(),
                            MainetConstants.D2KFUNCTION.CPD_VALUE).equals(MainetConstants.COMPLETED)) {
                        model.getEntity().setHelpEnddt(new Date());
                    }
                }
            } else {
                model.getEntity().setCreatedBy(session.getEmployee().getEmpId());
                model.getEntity().setCreatedDate(new Date());
                model.getEntity().setLgIpMac(session.getEmployee().getEmppiservername());
                model.getEntity().setOrgid(session.getOrganisation().getOrgid());

                if (StringUtils.isNotEmpty(model.getEntity().getHelpNote())) {
                    StringBuilder notes = new StringBuilder((session.getEmployee().getFullName())).append(COLON)
                            .append(model.getEntity().getHelpNote());
                    model.getEntity().setHelpNote(notes.toString());
                }
            }
        	long id=model.getEntity().getHelpId()==null?0:model.getEntity().getHelpId();
            HelpDeskDTO actDto = helpDeskService.saveOrUpdateCallLog(model.getEntity());

            RequestDTO requestDTO = new RequestDTO();
            if(getModel().getOrganisationId()!=null) {
            	requestDTO.setOrgId(getModel().getOrganisationId());
            }else {
            	requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            }
            
            requestDTO.setStatus(MainetConstants.AuthStatus.APPROVED);
            requestDTO.setIdfId(HELP_DESK + actDto.getHelpId().toString());
            requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.EIP);
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
                List<Long> attList = Stream.of(this.getModel().getRemoveFileById().split(MainetConstants.operator.COMA))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                fileUpload.updateAllDocumentStatus(MainetConstants.Common_Constant.INACTIVE_FLAG,
                        session.getEmployee().getEmpId(), attList);
            }
            EmployeeBean empbean = employeeService.findById(actDto.getHelpEmpid());
            EmployeeBean emp = employeeService.findById(actDto.getCreatedBy());
            SMSAndEmailDTO emailDto = new SMSAndEmailDTO();
            if(id==0) {
            	if(null!=empbean) {
            	 emailDto.setMobnumber(empbean.getEmpmobno());
                 if(empbean.getEmpemail()!=null) {
                 emailDto.setEmail(empbean.getEmpemail());}
                 emailDto.setEmpName(empbean.getFullName());
            	}
                   
            }else {
            	if(null!=emp) {
            	  emailDto.setMobnumber(emp.getEmpmobno());
                  if(emp.getEmpemail()!=null) {
                  emailDto.setEmail(emp.getEmpemail());}
                  emailDto.setEmpName(emp.getFullName()); 
            	}
            }
            emailDto.setTokenNumber(actDto.getHelpId().toString());
            emailDto.setStatus(CommonMasterUtility.getCPDDescription(model.getEntity().getHelpStatus(),
                    MainetConstants.BLANK));

            smsAndEmailService.sendEmailSMS(MainetConstants.DEPARTMENT.ONLINE_SERVICES_CODE, "HelpDesk.html",
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, emailDto,
                    UserSession.getCurrent().getOrganisation(), 1);
            }
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
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

        return new ModelAndView("HelpDeskUpload", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(method = RequestMethod.POST,params = "employeeList")
    public @ResponseBody List<LookUp> getEmployeeList(@RequestParam("organisationId") final Long orgId) {
    	List<GroupMaster> group = groupMasterService.getGroupMasByGroupCode(MainetConstants.HELPDESK_GROUP);
         List<Long> helpDeskUserGroupId = new ArrayList<>();
         	if (group != null) {
         		for(GroupMaster grp:group) {
         			Long groupId = grp.getGmId();
         			helpDeskUserGroupId.add(groupId);
         		}
         	}
             
        final List<Employee> list = employeeService.getEmployeeByListOfGroupId(helpDeskUserGroupId,orgId);
        final List<LookUp> lookupList = new ArrayList<>();
        LookUp lookup = null;
        for (final Employee emp : list) {
            lookup = new LookUp();
            lookup.setLookUpId(emp.getEmpId());
            lookup.setLookUpCode(emp.getFullName());
            lookupList.add(lookup);
        }
        return lookupList;
    }
    
}
